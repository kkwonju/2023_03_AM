package com.KoreaIT.java.AM.sevice;

import java.util.List;

import com.KoreaIT.java.AM.container.Container;
import com.KoreaIT.java.AM.dao.ArticleDao;
import com.KoreaIT.java.AM.dto.Article;

public class ArticleService {
	ArticleDao articleDao;

	public ArticleService() {
		articleDao = Container.articleDao;
	}
	
	public int setNewId() {
		int id = articleDao.setNewId();
		return id;
	}
	
	public void add(Article article) {
		articleDao.add(article);
	}
	
	public void remove(Article article) {
		articleDao.remove(article);
	}
	
	public List<Article> getForPrintArticles(String searchKeyword) {
		List<Article> articles = articleDao.getArticles(searchKeyword);
		return articles;
	}
	
	public Article getArticleById(int id) {
		return articleDao.getArticleById(id);
	}

		
}
