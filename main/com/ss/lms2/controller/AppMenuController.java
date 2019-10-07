package com.ss.lms2.controller;

import com.ss.lms2.view.*;

public class AppMenuController implements AppMenu.Delegate {
	
	private LibraryView librarianView;
	private BorrowerView borrowerView;
	private AdminView adminView;
	
	
	public AppMenuController(
			LibraryView librarianView,
			BorrowerView borrowerView,
			AdminView adminView
			) {
		this.librarianView = librarianView;
		this.borrowerView = borrowerView;
		this.adminView = adminView;
	}


	@Override
	public void onRoleSelect(int num) {
		getViews()[num - 1].showIntro();
	}
	
	
	private View[] getViews() {
		
		View[] views = {librarianView, adminView, borrowerView};
		return views;
	}
}