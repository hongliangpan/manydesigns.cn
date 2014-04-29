package com.manydesigns.portofino.report.pojo;

public class FieldPojo {
	private static final String S__PERCENT = "_percent";
	private String m_id;
	private String m_rptId;
	private String m_defFieldId;
	private String m_calcExp;
	private int m_isDisplay;
	private String m_displayName;
	private String m_config;
	private String m_field;
	private String m_dataType;
	private int m_isGroup;
	private int m_isSum;
	private String m_sumMethod;
	private int m_isPercentage;
	private int m_isQueryTime;
	private String m_queryRange;
	private String m_url;
	private String m_alignment;
	private String m_pattern;
	private int m_isSubtotal;
	private String m_groupAlignment;

	private String m_headGroup;
	private String m_headGroup2;

	private int m_width;

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
	 * @return rptId - {return content description}
	 */
	public String getRptId() {
		return m_rptId;
	}

	/**
	 * @param rptId - {parameter description}.
	 */
	public void setRptId(String rptId) {
		m_rptId = rptId;
	}

	/**
	 * @return defFieldId - {return content description}
	 */
	public String getDefFieldId() {
		return m_defFieldId;
	}

	/**
	 * @param defFieldId - {parameter description}.
	 */
	public void setDefFieldId(String defFieldId) {
		m_defFieldId = defFieldId;
	}

	/**
	 * @return calcExp - {return content description}
	 */
	public String getCalcExp() {
		return m_calcExp;
	}

	/**
	 * @param calcExp - {parameter description}.
	 */
	public void setCalcExp(String calcExp) {
		m_calcExp = calcExp;
	}

	/**
	 * @return isDisplay - {return content description}
	 */
	public int getIsDisplay() {
		return m_isDisplay;
	}

	/**
	 * @param isDisplay - {parameter description}.
	 */
	public void setIsDisplay(int isDisplay) {
		m_isDisplay = isDisplay;
	}

	/**
	 * @return displayName - {return content description}
	 */
	public String getDisplayName() {
		return m_displayName;
	}

	/**
	 * @param displayName - {parameter description}.
	 */
	public void setDisplayName(String displayName) {
		m_displayName = displayName;
	}

	/**
	 * @return config - {return content description}
	 */
	public String getConfig() {
		return m_config;
	}

	/**
	 * @param config - {parameter description}.
	 */
	public void setConfig(String config) {
		m_config = config;
	}

	/**
	 * @return field - {return content description}
	 */
	public String getField() {
		return m_field;
	}

	/**
	 * @return field - {return content description}
	 */
	public String getField4Percentage() {
		return m_field + S__PERCENT;
	}

	public static String getFile(String field4Percentage) {
		return field4Percentage.substring(0, field4Percentage.indexOf(S__PERCENT));
	}

	/**
	 * @param field - {parameter description}.
	 */
	public void setField(String field) {
		m_field = field;
	}

	/**
	 * @return dataType - {return content description}
	 */
	public String getDataType() {
		return m_dataType;
	}

	/**
	 * @param dataType - {parameter description}.
	 */
	public void setDataType(String dataType) {
		m_dataType = dataType;
	}

	/**
	 * @return isGroup - {return content description}
	 */
	public int getIsGroup() {
		return m_isGroup;
	}

	/**
	 * @param isGroup - {parameter description}.
	 */
	public void setIsGroup(int isGroup) {
		m_isGroup = isGroup;
	}

	/**
	 * @return isSum - {return content description}
	 */
	public int getIsSum() {
		return m_isSum;
	}

	/**
	 * @param isSum - {parameter description}.
	 */
	public void setIsSum(int isSum) {
		m_isSum = isSum;
	}

	/**
	 * @return sumMethod - {return content description}
	 */
	public String getSumMethod() {
		return m_sumMethod;
	}

	/**
	 * @param sumMethod - {parameter description}.
	 */
	public void setSumMethod(String sumMethod) {
		m_sumMethod = sumMethod;
	}

	/**
	 * @return isPercentage - {return content description}
	 */
	public int getIsPercentage() {
		return m_isPercentage;
	}

	/**
	 * @param isPercentage - {parameter description}.
	 */
	public void setIsPercentage(int isPercentage) {
		m_isPercentage = isPercentage;
	}

	/**
	 * @return isQueryTime - {return content description}
	 */
	public int getIsQueryTime() {
		return m_isQueryTime;
	}

	/**
	 * @param isQueryTime - {parameter description}.
	 */
	public void setIsQueryTime(int isQueryTime) {
		m_isQueryTime = isQueryTime;
	}

	/**
	 * @return queryRange - {return content description}
	 */
	public String getQueryRange() {
		return m_queryRange;
	}

	/**
	 * @param queryRange - {parameter description}.
	 */
	public void setQueryRange(String queryRange) {
		m_queryRange = queryRange;
	}

	/**
	 * @return url - {return content description}
	 */
	public String getUrl() {
		return m_url;
	}

	/**
	 * @param url - {parameter description}.
	 */
	public void setUrl(String url) {
		m_url = url;
	}

	/**
	 * @return alignment - {return content description}
	 */
	public String getAlignment() {
		return m_alignment;
	}

	/**
	 * @param alignment - {parameter description}.
	 */
	public void setAlignment(String alignment) {
		m_alignment = alignment;
	}

	/**
	 * @return pattern - {return content description}
	 */
	public String getPattern() {
		return m_pattern;
	}

	/**
	 * @param pattern - {parameter description}.
	 */
	public void setPattern(String pattern) {
		m_pattern = pattern;
	}

	/**
	 * @return isSubtotal - {return content description}
	 */
	public int getIsSubtotal() {
		return m_isSubtotal;
	}

	/**
	 * @param isSubtotal - {parameter description}.
	 */
	public void setIsSubtotal(int isSubtotal) {
		m_isSubtotal = isSubtotal;
	}

	/**
	 * @return groupAlignment - {return content description}
	 */
	public String getGroupAlignment() {
		return m_groupAlignment;
	}

	/**
	 * @param groupAlignment - {parameter description}.
	 */
	public void setGroupAlignment(String groupAlignment) {
		m_groupAlignment = groupAlignment;
	}

	/**
	 * @return headGroup - {return content description}
	 */
	public String getHeadGroup() {
		return m_headGroup;
	}

	/**
	 * @param headGroup - {parameter description}.
	 */
	public void setHeadGroup(String headGroup) {
		m_headGroup = headGroup;
	}

	/**
	 * @return headGroup2 - {return content description}
	 */
	public String getHeadGroup2() {
		return m_headGroup2;
	}

	/**
	 * @param headGroup2 - {parameter description}.
	 */
	public void setHeadGroup2(String headGroup2) {
		m_headGroup2 = headGroup2;
	}

	/**
	 * @return width - {return content description}
	 */
	public int getWidth() {
		return m_width;
	}

	/**
	 * @param width - {parameter description}.
	 */
	public void setWidth(int width) {
		m_width = width;
	}
}
