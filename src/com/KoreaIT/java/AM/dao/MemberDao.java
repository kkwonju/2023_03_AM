package com.KoreaIT.java.AM.dao;

import java.util.ArrayList;
import java.util.List;

import com.KoreaIT.java.AM.dto.Member;

public class MemberDao extends Dao{
	public List<Member> members;
	

	public MemberDao() {
		members = new ArrayList<>();
	}
	
	public int setNewId() {
		return lastId + 1;
	}
	
	
	public int getLastId() {
		return lastId;
	}
	
	public void add(Member member) {
		members.add(member);
		lastId++;
	}
	
	/** 로그인 아이디 중복검사 */
	public boolean isJoinalbeUserID(String loginId) {
		int index = getMemberByUserId(loginId);
		if (index == -1) {
			return true; // 일치하는 값이 없었다 > 그러니 사용 가능하다
		}
		return false; // 일치하는 값이 있었다 > 중복된다
	}
	
	/** loginId 찾아 Member 인스턴스 반환 */
	public Member getMemberByLoginId(String loginId) {
		int index = getMemberByUserId(loginId);
		if (index == -1) {
			return null;
		}
		return members.get(index);
	}

	/** loginId 찾아 index 반환 */
	public int getMemberByUserId(String loginId) {
		int i = 0;
		for (Member member : members) {
			if (member.loginId.equals(loginId)) { // equals와 ==의 차이
				return i;
			}
			i++;
		}
		return -1;
	}
}
