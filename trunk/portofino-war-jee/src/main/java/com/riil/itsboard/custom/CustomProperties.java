package com.riil.itsboard.custom;

import java.io.File;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.FileConfiguration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang.StringUtils;

public class CustomProperties {

	private static final String S_COLUMN_CONFIG_FILE = "column.remarks.properties";
	private static CustomProperties instance;
	protected FileConfiguration configuration;

	private CustomProperties() {
	}

	public static synchronized CustomProperties getInstance() {
		if (instance == null) {
			instance = new CustomProperties();
		}
		if (null == instance.configuration) {
			try {
				instance.loadConfiguration();
			} catch (ConfigurationException e) {
				e.printStackTrace();
			}
		}
		return instance;
	}

	protected void loadConfiguration() throws ConfigurationException {
		String file = CustomProperties.class.getClassLoader().getResource(S_COLUMN_CONFIG_FILE).getPath();
		File appConfigurationFile = new File(file);
		configuration = new PropertiesConfiguration(appConfigurationFile);
	}

	public String getColumnRemarks(String columnName) {
		String t_remark = configuration.getString(columnName);
		if (StringUtils.isNotBlank(t_remark)) {

			return t_remark;
		}
		System.err.println("Unknow column remarks" + columnName);
		if (columnName.startsWith("COUNT(")) {
			return "数量";
		}
		return columnName;
	}
}
