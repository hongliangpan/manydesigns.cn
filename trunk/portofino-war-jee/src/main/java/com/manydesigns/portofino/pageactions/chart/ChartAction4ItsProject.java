package com.manydesigns.portofino.pageactions.chart;

import java.text.MessageFormat;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;

import org.apache.commons.lang.StringUtils;

import com.manydesigns.portofino.buttons.annotations.Button;
import com.manydesigns.portofino.security.AccessLevel;
import com.manydesigns.portofino.security.RequiresPermissions;
import com.riil.itsboard.custom.Params;
import com.riil.itsboard.utils.GroovyUtils;

/**
 * sql 配置规则<br>
 * sql 通过页面配置<br>
 * 1=1 替换成 1=1 AND 区域过滤
 */
public class ChartAction4ItsProject extends ChartAction {
	// protected String where= " WHERE CONCAT(c_region1,c_region2,c_region3) LIKE '%{0}%'";
	protected static final String REGION_PART = "(c_region1 ='{0}' OR c_region2 ='{0}' OR c_region3 ='{0}')";
	protected static final String REGION_ALL = "1=1";
	private String regionParam;

	@Button(list = "pageHeaderButtons", titleKey = "dataList", order = 1, icon = Button.ICON_PLUS)
	@RequiresPermissions(level = AccessLevel.VIEW)
	public Resolution dataList() {
		if (this.chartConfiguration != null) {
			String sql = chartConfiguration.getQueryOriginal();
			String query = formatSql4DataList(sql);
			this.chartConfiguration.setQuery(query);
			context.getRequest().setAttribute("sql", query);
			this.chartConfiguration.setLegend(" ");
		}
		super.execute();
		return new ForwardResolution("/m/chart/data_list.jsp");
	}

	@DefaultHandler
	public Resolution execute() {
		context.getResponse().setContentType("text/html;charset=UTF-8");
		if (this.chartConfiguration != null) {
			String sql = chartConfiguration.getQueryOriginal();
			String query = formatSql(sql);
			this.chartConfiguration.setQuery(query);
			context.getRequest().setAttribute("sql", query);
			this.chartConfiguration.setLegend(" ");
		}
		return super.execute();

	}

	public String formatSql4DataList(String sqlTemplate) {
		if (StringUtils.isBlank(regionParam)) {
			regionParam = context.getRequest().getSession().getAttribute("region").toString();
		}
		if (StringUtils.isBlank(regionParam)) {
			Params params = Params.getParams(context.getRequest(), "");
			String region = params.getRegionDisplay();
			regionParam = region;
		}

		return formatSql(sqlTemplate, regionParam);
	}

	public String formatSql(String sqlTemplate) {
		// String region = context.getRequest().getParameter("region");
		Params params = Params.getParams(context.getRequest(), "");
		String region = params.getRegionDisplay();
		if (StringUtils.isNotBlank(region)) {
			context.getRequest().getSession().setAttribute("region", region);
			regionParam = region;
		}
		return formatSql(sqlTemplate, region);
	}

	private String formatSql(String sqlTemplate, String region) {
		String resultSql = GroovyUtils.replaceSql4MessageFormat(sqlTemplate);
		String where;

		if (region == null || region.length() == 0 || "全国".equals(region)) {
			where = REGION_ALL;
		} else {
			where = GroovyUtils.replaceSql4MessageFormat(REGION_PART);
		}

		if (region.indexOf(",") >= 0) {
			String[] regions = region.split(",");
			StringBuilder wheres = new StringBuilder("(");
			for (int i = 1; i <= 3; i++) {
				for (String oneRegion : regions) {
					wheres.append("c_region").append(i).append("='").append(oneRegion).append("' OR ");
				}
			}
			where = wheres.substring(0, wheres.lastIndexOf("OR") - 1) + ")";
			where = GroovyUtils.replaceSql4MessageFormat(where);
		}
		resultSql = replace(resultSql);
		if (sqlTemplate.toUpperCase().indexOf("WHERE") < 0) {
			where = " WHERE" + where;
		}
		resultSql = MessageFormat.format(resultSql, MessageFormat.format(where, region));
		return resultSql;
	}

	public String replace(String sql) {
		if (sql.indexOf("1=1") < 0 && sql.indexOf("1 = 1") < 0) {
			return sql;
		}
		return sql.replaceAll("1=1", "{0}").replaceAll("1 = 1", "{0}");
	}
}
