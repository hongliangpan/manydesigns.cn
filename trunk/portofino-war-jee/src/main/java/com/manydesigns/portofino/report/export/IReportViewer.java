package com.manydesigns.portofino.report.export;

import javax.servlet.http.HttpServletRequest;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sourceforge.stripes.action.Resolution;

import com.manydesigns.portofino.report.enums.DocType;
import com.manydesigns.portofino.report.pojo.ReportPojo;

public interface IReportViewer {
	/**
	 * 导出报表.
	 * 
	 * @param request
	 * @param reportPojo
	 * @param docType
	 * @param returnUrl
	 * @return
	 */
	Resolution export(HttpServletRequest request, ReportPojo reportPojo, DocType docType, String returnUrl);

	/**
	 * 根据报表id生成 JasperReportBuilder.
	 * 
	 * @param reportPojo
	 * @return
	 */
	JasperReportBuilder getReportBuilder(ReportPojo reportPojo);

	/**
	 * 二次开发回调接口.
	 * 
	 * @param reportPojo
	 */
	void modifyReportPojo(ReportPojo reportPojo);
}
