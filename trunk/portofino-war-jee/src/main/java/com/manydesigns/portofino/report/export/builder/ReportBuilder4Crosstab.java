package com.manydesigns.portofino.report.export.builder;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.manydesigns.portofino.report.pojo.ReportPojo;

//import static com.manydesigns.portofino.report.export.builder.ReportBuilder.*;
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
public class ReportBuilder4Crosstab {
	public final static Logger logger = LoggerFactory.getLogger(ExportUtils.class);

	@SuppressWarnings({ "rawtypes", "deprecation" })
	public static JasperReportBuilder getReportBuilder(ReportPojo reportPojo) {

		return ReportBuilder.getReportBuilder(reportPojo);
	}

}
