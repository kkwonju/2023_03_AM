//? : 저장되어 불러내기가 가능한 단계로 만들기
//과제 단락 나눠 해결하는 연습부터, 하나씩 발전되는 식으로 코딩
//command.length() == 0 : 입력 없음

package com.KoreaIT.java.AM;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

	/* articles : Article만 넣을 수 있는 List 객체 생성 */
	static List<Article> articles = new ArrayList<>();
	static List<Member> members = new ArrayList<>();

	public static void main(String[] args) {

		System.out.println("==프로그램 시작==");

		makeTestData();

		Scanner sc = new Scanner(System.in);

		int lastArticleId = 0;

		while (true) {

			System.out.print("명령어 > ");
			String command = sc.nextLine().trim();

			if (command.length() == 0) {
				System.out.println("명령어를 입력해주세요");
				continue;
			}

			if (command.equals("exit")) {
				break;
			}

			/* 게시글 목록 */
			if (command.equals("article list")) {
				if (articles.size() == 0) {
					System.out.println("게시글이 없습니다");
				} else {
					System.out.println(" 번호 / 제목 / 조회 수 ");
					for (int i = articles.size() - 1; i >= 0; i--) {
						Article article = articles.get(i);
						System.out.printf(" %d / %s / %d \n", article.id, article.title, article.hit);
					}
				}
				/* 게시글 작성 */
			} else if (command.equals("article write")) {

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

				/* 게시글 상세보기 */
			} else if (command.startsWith("article detail")) {

				String[] cmdDiv = command.split(" "); // 'article' 'detail' '1'

				if (cmdDiv.length < 3) {
					System.out.println("명령어를 확인해주세요");
					continue;
				}

				int id = Integer.parseInt(cmdDiv[2]);

				Article foundArticle = getArticleById(id);

				if (foundArticle == null) {
					System.out.printf("%d번 게시물은 존재하지 않습니다\n", id);
					continue;
				}

				System.out.println("번호 : " + foundArticle.id);
				System.out.println("날짜 : " + foundArticle.regDate);
				System.out.println("수정날짜 : " + foundArticle.updateDate);
				System.out.println("제목 : " + foundArticle.title);
				System.out.println("내용 : " + foundArticle.content);
				System.out.println("조회 수 : " + foundArticle.hit);
				foundArticle.hit++;

				/* 게시글 수정 */
			} else if (command.startsWith("article update")) {
				String[] cmdDiv = command.split(" ");

				if (cmdDiv.length < 3) {
					System.out.println("명령어를 확인해주세요");
					continue;
				}

				int id = Integer.parseInt(cmdDiv[2]);

				Article foundArticle = getArticleById(id);

				if (foundArticle == null) {
					System.out.printf("%d번 게시물은 존재하지 않습니다\n", id);
					continue;
				}
				foundArticle.updateDate = Util.getNowDateTimeStr();
				System.out.print("제목 : ");
				foundArticle.title = sc.nextLine();
				System.out.print("내용 : ");
				foundArticle.content = sc.nextLine();
				System.out.println(id + "번 게시글이 수정되었습니다, " + Util.getNowDateTimeStr());

				/* 게시글 삭제 */
			} else if (command.startsWith("article delete")) {
				String[] cmdDiv = command.split(" ");

				if (cmdDiv.length < 3) {
					System.out.println("명령어를 확인해주세요");
					continue;
				}

				int id = Integer.parseInt(cmdDiv[2]);

				int foundIndex = getArticleIndexByid(id);

				if (foundIndex == -1) {
					System.out.printf("%d번 게시물은 존재하지 않습니다\n", id);
					continue;
				}
				articles.remove(foundIndex);
				System.out.printf("%d번 게시물이 삭제되었습니다\n", id);

				/* 회원가입 기능 */
			} else if (command.equals("member join")) {
				String userID = null;
				String password = null;
				String passwordConfirm = null;

				while (true) {
					System.out.print("아이디 : ");
					userID = sc.nextLine();
					if (isJoinalbeUserID(userID) == false) {
						System.out.println("사용 중인 아이디입니다");
						continue; // false == false > 사용 불가능해 == 불가능 > true > 다시 입력
					}
					System.out.println("멋진 아이디입니다");
					break;
				}

				while (true) {
					System.out.print("비밀번호 : ");
					password = sc.nextLine();
					System.out.print("비밀번호 확인 : ");
					passwordConfirm = sc.nextLine();
					
					if(password.equals(passwordConfirm) == false) {
						System.out.println("비밀번호를 확인해주세요");
						continue;
					}
					break;
				}
				//
				System.out.print("이름 : ");
				String name = sc.nextLine();

				Member member = new Member(userID, password, name);
				members.add(member);

			} else if (command.equals("member list")) {
				for (Member member : members) {
					System.out.println(member.userID);
					System.out.println(member.userPassword);
					System.out.println(member.userName);
				}

				/* 명령어 잘못 입력 */
			} else {
				System.out.println("존재하지 않는 명령어입니다");
			}
		}

		System.out.println("==프로그램 끝==");

		sc.close();
	}

	public static boolean isJoinalbeUserID(String userID) {
		int index = getMemberByUserID(userID);
		if (index == -1) {
			return true; // 일치하는 값이 없었다 > 그러니 사용 가능하다
		}
		return false; // 일치하는 값이 있었다 > 중복된다
	}

	public static int getMemberByUserID(String userID) {
		int i = 0;
		for (Member member : members) {
			if (member.userID.equals(userID)) { // equals와 ==의 차이
				return i;
			}
			i++;
		}
		return -1;
	}

	public static int getArticleIndexByid(int id) {
		for (int i = 0; i < articles.size(); i++) {
			Article article = articles.get(i);
			if (article.id == id) {
				return i;
			}
		}
		return -1;
	}

	public static Article getArticleById(int id) {
		for (int i = 0; i < articles.size(); i++) {
			Article article = articles.get(i);
			if (article.id == id) {
				return article;
			}
		}
		return null;
	}

	/* 테스트 데이터 생성 */
	public static void makeTestData() {
		System.out.println("테스트를 위한 데이터를 생성합니다");
		articles.add(new Article(1, Util.getNowDateTimeStr(), Util.getNowDateTimeStr(), "제목1", "내용1", 11));
		articles.add(new Article(2, Util.getNowDateTimeStr(), Util.getNowDateTimeStr(), "제목2", "내용2", 22));
		articles.add(new Article(3, Util.getNowDateTimeStr(), Util.getNowDateTimeStr(), "제목3", "내용3", 33));
	}
}

/* 게시글 클래스 */
class Article {
	int id;
	String title;
	String regDate;
	String updateDate;
	String content;
	String time;
	int hit;

	Article(int id, String regDate, String updateDate, String title, String content) {
		this(id, regDate, updateDate, title, content, 0);
	}

	Article(int id, String regDate, String updateDate, String title, String content, int hit) {
		this.id = id;
		this.regDate = regDate;
		this.updateDate = updateDate;
		this.title = title;
		this.content = content;
		this.hit = hit;
	}
}

class Member {
	int id;
	String userID;
	String userPassword;
	String userName;

	Member(String userID, String userPassword, String userName) {
		this.userID = userID;
		this.userPassword = userPassword;
		this.userName = userName;
	}
}