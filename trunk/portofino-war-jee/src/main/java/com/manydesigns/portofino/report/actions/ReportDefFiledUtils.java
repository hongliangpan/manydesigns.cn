package com.manydesigns.portofino.report.actions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;

import com.manydesigns.portofino.persistence.Persistence;
import com.manydesigns.portofino.report.enums.DataType;
import com.manydesigns.portofino.report.pojo.DbColumn;
import com.manydesigns.portofino.report.utils.SqlUtils;
import com.riil.itsboard.custom.DbUtils4Its;

public class ReportDefFiledUtils {

	public static void insertRptDefField(Persistence persistence, Object object) {
		Object sql = ((HashMap) object).get("c_sql");
		Object rptDefId = ((HashMap) object).get("c_id");
		if (null == sql) {
			return;
		}
		List<DbColumn> t_columns = SqlUtils.getDbColumns(sql.toString());
		DbUtils4Its.runSql(persistence, "DELETE FROM t_rpt_def_field WHERE c_rpt_def_id='" + rptDefId + "'");
		Map<String, String> columnRemarks = getColumnRemark(sql.toString());
		List<Map<String, Object>> fieldMap = getFieldMap();
		int i = 1;
		for (DbColumn t_field : t_columns) {
			Map<String, Object> fieldInfo = getFiledInfo(fieldMap, t_field.getName());
			String insertSql = buildDefFiledSql(rptDefId.toString(), t_field, columnRemarks, fieldInfo, i);
			System.err.println(insertSql);
			DbUtils4Its.runSql(persistence, insertSql);
			i++;
		}
	}

	public static Map<String, String> getColumnRemark(String reportSql) {
		String tableName = SqlUtils.parseTables(reportSql);
		return DbUtils4Its.getColumnRemark(tableName);
	}

	public static List<Map<String, Object>> getFieldMap() {
		String sql = "SELECT * FROM t_rptd_field_map";
		return DbUtils4Its.runSqlReturnMap(sql);
	}

	public static Map<String, Object> getFiledInfo(List<Map<String, Object>> fieldMap, String field) {
		for (Map<String, Object> t_map : fieldMap) {
			if (t_map.get("c_id").equals(field)) {
				return t_map;
			}
		}
		return new HashMap<String, Object>();
	}

	public static String getFiledDisplayName(Map<String, String> columnRemarks, Map<String, Object> fieldInfo,
			String fieldName, DbColumn column) {
		String t_displayName = column.getRemarks();
		if (fieldInfo.size() > 0 && null != fieldInfo.get("c_name")) {
			t_displayName = fieldInfo.get("c_name").toString();
			if (null != fieldInfo.get("c_unit") && !"".equals(fieldInfo.get("c_unit").toString().trim())) {
				t_displayName += "(" + fieldInfo.get("c_unit") + ")";
			}
		}
		if (null == t_displayName) {
			t_displayName = columnRemarks.get(fieldName);
		}
		if (null == t_displayName) {
			t_displayName = columnRemarks.get(getSimpleName(fieldName));
		}
		if (null == t_displayName) {
			t_displayName = columnRemarks.get(getSimpleName(getSimpleName(fieldName)));
		}
		if (null == t_displayName) {
			t_displayName = fieldName;
		}
		return t_displayName;
	}

	public static String getSimpleName(String fieldName) {
		if (fieldName.indexOf("_") < 0) {
			return fieldName;
		}
		return fieldName.substring(0, fieldName.lastIndexOf("_"));
	}

	public static String getFiledDataType(Map<String, String> columnRemarks, Map<String, Object> fieldInfo,
			String fieldName, DbColumn column) {
		String dataType = null;
		if (fieldInfo.size() > 0 && null != fieldInfo.get("c_data_type")) {
			dataType = fieldInfo.get("c_data_type").toString();
		}

		if (StringUtils.isBlank(dataType)) {
			dataType = column.getColumnType();
		}
		if (null == dataType) {
			return DataType.string.getName();
		}
		return DataType.valueOfJdbcType(dataType).getName();
	}

	public static String buildDefFiledSql(String rptDefId, DbColumn column, Map<String, String> columnRemarks,
			Map<String, Object> fieldInfo, int sortId) {
		String t_id = UUID.randomUUID().toString();
		String t_fieldName = column.getName();
		String t_displayName;
		String t_dataType;

		t_displayName = getFiledDisplayName(columnRemarks, fieldInfo, t_fieldName, column);
		t_dataType = getFiledDataType(columnRemarks, fieldInfo, t_fieldName, column);

		boolean c_is_group = false;
		boolean c_is_sum = false;
		boolean c_is_query_time = false;
		boolean c_is_percentage = false;
		String c_sum_method = "";
		if (t_fieldName.indexOf("_group") > 0) {
			c_is_group = true;
		}

		if (t_fieldName.indexOf("_avg") > 0) {
			c_is_sum = true;
			c_sum_method = "avg";
			t_dataType = DataType.integer.name();
		}
		if (t_fieldName.indexOf("_sum") > 0) {
			c_is_sum = true;
			c_sum_method = "sum";
			t_dataType = DataType.integer.name();
		}
		if (t_fieldName.indexOf("_count") > 0) {
			c_is_sum = true;
			c_sum_method = "count";
			t_dataType = DataType.integer.name();
		}

		if (t_fieldName.indexOf("_query_time") > 0) {
			c_is_query_time = true;
		}

		if (t_fieldName.indexOf("_percentage") > 0) {
			c_is_percentage = true;
		}

		return "INSERT INTO t_rpt_def_field ( c_id, c_rpt_def_id, c_field, c_display_name, c_data_type, c_is_group, c_is_sum, c_sum_method, c_is_percentage, c_is_query_time,c_sort_id )"
				+ "VALUES" + "	(" + "		'"
				+ t_id
				+ "','"
				+ rptDefId
				+ "','"
				+ t_fieldName
				+ "','"
				+ t_displayName
				+ "','"
				+ t_dataType
				+ "','"
				+ (c_is_group ? 1 : 0)
				+ "','"
				+ (c_is_sum ? 1 : 0)
				+ "','"
				+ c_sum_method
				+ "','"
				+ (c_is_percentage ? 1 : 0) + "','" + (c_is_query_time ? 1 : 0) + "'," + sortId + ");";
	}
}
