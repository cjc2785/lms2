package com.ss.lms2.dao;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.ss.lms2.db.Db;
import com.ss.lms2.db.TableRow;
import com.ss.lms2.pojo.BookLoan;
import com.ss.lms2.pojo.Borrower;
import com.ss.lms2.pojo.LibraryBranch;
import com.ss.lms2.pojo.Book;
import com.ss.lms2.pojo.Author;
import com.ss.lms2.pojo.Publisher;

public class BookLoanDao {

	private Db db;

	private static BookLoanDao dao = new BookLoanDao(Db.getConnection());

	public static BookLoanDao getDao() {
		return dao;
	}

	public BookLoanDao(Db db) {
		this.db = db;
	}

	//Get all loans by the borrower
	public List<BookLoan> getByBorrower(Borrower borrower) throws SQLException {

		String query = "SELECT * FROM library.tbl_book b " +
				"JOIN library.tbl_author a ON a.authorId=b.authId " +
				"JOIN library.tbl_publisher p ON p.publisherId=b.pubId " + 
				"JOIN library.tbl_book_loans bl ON bl.bookId=b.bookId " + 
				"JOIN library.tbl_library_branch lb ON lb.branchId=bl.branchId " +
				"JOIN library.tbl_borrower bor ON bor.cardNo=bl.cardNo " +
				"WHERE bor.cardNo=?";

		return db.withQuery(query, 
				row -> rowToLoans(borrower, row),
				parameterList -> {
					parameterList.setInt(1, borrower.getCardNo());
				});
	}

	//Get all loans by the borrower at the branch
	public List<BookLoan> getByBorrower(Borrower borrower, LibraryBranch branch)
			throws SQLException {

		String query = "SELECT * FROM library.tbl_book b " +
				"JOIN library.tbl_author a ON a.authorId=b.authId " +
				"JOIN library.tbl_publisher p ON p.publisherId=b.pubId " + 
				"JOIN library.tbl_book_loans bl ON bl.bookId=b.bookId " + 
				"JOIN library.tbl_library_branch lb ON lb.branchId=bl.branchId " +
				"JOIN library.tbl_borrower bor ON bor.cardNo=bl.cardNo " +
				"WHERE bor.cardNo=? AND " +
				"lb.branchId=?";

		return db.withQuery(query, 
				row -> rowToLoans(borrower, row),
				parameterList -> {
					parameterList.setInt(1, borrower.getCardNo());
					parameterList.setInt(2, branch.getBranchId());
				});
	}

	public Optional<BookLoan> get(Borrower borrower, LibraryBranch branch, Book book) 
			throws SQLException {

		String query = "SELECT * FROM library.tbl_book b " +
				"JOIN library.tbl_author a ON a.authorId=b.authId " +
				"JOIN library.tbl_publisher p ON p.publisherId=b.pubId " + 
				"JOIN library.tbl_book_loans bl ON bl.bookId=b.bookId " + 
				"JOIN library.tbl_library_branch lb ON lb.branchId=bl.branchId " +
				"JOIN library.tbl_borrower bor ON bor.cardNo=bl.cardNo " +
				"WHERE bor.cardNo=? AND " +
				"lb.branchId=? AND " + 
				"b.bookId=?";

		return db.withQueryOne(query, 
				row -> rowToLoans(borrower, row),
				parameterList -> {
					parameterList.setInt(1, borrower.getCardNo());
					parameterList.setInt(2, branch.getBranchId());
					parameterList.setInt(3, book.getBookId());
				});
	}

	//Will fail if the loan already exists
	public void insert(BookLoan loan) throws SQLException {

		String query = "INSERT INTO library.tbl_book_loans " + 
				"VALUES (?,?,?, ?,?) ";

		db.withUpdate(query, parameterList -> {
			parameterList.setInt(1, loan.getBook().getBookId());
			parameterList.setInt(2, loan.getBranch().getBranchId());
			parameterList.setInt(3, loan.getBorrower().getCardNo());
			parameterList.setDate(4, loan.getDateOut());
			parameterList.setDate(5, loan.getDueDate());
		});
	}

	public void delete(BookLoan loan) throws SQLException {

		String query = "DELETE FROM library.tbl_book_loans bl WHERE " + 
				"bl.bookId=? AND " +
				"bl.branchId=? AND " +
				"bl.cardNo=?";

		db.withUpdate(query, parameterList -> {
			parameterList.setInt(1, loan.getBook().getBookId());
			parameterList.setInt(2, loan.getBranch().getBranchId());
			parameterList.setInt(3, loan.getBorrower().getCardNo());;
		});
	}
	
	public void updateDueDate(BookLoan bookLoan) throws SQLException {
		
		String query = "UPDATE library.tbl_book_loan SET " + 
				"dueDate=? " +
				"WHERE bookId=? AND branchId=? AND cardNo=?";
		
		db.withUpdate(query, parameterList -> {
			parameterList.setDate(1, bookLoan.getDueDate());
			parameterList.setInt(2, bookLoan.getBook().getBookId());
			parameterList.setInt(3, bookLoan.getBranch().getBranchId());
			parameterList.setInt(4, bookLoan.getBorrower().getCardNo());
		});
	}

	private BookLoan rowToLoans(Borrower borrower, TableRow row) {

		LocalDate dateOut = row.getDate("dateOut");
		LocalDate dueDate = row.getDate("dueDate");

		int bookId = row.getInt("bookId");
		String title = row.getString("title");

		int authorId = row.getInt("authorId");
		String authorName = row.getString("authorName");

		int publisherId = row.getInt("publisherId");
		String publisherName = row.getString("publisherName");
		String publisherAddress = row.getString("publisherAddress");
		String publisherPhone = row.getString("publisherPhone");

		int branchId = row.getInt("branchId");
		String branchName = row.getString("branchName");
		String branchAddress = row.getString("branchAddress");

		Author author = new Author(authorId, authorName);

		Publisher publisher = new Publisher(
				publisherId,
				publisherName,
				publisherAddress,
				publisherPhone);

		Book book = new Book(bookId, title, author, publisher);

		LibraryBranch branch = new LibraryBranch(branchId, branchName, branchAddress);

		BookLoan loan = new BookLoan(book, branch, borrower, dateOut, dueDate);

		return loan;
	}
}