package com.ss.lms2.service;

import java.sql.SQLException;
import java.util.Optional;

import com.ss.lms2.dao.*;
import com.ss.lms2.pojo.*;

public class BookCopiesService {

	private BookCopiesDao copiesDao;
	
	private static BookCopiesService service = new BookCopiesService(
			BookCopiesDao.getDao()
			);
	
	public static BookCopiesService getService() {
		return service;
	}
	
	
	private BookCopiesService(BookCopiesDao copiesDao) {
		this.copiesDao = copiesDao;
	}


	public Optional<BookCopies> get(LibraryBranch branch, Book book) throws SQLException {
		
		return copiesDao.get(branch, book);
	}
	
	public void update(BookCopies copies) throws SQLException {
		
		copiesDao.update(copies);
	}
}
