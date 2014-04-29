package com.manydesigns.portofino.report.export.builder;

import static net.sf.dynamicreports.report.builder.DynamicReports.cht;

import java.util.Map;

import net.sf.dynamicreports.report.builder.chart.AbstractBaseChartBuilder;
import net.sf.dynamicreports.report.builder.chart.AbstractCategoryChartBuilder;
import net.sf.dynamicreports.report.builder.chart.AbstractChartBuilder;
import net.sf.dynamicreports.report.builder.chart.AbstractPieChartBuilder;
import net.sf.dynamicreports.report.builder.chart.BubbleChartBuilder;
import net.sf.dynamicreports.report.builder.column.ValueColumnBuilder;
import net.sf.dynamicreports.report.constant.Orientation;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.manydesigns.portofino.report.enums.ChartTypeCn;
import com.manydesigns.portofino.report.pojo.ReportPojo;

public class ChartBuilder {
	public final static Logger logger = LoggerFactory.getLogger(ChartBuilder.class);

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected static void setCategorySerie(AbstractCategoryChartBuilder chart, ReportPojo reportPojo,
			Map<String, ValueColumnBuilder> columns) {
		setCommonInfo(chart, reportPojo, columns);
		chart.setCategory(columns.get(reportPojo.getChartX()));
		for (String t_fieldName : reportPojo.getChartValueFields()) {
			chart.addSerie(cht.serie(columns.get(t_fieldName.trim())));
		}
		chart.setFixedWidth(575);
		chart.setFixedHeight(375);
		if ("Horizontal".equals(reportPojo.getChartOrientation())) {
			chart.setOrientation(Orientation.HORIZONTAL);
		} else {
			chart.setOrientation(Orientation.VERTICAL);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected static void setPieKeySerie(AbstractPieChartBuilder chart, ReportPojo reportPojo,
			Map<String, ValueColumnBuilder> columns) {
		setCommonInfo(chart, reportPojo, columns);
		chart.setKey(columns.get(reportPojo.getChartX()));
		for (String t_fieldName : reportPojo.getChartValueFields()) {
			chart.addSerie(cht.serie(columns.get(t_fieldName)));
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected static void setBubbleChart(BubbleChartBuilder chart, ReportPojo reportPojo,
			Map<String, ValueColumnBuilder> columns) {
		setCommonInfo(chart, reportPojo, columns);
		chart.setXValue(columns.get(reportPojo.getChartX()));
		String[] zValues = reportPojo.getChartValueFields();
		String[] yValues = reportPojo.getChartYFields();
		if (zValues.length != yValues.length) {
			logger.error("BubbleChartBuilder y和z字段数不一致,y:" + reportPojo.getChartY() + " z:"
					+ reportPojo.getChartValue());
		}
		int i = 0;
		for (String t_zFieldName : zValues) {
			String t_yFieldName = yValues[i];
			chart.addSerie(cht.xyzSerie().setYValue(columns.get(t_yFieldName)).setZValue(columns.get(t_zFieldName)));
		}
	}

	@SuppressWarnings({ "rawtypes" })
	protected static void setCommonInfo(AbstractBaseChartBuilder chart, ReportPojo reportPojo,
			Map<String, ValueColumnBuilder> columns) {
		chart.setTitle(reportPojo.getName());
	}

	/***
	 * SQL Query parameters <br>
	 * 1D Charts: key, value, (optional) label <br>
	 * 2D Charts: x axis key, y axis key, value, (optional) x axis label, (optional) y axis label {method description}.
	 * 
	 * @param report
	 * @param columns
	 */
	@SuppressWarnings("rawtypes")
	public static AbstractChartBuilder buildChart(ReportPojo reportPojo, Map<String, ValueColumnBuilder> columns) {
		if (StringUtils.isBlank(reportPojo.getChartType())) {
			return null;
		}
		if (StringUtils.isBlank(reportPojo.getChartX()) || StringUtils.isBlank(reportPojo.getChartValue())) {
			return null;
		}
		AbstractChartBuilder chart = ChartTypeCn.getChart(reportPojo);

		if (chart instanceof AbstractPieChartBuilder || chart.getClass().getSimpleName().startsWith("Pie")) {
			setPieKeySerie((AbstractPieChartBuilder) chart, reportPojo, columns);
		} else if (chart instanceof BubbleChartBuilder || chart.getClass().getSimpleName().startsWith("Bubble")) {
			setPieKeySerie((AbstractPieChartBuilder) chart, reportPojo, columns);
		} else {
			setCategorySerie((AbstractCategoryChartBuilder) chart, reportPojo, columns);
		}
		return chart;
	}

}
