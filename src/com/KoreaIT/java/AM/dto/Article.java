package com.KoreaIT.java.AM.dto;

/* 게시글 클래스 */
public class Article extends Dto{
	public String content;
	public String time;
	public int hit;
	public int memberId;

	public Article(int id, String regDate, String updateDate, int memberId, String title, String content) {
		this(id, regDate, updateDate, memberId, title, content, 0);
	}

	public Article(int id, String regDate,  String updateDate, int memberId, String title, String content, int hit) {
		this.id = id;
		this.regDate = regDate;
		this.updateDate = updateDate;
		this.memberId = memberId;
		this.title = title;
		this.content = content;
		this.hit = hit;
	}
}