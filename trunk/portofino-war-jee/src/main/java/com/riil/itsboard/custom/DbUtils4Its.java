package com.riil.itsboard.custom;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Session;
import org.hibernate.jdbc.Work;

import com.manydesigns.elements.stripes.ElementsActionBeanContext;
import com.manydesigns.portofino.modules.DatabaseModule;
import com.manydesigns.portofino.persistence.Persistence;
import com.manydesigns.portofino.persistence.QueryUtils;
import com.manydesigns.portofino.util.DbUtils;
import com.riil.itsboard.utils.GroovyUtils;

public class DbUtils4Its {
	static String defaultDbName = "riil_its_board";

	public static Session getSession() {
		return DbUtils.getPersistence().getSession(DbUtils4Its.getDbName());
	}

	public static Persistence getPersistence() {
		return DbUtils.getPersistence();
	}

	public static void callProcedure(Persistence persistence, final String sql) {

		getSession().doWork(new Work() {
			public void execute(Connection connection) throws SQLException {
				CallableStatement procStatement = null;
				try {
					System.err.println(sql);
					procStatement = connection.prepareCall(sql);

					procStatement.execute();
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (null != procStatement) {
						procStatement.close();
					}
				}
			}
		});

	}

	public static void runSql(Persistence persistence, final String sql) {
		QueryUtils.runSqlDml(persistence.getSession(DbUtils4Its.getDbName()), sql);
	}

	public static void runSql(final String sql) {
		QueryUtils.runSqlDml(getSession(), sql);
	}

	public static List<Map<String, Object>> runSqlReturnMap(String sql, HttpServletRequest request) {
		return runSqlReturnMap(getDbName(), sql, request);
	}

	public static List<Map<String, Object>> runSqlReturnMap(String sql, Persistence persistence) {
		List<Map<String, Object>> mapList = QueryUtils.runSqlReturnMap(persistence.getSession(getDbName()), sql);
		return mapList;
	}

	public static List<Map<String, Object>> runSqlReturnMap(String sql) {
		List<Map<String, Object>> mapList = QueryUtils.runSqlReturnMap(getSession(), sql);
		return mapList;
	}

	public static List<Map<String, Object>> runSqlReturnMap(String dbName, String sql, HttpServletRequest request) {
		ElementsActionBeanContext context = new ElementsActionBeanContext();
		context.setRequest(request);
		sql = GroovyUtils.getQuerySql4RegionParam(sql, context);
		System.err.println(sql);
		Persistence persistence = (Persistence) request.getServletContext().getAttribute(DatabaseModule.PERSISTENCE);
		List<Map<String, Object>> mapList = QueryUtils.runSqlReturnMap(persistence.getSession(dbName), sql);
		return mapList;
	}

	public static List<String> getColumnName(List<Map<String, Object>> data, String sql) {
		List<String> t_result = new ArrayList<String>();
		for (Map<String, Object> t_map : data) {
			for (String columnName : t_map.keySet()) {
				String t_remark = CustomProperties.getInstance().getColumnRemarks(columnName);
				t_result.add(t_remark);
			}
			break;
		}

		return t_result;
	}

	public static Map<String, String> getColumnRemark(String tableName) {
		String schema = "itsboard200";
		String columnSql = "SELECT column_name,column_comment FROM INFORMATION_SCHEMA.columns WHERE TABLE_SCHEMA='"
				+ schema + "' AND TABLE_NAME='" + tableName + "'";
		Map<String, String> t_result = new HashMap<String, String>();
		List<Map<String, Object>> t_comments = runSqlReturnMap(columnSql, getPersistence());
		for (Map<String, Object> t_map : t_comments) {
			for (Entry<String, Object> t_column : t_map.entrySet()) {
				if (null != t_column.getValue()) {
					t_result.put(t_column.getKey(), t_column.getValue().toString());
				}
			}
		}
		return t_result;
	}

	public static List<List<String>> getRowValues(List<Map<String, Object>> data, String sql) {
		List<List<String>> t_result = new ArrayList<List<String>>();
		for (Map<String, Object> t_map : data) {
			List<String> t_row = new ArrayList<String>();
			for (Object value : t_map.values()) {
				if (value != null) {
					t_row.add(value.toString());
				} else {
					t_row.add("");
				}
			}
			t_result.add(t_row);
		}

		return t_result;
	}

	@SuppressWarnings("unchecked")
	public static List<Map<String, Object>> getRegions(HttpServletRequest request) {
		Persistence persistence = (Persistence) request.getServletContext().getAttribute(DatabaseModule.PERSISTENCE);
		List<Object> list = QueryUtils
				.getObjects(persistence.getSession(getDbName()), "from t_dict_region", null, null);
		List<Map<String, Object>> regions = new java.util.ArrayList<Map<String, Object>>();
		for (Object itm : list) {
			regions.add((HashMap<String, Object>) itm);
		}
		return regions;
	}

	public static String getDbName() {
		return defaultDbName;
	}

}
