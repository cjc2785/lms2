package com.ss.lms2.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.ss.lms2.pojo.*;
import com.ss.lms2.service.*;
import com.ss.lms2.view.*;

public class LibraryController implements LibraryView.Delegate {
	
	LibraryView view;
	AppMenu appMenu;
	LibraryBranchService branchService;
	BookService bookService;
	BookCopiesService copiesService;

	@Override
	public void onChooseToEnterBranch() {
		
		try {
			List<LibraryBranch> branches = branchService.getAll();
			view.showBranchList(branches);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void onChooseAddCopies(LibraryBranch branch) {
		
		try {
			List<Book> books = bookService.getAll();
			view.showAddCopies(branch, books);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void onUpdate(LibraryBranch branch) {
		
		try {
			branchService.update(branch);
			view.showUpdateSuccess(branch);
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}

	@Override
	public void onUpdate(BookCopies copies) {
		try {
			copiesService.update(copies);
			view.showUpdate(copies.getBranch());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}

	@Override
	public void onIntroQuit() {
		appMenu.show();
	}

	@Override
	public void onSelectBookToAddCopies(LibraryBranch branch, Book book) {
		
		try {
			Optional<BookCopies> optCopies = copiesService.get(branch, book);
			
			BookCopies copies = optCopies.isPresent() ? 
					optCopies.get() : 
					new BookCopies(book, branch, 0);
					
			view.showEnterNumberOfCopies(copies);
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
