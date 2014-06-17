package com.healthtrust.rhalf;

import org.junit.Assert;
import org.junit.Test;

import com.healthtrust.rhalf.constants.PropertiesConstants;
import com.healthtrust.rhalf.util.PropertyUtil;

public class TestPropertyUtil {
		
	@Test
	public void testgetPropertyValue(){		
		String level = PropertyUtil.getInstance().getString(PropertiesConstants.LOG_LEVEL);		
		Assert.assertNotNull(level);
	}
	
	@Test
	public void testLoadURL(){		
		PropertyUtil resource = PropertyUtil.getInstance();
		Assert.assertNotNull(resource);	
	}			
}
