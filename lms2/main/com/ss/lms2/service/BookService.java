package com.ss.lms2.service;

import java.sql.SQLException;

import java.util.List;


import com.ss.lms2.dao.*;
import com.ss.lms2.pojo.*;


public class BookService {

	private BookDao bookDao;
	

	public List<Book> getAll() throws SQLException {
		return bookDao.getAll();
	}
}
