package com.ss.lms2.view;

import java.util.List;

import com.ss.lms2.pojo.*;

public class AdminView {
	
	public interface Delegate {
		public void crudBookAuthor();
		public void crudPublisher();
		public void crudLibraryBranch();
		public void crudBorrower();
		public void crudBookLoan();
		
		public void onChooseToEnterBranch();
		public void onChooseAddCopies(LibraryBranch branch);
		public void onSelectBookToAddCopies(LibraryBranch branch, Book book);
		public void onUpdate(LibraryBranch branch);
		public void onUpdate(BookCopies copies);
		
		public void onChooseCheckout(Borrower borrower);
		public void onChooseBookReturn(Borrower borrower);
		public void onCardNumberInput(int cardNo);
		public void onCheckoutBranchSelect(Borrower borrower, LibraryBranch branch);
		public void onReturnBranchSelect(Borrower borrower, LibraryBranch branch);
		public void onCheckout(BookLoan loan);
		public void onReturn(BookLoan loan);
		
		public void onIntroQuit();
	}
	
	private Delegate delegate = null;
	
	public void setDelegate(Delegate delegate) {
		this.delegate = delegate;
	}
	
	//ADMIN1
	public void showIntro() {
		
		String display = "1)	Add/Update/Delete Book and Author\n"
				+ "2)	Add/Update/Delete Publishers\n"
				+ "3)	Add/Update/Delete Library Branches\n"
				+ "4)	Add/Update/Delete Borrowers\n"
				+ "5)	Over-ride Due Date for a Book Loan";
		
		System.out.println(display);
		
		int num = View.nextInt();
		
	//Admin CRUD Menu
		switch(num) {
		case 0:
			delegate.crudBookAuthor();
			break;
		case 1:
			delegate.crudPublisher();	
			break;
		case 2:
			delegate.crudLibraryBranch();
			break;
		case 3:
			delegate.crudBorrower();
			break;
		case 4:
			delegate.crudBookLoan();
			break;
		}
	}
	
	//ADMIN2
	public void crudBookAuthor(List<LibraryBranch> branches) {
			
	}
	
	public void show() {
		
		String display = "Choose an action number: \n" +
				 "1: Insert an author\n" + 
				 "2: Get an author\n" + 
				 "3: Get all authors\n" +
				 "4: Update an author\n" +
				 "5: Delete an author\n";
		
		System.out.println(display);
		int num = View.nextInt();
	
	}

}
