package com.healthtrust.rhalf.dao;


import org.cassandraunit.utils.EmbeddedCassandraServerHelper;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.healthtrust.rhalf.resources.Patient;

public class PatientDAOIT {

	private static PatientDAO dao = null;
	private static String dataSetPath = "cassandra-init.cql";
	
	@BeforeClass
	public static void before(){
		try{
			EmbeddedCassandraServerHelper.startEmbeddedCassandra();
			dao = new PatientDAOImpl("localhost", 9142, "healthtrust", dataSetPath);
		}catch(Exception e){
			Assert.fail("Could not start embedded cassandra instance.");
		}
	}
		
	@Test
	public void testCreate() throws Exception{
		dao.create(getPatient());
		Patient p = dao.read(Patient.class, "cburse@gmail.com");
		Assert.assertNotNull("I don't exist :(", p);
	}
	
	@Test
	public void testRead() throws Exception{
		Patient p = dao.read(Patient.class, "bobby.brown@healthtrustpg.com");
		Assert.assertNotNull("Where's Bobby(Not the singer)!",p);
	}
	
	@Test
	public void testUpdate() throws Exception{
		String position = "Lacky";
		Patient p1 = dao.read(Patient.class, "michael.arnold@healthtrustpg.com");
		Patient p2 = dao.read(Patient.class, "michael.arnold@healthtrustpg.com");
		p1.setPosition(position);
		dao.update(p1);
		p1 = dao.read(Patient.class, "michael.arnold@healthtrustpg.com");
		
		Assert.assertNotEquals("Original Michael shouldn't be a lacky!",p2.getPosition(), position);
		Assert.assertEquals("New Michael should be a lacky!",p1.getPosition(), position);
	}
	
	@Test
	public void testDelete() throws Exception{
		dao.delete(getPatient());
		Patient cb = dao.read(Patient.class, "cburse@gmail.com");
		Assert.assertNull("What am I doing here?!?!?!?",cb);
	}
	
	private Patient getPatient(){
		return new Patient("cburse@gmail.com","Charles","Burse","Lacky");
	}
}
