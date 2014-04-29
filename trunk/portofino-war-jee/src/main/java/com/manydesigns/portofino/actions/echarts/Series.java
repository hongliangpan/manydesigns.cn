package com.manydesigns.portofino.actions.echarts;

import java.util.LinkedList;
import java.util.List;

public class Series {
	private String name;
	private String type;
	private List<Object> data = new LinkedList<Object>();

	/**
	 * @param values - {parameter description}.
	 */
	public void addData(Object values) {
		this.data.add(values);
	}

	/**
	 * @return name - {return content description}
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name - {parameter description}.
	 */
	public void setName(String name) {
		this.name = name;
	}

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
	 * @return data - {return content description}
	 */
	public List<Object> getData() {
		return data;
	}

	/**
	 * @param data - {parameter description}.
	 */
	public void setData(List<Object> data) {
		this.data = data;
	}

}
