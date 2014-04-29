package com.manydesigns.portofino.report.actions;

import java.util.HashMap;

import com.manydesigns.portofino.persistence.Persistence;
import com.riil.itsboard.custom.DbUtils4Its;

public class ReportPeriodUtils {

	public static void insertRptDefPeriod(Persistence persistence, Object object) {
		Object rptDefId = ((HashMap) object).get("c_id");
		if (null == rptDefId) {
			return;
		}
		String sql = buildSql(rptDefId.toString());
		DbUtils4Its.runSql(sql);
	}

	public static String buildSql(String rptDefId) {
		return "INSERT INTO t_rpt_def_period (c_id,c_rpt_def_id,c_period_type_id,c_sort_id,c_tag1)"
				+ "SELECT SEQ('def_period'),'" + rptDefId
				+ "',c_id,c_sort_id,c_name FROM t_rptd_period_type WHERE c_is_enabled =1"
				+ " AND NOT EXISTS (SELECT c_id FROM t_rpt_def_period WHERE c_rpt_def_id='" + rptDefId + "')"
				+ " ORDER BY c_sort_id";
	}

	public static void insertRptDefParam(Persistence persistence, Object object) {
		Object rptDefId = ((HashMap) object).get("c_id");
		if (null == rptDefId) {
			return;
		}
		String sql = buildRptDefParamSql(rptDefId.toString());
		DbUtils4Its.runSql(sql);
	}

	public static String buildRptDefParamSql(String rptDefId) {
		return "INSERT INTO t_rpt_def_param (c_id,c_rpt_def_id,c_field,c_display_name,c_tips,c_is_single,c_is_query_param,c_is_build_param,c_display_style)"
				+ "SELECT SEQ('def_param'),'"
				+ rptDefId
				+ "','c_time','时间','查询时间','1',1,0,'date' FROM DUAL"
				+ " WHERE NOT EXISTS (SELECT c_id FROM t_rpt_def_param WHERE c_rpt_def_id='" + rptDefId + "')";
	}
}
