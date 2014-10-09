package com.vinfotech.utility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Util class for validating i/p
 * 
 * @author kuldips
 * 
 */
public class ValidationUtil {
	private static final String EMAIL_PATTERN = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
	private static final String USERNAME_PATTERN = "^[a-z0-9_-]{3,15}$";
	private static final String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,20})";

	/**
	 * Validates email with regular expression
	 * 
	 * @param email
	 *            the email to validate
	 * @return true if email is valid, false otherwise
	 */
	public static boolean isValidEmail(String email) {
		Pattern pattern = Pattern.compile(EMAIL_PATTERN);
		Matcher matcher = pattern.matcher(email);

		return matcher.matches();
	}

	/**
	 * Validates user name with regular expression
	 * 
	 * @param username
	 *            the username to validate
	 * @return true if user name is valid, false otherwise
	 */
	public static boolean isValidUsername(String username) {
		Pattern pattern = Pattern.compile(USERNAME_PATTERN);
		Matcher matcher = pattern.matcher(username);

		return matcher.matches();
	}

	public static boolean isValidFirstname(String username) {
		if (username.toString().matches("[a-zA-Z ]+") && !username.equals("") && username.length() > 1)
			return true;
		else
			return false;
	}

	/**
	 * Validate password with regular expression
	 * 
	 * @param password
	 *            password for validation
	 * @return true valid password, false invalid password
	 */
	public static boolean isValidPassword(final String password) {

		Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
		Matcher matcher = pattern.matcher(password);
		return matcher.matches();

	}

}
