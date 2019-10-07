package com.ss.lms2.service;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.ss.lms2.dao.*;
import com.ss.lms2.pojo.*;

public class LibraryBranchService {

	private LibraryBranchDao branchDao;
	private BookLoanDao loanDao;
	
	
	private static LibraryBranchService service = new LibraryBranchService(
			LibraryBranchDao.getDao(),
			BookLoanDao.getDao()
			);
	
	public static LibraryBranchService getService() {
		return service;
	}
	
	private LibraryBranchService(LibraryBranchDao branchDao, BookLoanDao loanDao) {
		this.branchDao = branchDao;
		this.loanDao = loanDao;
	}

	//Utility function that can be passed to a filter
	static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object,Boolean> visited = new HashMap<>();
        return t -> visited.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
	} 
	
	public List<LibraryBranch> getAll() throws SQLException {
		return branchDao.getAll();
	}
	
	public Optional<LibraryBranch> get(int branchId) throws SQLException {
		return branchDao.get(branchId);
	}
	
	public void delete(LibraryBranch branch) throws SQLException {
		branchDao.delete(branch);
	}
	
	public void insert(LibraryBranch branch) throws SQLException {
		branchDao.insert(branch);
	}
	
	public void update(LibraryBranch branch) throws SQLException {
		branchDao.update(branch);
	}
	
	//Returns all libraries the borrower has at least 1 book checked out at
	public List<LibraryBranch> getUsedLibraries(Borrower borrower) 
			throws SQLException {
		
		return loanDao.getByBorrower(borrower)
				.stream()
				.map(BookLoan::getBranch)
				.filter(distinctByKey(LibraryBranch::getBranchId))
				.collect(Collectors.toList());
	}
}
