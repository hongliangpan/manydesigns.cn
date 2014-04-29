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

package com.manydesigns.portofino.model.database;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.manydesigns.elements.annotations.Required;
import com.manydesigns.elements.util.ReflectionUtil;
import com.manydesigns.portofino.database.Type;
import com.manydesigns.portofino.model.Annotated;
import com.manydesigns.portofino.model.Annotation;
import com.manydesigns.portofino.model.Model;
import com.manydesigns.portofino.model.ModelObject;
import com.manydesigns.portofino.model.ModelObjectVisitor;

/*
 * @author Paolo Predonzani - paolo.predonzani@manydesigns.com
 * @author Angelo Lupo - angelo.lupo@manydesigns.com
 * @author Giampiero Granatella - giampiero.granatella@manydesigns.com
 * @author Alessio Stalla - alessio.stalla@manydesigns.com
 */
@XmlAccessorType(XmlAccessType.NONE)
public class Column implements ModelObject, Annotated {
	public static final String copyright = "Copyright (c) 2005-2013, ManyDesigns srl";

	// **************************************************************************
	// Fields (physical JDBC)
	// **************************************************************************

	protected Table table;
	protected String columnName;
	protected int jdbcType;
	protected String columnType;
	protected boolean nullable;
	protected boolean autoincrement;
	protected Integer length;
	protected Integer scale;
	// hongliangpan add 备注
	protected String memo;

	// **************************************************************************
	// Fields (logical)
	// **************************************************************************

	protected String javaType;
	protected String propertyName;
	protected final List<Annotation> annotations;

	// **************************************************************************
	// Fields for wire-up
	// **************************************************************************

	protected String actualPropertyName;
	protected Class actualJavaType;

	public static final Logger logger = LoggerFactory.getLogger(Column.class);

	// **************************************************************************
	// Constructors and init
	// **************************************************************************
	public Column() {
		annotations = new ArrayList<Annotation>();
	}

	public Column(final Table table) {
		this();
		this.table = table;
	}

	// **************************************************************************
	// ModelObject implementation
	// **************************************************************************

	public String getQualifiedName() {
		return MessageFormat.format("{0}.{1}", table.getQualifiedName(), columnName);
	}

	@Override
	public void afterUnmarshal(final Unmarshaller u, final Object parent) {
		table = (Table) parent;
	}

	@Override
	public void reset() {
		actualPropertyName = null;
		actualJavaType = null;
	}

	@Override
	public void init(final Model model) {
		assert table != null;
		// TODO questi assert dovrebbero essere test + throw exception
		assert columnName != null;
		assert columnType != null;
		assert length != null;
		assert scale != null;

		if (propertyName == null) {
			actualPropertyName = DatabaseLogic.getUniquePropertyName(table, DatabaseLogic.normalizeName(columnName));
		} else {
			actualPropertyName = propertyName; // AS do not normalize (can be mixed-case Java properties)
		}

		if (javaType != null) {
			actualJavaType = ReflectionUtil.loadClass(javaType);
			if (actualJavaType == null) {
				logger.warn("Cannot load column {} of java type: {}", getQualifiedName(), javaType);
			}
		} else {
			actualJavaType = Type.getDefaultJavaType(jdbcType, length, scale);
			if (actualJavaType == null) {
				logger.error(
						"Cannot determine default Java type for table: {}, column: {}, jdbc type: {}, type name: {}. Skipping column.",
						new Object[] { table.getTableName(), getColumnName(), jdbcType, javaType });
			}
		}
	}

	@Override
	public void link(final Model model) {
	}

	@Override
	public void visitChildren(final ModelObjectVisitor visitor) {
		for (Annotation annotation : annotations) {
			visitor.visit(annotation);
		}
	}

	// **************************************************************************
	// Getters/setter
	// **************************************************************************

	public Table getTable() {
		return table;
	}

	public void setTable(final Table table) {
		this.table = table;
	}

	public String getDatabaseName() {
		return table.getDatabaseName();
	}

	public String getSchemaName() {
		return table.getSchemaName();
	}

	public String getTableName() {
		return table.getTableName();
	}

	@Required
	@XmlAttribute(required = true)
	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(final String columnName) {
		this.columnName = columnName;
	}

	@XmlAttribute(required = true)
	public int getJdbcType() {
		return jdbcType;
	}

	public void setJdbcType(final int jdbcType) {
		this.jdbcType = jdbcType;
	}

	@Required
	@XmlAttribute(required = true)
	public String getColumnType() {
		return columnType;
	}

	public void setColumnType(final String columnType) {
		this.columnType = columnType;
	}

	@XmlAttribute(required = true)
	public boolean isNullable() {
		return nullable;
	}

	public void setNullable(final boolean nullable) {
		this.nullable = nullable;
	}

	@XmlAttribute(required = true)
	public Integer getLength() {
		return length;
	}

	public void setLength(final Integer length) {
		this.length = length;
	}

	@XmlAttribute(required = true)
	public Integer getScale() {
		return scale;
	}

	public void setScale(final Integer scale) {
		this.scale = scale;
	}

	@XmlAttribute(required = true)
	public boolean isAutoincrement() {
		return autoincrement;
	}

	public void setAutoincrement(final boolean autoincrement) {
		this.autoincrement = autoincrement;
	}

	public boolean isSearchable() {
		// TODO: Blobs are not searchable but Liquibase does not return this information
		return true;
	}

	public Class getActualJavaType() {
		return actualJavaType;
	}

	@XmlAttribute(required = false)
	public String getJavaType() {
		return javaType;
	}

	public void setJavaType(final String javaType) {
		this.javaType = javaType;
	}

	public String getActualPropertyName() {
		return actualPropertyName;
	}

	@XmlAttribute(required = false)
	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(final String propertyName) {
		this.propertyName = propertyName;
	}

	@Override
	@XmlElementWrapper(name = "annotations")
	@XmlElement(name = "annotation", type = Annotation.class)
	public List<Annotation> getAnnotations() {
		return annotations;
	}

	@Override
	public String toString() {
		return MessageFormat.format("column {0} {1}({2},{3}){4}", getQualifiedName(), columnType,
				Integer.toString(length), Integer.toString(scale), nullable ? "" : " NOT NULL");
	}

	// **************************************************************************
	// Utility methods
	// **************************************************************************

	public static String composeQualifiedName(final String databaseName, final String schemaName,
			final String tableName, final String columnName) {
		return MessageFormat.format("{0}.{1}.{2}.{3}", databaseName, schemaName, tableName, columnName);
	}

	public Annotation findModelAnnotationByType(final String annotationType) {
		for (Annotation annotation : annotations) {
			if (annotation.getType().equals(annotationType)) {
				return annotation;
			}
		}
		return null;
	}

	/**
	 * @return memo -memo
	 */
	public final String getMemo() {
		return memo;
	}

	/**
	 * @param memo - memo.
	 */
	public final void setMemo(final String memo) {
		this.memo = memo;
	}
}
