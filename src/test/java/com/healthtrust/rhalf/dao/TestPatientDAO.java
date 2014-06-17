package com.healthtrust.rhalf.dao;


import org.cassandraunit.CQLDataLoader;
import org.cassandraunit.dataset.cql.ClassPathCQLDataSet;
import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Cluster.Builder;
import com.datastax.driver.core.Session;
import com.datastax.driver.mapping.MappingSession;
import com.healthtrust.rhalf.constants.Constants;
import com.healthtrust.rhalf.resources.Patient;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Cluster.class,CQLDataLoader.class,Session.class})
public class TestPatientDAO {

	private PatientDAOImpl dao = null;
	private String dataSetPath = "cassandra-init.cql";
	private MappingSession mappingSession;
	private String server = "localhost";
	private int port = 9142;
	private String keyspace = "test";
	
	@Before
	public void before(){
		try{
			mappingSession = PowerMock.createMock(MappingSession.class);
			dao = PowerMock.createMock(PatientDAOImpl.class, server, port, keyspace, mappingSession,dataSetPath);
		}catch(Exception e){
			Assert.fail("Could not start embedded cassandra instance.");
		}
	}
	
	@Test
	public void testInit() throws Exception{
		Cluster cluster = PowerMock.createMock(Cluster.class);
		Builder builder = PowerMock.createMock(Builder.class);
		Session session = PowerMock.createMock(Session.class);
		ClassPathCQLDataSet dataSet = PowerMock.createMock(ClassPathCQLDataSet.class);
		CQLDataLoader loader = PowerMock.createMock(CQLDataLoader.class);
		
		PowerMock.stub(PowerMock.method(Cluster.class, "builder")).toReturn(builder);
		PowerMock.replay(Cluster.class);
		EasyMock.expect(builder.addContactPoints(server.split(Constants.SERVER_DELIMITER))).andReturn(builder);
		EasyMock.expect(builder.withPort(port)).andReturn(builder);
		EasyMock.expect(builder.build()).andReturn(cluster);
		PowerMock.replay(builder);
				
		EasyMock.expect(cluster.connect()).andReturn(session);
				
		PowerMock.expectNew(CQLDataLoader.class, session).andReturn(loader);
		PowerMock.expectNew(ClassPathCQLDataSet.class, dataSetPath).andReturn(dataSet);
		
		PowerMock.suppress(PowerMock.method(CQLDataLoader.class, "load"));
		loader.load(dataSet);		
		PowerMock.replay(loader);
		
		EasyMock.expect(cluster.connect(keyspace)).andReturn(session);
		PowerMock.replay(cluster);
		
		PowerMock.expectNew(MappingSession.class, keyspace, session).andReturn(mappingSession);
		PowerMock.replay(session);
		
		dao.init(server, port, keyspace);
		PowerMock.replay(dao);
		PowerMock.verify(dao);
	}
	
	@Test
	public void testCreate() throws Exception{
		EasyMock.expect(mappingSession.save(EasyMock.anyObject())).andReturn(null);
		PowerMock.replay(mappingSession);
		dao.create(new Object());
		PowerMock.replay(dao);
		PowerMock.verify(dao);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testRead() throws Exception{
		EasyMock.expect(mappingSession.get(EasyMock.anyObject(Class.class), EasyMock.anyObject(String.class))).andReturn(null);
		PowerMock.replay(mappingSession);
		dao.read(Patient.class, "key");
		PowerMock.replay(dao);
		PowerMock.verify(dao);
	}
	
	@Test
	public void testUpdate() throws Exception{
		EasyMock.expect(mappingSession.save(EasyMock.anyObject())).andReturn(null);
		PowerMock.replay(mappingSession);
		dao.update(new Object());
		PowerMock.replay(dao);
		PowerMock.verify(dao);
	}
	
	@Test
	public void testDelete() throws Exception{
		mappingSession.delete(EasyMock.anyObject());
		PowerMock.replay(mappingSession);
		dao.delete(new Object());
		PowerMock.replay(dao);
		PowerMock.verify(dao);
	}
}
