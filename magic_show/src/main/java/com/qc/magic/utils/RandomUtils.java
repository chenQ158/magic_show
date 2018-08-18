package com.qc.magic.utils;

import java.util.Date;
import java.util.Random;

public class RandomUtils {

	final static String[] NAME_PREFIXS = {"Mr ","Mrs ","Mrss ","Miss"};
	final static char[] BIG_LETTERS = "ABCDEFGHIJLMNOPQRSTUVWXYZ".toCharArray();
	final static char[] SMALL_LETTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
	final static char[] DIGITALS = "0123456789".toCharArray();
	
	public static String getRandomName() {
		Random random = new Random();
		int num = random.nextInt(NAME_PREFIXS.length-1);
		String prefix = NAME_PREFIXS[num];
		num = random.nextInt(BIG_LETTERS.length);
		char head = BIG_LETTERS[num];
		StringBuffer name = new StringBuffer();
		for (int i=0,len = random.nextInt(10); i<len; i++) {
			name.append(SMALL_LETTERS[random.nextInt(SMALL_LETTERS.length)]);
		}
		return prefix+head+name.toString();
	}
	
	public static String getRandomEmail() {
		Random random = new Random();
		StringBuffer prefix = new StringBuffer();
		StringBuffer suffix = new StringBuffer();
		for (int i=0,len=random.nextInt(5)+5; i<len; i++) {
			prefix.append(i<len/2 ? SMALL_LETTERS[random.nextInt(SMALL_LETTERS.length)]
					:DIGITALS[random.nextInt(DIGITALS.length)]);
			if (i<5) {
				suffix.append(SMALL_LETTERS[random.nextInt(SMALL_LETTERS.length)]);
			}
		}
		return prefix.toString() + new Date().getTime() + "@"+suffix.toString()+".com";
	}
}
