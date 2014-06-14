package com.healthtrust.rhalf.constants;

import java.util.HashMap;
import java.util.Map;

public final class Constants {

	private Constants(){}
	
	public static final int T_POOL_SIZE = 1;
	public static final long KEEP_ALIVE = 1500;
	public static final int DEFAULT_ZERO = 0;
	public static final int DEFAULT_ONE = 1;
	public static final int DEFAULT_TWO = 0;
	public static final int DEFAULT_THREE = 3;
	public static final short DEFAULT_STATUS = 100;
	public static final int FOUR = 4;
	public static final String NULL ="null";
	public static final String DELIMITER = "-";
	public static final long ONE_SEC = 1000;	
	public static final String ROOT_DIR = "/";
	
	/**Environment variables**/
	public static final String PROD = "prod";
	public static final String QA = "qa";
	public static final String DEV = "dev";
	public static final String LOCAL = "local";
	public static final String EDC = "edc";
	public static final String NDC  = "ndc";
	public static final String PROPERTY_PREFIX = "lab_";
	public static final String PROPERTY_POSTFIX = ".properties";
	public static final String DEFAULT_PROPERTY = "lab_local.properties";
	
	public static final String LOCAL_HOST = "localhost";
	public static final String SUCCESS = "OK";
	public static final int ERROR_CODE = 900;
	public static final String DOLLAR = "$";
		
	public static final String PROD_PREFIX = "osel";
	public static final String PROD_PREFIX_A = "osei";
	public static final String QA_PREFIX = "cerl";
	public static final String QA_PREFIX_A = "ceri";
	public static final String DEV_PREFIX = "tstl";
	public static final String DEV_PREFIX_A = "tsti";
	public static final String WORKSTATION_PREFIX = "weus";	
	
	public static final Map<String, String> ENVIRONMENT = new HashMap<String, String>();		 
	static {
		ENVIRONMENT.put(PROD_PREFIX, PROD);
		ENVIRONMENT.put(PROD_PREFIX_A, PROD);
		ENVIRONMENT.put(QA_PREFIX, QA);
		ENVIRONMENT.put(QA_PREFIX_A, QA);
		ENVIRONMENT.put(DEV_PREFIX, DEV);
		ENVIRONMENT.put(DEV_PREFIX_A, DEV);
		ENVIRONMENT.put(WORKSTATION_PREFIX, LOCAL);		
	}
}
