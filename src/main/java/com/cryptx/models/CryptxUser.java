package com.cryptx.models;

public class CryptxUser {

	private int userId;
	private String name;
	private String email;
	private String phone;
	private String password;
	private String ssn;
	private String address;
	private String city;
	private String country;
	private String postalCode;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSsn() {
		return ssn;
	}

	public void setSsn(String ssn) {
		this.ssn = ssn;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	@Override
	public String toString() {
		return String.format(
				"User Id: [%s], Name : [%s], email :[%s], phone:[%s], password:[%s], ssn:[%s], address:[%s], city:[%s], country:[%s], postalcode:[%s]",
				this.getUserId(), this.getName(), this.getEmail(), this.getPhone(), this.getPassword(), this.getSsn(), this.getAddress(),
				this.getCity(), this.getCountry(), this.getPostalCode());
	}

	public static CryptxUser getDummyUserData() {
		CryptxUser dummyUser = new CryptxUser();
		dummyUser.setUserId(1);
		dummyUser.setName("Vineet Ahirkar");
		dummyUser.setEmail("vineeta1@umbc.edu");
		dummyUser.setPhone("1234567852");
		dummyUser.setSsn("555-88-9999");
		dummyUser.setAddress("4754 Belwood Green");
		dummyUser.setCity("Baltimore");
		dummyUser.setCountry("US");
		dummyUser.setPostalCode("21227");
		return dummyUser;
	}
}
