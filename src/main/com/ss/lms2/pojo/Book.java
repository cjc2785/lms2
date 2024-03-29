package com.ss.lms2.pojo;

public class Book {
	
	private int bookId;
	private String title;
	private Author author;
	private Publisher publisher;
	
	public Book(int bookId, String title, Author author, Publisher publisher) {
		this.bookId = bookId;
		this.title = title;
		this.author = author;
		this.publisher = publisher;
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

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}

	public Publisher getPublisher() {
		return publisher;
	}

	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}
}
