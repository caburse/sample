//package com.healthtrust.rhalf;
//
//import junit.framework.Assert;
//
//import org.junit.Test;
//
//import com.healthtrust.rhalf.dao.LabDAO;
//import com.healthtrust.rhalf.dao.LabDAOImpl;
//
//public class TestLabDAO {
//
//	LabDAO dao = null;
//	
//	public TestLabDAO(){		
//		dao = new LabDAOImpl();
//	}
//	
//	@Test
//	public void testHW() throws Exception{	
//		String greeting = dao.getHW();
//		Assert.assertEquals("Whatup!", greeting);		
//	}
//	
//	@Test
//	public void testH() throws Exception{
//		String text = "Newbie";		
//		String greeting = dao.getH(text);
//		Assert.assertEquals("Whatup "+text+"!", greeting);		
//	}	
//}
