package com.manydesigns.portofino.report.pojo;

public class DbColumn {
	protected String table;
	protected String name;
	protected String remarks;
	protected String jdbcType;
	protected String columnType;

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
	 * @return remarks - {return content description}
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks - {parameter description}.
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return columnType - {return content description}
	 */
	public String getColumnType() {
		return columnType;
	}

	/**
	 * @param columnType - {parameter description}.
	 */
	public void setColumnType(String columnType) {
		this.columnType = columnType;
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
	 * @return jdbcType - {return content description}
	 */
	public String getJdbcType() {
		return jdbcType;
	}

	/**
	 * @param jdbcType - {parameter description}.
	 */
	public void setJdbcType(String jdbcType) {
		this.jdbcType = jdbcType;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Field [table=" + table + ", name=" + name + ", remarks=" + remarks + ", jdbcType=" + jdbcType
				+ ", columnType=" + columnType + "]";
	}
}
