package com.ss.lms2.view;

import java.util.List;


import com.ss.lms2.pojo.*;

public class LibraryView implements View {
	
	public interface Delegate {
		public void onChooseToEnterBranch();
		public void onChooseAddCopies(LibraryBranch branch);
		public void onSelectBookToAddCopies(LibraryBranch branch, Book book);
		public void onUpdate(LibraryBranch branch);
		public void onUpdate(BookCopies copies);
		public void onIntroQuit();
	}
	
	private Delegate delegate = null;
	
	public void setDelegate(Delegate delegate) {
		this.delegate = delegate;
	}
	
	public void showUpdateSuccess(LibraryBranch branch) {
		System.out.println("Successfully updated");
		showBranchMenu(branch);
	}

	
	//LIB1
	public void showIntro() {
		
		String display = "1) Enter the branch you manage\n" +
				"2) Quit to previous";
		
		System.out.println(display);
		
		int num = View.nextInt();
		
		switch(num) {
		case 1:
			delegate.onChooseToEnterBranch();
			break;
		case 2:
			delegate.onIntroQuit();	
			break;
		}
	}
	
	//LIB2
	public void showBranchList(List<LibraryBranch> branches) {
		
		int size = branches.size();
		String branchText = getBranchListDisplay(branches);
		
	
		System.out.print(branchText);
		System.out.println(size + 1 + ") Quit to previous");
		System.out.println("Enter your branch:");
		
		int index = View.nextInt() - 1;
		
		if(index == size) {
			showIntro();
			return;
		}
		
		LibraryBranch branch = branches.get(index);
		
		showBranchMenu(branch);
	}
	
	//LIB3
	public void showBranchMenu(LibraryBranch branch) {
		
		String display = "1) Update the details of the library\n" +
				"2) Add copies of a book to the branch\n" +
				"3) Quit to previous\n";
		
		System.out.println(display);
		
		
		int num = View.nextInt();
		
		switch(num) {
		case 1:
			showUpdate(branch);
			break;
		case 2:
			delegate.onChooseAddCopies(branch);
			break;
		case 3:
			delegate.onChooseToEnterBranch();
		}
	}
	
	public void showUpdate(LibraryBranch branch) {
		
		System.out.println("You have chose to update the branch " + 
				"with branch id: " + branch.getBranchId() + " and branch " +
				"name: " + branch.getBranchName());
		
		System.out.println("Enter 'quit' at any prompt to cancel the operation");
	
		System.out.println("Please enter a new branch name or enter " + 
				"N/A for no change:");
		
		String newName = View.nextLine();
		
		switch(newName) {
		case "N/A":
			newName = branch.getBranchName();
			break;
		case "quit":
			showBranchMenu(branch);
			return;
		}
		

		System.out.println("Please enter a new branch address or enter " + 
				"N/A for no change:");
		
		String newAddress = View.nextLine();
		
		switch(newAddress) {
		case "N/A":
			newAddress = branch.getBranchAddress();
			break;
		case "quit":
			showBranchMenu(branch);
			return;
		}
		
		LibraryBranch newBranch = new LibraryBranch(
				branch.getBranchId(), 
				newName, 
				newAddress);
		
		delegate.onUpdate(newBranch);
	}
	
	public void showAddCopies(LibraryBranch branch, List<Book> books) {
		
		int size = books.size();
		String listText = getBookListDisplay(books);
		
		System.out.println("Pick the book you want to add copies of to your branch:\n");
		System.out.print(listText);
		System.out.println(size + 1 + ") Quit to cancel operation");
		
		int index = View.nextInt() - 1;
		
		if(index == size) {
			showBranchMenu(branch);
			return;
		}
		
		Book book = books.get(index);
		
		delegate.onSelectBookToAddCopies(branch, book);
	}
	
	//Does not allow inserting less copies
	public void showEnterNumberOfCopies(BookCopies currentCopies) {
		
		int currentNoCopies = currentCopies.getNoOfCopies();
		
		System.out.println("Existing number of copies: " + currentNoCopies);
		System.out.println();
		System.out.println("Enter new number of copies:");
		
		int newNoOfCopies = View.nextInt();
		
		//Restart the prompt if the user enters invalid number of copies
		if(newNoOfCopies < currentNoCopies) {
			System.out.print("Number of copies must be at least the existing number, ");
			System.out.println("Please Try again");
			showEnterNumberOfCopies(currentCopies);
			return;
		}
		
		Book book = currentCopies.getBook();
		LibraryBranch branch = currentCopies.getBranch();
		
		BookCopies newCopies = new BookCopies(book, branch, newNoOfCopies);
		
		delegate.onUpdate(newCopies);
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