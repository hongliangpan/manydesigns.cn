package com.riil.itsboard.custom;

import org.apache.commons.lang.StringUtils;

public enum SortType {

	DESC("DESC", "双降↓↓", "D", "D"),

	ASC("ASC", "双升↑↑", "A", "A"),

	DA("DA", "纵降横升↓↑", "D", "A"),

	AD("AD", "纵升横降↑↓", "A", "D");

	/**
	 * <code>id</code> - id.
	 */
	private final String m_id;
	private final String m_name;
	private final String m_y;
	private final String m_x;

	/**
	 * Constructors.
	 * 
	 * @param id 标识号
	 */
	private SortType(final String id, final String name, final String y, final String x) {
		this.m_id = id;
		this.m_name = name;
		this.m_y = y;
		this.m_x = x;
	}

	/**
	 * parse enum.
	 * 
	 * @param id id
	 * @return DataType
	 */
	public static SortType parse(final String id) {
		if (StringUtils.isBlank(id)) {
			return null;
		}
		for (SortType t_generic : SortType.values()) {
			if (t_generic.getId().equals(id)) {
				return t_generic;
			}
		}
		return null;
	}

	/**
	 * @return m_枚举值
	 */
	public String getId() {
		return m_id;
	}

	/**
	 * get displayName from i18n
	 * 
	 * @return displayName
	 */
	public String getName() {
		return m_name;
	}

	/**
	 * @return y - {return content description}
	 */
	public String getY() {
		return m_y;
	}

	/**
	 * @return x - {return content description}
	 */
	public String getX() {
		return m_x;
	}

}
