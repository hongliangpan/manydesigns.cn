package com.manydesigns.portofino.report.pojo;

import java.util.Map;

import net.sf.dynamicreports.report.builder.column.ValueColumnBuilder;

import org.apache.commons.lang.StringUtils;

import com.manydesigns.portofino.report.export.builder.ColumnBuilder;

public class ReportPojo {

	private String m_id;
	private String m_rptDefId;
	private String m_name;
	private String m_orderField;
	private String m_orderType;
	private String m_docTypes;
	private int m_pageRows;
	private int m_topn;
	private String m_paramValue1;
	private String m_paramValue2;
	private String m_paramValue3;
	private String m_paramValue4;
	private String m_paramValue5;
	private String m_nameEn;
	private String m_typeId;
	private String m_displayTypeId;
	private String m_className;
	private String m_chartType;
	private String m_chartOrientation;
	private String m_sql;
	private String m_chartSql;
	private String m_chartX;
	private String m_chartY;
	private String m_chartValue;
	private String m_subRptDefIds;
	private int m_isShowPageNumber;
	private String m_rowNumberType;

	private String m_subTitle;
	private String m_title;

	// list pojo
	private Map<String, FieldPojo> m_fields;
	private Map<String, ValueColumnBuilder> m_columnMap;
	private ValueColumnBuilder[] m_columns;

	/**
	 * @return id - {return content description}
	 */
	public String getId() {
		return m_id;
	}

	/**
	 * @param id - {parameter description}.
	 */
	public void setId(String id) {
		m_id = id;
	}

	/**
	 * @return rptDefId - {return content description}
	 */
	public String getRptDefId() {
		return m_rptDefId;
	}

	/**
	 * @param rptDefId - {parameter description}.
	 */
	public void setRptDefId(String rptDefId) {
		m_rptDefId = rptDefId;
	}

	/**
	 * @return name - {return content description}
	 */
	public String getName() {
		return m_name;
	}

	/**
	 * @param name - {parameter description}.
	 */
	public void setName(String name) {
		m_name = name;
	}

	/**
	 * @return orderField - {return content description}
	 */
	public String getOrderField() {
		return m_orderField;
	}

	/**
	 * @param orderField - {parameter description}.
	 */
	public void setOrderField(String orderField) {
		m_orderField = orderField;
	}

	/**
	 * @return orderType - {return content description}
	 */
	public String getOrderType() {
		return m_orderType;
	}

	/**
	 * @param orderType - {parameter description}.
	 */
	public void setOrderType(String orderType) {
		m_orderType = orderType;
	}

	/**
	 * @return docTypes - {return content description}
	 */
	public String getDocTypes() {
		return m_docTypes;
	}

	/**
	 * @param docTypes - {parameter description}.
	 */
	public void setDocTypes(String docTypes) {
		m_docTypes = docTypes;
	}

	/**
	 * @return pageRows - {return content description}
	 */
	public int getPageRows() {
		return m_pageRows;
	}

	/**
	 * @param pageRows - {parameter description}.
	 */
	public void setPageRows(int pageRows) {
		m_pageRows = pageRows;
	}

	/**
	 * @return topn - {return content description}
	 */
	public int getTopn() {
		return m_topn;
	}

	/**
	 * @param topn - {parameter description}.
	 */
	public void setTopn(int topn) {
		m_topn = topn;
	}

	/**
	 * @return paramValue1 - {return content description}
	 */
	public String getParamValue1() {
		return m_paramValue1;
	}

	/**
	 * @param paramValue1 - {parameter description}.
	 */
	public void setParamValue1(String paramValue1) {
		m_paramValue1 = paramValue1;
	}

	/**
	 * @return paramValue2 - {return content description}
	 */
	public String getParamValue2() {
		return m_paramValue2;
	}

	/**
	 * @param paramValue2 - {parameter description}.
	 */
	public void setParamValue2(String paramValue2) {
		m_paramValue2 = paramValue2;
	}

	/**
	 * @return paramValue3 - {return content description}
	 */
	public String getParamValue3() {
		return m_paramValue3;
	}

	/**
	 * @param paramValue3 - {parameter description}.
	 */
	public void setParamValue3(String paramValue3) {
		m_paramValue3 = paramValue3;
	}

	/**
	 * @return paramValue4 - {return content description}
	 */
	public String getParamValue4() {
		return m_paramValue4;
	}

	/**
	 * @param paramValue4 - {parameter description}.
	 */
	public void setParamValue4(String paramValue4) {
		m_paramValue4 = paramValue4;
	}

	/**
	 * @return paramValue5 - {return content description}
	 */
	public String getParamValue5() {
		return m_paramValue5;
	}

	/**
	 * @param paramValue5 - {parameter description}.
	 */
	public void setParamValue5(String paramValue5) {
		m_paramValue5 = paramValue5;
	}

	/**
	 * @return nameEn - {return content description}
	 */
	public String getNameEn() {
		return m_nameEn;
	}

	/**
	 * @param nameEn - {parameter description}.
	 */
	public void setNameEn(String nameEn) {
		m_nameEn = nameEn;
	}

	/**
	 * @return typeId - {return content description}
	 */
	public String getTypeId() {
		return m_typeId;
	}

	/**
	 * @param typeId - {parameter description}.
	 */
	public void setTypeId(String typeId) {
		m_typeId = typeId;
	}

	/**
	 * @return displayTypeId - {return content description}
	 */
	public String getDisplayTypeId() {
		return m_displayTypeId;
	}

	/**
	 * @param displayTypeId - {parameter description}.
	 */
	public void setDisplayTypeId(String displayTypeId) {
		m_displayTypeId = displayTypeId;
	}

	/**
	 * @return className - {return content description}
	 */
	public String getClassName() {
		return m_className;
	}

	/**
	 * @param className - {parameter description}.
	 */
	public void setClassName(String className) {
		m_className = className;
	}

	/**
	 * @return chartType - {return content description}
	 */
	public String getChartType() {
		return m_chartType;
	}

	/**
	 * @param chartType - {parameter description}.
	 */
	public void setChartType(String chartType) {
		m_chartType = chartType;
	}

	/**
	 * @return chartOrientation - {return content description}
	 */
	public String getChartOrientation() {
		return m_chartOrientation;
	}

	/**
	 * @param chartOrientation - {parameter description}.
	 */
	public void setChartOrientation(String chartOrientation) {
		m_chartOrientation = chartOrientation;
	}

	/**
	 * @return sql - {return content description}
	 */
	public String getSql() {
		return m_sql;
	}

	/**
	 * @param sql - {parameter description}.
	 */
	public void setSql(String sql) {
		m_sql = sql;
	}

	/**
	 * @return subRptDefIds - {return content description}
	 */
	public String getSubRptDefIds() {
		return m_subRptDefIds;
	}

	/**
	 * @param subRptDefIds - {parameter description}.
	 */
	public void setSubRptDefIds(String subRptDefIds) {
		m_subRptDefIds = subRptDefIds;
	}

	/**
	 * @return chartX - {return content description}
	 */
	public String getChartX() {
		return m_chartX;
	}

	/**
	 * @param chartX - {parameter description}.
	 */
	public void setChartX(String chartX) {
		m_chartX = chartX;
	}

	/**
	 * @return chartY - {return content description}
	 */
	public String getChartY() {
		return m_chartY;
	}

	/**
	 * @param chartY - {parameter description}.
	 */
	public void setChartY(String chartY) {
		m_chartY = chartY;
	}

	/**
	 * @return chartValue - {return content description}
	 */
	public String[] getChartYFields() {
		if (StringUtils.isBlank(m_chartY)) {
			return new String[] {};
		}
		return m_chartY.split(",");
	}

	/**
	 * @return chartValue - {return content description}
	 */
	public String getChartValue() {
		return m_chartValue;
	}

	/**
	 * @return chartValue - {return content description}
	 */
	public String[] getChartValueFields() {
		if (StringUtils.isBlank(m_chartValue)) {
			return new String[] {};
		}
		return m_chartValue.split(",");
	}

	/**
	 * @param chartValue - {parameter description}.
	 */
	public void setChartValue(String chartValue) {
		m_chartValue = chartValue;
	}

	/**
	 * @return fields - {return content description}
	 */
	public Map<String, FieldPojo> getFields() {
		return m_fields;
	}

	/**
	 * @param fields - {parameter description}.
	 */
	public void setFields(Map<String, FieldPojo> fields) {
		m_fields = fields;

		m_columnMap = ColumnBuilder.getColumns(this, fields);

		m_columns = toArray(m_columnMap);
	}

	private static ValueColumnBuilder[] toArray(Map<String, ValueColumnBuilder> columns) {

		ValueColumnBuilder[] t_result = new ValueColumnBuilder[columns.size()];
		columns.values().toArray(t_result);
		return t_result;
	}

	/**
	 * @return columnMap - {return content description}
	 */
	public Map<String, ValueColumnBuilder> getColumnMap() {
		return m_columnMap;
	}

	/**
	 * @param columnMap - {parameter description}.
	 */
	public void setColumnMap(Map<String, ValueColumnBuilder> columnMap) {
		m_columnMap = columnMap;
	}

	/**
	 * @return columns - {return content description}
	 */
	public ValueColumnBuilder[] getColumns() {
		return m_columns;
	}

	/**
	 * @param columns - {parameter description}.
	 */
	public void setColumns(ValueColumnBuilder[] columns) {
		m_columns = columns;
	}

	/**
	 * @return isShowPageNumber - {return content description}
	 */
	public int getIsShowPageNumber() {
		return m_isShowPageNumber;
	}

	/**
	 * @param isShowPageNumber - {parameter description}.
	 */
	public void setIsShowPageNumber(int isShowPageNumber) {
		m_isShowPageNumber = isShowPageNumber;
	}

	/**
	 * @return chartSql - {return content description}
	 */
	public String getChartSql() {
		return m_chartSql;
	}

	/**
	 * @param chartSql - {parameter description}.
	 */
	public void setChartSql(String chartSql) {
		m_chartSql = chartSql;
	}

	/**
	 * @return rowNumberType - {return content description}
	 */
	public String getRowNumberType() {
		return m_rowNumberType;
	}

	/**
	 * @param rowNumberType - {parameter description}.
	 */
	public void setRowNumberType(String rowNumberType) {
		m_rowNumberType = rowNumberType;
	}

	/**
	 * @return subTitle - {return content description}
	 */
	public String getSubTitle() {
		return m_subTitle;
	}

	/**
	 * @param subTitle - {parameter description}.
	 */
	public void setSubTitle(String subTitle) {
		m_subTitle = subTitle;
	}

	/**
	 * @return title - {return content description}
	 */
	public String getTitle() {
		if (StringUtils.isBlank(m_title)) {
			return m_name;
		}
		return m_title;
	}

	/**
	 * @param title - {parameter description}.
	 */
	public void setTitle(String title) {
		m_title = title;
	}

	public static String buildQuerySql(ReportPojo reportPojo) {
		String t_sql = reportPojo.getSql();
		int t_indexOfOrder = t_sql.toUpperCase().lastIndexOf("ORDER\\s{1,}BY");
		if (reportPojo.getTopn() > 0) {
			String t_limit = " limit " + reportPojo.getTopn();
			if (t_indexOfOrder > 0) {
				t_sql = t_sql.substring(0, t_indexOfOrder) + t_limit + t_sql.substring(t_indexOfOrder);
			} else {
				t_sql += t_limit;
			}
		}
		if (StringUtils.isBlank(reportPojo.getOrderField())) {
			return t_sql;
		}
		if (t_indexOfOrder > 0) {
			t_sql = t_sql.substring(0, t_indexOfOrder);
		}
		t_sql += " ORDER BY " + reportPojo.getOrderField() + " " + reportPojo.getOrderType();

		return t_sql;
	}
}
