package com.ss.lms2.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.ss.lms2.db.Db;
import com.ss.lms2.db.TableRow;
import com.ss.lms2.pojo.*;

public class PublisherDao {
	
	private Db db;
	
	private static PublisherDao dao = new PublisherDao(Db.getConnection());
	
	public static PublisherDao getDao() {
		return dao;
	}

	private PublisherDao(Db db) {
		this.db = db;
	}
	
	
	public List<Publisher> getAll() throws SQLException{
		String query = "SELECT * FROM library.tbl_publisher a";
				
			return db.withQuery(query, this::rowToPublisher);
	}
	
	public Optional<Publisher> get(int publisherId) throws SQLException{
		String query = "SELECT * FROM library.tbl_publisher a "
				+  "WHERE publisherId = ?";
				
			return db.withQueryOne(query, this::rowToPublisher, 
					parameterList -> {
					parameterList.setInt(1, publisherId);	
					});
	}
	
	public void delete(Publisher publisher) throws SQLException {
		
		String query = "DELETE FROM library.tbl_publisher " + 
				"WHERE publisherId=?";
		
		db.withUpdate(query, parameterList -> {
			parameterList.setInt(1, publisher.getPublisherId());
		});
	}
	
	public void insert(Publisher publisher) throws SQLException {
		
		String query = "INSERT INTO library.tbl_publisher VALUES " + 
				"(?,?,?,?) ";
		
		db.withUpdate(query, parameterList -> {
			parameterList.setInt(1, publisher.getPublisherId());
			parameterList.setString(2, publisher.getPublisherName());
			parameterList.setString(3, publisher.getPublisherAddress());
			parameterList.setString(4, publisher.getPublisherPhone());
		});
	}
	
	public void update(Publisher publisher) throws SQLException {
		
		String query = "UPDATE library.tbl_publisher SET " 
				+ "publisherName=?, "
				+ "publisherAddress=?"
				+ "publisherPhone=?" 
				+ "WHERE publisherId=?";
		
		db.withUpdate(query, parameterList -> {
			parameterList.setString(1, publisher.getPublisherName());
			parameterList.setString(2, publisher.getPublisherAddress());
			parameterList.setString(3, publisher.getPublisherPhone());
			parameterList.setInt(4, publisher.getPublisherId());
		});
	}
	
	private Publisher rowToPublisher(TableRow row) {
		int publisherId = row.getInt("publisherId");
		String publisherName = row.getString("publisherName");
		String publisherAddress = row.getString("publisherAddress");
		String publisherPhone = row.getString("publisherPhone");
		
		Publisher publisher = new Publisher(publisherId, publisherName,
				publisherAddress, publisherPhone);

		return publisher;
	}
	
}