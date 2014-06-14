package com.healthtrust.rhalf.dao;

import com.healthtrust.rhalf.exception.BaseWebException;


public class LabDAOImpl  implements LabDAO{
	
	public LabDAOImpl() {}
	
	/**
	 * Returns Whatup
	 */
	@Override
	public String getHW() throws BaseWebException{
		return "Whatup!";		
	}
	
	/**
	 * Returns Whatup + [string]
	 */
	@Override
	public String getH(String name) throws BaseWebException{
		return "Whatup "+name+"!";		
	}
}