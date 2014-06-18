package com.healthtrust.rhalf.service;

import javax.ws.rs.core.Response;

import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.powermock.api.easymock.PowerMock;

import com.healthtrust.rhalf.dao.PatientDAO;
import com.healthtrust.rhalf.dao.PatientDAOImpl;
import com.healthtrust.rhalf.resources.Patient;
import com.healthtrust.rhalf.service.SampleService;



public class TestLabRestService {

//	private static Logger logger = Logger.getLogger(TestLabRestService.class);
//	private DefaultClientConfig clientConfig;
//	private Client client = null;	
//	private final String baseURI = "http://localhost:8080/ht-rhalf";
	
	private static SampleService service; 
	private static PatientDAO dao = null;
	
	@Before
	public void before(){
		dao = PowerMock.createMock(PatientDAOImpl.class);
		service = PowerMock.createMock(SampleService.class, dao);
	}
	
	public TestLabRestService() {
		//For testing when service is running.
//		clientConfig = new DefaultClientConfig();
//		client = Client.create(clientConfig);		
	}
	
	 
	@Test//http://localhost:8080/ht-rhalf/get/emailaddress
	public void testGet() throws Exception{
//		ClientResponse response=null;
//		WebResource service = client.resource(baseURI).path("get").("bobby.brown@healthtrustpg.com");				
//		try{		
//			response = service.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
//		}catch(Exception e){
//			logger.info(e);
//		}			
//		Assert.assertTrue(200==response.getStatus());	
//		Assert.assertNotNull(response);
//		logger.info(response.getStatus());
//		logger.info(response.getEntity(String.class));
	}
	
	@Test//http://localhost:8080/ht-rhalf/post
	public void testPost() throws Exception{		
//		ClientResponse response=null;
//		WebResource service = client.resource(baseURI).path("post");
//		Patient patient = new Patient("cburse@gmail.com","Charles","Burse","Lacky");
//		try{		
//			response = service.accept(MediaType.APPLICATION_JSON).post(ClientResponse.class,patient);
//		}catch(Exception e){
//			logger.info(e);
//		}			
//		Assert.assertTrue(201==response.getStatus());	
//		Assert.assertNotNull(response);
//		logger.info(response.getStatus());
//		logger.info(response.getEntity(String.class));
	}
	
	@Test
	public void testMockGet() throws Exception{
		Patient patient = new Patient("Syed.N@healthtrustpg.com", "Syed", "N", "President");
		String id = "Syed.N@healthtrustpg.com";
		EasyMock.expect(dao.read(Patient.class, id)).andReturn(patient);
		PowerMock.replay(dao);		
				
		Response response = service.get(id);
		Assert.assertNotNull("Get shouldn't be null",response);
		Assert.assertTrue("Get status shouldn't be "+response.getStatus(),200==response.getStatus());
		
		PowerMock.replay(service);
		PowerMock.verify(service);
	}
	
	@Test
	public void testMockGetNullReturn() throws Exception{		
		String id = "Kristin.C@healthtrustpg.com";
		EasyMock.expect(dao.read(Patient.class, id)).andReturn(null);
		PowerMock.replay(dao);		
				
		Response response = service.get(id);
		Assert.assertNotNull("Get shouldn't be null",response);
		Assert.assertTrue("Get status shouldn't be "+response.getStatus(),204==response.getStatus());
		
		PowerMock.replay(service);
		PowerMock.verify(service);
	}
	
	@Test
	public void testMockGetBadParam() throws Exception{
		String id = "Jerry.R@healthtrustpg";
				
		Response response = service.get(id);
		Assert.assertNotNull("Get shouldn't be null",response);
		Assert.assertTrue("Get status shouldn't be "+response.getStatus(),204==response.getStatus());
		
		PowerMock.replay(service);
		PowerMock.verify(service);
	}
	
	@Test
	public void testMockPost() throws Exception{				
		Patient patient = new Patient("abc@healthtrustpg.com", "abc", "N", "President");		
		EasyMock.expect(dao.create(patient)).andReturn(patient);
		PowerMock.replay(dao);		
				
		Response response = service.post(patient);
		Assert.assertNotNull("Post shouldn't be null",response);
		Assert.assertTrue("Post status shouldn't be " + response.getStatus(),202==response.getStatus());	
		
		PowerMock.replay(service);
		PowerMock.verify(service);
	}
	
	@Test
	public void testMockPostNullReturn() throws Exception{				
		Patient patient = new Patient("abc@healthtrustpg.com", "abc", "N", "President");		
		EasyMock.expect(dao.create(patient)).andReturn(null);
		PowerMock.replay(dao);		
				
		Response response = service.post(patient);
		Assert.assertNotNull("Post shouldn't be null",response);
		Assert.assertTrue("Post status shouldn't be " + response.getStatus(),500==response.getStatus());	
		
		PowerMock.replay(service);
		PowerMock.verify(service);
	}
	
	@Test
	public void testMockPut() throws Exception{				
		Patient patient = new Patient("abc@healthtrustpg.com", "abc", "N", "President");		
		EasyMock.expect(dao.update(patient)).andReturn(patient);
		PowerMock.replay(dao);		
				
		Response response = service.put(patient);
		Assert.assertNotNull("Put Should not be null",response);
		Assert.assertTrue("Put status shouldn't be "+response.getStatus(),200==response.getStatus());		
		PowerMock.replay(service);
		PowerMock.verify(service);
	}
	
	@Test
	public void testMockPutNullReturn() throws Exception{				
		Patient patient = new Patient("abc@healthtrustpg.com", "abc", "N", "President");		
		EasyMock.expect(dao.update(patient)).andReturn(null);
		PowerMock.replay(dao);		
				
		Response response = service.put(patient);
		Assert.assertNotNull("Put Should not be null",response);
		Assert.assertTrue("Put status shouldn't be "+response.getStatus(),500==response.getStatus());		
		PowerMock.replay(service);
		PowerMock.verify(service);
	}
	
	@Test
	public void testMockDelete() throws Exception{		
		Patient patient = new Patient("abc@healthtrustpg.com", "abc", "N", "President");		
		dao.delete(patient);		
		PowerMock.replay(dao);		
				
		Response response = service.delete(patient);
		Assert.assertNotNull("Delete Response should not be null",response);
		Assert.assertTrue("Delete status shouldn't be "+response.getStatus(),202==response.getStatus());
		
		PowerMock.replay(service);
		PowerMock.verify(service);
	}
	
	
}