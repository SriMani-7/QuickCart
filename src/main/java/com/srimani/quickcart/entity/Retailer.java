package com.srimani.quickcart.entity;

public class Retailer {
	private long userId;
	private String name;
	private String phoneNumber;
	private String contactEMail;
	private String address;

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getContactEMail() {
		return contactEMail;
	}

	public void setContactEMail(String contactEMail) {
		this.contactEMail = contactEMail;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
