package com.healthtrust.rhalf.dao;

import com.healthtrust.rhalf.exception.BaseWebException;


public interface LabDAO {	
	String getHW() throws BaseWebException;
	String getH(String name) throws BaseWebException;
}
