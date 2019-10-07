package com.ss.lms2.service;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import com.ss.lms2.pojo.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BookServiceTest {
	
	private final BookService service = BookService.getService();
	
	@BeforeEach
	public void setUp() throws SQLException  {
		TestUtils.populateTestDb();
	}

	@Test
	public void getAllShouldReturn5Books() throws SQLException {
		
		List<Book> books = service.getAll();
		
		int actual = books.size();
		
		assertEquals(5, actual);
	}
	
	@Test
	public void getAllAvalilableShouldReturnDcBranchBooksWithAtLeast1Copy() throws SQLException {
		
		
		LibraryBranch dc = new LibraryBranch(
				3, "dc", "1 dc ave");
		
		List<Book> actual = service.getAvailable(dc);
		
		List<Integer> actualIds = actual.stream()
				.map(Book::getBookId)
				.collect(Collectors.toList());
		
		List<Integer> expectedIds = List.of(1, 3, 4);
		
		assertTrue(actualIds.containsAll(expectedIds));
	}
}
