package com.ss.lms2;

import com.ss.lms2.view.*; 
import com.ss.lms2.controller.*;

public class App {

	public static void main(String[] args) {

		AppMenu appMenu = new AppMenu();

		LibraryView libraryView = new LibraryView();
		BorrowerView borrowerView = new BorrowerView();


		LibraryController libraryController = new LibraryController(
				libraryView, appMenu);

		BorrowerController borrowerController = new BorrowerController(
				borrowerView, appMenu);

		//TODO: implement & initialize AppMenuController
		
		
		
		libraryView.setDelegate(libraryController);
		borrowerView.setDelegate(borrowerController);
		
		
		appMenu.show();
	}
}
