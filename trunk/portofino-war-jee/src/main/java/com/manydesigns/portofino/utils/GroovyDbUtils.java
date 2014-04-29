package com.manydesigns.portofino.utils;

import groovy.sql.Sql;

import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.manydesigns.portofino.model.database.ConnectionProvider;
import com.manydesigns.portofino.model.database.JdbcConnectionProvider;
import com.manydesigns.portofino.util.DbUtils;

public class GroovyDbUtils {

	public static final Logger logger = LoggerFactory.getLogger(GroovyDbUtils.class);
	protected static Sql sql;
	static {
		sql = initSql();
	}

	protected static Sql initSql() {
		ConnectionProvider t_dbProvider = DbUtils.getPersistence().getModel().getDatabases().get(0)
				.getConnectionProvider();
		JdbcConnectionProvider t_jdbcProvider = (JdbcConnectionProvider) t_dbProvider;
		String url = t_jdbcProvider.getUrl();
		String user = t_jdbcProvider.getUsername();
		String password = t_jdbcProvider.getPassword();
		String driverClassName = t_jdbcProvider.getDriver();
		try {
			return Sql.newInstance(url, user, password, driverClassName);
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		} catch (ClassNotFoundException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * @return sql - {return content description}
	 */
	public static Sql getSql() {
		return sql;
	}
}
