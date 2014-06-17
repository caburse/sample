package com.healthtrust.rhalf.exception;

public final class BaseException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3676267343786622864L;
	private String code;
	private String message;
	
	public BaseException(){
		super();
	}
	
	public BaseException(String message){
		super(message);
		this.message=message;
	}
	public BaseException(Exception e){
		super(e);
	}
	public BaseException(String message, Throwable t){
		super(message,t);
		this.message=message;
	}
	public BaseException(String code, String message){
		super(message);
		this.code=code;
		this.message=message;
	}
	
	public final String getMessage() {
		return message;
	}
	public final void setMessage(String message) {
		this.message = message;
	}
	public final String getCode(){
		return code;
	}
	public final void setCode(String code){
		this.code = code;
	}
	
}
