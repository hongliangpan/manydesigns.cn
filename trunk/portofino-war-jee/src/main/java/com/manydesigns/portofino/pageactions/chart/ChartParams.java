package com.manydesigns.portofino.pageactions.chart;

import javax.servlet.http.HttpServletRequest;

public class ChartParams {
	/**
	 * 类型
	 */
	private String table;
	/**
	 * 类型
	 */
	private String type;
	private String key;
	private String key2;
	private String key3;

	private String title;

	/**
	 * @return type - {return content description}
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type - {parameter description}.
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return key - {return content description}
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key - {parameter description}.
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * @return key2 - {return content description}
	 */
	public String getKey2() {
		return key2;
	}

	/**
	 * @param key2 - {parameter description}.
	 */
	public void setKey2(String key2) {
		this.key2 = key2;
	}

	/**
	 * @return table - {return content description}
	 */
	public String getTable() {
		return table;
	}

	/**
	 * @param table - {parameter description}.
	 */
	public void setTable(String table) {
		this.table = table;
	}

	/**
	 * @return key3 - {return content description}
	 */
	public String getKey3() {
		return key3;
	}

	/**
	 * @param key3 - {parameter description}.
	 */
	public void setKey3(String key3) {
		this.key3 = key3;
	}

	/**
	 * @return title - {return content description}
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title - {parameter description}.
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	public static ChartParams instance(HttpServletRequest request) {
		ChartParams params = new ChartParams();
		params.setTable(request.getParameter("table"));
		params.setKey(request.getParameter("key"));
		params.setType(request.getParameter("type"));
		params.setKey2(request.getParameter("key2"));
		params.setKey2(request.getParameter("key3"));
		params.setTitle(request.getParameter("title"));
		return params;
	}
}
