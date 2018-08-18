package com.qc.magic.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateUtils {

	final static String RULE_EMAIL = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$"; 
	final static Integer MIN_LENGTH=10;
	
	public static boolean isEamil(String email) {
		Pattern p = Pattern.compile(RULE_EMAIL);
		Matcher m = p.matcher(email);
		return m.matches();
	}

	public static boolean isLengthEnough(String password) {
		return password.length() >= MIN_LENGTH;
	}
}
