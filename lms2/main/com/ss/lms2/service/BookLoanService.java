package com.ss.lms2.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.ss.lms2.dao.*;
import com.ss.lms2.pojo.*;

public class BookLoanService {

	private BookLoanDao loanDao;
	private BookCopiesDao copiesDao;
	
	private static BookLoanService service = new BookLoanService(
			BookLoanDao.getDao(),
			BookCopiesDao.getDao()
			);
	
	public static BookLoanService getService() {
		return service;
	}
	
	private BookLoanService(BookLoanDao loanDao, BookCopiesDao copiesDao) {
		this.loanDao = loanDao;
		this.copiesDao = copiesDao;
	}
	
	

	public List<BookLoan> getByBorrower(Borrower borrower, LibraryBranch branch) throws SQLException {
		return loanDao.getByBorrower(borrower, branch);
	}
	
	public Optional<BookLoan> get(Borrower borrower, LibraryBranch branch, Book book) 
		throws SQLException {
		
		return loanDao.get(borrower, branch, book);
	}
	
	//Also decrements the noOfCopies 
	//Will decrement even if the loan exists
	//Don't call if there are no copies of the book in the library
	public void insertLoan(BookLoan loan) throws SQLException {
		
		BookCopies copies = copiesDao.get(loan.getBranch(), loan.getBook()).get();
		
		//decrement the noOfCopies
		copies.setNoOfCopies(copies.getNoOfCopies() - 1);
		copiesDao.update(copies);
		
		loanDao.insert(loan);
	}
	
	//Also increments the noOfCopies 
	//Will increment even if the loan does not exist
	//Don't call if there were never any copies of the book in the library
	public void deleteLoan(BookLoan loan) throws SQLException {
		
		BookCopies copies = copiesDao.get(loan.getBranch(), loan.getBook()).get();
		
		//increment the noOfCopies
		copies.setNoOfCopies(copies.getNoOfCopies() + 1);
		copiesDao.update(copies);
		
		loanDao.delete(loan);
	}
}
