package com.KoreaIT.java.AM.dao;

public abstract class Dao {
	protected int lastId;
	Dao() {
		lastId = 0;
	}
	public abstract int getLastId();
}
