package com.healthtrust.rhalf.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.naming.InitialContext;

import org.apache.cassandra.exceptions.ConfigurationException;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.thrift.transport.TTransportException;
import org.cassandraunit.utils.EmbeddedCassandraServerHelper;

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
	private InputStream fileInputStream = null;
	
	/**
	 * Use Static instantiation PropertyUtil.getInstance() 
	 */
	private PropertyUtil(){
		try{
			loadURL();
			Runtime.getRuntime().addShutdownHook(new UtilShutdownHook());
		}catch(IOException ioe){
			LOGGER.error("Unable to load Property File\n"+ioe);
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
	 * Try to get property file from JNDI else load default
	 */
	private void loadURL() throws IOException{
		LOGGER.debug("BEGIN");
					
		try {
			initCassandra();
			InitialContext initCtx = new InitialContext();
			String location = (String) initCtx.lookup(PropertiesConstants.PROPERTY_URL_JNDI);
			fileInputStream = new FileInputStream(new File(location));
			
			loadPropertyFile(fileInputStream);
			
			LOGGER.debug("Loading Property File" + location);
		}catch (Exception e) {
			LOGGER.debug("No JNDI Settings found. " + e);			
		}
		setLogLevel(getInt(PropertiesConstants.LOG_LEVEL));
		LOGGER.debug("END");
	}
	
	private void initCassandra() throws TTransportException, IOException, ConfigurationException{
		EmbeddedCassandraServerHelper.startEmbeddedCassandra("/embedded-cassandra.yaml");	
		LOGGER.debug("Instantiating embedded Cassandra Instance **TESTING ONLY!!!**");		
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
		}catch(Exception e){
			LOGGER.error("Couldn't parse "+urlKey +":" + text+"\n"+e);
		}
		return value;				
	}
	

	/** ALL **/
	final static int ONE = 1;
	/** DEBUG **/
	final static int TWO = 2;
	/** ERROR **/
	final static int THREE = 3;
	/** FATAL **/
	final static int FOUR = 4;
	/** INFO **/
	final static int FIVE = 5;
	/** OFF **/
	final static int SIX = 6;
	/** WARN **/
	final static int SEVEN = 7;
	/** TRACE **/
	final static int EIGHT = 8;
	
	/**
	 * Sets logging level of root logger.
	 * Default being ALL
	 * @param level
	 */
	public static void setLogLevel(int level){
		switch(level){
			case ONE: 
				Logger.getRootLogger().setLevel(Level.ALL); 
				LOGGER.info("Logging Level set to LEVEL.ALL"); 
				break;
			case TWO: 
				Logger.getRootLogger().setLevel(Level.DEBUG); 
				LOGGER.info("Logging Level set to LEVEL.DEBUG"); 
				break;
			case THREE:
				Logger.getRootLogger().setLevel(Level.ERROR);
				LOGGER.info("Logging Level set to LEVEL.ERROR");
				break;
			case FOUR:
				Logger.getRootLogger().setLevel(Level.FATAL);
				LOGGER.info("Logging Level set to LEVEL.FATAL");
				break;
			case FIVE:
				Logger.getRootLogger().setLevel(Level.INFO);
				LOGGER.info("Logging Level set to LEVEL.INFO");
				break;
			case SIX:
				Logger.getRootLogger().setLevel(Level.OFF); 
				LOGGER.info("Logging Level set to LEVEL.OFF");
				break;
			case SEVEN:
				Logger.getRootLogger().setLevel(Level.WARN);
				LOGGER.info("Logging Level set to LEVEL.WARN");
				break;
			case EIGHT:
				Logger.getRootLogger().setLevel(Level.TRACE);
				LOGGER.info("Logging Level set to LEVEL.WARN");
				break;
			default: 
				Logger.getRootLogger().setLevel(Level.ALL);
				LOGGER.info("Logging Level defaulted to LEVEL.ALL Please add parameter to properties file for custom logging.");
				break;
		}
	}
	
	/**
	 * Try to figure out if the file is on the local disk or within the java package.
	 * @param fileName
	 * @return
	 */
	@SuppressWarnings("resource")
	public static InputStream getStream(String fileName){
		InputStream fileInputStream = null;
		try{
			fileInputStream = new FileInputStream(new File(fileName));
			LOGGER.info("Property File:" + fileName);
		}catch(Exception e){
			LOGGER.debug("Attempting to grab resource as stream."+e.getMessage());
		}

		//Just in case file doesn't exist.
		if(null == fileInputStream){			
			fileInputStream = PropertyUtil.class.getClassLoader().getResourceAsStream(fileName);
			LOGGER.info("Property File:" + fileName);
		}		
		return fileInputStream;
	}
		
	private class UtilShutdownHook extends Thread{
				
		@Override
		public void run(){
			try{
				if(null != fileInputStream){
					fileInputStream.close();
				}
			}catch(IOException ioe){
				LOGGER.debug("Issue encountered closing input stream "+ioe);
			}
		}
	}
	
}
