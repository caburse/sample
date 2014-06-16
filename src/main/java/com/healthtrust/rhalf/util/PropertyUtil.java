package com.healthtrust.rhalf.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.util.Properties;

import javax.naming.InitialContext;

import org.apache.cassandra.exceptions.ConfigurationException;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.thrift.transport.TTransportException;
import org.cassandraunit.CQLDataLoader;
import org.cassandraunit.CassandraCQLUnit;
import org.cassandraunit.dataset.cql.ClassPathCQLDataSet;
import org.cassandraunit.utils.EmbeddedCassandraServerHelper;

import com.healthtrust.rhalf.constants.Constants;
import com.healthtrust.rhalf.constants.PropertiesConstants;

/**
 * Tries to find property file in name space otherwise
 * it checks to see if the file is on the file system.
 * Uses env field to do this.
 * Either env = dev/qa/prod or env = ../conf/*.properties
 * (reletive or exact path of file)
 * @author caburse
 */
public final class PropertyUtil {
	private static final Logger LOGGER = Logger.getLogger(PropertyUtil.class);	
	private static PropertyUtil resource = null;
	private Properties properties = null;
	private String environment = null;
	
	/**
	 * Use Static instantiation PropertyUtil.getInstance() 
	 */
	private PropertyUtil(){
		try{
			loadURL();
		}catch(IOException ioe){
			LOGGER.error("Unable to load Property File");
		}
		LOGGER.debug("INSTANTIATED");
	}
	
	/**
	 * Instantiate static PropertyUtil object.
	 * @return
	 */
	public static PropertyUtil getInstance(){		
		if(resource==null){
			resource = new PropertyUtil();
		}
		return resource;
	}
	
	/**
	 * Try to get property file from JNDI
	 */
	private void loadURL() throws IOException{
		LOGGER.debug("BEGIN");		
		InputStream fileInputStream = null;
					
		try {
			initCassandra();
			InitialContext initCtx = new InitialContext();
			String location = (String) initCtx.lookup(PropertiesConstants.PROPERTY_URL_JNDI);
			fileInputStream = new FileInputStream(new File(location));
			
			loadPropertyFile(fileInputStream);
			
			LOGGER.debug("Loading Property File" + location);
		}catch (Exception e) {
			loadENV();
		}
		setLogLevel(getInt(PropertiesConstants.LOG_LEVEL));
		LOGGER.debug("END");
	}
	
	private void initCassandra() throws TTransportException, IOException, ConfigurationException{
		EmbeddedCassandraServerHelper.startEmbeddedCassandra("/embedded-cassandra.yaml");		
		LOGGER.debug("Instantiating embedded Cassandra Instance **TESTING ONLY!!!**");		
	}
	
	/**
	 * Try to figure out property file from local environment otherwise load default.
	 */
	private void loadENV() throws IOException{
		LOGGER.debug("BEGIN");		
		InputStream fileInputStream = null;
					
		try {			
			String hostname = InetAddress.getLocalHost().getHostName();
			String prefix = hostname.substring(Constants.DEFAULT_ZERO, Constants.FOUR);
			String env = Constants.ENVIRONMENT.get(prefix.toLowerCase());
			environment=env;
			fileInputStream = getPropertyFile(env);
			
			loadPropertyFile(fileInputStream);
		} catch (IOException e) {
			fileInputStream = PropertyUtil.getStream(Constants.DEFAULT_PROPERTY);
			loadPropertyFile(fileInputStream);
			LOGGER.error("Loading Default Property file:"+Constants.DEFAULT_PROPERTY);			
		}catch (Exception e) {
			fileInputStream = PropertyUtil.getStream(Constants.DEFAULT_PROPERTY);
			loadPropertyFile(fileInputStream);
			LOGGER.error("Loading Default Property file:"+Constants.DEFAULT_PROPERTY);
		}
		LOGGER.debug("END");
	}
	
	/**
	 * Load property from stream extracted to avoid duplicate calls.
	 * @param stream
	 * @throws IOException
	 */
	private void loadPropertyFile(InputStream stream) throws IOException{
		properties = new Properties();
		properties.load(stream);
	}
		
	/**
	 * Get string representation of value from property file
	 * @param urlKey
	 * @return
	 */
	public String getString(String urlKey) {
		return properties.getProperty(urlKey);				
	}
	
	/**
	 * Get integer representation of value from property file
	 * @param urlKey
	 * @return
	 */
	public int getInt(String urlKey) {
		int value = 0;
		String text = null;
		try{
			text = properties.getProperty(urlKey).trim();
			value = Integer.parseInt(text);
		}catch(Exception e){LOGGER.error("Couldn't parse "+urlKey +":" + text);}
		return value;				
	}
	
	/**
	 * Code that concatenates 
	 * Constants.PROPERTY_PREFIX + env.toLowerCase()+ Constants.PROPERTY_POSTFIX
	 * in an attempt to find the property file that follows this naming convention
	 * @param env
	 * @return
	 * @throws FileNotFoundException
	 */
	private InputStream getPropertyFile(String env)throws FileNotFoundException{		
		String file =   Constants.PROPERTY_PREFIX+
						env.toLowerCase()+
						Constants.PROPERTY_POSTFIX;		
		LOGGER.debug("Loading Property File " + file);
		return PropertyUtil.getStream(file);
	}
	
	/**
	 * Sets logging level of root logger.
	 * @param level
	 */
	public static void setLogLevel(int level){
		switch(level){
			case ONE: Logger.getRootLogger().setLevel(Level.ALL); LOGGER.info("Logging Level set to LEVEL.ALL"); break;
			case TWO: Logger.getRootLogger().setLevel(Level.DEBUG); LOGGER.info("Logging Level set to LEVEL.DEBUG"); break;
			case THREE: Logger.getRootLogger().setLevel(Level.ERROR); LOGGER.info("Logging Level set to LEVEL.ERROR"); break;
			case FOUR: Logger.getRootLogger().setLevel(Level.FATAL); LOGGER.info("Logging Level set to LEVEL.FATAL"); break;
			case FIVE: Logger.getRootLogger().setLevel(Level.INFO); LOGGER.info("Logging Level set to LEVEL.INFO"); break;
			case SIX: Logger.getRootLogger().setLevel(Level.OFF); LOGGER.info("Logging Level set to LEVEL.OFF"); break;
			case SEVEN: Logger.getRootLogger().setLevel(Level.WARN); LOGGER.info("Logging Level set to LEVEL.WARN"); break;
			default: Logger.getRootLogger().setLevel(Level.ALL); LOGGER.info("Logging Level defaulted to LEVEL.ALL Please add parameter to properties file for custom logging.");break;
		}
	}
	
	/**
	 * Try to figure out if the file is on the local disk or within the java package.
	 * @param fileName
	 * @return
	 */
	public static InputStream getStream(String fileName){
		InputStream fileInputStream = null;
		try{
			fileInputStream = new FileInputStream(new File(fileName));
			LOGGER.info("Property File:" + fileName);
		}catch(Exception e){}

		//Just in case file doesn't exist.
		if(null == fileInputStream){			
			fileInputStream = PropertyUtil.class.getClassLoader().getResourceAsStream(fileName);
			LOGGER.info("Property File:" + fileName);
		}		
		return fileInputStream;
	}
	
	public String getENV(){
		return environment;
	}
	
	final static int ONE = 1;
	final static int TWO = 2;
	final static int THREE = 3;
	final static int FOUR = 4;
	final static int FIVE = 5;
	final static int SIX = 6;
	final static int SEVEN = 7;
}
