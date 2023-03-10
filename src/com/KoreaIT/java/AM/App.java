package com.KoreaIT.java.AM;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.KoreaIT.java.AM.controller.ArticleController;
import com.KoreaIT.java.AM.controller.Controller;
import com.KoreaIT.java.AM.controller.MemberController;
import com.KoreaIT.java.AM.dto.Article;
import com.KoreaIT.java.AM.dto.Member;
 
public class App {
	/* articles : Article만 넣을 수 있는 List 객체 생성 */
	static List<Article> articles;
	static List<Member> members;

	public void start() {
		articles = new ArrayList<>();
		members = new ArrayList<>();

		System.out.println("==프로그램 시작==");
		Scanner sc = new Scanner(System.in);

		MemberController memberController = new MemberController(members, sc);
		ArticleController articleController = new ArticleController(articles, sc);
		articleController.makeTestData();
		Controller controller;

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
			
			String[] commandDiv = command.split(" ");
			String controllerName = commandDiv[0];
			/*
			 @error_1 , be
			 String actionMethodName = commandDiv[1]; 
			 java.lang.ArrayIndexOutOfBoundsException:
			 Index 1 out of bounds for length 1
			 */
			
			if(commandDiv.length == 1) {
				System.out.println("명령어를 확인해주세요");
				continue;
			}
			
			String actionMethodName = commandDiv[1]; // @error_1 , af
			controller = null;
			
			if(controllerName.equals("article")) {
				controller = articleController;
			} else if(controllerName.equals("member")) {
				controller = memberController;  
			} else {
				System.out.println("명령어를 확인해주세요");
				continue;
			}
			
			controller.doAction(actionMethodName, command);
			
		}
		System.out.println("==프로그램 끝==");
		sc.close();
	}

}