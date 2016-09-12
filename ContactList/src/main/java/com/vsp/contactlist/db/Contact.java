package com.vsp.contactlist.db;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Contact implements Serializable 
{
	@Id	
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id 		= 0L;
	private String name 	= null;
	private String address 	= null;
	private String phone 	= null;
	private String comments = null;
	
	public Contact(){}
	
	public Contact(String name, String address, String phone, String comments) {
		super();
		this.name = name;
		this.address = address;
		this.phone = phone;
		this.comments = comments;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	
	public String toString()
	{
		return "{ name: " + this.name + ", address : " + this.address + ", phone : " + this.phone + ", comments : " + this.comments + " }";
	}

}
