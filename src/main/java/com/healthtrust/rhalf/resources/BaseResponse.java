package com.healthtrust.rhalf.resources;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="error")
public class BaseResponse {

	private String message;
	private int code;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public BaseResponse() {
	}
	public BaseResponse(String message, int code){
		this.message=message;
		this.code=code;
	}
}
