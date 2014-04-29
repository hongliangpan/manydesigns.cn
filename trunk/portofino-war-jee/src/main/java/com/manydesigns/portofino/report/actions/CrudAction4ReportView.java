package com.manydesigns.portofino.report.actions;

import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.manydesigns.elements.ElementsThreadLocals;
import com.manydesigns.elements.messages.SessionMessages;
import com.manydesigns.portofino.buttons.annotations.Button;
import com.manydesigns.portofino.pageactions.PageActionName;
import com.manydesigns.portofino.pageactions.annotations.ConfigurationClass;
import com.manydesigns.portofino.pageactions.annotations.ScriptTemplate;
import com.manydesigns.portofino.pageactions.annotations.SupportsDetail;
import com.manydesigns.portofino.pageactions.crud.configuration.CrudConfiguration;
import com.manydesigns.portofino.report.enums.DocType;
import com.manydesigns.portofino.report.export.DefaultReportViewer;
import com.manydesigns.portofino.report.export.IReportViewer;
import com.manydesigns.portofino.report.export.builder.DataBuilder;
import com.manydesigns.portofino.report.pojo.ReportPojo;
import com.manydesigns.portofino.security.AccessLevel;
import com.manydesigns.portofino.security.RequiresPermissions;
import com.manydesigns.portofino.security.SupportsPermissions;

/**
 * <br>
 * <p>
 * Create on : 2014-3-6<br>
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
@SupportsPermissions({ CrudAction4Report.PERMISSION_CREATE, CrudAction4Report.PERMISSION_EDIT,
		CrudAction4Report.PERMISSION_DELETE })
@RequiresPermissions(level = AccessLevel.VIEW)
@ScriptTemplate("script_template.groovy")
@ConfigurationClass(CrudConfiguration.class)
@SupportsDetail
@PageActionName("Crud")
public class CrudAction4ReportView extends CrudAction4Report {

	public final static Logger logger = LoggerFactory.getLogger(CrudAction4ReportView.class);

	public Resolution exportFromList(DocType docType) {
		if (selection == null || selection.length != 1) {
			SessionMessages.addWarningMessage(ElementsThreadLocals.getText("no.object.was.selected"));
			return new RedirectResolution(returnUrl, false);
		}

		pk = selection[0].split("/");
		String reportId = pk[0];
		return export(docType, reportId);

	}

	public Resolution export(DocType docType) {
		String reportId = pk[0];
		return export(docType, reportId);
	}

	public Resolution export(DocType docType, String reportId) {
		ReportPojo t_reportPojo = DataBuilder.getReportPojo(reportId);

		IReportViewer reportView;
		if (StringUtils.isNotBlank(t_reportPojo.getClassName())) {
			try {
				reportView = (IReportViewer) Class.forName(t_reportPojo.getClassName()).newInstance();
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				reportView = new DefaultReportViewer();
			}
		} else {
			reportView = new DefaultReportViewer();
		}
		return reportView.export(context.getRequest(), t_reportPojo, docType, getReturnUrl());
	}

	@Button(list = "crud-search", key = "commons.exportHtml", order = 10, group = "crudExport", icon = "icon-share", type = Button.TYPE_WARNING)
	public Resolution exportSearchHtml() {
		return exportFromList(DocType.html);
	}

	@Button(list = "crud-read", key = "commons.exportHtml", order = 10)
	public Resolution exportReadHtml() {
		return export(DocType.html);
	}

	@Button(list = "crud-search", key = "commons.exportDoc", order = 11, group = "crudExport", icon = "icon-share")
	public Resolution exportSearchDoc() {
		return exportFromList(DocType.doc);
	}

	@Button(list = "crud-read", key = "commons.exportDoc", order = 11, group = "crudExport", icon = "icon-share")
	public Resolution exportReadDoc() {
		return export(DocType.doc);
	}

	@Button(list = "crud-search", key = "commons.exportDocx", order = 12, group = "crudExport", icon = "icon-share")
	public Resolution exportSearchDocx() {
		return exportFromList(DocType.docx);
	}

	@Button(list = "crud-read", key = "commons.exportDocx", order = 12, group = "crudExport", icon = "icon-share")
	public Resolution exportReadDocx() {
		return export(DocType.docx);
	}

	@Button(list = "crud-search", key = "commons.exportXls", order = 13, group = "crudExport", icon = "icon-share")
	public Resolution exportSearchExcel() {
		return exportFromList(DocType.xls);
	}

	@Button(list = "crud-read", key = "commons.exportXls", order = 13, group = "crudExport", icon = "icon-share")
	public Resolution exportReadExcel() {
		return export(DocType.xls);
	}

	@Button(list = "crud-search", key = "commons.exportXlsx", order = 14, group = "crudExport", icon = "icon-share")
	public Resolution exportSearchExcelx() {
		return exportFromList(DocType.xlsx);
	}

	@Button(list = "crud-read", key = "commons.exportXlsx", order = 14, group = "crudExport", icon = "icon-share")
	public Resolution exportReadExcelx() {
		return export(DocType.xlsx);
	}

	@Button(list = "crud-search", key = "commons.exportPng", order = 15, group = "crudExport", icon = "icon-share")
	public Resolution exportSearchPng() {
		return exportFromList(DocType.png);
	}

	@Button(list = "crud-read", key = "commons.exportPng", order = 15, group = "crudExport", icon = "icon-share")
	public Resolution exportReadPng() {
		return export(DocType.png);
	}

	@Button(list = "crud-search", key = "commons.exportPdf", order = 11, group = "crudExport", icon = "icon-share")
	public Resolution exportSearchPdf() {
		return exportFromList(DocType.pdf);
	}

	@Button(list = "crud-read", key = "commons.exportPdf", order = 11, group = "crudExport", icon = "icon-share")
	public Resolution exportPdf() {
		return export(DocType.pdf);
	}

	@Button(list = "crud-search", key = "commons.exportPptx", order = 19, group = "crudExport", icon = "icon-share")
	public Resolution exportSearchPptx() {
		return exportFromList(DocType.pptx);
	}

	@Button(list = "crud-read", key = "commons.exportPptx", order = 19, group = "crudExport", icon = "icon-share")
	public Resolution exportPptx() {
		return export(DocType.pptx);
	}

}
