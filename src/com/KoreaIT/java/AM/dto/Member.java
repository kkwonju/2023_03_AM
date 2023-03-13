package com.KoreaIT.java.AM.dto;

public class Member {
	public int id;
	public String userID;
	public String userPassword;
	public String userName;

	public Member(String userID, String userPassword, String userName) {
		this.userID = userID;
		this.userPassword = userPassword;
		this.userName = userName;
	}
}