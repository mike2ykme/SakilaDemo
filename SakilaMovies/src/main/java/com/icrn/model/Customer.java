package com.icrn.model;

public class Customer {

	private int id;
	private String firstName;
	private String lastName;
	private String email;
	private String streetAddress;
	private String city;
	private int postalCode;
	private boolean status;
	
	public Customer(){
		super();
	}
	public Customer(int id){
		this.id = id;
	}
	public Customer(int id, String firstName, String lastName) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	public Customer(int id, String firstName, String lastName,String email, String streetAddress, String city, int postalCode, boolean status) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.streetAddress = streetAddress;
		this.city = city;
		this.postalCode = postalCode;
		this.status = status;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getAddress() {
		return streetAddress;
	}
	public boolean isStatus() {
		return status;
	}
	public void setAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public String getStreetAddress() {
		return streetAddress;
	}
	public String getCity() {
		return city;
	}
	public int getPostalCode() {
		return postalCode;
	}
	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public void setPostalCode(int postalCode) {
		this.postalCode = postalCode;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	@Override
	public String toString() {
		return "Customer [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ ", streetAddress=" + streetAddress + ", city=" + city + ", postalCode=" + postalCode + ", status="
				+ status + "]";
	}

}
