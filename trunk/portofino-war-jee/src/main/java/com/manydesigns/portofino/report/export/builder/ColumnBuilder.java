package com.manydesigns.portofino.report.export.builder;

import static net.sf.dynamicreports.report.builder.DynamicReports.col;
import static net.sf.dynamicreports.report.builder.DynamicReports.grid;
import static net.sf.dynamicreports.report.builder.DynamicReports.type;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import net.sf.dynamicreports.report.builder.AbstractBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.FieldBuilder;
import net.sf.dynamicreports.report.builder.HyperLinkBuilder;
import net.sf.dynamicreports.report.builder.column.PercentageColumnBuilder;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.column.ValueColumnBuilder;
import net.sf.dynamicreports.report.builder.grid.ColumnTitleGroupBuilder;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.definition.datatype.DRIDataType;
import net.sf.dynamicreports.report.exception.DRException;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.manydesigns.portofino.report.enums.RowNumberType;
import com.manydesigns.portofino.report.pojo.FieldPojo;
import com.manydesigns.portofino.report.pojo.ReportPojo;

/**
 * <br>
 * <p>
 * Create on : 2014-3-8<br>
 * <p>
 * </p>
 * <br>
 * 
 * @author panhongliang<br>
 * @version portofino-war-jee v1.0
 *          <p>
 *          <br>
 *          <strong>Modify History:</strong><br>
 *          user modify_date modify_content<br>
 *          -------------------------------------------<br>
 *          <br>
 */
public class ColumnBuilder {

	public final static Logger logger = LoggerFactory.getLogger(ExportUtils.class);

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map<String, ValueColumnBuilder> getColumns(ReportPojo reportPojo) {
		Map<String, FieldPojo> t_fields = DataBuilder.getFieldPojos(reportPojo.getId());

		return getColumns(reportPojo, t_fields);

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map<String, ValueColumnBuilder> getColumns(ReportPojo reportPojo, Map<String, FieldPojo> fields) {
		Map<String, ValueColumnBuilder> t_result = new LinkedHashMap<String, ValueColumnBuilder>();
		if (null == fields || fields.size() == 0) {
			logger.error("no field for report " + reportPojo.getName());
			return t_result;
		}
		buildRowNumberColumn(reportPojo, t_result);
		for (FieldPojo t_field : fields.values()) {
			buildOneColumn(t_result, t_field);
		}
		return t_result;

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void buildOneColumn(Map<String, ValueColumnBuilder> columnMap, FieldPojo field) {
		try {
			TextColumnBuilder t_column = col.column(field.getDisplayName(), field.getField(),
					(DRIDataType) type.detectType(field.getDataType()));
			t_column.setFieldName(field.getField());
			if (StringUtils.isNotBlank(field.getPattern())) {
				t_column.setPattern(field.getPattern());
			}
			if (field.getWidth() > 0) {
				t_column.setFixedWidth(field.getWidth());
			}
			if (!Strings.isNullOrEmpty(field.getHeadGroup())) {
				// TODO TEST
				// t_column.setFixedHeight(20);
				// t_column.setFixedWidth(0);
			}
			if (StringUtils.isNotBlank(field.getAlignment())) {
				t_column.setHorizontalAlignment(HorizontalAlignment.valueOf(field.getAlignment()));
			}

			if (StringUtils.isNotBlank(field.getUrl())) {
				HyperLinkBuilder hyperLink = DynamicReports.hyperLink(field.getUrl());
				t_column.setHyperLink(hyperLink);
			}

			columnMap.put(field.getField(), t_column);

			if (1 == field.getIsPercentage()) {
				PercentageColumnBuilder t_percentageColumn = col.percentageColumn(field.getDisplayName() + "[%]",
						t_column);
				t_percentageColumn.setFieldName(field.getField());
				columnMap.put(field.getField4Percentage(), t_percentageColumn);
			}

		} catch (DRException e) {
			logger.error(e.getMessage(), e);
		}
	}

	public static Map<String, AbstractBuilder> getHeadGroupColumns(ReportPojo reportPojo,
			Map<String, ValueColumnBuilder> columnMap) {

		Map<String, AbstractBuilder> t_allColumns = new LinkedHashMap<String, AbstractBuilder>();
		// head1 列
		Map<String, AbstractBuilder> t_headGroupColumn = new LinkedHashMap<String, AbstractBuilder>();

		Map<String, FieldPojo> t_fields = reportPojo.getFields();
		Multimap<String, FieldPojo> t_headGroupFileds = ArrayListMultimap.create();
		Multimap<String, FieldPojo> t_headGroup2Fileds = ArrayListMultimap.create();
		// head2 包含哪些head1
		Map<String, Set<String>> t_head2Head1Map = new LinkedHashMap<String, Set<String>>();

		boolean t_hasGroup = false;

		for (ValueColumnBuilder t_column : columnMap.values()) {
			FieldPojo t_field = t_fields.get(t_column.getFieldName());
			if (null == t_field) {
				t_allColumns.put(t_column.getFieldName(), t_column);
				continue;
			}
			if (!Strings.isNullOrEmpty(t_field.getHeadGroup2())) {
				t_headGroup2Fileds.put(t_field.getHeadGroup2(), t_field);
				if (!t_head2Head1Map.containsKey(t_field.getHeadGroup2())) {
					t_head2Head1Map.put(t_field.getHeadGroup2(), new LinkedHashSet<String>());
				}
				Set<String> t_head1s = t_head2Head1Map.get(t_field.getHeadGroup2());
				if (!t_head1s.contains(t_field.getHeadGroup())) {
					t_head1s.add(t_field.getHeadGroup());
				}
			}
			if (!Strings.isNullOrEmpty(t_field.getHeadGroup())) {
				t_headGroupFileds.put(t_field.getHeadGroup(), t_field);

			}
			if (!Strings.isNullOrEmpty(t_field.getHeadGroup2())) {
				t_hasGroup = true;
				t_allColumns.put(t_field.getHeadGroup2(), t_column);
			} else if (!Strings.isNullOrEmpty(t_field.getHeadGroup())) {
				t_hasGroup = true;
				t_allColumns.put(t_field.getHeadGroup(), t_column);
			} else {
				t_allColumns.put(t_field.getField(), t_column);
			}
		}
		if (!t_hasGroup) {
			return new LinkedHashMap<String, AbstractBuilder>();
		}
		for (String t_headGroup : t_headGroupFileds.keySet()) {
			ColumnTitleGroupBuilder t_titleGroup = grid.titleGroup(t_headGroup);
			Collection<FieldPojo> t_values = t_headGroupFileds.get(t_headGroup);
			for (FieldPojo t_fieldPojo : t_values) {
				t_titleGroup.add((TextColumnBuilder) columnMap.get(t_fieldPojo.getField()));
			}
			t_headGroupColumn.put(t_headGroup, t_titleGroup);
			t_allColumns.put(t_headGroup, t_titleGroup);
		}

		for (Entry<String, Set<String>> t_entry : t_head2Head1Map.entrySet()) {
			ColumnTitleGroupBuilder t_titleGroup = grid.titleGroup(t_entry.getKey());
			for (String t_head1 : t_entry.getValue()) {
				t_titleGroup.add((ColumnTitleGroupBuilder) t_headGroupColumn.get(t_head1));
				t_allColumns.remove(t_head1);
			}
			t_allColumns.put(t_entry.getKey(), t_titleGroup);
		}

		return t_allColumns;
	}

	@SuppressWarnings("rawtypes")
	public static void buildRowNumberColumn(ReportPojo reportPojo, Map<String, ValueColumnBuilder> result) {
		if (StringUtils.isBlank(reportPojo.getRowNumberType())) {
			return;
		}
		RowNumberType t_type = RowNumberType.valueOf(reportPojo.getRowNumberType());
		TextColumnBuilder t_rowNumberColumn = null;
		switch (t_type) {
		case all:
			// Report row
			t_rowNumberColumn = col.reportRowNumberColumn("行号");
			break;
		case page:
			// Page row
			t_rowNumberColumn = col.pageRowNumberColumn("行号");
			break;
		case column:
			// Page column row
			t_rowNumberColumn = col.columnRowNumberColumn("行号");
			break;
		default:
			break;
		}
		if (null != t_rowNumberColumn) {
			t_rowNumberColumn.setFieldName("行号");
			result.put("行号", t_rowNumberColumn);
		}

	}

	@SuppressWarnings("rawtypes")
	public static Map<String, FieldBuilder> getFieldBuilders(String reportId, Map<String, FieldPojo> fields) {
		Map<String, FieldBuilder> t_result = new HashMap<String, FieldBuilder>();
		// if (fields.size() == 0) {
		// logger.error("no field for report " + reportId);
		// }
		// for (FieldPojo t_field : fields.values()) {
		// try {
		// FieldBuilder t_builder = DynamicReports.field(t_field.getField(),
		// type.detectType(t_field.getDataType()));
		// t_result.put(t_field.getField(), t_builder);
		// } catch (DRException e) {
		// logger.error(e.getMessage(), e);
		// }
		// }
		return t_result;
	}

}
