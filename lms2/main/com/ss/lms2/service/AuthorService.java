package com.ss.lms2.service;

import java.sql.SQLException;

import java.util.List;
import java.util.Optional;

import com.ss.lms2.dao.*;
import com.ss.lms2.pojo.*;


public class AuthorService {

	private AuthorDao authorDao;
	
	private static AuthorService service = new AuthorService(
			AuthorDao.getDao()
			);
	
	public Optional<Author> get(int authorId) throws SQLException {
		return authorDao.get(authorId);
	}
	
	public static AuthorService getService() {
		return service;
	}
	
	private AuthorService(AuthorDao authorDao) {
		this.authorDao = authorDao;
	}
	
	public void delete(Author author) throws SQLException {
		authorDao.delete(author);
	}
	
	public void insert(Author author) throws SQLException {
		authorDao.insert(author);
	}
	
	public void update(Author author) throws SQLException {
		authorDao.update(author);
	}

	public List<Author> getAll() throws SQLException {
		return authorDao.getAll();
	}
	
}
