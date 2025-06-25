package com.finalProject.util;

public class SmsCodeHolder {
	private static String identifierLetter; // 英文
	private static String verificationCode; // 數字

	public static void set(String id, String code) {
		identifierLetter = id;
		verificationCode = code;
	}

	public static String getIdentifier() {
		return identifierLetter;
	}

	public static String getCode() {
		return verificationCode;
	}
}