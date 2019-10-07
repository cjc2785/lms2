package com.ss.lms2.service;

import java.sql.SQLException;

import java.util.List;
import java.util.Optional;

import com.ss.lms2.dao.*;
import com.ss.lms2.pojo.*;


public class BookService {

	private BookDao bookDao;
	
	private static BookService service = new BookService(
			BookDao.getDao()
			);
	
	public static BookService getService() {
		return service;
	}
	
	private BookService(BookDao bookDao) {
		this.bookDao = bookDao;
	}
	
	public void delete(Book book) throws SQLException {
		bookDao.delete(book);
	}
	
	public void insert(Book book) throws SQLException {
		bookDao.insert(book);
	}
	
	public void update(Book book) throws SQLException {
		bookDao.update(book);
	}
	
	public Optional<Book> get(int bookId) throws SQLException {
		return bookDao.get(bookId);
	}
	
	public List<Book> getAll() throws SQLException {
		return bookDao.getAll();
	}
	
	public List<Book> getAvailable(LibraryBranch branch) throws SQLException {
		return bookDao.getAvailable(branch);
	}
}
