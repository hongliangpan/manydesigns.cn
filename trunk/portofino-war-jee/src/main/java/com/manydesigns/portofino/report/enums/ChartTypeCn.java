package com.manydesigns.portofino.report.enums;

import static net.sf.dynamicreports.report.builder.DynamicReports.cht;
import net.sf.dynamicreports.report.builder.chart.AbstractChartBuilder;
import net.sf.dynamicreports.report.constant.ChartType;

import org.apache.commons.lang.StringUtils;

import com.manydesigns.portofino.report.pojo.ReportPojo;

/**
 * 图chart类型 <br>
 * <p>
 * Create on : 2014-3-3<br>
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
public enum ChartTypeCn {
	AREA("区域图"), /**/
	STACKEDAREA("堆叠区域图"), /**/
	BAR("条形图"), /**/
	BAR3D("3D条形图"), /**/
	LAYEREDBAR("气泡条形图"), /**/
	WATERFALLBAR("瀑布图"), /**/
	STACKEDBAR("堆叠图"), /**/
	STACKEDBAR3D("3D堆叠图"), /**/
	GROUPEDSTACKEDBAR("分组堆叠图"), /**/
	LINE("折线图"), /**/
	PIE("饼图"), /**/
	PIE3D("3D饼图"), /**/
	TIMESERIES("时间序列图"), /**/
	DIFFERENCE("差异图"), /**/
	XYAREA("XYAREA区域"), /**/
	XYBAR("XYBAR条形"), /**/
	XYLINE("XYLINE线图"), /**/
	XYSTEP("XYSTEP"), /**/
	SCATTER("SCATTER散点图"), /**/
	MULTI_AXIS("MULTI_AXIS"), /**/
	SPIDER("SPIDER蜘蛛"), /**/
	XYBLOCK("XYBLOCK"), /**/
	BUBBLE("气泡图"), /**/
	CANDLESTICK("蜡烛图"), /**/
	HIGHLOW("HIGHLOW"), /**/
	METER("仪表图"), /**/
	THERMOMETER("温度计"), /**/
	GANTT("甘特图");

	private final String m_name;

	/**
	 * Constructors.
	 * 
	 * @param name 标识号
	 */
	private ChartTypeCn(final String name) {
		this.m_name = name;
	}

	/**
	 * parse enum.
	 * 
	 * @param name id
	 * @return DataType
	 */
	public static ChartTypeCn parse(final String name) {
		if (StringUtils.isBlank(name)) {
			return null;
		}
		for (ChartTypeCn t_generic : ChartTypeCn.values()) {
			if (t_generic.getName().equals(name)) {
				return t_generic;
			}
		}
		return null;
	}

	/**
	 * get displayName from i18n
	 * 
	 * @return displayName
	 */
	public String getName() {
		return m_name;
	}

	public static AbstractChartBuilder getChart(ReportPojo reportPojo) {
		AbstractChartBuilder chart = null;
		switch (ChartType.valueOf(reportPojo.getChartType())) {
		case AREA:
			chart = cht.areaChart();

			break;
		case STACKEDAREA:
			chart = cht.stackedAreaChart();
			break;
		case BAR:
			chart = cht.barChart();
			break;
		case LAYEREDBAR:
			chart = cht.layeredBarChart();
			break;
		case WATERFALLBAR:
			chart = cht.waterfallBarChart();
			break;

		case BAR3D:
			chart = cht.bar3DChart();
			break;

		case STACKEDBAR:
			chart = cht.stackedBarChart();
			break;

		case STACKEDBAR3D:
			chart = cht.stackedBar3DChart();
			break;

		case GROUPEDSTACKEDBAR:
			chart = cht.groupedStackedBarChart();
			break;

		case LINE:
			chart = cht.lineChart();
			break;

		case PIE:
			chart = cht.pieChart();
			break;

		case PIE3D:
			chart = cht.pie3DChart();
			break;

		case TIMESERIES:
			chart = cht.timeSeriesChart();
			break;

		case DIFFERENCE:
			chart = cht.differenceChart();
			break;

		case XYAREA:
			chart = cht.xyAreaChart();
			break;

		case XYBAR:
			chart = cht.xyBarChart();
			break;

		case XYLINE:
			chart = cht.xyLineChart();
			break;

		case XYSTEP:
			chart = cht.xyStepChart();
			break;

		case SCATTER:
			chart = cht.scatterChart();
			break;

		case MULTI_AXIS:
			chart = cht.multiAxisChart();
			break;

		case SPIDER:
			chart = cht.spiderChart();
			break;

		case XYBLOCK:
			// chart = cht.xyBlockChart(defaultLowerBound, defaultUpperBound, defaultPaint);
			break;

		case BUBBLE:
			chart = cht.bubbleChart();
			break;

		case CANDLESTICK:
			chart = cht.candlestickChart();
			break;

		case HIGHLOW:
			chart = cht.highLowChart();
			break;

		case METER:
			chart = cht.meterChart();
			break;

		case THERMOMETER:
			chart = cht.thermometerChart();
			break;

		case GANTT:
			chart = cht.ganttChart();
			break;

		default:
			break;
		}
		return chart;
	}
}