package com.KoreaIT.java.AM.dto;

public class Member {
	public int id;
	public String loginId;
	public String loginPw;
	public String userName;

	public Member(String loginId, String loginPw, String userName) {
		this.loginId = loginId;
		this.loginPw = loginPw;
		this.userName = userName;
	}
}