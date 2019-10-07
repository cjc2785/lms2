package com.ss.lms2.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.ss.lms2.pojo.*;
import com.ss.lms2.service.*;
import com.ss.lms2.view.*;

public class LibraryController implements LibraryView.Delegate {
	
	private LibraryView view;
	private AppMenu appMenu;
	private final LibraryBranchService branchService = LibraryBranchService.getService();
	private final BookService bookService = BookService.getService();
	private final BookCopiesService copiesService = BookCopiesService.getService();

	public LibraryController(LibraryView view, AppMenu appMenu) {
		this.view = view;
		this.appMenu = appMenu;
	}
	
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
			view.showUpdateSuccess(copies.getBranch());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}

	@Override
	public void onIntroQuit() {
		appMenu.showIntro();
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
