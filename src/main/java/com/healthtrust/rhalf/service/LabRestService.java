package com.healthtrust.rhalf.service;

import java.net.HttpURLConnection;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.healthtrust.rhalf.constants.Constants;
import com.healthtrust.rhalf.dao.LabDAO;
import com.healthtrust.rhalf.exception.BaseWebException;
import com.healthtrust.rhalf.resources.BaseWebResponse;
import com.healthtrust.rhalf.util.PropertyUtil;

// Anchor for Spring to find classes containing Dependency Injection(DI)
@Named
// Defines the class url after context root (we've added nothing)
//Ex. http://localhost:8080/Lab1/<-- We are here
@Path("/")
public class LabRestService {
	
	// Denotes the following object will be injected via Spring DI
	@Inject	
	// The data access object should be added as private for proper encapsulation
    // also we add the interface incase the implementation changes we don't change the service class.
	private LabDAO labDAO;

	//Logger is used every where. Notice how it's instantiated for each class.
	private static Logger logger = Logger.getLogger(LabRestService.class);

	 //Default constructor is needed for DI because we're adding a constructor 
    //that takes an argument, as well sonar likes it...
	public LabRestService() {
		PropertyUtil.getInstance();
	}

	// The constructor that takes an argument is needed for testing.
	public LabRestService(LabDAO labDAO) {
		this.labDAO = labDAO;
		PropertyUtil.getInstance();
	}
	
	/**
	 * Hello World Operation returns Hello World Text. If error encountered
	 * durring processing it returns error in application/xml format.
	 * 
	 * @request.representation.200.mediaType N/A
	 * @response.representation.200.mediaType Plain Text 
	 * @response.representation.200.doc Returns Plain Text.
	 * 
	 * @response.representation.500.mediaType application/xml
	 * @response.representation.500.doc Generic unrecoverable error. Returns
	 *                                  Error xml: <error>description of
	 *                                  error.</error>
	 * @return
	 */
	// Defines the operation url after the class Ex. http://localhost:8080/Lab1/lab
	@Path("/lab")
	// CRUD specs state we need Create(PUT) Read(GET) Update(POST) Delete(DELETE) operation (symantics)
	@GET
	@Produces({MediaType.TEXT_PLAIN, MediaType.APPLICATION_XML})
	public final Response getHelloWorld() {
		Response resp = null;
		logger.info(this.getClass().getName() + "getHW");
		
		try {
			long beginConnect = System.currentTimeMillis();
			// Retrieve data using DAO
			String text = labDAO.getHW();
			long endConnect = System.currentTimeMillis();
			// Log time
			logger.info("Service Time: " + (endConnect - beginConnect)/ Constants.ONE_SEC + "s");
			if (null == text) {
				logger.error("No response Hello World");
				// In this case we have an issue as there should always be data return response with some details(XML)
				return Response.status(HttpURLConnection.HTTP_INTERNAL_ERROR).entity("Unkown Error!").build();
			}			
			resp = Response.ok().entity(text).header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN).build();
		} catch (Exception e) {
			logger.error("Exception encountered:" + e.getMessage());
			resp = Response.status(HttpURLConnection.HTTP_INTERNAL_ERROR).entity(new BaseWebResponse<Object>(new BaseWebException(e))).build();
		}
		logger.debug(this.getClass().getName() + "getHW");
		return resp;
	}
	
	/**
	 * Hello Operation takes text from url as input and returns Hello World Text. If error encountered
	 * durring processing it returns error in application/xml format.
	 * 
	 * @request.representation.200.mediaType Plain Text 
	 * @response.representation.200.mediaType Plain Text 
	 * @response.representation.200.doc Returns Plain Text.
	 * 
	 * @response.representation.500.mediaType application/xml
	 * @response.representation.500.doc Generic unrecoverable error. Returns
	 *                                  Error xml: <error>description of
	 *                                  error.</error>
	 * @return
	 */
	@Path("/lab/{name: [a-zA-Z(%20)]+}")
	@GET
	@Produces({MediaType.TEXT_PLAIN, MediaType.APPLICATION_XML})
	public final Response getHello(@PathParam("name") String name) {
		Response resp = null;
		logger.info(this.getClass().getName() + "getHello");
		try {
			//Timing
			long beginConnect = System.currentTimeMillis();
			//DAO
			String text = labDAO.getH(name);
			long endConnect = System.currentTimeMillis();
			logger.info("Service Time: " + (endConnect - beginConnect)/ Constants.ONE_SEC + "s");
			if (null == text) {
				logger.error("No response Hello World");
				return Response.status(HttpURLConnection.HTTP_INTERNAL_ERROR).entity("Service not available!").build();
			}
			//Setting response element.			
			resp = Response.ok().entity(text).header(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_PLAIN).build();
		} catch (Exception e) {
			//Exception Handling
			logger.error("Exception encountered:" + e.getMessage());
			resp = Response.status(HttpURLConnection.HTTP_INTERNAL_ERROR).entity(new BaseWebResponse<Object>(new BaseWebException(e))).build();
		}
		logger.debug(this.getClass().getName() + "getHello");
		return resp;
	}

}