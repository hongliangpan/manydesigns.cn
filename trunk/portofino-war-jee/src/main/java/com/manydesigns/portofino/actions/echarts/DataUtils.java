package com.manydesigns.portofino.actions.echarts;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.manydesigns.portofino.utils.JsonMapper;
import com.riil.itsboard.custom.DbUtils4Its;

public class DataUtils {

	public static Set<String> getXNames(List<Map<String, Object>> rows) {
		Set<String> values = new LinkedHashSet<String>();
		for (Map<String, Object> map : rows) {
			String key = getKey(map, "x");
			if (values.contains(key)) {
				continue;
			}
			values.add(key);
		}
		return values;
	}

	public static Set<String> getYNames(List<Map<String, Object>> rows) {
		Set<String> values = new LinkedHashSet<String>();
		for (Map<String, Object> map : rows) {
			String key = getKey(map, "y");
			if (values.contains(key)) {
				continue;
			}
			values.add(key);
		}
		// TODO sort
		return values;
	}

	public static List<Series> getSeries(List<Map<String, Object>> rows, Set<String> xnames, Set<String> ynames) {
		List<Series> result = new LinkedList<Series>();
		for (String yname : ynames) {
			Series series = new Series();
			List<Object> values = getSeriesOne(rows, xnames, yname);
			series.setName(yname);
			series.setType(CharType.bar.name());
			series.setData(values);

			result.add(series);
		}
		return result;
	}

	public static List<NameValue> getPieValues(List<Map<String, Object>> rows) {
		List<NameValue> result = new ArrayList<NameValue>(rows.size());
		for (Map<String, Object> rowMap : rows) {
			NameValue nameValue = new NameValue();
			nameValue.setName(getKeyX(rowMap));
			nameValue.setValue(getValueDouble(rowMap));
			result.add(nameValue);
		}
		return result;
	}

	public static List<List<NameValue>> getMapValues(List<Map<String, Object>> rows, Set<String> yNames) {
		List<List<NameValue>> result = new ArrayList<List<NameValue>>();
		for (String yname : yNames) {
			List<NameValue> oneSeries = new ArrayList<NameValue>();
			for (Map<String, Object> rowMap : rows) {
				if (yname.equals(getKeyY(rowMap))) {
					NameValue nameValue = new NameValue();
					nameValue.setName(getKeyX(rowMap));
					nameValue.setValue(getValueDouble(rowMap));
					oneSeries.add(nameValue);
				}
			}
			result.add(oneSeries);
		}
		return result;
	}

	private static List<Object> getSeriesOne(List<Map<String, Object>> rows, Set<String> xnames, String yname) {
		List<Object> values = new ArrayList<Object>(xnames.size());
		for (String xname : xnames) {
			boolean found = false;
			for (Map<String, Object> rowMap : rows) {
				if (xname.equals(getKeyX(rowMap)) && yname.equals(getKeyY(rowMap))) {
					values.add(getValueDouble(rowMap));
					found = true;
					break;
				}
			}
			if (!found) {
				values.add(0);
			}
		}
		return values;
	}

	private static String getValue(Map<String, Object> map) {
		return map.get("c_value").toString();
	}

	// private static Integer getValueInteger(Map<String, Object> map) {
	// return Integer.parseInt(map.get("c_value").toString());
	// }

	private static Double getValueDouble(Map<String, Object> map) {
		Object t_value = map.get("c_value");
		if (null == t_value) {
			return 0d;
		}
		return Double.parseDouble(t_value.toString());
	}

	private static String getKeyX(Map<String, Object> map) {
		return getKey(map, "x");
	}

	private static String getKeyY(Map<String, Object> map) {
		return getKey(map, "y");
	}

	private static String getKey(Map<String, Object> map, String x) {
		String key = "c_" + x;
		return map.get(key).toString();
	}

	public static String getLabel(Map<String, Object> map, String x) {
		String keyLabel = "c_" + x + "_label";
		if (map.containsKey(keyLabel)) {
			return map.get(keyLabel).toString();
		}
		return getKey(map, x);
	}

	public static List<Object> getMaxValues(List<List<NameValue>> mapValues) {
		List<Object> maxValues = new LinkedList<Object>();
		for (List<NameValue> list : mapValues) {
			Double maxValue = null;
			for (NameValue nameValue : list) {
				Double nowValue = Double.parseDouble(nameValue.getValue().toString());
				if (null == maxValue) {
					maxValue = nowValue;
				} else if (maxValue.compareTo(nowValue) < 0) {
					maxValue = nowValue;
				}
			}
			maxValues.add(maxValue);
		}
		return maxValues;
	}

	public static String getChartJsonData(String sql, HttpServletRequest request) {
		List<Map<String, Object>> rows = DbUtils4Its.runSqlReturnMap(sql, request);
		Data data = new Data();
		data.setCharType(CharType.bar.name());
		data.setXnames(DataUtils.getXNames(rows));
		data.setYnames(DataUtils.getYNames(rows));
		data.setSeriesList(DataUtils.getSeries(rows, data.getXnames(), data.getYnames()));

		data.setPieValues(getPieValues(rows));
		data.setMapValues(getMapValues(rows, data.getYnames()));

		data.setMaxValues(getMaxValues(data.getMapValues()));
		data.setMaxValue(getMaxValue(data.getMaxValues()));
		return JsonMapper.getInstance().toJson(data);
	}

	private static Object getMaxValue(List<Object> maxValues) {
		Double maxValue = null;
		for (Object object : maxValues) {
			Double nowValue = Double.parseDouble(object.toString());
			if (null == maxValue) {
				maxValue = nowValue;
			} else if (maxValue.compareTo(nowValue) < 0) {
				maxValue = nowValue;
			}
		}
		if (null != maxValue) {
			if (maxValue % 10 != 0) {
				maxValue = Math.ceil(maxValue / 10) * 10;
			}
		}
		return maxValue;
	}
}
