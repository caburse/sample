package com.healthtrust.rhalf.exception;

public class BaseWebError extends Error {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8582112091511776188L;

	public BaseWebError(Exception e){
		super(e);
	}	
}
