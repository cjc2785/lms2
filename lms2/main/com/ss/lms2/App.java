package com.ss.lms2;

import com.ss.lms2.view.*;

import java.sql.SQLException;

import com.ss.lms2.controller.*;
//import com.ss.lms2.service.*;

public class App {

	public static void main(String[] args) throws SQLException {

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
		
		//Use this to setup the test db
		//TestUtils.populateTestDb();
	
		//appMenu.show();
	}
}
