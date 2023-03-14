package com.KoreaIT.java.AM.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.KoreaIT.java.AM.dto.Article;
import com.KoreaIT.java.AM.dto.Member;
import com.KoreaIT.java.AM.util.Util;

public class MemberController extends Controller {
	public static List<Member> members;
	private Scanner sc;
	private String command;
	private String actionMethodName;

	int lastMemberId = 3;

	public MemberController(Scanner sc) {
		this.members = new ArrayList<>();
		this.sc = sc;
	}

	public void doAction(String actionMethodName, String command) {
		this.command = command;
		this.actionMethodName = actionMethodName;

		switch (actionMethodName) {
		case "join":
			dojoin();
			break;
		case "login":
			doLogin();
			break;
		case "logout":
			doLogout();
			break;
		case "profile":
			showProfile();
			break;
		default:
			System.out.println("해당 기능을 찾을 수 없습니다");
			break;
		}
	}

	/** 회원정보 출력 */
	private void showProfile() {
		System.out.println("== 현재 로그인 된 회원의 정보 ==");
		System.out.println("회원 id값 : " + loginedMember.id);
		System.out.printf("로그인 아이디 : %s\n", loginedMember.loginId);
		System.out.printf("이름 : %s\n", loginedMember.userName);
	}

	/** 로그아웃 기능 */
	private void doLogout() {
		loginedMember = null;
		System.out.println("로그아웃되었습니다");
	}

	/** 로그인 기능 */
	private void doLogin() {
		System.out.print("아이디 : ");
		String loginId = sc.nextLine();
		Member member = getMemberByLoginId(loginId);

		if (member == null) {
			System.out.println("일치하는 회원이 없습니다");
			return;
		}
		System.out.print("비밀번호 : ");
		String loginPw = sc.nextLine();
		if (member.loginPw.equals(loginPw) == false) {
			System.out.println("비밀번호가 일치하지 않습니다");
			return;
		}
		loginedMember = member;
		System.out.printf("반갑습니다, %s님!\n", loginedMember.userName);
		/*
		 * 로그인 상태 판단, boolean 또는 int는 추가적인 데이터를 사용할 수 없음
		 */
	}

	/** 회원가입 기능 */
	private void dojoin() {
		int id = lastMemberId + 1;
		String userId = null;
		String userPassword = null;
		String passwordConfirm = null;

		while (true) {
			System.out.print("아이디 : ");
			userId = sc.nextLine();
			if (isJoinalbeUserID(userId) == false) {
				System.out.println("사용 중인 아이디입니다");
				continue; // false == false > 사용 불가능해 == 불가능 > true > 다시 입력
			}
			System.out.println("멋진 아이디입니다");
			break;
		}
		while (true) {
			System.out.print("비밀번호 : ");
			userPassword = sc.nextLine();
			System.out.print("비밀번호 확인 : ");
			passwordConfirm = sc.nextLine();

			if (userPassword.equals(passwordConfirm) == false) {
				System.out.println("비밀번호를 확인해주세요");
				continue;
			}
			break;
		}
		System.out.print("이름 : ");
		String name = sc.nextLine();

		Member member = new Member(id, userId, userPassword, name);
		members.add(member);
		System.out.println(id + "번 회원이 가입되었습니다");
		lastMemberId++;
	}

	/** loginId 찾아 Member 인스턴스 반환 */
	private Member getMemberByLoginId(String loginId) {
		int index = getMemberByUserID(loginId);
		if (index == -1) {
			return null;
		}
		return members.get(index);

	}

	/** 로그인 아이디 중복검사 */
	public boolean isJoinalbeUserID(String loginId) {
		int index = getMemberByUserID(loginId);
		if (index == -1) {
			return true; // 일치하는 값이 없었다 > 그러니 사용 가능하다
		}
		return false; // 일치하는 값이 있었다 > 중복된다
	}

	/** loginId 찾아 index 반환 */
	public int getMemberByUserID(String loginId) {
		int i = 0;
		for (Member member : members) {
			if (member.loginId.equals(loginId)) { // equals와 ==의 차이
				return i;
			}
			i++;
		}
		return -1;
	}

	public void makeTestData() {
		members.add(new Member(1, "test1", "0000", "kkkk"));
		members.add(new Member(2, "test2", "0001", "jjjj"));
		members.add(new Member(3, "test3", "0002", "llll"));
	}
}