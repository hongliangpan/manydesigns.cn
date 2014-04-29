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
public enum DocType {
	pdf("pdf"),

	doc("word"),

	xls("excel"),

	docx("wordx"),

	pptx("pptx"),

	xlsx("excelx"),

	png("png图片"),

	html("html页面"),

	htmlFile("html文件");

	private final String m_name;

	/**
	 * Constructors.
	 * 
	 * @param name 标识号
	 */
	private DocType(final String name) {
		this.m_name = name;
	}

	/**
	 * parse enum.
	 * 
	 * @param name id
	 * @return DataType
	 */
	public static DocType parse(final String name) {
		if (StringUtils.isBlank(name)) {
			return null;
		}
		for (DocType t_generic : DocType.values()) {
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