package com.door.auth.data.payload.request;

public class LoginRequest {
	
	public String password;
	public String email;
	
	public LoginRequest() {}

	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
