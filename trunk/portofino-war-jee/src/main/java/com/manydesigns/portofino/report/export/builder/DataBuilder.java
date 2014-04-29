package com.manydesigns.portofino.report.export.builder;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sf.dynamicreports.report.datasource.DRDataSource;
import net.sf.jasperreports.engine.JRDataSource;

import com.manydesigns.portofino.report.pojo.FieldPojo;
import com.manydesigns.portofino.report.pojo.ParamPojo;
import com.manydesigns.portofino.report.pojo.ReportPojo;
import com.manydesigns.portofino.utils.ObjectMapper;
import com.riil.itsboard.custom.DbUtils4Its;

public class DataBuilder {
	public static JRDataSource createDataSource(ReportPojo reportPojo) {
		DRDataSource dataSource = new DRDataSource("item", "orderdate", "quantity", "unitprice");
		dataSource.add("Notebook", new Date(), 1, new BigDecimal(500));
		dataSource.add("Book", new Date(), 7, new BigDecimal(300));
		dataSource.add("PDA", new Date(), 2, new BigDecimal(250));
		return dataSource;
	}

	public static Map<String, FieldPojo> getFieldPojos(String reportId) {
		String t_sql = "SELECT d.c_head_group,d.c_head_group2,d.c_width,f.c_id,d.c_group_alignment,d.c_alignment,d.c_pattern,d.c_is_subtotal, f.c_rpt_id, f.c_def_field_id, f.c_calc_exp, f.c_is_display, f.c_display_name, f.c_config, d.c_field, d.c_data_type, d.c_is_group, d.c_is_sum, d.c_sum_method, d.c_is_percentage, d.c_is_query_time, d.c_query_range, d.c_url FROM t_rpt_field f INNER JOIN t_rpt r ON r.c_id = f.c_rpt_id INNER JOIN t_rpt_def_field d ON d.c_id = f.c_def_field_id  WHERE r.c_id = '"
				+ reportId + "' ORDER BY d.c_sort_id";
		List<Map<String, Object>> datas = DbUtils4Its.runSqlReturnMap(t_sql);
		if (datas.size() <= 0) {
			return null;
		}
		Map<String, FieldPojo> t_result = new LinkedHashMap<String, FieldPojo>();
		for (Map<String, Object> t_map : datas) {
			FieldPojo t_field = new FieldPojo();
			ObjectMapper.map2Object(t_map, t_field);
			t_result.put(t_field.getField(), t_field);
		}

		return t_result;
	}

	public static ReportPojo getReportPojo(String reportId) {
		String t_sql = "SELECT r.c_id,d.c_is_show_page_number,d.c_row_number_type ,r.c_rpt_def_id, r.c_name,IFNULL(d.c_title,r.c_title) c_title,IFNULL(d.c_sub_title,r.c_sub_title) c_sub_title, r.c_order_field, r.c_order_type, r.c_doc_types, r.c_page_rows, r.c_topn, r.c_param_value1, r.c_param_value2, r.c_param_value3, r.c_param_value4, r.c_param_value5, d.c_name_en, d.c_type_id, d.c_display_type_id, d.c_class c_class_name, d.c_chart_type,d.c_chart_x, d.c_chart_y, d.c_chart_value,  d.c_chart_orientation, d.c_sql,d.c_chart_sql, d.c_sub_rpt_def_ids FROM t_rpt r INNER JOIN t_rpt_def d ON r.c_rpt_def_id = d.c_id WHERE r.c_id = '"
				+ reportId + "' ORDER BY d.c_sort_id";
		List<Map<String, Object>> datas = DbUtils4Its.runSqlReturnMap(t_sql);
		if (datas.size() <= 0) {
			return null;
		}
		ReportPojo t_reportPojo = new ReportPojo();
		ObjectMapper.map2Object(datas.get(0), t_reportPojo);

		t_reportPojo.setFields(getFieldPojos(t_reportPojo.getId()));
		// TODO set param
		return t_reportPojo;
	}

	public static Map<String, ParamPojo> getParamPojos(String reportId) {
		// TODO
		String t_sql = "SELECT f.c_id, f.c_rpt_id, f.c_def_field_id, f.c_calc_exp, f.c_is_display, f.c_display_name, f.c_config, d.c_field, d.c_data_type, d.c_is_group, d.c_is_sum, d.c_sum_method, d.c_is_percentage, d.c_is_query_time, d.c_query_range, d.c_url FROM t_rpt_field f INNER JOIN t_rpt r ON r.c_id = f.c_rpt_id INNER JOIN t_rpt_def_field d ON r.c_rpt_def_id = d.c_rpt_def_id WHERE r.c_id = '"
				+ reportId + "'";
		List<Map<String, Object>> datas = DbUtils4Its.runSqlReturnMap(t_sql);
		if (datas.size() <= 0) {
			return null;
		}
		Map<String, ParamPojo> t_result = new HashMap<String, ParamPojo>();
		for (Map<String, Object> t_map : datas) {
			ParamPojo t_field = new ParamPojo();
			ObjectMapper.map2Object(t_map, t_field);
			t_result.put(t_field.getField(), t_field);
		}

		return t_result;
	}
}
