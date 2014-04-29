package com.manydesigns.portofino.actions.echarts;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Data {

	private String charType;

	private Set<String> legends = new LinkedHashSet<String>();

	private Set<String> xnames = new LinkedHashSet<String>();
	// yname == legends
	private Set<String> ynames = new LinkedHashSet<String>();

	private List<NameValue> pieValues;
	private List<List<NameValue>> mapValues;
	private List<Object> maxValues;
	private Object maxValue;
	private List<Series> seriesList = new LinkedList<Series>();

	public void addAxis(Series axis) {
		seriesList.add(axis);
	}

	/**
	 * @return charType - {return content description}
	 */
	public String getCharType() {
		return charType;
	}

	/**
	 * @param charType - {parameter description}.
	 */
	public void setCharType(String charType) {
		this.charType = charType;
	}

	/**
	 * @return legends - {return content description}
	 */
	public Set<String> getLegends() {
		if (legends.size() > 0) {
			return legends;
		}

		return ynames;
	}

	/**
	 * @param legends - {parameter description}.
	 */
	public void setLegends(Set<String> legends) {
		this.legends = legends;
	}

	/**
	 * @return xnames - {return content description}
	 */
	public Set<String> getXnames() {
		return xnames;
	}

	/**
	 * @param xnames - {parameter description}.
	 */
	public void setXnames(Set<String> xnames) {
		this.xnames = xnames;
	}

	/**
	 * @return ynames - {return content description}
	 */
	public Set<String> getYnames() {
		return ynames;
	}

	/**
	 * @param ynames - {parameter description}.
	 */
	public void setYnames(Set<String> ynames) {
		this.ynames = ynames;
	}

	/**
	 * @return seriesList - {return content description}
	 */
	public List<Series> getSeriesList() {
		return seriesList;
	}

	/**
	 * @param seriesList - {parameter description}.
	 */
	public void setSeriesList(List<Series> seriesList) {
		this.seriesList = seriesList;
	}

	/**
	 * @return pieValues - {return content description}
	 */
	public List<NameValue> getPieValues() {
		return pieValues;
	}

	/**
	 * @param pieValues - {parameter description}.
	 */
	public void setPieValues(List<NameValue> pieValues) {
		this.pieValues = pieValues;
	}

	/**
	 * @return mapValues - {return content description}
	 */
	public List<List<NameValue>> getMapValues() {
		return mapValues;
	}

	/**
	 * @param mapValues - {parameter description}.
	 */
	public void setMapValues(List<List<NameValue>> mapValues) {
		this.mapValues = mapValues;
	}

	/**
	 * @return maxValues - {return content description}
	 */
	public List<Object> getMaxValues() {
		return maxValues;
	}

	/**
	 * @param maxValues - {parameter description}.
	 */
	public void setMaxValues(List<Object> maxValues) {
		this.maxValues = maxValues;
	}

	/**
	 * @return maxValue - {return content description}
	 */
	public Object getMaxValue() {
		return maxValue;
	}

	/**
	 * @param maxValue - {parameter description}.
	 */
	public void setMaxValue(Object maxValue) {
		this.maxValue = maxValue;
	}

}
