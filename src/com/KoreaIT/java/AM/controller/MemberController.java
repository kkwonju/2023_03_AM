package com.KoreaIT.java.AM.controller;

import java.util.List;
import java.util.Scanner;

import com.KoreaIT.java.AM.container.Container;
import com.KoreaIT.java.AM.dto.Member;

public class MemberController extends Controller {
	private static List<Member> members;
	private Scanner sc;
	private String command;
	private String actionMethodName;

	public MemberController(Scanner sc) {
		this.members = Container.memberDao.members; // 저장 위치
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
		System.out.printf("회원 번호 : %d\n", loginedMember.id);
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
		Member member = null;
		String loginId = null;
		String loginPw = null;

		while (true) {
			System.out.print("아이디 : ");
			loginId = sc.nextLine().trim();
			if (loginId.length() == 0) {
				System.out.println("아이디를 입력해주세요");
				continue;
			}
			break;
		}
		while (true) {
			System.out.print("비밀번호 : ");
			loginPw = sc.nextLine().trim();
			if (loginPw.length() == 0) {
				System.out.println("비밀번호를 입력해주세요");
				continue;
			}
			break;
		}
		member = getMemberByLoginId(loginId);

		if (member == null) {
			System.out.println("일치하는 회원이 없습니다");
			return;
		}
		if (member.loginPw.equals(loginPw) == false) {
			System.out.println("비밀번호가 일치하지 않습니다");
			return;
		}
		loginedMember = member;
		System.out.printf("반갑습니다, %s님!\n", loginedMember.userName);
	}

	/** 회원가입 기능 */
	private void dojoin() {
		int id = Container.memberDao.setNewId();
		String loginId = null;
		String loginPw = null;
		String loginPwConfirm = null;

		while (true) {
			System.out.print("아이디 : ");
			loginId = sc.nextLine().trim();
			if (loginId.length() == 0) {
				System.out.println("필수정보입니다");
				continue;
			} else if (isJoinalbeUserID(loginId) == false) {
				System.out.println("사용 중인 아이디입니다");
				continue; // false == false > 사용 불가능해 == 불가능 > true > 다시 입력
			}
			System.out.println("멋진 아이디입니다");
			break;
		}
		while (true) {
			System.out.print("비밀번호 : ");
			loginPw = sc.nextLine().trim();
			if (loginPw.length() == 0) {
				System.out.println("필수정보입니다");
				continue;
			}
			System.out.print("비밀번호 확인 : ");
			loginPwConfirm = sc.nextLine();
			if (loginPw.equals(loginPwConfirm) == false) {
				System.out.println("비밀번호를 확인해주세요");
				continue;
			}
			break;
		}
		System.out.print("이름 : ");
		String name = sc.nextLine();

		Member member = new Member(id, loginId, loginPw, name);
		Container.memberDao.add(member);
		System.out.println(id + "번 회원이 가입되었습니다");
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
		Container.memberDao.add(new Member(1, "test1", "0000", "kkkk"));
		Container.memberDao.add(new Member(2, "test2", "0001", "jjjj"));
		Container.memberDao.add(new Member(3, "test3", "0002", "llll"));
	}
}