package com.ss.lms2.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.ss.lms2.db.Db;
import com.ss.lms2.db.TableRow;
import com.ss.lms2.pojo.LibraryBranch;
import com.ss.lms2.pojo.Book;
import com.ss.lms2.pojo.Author;
import com.ss.lms2.pojo.Publisher;

public class BookDao {

	private Db db;
	
	private static BookDao dao = new BookDao(Db.getConnection());
	
	public static BookDao getDao() {
		return dao;
	}
	
	private BookDao(Db db) {
		this.db = db;
	}

	public List<Book> getAll() throws SQLException {
		String query = "SELECT * FROM library.tbl_book b " +
				"JOIN library.tbl_author a ON a.authorId=b.authId " +
				"JOIN library.tbl_publisher p ON p.publisherId=b.pubId";
		
		return db.withQuery(query, this::rowToBook);
	}
	
	//Get all books that have copies remaining at the branch
	public List<Book> getAvailable(LibraryBranch branch) throws SQLException {
		
		String query = "SELECT * FROM library.tbl_book b " +
				"JOIN library.tbl_author a ON a.authorId=b.authId " +
				"JOIN library.tbl_publisher p ON p.publisherId=b.pubId " + 
				"JOIN library.tbl_book_copies bc ON bc.bookId=b.bookId " +
				"JOIN library.tbl_library_branch lb ON lb.branchId=bc.branchId " +
				"WHERE lb.branchId=? AND bc.noOfCopies > 0";
		
		return db.withQuery(query, this::rowToBook, parameterList -> {
			parameterList.setInt(1, branch.getBranchId());
		});
	}
	
	/*
	 * For the nested relations (author & publisher), only considers
	 * 	their new id value
	 */
	public Optional<Book> get(int bookId) throws SQLException{
		String query = "SELECT * FROM library.tbl_book b " 
				+ "JOIN library.tbl_author a ON a.authorId=b.authId " 
				+ "JOIN library.tbl_publisher p ON p.publisherId=b.pubId "
				+ "WHERE bookId = ?";
		return db.withQueryOne(query, this::rowToBook,
				parameterList -> {
				parameterList.setInt(1, bookId);
				});
	}
	
	
	public void delete(Book book) throws SQLException {
		
		String query = "DELETE FROM library.tbl_book " + 
				"WHERE bookId=?";
		
		db.withUpdate(query, parameterList -> {
			parameterList.setInt(1, book.getBookId());
		});
	}
	
	
	public void insert(Book book) throws SQLException {
		
		String query = "INSERT INTO library.tbl_book VALUES " + 
				"(?,?,?,?) ";
		
		db.withUpdate(query, parameterList -> {
			parameterList.setInt(1, book.getBookId());
			parameterList.setString(2, book.getTitle());
			parameterList.setInt(3, book.getAuthor().getAuthorId());
			parameterList.setInt(4, book.getPublisher().getPublisherId());
		});
	}
	
	
	public void update(Book book) throws SQLException {
		
		String query = "UPDATE library.tbl_book SET " + 
				"title=?, " +
				"authId=?, " +
				"pubId=? " +
				"WHERE bookId=?";
		
		db.withUpdate(query, parameterList -> {
			parameterList.setString(1, book.getTitle());
			parameterList.setInt(2, book.getAuthor().getAuthorId());
			parameterList.setInt(3, book.getPublisher().getPublisherId());
			parameterList.setInt(4, book.getBookId());
		});
	}
	
	private Book rowToBook(TableRow row) {
		
		int bookId = row.getInt("bookId");
		String title = row.getString("title");
		
		int authorId = row.getInt("authId");
		String authorName = row.getString("authorName");
		
		int publisherId = row.getInt("publisherId");
		String publisherName = row.getString("publisherName");
		String publisherAddress = row.getString("publisherAddress");
		String publisherPhone = row.getString("publisherPhone");
		
		Author author = new Author(authorId, authorName);
		
		Publisher publisher = new Publisher(
				publisherId,
				publisherName,
				publisherAddress,
				publisherPhone);
		
		Book book = new Book(bookId, title, author, publisher);
	
		return book;
	}
}