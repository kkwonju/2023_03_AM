//? : 저장되어 불러내기가 가능한 단계로 만들기
//과제 단락 나눠 해결하는 연습부터, 하나씩 발전되는 식으로 코딩
//command.length() == 0 : 입력 없음

package com.KoreaIT.java.AM;

import java.util.Scanner;

public class Main {
	public static void main(String[] args) {		
		System.out.println("==프로그램 시작==");
		
		Scanner sc = new Scanner(System.in);

		int num = 0;
		while (true) {
			System.out.print("명령어 > ");
			String command = sc.nextLine();
			
			if(command.equals("article list")){
				if(num > 0) {
					;
				}
				else {
					System.out.println("게시글이 없습니다");
				}
			}
			else if(command.equals("article write")) {
				num++;
				System.out.print("제목 : ");
				String title = sc.nextLine();
				System.out.print("내용 : ");
				String content = sc.nextLine();
				System.out.printf("%d번 글이 생성되었습니다\n", num);
				
			}
			else if(command.equals("exit")) {
				break;
			}
			else {
				System.out.println("존재하지 않는 명령어입니다");
			}
		}

		System.out.println("==프로그램 끝==");

		sc.close();
	}
}

