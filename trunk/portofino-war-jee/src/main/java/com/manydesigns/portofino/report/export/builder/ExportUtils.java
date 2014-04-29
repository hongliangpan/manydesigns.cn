package com.manydesigns.portofino.report.export.builder;

import static net.sf.dynamicreports.report.builder.DynamicReports.export;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.jasper.builder.export.JasperHtmlExporterBuilder;
import net.sf.dynamicreports.jasper.constant.ImageType;
import net.sf.dynamicreports.report.exception.DRException;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.StreamingResolution;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.manydesigns.elements.ElementsThreadLocals;
import com.manydesigns.elements.messages.SessionMessages;
import com.manydesigns.portofino.report.enums.DocType;
import com.manydesigns.portofino.report.pojo.ReportPojo;

public class ExportUtils {
	public final static Logger logger = LoggerFactory.getLogger(ExportUtils.class);

	public static void exportFile(JasperReportBuilder reportBuilder, File tmpFile, DocType docType) {
		FileOutputStream t_outputStream = null;
		try {
			t_outputStream = new FileOutputStream(tmpFile);
			switch (docType) {
			case pdf:
				reportBuilder.toPdf(t_outputStream);
				break;
			case doc:
				reportBuilder.toRtf(t_outputStream);
				break;
			case docx:
				reportBuilder.toDocx(t_outputStream);
				break;
			case xlsx:
				reportBuilder.toXlsx(t_outputStream);
				break;
			case xls:
				reportBuilder.toXls(t_outputStream);
				break;
			case pptx:
				reportBuilder.toPptx(t_outputStream);
				break;
			case png:
				reportBuilder.toImage(t_outputStream, ImageType.PNG);
				break;
			case html:
				JasperHtmlExporterBuilder htmlExporter = export.htmlExporter(t_outputStream).setImagesURI(
						"image?image=");
				reportBuilder.toHtml(htmlExporter);
				break;
			default:
				break;
			}

		} catch (DRException e) {
			logger.error(e.getMessage(), e);
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (null != t_outputStream) {
				try {
					t_outputStream.close();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}

	}

	public static Resolution exportHtml(ReportPojo reportPojo, DocType docType, String returnUrl,
			JasperReportBuilder reportBuilder) {
		StringWriter t_writer = null;
		try {
			t_writer = new StringWriter();
			JasperHtmlExporterBuilder htmlExporter = export.htmlExporter(t_writer).setImagesURI("/image?image=");
			// htmlExporter.setImagesDirName("/images").setOutputImagesToDir(true);
			reportBuilder.toHtml(htmlExporter);

			return new StreamingResolution("text/html; charset=utf-8", t_writer.toString());
		} catch (Exception e) {
			logger.error(docType.name() + " export failed", e);
			SessionMessages.addErrorMessage(getMessage("commons.export.failed"));
			return new RedirectResolution(returnUrl);
		} finally {
			if (null != t_writer) {
				try {
					t_writer.close();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
	}

	@Deprecated
	public static Resolution exportHtmlFile(ReportPojo reportPojo, DocType docType, String returnUrl,
			JasperReportBuilder reportBuilder) {
		FileOutputStream t_outputStream = null;
		try {
			// TODO 文件名的唯一 ,图片不能下载
			String t_htmlFile = "export." + reportPojo.getNameEn() + ".read." + docType.name();
			final File tmpFile = new File(t_htmlFile);
			t_outputStream = new FileOutputStream(tmpFile);
			JasperHtmlExporterBuilder htmlExporter = export.htmlExporter(t_outputStream).setImagesURI("image?image=");
			reportBuilder.toHtml(htmlExporter);

			return new ForwardResolution(t_htmlFile);
		} catch (Exception e) {
			logger.error(docType.name() + " export failed", e);
			SessionMessages.addErrorMessage(getMessage("commons.export.failed"));
			return new RedirectResolution(returnUrl);
		} finally {
			if (null != t_outputStream) {
				try {
					t_outputStream.close();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
	}

	protected static String getMessage(String key, Object... args) {
		return ElementsThreadLocals.getText(key, args);
	}
}
