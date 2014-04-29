package com.riil.itsboard.utils;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import liquibase.database.DatabaseConnection;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.snapshot.DatabaseSnapshot;
import liquibase.snapshot.DatabaseSnapshotGeneratorFactory;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.manydesigns.portofino.model.database.Database;
import com.manydesigns.portofino.model.database.Schema;
import com.manydesigns.portofino.sync.DatabaseSyncer;
import com.manydesigns.portofino.utils.FileUtils;

public class ColumnRemarkTypeGenerate {
	private static final String S_SCHEMA = "itsboard200";
	public static final Logger logger = LoggerFactory.getLogger(DatabaseSyncer.class);
	JdbcConnectionProviderTemp connectionProvider;
	Map<String, String> columnMap = new LinkedHashMap<String, String>();

	public static String insertHead = "INSERT INTO t_rptd_field_map (c_id, c_name, c_data_type, c_unit, c_create_user, c_create_time, c_modify_user, c_modify_time, c_tag1, c_tag2, c_tag3, c_tag4) VALUES (";
	public static String insertTail = "', NULL, NULL, NULL, NULL, '2014-3-1 17:47:00', NULL, NULL, NULL, NULL);";

	// 使用 liquibase 生成 列表注释
	// 表名.列名=中文

	public Database syncDatabase() throws Exception {
		columnMap.put("t_customer.c_name", insertHead + "'t_customer.c_name','客户名称','string" + insertTail);
		initJdbc();
		String databaseName = connectionProvider.getDatabase().getDatabaseName();
		Database targetDatabase = new Database();
		targetDatabase.setDatabaseName(databaseName);

		Connection conn = null;
		try {
			logger.debug("Acquiring connection");
			conn = connectionProvider.acquireConnection();

			logger.debug("Reading database metadata");
			// DatabaseMetaData metadata = conn.getMetaData();

			logger.debug("Creating Liquibase connection");
			DatabaseConnection liquibaseConnection = new JdbcConnection(conn);

			logger.debug("Reading schema names from metadata");
			List<Schema> schemas = connectionProvider.getDatabase().getSchemas();

			DatabaseSnapshotGeneratorFactory dsgf = DatabaseSnapshotGeneratorFactory.getInstance();

			logger.debug("Finding Liquibase database");
			DatabaseFactory databaseFactory = DatabaseFactory.getInstance();
			liquibase.database.Database liquibaseDatabase = databaseFactory
					.findCorrectDatabaseImplementation(liquibaseConnection);
			Schema t_schema = new Schema();
			t_schema.setDatabase(connectionProvider.getDatabase());
			t_schema.setSchemaName(S_SCHEMA);
			schemas.add(t_schema);
			for (Schema schema : schemas) {
				if (!S_SCHEMA.equals(schema.getSchemaName())) {
					continue;
				}

				logger.debug("Creating Liquibase database snapshot");
				DatabaseSnapshot snapshot = dsgf.createSnapshot(liquibaseDatabase, schema.getSchemaName(), null);

				logger.debug("Synchronizing schema");
				syncSchema(snapshot, schema);
			}
		} finally {
			connectionProvider.releaseConnection(conn);
		}
		targetDatabase.setConnectionProvider(connectionProvider);
		connectionProvider.setDatabase(targetDatabase);
		return targetDatabase;
	}

	public void initJdbc() {
		connectionProvider = new JdbcConnectionProviderTemp();
		connectionProvider.setDriver("com.mysql.jdbc.Driver");
		connectionProvider.setUsername("riil");
		connectionProvider.setPassword("r4rfde32wsaq1");
		connectionProvider
				.setUrl("jdbc:mysql://172.17.185.23:3306/itsboard200?useUnicode=true&amp;amp;characterEncoding=UTF-8");

		Database targetDatabase = new Database();
		targetDatabase.setDatabaseName(S_SCHEMA);

		connectionProvider.setDatabase(targetDatabase);
	}

	public Schema syncSchema(final DatabaseSnapshot databaseSnapshot, final Schema sourceSchema) {
		logger.info("Synchronizing schema: {}", sourceSchema.getSchemaName());

		syncTables(databaseSnapshot, sourceSchema);

		return sourceSchema;
	}

	protected void syncTables(final DatabaseSnapshot databaseSnapshot, final Schema sourceSchema) {
		logger.info("Synchronizing tables");
		for (liquibase.database.structure.Table liquibaseTable : databaseSnapshot.getTables()) {
			String tableName = liquibaseTable.getName();

			saveRemarks(tableName, getTableComment(tableName) + "', 'string");

			syncColumns(liquibaseTable);

		}
	}

	protected void syncColumns(final liquibase.database.structure.Table liquibaseTable) {
		logger.debug("Synchronizing columns");
		for (liquibase.database.structure.Column liquibaseColumn : liquibaseTable.getColumns()) {
			logger.debug("Processing column: {}", liquibaseColumn.getName());

			// hongliangpan add
			String t_remarkType = liquibaseColumn.getRemarks() + "', '"
					+ getType(liquibaseColumn.getTypeName(), liquibaseColumn.getName() + "'");
			// saveRemarks(liquibaseTable.getName() + "." + liquibaseColumn.getName(), t_remarkType);
			saveRemarks(liquibaseColumn.getName(), t_remarkType);
		}

	}

	private String getType(String typeName, String column) {
		if (StringUtils.isBlank(typeName)) {
			return "string";
		}
		if ("INT".equals(typeName)) {
			return "integer";
		}
		if ("VARCHAR".equals(typeName) || "TEXT".equals(typeName)) {
			return "string";
		}
		if ("DATE".equals(typeName)) {
			return "date";
		}
		if ("FLOAT".equals(typeName)) {
			return "float";
		}
		if ("DATETIME".equals(typeName) || "TIMESTAMP".equals(typeName)) {
			return "dateyeartominute";
		}
		if ("TINYINT".equals(typeName) && column.toLowerCase().indexOf("_is_") > 0) {
			return "boolean";
		}
		if ("".equals(typeName)) {
			return "";
		}
		if ("".equals(typeName)) {
			return "";
		}
		if ("".equals(typeName)) {
			return "";
		}
		return "string";
	}

	public void saveRemarks(String key, String value) {
		// System.out.println(msg);
		columnMap.put(key, insertHead + "'" + key + "','" + value + insertTail);
	}

	public void printlnColumnMap() throws IOException {

		FileUtils.writeLines(new File("d:/columnName.sql"), columnMap.values());
	}

	// hongliangpan add
	private String getTableComment(String tableName) {
		Statement st = null;
		ResultSet rs = null;
		Connection conn = null;
		String result = "";
		try {
			conn = connectionProvider.acquireConnection();
			st = conn.createStatement();
			rs = st.executeQuery("SELECT TABLE_COMMENT FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_NAME='" + tableName
					+ "'");
			if (rs.next()) {
				return rs.getString(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null) {
					rs.close();
					rs = null;
				}
				if (st != null) {
					st.close();
					st = null;
				}
				if (conn != null) {
					conn.close();
					conn = null;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public static void main(String[] args) throws Exception {
		ColumnRemarkTypeGenerate generate = new ColumnRemarkTypeGenerate();
		generate.syncDatabase();
		generate.printlnColumnMap();
	}
}
