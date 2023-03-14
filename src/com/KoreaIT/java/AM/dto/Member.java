package com.KoreaIT.java.AM.dto;

public class Member extends Dto{
	public String loginId;
	public String loginPw;
	public String userName;

	public Member(int id,String loginId, String loginPw, String userName) {
		this.id = id;
		this.loginId = loginId;
		this.loginPw = loginPw;
		this.userName = userName;
	}
}