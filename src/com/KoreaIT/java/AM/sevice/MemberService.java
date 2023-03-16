package com.KoreaIT.java.AM.sevice;

import com.KoreaIT.java.AM.container.Container;
import com.KoreaIT.java.AM.dao.ArticleDao;
import com.KoreaIT.java.AM.dao.MemberDao;
import com.KoreaIT.java.AM.dto.Article;
import com.KoreaIT.java.AM.dto.Member;

public class MemberService {
	MemberDao memberDao;

	public MemberService() {
		memberDao = Container.memberDao;
	}

	public int setNewId() {
		return memberDao.setNewId();
	}

	public int getLastId() {
		return memberDao.getLastId();
	}

	public void add(Member member) {
		memberDao.add(member);
	}

	public boolean isJoinalbeUserID(String loginId) {
		return memberDao.isJoinalbeUserID(loginId);
	}

	public Member getMemberByLoginId(String loginId) {
		return memberDao.getMemberByLoginId(loginId);
	}

	public int getMemberByUserId(String loginId) {
		return memberDao.getMemberByUserId(loginId);
	}
}
