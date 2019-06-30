package com.notes.notesApp.utils;

public class PasswordChecker {
	
	public static boolean isThereANumber(String password) {
		int numberCount = 0;
		for(int i=0; i<password.length();i++) {
			if(Character.isDigit(password.charAt(i))) {
				numberCount ++;
			}
		}
		if(numberCount > 0) {
			return true;
		}
		else {
			return false;
		}
	}
	public static boolean isThereAnUpperCase(String password) {
		int upperCaseCount = 0;
		for(int i=0;i<password.length();i++) {
			if(Character.isUpperCase(password.charAt(i))) {
				upperCaseCount ++;
			}
		}
		if(upperCaseCount > 0) {
			return true;
		}
		else {
			return false;
		}
	}	
	
}
