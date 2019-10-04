package com.ss.lms2.pojo;

import java.time.LocalDate;

public class BookLoan {

	private Book book;
	private LibraryBranch branch;
	private Borrower borrower;
	private LocalDate dateOut;
	private LocalDate dueDate;
	
	public BookLoan(Book book, LibraryBranch branch, Borrower borrower, LocalDate dateOut, LocalDate dueDate) {
		this.book = book;
		this.branch = branch;
		this.borrower = borrower;
		this.dateOut = dateOut;
		this.dueDate = dueDate;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public LibraryBranch getBranch() {
		return branch;
	}

	public void setBranch(LibraryBranch branch) {
		this.branch = branch;
	}

	public Borrower getBorrower() {
		return borrower;
	}

	public void setBorrower(Borrower borrower) {
		this.borrower = borrower;
	}

	public LocalDate getDateOut() {
		return dateOut;
	}

	public void setDateOut(LocalDate dateOut) {
		this.dateOut = dateOut;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}
}
