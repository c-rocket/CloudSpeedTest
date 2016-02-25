package com.oracle.cloud.speedtest.util;

import java.util.Random;

public class StringUtil {
	private static Random random = new Random();

	public static Object random(int length) {
		char[] chars = "abcdefghijklmnopqrstuvwxyz".toCharArray();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < length; i++) {
			char c = chars[random.nextInt(chars.length)];
			sb.append(c);
		}
		return sb.toString();
	}

}
