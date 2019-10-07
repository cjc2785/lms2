package com.ss.lms2.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.ss.lms2.db.Db;
import com.ss.lms2.db.TableRow;
import com.ss.lms2.pojo.*;

public class AuthorDao {
	
	private Db db;
	
	private static AuthorDao dao = new AuthorDao(Db.getConnection());
	
	public static AuthorDao getDao() {
		return dao;
	}

	private AuthorDao(Db db) {
		this.db = db;
	}
	
	
	public List<Author> getAll() throws SQLException{
		String query = "SELECT * FROM library.tbl_author a";
				
			return db.withQuery(query, this::rowToAuthor);
	}
	
	public Optional<Author> get(int authorId) throws SQLException{
		String query = "SELECT * FROM library.tbl_author a"
				+  " WHERE authorId = ?";
				
			return db.withQueryOne(query, this::rowToAuthor, 
					parameterList -> {
					parameterList.setInt(1, authorId);	
					});
	}
	
	public void delete(Author author) throws SQLException {
		
		String query = "DELETE FROM library.tbl_author " + 
				"WHERE authorId=?";
		
		db.withUpdate(query, parameterList -> {
			parameterList.setInt(1, author.getAuthorId());
		});
	}
	
	public void insert(Author author) throws SQLException {
		
		String query = "INSERT INTO library.tbl_author VALUES " + 
				"(?,?) ";
		
		db.withUpdate(query, parameterList -> {
			parameterList.setInt(1, author.getAuthorId());
			parameterList.setString(2, author.getAuthorName());
		});
	}
	
	public void update(Author author) throws SQLException {
		
		String query = "UPDATE library.tbl_author SET " + 
				"authorName=? " +
				"WHERE authorId=?";
		
		db.withUpdate(query, parameterList -> {
			parameterList.setString(1, author.getAuthorName());
			parameterList.setInt(2, author.getAuthorId());
		});
	}
	
	private Author rowToAuthor(TableRow row) {
		int authorId = row.getInt("authorId");
		String authorName = row.getString("authorName");
		
		Author author = new Author(authorId, authorName);

		return author;
	}
	
}