package com.riil.itsboard.utils;

import java.text.MessageFormat;

import javax.servlet.http.HttpServletRequest;

import com.manydesigns.elements.stripes.ElementsActionBeanContext;
import com.riil.itsboard.custom.Params;

public class GroovyUtils {

	public enum RegionType {
		WHERE, AND
	}

	// protected String where= " WHERE CONCAT(c_region1,c_region2,c_region3) LIKE '%{0}%'";
	protected static final String REGION_PART = " (c_region1 ='{0}' OR c_region2 ='{0}' OR c_region3 ='{0}') ";
	protected static final String REGION_ALL = " 1=1 ";

	public static String replaceSql4MessageFormat(final String sql) {
		return sql.replaceAll("'", "''");
	}

	public static String getQuerySql4RegionParam(String sqlTemplate, HttpServletRequest request) {
		ElementsActionBeanContext context = new ElementsActionBeanContext();
		context.setRequest(request);
		return GroovyUtils.getQuerySql4RegionParam(sqlTemplate, context);
	}

	public static String getQuerySql4RegionParam(String sqlTemplate, ElementsActionBeanContext context) {
		String resultSql = GroovyUtils.replaceSql4MessageFormat(sqlTemplate);
		String where;
		// String region = context.getRequest().getParameter("region");
		Params params = Params.getParams(context.getRequest(), "");
		String region = params.getRegionDisplay();

		if (region == null || region.length() == 0 || "全国".equals(region)) {
			where = REGION_ALL;
		} else {
			where = GroovyUtils.replaceSql4MessageFormat(REGION_PART);
		}

		if (region.indexOf(",") >= 0) {
			String[] regions = region.split(",");
			StringBuilder wheres = new StringBuilder("(");
			for (int i = 1; i <= 3; i++) {
				for (String oneRegion : regions) {
					wheres.append("c_region").append(i).append("='").append(oneRegion).append("' OR ");
				}
			}
			where = wheres.substring(0, wheres.lastIndexOf("OR") - 1) + ")";
			where = GroovyUtils.replaceSql4MessageFormat(where);
		}
		resultSql = replace(resultSql);
		if (sqlTemplate.toUpperCase().indexOf("WHERE") < 0) {
			where = " WHERE" + where;
		}
		resultSql = MessageFormat.format(resultSql, MessageFormat.format(where, region));
		return resultSql;
	}

	private static String replace(String resultSql) {
		return resultSql;
	}
}
