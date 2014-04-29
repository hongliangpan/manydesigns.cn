package com.riil.itsboard.custom;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.manydesigns.portofino.pageactions.crud.CrudAction;

public class MatrixUtils {

	public static final Logger logger = LoggerFactory.getLogger(CrudAction.class);

	private static int chineseCompare(Object _oChinese1, Object _oChinese2) {
		return Collator.getInstance(Locale.CHINESE).compare(_oChinese1, _oChinese2);
	}

	private static int chineseCompareDesc(Object _oChinese1, Object _oChinese2) {
		return Collator.getInstance(Locale.CHINESE).compare(_oChinese2, _oChinese1);
	}

	/**
	 * xy轴的字段.
	 * 
	 * @param mapList
	 * @param fieldXY
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<String> getStatValues(List<Map<String, Object>> mapList, String fieldXY, String sort) {
		List<String> listValues = new ArrayList<String>();
		for (Map<String, Object> map : mapList) {
			Object object = map.get(fieldXY);
			if (null == object) {
				continue;
			}
			String value = object.toString();
			if (!listValues.contains(value)) {
				listValues.add(value);
			}
		}
		if (!"c_state".equals(fieldXY) && StringUtils.isNotBlank(sort)) {
			Collections.sort(listValues, new Comparator() {
				public int compare(Object _o1, Object _o2) {
					return chineseCompare(_o1, _o2);
				}
			});
			if ("D".equals(sort)) {
				Collections.reverse(listValues);
			}
		}
		return listValues;
	}

	public static List<Map> getStatProject(List<Map<String, Object>> mapList, String statY, String statYValue,
			String statX, String statXValue) {
		List<Map> listProjs = new ArrayList<Map>();
		for (Map map : mapList) {
			Object xobj = map.get(statX);
			Object yobj = map.get(statY);
			if (statYValue.equals(yobj + "") && statXValue.equals(xobj + "")) {
				listProjs.add(map);
			}
		}
		// sortProjects(listProjs);
		return listProjs;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void sortProjects(List<Map> listProjs) {
		try {
			Collections.sort(listProjs, new Comparator() {
				public int compare(Object _o1, Object _o2) {
					Map map1 = (Map) _o1;
					Map map2 = (Map) _o2;
					return Collator.getInstance(Locale.CHINESE).compare(map1.get("c_name"), map2.get("c_name"));
				}
			});
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public static int getQuantity(List<Map<String, Object>> mapList, String statX, String statXValue) {
		int count = 0;
		for (Map map : mapList) {
			if (statXValue.equals(map.get(statX) + "")) {
				count++;
			}
		}
		return count;
	}

	public static String getNiceName(Map map) {
		return getNiceName(map.get("c_name"));
	}

	public static String getNiceName(Object name) {
		int length = 12;
		if (name == null)
			return "";
		else {
			if (name.toString().trim().length() > length)
				return name.toString().trim().substring(0, length) + "..";
			else return name.toString();
		}
	}

	public static Boolean parseBool(Object value) {
		if (value == null) {
			return false;
		} else if ("1".equals(value.toString())) {
			return true;
		} else {
			return value.toString().equals("true");
		}
	}
}
