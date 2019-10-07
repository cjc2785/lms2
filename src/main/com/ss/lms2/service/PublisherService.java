package com.ss.lms2.service;

import java.sql.SQLException;

import java.util.List;
import java.util.Optional;

import com.ss.lms2.dao.*;
import com.ss.lms2.pojo.*;


public class PublisherService {

	private PublisherDao publisherDao;
	
	private static PublisherService service = new PublisherService(
			PublisherDao.getDao()
			);
	
	public Optional<Publisher> get(int publisherId) throws SQLException {
		return publisherDao.get(publisherId);
	}
	
	public static PublisherService getService() {
		return service;
	}
	
	private PublisherService(PublisherDao publisherDao) {
		this.publisherDao = publisherDao;
	}
	
	public void delete(Publisher publisher) throws SQLException {
		publisherDao.delete(publisher);
	}
	
	public void insert(Publisher publisher) throws SQLException {
		publisherDao.insert(publisher);
	}
	
	public void update(Publisher publisher) throws SQLException {
		publisherDao.update(publisher);
	}

	public List<Publisher> getAll() throws SQLException {
		return publisherDao.getAll();
	}
	
}
