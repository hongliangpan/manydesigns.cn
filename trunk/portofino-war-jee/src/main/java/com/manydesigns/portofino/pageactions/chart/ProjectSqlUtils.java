package com.manydesigns.portofino.pageactions.chart;

import java.text.MessageFormat;

import com.riil.itsboard.custom.DbUtils4Its;

public class ProjectSqlUtils {
	private static final String S_PROJECT_STATE = "SELECT s.c_name c_state_name,p.* FROM t_project p\n"
			+ "INNER JOIN t_dict_state s ON p.c_state=s.c_id\n" + "WHERE s.c_name ='{0}'";

	public static String getDbName() {
		return DbUtils4Its.getDbName();
	}

	public static String getQuerySql(ChartParams param) {
		if (param.getType().equalsIgnoreCase("project_state")) {
			return MessageFormat.format(S_PROJECT_STATE.replaceAll("'", "''"), param.getKey());
		}
		return "";
	}
}
