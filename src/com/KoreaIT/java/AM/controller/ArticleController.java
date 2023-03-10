package com.KoreaIT.java.AM.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.KoreaIT.java.AM.dto.Article;
import com.KoreaIT.java.AM.util.Util;

public class ArticleController extends Controller{
	private List<Article> articles;
	private Scanner sc;
	private String command;
	private String actionMethodName;

	int lastArticleId = 3;

	public ArticleController(List<Article> articles, Scanner sc) {
		this.articles = articles;
		this.sc = sc;
	}
	
	public void doAction(String actionMethodName, String command) {
		this.command = command;
		this.actionMethodName = actionMethodName;
		
		switch(actionMethodName) {
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
			System.out.println("명령어를 확인해주세요");
			break;
		}
	}

	public void showList() {
		if (articles.size() == 0) {
			System.out.println("게시글이 없습니다");
			return;
		}
		String searchKeyword = command.substring("article list".length()).trim();
		List<Article> forPrintArticles = articles;

		if (searchKeyword.length() > 0) {
			System.out.println("searchKeyword : " + searchKeyword);
			forPrintArticles = new ArrayList<>();

			for (Article article : articles) {
				if (article.title.contains(searchKeyword)) {
					forPrintArticles.add(article);
				}
			}
			if (forPrintArticles.size() == 0) {
				System.out.println("검색 결과가  없습니다");
				return;
			}
		}

		System.out.println(" 번호 / 제목 / 조회 수 ");
		for (int i = forPrintArticles.size() - 1; i >= 0; i--) {
			Article article = forPrintArticles.get(i);
			System.out.printf(" %d / %s / %d \n", article.id, article.title, article.hit);
		}
	}


	public void doWrite() {
		int id = lastArticleId + 1;
		System.out.print("제목 : ");
		String title = sc.nextLine();
		String regDate = Util.getNowDateTimeStr();
		System.out.print("내용 : ");
		String content = sc.nextLine();
		Article article = new Article(id, regDate, regDate, title, content);
		articles.add(article);
		System.out.printf("%d번 글이 생성되었습니다\n", id);
		lastArticleId++;
	}

	public void showDetail() {
		String[] cmdDiv = command.split(" "); // 'article' 'detail' '1'
		if (cmdDiv.length < 3) {
			System.out.println("게시물 번호를 확인해주세요");
			return;
		}
		int id = Integer.parseInt(cmdDiv[2]);
		Article foundArticle = getArticleById(id);
		if (foundArticle == null) {
			System.out.printf("%d번 게시물은 존재하지 않습니다\n", id);
			return;
		}
		System.out.println("번호 : " + foundArticle.id);
		System.out.println("날짜 : " + foundArticle.regDate);
		System.out.println("수정날짜 : " + foundArticle.updateDate);
		System.out.println("제목 : " + foundArticle.title);
		System.out.println("내용 : " + foundArticle.content);
		System.out.println("조회 수 : " + foundArticle.hit);
		foundArticle.hit++;
	}

	public void doModify() {
		String[] cmdDiv = command.split(" ");
		if (cmdDiv.length < 3) {
			System.out.println("게시물 번호를 확인해주세요");
			return;
		}
		int id = Integer.parseInt(cmdDiv[2]);

		Article foundArticle = getArticleById(id);

		if (foundArticle == null) {
			System.out.printf("%d번 게시물은 존재하지 않습니다\n", id);
			return;
		}
		foundArticle.updateDate = Util.getNowDateTimeStr();
		System.out.print("제목 : ");
		foundArticle.title = sc.nextLine();
		System.out.print("내용 : ");
		foundArticle.content = sc.nextLine();
		System.out.println(id + "번 게시글이 수정되었습니다, " + Util.getNowDateTimeStr());
	}

	public void doDelete() {
		String[] cmdDiv = command.split(" ");

		if (cmdDiv.length < 3) {
			System.out.println("게시물 번호를 확인해주세요");
			return;
		}
		int id = Integer.parseInt(cmdDiv[2]);
		int foundIndex = getArticleIndexByid(id);
		if (foundIndex == -1) {
			System.out.printf("%d번 게시물은 존재하지 않습니다\n", id);
			return;
		}
		articles.remove(foundIndex);
		System.out.printf("%d번 게시물이 삭제되었습니다\n", id);
	}

	public int getArticleIndexByid(int id) {
		for (int i = 0; i < articles.size(); i++) {
			Article article = articles.get(i);
			if (article.id == id) {
				return i;
			}
		}
		return -1;
	}

	public Article getArticleById(int id) {
		for (int i = 0; i < articles.size(); i++) {
			Article article = articles.get(i);
			if (article.id == id) {
				return article;
			}
		}
		return null;
	}

	/* 테스트 데이터 생성 */
	public void makeTestData() {
		System.out.println("테스트를 위한 데이터를 생성합니다");
		articles.add(new Article(1, Util.getNowDateTimeStr(), Util.getNowDateTimeStr(), "제목1", "내용1", 11));
		articles.add(new Article(2, Util.getNowDateTimeStr(), Util.getNowDateTimeStr(), "제목2", "내용2", 22));
		articles.add(new Article(3, Util.getNowDateTimeStr(), Util.getNowDateTimeStr(), "제목3", "내용3", 33));
	}
}
