package com.manydesigns.portofino.actions.echarts;

import java.io.StringReader;
import java.util.List;

import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.StreamingResolution;
import net.sourceforge.stripes.action.UrlBinding;

import com.google.common.collect.Lists;
import com.manydesigns.portofino.utils.JsonMapper;
import com.riil.itsboard.utils.GroovyUtils;

@UrlBinding("/actions/echarts/dataProvider/{$event}")
public class DataProvider implements ActionBean {
	private String result;
	private ActionBeanContext context;

	@DefaultHandler
	public Resolution getMonthes() {
		List<String> monthes = Lists.newArrayList();
		for (int t_i = 1; t_i <= 12; t_i++) {
			monthes.add(t_i + "月");
		}
		result = JsonMapper.getInstance().toJson(monthes);
		return new StreamingResolution("text", new StringReader(result));
	}

	@HandlesEvent("getDatas")
	public Resolution getDatas() {
		List<String> monthes = Lists.newArrayList();
		for (int t_i = 1; t_i <= 12; t_i++) {
			monthes.add(t_i + "月");
		}
		result = JsonMapper.getInstance().toJson(monthes);
		return new StreamingResolution("text", new StringReader(result));
	}

	@HandlesEvent("getDatas4Warranty")
	public Resolution getDatas4Warranty() {
		String sql = "SELECT c.c_region3 c_x,CONCAT(MONTH(c_warranty_end),'月') c_y,COUNT(0) c_value,c.c_region3 c_x_lable,MONTH(c_warranty_end) c_y_lable FROM t_customer c\n"
				+ " WHERE YEAR(c_warranty_end)=YEAR(NOW()) AND 1=1 GROUP BY c.c_region3,MONTH(c_warranty_end) ORDER BY MONTH(c_warranty_end),c.c_region3";
		sql = GroovyUtils.getQuerySql4RegionParam(sql, context.getRequest());
		result = DataUtils.getChartJsonData(sql, context.getRequest());
		return new StreamingResolution("text", new StringReader(result));
	}

	@HandlesEvent("getDatas4AreaComplaint")
	public Resolution getDatas4AreaComplaint() {
		String sql = "SELECT c_x, c_y, c_value, c_x_label, c_y_label FROM(\n"
				+ "(SELECT c_region3 c_x,'未投诉客户量' c_y,COUNT(0) c_value,'未投诉客户量' c_x_label,c_region3 c_y_label\n"
				+ "FROM t_customer \n"
				+ "WHERE  c_region3 IN (\n"
				+ "SELECT c_region3 FROM t_customer_complaint cc\n"
				+ "INNER JOIN t_customer c ON cc.c_customer_id= c.c_id\n"
				+ ") AND c_id NOT IN (\n"
				+ "SELECT c_customer_id FROM t_customer_complaint \n"
				+ ") AND 1=1\n"
				+ "GROUP BY c_region3)\n"
				+ "UNION ALL\n"
				+ "(SELECT c_region3 c_x,'投诉客户量' c_y,COUNT(cc.c_customer_id+cc.c_complaint_id) c_value,'投诉客户量' c_x_label,c_region3 c_y_label FROM t_customer_complaint cc\n"
				+ "INNER JOIN t_customer c ON cc.c_customer_id=c.c_id\n"
				+ "INNER JOIN t_dict_complaint d ON d.c_id=cc.c_complaint_id\n" + "WHERE 1=1\n"
				+ "GROUP BY c.c_region3\n" + ")\n" + ") mm\n" + "ORDER BY mm.c_y ,mm.c_x ,mm. c_value DESC";

		result = DataUtils.getChartJsonData(sql, context.getRequest());
		return new StreamingResolution("text", new StringReader(result));
	}

	public Resolution getDatas4CustomerMap() {
		String sql = "SELECT c_x,c_y,c_value FROM\n" + "(\n"
				+ "SELECT c_region3 c_x,'用户量' c_y ,count(0) c_value FROM t_customer\n"
				+ "WHERE 1=1 GROUP BY c_region3\n" + "UNION ALL\n"
				+ "SELECT c_region3 c_x, '投诉客户量' c_y, COUNT( cc.c_customer_id + cc.c_complaint_id ) c_value\n"
				+ " FROM t_customer_complaint cc INNER JOIN t_customer c ON cc.c_customer_id = c.c_id \n"
				+ "INNER JOIN t_dict_complaint d ON d.c_id = cc.c_complaint_id WHERE 1 = 1 GROUP BY c.c_region3\n"
				+ ") t\n" + "ORDER BY c_x,c_y";

		sql = "SELECT c_x, c_y, c_value, c_x_label, c_y_label FROM(\n"
				+ "(SELECT c_region3 c_x,'未投诉客户量' c_y,COUNT(0) c_value,'未投诉客户量' c_x_label,c_region3 c_y_label\n"
				+ "FROM t_customer \n"
				+ "WHERE  c_id NOT IN (\n"
				+ "SELECT c_customer_id FROM t_customer_complaint \n"
				+ ") AND 1=1\n"
				+ "GROUP BY c_region3)\n"
				+ "UNION ALL\n"
				+ "(SELECT c_region3 c_x,'投诉客户量' c_y,COUNT(cc.c_customer_id+cc.c_complaint_id) c_value,'投诉客户量' c_x_label,c_region3 c_y_label FROM t_customer_complaint cc\n"
				+ "INNER JOIN t_customer c ON cc.c_customer_id=c.c_id\n"
				+ "INNER JOIN t_dict_complaint d ON d.c_id=cc.c_complaint_id\n" + "WHERE 1=1\n"
				+ "GROUP BY c.c_region3\n" + ")\n" + ") mm\n" + "ORDER BY mm.c_y ,mm.c_x ,mm. c_value DESC";
		result = DataUtils.getChartJsonData(sql, context.getRequest());
		return new StreamingResolution("text", new StringReader(result));
	}

	public Resolution getDatas4CustomerTemplateMap() {
		String sql = "SELECT c_region3  c_x,'样板' c_y ,COUNT(0) c_value FROM t_customer\n"
				+ "WHERE c_is_template=1 AND 1=1 GROUP BY c_region3\n" + "UNION ALL\n"
				+ "SELECT c_region3  c_x,'精品样板' c_y ,COUNT(0) c_value FROM t_customer\n"
				+ "WHERE c_is_template_boutique=1 AND 1=1 GROUP BY c_region3\n" + "UNION ALL\n"
				+ "SELECT c_region3  c_x,'用的好' c_y ,COUNT(0) c_value FROM t_customer\n"
				+ "WHERE c_is_good=1 AND 1=1 GROUP BY c_region3";
		result = DataUtils.getChartJsonData(sql, context.getRequest());
		return new StreamingResolution("text", new StringReader(result));
	}

	public Resolution getDatas4CustomerMapMoney() {
		String sql = "SELECT c_region3 c_x,'合同额' c_y ,ROUND(SUM(c_money)) c_value FROM t_customer\n"
				+ "WHERE 1=1 GROUP BY c_region3";

		result = DataUtils.getChartJsonData(sql, context.getRequest());
		return new StreamingResolution("text", new StringReader(result));
	}

	public Resolution getDatas4CustomerMap200() {
		String sql = "SELECT c_x,c_y,c_value FROM\n" + "(\n"
				+ "SELECT c_region3 c_x,'用户量' c_y ,count(0) c_value FROM t_customer\n"
				+ "WHERE 1=1 GROUP BY c_region3\n" + "UNION ALL\n"
				+ "SELECT c_region3 c_x, '投诉客户量' c_y, COUNT( cc.c_customer_id + cc.c_complaint_id ) c_value\n"
				+ " FROM t_customer_complaint cc INNER JOIN t_customer c ON cc.c_customer_id = c.c_id \n"
				+ "INNER JOIN t_dict_complaint d ON d.c_id = cc.c_complaint_id WHERE 1 = 1 GROUP BY c.c_region3\n"
				+ "UNION ALL\n" + "SELECT c_region3 c_x,'合同额' c_y ,ROUND(SUM(c_money)) c_value FROM t_customer\n"
				+ "WHERE 1=1 GROUP BY c_region3\n" + ") t\n" + "ORDER BY c_x,c_y";

		result = DataUtils.getChartJsonData(sql, context.getRequest());
		return new StreamingResolution("text", new StringReader(result));
	}

	public Resolution getDatas4ProjectState() {
		String sql = "select s.c_name c_x,s.c_name c_y,count(0) c_value from t_project p \n"
				+ "INNER JOIN t_dict_state s ON p.c_state=s.c_id \n"
				+ "INNER JOIN t_customer c ON p.c_customer_id=c.c_id \n" + "WHERE 1=1\n"
				+ "GROUP BY s.c_id ORDER BY s.c_sort_id";

		result = DataUtils.getChartJsonData(sql, context.getRequest());
		return new StreamingResolution("text", new StringReader(result));
	}

	public String getResult() {
		return result;
	}

	public ActionBeanContext getContext() {
		return context;
	}

	public void setContext(ActionBeanContext arg0) {
		this.context = arg0;
	}

}