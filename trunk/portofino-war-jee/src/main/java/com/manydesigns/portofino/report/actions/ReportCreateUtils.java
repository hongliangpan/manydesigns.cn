package com.manydesigns.portofino.report.actions;

import java.util.HashMap;

import com.manydesigns.portofino.persistence.Persistence;
import com.riil.itsboard.custom.DbUtils4Its;

public class ReportCreateUtils {

	public static void insertRptField(Persistence persistence, Object object) {

		String rptId = ((HashMap) object).get("c_id").toString();
		String rptDefId = ((HashMap) object).get("c_rpt_def_id").toString();
		DbUtils4Its.runSql(persistence, "DELETE FROM t_rpt_field WHERE c_rpt_id='" + rptId + "'");

		String sql = "INSERT INTO t_rpt_field (c_id,c_rpt_id,c_def_field_id,c_calc_exp,c_is_display,c_display_name)"
				+ " SELECT SEQ('field'),'" + rptId
				+ "',c_id,c_calc_exp,1,c_display_name FROM t_rpt_def_field WHERE c_rpt_def_id ='" + rptDefId + "'"
				+ " AND NOT EXISTS (SELECT c_id FROM t_rpt_field WHERE c_rpt_id='" + rptId + "')"
				+ " ORDER BY c_sort_id";

		DbUtils4Its.runSql(sql);
	}

	public static void insertRptParam(Persistence persistence, Object object) {
		String rptId = ((HashMap) object).get("c_id").toString();
		String rptDefId = ((HashMap) object).get("c_rpt_def_id").toString();
		// TODO
		String sql = "";

		// DbUtils4Its.runSql(sql);

	}

	public static void insertRptPeriod(Persistence persistence, Object object) {
		String rptId = ((HashMap) object).get("c_id").toString();
		String rptDefId = ((HashMap) object).get("c_rpt_def_id").toString();
		DbUtils4Its.runSql(persistence, "DELETE FROM t_rpt_period WHERE c_rpt_id='" + rptId + "'");

		String sql = "INSERT INTO t_rpt_period (c_id,c_rpt_id,c_def_period_id,c_period_type_id)\n"
				+ "SELECT SEQ('period'),'" + rptId
				+ "',c_id,c_period_type_id FROM t_rpt_def_period WHERE c_rpt_def_id ='" + rptDefId + "'\n"
				+ " AND NOT EXISTS (SELECT c_id FROM t_rpt_period WHERE c_rpt_id='" + rptId + "')\n"
				+ " ORDER BY c_sort_id";

		DbUtils4Its.runSql(sql);
	}
}
