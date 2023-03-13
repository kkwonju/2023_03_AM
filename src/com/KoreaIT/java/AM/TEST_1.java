package com.KoreaIT.java.AM;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TEST_1 {
	static List<Post> posts = new ArrayList<>();
	
	
	public static void main(String[]args) {
		
		makeTestData();
		
		System.out.println("== 프로그램 시작");
		Scanner sc = new Scanner(System.in);

		int lastPostId = 3;
		
		while (true) {
			System.out.print("명령어 ) ");
			String command = sc.nextLine().trim();

			if (command.length() == 0) {
				System.out.println("명령어를 입력해주세요");
				continue;
			}

			if (command.equals("exit")) {
				break;
			}

			if (command.equals("post list")) {
				if(posts.size() == 0) {
					System.out.println("게시물이 없습니다");
					continue;
				}
				System.out.println("번호  |  제목");
				for(int i = posts.size()-1; i >= 0; i--) {
					Post foundPost = posts.get(i);
					System.out.printf("  %d  |  %s\n", foundPost.id, foundPost.title);
				}
				
			} else if(command.equals("post write")) {
				int id = lastPostId + 1;
				System.out.print("제목  : ");
				String title = sc.nextLine();
				System.out.print("내용  : ");
				String body = sc.nextLine();
				String regDate = Util.getNowDateTimeStr();
				Post post = new Post(id, regDate, regDate, title, body);
				posts.add(post);
				System.out.println(id + "번 게시물이 작성되었습니다");
				lastPostId++;
				
			} else if(command.startsWith("post detail")) {
				String[] comDiv = command.split(" ");
				if(comDiv.length < 3) {
					System.out.println("게시물 번호를 입력해주세요");
					continue;
				}
				int id = Integer.parseInt(comDiv[2]);
				Post foundPost = getPostById(id);
				if(foundPost == null) {
					System.out.println("해당 게시물이 존재하지않습니다");
					continue;
				}
				System.out.println("번호 : " + foundPost.id);
				System.out.println("날짜 : " + foundPost.regDate);
				System.out.println("제목 : " + foundPost.title);
				System.out.println("내용 : " + foundPost.body);
				
			} else if(command.startsWith("post modify")) {
				String[] comDiv = command.split(" ");
				if(comDiv.length < 3) {
					System.out.println("게시물 번호를 입력해주세요");
					continue;
				}
				int id = Integer.parseInt(comDiv[2]);
				Post foundPost = getPostById(id);
				if(foundPost == null) {
					System.out.println("해당 게시물이 존재하지않습니다");
					continue;
				}
				
				System.out.print("제목 : ");
				foundPost.title = sc.nextLine();
				System.out.print("내용 : ");
				foundPost.body = sc.nextLine();
				foundPost.updateDate = Util.getNowDateTimeStr();
 				System.out.println(id + "번 게시물을 수정했습니다");
 				
			} else if(command.startsWith("post delete")) {
				String[] comDiv = command.split(" ");
				if(comDiv.length < 3) {
					System.out.println("게시물 번호를 입력해주세요");
					continue;
				}
				int id = Integer.parseInt(comDiv[2]);
				int foundPostIndex = getPostByIndex(id);
				if(foundPostIndex == -1) {
					System.out.println("해당 게시물이 존재하지않습니다");
					continue;
				}
				posts.remove(foundPostIndex);
				System.out.println(id + "번 게시물이 삭제되었습니다");
				
			} else {
				System.out.println("명령어를 확인해주세요");
			}
			
		}
		System.out.println("== 프로그램 종료 ==");
		sc.close();
	}
	private static int getPostByIndex(int id) {
		int i = 0;
		for(Post post : posts) {
			if(post.id == id) {
				return i;
			}
			i++;
		}
		return -1;
	}
	
	private static Post getPostById(int id) {
		for(Post post : posts) {
			if(post.id == id) {
				return post;
			}
		}
		
		return null;
	}
	
	private static void makeTestData() {
		posts.add(new Post(1, "2023-03-13 12:12:12", "2023-03-13 12:12:12", "제목 1", "내용 1"));
		posts.add(new Post(2, "2023-03-13 10:10:10", "2023-03-13 10:10:10", "제목 2", "내용 2"));
		posts.add(new Post(3, "2023-03-13 08:08:08", "2023-03-13 08:08:08", "제목 3", "내용 3"));
	}
	
	
}

class Post {
	int id;
	String regDate;
	String updateDate;
	String title;
	String body;
	
	Post(int id, String regDate, String updateDate, String title, String body) {
		this.id = id;
		this.regDate = regDate;
		this.updateDate = updateDate;
		this.title = title;
		this.body = body;
		
	}
}
