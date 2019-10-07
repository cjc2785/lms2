package com.ss.lms2.view;

public class AppMenu implements View {
	
	public interface Delegate {
		public void onRoleSelect(int num);
	}
	
	private Delegate delegate = null;


	public void setDelegate(Delegate delegate) {
		this.delegate = delegate;
	}


	public void showIntro() {
		//TODO: implement
		System.out.println("Welcome to the GCIT Library Management System.");
		System.out.println("Which category of a user are you?");
		
		System.out.println("1) Librarian");
		System.out.println("2) Administrator");
		System.out.println("3) Borrower");
		
		int num = View.nextInt();
		
		delegate.onRoleSelect(num);
	}
}
