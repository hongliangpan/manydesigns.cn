package com.manydesigns.portofino.report.enums;

import org.apache.commons.lang.StringUtils;

/**
 * 字段数据类型<br>
 * 参考 net.sf.dynamicreports.report.builder.datatype.DataTypes <br>
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
public enum DataType {

	byteType("byte"),

	shortType("short"),

	integer("integer"),

	longType("long"),

	floatType("float"),

	doubleType("double"),

	bigdecimal("bigdecimal"),

	biginteger("biginteger"),

	date("date"),

	dateyear("dateyear"),

	datemonth("datemonth"),

	dateday("dateday"),

	string("string"),

	text("text"),

	list("list"), percentage("percentage"),

	booleanType("boolean"),

	character("character"),

	dateyeartomonth("dateyeartomonth"),

	dateyeartohour("dateyeartohour"),

	dateyeartominute("dateyeartominute"),

	dateyeartosecond("dateyeartosecond"),

	dateyeartofraction("dateyeartofraction"),

	timehourtominute("timehourtominute"),

	timehourtosecond("timehourtosecond"),

	timehourtofraction("timehourtofraction")

	;

	private final String m_name;

	/**
	 * Constructors.
	 * 
	 * @param name 标识号
	 */
	private DataType(final String name) {
		this.m_name = name;
	}

	/**
	 * parse enum.
	 * 
	 * @param name id
	 * @return DataType
	 */
	public static DataType parse(final String name) {
		if (StringUtils.isBlank(name)) {
			return null;
		}
		for (DataType t_generic : DataType.values()) {
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

	public static DataType valueOfJdbcType(String type) {
		if ("BIGINT".equals(type)) {
			type = "integer";
		}
		for (DataType dataType : DataType.values()) {
			if (dataType.getName().indexOf(type.toLowerCase()) >= 0) {
				return dataType;
			}
		}
		return DataType.string;
	}
}