package com.manydesigns.portofino.pageactions.chart;

import javax.servlet.http.HttpServletRequest;

import com.riil.itsboard.custom.DbUtils4Its;

public class ChartDataQueryUtils {

	public static String getDbName() {
		return DbUtils4Its.getDbName();
	}

	public static String getQuerySql(HttpServletRequest request) {
		// TODO 还需要传递页面的 区域等查询参数
		ChartParams param = ChartParams.instance(request);
		if (param.getTable().indexOf("t_project") >= 0) {
			return ProjectSqlUtils.getQuerySql(param);
		}

		if (param.getTable().indexOf("t_customer") >= 0) {
			return CustomerSqlUtils.getQuerySql(param);
		}
		return "";
	}

}
