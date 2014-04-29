package com.manydesigns.portofino.report.enums;

import org.apache.commons.lang.StringUtils;

/**
 * 报表导出文件类型<br>
 * <p>
 * Create on : 2014-3-3<br>
 * <p>
 * </p>
 * <br>
 * 
 * @author panhongliang<br>
 * @version portofino-war-jee v1.0
 *          <p>
 *          <br>
 *          <strong>Modify History:</strong><br>
 *          user modify_date modify_content<br>
 *          -------------------------------------------<br>
 *          <br>
 */
public enum RowNumberType {
	none("不显示"),

	all("大排行"),

	page("每页独立行号"),

	column("列排行");

	private final String m_name;

	/**
	 * Constructors.
	 * 
	 * @param name 标识号
	 */
	private RowNumberType(final String name) {
		this.m_name = name;
	}

	/**
	 * parse enum.
	 * 
	 * @param name id
	 * @return DataType
	 */
	public static RowNumberType parse(final String name) {
		if (StringUtils.isBlank(name)) {
			return null;
		}
		for (RowNumberType t_generic : RowNumberType.values()) {
			if (t_generic.getName().equals(name)) {
				return t_generic;
			}
		}
		return null;
	}

	/**
	 * get displayName from i18n
	 * 
	 * @return displayName
	 */
	public String getName() {
		return m_name;
	}

}