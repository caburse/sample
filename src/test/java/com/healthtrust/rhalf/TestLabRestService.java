//package com.healthtrust.rhalf;
//
//import javax.ws.rs.core.MediaType;
//
//import org.apache.log4j.Logger;
//import org.easymock.EasyMock;
//import org.junit.Assert;
//import org.junit.Test;
//import org.powermock.api.easymock.PowerMock;
//
//import com.sun.jersey.api.client.Client;
//import com.sun.jersey.api.client.ClientResponse;
//import com.sun.jersey.api.client.WebResource;
//import com.sun.jersey.api.client.config.DefaultClientConfig;
//import com.healthtrust.rhalf.dao.LabDAO;
//import com.healthtrust.rhalf.dao.LabDAOImpl;
//import com.healthtrust.rhalf.exception.BaseWebException;
//import com.healthtrust.rhalf.service.LabRestService;
//
//
//
//public class TestLabRestService {
//
//	private static Logger logger = Logger.getLogger(TestLabRestService.class);
////	private DefaultClientConfig clientConfig;
////	private Client client = null;	
////	private final String baseURI = "http://localhost:8080/Lab1";
//	
//	private LabRestService service; 
//	LabDAO dao = null;
//		
//	public TestLabRestService() {
////		clientConfig = new DefaultClientConfig();
////		client = Client.create(clientConfig);		
//
//		dao = PowerMock.createMock(LabDAOImpl.class);
//		service = PowerMock.createMock(LabRestService.class, dao);
//	}
//	
//	 
//	@Test//http://localhost:8080/Lab1/lab
//	public void testHelloWorld() throws Exception{
////		ClientResponse response=null;
////		WebResource service = client.resource(baseURI).path("lab");				
////		try{		
////			response = service.accept(MediaType.TEXT_PLAIN).get(ClientResponse.class);
////		}catch(Exception e){
////			logger.info(e);
////		}			
////		Assert.assertTrue(200==response.getStatus());	
////		Assert.assertNotNull(response);
////		logger.info(response.getStatus());
////		logger.info(response.getEntity(String.class));
//	}
//	
//	@Test//http://localhost:8080/Lab1/lab/Newbie
//	public void testHello() throws Exception{		
////		ClientResponse response=null;
////		WebResource service = client.resource(baseURI).path("lab").path("Newbie");				
////		try{		
////			response = service.accept(MediaType.TEXT_PLAIN).get(ClientResponse.class);
////		}catch(Exception e){
////			logger.info(e);
////		}			
////		Assert.assertTrue(200==response.getStatus());	
////		Assert.assertNotNull(response);
////		logger.info(response.getStatus());
////		logger.info(response.getEntity(String.class));
//	}
//	
//	@Test
//	public void testMockHelloWorld() throws Exception{
//		String text = "Hello World";				
//		EasyMock.expect(dao.getHW()).andReturn(text);
//		PowerMock.replay(dao);		
//				
//		service.getHelloWorld();
//		PowerMock.replay(service);
//		PowerMock.verify(service);
//	}
//	
//	@Test
//	public void testMockHello() throws Exception{				
//		String text = "Hello";
//		String result = "Hello World";
//		EasyMock.expect(dao.getH(text)).andReturn(result);
//		PowerMock.replay(dao);
//				
//		service.getHello(text);
//		PowerMock.replay(service);
//		PowerMock.verify(service);
//	}
//	
//
//	@Test
//	public void testMockHelloWorldEmpty() throws Exception{
//		String text = null;				
//		EasyMock.expect(dao.getHW()).andReturn(text);
//		PowerMock.replay(dao);		
//				
//		service.getHelloWorld();
//		PowerMock.replay(service);
//		PowerMock.verify(service);
//	}
//	
//	@Test
//	public void testMockHelloEmpty() throws Exception{				
//		String text = "text";
//		String result = null;
//		EasyMock.expect(dao.getH(text)).andReturn(result);
//		PowerMock.replay(dao);
//				
//		service.getHello(text);
//		PowerMock.replay(service);
//		PowerMock.verify(service);
//	}
//	
//	@Test
//	public void testMockHelloWorldException() throws Exception{		
//		EasyMock.expect(dao.getHW()).andThrow(new BaseWebException());
//		PowerMock.replay(dao);		
//				
//		service.getHelloWorld();
//		PowerMock.replay(service);
//		PowerMock.verify(service);
//	}
//	
//	@Test
//	public void testMockHelloException() throws Exception{				
//		String text = "text";		
//		EasyMock.expect(dao.getH(text)).andThrow(new BaseWebException());
//		PowerMock.replay(dao);
//				
//		service.getHello(text);
//		PowerMock.replay(service);
//		PowerMock.verify(service);
//	}
//}
