package com.healthtrust.rhalf.resources;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="patient")
@Entity(name="patient")
public class Patient {

	@Id
	private String id;
	@Column
	private String fname;
	@Column
	private String lname;
	@Column
	private String position;
	
	public Patient(){
	}
	
	public Patient(String id, String fname, String lname, String position){
		this.id=id;
		this.fname=fname;
		this.lname=lname;
		this.position=position;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getLname() {
		return lname;
	}
	public void setLname(String lname) {
		this.lname = lname;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
}
