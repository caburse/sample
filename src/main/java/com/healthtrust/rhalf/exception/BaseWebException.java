package com.healthtrust.rhalf.exception;

public class BaseWebException extends Exception{

		private static final long serialVersionUID = 1L;
		
		private String errorCode;
		
		private String errorMessage;
		
		
		/**
		 * @return the code
		 */
		public final String getErrorCode() {
			return errorCode;
		}

		/**
		 * @param code the code to set
		 */
		public final void setErrorCode(String code) {
			this.errorCode = code;
		}

		/**
		 * @return the message
		 */
		public final String getErrorMessage() {
			return errorMessage;
		}

		/**
		 * @param message the message to set
		 */
		public final void setErrorMessage(String message) {
			this.errorMessage = message;
		}
		
		public BaseWebException(){
			super();
		}
		
		
		public BaseWebException(String cd, String msg){
			super();
			errorCode = cd;
			errorMessage = msg;
		}
		
		public  BaseWebException(String message, Throwable error){
			super(message, error);
		}
		public  BaseWebException(String message){
			super(message);
		}
		
		public  BaseWebException(Exception e){
			super(e);
		}
}
