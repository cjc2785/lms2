package com.ss.lms2.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.ss.lms2.pojo.*;
import com.ss.lms2.service.*;
import com.ss.lms2.view.*;

public class BorrowerController implements BorrowerView.Delegate {
	
	BorrowerView view;
	AppMenu appMenu;
	LibraryBranchService branchService;
	BookService bookService;
	BookLoanService loanService;
	BorrowerService borrowerService;
	
	
	@Override
	public void onChooseCheckout(Borrower borrower) {
		try {
			List<LibraryBranch> branches = branchService.getAll();
			view.showCheckoutBranchList(borrower, branches);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public void onChooseBookReturn(Borrower borrower) {
		try {
			List<LibraryBranch> branches = branchService.getUsedLibraries(borrower);
			view.showReturnBranchList(borrower, branches);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	@Override
	public void onCardNumberInput(int cardNo) {
		try {
			Optional<Borrower> optBorrower = borrowerService.get(cardNo);
			if(optBorrower.isEmpty()) {
				view.showInvalidCardNumber();
				return;
			}
			view.showMain(optBorrower.get());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	@Override
	public void onCheckoutBranchSelect(Borrower borrower, LibraryBranch branch) {
		try {
			List<Book> books = bookService.getAvailable(branch);
			view.showCheckoutBookList(borrower, branch, books);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	@Override
	public void onReturnBranchSelect(Borrower borrower, LibraryBranch branch) {
		try {
			List<BookLoan> loans = loanService.getByBorrower(borrower, branch);
			view.showReturnBookList(borrower, loans);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	@Override
	public void onCheckout(BookLoan loan) {
		try {
			loanService.insertLoan(loan);
			view.showUpdateSuccess(loan.getBorrower());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}
	
	@Override
	public void onReturn(BookLoan loan) {
		try {
			loanService.deleteLoan(loan);
			view.showUpdateSuccess(loan.getBorrower());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}	
	}
	
	@Override
	public void onMainQuit() {
		appMenu.show();
	}
}
