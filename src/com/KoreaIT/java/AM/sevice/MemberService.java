package com.KoreaIT.java.AM.sevice;

import com.KoreaIT.java.AM.container.Container;
import com.KoreaIT.java.AM.dao.ArticleDao;
import com.KoreaIT.java.AM.dao.MemberDao;
import com.KoreaIT.java.AM.dto.Article;

public class MemberService {
	MemberDao memberDao;

	public MemberService() {
		memberDao = Container.memberDao;
	}

}
