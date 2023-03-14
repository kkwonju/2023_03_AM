package com.KoreaIT.java.AM.controller;

import com.KoreaIT.java.AM.dto.Member;

public abstract class Controller {

	protected static Member loginedMember = null;

	/** 로그인 상태 확인 */
	public static boolean isLogined() {
		return loginedMember != null;
	}

	public abstract void doAction(String actionMethodName, String command);

	public void makeTestData() {
	}
}
