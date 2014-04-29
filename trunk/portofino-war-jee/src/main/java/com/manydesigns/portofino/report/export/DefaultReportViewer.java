package com.manydesigns.portofino.report.export;

import java.io.File;
import java.io.FileInputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.StreamingResolution;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.manydesigns.elements.ElementsThreadLocals;
import com.manydesigns.elements.messages.SessionMessages;
import com.manydesigns.portofino.report.enums.DocType;
import com.manydesigns.portofino.report.export.builder.ExportUtils;
import com.manydesigns.portofino.report.export.builder.ReportBuilder;
import com.manydesigns.portofino.report.pojo.ReportPojo;

public class DefaultReportViewer implements IReportViewer {
	public final static Logger logger = LoggerFactory.getLogger(DefaultReportViewer.class);

	@Override
	public Resolution export(HttpServletRequest request, ReportPojo reportPojo, DocType docType, String returnUrl) {
		try {
			modifyReportPojo(reportPojo);
			JasperReportBuilder t_builder = getReportBuilder(reportPojo);

			modifyBuilder(t_builder, reportPojo);
			if (DocType.html == docType) {
				request.getSession().setAttribute(
						net.sf.jasperreports.j2ee.servlets.ImageServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE,
						t_builder.toJasperPrint());
				return ExportUtils.exportHtml(reportPojo, docType, returnUrl, t_builder);
			}

			String contentType = "application/" + docType.name();
			if (DocType.htmlFile == docType) {
				contentType = "text/html; charset=UTF-8";
			}
			final File tmpFile = File.createTempFile("export." + reportPojo.getNameEn(), ".read." + docType.name());
			ExportUtils.exportFile(t_builder, tmpFile, docType);
			FileInputStream fileInputStream = new FileInputStream(tmpFile);

			return new StreamingResolution(contentType, fileInputStream) {
				@Override
				protected void stream(HttpServletResponse response) throws Exception {
					super.stream(response);
					if (!tmpFile.delete()) {
						logger.warn("Temporary file {} could not be deleted", tmpFile.getAbsolutePath());
					}
				}
			}.setFilename(reportPojo.getNameEn() + "." + docType.name());
		} catch (Exception e) {
			logger.error(docType.name() + " export failed!" + e.getMessage(), e);
			SessionMessages.addErrorMessage(getMessage("commons.export.failed"));
			return new RedirectResolution(returnUrl);
		}
	}

	/**
	 * 修改报表对象,改变报表行为<br>
	 * 二次开发回调接口.
	 * 
	 * @param reportPojo
	 */
	@Override
	public void modifyReportPojo(ReportPojo reportPojo) {
	}

	/**
	 * 修改报表Builder,改变报表行为<br>
	 * 二次开发回调接口.
	 * 
	 * @param builder
	 */
	protected void modifyBuilder(JasperReportBuilder builder, ReportPojo reportPojo) {
	}

	protected String getMessage(String key, Object... args) {
		return ElementsThreadLocals.getText(key, args);
	}

	@Override
	public JasperReportBuilder getReportBuilder(ReportPojo reportPojo) {
		return ReportBuilder.getReportBuilder(reportPojo);
	}
}
