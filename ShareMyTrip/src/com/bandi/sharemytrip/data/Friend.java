package com.bandi.sharemytrip.data;

public class Friend 
{
	private String id; /* Friend Name */
	private String number;
	private int contactId;
	private String address;
	private String email;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public int getContactId() {
		return contactId;
	}
	public void setContactId(int contactId) {
		this.contactId = contactId;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	// Will be used by the ArrayAdapter in the ListView
	@Override
	public String toString() {
		return id;
	}

	public String getName() {
		return getId();
	}

	public void setName(String tripName) {
		setId(tripName);
	}
}
