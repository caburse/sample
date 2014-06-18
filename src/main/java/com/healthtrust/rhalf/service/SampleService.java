package com.healthtrust.rhalf.service;

import java.net.HttpURLConnection;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.healthtrust.rhalf.constants.Constants;
import com.healthtrust.rhalf.dao.PatientDAO;
import com.healthtrust.rhalf.exception.BaseException;
import com.healthtrust.rhalf.resources.BaseResponse;
import com.healthtrust.rhalf.resources.Patient;
import com.healthtrust.rhalf.util.PropertyUtil;

// Anchor for Spring to find classes containing Dependency Injection(DI)
@Named
// Defines the class url after context root (we've added nothing)
//Ex. http://localhost:8080/ht-rhalf/<-- We are here
@Path("/")
public class SampleService {
	
	// Denotes the following object will be injected via Spring DI
	@Inject	
	// The data access object should be added as private for proper encapsulation
    // also we add the interface incase the implementation changes we don't change the service class.
	private PatientDAO patientDAO;

	//Logger is used every where. Notice how it's instantiated for each class.
	private static final Logger LOGGER = Logger.getLogger(SampleService.class);

	 //Default constructor is needed for DI because we're adding a constructor 
    //that takes an argument, as well sonar likes it...
	public SampleService() {
		PropertyUtil.getInstance();
	}

	// The constructor that takes an argument is needed for testing.
	protected SampleService(PatientDAO labDAO) {
		this.patientDAO = labDAO;
	}
		
	/**
	 * 
	 * @param id
	 * @return
	 */
	@Path("/get/{id: .+}")//: "+Constants.EMAIL_REGEX+" }")
	@GET
	 
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public final Response get(@PathParam("id") String id) {
		Response response = null;
		try {
			if(!id.matches(Constants.EMAIL_REGEX)){
				LOGGER.error("Invalid Id! " + id);
				throw new BaseException("No information found!");
			}
			
			//Timing
			long beginConnect = System.currentTimeMillis();
			//DAO
			Patient patient = patientDAO.read(Patient.class, id);
			long endConnect = System.currentTimeMillis();
			LOGGER.info("Service Time: " + (endConnect - beginConnect)/ Constants.ONE_SEC + "s");
			if (null == patient) {
				throw new BaseException("No information found!");
			}
			//Setting response element.			
			response = Response.ok().entity(patient).build();
		}catch(BaseException pe){
			//Exception Handling
			LOGGER.error("No Data Found " +pe);
			response = Response.status(HttpURLConnection.HTTP_NO_CONTENT).entity(new BaseResponse(pe.getMessage(), HttpURLConnection.HTTP_NO_CONTENT)).build();
		} catch (Exception e) {
			//Exception Handling
			LOGGER.error("Exception encountered:" + e);
			response = Response.status(HttpURLConnection.HTTP_INTERNAL_ERROR).entity(new BaseResponse(e.getMessage(), HttpURLConnection.HTTP_INTERNAL_ERROR)).build();
		}
		return response;
	}
	
	/**
	 * 
	 * @param patient
	 * @return
	 */
	@Path("/put")//: "+Constants.EMAIL_REGEX+" }")
	@PUT
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public final Response put(Patient request) {
		Response response = null;
		try {			
			//Timing
			long beginConnect = System.currentTimeMillis();
			//DAO
			Patient patient = (Patient)patientDAO.update(request);
			long endConnect = System.currentTimeMillis();
			LOGGER.info("Service Time: " + (endConnect - beginConnect)/ Constants.ONE_SEC + "s");
			if (null == patient) {
				throw new BaseException("Insert Failed!");
			}
			//Setting response element.			
			response = Response.ok().entity(patient).build();
		}catch(BaseException pe){
			//Exception Handling
			LOGGER.error("Exception encountered:" + pe);
			response = Response.status(HttpURLConnection.HTTP_INTERNAL_ERROR).entity(new BaseResponse(pe.getMessage(), HttpURLConnection.HTTP_INTERNAL_ERROR)).build();
		} catch (Exception e) {
			//Exception Handling
			LOGGER.error("Exception encountered:" + e);
			response = Response.status(HttpURLConnection.HTTP_INTERNAL_ERROR).entity(new BaseResponse(e.getMessage(), HttpURLConnection.HTTP_INTERNAL_ERROR)).build();
		}
		LOGGER.debug(this.getClass().getName() + "getHello");
		return response;
	}
	
	/**
	 * 
	 * @param patient
	 * @return
	 */
	@Path("/post")//: "+Constants.EMAIL_REGEX+" }")
	@POST
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public final Response post(Patient request) {
		Response response = null;
		try {			
			//Timing
			long beginConnect = System.currentTimeMillis();
			//DAO
			Patient patient = (Patient)patientDAO.create(request);
			long endConnect = System.currentTimeMillis();
			LOGGER.info("Service Time: " + (endConnect - beginConnect)/ Constants.ONE_SEC + "s");
			if (null == patient) {
				throw new BaseException("Update Failed!");
			}
			//Setting response element.			
			response = Response.status(HttpURLConnection.HTTP_ACCEPTED).entity(patient).build();
		}catch(BaseException pe){
			//Exception Handling
			LOGGER.error("Exception encountered:" + pe);
			response = Response.status(HttpURLConnection.HTTP_INTERNAL_ERROR).entity(new BaseResponse(pe.getMessage(), HttpURLConnection.HTTP_INTERNAL_ERROR)).build();
		} catch (Exception e) {
			//Exception Handling
			LOGGER.error("Exception encountered:" + e);
			response = Response.status(HttpURLConnection.HTTP_INTERNAL_ERROR).entity(new BaseResponse(e.getMessage(), HttpURLConnection.HTTP_INTERNAL_ERROR)).build();
		}
		LOGGER.debug(this.getClass().getName() + "getHello");
		return response;
	}
	
	/**
	 * 
	 * @param patient
	 * @return
	 */
	@Path("/delete")//: "+Constants.EMAIL_REGEX+" }")
	@DELETE
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public final Response delete(Patient request) {
		Response response = null;
		try {			
			//Timing
			long beginConnect = System.currentTimeMillis();
			//DAO
			patientDAO.delete(request);
			long endConnect = System.currentTimeMillis();
			LOGGER.info("Service Time: " + (endConnect - beginConnect)/ Constants.ONE_SEC + "s");
			
			//Setting response element.			
			response = Response.status(HttpURLConnection.HTTP_ACCEPTED).entity(new BaseResponse(Constants.SUCCESS,HttpURLConnection.HTTP_ACCEPTED)).build();
		} catch (Exception e) {
			//Exception Handling
			LOGGER.error("Exception encountered:" + e);
			response = Response.status(HttpURLConnection.HTTP_INTERNAL_ERROR).entity(new BaseResponse(e.getMessage(), HttpURLConnection.HTTP_INTERNAL_ERROR)).build();
		}
		LOGGER.debug(this.getClass().getName() + "getHello");
		return response;
	}
}