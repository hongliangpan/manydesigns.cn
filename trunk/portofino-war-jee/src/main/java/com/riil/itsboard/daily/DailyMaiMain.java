package com.riil.itsboard.daily;

import groovy.lang.GroovyCodeSource;
import groovy.lang.GroovyObject;

import java.io.File;

import com.manydesigns.portofino.utils.ContextUtils;

public class DailyMaiMain {
	private static final String S_FILE_SEPERATOR = "/";

	public static void execute() throws Exception {

		String t_pathname = ContextUtils.getGroovyClasspath().getPath() + "/DailyWeekJob.groovy";
		File file = new File(t_pathname);
		Class groovyClass = ContextUtils.getClassLoader().parseClass(new GroovyCodeSource(file));
		GroovyObject object = (GroovyObject) groovyClass.newInstance();

		object.invokeMethod("schedule", "daily.mail");

	}

	/**
	 * getPath.
	 * 
	 * @return String
	 */
	public static String getPath() {
		return "src/main/java" + S_FILE_SEPERATOR
				+ DailyMaiMain.class.getPackage().getName().replaceAll("\\.", S_FILE_SEPERATOR) + S_FILE_SEPERATOR;
	}

	/**
	 * getPath.
	 * 
	 * @return String
	 */
	public static String getPath100() {
		return "." + S_FILE_SEPERATOR + DailyMaiMain.class.getPackage().getName().replaceAll("\\.", S_FILE_SEPERATOR)
				+ S_FILE_SEPERATOR;
	}
}
