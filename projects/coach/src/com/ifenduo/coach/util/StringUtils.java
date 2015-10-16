package com.ifenduo.coach.util;

public class StringUtils {
	public static String removeDot(String str) {
		str = str.replace(".", "-");
		str = str.replace("/", "-");
		str = str.replace(":", "-");
		return str;
	}
}
