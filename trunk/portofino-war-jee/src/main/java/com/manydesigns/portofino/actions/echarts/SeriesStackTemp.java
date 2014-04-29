package com.manydesigns.portofino.actions.echarts;

/**
 * 温度计 <br>
 * <p>
 * Create on : 2013-12-23<br>
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
public class SeriesStackTemp extends Series {
	private String stack = "sum";
	private String barCategoryGap = "50%";
	private String itemStyle = "{\n" + "                normal: {\n" + "                    color: 'tomato',\n"
			+ "                    borderColor: 'tomato',\n" + "                    borderWidth: 6,\n"
			+ "                    label : {\n" + "                        show: true, position: 'inside'\n"
			+ "                    }\n" + "                }\n" + "            }";

	/**
	 * @return stack - {return content description}
	 */
	public String getStack() {
		return stack;
	}

	/**
	 * @param stack - {parameter description}.
	 */
	public void setStack(String stack) {
		this.stack = stack;
	}

	/**
	 * @return barCategoryGap - {return content description}
	 */
	public String getBarCategoryGap() {
		return barCategoryGap;
	}

	/**
	 * @param barCategoryGap .
	 */
	public void setBarCategoryGap(String barCategoryGap) {
		this.barCategoryGap = barCategoryGap;
	}

	/**
	 * @return itemStyle
	 */
	public String getItemStyle() {
		return itemStyle;
	}

	/**
	 * @param itemStyle .
	 */
	public void setItemStyle(String itemStyle) {
		this.itemStyle = itemStyle;
	}

}
