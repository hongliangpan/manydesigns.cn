package com.manydesigns.portofino.utils;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;

import com.manydesigns.portofino.report.pojo.ReportPojo;

public class ObjectMapper {
	public static void map2Object(Map<String, Object> datas, Object object) {
		try {
			BeanUtilsBeanCustom beanUtilsBean = new BeanUtilsBeanCustom();
			beanUtilsBean.populate(object, datas);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static Map<String, Object> object2Map(Object object) {
		try {
			return BeanUtils.describe(object);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args) {
		ReportPojo t_result = new ReportPojo();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("c_order_type", "c_order_type");
		map2Object(map, t_result);
		System.out.println(t_result.getOrderType());

		map.put("order_type", "order_type");
		map2Object(map, t_result);
		System.out.println(t_result.getOrderType());
	}
}

class BeanUtilsBeanCustom extends BeanUtilsBean {
	public void setProperty(Object bean, String name, Object value) throws IllegalAccessException,
			InvocationTargetException {
		name = getName(name);
		super.setProperty(bean, name, value);
	}

	public String getName(String name) {
		if (name.startsWith("c_")) {
			name = name.substring(2);
		}
		while (name.indexOf("_") > 0) {
			name = name.substring(0, name.indexOf("_"))
					+ String.valueOf(name.charAt(name.indexOf("_") + 1)).toUpperCase()
					+ name.substring(name.indexOf("_") + 2);
		}
		return name;
	}

	public String getProperty(Object bean, String name) throws IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {
		name = getName(name);
		return (getNestedProperty(bean, name));

	}
}