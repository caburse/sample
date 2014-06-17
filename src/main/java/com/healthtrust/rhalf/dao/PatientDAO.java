package com.healthtrust.rhalf.dao;




public interface PatientDAO {
	public Object create(Object obj);	
	public <E> E read(Class<E> entityClass, Object primaryKey);	
	public Object update(Object obj);
	public <E> void delete(Object obj);	
}
