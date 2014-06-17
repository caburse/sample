package com.healthtrust.rhalf.dao;

import org.cassandraunit.CQLDataLoader;
import org.cassandraunit.dataset.cql.ClassPathCQLDataSet;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.MappingSession;
import com.healthtrust.rhalf.constants.Constants;

public class PatientDAOImpl  implements PatientDAO{
	
//	private static final Logger LOGGER = Logger.getLogger(PatientDAOImpl.class);
	
	private MappingSession mapper;
		
	private String dataSetPath = "/cassandra-init.cql";
		
	public PatientDAOImpl() {		
	}
	protected PatientDAOImpl(String servers, int port, String keyspace, MappingSession mapper, String dataSetPath) {
		this.mapper=mapper;
		this.dataSetPath=dataSetPath;
	}
	public PatientDAOImpl(String servers, int port, String keyspace) {		
		init(servers, port, keyspace);
	}
	
	/**
	 * I don't like it but for testing the relative path doesn't work
	 * @param servers
	 * @param port
	 * @param keyspace
	 * @param dataSetPath
	 */
	protected PatientDAOImpl(String servers, int port, String keyspace, String dataSetPath) {
		this.dataSetPath=dataSetPath;
		init(servers, port, keyspace);
	}
	
	public void init(String servers, int port, String keyspace){
		Cluster cluster = Cluster.builder().addContactPoints(servers.split(Constants.SERVER_DELIMITER)).withPort(port).build();
		
		//Initial Test Set
		CQLDataLoader loader = new CQLDataLoader(cluster.connect());
		loader.load(new ClassPathCQLDataSet(dataSetPath));
				
		Session session = (keyspace == null || keyspace.isEmpty())?cluster.connect():cluster.connect(keyspace);			
		
		mapper = new MappingSession(keyspace, session);
	}
		
	public Object create(Object obj){
		return mapper.save(obj);
	}
	
	public <E> E read(Class<E> entityClass, Object primaryKey){
		return mapper.get(entityClass, primaryKey);
	}
	
	public Object update(Object obj){
		return mapper.save(obj);
	}
	
	public <E> void delete(Object obj){
		mapper.delete(obj);
	}
}