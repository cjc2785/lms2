package com.ss.lms2.pojo;

public class Book {
	
	private int bookId;
	private String title;
	private int authId;
	private int pubId;
	
	public Book(int bookId, String title, int authId, int pubId) {
		this.bookId = bookId;
		this.title = title;
		this.authId = authId;
		this.pubId = pubId;
	}

	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getAuthId() {
		return authId;
	}

	public void setAuthId(int authId) {
		this.authId = authId;
	}

	public int getPubId() {
		return pubId;
	}

	public void setPubId(int pubId) {
		this.pubId = pubId;
	}
}
