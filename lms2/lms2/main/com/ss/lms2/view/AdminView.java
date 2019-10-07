package com.ss.lms2.view;

import java.time.LocalDate;
import com.ss.lms2.pojo.*;

public class AdminView {
	
	public interface Delegate {		
		public void insertAuthor(Author author);
		public void updateAuthor(Author author);
		public void deleteAuthor(Author author);
		public void insertPublisher(Publisher publisher);
		public void updatePublisher(Publisher publisher);
		public void insertBranch(LibraryBranch branch);
		public void updateBranch(LibraryBranch branch);
		public void deleteBranch(LibraryBranch branch);
		public void insertBorrower(Borrower borrower);
		public void updateBorrower(Borrower borrower);
		public void updateBook(Book book);
		public void deleteBook(Book book);
		public void insertBook(Book book);
		public void updateBookLoan(BookLoan loan);
		public void onIntroQuit();
		public void deletePublisher(Publisher publisher);
	}
	
	private Delegate delegate = null;
	
	public void setDelegate(Delegate delegate) {
		this.delegate = delegate;
	}
	
	//ADMIN1
	public void showIntro() {
		
		String display = "1)	Add/Update/Delete Author\n"
				+ "2)	Add/Update/Delete Book\n"
				+ "3)	Add/Update/Delete Publishers\n"
				+ "4)	Add/Update/Delete Library Branches\n"
				+ "5)	Add/Update/Delete Borrowers\n"
				+ "6)	Over-ride Due Date for a Book Loan\n"
				+ "7)	Quit";
		
		System.out.println(display);
		
		int num = View.nextInt();
		
	//Admin CRUD Menu
		switch(num) {
		case 1:
			showAuthorMenu();
			break;
		case 2:
			showBookMenu();
			break;
		case 3:
			showPublisherMenu();	
			break;
		case 4:
			showBranchMenu();
			break;
		case 5:
			showBorrowerMenu();
			break;
		case 6:
			showLoanUpdate();
			break;
		case 7:
			delegate.onIntroQuit();
			break;
		}
	}
	
	//Duplicate Check Error
	public void showDuplicateInsertError(String entity) {
		System.out.println("This " + entity + " already exists!!");
		showIntro();
	}
	
	//Relationship Error
	public void showBookRelationDeosNotExistError(String entity) {
		System.out.println("This " + entity + " does not exist!!");
		showIntro();
	}
	
	//Operation Success
	public void showOperationSuccess(){
		System.out.println("Operation Successful!!");
		showIntro();
	}
	
	//ADMIN2
	//AuthorMenu
	public void showAuthorMenu() {
		
		String display = "Choose an action number: \n" +
				 "1: Insert an author\n" + 
				 "2: Update an author\n" +
				 "3: Delete an author\n";
		
		System.out.println(display);
		int num = View.nextInt();
		
		switch(num) {
		case 1:
			showAuthorInsert();	
			break;
		case 2:
			showAuthorUpdate();
			break;
		case 3:
			showAuthorDelete();
			break;
		}
	}
	
	//BookMenu
	public void showBookMenu() {
		
		String display = "Choose an action number: \n" +
				 "1: Insert a book\n" + 
				 "2: Update a book\n" +
				 "3: Delete a book\n";
		
		System.out.println(display);
		int num = View.nextInt();
		
		switch(num) {
		case 1:
			showBookInsert();	
			break;
		case 2:
			showBookUpdate();
			break;
		case 3:
			showBookDelete();
			break;
		}
	}

	//PublisherMenu
	public void showPublisherMenu() {
		
		String display = "Choose an action number: \n" +
				 "1: Insert a publisher\n" + 
				 "2: Update a publisher\n" +
				 "3: Delete a publisher\n";
		
		System.out.println(display);
		int num = View.nextInt();
		
		switch(num) {
		case 1:
			showPublisherInsert();	
			break;
		case 2:
			showPublisherUpdate();
			break;
		case 3:
			showPublisherDelete();
			break;
		}
	}
	
	//BorrowerMenu
	public void showBorrowerMenu() {
		
		String display = "Choose an action number: \n" +
				 "1: Insert a borrower\n" + 
				 "2: Update a borrower\n" +
				 "3: Delete a borrower\n";
		
		System.out.println(display);
		int num = View.nextInt();
		
		switch(num) {
		case 1:
			showBorrowerInsert();	
			break;
		case 2:
			showBorrowerUpdate();
			break;
		case 3:
			showBorrowerDelete();
			break;
		}
	}
	
	//BranchMenu
	public void showBranchMenu() {
		
		String display = "Choose an action number: \n" +
				 "1: Insert a branch\n" + 
				 "2: Update a branch\n" +
				 "3: Delete a branch\n";
		
		System.out.println(display);
		int num = View.nextInt();
		
		switch(num) {
		case 1:
			showBranchInsert();	
			break;
		case 2:
			showBranchUpdate();
			break;
		case 3:
			showBranchDelete();
			break;
		}
	}	


	//Update Book Loan
	public void showLoanUpdate() {
		
			System.out.println("Enter a Book Id: ");
			
				int BookId = View.nextInt();
			
			System.out.println("Enter a Branch Id: ");
				
				int BranchId = View.nextInt();
				
			System.out.println("Enter a CardNo: ");
				
				int CardNo = View.nextInt();
				
			System.out.println("Enter a New DueDate(YYYY-MM-DD): ");
				String dateString = View.nextLine();
				LocalDate date = LocalDate.parse(dateString);
			
			LibraryBranch branch = new LibraryBranch(BranchId, null, null);
			Borrower borrower =  new Borrower(CardNo, null, null, null);
			Book book = new Book(BookId, null, null, null);
			BookLoan loan = new BookLoan(book, branch, borrower, null, date);
			
			delegate.updateBookLoan(loan);
		}
	
	//Book
	
	public void showBookInsert() {
		System.out.println("Enter a Book Id: ");
		
			int BookId = View.nextInt();
	
		System.out.println("Enter a Book Title: ");
	
			String Title = View.nextLine();
		
		System.out.println("Enter a Author Id: ");
		
			int AuthorId = View.nextInt();
		
		System.out.println("Enter a Publisher Id: ");
		
			int PublisherId = View.nextInt();
	
		Author author = new Author(AuthorId, null);	
	
		Publisher publisher = new Publisher (PublisherId, null, null, null);
			
		Book book = new Book(BookId, Title, author, publisher);
	
	delegate.insertBook(book);
	
		}
		
	public void showBookUpdate() {
		
		System.out.println("Enter a Book Id: ");
			
				int BookId = View.nextInt();
			
			System.out.println("Enter a New Book Title: ");
			
				String Title = View.nextLine();
				
			System.out.println("Enter a New Author Id: ");
				
				int AuthorId = View.nextInt();
				
			System.out.println("Enter a New Publisher Id: ");
				
				int PublisherId = View.nextInt();
			
			Author author = new Author(AuthorId, null);	
			
			Publisher publisher = new Publisher (PublisherId, null, null, null);
				
			Book book = new Book(BookId, Title, author, publisher);
			
			delegate.updateBook(book);
			
		}
	
	public void showBookDelete() {

		System.out.println("Enter a Book ID: ");
		
			int Id = View.nextInt();
		
		Book book = new Book(Id, null, null, null);
		
		delegate.deleteBook(book);
	}
	
	//Borrower
		
	public void showBorrowerInsert() {
		System.out.println("Enter Borrower Card No: ");
		
			int CardNo = View.nextInt();
		
		System.out.println("Enter Borrower Name: ");
		
			String Name = View.nextLine();
			
		System.out.println("Enter Borrower Address: ");
			
			String Address = View.nextLine();
			
		System.out.println("Enter Borrower Phone Number: ");
			
			String Phone = View.nextLine();
		
		Borrower borrower = new Borrower(CardNo, Name, Address, Phone);
		
		delegate.insertBorrower(borrower);
		
	}
	
	public void showBorrowerUpdate() {
		
		System.out.println("Enter Borrower Card No: ");
		
			int CardNo = View.nextInt();
	
		System.out.println("Enter a New Borrower Name: ");
	
			String Name = View.nextLine();
		
		System.out.println("Enter a New Borrower Address: ");
		
			String Address = View.nextLine();
		
		System.out.println("Enter a New Borrower Phone Number: ");
		
			String Phone = View.nextLine();
	
		Borrower borrower = new Borrower(CardNo, Name, Address, Phone);
	
	delegate.updateBorrower(borrower);
	}
		
	public void showBorrowerDelete() {
		System.out.println("Enter an CardNo: ");
		
			int CardNo = View.nextInt();
		
		Borrower borrower = new Borrower (CardNo, null, null, null);
		
		delegate.updateBorrower(borrower);
		
	}
	
	//Branch
	
	public void showBranchInsert() {
		System.out.println("Enter Brnach ID: ");
		
			int Id = View.nextInt();
		
		System.out.println("Enter Branch Name: ");
		
			String Name = View.nextLine();
			
		System.out.println("Enter Branch Address: ");
			
			String Address = View.nextLine();
			
		LibraryBranch branch = new LibraryBranch(Id, Name, Address);
		
		delegate.insertBranch(branch);
		
	}
	
	public void showBranchUpdate() {
		
		System.out.println("Enter Branch ID: ");
		
			int Id = View.nextInt();
	
		System.out.println("Enter a New Branch Name: ");
	
			String Name = View.nextLine();
		
		System.out.println("Enter a New Branch Address: ");
		
			String Address = View.nextLine();
		
	LibraryBranch branch = new LibraryBranch(Id, Name, Address);
	
	delegate.updateBranch(branch);
	}
		
	public void showBranchDelete() {
		System.out.println("Enter an ID: ");
		
			int Id = View.nextInt();
		
		LibraryBranch branch = new LibraryBranch (Id, null, null);
		
		delegate.deleteBranch(branch);
		
	}
	
	//Publisher
	
	public void showPublisherInsert() {
		System.out.println("Enter Publisher ID: ");
		
			int Id = View.nextInt();
		
		System.out.println("Enter Publisher Name: ");
		
			String Name = View.nextLine();
			
		System.out.println("Enter Publisher Address: ");
			
			String Address = View.nextLine();
			
		System.out.println("Enter Publisher Phone Number: ");
			
			String Phone = View.nextLine();
		
		Publisher publisher = new Publisher(Id, Name, Address, Phone);
		
		delegate.insertPublisher(publisher);
		
	}
	
	public void showPublisherUpdate() {
		
		System.out.println("Enter Publisher ID: ");
		
			int Id = View.nextInt();
	
		System.out.println("Enter a New Publisher Name: ");
	
			String Name = View.nextLine();
		
		System.out.println("Enter a New Publisher Address: ");
		
			String Address = View.nextLine();
		
		System.out.println("Enter a New Publisher Phone Number: ");
		
			String Phone = View.nextLine();
	
	Publisher publisher = new Publisher(Id, Name, Address, Phone);
	
	delegate.updatePublisher(publisher);
	}
		
	public void showPublisherDelete() {
		System.out.println("Enter an ID: ");
		
			int Id = View.nextInt();
		
		Publisher publisher = new Publisher (Id, null, null, null);
		
		delegate.deletePublisher(publisher);
		
	}
	
	//Author
	
	public void showAuthorInsert() {
		System.out.println("Enter an ID: ");
		
			int Id = View.nextInt();
		
		System.out.println("Enter a Name: ");
		
			String Name = View.nextLine();
		
		Author author = new Author(Id, Name);
		
		delegate.insertAuthor(author);
		
	}
	
	public void showAuthorUpdate() {
		System.out.println("Enter an ID: ");
		
			int Id = View.nextInt();
		
		System.out.println("Enter a New Name: ");
		
			String Name = View.nextLine();
		
		Author author = new Author(Id, Name);
		
		delegate.updateAuthor(author);
		
	}
		
	public void showAuthorDelete() {
		System.out.println("Enter an ID: ");
		
			int Id = View.nextInt();
		
		Author author = new Author(Id, "");
		
		delegate.deleteAuthor(author);
		
	}
	
}
