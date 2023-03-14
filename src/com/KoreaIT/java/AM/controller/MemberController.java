package com.KoreaIT.java.AM.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.KoreaIT.java.AM.dto.Article;
import com.KoreaIT.java.AM.dto.Member;
import com.KoreaIT.java.AM.util.Util;

public class MemberController extends Controller {
	private List<Member> members;
	private Scanner sc;
	private String command;
	private String actionMethodName;

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
//		case "login":
//			login();
//			break;
//		case "logout":
//			logout();
//			break;
		default:
			System.out.println("해당 기능은 사용할 수 없습니다");
			break;
		}
	}

	int lastMemberId = 3;

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

		Member member = new Member(userId, userPassword, name);
		members.add(member);
		System.out.println(id + "번 회원이 가입되었습니다");
		lastMemberId++;

	}

	/** 로그인 아이디 중복검사 */
	public boolean isJoinalbeUserID(String userID) {
		int index = getMemberByUserID(userID);
		if (index == -1) {
			return true; // 일치하는 값이 없었다 > 그러니 사용 가능하다
		}
		return false; // 일치하는 값이 있었다 > 중복된다
	}

	/** 데이터 내 id 찾기 */
	public int getMemberByUserID(String userID) {
		int i = 0;
		for (Member member : members) {
			if (member.userID.equals(userID)) { // equals와 ==의 차이
				return i;
			}
			i++;
		}
		return -1;
	}

//	public void login() {
//		System.out.println("==  Login  ==");
//		while (true) {
//			System.out.print("아이디 : ");
//			String loginId = sc.nextLine();			
//			int index = getMemberByUserID(loginId);
//			Member foundMember = members.get(index);
//			if(loginId.equals(foundMember.userID) == false) {
//				System.out.println("가입되지 않은 아이디입니다");
//				continue;
//			}
//			System.out.print("비밀번호 : ");
//			String loginPw = sc.nextLine();
//			if(loginPw.equals(foundMember.userPassword) == false) {
//				System.out.println("비밀번호가 일치하지 않습니다");
//				continue;
//			}
//			break;
//		}
//		System.out.println("로그인되었습니다");
//
//	}
//
//	public void logout() {
//
//	}

	public void makeTestDataMember() {
		System.out.println("테스트를 위한 데이터를 생성합니다");
		members.add(new Member("아이디 1", "0000", "kwonju"));
		members.add(new Member("아이디 2", "1111", "kwonju"));
		members.add(new Member("아이디 3", "2222", "kwonju"));
	}
}