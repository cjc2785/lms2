package com.ss.lms2.service;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import com.ss.lms2.pojo.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LibraryBranchServiceTest {
	
	private final LibraryBranchService service = LibraryBranchService.getService();
	
	@BeforeEach
	public void setUp() throws SQLException  {
		TestUtils.populateTestDb();
	}

	
	@Test
	void getAllShouldReturn5Branches() throws SQLException {
		
		List<LibraryBranch> branches = service.getAll();
		
		int actual = branches.size();
		
		assertEquals(5, actual);
	}

	@Test
	void updateShouldChangeTheBranchName() throws SQLException {
		
		LibraryBranch branch = new LibraryBranch(
				3, "new dc", "1 dc ave");
		
		
		service.update(branch);
		
		
		String actual = service.get(3).get().getBranchName();
		
		assertEquals(actual, "new dc");
	}
	
	@Test
	void getUsedLibrariesShouldReturnLibrariesUsedByDan() 
		throws SQLException {
		
		Borrower dan = new Borrower(
				2, "dan", "1 dan street", "222-222-2222");
		
		
		List<LibraryBranch> actual = service.getUsedLibraries(dan);
		
		List<Integer> actualIds = actual.stream() 
				.map(LibraryBranch::getBranchId)
				.collect(Collectors.toList());
		
		int actualSize = actualIds.size();
		
		List<Integer> expectedIds = List.of(1, 2, 3);
		
		assertEquals(actualSize, 3);
		assertTrue(actualIds.containsAll(expectedIds));
	}
}
