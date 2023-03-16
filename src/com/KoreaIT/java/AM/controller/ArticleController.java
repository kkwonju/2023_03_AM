package com.KoreaIT.java.AM.controller;

import java.util.List;
import java.util.Scanner;

import com.KoreaIT.java.AM.container.Container;
import com.KoreaIT.java.AM.dto.Article;
import com.KoreaIT.java.AM.dto.Member;
import com.KoreaIT.java.AM.sevice.ArticleService;
import com.KoreaIT.java.AM.sevice.MemberService;
import com.KoreaIT.java.AM.util.Util;

public class ArticleController extends Controller {
	private List<Article> articles;
	private Scanner sc;
	private String command;
	private String actionMethodName;
	private ArticleService articleService;

	public ArticleController(Scanner sc) {
		this.sc = sc;
		this.articleService = Container.articleService;
	}

	public void doAction(String actionMethodName, String command) {
		this.command = command;
		this.actionMethodName = actionMethodName;

		switch (actionMethodName) {
		case "write":
			doWrite();
			break;
		case "list":
			showList();
			break;
		case "detail":
			showDetail();
			break;
		case "modify":
			doModify();
			break;
		case "delete":
			doDelete();
			break;
		default:
			System.out.println("해당 기능은 사용할 수 없습니다");
			break;
		}
	}
	
	/** 게시글 작성 */
	private void doWrite() {
		int id = articleService.setNewId();
		System.out.print("제목 : ");
		String regDate = Util.getNowDateTimeStr();
		String title = sc.nextLine();
		System.out.print("내용 : ");
		String content = sc.nextLine();
		
		Article article = new Article(id, regDate, regDate, loginedMember.id, title, content);
		/* add는 DB입장에서 요소가 추가되는 것, controller에서 건드리면 안되는 부분 */
		articleService.add(article);

		System.out.printf("%d번 글이 생성되었습니다\n", id);
	}

	/** 게시글 검색(목록) */
	public void showList() {
		String searchKeyword = command.substring("article list".length()).trim();
		/* 데이터에 접근해야 할 때, service에 요청, 기능 구현 */
		List<Article> forPrintArticles = articleService.getForPrintArticles(searchKeyword);

		if (forPrintArticles.size() == 0) {
			System.out.println("게시글이 없습니다");
			return;
		}
		

		System.out.println(" 번호 / 제목 / 조회 수 / 작성자");
		for (int i = forPrintArticles.size() - 1; i >= 0; i--) {
			
			String writerName = null;
			Article article = forPrintArticles.get(i);
			
			List<Member> members = Container.memberDao.members;
			
			for (Member member : members) {
				if (member.id == article.memberId) {
					writerName = member.userName;
				}
			}
			System.out.printf(" %d  /  %s  /  %d  /  %s\n ", article.id, article.title, article.hit, writerName);
		}
	}

	/** 게시글 상세보기 */
	private void showDetail() {
		String[] cmdDiv = command.split(" "); // 'article' 'detail' '1'
		if (cmdDiv.length < 3) {
			System.out.println("게시물 번호를 확인해주세요");
			return;
		}
		int id = Integer.parseInt(cmdDiv[2]);
		
		Article foundArticle = articleService.getArticleById(id);
		
		if (foundArticle == null) {
			System.out.printf("%d번 게시물은 존재하지 않습니다\n", id);
			return;
		}
		String writerName = null;
		List<Member> members = Container.memberDao.members;
		
		for (Member member : members) {
			if (member.id == foundArticle.id) {
				writerName = member.userName;
			}
		}
		System.out.println("번호 : " + foundArticle.id);
		System.out.println("날짜 : " + foundArticle.regDate);
		System.out.println("수정날짜 : " + foundArticle.updateDate);
		System.out.println("작성자 : " + writerName);
		System.out.println("제목 : " + foundArticle.title);
		System.out.println("내용 : " + foundArticle.content);
		System.out.println("조회 수 : " + foundArticle.hit);
		foundArticle.hit++;
	}

	/** 게시글 수정 */
	private void doModify() {
		String[] cmdDiv = command.split(" ");
		if (cmdDiv.length < 3) {
			System.out.println("게시물 번호를 확인해주세요");
			return;
		}
		int id = Integer.parseInt(cmdDiv[2]);

		Article foundArticle = articleService.getArticleById(id);

		if (foundArticle == null) {
			System.out.printf("%d번 게시물은 존재하지 않습니다\n", id);
			return;
		}

		if (foundArticle.memberId != loginedMember.id) {
			System.out.println("권한이 없습니다");
			return;
		}

		foundArticle.updateDate = Util.getNowDateTimeStr();
		System.out.print("제목 : ");
		foundArticle.title = sc.nextLine();
		System.out.print("내용 : ");
		foundArticle.content = sc.nextLine();
		System.out.println(id + "번 게시글이 수정되었습니다, " + Util.getNowDateTimeStr());
	}

	/** 게시글 삭제 */
	private void doDelete() {
		String[] cmdDiv = command.split(" ");

		if (cmdDiv.length < 3) {
			System.out.println("게시물 번호를 확인해주세요");
			return;
		}
		int id = Integer.parseInt(cmdDiv[2]);
		Article foundArticle = articleService.getArticleById(id);
		if (foundArticle == null) {
			System.out.printf("%d번 게시물은 존재하지 않습니다\n", id);
			return;
		}

		/*
		 * 게시글의 존재 여부와 게시글의 memberId값을 확인한 후 권한 여부를 따져야하므로, case가 아닌 method 내부, 본 위치에
		 * 작성해줌이 옳다
		 */
		if (foundArticle.memberId != loginedMember.id) {
			System.out.println("권한이 없습니다");
			return;
		}

		articleService.remove(foundArticle);
		System.out.printf("%d번 게시물이 삭제되었습니다\n", id);
	}

	/* 테스트 데이터 생성 */
	public void makeTestData() {
		System.out.println("테스트를 위한 데이터를 생성합니다");
		articleService.add(new Article(1, Util.getNowDateTimeStr(), Util.getNowDateTimeStr(), 3, "제목1", "내용1", 11));
		articleService.add(new Article(2, Util.getNowDateTimeStr(), Util.getNowDateTimeStr(), 2, "제목2", "내용2", 22));
		articleService.add(new Article(3, Util.getNowDateTimeStr(), Util.getNowDateTimeStr(), 2, "제목3", "내용3", 33));
	}
}
