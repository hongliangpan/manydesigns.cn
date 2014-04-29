/**
 * Copyright (c) 2013-2015 www.javahih.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 */
package com.manydesigns.portofino.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtils {
	public static boolean matchs(String regex, String input) {
		return matcher(regex, input).matches();
	}

	public static Matcher matcher(String regex, String input) {
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(input);
		return m;
	}

	public static boolean lookingAt(String regex, String input) {
		Matcher m = matcher(regex, input);
		return m.lookingAt();
	}

	public static boolean find(String regex, String input) {
		Matcher m = matcher(regex, input);
		return m.find();
	}

	public static String[] split(String regex, String input) {
		Pattern p = Pattern.compile(regex);
		return p.split(input);
	}

	public static StringBuffer appendReplace(String regex, String input,
			String repstr) {
		StringBuffer sb = new StringBuffer();
		Matcher m = matcher(regex, input);
		while (m.find()) {
			m.appendReplacement(sb, repstr);
		}
		m.appendTail(sb);
		return sb;
	}
}