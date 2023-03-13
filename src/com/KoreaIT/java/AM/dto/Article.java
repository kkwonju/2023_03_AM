package com.KoreaIT.java.AM.dto;

/* 게시글 클래스 */
public class Article extends Dto{
	public String content;
	public String time;
	public int hit;

	public Article(int id, String regDate, String updateDate, String title, String content) {
		this(id, regDate, updateDate, title, content, 0);
	}

	public Article(int id, String regDate, String updateDate, String title, String content, int hit) {
		this.id = id;
		this.regDate = regDate;
		this.updateDate = updateDate;
		this.title = title;
		this.content = content;
		this.hit = hit;
	}
}