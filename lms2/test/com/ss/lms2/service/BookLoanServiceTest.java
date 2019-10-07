package com.ss.lms2.service;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.ss.lms2.pojo.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BookLoanServiceTest {
	
	private final BookLoanService service = BookLoanService.getService();
	private final BookCopiesService copiesService = BookCopiesService.getService();
	
	@BeforeEach
	public void setUp() throws SQLException  {
		TestUtils.populateTestDb();
	}

	
	@Test
	void getGetByBorrowerShouldReturnDansLoansFromDc() throws SQLException {

		
		Borrower dan = new Borrower(
				2, "dan", "1 dan street", "222-222-2222");
		
		LibraryBranch dc = new LibraryBranch(
				3, "dc", "1 dc ave");
		
		List<BookLoan> actual = service.getByBorrower(dan, dc);
				
		
		List<Integer> actualBooks = actual.stream()
				.map(BookLoan::getBook)
				.map(Book::getBookId)
				.collect(Collectors.toList());
		
		
		List<Integer> actualBranches = actual.stream()
				.map(BookLoan::getBranch)
				.map(LibraryBranch::getBranchId)
				.collect(Collectors.toList());
		
		int actualLoanCount = actual.size();
		
		List<Integer> expectedBooks = List.of(1, 2, 3);
		List<Integer> expectedBranches = List.of(3, 3, 3);
		
		
		assertEquals(3, actualLoanCount);
		
		assertTrue(actualBooks.containsAll(expectedBooks));
		assertTrue(actualBranches.containsAll(expectedBranches));
	}

	@Test
	void insertShouldAddALoan() throws SQLException {
		
		Borrower dan = new Borrower(
				2, "dan", "1 dan street", "222-222-2222");
		
		LibraryBranch dc = new LibraryBranch(
				3, "dc", "1 dc ave");
		
		Book football = new Book(4, null, null, null);
		
		BookLoan loan = new BookLoan(
				football,
				dc, 
				dan,
				LocalDate.now(), 
				LocalDate.now());
		
		
		service.insertLoan(loan);
		
		boolean actual = service.get(dan, dc, football).isPresent();
		
		assertTrue(actual);
	}
	
	@Test
	void insertShouldDecrementNoOfCopies() throws SQLException {
		
		Borrower dan = new Borrower(
				2, "dan", "1 dan street", "222-222-2222");
		
		LibraryBranch branch = new LibraryBranch(
				3, "dc", "1 dc ave");
		
		Book football = new Book(4, null, null, null);
		
		BookLoan loan = new BookLoan(
				football,
				branch, 
				dan,
				LocalDate.now(), 
				LocalDate.now());
		
		
		service.insertLoan(loan);
		
		
		
		int actual = copiesService.get(branch, football)
				.get()
				.getNoOfCopies();
				
				
		assertEquals(2, actual);
	}
	
	@Test
	void insertShouldBeIdempotent() throws SQLException {
		
		Borrower dan = new Borrower(
				2, "dan", "1 dan street", "222-222-2222");
		
		LibraryBranch dc = new LibraryBranch(
				3, "dc", "1 dc ave");
		
		Book canada = new Book(2, null, null, null);
		
		BookLoan existingLoan = new BookLoan(
				canada,
				dc, 
				dan,
				LocalDate.now(), 
				LocalDate.now());
		
		
		service.insertLoan(existingLoan);
		
		
		int actual = copiesService.get(dc, canada)
				.get()
				.getNoOfCopies();
		
		assertEquals(0, actual);
	}
	
	@Test
	void deleteShouldRemoveALoan() throws SQLException {
		
		Borrower dan = new Borrower(
				2, "dan", "1 dan street", "222-222-2222");
		
		LibraryBranch dc = new LibraryBranch(
				3, "dc", "1 dc ave");
		
		Book canada = new Book(2, null, null, null);
		
		BookLoan loan = new BookLoan(
				canada,
				dc, 
				dan,
				LocalDate.now(), 
				LocalDate.now());
		
		
		service.deleteLoan(loan);
		
		boolean actual = service.get(dan, dc, canada).isEmpty();
		
		assertTrue(actual);
	}
	
	@Test
	void deleteShouldIncrementNoOfCopies() throws SQLException {
		
		Borrower dan = new Borrower(
				2, "dan", "1 dan street", "222-222-2222");
		
		LibraryBranch dc = new LibraryBranch(
				3, "dc", "1 dc ave");
		
		Book canada = new Book(2, null, null, null);
		
		BookLoan loan = new BookLoan(
				canada,
				dc, 
				dan,
				LocalDate.now(), 
				LocalDate.now());
		
		
		service.deleteLoan(loan);
		
		int actual = copiesService.get(dc, canada)
				.get()
				.getNoOfCopies();
				
				
		assertEquals(1, actual);
	}
	
	@Test
	void deleteShouldBeIdempotent() throws SQLException {
		
		Borrower dan = new Borrower(
				2, "dan", "1 dan street", "222-222-2222");
		
		LibraryBranch dc = new LibraryBranch(
				3, "dc", "1 dc ave");
		
		Book canada = new Book(2, null, null, null);
		
		BookLoan loan = new BookLoan(
				canada,
				dc, 
				dan,
				LocalDate.now(), 
				LocalDate.now());
		
		//Delete twice should be the same as once
		service.deleteLoan(loan);
		service.deleteLoan(loan);
		
		int actual = copiesService.get(dc, canada)
				.get()
				.getNoOfCopies();
				
				
		assertEquals(1, actual);
	}
}
