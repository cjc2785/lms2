package com.ss.lms2;

import com.ss.lms2.view.*;

import java.sql.SQLException;

import com.ss.lms2.controller.*;
import com.ss.lms2.service.*;

public class App {

	public static void main(String[] args) throws SQLException {

		AppMenu appMenu = new AppMenu();

		LibraryView libraryView = new LibraryView();
		BorrowerView borrowerView = new BorrowerView();
		AdminView adminView = new AdminView();

		LibraryController libraryController = new LibraryController(
				libraryView, appMenu);

		BorrowerController borrowerController = new BorrowerController(
				borrowerView, appMenu);
		
		AdminController adminController = new AdminController
				(adminView, appMenu);
				

		//TODO: implement & initialize AppMenuController
		
		
		
		libraryView.setDelegate(libraryController);
		borrowerView.setDelegate(borrowerController);
		adminView.setDelegate(adminController);
		
		//Use this to setup the test db
		TestUtils.populateTestDb();
	
	//	adminView.showIntro();
		//borrowerView.showIntro();
	}
}
