package com.healthtrust.rhalf.resources;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.healthtrust.rhalf.exception.BaseWebError;
import com.healthtrust.rhalf.exception.BaseWebException;

/**
 * Generic wrapper class for com.walmart.membership.order.service responses. Along with the resource,
 * this class maintains lists of errors and hypermedia links.
 * 
 * @author mc0oraw
 *
 * @param <T> The type of the requested entity/resource.
 */
@XmlRootElement(name="baseWebResponse")
public class BaseWebResponse<T> {
	
	/**
	 * A list of errors.
	 */
	private List<Error> errors = new ArrayList<Error>();
	
	/**
	 * Creates an empty response.
	 */
	public BaseWebResponse() {
		super();
	}
	
	/**
	 * Creates a response containing an error.
	 * @param err the error.
	 */
	public BaseWebResponse(Error err) {
		this.errors.add(err);
	}
	
	/**
	 * Creates a response containing an com.walmart.membership.order.exception.
	 * @param ex the com.walmart.membership.order.exception.
	 */
	public BaseWebResponse(BaseWebException ex) {
		this.errors.add(new BaseWebError(ex));
	}
	
	/**
	 * Sets the error list of the response.
	 * @param errors a list containing errors.
	 */
	final public void setErrors(List<Error> errors) {
		this.errors = errors;
	}
	
	/**
	 * Gets the list of errors associated with this response.
	 * @return a list of errors.
	 */
	@XmlElementWrapper(name = "errors")
	@XmlElement(name = "error")
	final public List<Error> getErrors() {
		return errors;
	}	
}