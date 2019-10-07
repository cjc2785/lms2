package com.ss.lms2.controller;

import java.sql.SQLException;
import com.ss.lms2.pojo.*;
import com.ss.lms2.service.*;
import com.ss.lms2.view.*;

public class AdminController implements AdminView.Delegate {

	private AdminView view;
	private AppMenu appMenu;
	private LibraryBranchService branchService = LibraryBranchService.getService();
	private BookService bookService = BookService.getService();
	private BorrowerService borrowerService = BorrowerService.getService();
	private AuthorService authorService = AuthorService.getService();
	private PublisherService publisherService = PublisherService.getService();
	private BookLoanService bookLoanService = BookLoanService.getService();

	
	public AdminController(AdminView view, AppMenu appMenu) {
		this.view = view;
		this.appMenu = appMenu;
	}
	
	@Override
	public void onIntroQuit() {
		appMenu.showIntro();
	}

	@Override
	public void insertAuthor(Author author) {
		try {
			boolean exists = authorService.get(author.getAuthorId()).isPresent();
			if (exists) {
				view.showDuplicateInsertError("Author");
				return;
			}
				authorService.insert(author);
				view.showOperationSuccess();
					
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public void updateAuthor(Author author) {
		try {
		authorService.update(author);
		view.showOperationSuccess();
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void deleteAuthor(Author author) {
		try {
		authorService.delete(author);
		view.showOperationSuccess();
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public void insertPublisher(Publisher publisher) {
		try {
			
			boolean exists = publisherService.get(publisher.getPublisherId()).isPresent();
			if (exists) {
				view.showDuplicateInsertError("Publisher");
				return;
			}
				publisherService.insert(publisher);
				view.showOperationSuccess();
				
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public void updatePublisher(Publisher publisher) {
		try {
		publisherService.update(publisher);
		view.showOperationSuccess();
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}

	}
	
	@Override
	public void deletePublisher(Publisher publisher) {
		try {
			publisherService.delete(publisher);
			view.showOperationSuccess();
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void insertBranch(LibraryBranch branch) {
		try {
			
			boolean exists = branchService.get(branch.getBranchId()).isPresent();
			if (exists) {
				view.showDuplicateInsertError("Branch");
				return;
			}
				branchService.insert(branch);
				view.showOperationSuccess();
				
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public void updateBranch(LibraryBranch branch) {
		try {
		branchService.update(branch);
		view.showOperationSuccess();
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public void deleteBranch(LibraryBranch branch) {
		try {
			branchService.delete(branch);
			view.showOperationSuccess();
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public void insertBorrower(Borrower borrower) {
		try {
		
			boolean exists = borrowerService.get(borrower.getCardNo()).isPresent();
			if (exists) {
				view.showDuplicateInsertError("Borrower");
				return;
			}
				borrowerService.insert(borrower);
				view.showOperationSuccess();
				
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public void updateBorrower(Borrower borrower) {
		try {
		borrowerService.update(borrower);
		view.showOperationSuccess();
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}

	}
	
	@Override
	public void deleteBorrower(Borrower borrower) {
		try {
		borrowerService.delete(borrower);
		view.showOperationSuccess();
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public void updateBook(Book book) {
		try {
		
			boolean authorExists = authorService.get(book.getAuthor().getAuthorId()).isPresent();
			if (!authorExists) {
				view.showBookRelationDeosNotExistError("Author");
				return;
			}
			boolean publisherExists = publisherService.get(book.getPublisher().getPublisherId()).isPresent();
			if (!publisherExists) {
				view.showBookRelationDeosNotExistError("Publisher");
				return;
			}
				bookService.update(book);
				view.showOperationSuccess();

		}catch(SQLException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public void deleteBook(Book book) {
		try {
		bookService.delete(book);
		view.showOperationSuccess();
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public void insertBook(Book book) {
		try {
		boolean exists = bookService.get(book.getBookId()).isPresent();
		if (exists) {
			view.showDuplicateInsertError("Book");
			return;
		}
		boolean authorExists = authorService.get(book.getAuthor().getAuthorId()).isPresent();
		if (!authorExists) {
			view.showBookRelationDeosNotExistError("Author");
			return;
		}
		boolean publisherExists = publisherService.get(book.getPublisher().getPublisherId()).isPresent();
		if (!publisherExists) {
			view.showBookRelationDeosNotExistError("Publisher");
			return;
		}
			bookService.insert(book);
			view.showOperationSuccess();
			
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}

	}
	
	@Override
	public void updateBookLoan(BookLoan loan) {
		try {
		bookLoanService.updateDueDate(loan);
		view.showOperationSuccess();
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}

	}

}