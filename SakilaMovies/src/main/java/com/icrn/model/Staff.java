package com.icrn.model;

public class Staff {
	private int staffId;
	private String username;
	private String firstName;
	private String lastName;
	private int storeId;
	private boolean active;
	private String password;
	private String phone;
	private String address;
	private String postalCode;
	private String district;
	public Staff(int staffId) {
		this.staffId = staffId;
	}
	public Staff(int staffId, String username, String firstName, String lastName, int storeId, boolean active,
			String password, String phone, String address, String postalCode, String district, String city,
			String country) {
		this.staffId = staffId;
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.storeId = storeId;
		this.active = active;
		this.password = password;
		this.phone = phone;
		this.address = address;
		this.postalCode = postalCode;
		this.district = district;
		this.city = city;
		Country = country;
	}
	private String city;
	private String Country;
	public int getStaffId() {
		return staffId;
	}
	public String getUsername() {
		return username;
	}
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public int getStoreId() {
		return storeId;
	}
	public boolean getActive() {
		return active;
	}
	public String getPassword() {
		return password;
	}
	public String getPhone() {
		return phone;
	}
	public String getAddress() {
		return address;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public String getDistrict() {
		return district;
	}
	public String getCity() {
		return city;
	}
	public String getCountry() {
		return Country;
	}
//	public void setStaffId(long staffId) {
//		this.staffId = staffId;
//	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public void setCountry(String country) {
		Country = country;
	}
	@Override
	public String toString() {
		return "Staff [staffId=" + staffId + ", username=" + username + ", firstName=" + firstName + ", lastName="
				+ lastName + ", storeId=" + storeId + ", active=" + active + ", password=" + password + ", phone="
				+ phone + ", address=" + address + ", postalCode=" + postalCode + ", district=" + district + ", city="
				+ city + ", Country=" + Country + "]";
	}
	
	
}

//staff_id, first_name, last_name, store_id, active, username, password, phone, address, postal_code, district, city, country