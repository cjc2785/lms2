package com.ss.lms2.view;

import java.util.List;


import com.ss.lms2.pojo.*;

public class LibraryView {
	
	public interface Delegate {
		public void onEnterBranch();
		public void onIntroQuit();
	}
	
	private Delegate delegate = null;
	
	public void setDelegate(Delegate delegate) {
		this.delegate = delegate;
	}
	
	//LIB1
	public void showIntro() {
		
		String display = "1) Enter branch you manage\n" +
				"2) Quit to previous";
		
		System.out.println(display);
		
		int num = View.nextInt();
		
		switch(num) {
		case 0:
			delegate.onEnterBranch();
			break;
		case 1:
			delegate.onIntroQuit();	
		}
	}
	
	//LIB2
	public void showBranchSelect(List<LibraryBranch> branches) {
		
		
		
		
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