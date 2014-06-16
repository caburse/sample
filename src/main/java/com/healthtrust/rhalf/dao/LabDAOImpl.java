package com.healthtrust.rhalf.dao;

import org.apache.cassandra.service.EmbeddedCassandraService;
import org.apache.log4j.Logger;
import org.cassandraunit.CQLDataLoader;
import org.cassandraunit.CassandraCQLUnit;
import org.cassandraunit.DataLoader;
import org.cassandraunit.dataset.cql.ClassPathCQLDataSet;
import org.cassandraunit.utils.EmbeddedCassandraServerHelper;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ConsistencyLevel;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.SimpleStatement;
import com.datastax.driver.core.Statement;
import com.healthtrust.rhalf.constants.Constants;

public class LabDAOImpl  implements LabDAO{
	
	private static Logger logger = Logger.getLogger(LabDAOImpl.class);
	
	private static Session session;
	private static Cluster cluster;
		
	public LabDAOImpl() {}
	public LabDAOImpl(String servers, int port, String keyspace) {		
		init(servers, port, keyspace);
	}
	
	public void init(String servers, int port, String keyspace){
		cluster = Cluster.builder().addContactPoints(servers.split(Constants.SERVER_DELIMITER)).withPort(port).build();
		Metadata metadata=cluster.getMetadata();
		
		logger.info(String.format("Connected to cluster: %s \n", metadata.getClusterName()));
		
		for(Host host : metadata.getAllHosts()){
			logger.info(String.format("Datacenter: %s; Host: %s, args)); Rack: %s\n",host.getDatacenter(), host.getAddress(), host.getRack()));
		}
				
		CQLDataLoader loader = new CQLDataLoader(cluster.connect());
		loader.load(new ClassPathCQLDataSet("/cassandra-init.cql"));
				
		if(session == null){
			session = (keyspace == null || keyspace.isEmpty())?cluster.connect():cluster.connect(keyspace);			
		}
	}
	
	
	public ResultSet execute(String cql){
		logger.debug("Query Run: "+cql);
		Statement cqlQuery = new SimpleStatement(cql)
			.setConsistencyLevel(ConsistencyLevel.ONE)
			.enableTracing();
		return session.execute(cqlQuery);
	}	
}