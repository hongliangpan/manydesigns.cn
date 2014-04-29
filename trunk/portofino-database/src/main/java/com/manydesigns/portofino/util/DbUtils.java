package com.manydesigns.portofino.util;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.jdbc.Work;

import com.manydesigns.portofino.persistence.Persistence;
import com.manydesigns.portofino.persistence.QueryUtils;

public class DbUtils {
	// static String defaultDbName = "riil_its_board";
	private static Persistence persistence;

	public static void callProcedure(Persistence persistence, final String sql) {
		Session session = persistence.getSession(getDbName());

		session.doWork(new Work() {
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

	public static List<Map<String, Object>> runSqlReturnMap(String sql, Persistence persistence) {
		List<Map<String, Object>> mapList = QueryUtils.runSqlReturnMap(persistence.getSession(getDbName()), sql);
		return mapList;
	}

	public static String getDbName() {
		return persistence.getModel().getDatabases().get(0).getDatabaseName();
		// return defaultDbName;
	}

	public static Persistence getPersistence() {
		return persistence;
	}

	public static void setPersistence(Persistence persistence) {
		DbUtils.persistence = persistence;
	}
}
