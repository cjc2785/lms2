package com.ss.lms2.view;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.ss.lms2.pojo.*;

public class BorrowerView implements View {
	
	public interface Delegate {
		public void onChooseCheckout(Borrower borrower);
		public void onChooseBookReturn(Borrower borrower);
		public void onCardNumberInput(int cardNo);
		public void onCheckoutBranchSelect(Borrower borrower, LibraryBranch branch);
		public void onReturnBranchSelect(Borrower borrower, LibraryBranch branch);
		public void onCheckout(BookLoan loan);
		public void onReturn(BookLoan loan);
		public void onMainQuit();
	}
	
	private Delegate delegate = null;
	
	public void setDelegate(Delegate delegate) {
		this.delegate = delegate;
	}
	
	public void showUpdateSuccess(Borrower borrower) {
		System.out.println("Operation successful");
		showMain(borrower);
	}
	

	public void showIntro() {
		
		System.out.println("Enter your Card Number");
		
		int cardNo = View.nextInt();
		
		delegate.onCardNumberInput(cardNo);
	}
	
	public void showInvalidCardNumber() {
		System.out.println("Invalid card number. Please try again.");
		showIntro();
	}
	
	//Borr1
	public void showMain(Borrower borrower) {
		
		System.out.println("1) Check out a book");
		System.out.println("2) Return a book");
		System.out.println("3) Quit to previous");
		
		int num = View.nextInt();
		
		switch(num) {
		case 1:
			delegate.onChooseCheckout(borrower);
			break;
		case 2:
			delegate.onChooseBookReturn(borrower);
			break;
		case 3:
			delegate.onMainQuit();
			break;
		}
	}

	
	public void showCheckoutBranchList(Borrower borrower, List<LibraryBranch> branches) {
		
		int size = branches.size();
		String branchText = getBranchListDisplay(branches);
		

		System.out.println("Pick the branch you want to check out from:");
		System.out.print(branchText);
		System.out.println(size + 1 + ") Quit to previous");
		
		int index = View.nextInt() - 1;
		
		if(index == size) {
			showMain(borrower);
			return;
		}
		
		LibraryBranch branch = branches.get(index);
		
		delegate.onCheckoutBranchSelect(borrower, branch);
	}
	
	public void showCheckoutBookList(Borrower borrower, LibraryBranch branch, List<Book> books) {
		
		int size = books.size();
		String bookText = getBookListDisplay(books);
		
		System.out.println("Pick the book you want to check out");
		System.out.print(bookText);
		System.out.println(size + 1 + ") Quit to cancel operation");
		
		int index = View.nextInt() - 1;
		
		if(index == size) {
			showMain(borrower);
			return;
		}
		
		Book book = books.get(index);
		
		LocalDate outDate = LocalDate.now();
		LocalDate dueDate = outDate.plusWeeks(1);
		
		
		BookLoan loan = new BookLoan(book, branch, borrower, outDate, dueDate);
		

		delegate.onCheckout(loan);
	}
	
	
	public void showReturnBranchList(Borrower borrower, List<LibraryBranch> branches) {
		
		int size = branches.size();
		String branchText = getBranchListDisplay(branches);
		
		System.out.println("Pick the branch you want to return a book to:\n");
		System.out.print(branchText);
		System.out.println(size + 1 + ") Quit to previous");
		
		int index = View.nextInt() - 1;
		
		if(index == size) {
			showMain(borrower);
			return;
		}
		
		LibraryBranch branch = branches.get(index);
		
		delegate.onReturnBranchSelect(borrower, branch);
	}
	
	public void showReturnBookList(Borrower borrower, List<BookLoan> loans) {
		
		List<Book> books = loans.stream() 
				.map(BookLoan::getBook)
				.collect(Collectors.toList());
		
		int size = books.size();
		String bookText = getBookListDisplay(books);
		
		System.out.println("Pick the book you want to return");
		System.out.print(bookText);
		System.out.println(size + 1 + ") Quit to cancel operation");
		
		int index = View.nextInt() - 1;
		
		if(index == size) {
			showMain(borrower);
			return;
		}
		
		BookLoan loan = loans.get(index);

		delegate.onReturn(loan);
	}
	
	
	
	private String getBranchListDisplay(List<LibraryBranch> branches) {
		
		int size = branches.size();
		String branchText = "";
		
		for(int i = 0; i < size; ++i) {
			
			LibraryBranch branch = branches.get(i);
			String name = branch.getBranchName();
			String address = branch.getBranchAddress();
			
			branchText += i + 1 + ") " + name + ", " + address + "\n"; 
		}
		return branchText;
	}
	
	private String getBookListDisplay(List<Book> books) {
		
		int size = books.size();
		String bookText = "";
		
		for(int i = 0; i < size; ++i) {
			
			Book book = books.get(i);
			String title = book.getTitle();
			String authorName = book.getAuthor().getAuthorName();
			
			bookText += i + 1 + ") " + title + " by " + authorName + "\n"; 
		}
		
		return bookText;
	}
}