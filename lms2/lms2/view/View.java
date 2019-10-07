package com.ss.lms2.view;

import java.util.Scanner;

public class View {

	public static Scanner scanner = new Scanner(System.in);
	

	// Views should call View.nextInt() and View.nextLine()
	//  to avoid instantiating a new scanner
	
	public static int nextInt() {
		
		int num = scanner.nextInt();
		
		// Calls nextLine() as nextInt() does not read 
		//  the newline after the user presses enter
		scanner.nextLine();
		return num;
	}
	
	public static String nextLine() {
		String next = scanner.nextLine();
		return next;
	}
}
