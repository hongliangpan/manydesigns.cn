/*
 * Copyright (C) 2005-2013 ManyDesigns srl. All rights reserved. http://www.manydesigns.com/ This is free software; you
 * can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation; either version 3 of the License, or (at your option) any later version. This software is
 * distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details. You
 * should have received a copy of the GNU Lesser General Public License along with this software; if not, write to the
 * Free Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA, or see the FSF site:
 * http://www.fsf.org.
 */

package com.manydesigns.portofino.sync;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import liquibase.database.DatabaseConnection;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.database.structure.ForeignKeyConstraintType;
import liquibase.snapshot.DatabaseSnapshot;
import liquibase.snapshot.DatabaseSnapshotGeneratorFactory;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.manydesigns.elements.util.ReflectionUtil;
import com.manydesigns.portofino.model.Annotated;
import com.manydesigns.portofino.model.Annotation;
import com.manydesigns.portofino.model.Model;
import com.manydesigns.portofino.model.database.Column;
import com.manydesigns.portofino.model.database.ConnectionProvider;
import com.manydesigns.portofino.model.database.Database;
import com.manydesigns.portofino.model.database.DatabaseLogic;
import com.manydesigns.portofino.model.database.ForeignKey;
import com.manydesigns.portofino.model.database.Generator;
import com.manydesigns.portofino.model.database.ModelSelectionProvider;
import com.manydesigns.portofino.model.database.PrimaryKey;
import com.manydesigns.portofino.model.database.PrimaryKeyColumn;
import com.manydesigns.portofino.model.database.Reference;
import com.manydesigns.portofino.model.database.Schema;
import com.manydesigns.portofino.model.database.Table;

/**
 * @author Paolo Predonzani - paolo.predonzani@manydesigns.com
 * @author Angelo Lupo - angelo.lupo@manydesigns.com
 * @author Giampiero Granatella - giampiero.granatella@manydesigns.com
 * @author Alessio Stalla - alessio.stalla@manydesigns.com
 */
public class DatabaseSyncer {
	public static final String copyright = "Copyright (c) 2005-2013, ManyDesigns srl";

	public static final Logger logger = LoggerFactory.getLogger(DatabaseSyncer.class);

	protected final ConnectionProvider connectionProvider;

	public DatabaseSyncer(final ConnectionProvider connectionProvider) {
		this.connectionProvider = connectionProvider;
	}

	public Database syncDatabase(final Model sourceModel) throws Exception {
		String databaseName = connectionProvider.getDatabase().getDatabaseName();
		Database targetDatabase = new Database();
		targetDatabase.setDatabaseName(databaseName);

		Connection conn = null;
		try {
			logger.debug("Acquiring connection");
			conn = connectionProvider.acquireConnection();

			logger.debug("Reading database metadata");
			DatabaseMetaData metadata = conn.getMetaData();

			logger.debug("Creating Liquibase connection");
			DatabaseConnection liquibaseConnection = new JdbcConnection(conn);

			logger.debug("Retrieving source database");
			Database sourceDatabase = DatabaseLogic.findDatabaseByName(sourceModel, databaseName);
			if (sourceDatabase == null) {
				logger.debug("Source database not found. Creating an empty one.");
				sourceDatabase = new Database();
			}

			logger.debug("Reading schema names from metadata");
			List<Schema> schemas = connectionProvider.getDatabase().getSchemas();

			DatabaseSnapshotGeneratorFactory dsgf = DatabaseSnapshotGeneratorFactory.getInstance();

			logger.debug("Finding Liquibase database");
			DatabaseFactory databaseFactory = DatabaseFactory.getInstance();
			liquibase.database.Database liquibaseDatabase = databaseFactory
					.findCorrectDatabaseImplementation(liquibaseConnection);

			for (Schema schema : schemas) {
				logger.info("Processing schema: {}", schema.getSchemaName());
				Schema sourceSchema = DatabaseLogic.findSchemaByNameIgnoreCase(sourceDatabase, schema.getSchemaName());
				if (sourceSchema == null) {
					logger.debug("Source schema not found. Creating an empty one.");
					sourceSchema = new Schema();
					sourceSchema.setSchemaName(schema.getSchemaName());
				}

				logger.debug("Creating Liquibase database snapshot");
				DatabaseSnapshot snapshot = dsgf.createSnapshot(liquibaseDatabase, schema.getSchemaName(), null);

				logger.debug("Synchronizing schema");
				Schema targetSchema = new Schema();
				targetSchema.setDatabase(targetDatabase);
				targetDatabase.getSchemas().add(targetSchema);
				syncSchema(snapshot, sourceSchema, targetSchema);
			}
		} finally {
			connectionProvider.releaseConnection(conn);
		}
		targetDatabase.setConnectionProvider(connectionProvider);
		connectionProvider.setDatabase(targetDatabase);
		return targetDatabase;
	}

	public Schema syncSchema(final DatabaseSnapshot databaseSnapshot, final Schema sourceSchema,
			final Schema targetSchema) {
		logger.info("Synchronizing schema: {}", sourceSchema.getSchemaName());
		targetSchema.setSchemaName(sourceSchema.getSchemaName());

		syncTables(databaseSnapshot, sourceSchema, targetSchema);

		syncPrimaryKeys(databaseSnapshot, sourceSchema, targetSchema);

		syncForeignKeys(databaseSnapshot, sourceSchema, targetSchema);

		return targetSchema;
	}

	protected void syncForeignKeys(final DatabaseSnapshot databaseSnapshot, final Schema sourceSchema,
			final Schema targetSchema) {
		logger.info("Synchronizing foreign keys");
		for (liquibase.database.structure.ForeignKey liquibaseFK : databaseSnapshot.getForeignKeys()) {
			String fkName = liquibaseFK.getName();
			logger.info("Synchronizing foreign key {}", fkName);
			String fkTableName = liquibaseFK.getForeignKeyTable().getName();
			Table sourceTable = DatabaseLogic.findTableByNameIgnoreCase(sourceSchema, fkTableName);

			Table targetFromTable = DatabaseLogic.findTableByNameIgnoreCase(targetSchema, fkTableName);
			if (targetFromTable == null) {
				logger.error("Table '{}' not found in schema '{}'. Skipping foreign key: {}", new Object[] {
						fkTableName, targetSchema.getSchemaName(), fkName });
				continue;
			}

			ForeignKey targetFK = new ForeignKey(targetFromTable);
			targetFK.setName(fkName);

			liquibase.database.structure.Table liquibasePkTable = liquibaseFK.getPrimaryKeyTable();

			targetFK.setToDatabase(targetSchema.getDatabaseName());

			String pkSchemaName = liquibasePkTable.getSchema();
			String pkTableName = normalizeTableName(liquibasePkTable.getName(), databaseSnapshot);
			targetFK.setToSchema(pkSchemaName);
			targetFK.setToTableName(pkTableName);
			if (pkSchemaName == null || pkTableName == null) {
				logger.error("Null schema or table name: foreign key " + "(schema: {}, table: {}, fk: {}) "
						+ "references primary key (schema: {}, table{}). Skipping foreign key.", new Object[] {
						targetFromTable.getSchemaName(), targetFromTable.getTableName(), fkName, pkSchemaName,
						pkTableName });
				continue;
			}

			Database targetDatabase = targetSchema.getDatabase();
			Schema pkSchema = DatabaseLogic.findSchemaByNameIgnoreCase(targetDatabase, pkSchemaName);
			if (pkSchema == null) {
				logger.error("Cannot find referenced schema: {}. Skipping foreign key.", pkSchemaName);
				continue;
			}
			Table pkTable = DatabaseLogic.findTableByNameIgnoreCase(pkSchema, pkTableName);
			if (pkTable == null) {
				logger.error("Cannot find referenced table (schema: {}, table: {}). Skipping foreign key.",
						pkSchemaName, pkTableName);
				continue;
			}

			ForeignKeyConstraintType updateRule = liquibaseFK.getUpdateRule();
			if (updateRule == null) {
				updateRule = ForeignKeyConstraintType.importedKeyRestrict;
				logger.warn("Foreign key '{}' with null update rule. Using: {}", fkName, updateRule.name());
			}
			targetFK.setOnUpdate(updateRule.name());

			ForeignKeyConstraintType deleteRule = liquibaseFK.getDeleteRule();
			if (deleteRule == null) {
				deleteRule = ForeignKeyConstraintType.importedKeyRestrict;
				logger.warn("Foreign key '{}' with null delete rule. Using: {}", fkName, deleteRule.name());
			}
			targetFK.setOnDelete(deleteRule.name());

			String[] fromColumnNames = liquibaseFK.getForeignKeyColumns().split(",\\s+");
			String[] toColumnNames = liquibaseFK.getPrimaryKeyColumns().split(",\\s+");
			if (fromColumnNames.length != toColumnNames.length) {
				logger.error("Invalid foreign key {} - columns don't match", fkName);
				continue;
			}

			boolean referencesHaveErrors = false;
			for (int i = 0; i < fromColumnNames.length; i++) {
				String fromColumnName = fromColumnNames[i];
				String toColumnName = toColumnNames[i];

				Column fromColumn = DatabaseLogic.findColumnByNameIgnoreCase(targetFromTable, fromColumnName);
				if (fromColumn == null) {
					logger.error("Cannot find from column (schema: {}, table: {}, column: {}).", new Object[] {
							targetFromTable.getSchemaName(), targetFromTable.getTableName(), fromColumnName });
					referencesHaveErrors = true;
					break;
				}

				Column toColumn = DatabaseLogic.findColumnByNameIgnoreCase(pkTable, toColumnName);
				if (toColumn == null) {
					logger.error("Cannot find to column (schema: {}, table: {}, column: {}).",
							new Object[] { pkTable.getSchemaName(), pkTable.getTableName(), toColumnName });
					referencesHaveErrors = true;
					break;
				}

				Reference reference = new Reference();
				reference.setOwner(targetFK);
				reference.setFromColumn(fromColumn.getColumnName());
				reference.setToColumn(toColumn.getColumnName());
				targetFK.getReferences().add(reference);
			}

			if (referencesHaveErrors) {
				logger.error("Skipping foreign key (schema: {}, table: {}, fk: {}) because of errors.", new Object[] {
						pkTable.getSchemaName(), pkTable.getTableName(), fkName });
				continue;
			}

			// TODO ricercare per struttura? Es. rename
			ForeignKey sourceFK;
			if (sourceTable == null) {
				sourceFK = null;
			} else {
				sourceFK = DatabaseLogic.findForeignKeyByNameIgnoreCase(sourceTable, fkName);
			}

			if (sourceFK != null) {
				logger.debug("Found a foreign key with the same name in the previous version of the schema");
				targetFK.setManyPropertyName(sourceFK.getManyPropertyName());
				targetFK.setOnePropertyName(sourceFK.getOnePropertyName());
			}

			logger.debug("FK creation successfull. Adding FK to table.");
			targetFromTable.getForeignKeys().add(targetFK);
		}
	}

	protected String normalizeTableName(final String tableName, final DatabaseSnapshot databaseSnapshot) {
		String fkTableName = tableName;
		// Work around MySQL & case-insensitive dbs
		liquibase.database.structure.Table fkTable = databaseSnapshot.getTable(fkTableName);
		fkTableName = fkTable.getName();
		return fkTableName;
	}

	protected void syncPrimaryKeys(final DatabaseSnapshot databaseSnapshot, final Schema sourceSchema,
			final Schema targetSchema) {
		logger.info("Synchronizing primary keys");
		for (liquibase.database.structure.PrimaryKey liquibasePK : databaseSnapshot.getPrimaryKeys()) {
			String pkTableName = liquibasePK.getTable().getName();

			Table sourceTable = DatabaseLogic.findTableByNameIgnoreCase(sourceSchema, pkTableName);
			PrimaryKey sourcePK;
			if (sourceTable == null) {
				sourcePK = null;
			} else {
				sourcePK = sourceTable.getPrimaryKey();
			}

			Table targetTable = DatabaseLogic.findTableByNameIgnoreCase(targetSchema, pkTableName);
			if (targetTable == null) {
				logger.error("Coud not find table: {}. Skipping PK.", pkTableName);
				continue;
			}

			PrimaryKey targetPK = new PrimaryKey(targetTable);
			String primaryKeyName = liquibasePK.getName();
			targetPK.setPrimaryKeyName(primaryKeyName);

			List<String> columnNamesAsList = liquibasePK.getColumnNamesAsList();
			if (columnNamesAsList == null || columnNamesAsList.isEmpty()) {
				logger.error("Primary key (table: {}, pk: {}) has no columns. Skipping PK.", pkTableName,
						primaryKeyName);
				continue;
			}

			boolean pkColumnsHaveErrors = false;
			for (String columnName : columnNamesAsList) {
				PrimaryKeyColumn targetPKColumn = new PrimaryKeyColumn(targetPK);

				Column pkColumn = DatabaseLogic.findColumnByNameIgnoreCase(targetTable, columnName);
				if (pkColumn == null) {
					logger.error("Primary key (table: {}, pk: {}) has invalid column: {}", new Object[] { pkTableName,
							primaryKeyName, columnName });
					pkColumnsHaveErrors = true;
					break;
				}
				targetPKColumn.setColumnName(pkColumn.getColumnName());

				if (sourcePK != null) {
					PrimaryKeyColumn sourcePKColumn = sourcePK.findPrimaryKeyColumnByNameIgnoreCase(columnName);
					if (sourcePKColumn != null) {
						logger.debug("Found source PK column: {}", columnName);
						Generator sourceGenerator = sourcePKColumn.getGenerator();
						if (sourceGenerator != null) {
							logger.debug("Found generator: {}", sourceGenerator);
							Generator targetGenerator = (Generator) ReflectionUtil.newInstance(sourceGenerator
									.getClass());

							try {
								BeanUtils.copyProperties(targetGenerator, sourceGenerator);
							} catch (Exception e) {
								logger.error("Couldn't copy generator", e);
							}

							targetGenerator.setPrimaryKeyColumn(targetPKColumn);
							targetPKColumn.setGenerator(sourcePKColumn.getGenerator());
						}
					}
				}

				targetPK.getPrimaryKeyColumns().add(targetPKColumn);
			}

			if (pkColumnsHaveErrors) {
				logger.error("Primary key (table: {}, pk: {}) has problems with columns. Skipping PK.", pkTableName,
						primaryKeyName);
				continue;
			}

			logger.debug("PK creation successfull. Installing PK in table.");
			targetTable.setPrimaryKey(targetPK);
		}
	}

	protected void syncTables(final DatabaseSnapshot databaseSnapshot, final Schema sourceSchema,
			final Schema targetSchema) {
		logger.info("Synchronizing tables");
		for (liquibase.database.structure.Table liquibaseTable : databaseSnapshot.getTables()) {
			String tableName = liquibaseTable.getName();
			logger.debug("Processing table: {}", tableName);
			Table sourceTable = DatabaseLogic.findTableByNameIgnoreCase(sourceSchema, tableName);
			if (sourceTable == null) {
				sourceTable = new Table();
			}

			Table targetTable = new Table(targetSchema);
			targetSchema.getTables().add(targetTable);

			targetTable.setTableName(tableName);
			// hongliangpan add
			targetTable.setMemo(liquibaseTable.getRemarks());
			if (org.apache.commons.lang.StringUtils.isBlank(targetTable.getMemo())) {
				targetTable.setMemo(getTableComment(tableName));
			}
			logger.debug("Merging table attributes and annotations");
			targetTable.setEntityName(sourceTable.getEntityName());
			targetTable.setJavaClass(sourceTable.getJavaClass());
			targetTable.setShortName(sourceTable.getShortName());
			copyAnnotations(sourceTable, targetTable);

			syncColumns(liquibaseTable, sourceTable, targetTable);

			copySelectionProviders(sourceTable, targetTable);
		}
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

	protected void copySelectionProviders(final Table sourceTable, final Table targetTable) {
		for (ModelSelectionProvider sourceSP : sourceTable.getSelectionProviders()) {
			ModelSelectionProvider targetSP = (ModelSelectionProvider) ReflectionUtil.newInstance(sourceSP.getClass());
			try {
				BeanUtils.copyProperties(targetSP, sourceSP);
				targetSP.setFromTable(targetTable);
				targetTable.getSelectionProviders().add(targetSP);
				for (Reference sourceReference : sourceSP.getReferences()) {
					Reference targetReference = new Reference(targetSP);
					targetSP.getReferences().add(targetReference);
					targetReference.setFromColumn(sourceReference.getFromColumn());
					targetReference.setToColumn(sourceReference.getToColumn());
				}
			} catch (Exception e) {
				logger.error("Couldn't copy selection provider {}", sourceSP);
			}
		}
	}

	protected void syncColumns(final liquibase.database.structure.Table liquibaseTable, final Table sourceTable,
			final Table targetTable) {
		logger.debug("Synchronizing columns");
		for (liquibase.database.structure.Column liquibaseColumn : liquibaseTable.getColumns()) {
			logger.debug("Processing column: {}", liquibaseColumn.getName());

			Column targetColumn = new Column(targetTable);

			targetColumn.setColumnName(liquibaseColumn.getName());
			// hongliangpan add
			targetColumn.setMemo(liquibaseColumn.getRemarks());

			logger.debug("Merging column attributes and annotations");
			targetColumn.setAutoincrement(liquibaseColumn.isAutoIncrement());
			int jdbcType = liquibaseColumn.getDataType();
			if (jdbcType == Types.OTHER) {
				logger.debug("jdbcType = OTHER, trying to determine more specific type from type name");
				String jdbcTypeName = liquibaseColumn.getTypeName();
				try {
					Field field = Types.class.getField(jdbcTypeName);
					jdbcType = (Integer) field.get(null);
				} catch (Exception e) {
					logger.debug("Could not determine type (type name = {})", jdbcTypeName);
				}
			}
			targetColumn.setJdbcType(jdbcType);
			targetColumn.setColumnType(liquibaseColumn.getTypeName());
			targetColumn.setLength(liquibaseColumn.getColumnSize());

			targetColumn.setNullable(liquibaseColumn.isNullable());
			targetColumn.setScale(liquibaseColumn.getDecimalDigits());
			// TODO liquibaseColumn.getLengthSemantics()

			Column sourceColumn = DatabaseLogic.findColumnByNameIgnoreCase(sourceTable, liquibaseColumn.getName());
			if (sourceColumn != null) {
				targetColumn.setPropertyName(sourceColumn.getPropertyName());
				targetColumn.setJavaType(sourceColumn.getJavaType());
				copyAnnotations(sourceColumn, targetColumn);
				// hongliangpan add
				targetColumn.setNullable(sourceColumn.isNullable());
			}
			// hongliangpan add
			if (targetColumn.getColumnName().startsWith("c_is_")) {
				targetColumn.setJavaType(Boolean.class.getName());
			}

			// "TIMESTAMP"可以为空hongliangpan add
			if ("TIMESTAMP".equals(liquibaseColumn.getTypeName()) || "DATETIME".equals(liquibaseColumn.getTypeName())) {
				targetColumn.setNullable(true);
				addAnnotation(targetColumn, "com.manydesigns.elements.annotations.DateFormat", "yyyy-MM-dd HH:mm:ss");
			}
			logger.debug("Column creation successfull. Adding column to table.");
			targetTable.getColumns().add(targetColumn);
		}

		logger.debug("Sorting columns to preserve their previous order as much as possible");
		Collections.sort(targetTable.getColumns(), new Comparator<Column>() {
			private int oldIndex(final Column c) {
				int i = 0;
				for (Column old : sourceTable.getColumns()) {
					if (old.getColumnName().equals(c.getColumnName())) {
						return i;
					}
					i++;
				}
				return -1;
			}

			@Override
			public int compare(final Column c1, final Column c2) {
				Integer index1 = oldIndex(c1);
				Integer index2 = oldIndex(c2);
				if (index1 != -1) {
					if (index2 != -1) {
						return index1.compareTo(index2);
					} else {
						return -1;
					}
				} else {
					return index2 == -1 ? 0 : 1;
				}
			}
		});
	}

	/***
	 * hongliangpan add
	 */
	public void addAnnotation(Column targetColumn, String type, String value) {
		boolean hasThisTypeAnnotation = false;
		for (Annotation annotation : targetColumn.getAnnotations()) {
			if (type.equals(annotation.getType())) {
				hasThisTypeAnnotation = true;
				break;
			}
		}
		if (!hasThisTypeAnnotation) {
			Annotation anno = new Annotation();
			anno.setType(type);
			anno.getValues().add(value);
			targetColumn.getAnnotations().add(anno);
		}
	}

	protected void copyAnnotations(final Annotated source, final Annotated target) {
		for (Annotation ann : source.getAnnotations()) {
			Annotation annCopy = new Annotation();
			annCopy.setParent(target);
			annCopy.setType(ann.getType());
			for (String value : ann.getValues()) {
				annCopy.getValues().add(value);
			}
			target.getAnnotations().add(annCopy);
		}
	}

}
