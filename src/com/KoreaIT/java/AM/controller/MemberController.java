package com.KoreaIT.java.AM.controller;

import java.util.List;
import java.util.Scanner;

import com.KoreaIT.java.AM.dto.Member;

public class MemberController extends Controller {
	private List<Member> members;
	private Scanner sc;
	private String command;
	private String actionMethodName;

	public MemberController(List<Member> members, Scanner sc) {
		this.members = members;
		this.sc = sc;
	}

	public void doAction(String actionMethodName, String command) {
		this.command = command;
		this.actionMethodName = actionMethodName;
		
		switch(actionMethodName) {
		case "join":
			dojoin();
			break;
		default:
			System.out.println("명령어를 확인해주세요");
			break;
		}
	}

	int lastMemberId = 0;

	public void dojoin() {
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

	public boolean isJoinalbeUserID(String userID) {
		int index = getMemberByUserID(userID);
		if (index == -1) {
			return true; // 일치하는 값이 없었다 > 그러니 사용 가능하다
		}
		return false; // 일치하는 값이 있었다 > 중복된다
	}

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
}