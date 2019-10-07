package main;

import com.ss.lms2.view.*;

import java.sql.SQLException;

import com.ss.lms2.controller.*;

//Use this to setup the test db
import com.ss.lms2.service.TestUtils;


public class App {

	public static void main(String[] args) throws SQLException {

		AppMenu appMenu = new AppMenu();
		LibraryView libraryView = new LibraryView();
		BorrowerView borrowerView = new BorrowerView();
		AdminView adminView = new AdminView();
		
		
		AppMenuController appMenuController = new AppMenuController(
				libraryView, borrowerView, adminView);
		
		LibraryController libraryController = new LibraryController(
				libraryView, appMenu);

		BorrowerController borrowerController = new BorrowerController(
				borrowerView, appMenu);
		
		AdminController adminController = new AdminController(
				adminView, appMenu);
		
		
		appMenu.setDelegate(appMenuController);
		libraryView.setDelegate(libraryController);
		borrowerView.setDelegate(borrowerController);
		adminView.setDelegate(adminController);
		
		//Use this to setup the test db
		TestUtils.populateTestDb();
	
		appMenu.showIntro();
	}
}
