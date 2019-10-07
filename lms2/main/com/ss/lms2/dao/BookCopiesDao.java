package com.ss.lms2.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.ss.lms2.db.Db;
import com.ss.lms2.db.TableRow;
import com.ss.lms2.pojo.LibraryBranch;
import com.ss.lms2.pojo.*;

public class BookCopiesDao {

	private Db db;

	private static BookCopiesDao dao = new BookCopiesDao(Db.getConnection());

	public static BookCopiesDao getDao() {
		return dao;
	}

	public BookCopiesDao(Db db) {
		this.db = db;
	}

	//Get all books that have copies remaining at the branch
	public List<BookCopies> get(LibraryBranch branch) throws SQLException {

		String query = "SELECT b.*, a.*, p.* COALESCE(bc.noOfCopies, 0) AS noOfCopies FROM library.tbl_book b " +
				"JOIN library.tbl_author a ON a.authorId=b.authId " +
				"JOIN library.tbl_publisher p ON p.publisherId=b.pubId " + 
				"LEFT JOIN library.tbl_book_copies bc ON bc.bookId=b.bookId AND bc.branchId=?"; 

		return db.withQuery(query, 
				row -> rowToBookCopies(branch, row),
				parameterList -> {
					parameterList.setInt(1, branch.getBranchId());
				});
	}

	public Optional<BookCopies> get(LibraryBranch branch, Book book) throws SQLException {

		String query = "SELECT b.*, a.*, p.*, COALESCE(bc.noOfCopies, 0) AS noOfCopies FROM library.tbl_book b " +
				"JOIN library.tbl_author a ON a.authorId=b.authId " +
				"JOIN library.tbl_publisher p ON p.publisherId=b.pubId " + 
				"LEFT JOIN library.tbl_book_copies bc ON bc.bookId=b.bookId AND bc.branchId=? " +
				"WHERE b.bookId=?"; 

		return db.withQueryOne(query, 
				row -> rowToBookCopies(branch, row),
				parameterList -> {
					parameterList.setInt(1, branch.getBranchId());
					parameterList.setInt(2, book.getBookId());
				});
	}


	/*
	 * Only updates the noOfCopies
	 */
	public void update(BookCopies copies) throws SQLException {

		String query = "INSERT INTO library.tbl_book_copies " + 
				"VALUES (?,?,?) " +
				"ON DUPLICATE KEY UPDATE " +
				"noOfCopies=?";

		db.withUpdate(query, parameterList -> {
			parameterList.setInt(1, copies.getBook().getBookId());
			parameterList.setInt(2, copies.getBranch().getBranchId());
			parameterList.setInt(3, copies.getNoOfCopies());
			parameterList.setInt(4, copies.getNoOfCopies());
		});
	}

	private BookCopies rowToBookCopies(LibraryBranch branch, TableRow row) {

		int noOfCopies = row.getInt("noOfCopies");

		int bookId = row.getInt("bookId");
		String title = row.getString("title");

		int authorId = row.getInt("authorId");
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

		BookCopies copies = new BookCopies(book, branch, noOfCopies);

		return copies;
	}
}