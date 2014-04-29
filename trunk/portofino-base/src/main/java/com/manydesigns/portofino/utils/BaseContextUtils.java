package com.manydesigns.portofino.utils;

import groovy.lang.GroovyClassLoader;
import groovy.util.GroovyScriptEngine;

import java.io.File;
import java.util.HashMap;

import org.apache.commons.configuration.FileConfiguration;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

// hongliangpan add
public class BaseContextUtils {
	protected static File applicationDirectory;

	protected static File groovyClasspath;
	protected static GroovyScriptEngine groovyScriptEngine;

	protected static GroovyClassLoader classLoader;
	protected static FileConfiguration configuration;

	@SuppressWarnings("rawtypes")
	public static String getLoginUser() {
		Subject subject = SecurityUtils.getSubject();
		if (null == subject.getPrincipals()) {
			return "";
		}
		Object t_primaryPrincipal = subject.getPrincipals().getPrimaryPrincipal();
		if (null == t_primaryPrincipal || !(t_primaryPrincipal instanceof HashMap)) {
			return "";
		}
		if (null != ((HashMap) t_primaryPrincipal).get("c_name")) {
			return ((HashMap) t_primaryPrincipal).get("c_name").toString();
		} else {
			return "";
		}
	}

	/**
	 * @return applicationDirectory - {return content description}
	 */
	public static File getApplicationDirectory() {
		return applicationDirectory;
	}

	/**
	 * @param applicationDirectory - {parameter description}.
	 */
	public static void setApplicationDirectory(File applicationDirectory) {
		BaseContextUtils.applicationDirectory = applicationDirectory;
	}

	/**
	 * @return groovyClasspath - {return content description}
	 */
	public static File getGroovyClasspath() {
		return groovyClasspath;
	}

	/**
	 * @param groovyClasspath - {parameter description}.
	 */
	public static void setGroovyClasspath(File groovyClasspath) {
		BaseContextUtils.groovyClasspath = groovyClasspath;
	}

	/**
	 * @return groovyScriptEngine - {return content description}
	 */
	public static GroovyScriptEngine getGroovyScriptEngine() {
		return groovyScriptEngine;
	}

	/**
	 * @param groovyScriptEngine - {parameter description}.
	 */
	public static void setGroovyScriptEngine(GroovyScriptEngine groovyScriptEngine) {
		BaseContextUtils.groovyScriptEngine = groovyScriptEngine;
	}

	/**
	 * @return classLoader - {return content description}
	 */
	public static GroovyClassLoader getClassLoader() {
		return classLoader;
	}

	/**
	 * @param classLoader - {parameter description}.
	 */
	public static void setClassLoader(GroovyClassLoader classLoader) {
		BaseContextUtils.classLoader = classLoader;
	}

	/**
	 * @return configuration - {return content description}
	 */
	public static FileConfiguration getConfiguration() {
		return configuration;
	}

	/**
	 * @param configuration - {parameter description}.
	 */
	public static void setConfiguration(FileConfiguration configuration) {
		BaseContextUtils.configuration = configuration;
	}

}
