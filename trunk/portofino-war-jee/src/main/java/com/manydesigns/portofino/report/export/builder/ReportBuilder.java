package com.manydesigns.portofino.report.export.builder;

import static net.sf.dynamicreports.report.builder.DynamicReports.cmp;
import static net.sf.dynamicreports.report.builder.DynamicReports.grp;
import static net.sf.dynamicreports.report.builder.DynamicReports.report;
import static net.sf.dynamicreports.report.builder.DynamicReports.sbt;
import static net.sf.dynamicreports.report.builder.DynamicReports.stl;

import java.util.Map;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.AbstractBuilder;
import net.sf.dynamicreports.report.builder.chart.AbstractChartBuilder;
import net.sf.dynamicreports.report.builder.column.TextColumnBuilder;
import net.sf.dynamicreports.report.builder.column.ValueColumnBuilder;
import net.sf.dynamicreports.report.builder.component.PageXofYBuilder;
import net.sf.dynamicreports.report.builder.grid.ColumnGridComponentBuilder;
import net.sf.dynamicreports.report.builder.group.ColumnGroupBuilder;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.builder.subtotal.AggregationSubtotalBuilder;
import net.sf.dynamicreports.report.constant.GroupHeaderLayout;
import net.sf.dynamicreports.report.constant.HorizontalAlignment;
import net.sf.dynamicreports.report.constant.VerticalAlignment;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.manydesigns.portofino.report.Templates;
import com.manydesigns.portofino.report.pojo.FieldPojo;
import com.manydesigns.portofino.report.pojo.ReportPojo;
import com.riil.itsboard.custom.DbUtils4Its;

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
public class ReportBuilder {
	public final static Logger logger = LoggerFactory.getLogger(ExportUtils.class);

	@SuppressWarnings({ "rawtypes", "deprecation" })
	public static JasperReportBuilder getReportBuilder(ReportPojo reportPojo) {

		Map<String, ValueColumnBuilder> t_columnMap = reportPojo.getColumnMap();
		if ("chart".equalsIgnoreCase(reportPojo.getDisplayTypeId())) {
			return buildChart(reportPojo, t_columnMap);
		}
		if ("chart_grid".equalsIgnoreCase(reportPojo.getDisplayTypeId())) {
			return buildChartGridBySubreport(reportPojo, t_columnMap);
		}

		JasperReportBuilder t_report = createBaseReportInfo(reportPojo);
		if (reportPojo.getDisplayTypeId().toLowerCase().indexOf("grid") >= 0) {
			buildGrid(reportPojo, t_report);
		}
		if ("grid_chart".equalsIgnoreCase(reportPojo.getDisplayTypeId())) {
			AbstractChartBuilder chart = ChartBuilder.buildChart(reportPojo, t_columnMap);
			t_report.summary(cmp.verticalGap(5), cmp.verticalList(chart));
		}
		setDataSource(reportPojo, t_report);
		addFoot(t_report);
		return t_report;
	}

	public static JasperReportBuilder buildChart(ReportPojo reportPojo, Map<String, ValueColumnBuilder> columnMap) {
		JasperReportBuilder t_report = createBaseReportInfo(reportPojo);
		AbstractChartBuilder chart = ChartBuilder.buildChart(reportPojo, columnMap);
		t_report.summary(cmp.verticalGap(5), cmp.verticalList(chart));
		setDataSource(reportPojo, t_report);
		return t_report;
	}

	public static JasperReportBuilder createBaseReportInfo(ReportPojo reportPojo) {
		StyleBuilder textStyle = stl.style(Templates.columnStyle).setBorder(stl.pen1Point());
		return report().setTemplate(Templates.reportTemplate).setColumnStyle(textStyle)
				.title(Templates.createTitleComponent(reportPojo.getTitle()));
	}

	public static void buildGrid(ReportPojo reportPojo, JasperReportBuilder report) {
		// t_report.setPageFormat(PageType.A4, PageOrientation.LANDSCAPE);
		Map<String, FieldPojo> t_fields = reportPojo.getFields();
		Map<String, ValueColumnBuilder> t_columnMap = reportPojo.getColumnMap();
		ValueColumnBuilder[] t_columns = reportPojo.getColumns();
		addColumns(t_columns, report);
		addColumnTitleGroup(reportPojo, report, t_columnMap);

		addGroups(report, t_columnMap, t_fields);
		addSum(report, t_columnMap, t_fields);

		setDataSource(reportPojo, report);
	}

	public static void addFoot(JasperReportBuilder report) {
		// t_report.pageFooter(Templates.footerComponent);
		PageXofYBuilder pageXofY = cmp.pageXofY().setStyle(Templates.boldCenteredStyle);
		report.addPageFooter(pageXofY);
	}

	public static JasperReportBuilder buildChartGridBySubreport(ReportPojo reportPojo,
			Map<String, ValueColumnBuilder> t_columnMap) {
		AbstractChartBuilder chart = ChartBuilder.buildChart(reportPojo, t_columnMap);

		JasperReportBuilder t_gridReport = report().setTemplate(Templates.reportTemplate);
		buildGrid(reportPojo, t_gridReport);
		setDataSource(reportPojo, t_gridReport);

		JasperReportBuilder t_result = createBaseReportInfo(reportPojo);
		t_result.summary(cmp.verticalList(chart, cmp.subreport(t_gridReport)));
		addFoot(t_result);
		setDataSource(reportPojo, t_result);
		return t_result;
	}

	public static void setDataSource(ReportPojo reportPojo, JasperReportBuilder reportBuilder) {
		// t_report.setDataSource(DataBuilder.createDataSource(t_reportPojo));
		String t_buildQuerySql = reportPojo.buildQuerySql(reportPojo);
		logger.error(reportPojo.getName() + "run sql:\n" + t_buildQuerySql);
		reportBuilder.setDataSource(t_buildQuerySql, DbUtils4Its.getSession().connection());
	}

	@SuppressWarnings("rawtypes")
	public static void addColumns(ValueColumnBuilder[] columns, JasperReportBuilder report) {
		// report.columns(columns);

		for (ValueColumnBuilder t_valueColumnBuilder : columns) {
			report.addColumn(t_valueColumnBuilder);
		}
		// t_report.columnGrid(ListType.HORIZONTAL);
		// report.columns(columns);
	}

	@SuppressWarnings("rawtypes")
	private static void addColumnTitleGroup(ReportPojo reportPojo, JasperReportBuilder report,
			Map<String, ValueColumnBuilder> columnMap) {
		// TODO 流量分为入流量、出流量；入流量分为最大值、最小值等
		Map<String, AbstractBuilder> t_columns = ColumnBuilder.getHeadGroupColumns(reportPojo, columnMap);
		if (null == t_columns || t_columns.size() == 0) {
			return;
		}
		ColumnGridComponentBuilder[] t_col = new ColumnGridComponentBuilder[t_columns.size()];
		t_columns.values().toArray(t_col);
		report.columnGrid(t_col);
	}

	@SuppressWarnings({ "rawtypes" })
	protected static void addGroups(JasperReportBuilder report, Map<String, ValueColumnBuilder> columns,
			Map<String, FieldPojo> fields) {
		for (ValueColumnBuilder t_column : columns.values()) {
			if (!(t_column instanceof TextColumnBuilder)) {
				continue;
			}
			FieldPojo t_fieldPojo = fields.get(t_column.getColumn().getName());
			if (null != t_fieldPojo && 1 == t_fieldPojo.getIsGroup()) {
				StyleBuilder t_groupStyle = stl.style().bold();
				// group没有格
				// t_groupStyle.setBorder(stl.pen(0.2f, LineStyle.SOLID));
				if (StringUtils.isNotBlank(t_fieldPojo.getGroupAlignment())) {
					t_groupStyle.setAlignment(HorizontalAlignment.valueOf(t_fieldPojo.getGroupAlignment()),
							VerticalAlignment.MIDDLE);
					t_column.setHorizontalAlignment(HorizontalAlignment.valueOf(t_fieldPojo.getGroupAlignment()));
				}
				ColumnGroupBuilder t_columnGroup = grp.group(t_column).setStyle(t_groupStyle)
						.setTitleStyle(t_groupStyle);
				// 显示key:value形式
				t_columnGroup.setHeaderLayout(GroupHeaderLayout.VALUE);// GroupHeaderLayout.TITLE_AND_VALUE
				report.groupBy(t_columnGroup);
				// 分组汇总
				addSubtotal(report, columns, fields, t_columnGroup);
			}
		}
	}

	@SuppressWarnings({ "rawtypes" })
	protected static void addSubtotal(JasperReportBuilder report, Map<String, ValueColumnBuilder> columns,
			Map<String, FieldPojo> fields, ColumnGroupBuilder columnGroup) {
		for (ValueColumnBuilder t_column : columns.values()) {
			if (t_column instanceof TextColumnBuilder) {
				FieldPojo t_fieldPojo = fields.get(t_column.getColumn().getName());
				if (1 == t_fieldPojo.getIsSubtotal()) {
					AggregationSubtotalBuilder t_subtotalBuilder = buildOneField(t_column, t_fieldPojo);
					report.subtotalsAtGroupFooter(columnGroup, t_subtotalBuilder);
					// report.subtotalsAtFirstGroupFooter(t_subtotalBuilder);
				}
			}
			// if (t_column instanceof PercentageColumnBuilder) {
			// PercentageColumnBuilder t_percentageColumn = (PercentageColumnBuilder) t_column;
			// FieldPojo t_fieldPojo = fields.get(t_percentageColumn.getFieldName());
			// if (t_fieldPojo != null && 1 == t_fieldPojo.getIsSubtotal()) {
			// AggregationSubtotalBuilder t_subtotalBuilder = buildOneField(t_column, t_fieldPojo);
			// report.subtotalsAtGroupFooter(columnGroup, t_subtotalBuilder);
			// }
			// }
		}
	}

	@SuppressWarnings({ "rawtypes" })
	protected static void addSum(JasperReportBuilder report, Map<String, ValueColumnBuilder> columns,
			Map<String, FieldPojo> fields) {
		for (ValueColumnBuilder t_column : columns.values()) {
			if (!(t_column instanceof TextColumnBuilder)) {
				continue;
			}
			FieldPojo t_fieldPojo = fields.get(t_column.getColumn().getName());
			if (null != t_fieldPojo && 1 == t_fieldPojo.getIsSum()) {
				AggregationSubtotalBuilder t_subtotalBuilder = buildOneField(t_column, t_fieldPojo);
				report.subtotalsAtSummary(t_subtotalBuilder);
			}
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected static AggregationSubtotalBuilder buildOneField(ValueColumnBuilder t_column, FieldPojo t_fieldPojo) {
		AggregationSubtotalBuilder t_subtotalBuilder = null;
		if ("AVG".equalsIgnoreCase(t_fieldPojo.getSumMethod())) {
			t_subtotalBuilder = (AggregationSubtotalBuilder) sbt.avg(t_column);
		} else if ("COUNT".equalsIgnoreCase(t_fieldPojo.getSumMethod())) {
			t_subtotalBuilder = (AggregationSubtotalBuilder) sbt.count(t_column);
		} else if ("MAX".equalsIgnoreCase(t_fieldPojo.getSumMethod())) {
			t_subtotalBuilder = (AggregationSubtotalBuilder) sbt.max(t_column);
		} else if ("MIN".equalsIgnoreCase(t_fieldPojo.getSumMethod())) {
			t_subtotalBuilder = (AggregationSubtotalBuilder) sbt.min(t_column);
		} else if ("DISTINCT_COUNT".equalsIgnoreCase(t_fieldPojo.getSumMethod())) {
			t_subtotalBuilder = (AggregationSubtotalBuilder) sbt.distinctCount(t_column);
		} else if ("VARIANCE".equalsIgnoreCase(t_fieldPojo.getSumMethod())) {
			t_subtotalBuilder = (AggregationSubtotalBuilder) sbt.var(t_column);
		} else if ("STANDARD_DEVIATION".equalsIgnoreCase(t_fieldPojo.getSumMethod())) {
			t_subtotalBuilder = (AggregationSubtotalBuilder) sbt.stdDev(t_column);
		} else {
			t_subtotalBuilder = (AggregationSubtotalBuilder) sbt.sum(t_column);
		}
		// t_subtotalBuilder.setLabel(t_fieldPojo.getDisplayName());
		return t_subtotalBuilder;
	}

	enum Calculation {
		NOTHING, COUNT, SUM, AVERAGE, LOWEST, HIGHEST, STANDARD_DEVIATION, VARIANCE, FIRST, DISTINCT_COUNT
	}
}
