/*
 * Copyright (C) 2005-2013 ManyDesigns srl.  All rights reserved.
 * http://www.manydesigns.com/
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 3 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package com.manydesigns.portofino.pageactions.crud;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.DateFormat;
import jxl.write.DateTime;
import jxl.write.Number;
import jxl.write.NumberFormat;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.StreamingResolution;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.fop.apps.FOPException;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.apache.shiro.codec.Base64;
import org.hibernate.exception.ConstraintViolationException;
import org.xml.sax.SAXException;

import com.manydesigns.elements.ElementsThreadLocals;
import com.manydesigns.elements.Mode;
import com.manydesigns.elements.fields.DateField;
import com.manydesigns.elements.fields.Field;
import com.manydesigns.elements.fields.NumericField;
import com.manydesigns.elements.fields.PasswordField;
import com.manydesigns.elements.forms.FieldSet;
import com.manydesigns.elements.forms.FormBuilder;
import com.manydesigns.elements.forms.TableForm;
import com.manydesigns.elements.forms.TableFormBuilder4ExportAll;
import com.manydesigns.elements.messages.SessionMessages;
import com.manydesigns.elements.util.MimeTypes;
import com.manydesigns.elements.xml.XmlBuffer;
import com.manydesigns.portofino.buttons.annotations.Button;
import com.manydesigns.portofino.files.TempFile;
import com.manydesigns.portofino.files.TempFileService;
import com.manydesigns.portofino.pageactions.PageActionName;
import com.manydesigns.portofino.pageactions.annotations.ConfigurationClass;
import com.manydesigns.portofino.pageactions.annotations.ScriptTemplate;
import com.manydesigns.portofino.pageactions.annotations.SupportsDetail;
import com.manydesigns.portofino.pageactions.crud.configuration.CrudConfiguration;
import com.manydesigns.portofino.persistence.QueryUtils;
import com.manydesigns.portofino.security.AccessLevel;
import com.manydesigns.portofino.security.RequiresPermissions;
import com.manydesigns.portofino.security.SupportsPermissions;
import com.manydesigns.portofino.utils.ContextUtils;

/**
 * Default AbstractCrudAction implementation. Implements a crud page over a database table, based on a HQL query.
 * 
 * @author Paolo Predonzani - paolo.predonzani@manydesigns.com
 * @author Angelo Lupo - angelo.lupo@manydesigns.com
 * @author Giampiero Granatella - giampiero.granatella@manydesigns.com
 * @author Alessio Stalla - alessio.stalla@manydesigns.com
 */
@SupportsPermissions({ CrudAction4ItsProject.PERMISSION_CREATE, CrudAction4ItsProject.PERMISSION_EDIT,
		CrudAction4ItsProject.PERMISSION_DELETE })
@RequiresPermissions(level = AccessLevel.VIEW)
@ScriptTemplate("script_template.groovy")
@ConfigurationClass(CrudConfiguration.class)
@SupportsDetail
@PageActionName("Crud")
public class CrudAction4ItsProject extends CrudAction4UpdateByRole {

	@Override
	protected void commitTransaction() {
		session.getTransaction().commit();
	}

	/**
	 * Configures the builder for the search detail (view, create, edit) form. You can override this method to customize
	 * how the form is generated (e.g. adding custom links on specific properties, hiding or showing properties based on
	 * some runtime condition, etc.).
	 * 
	 * @param formBuilder the form builder.
	 * @param mode the mode of the form.
	 * @return the form builder.
	 */
	protected FormBuilder configureFormBuilder(FormBuilder formBuilder, Mode mode) {
		formBuilder.configPrefix(prefix).configMode(mode);
		configureFormSelectionProviders(formBuilder);
		// hongliangpan add
		if (classAccessor.getProperties().length > 16) {
			formBuilder.configNColumns(3);
		} else if (classAccessor.getProperties().length > 8) {
			formBuilder.configNColumns(2);
		}
		return formBuilder;
	}

	@Override
	protected void doSave(Object object) {
		try {
			processPassword(object);
			processSortId(object);
			processToken(object);
			processExpired4Warranty(object);
			processModifyUser(object);
			session.save(baseTable.getActualEntityName(), object);

		} catch (ConstraintViolationException e) {
			processException(e);
		} catch (Exception e) {
			logger.warn("Constraint violation in update", e);
			throw new RuntimeException(ElementsThreadLocals.getText("save.failed.because.constraint.violated")
					+ e.getMessage());
		}
	}

	public void processException(ConstraintViolationException e) {
		String t_message = e.getCause().getMessage();
		if (t_message.indexOf("Duplicate entry") >= 0 && t_message.indexOf("_name") >= 0) {
			t_message = "【对象名称已经存在：" + getFieldValue(t_message) + "】";
		} else if (t_message.indexOf("Duplicate entry") >= 0) {
			t_message = "【数据已经存在：" + getFieldValue(t_message) + "】";
		}
		logger.warn("Constraint violation in update" + t_message, e);
		throw new RuntimeException(ElementsThreadLocals.getText("save.failed.because.constraint.violated") + t_message);
	}

	public String getFieldValue(String message) {
		return message.substring(message.indexOf("'") + 1, message.indexOf("' for key"));
	}

	@Override
	protected void doUpdate(Object object) {
		try {
			processPassword(object);
			processShortName(object);
			processSortId(object);
			processToken(object);
			processExpired4Warranty(object);
			processModifyUser(object);
			// processExpired(object);
			session.update(baseTable.getActualEntityName(), object);
		} catch (ConstraintViolationException e) {
			processException(e);
		}
	}

	public void processPassword(Object object) {
		if (!(object instanceof HashMap)) {
			return;
		}

		HashMap t_map = (HashMap) object;
		String t_password = "c_password";
		if (t_map.containsKey(t_password)) {
			Object t_value = t_map.get(t_password);
			t_value = encryptPassword(t_value.toString());
			t_map.put(t_password, t_value);
		}
	}

	String encryptPassword(String password) {
		return md5Base64(password);
	}

	String md5Base64(String password) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(password.getBytes("UTF-8"));
			byte[] raw = md.digest();
			return toBase64(raw);
		} catch (NoSuchAlgorithmException e) {
			processException(e);
		} catch (UnsupportedEncodingException e) {
			processException(e);
		}
		return password;
	}

	private void processException(Exception e) {
		logger.error(e.getMessage(), e);
	}

	protected String toBase64(byte[] bytes) {
		return Base64.encodeToString(bytes);
	}

	// hongliangpan add
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void processShortName(Object object) {
		if (!(object instanceof HashMap)) {
			return;
		}

		HashMap t_map = (HashMap) object;
		String t_shortName = "c_short_name";
		String c_name = "c_name";
		if (t_map.containsKey(t_shortName) && t_map.containsKey(c_name)) {
			Object t_value = t_map.get(t_shortName);
			if (StringUtils.isBlank(String.valueOf(t_value))) {
				t_value = t_map.get(c_name);
				t_map.put(t_shortName, t_value);
			}
		}
	}

	// hongliangpan add
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void processSortId(Object object) {
		if (!(object instanceof HashMap)) {
			return;
		}

		HashMap t_map = (HashMap) object;
		String t_sortId = "c_sort_id";
		String t_id = "c_id";
		Object t_value = t_map.get(t_sortId);
		if (t_map.containsKey(t_sortId) && t_map.containsKey(t_id)) {
			if (null == t_value || "0".equals(String.valueOf(t_value))) {
				t_value = t_map.get(t_id);
				if (org.apache.commons.lang3.math.NumberUtils.isNumber(t_value.toString())) {
					t_map.put(t_sortId, t_value);
				}
			}
		} else if (t_map.containsKey(t_sortId) && (null == t_value || StringUtils.isBlank((String.valueOf(t_value))))) {
			String table = this.getBaseTable().getTableName();
			String sql = "SELECT max(c_id)+1 FROM " + table;
			t_value = QueryUtils.runSql(session, sql).get(0)[0];
			t_map.put(t_sortId, t_value);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void processToken(Object object) {
		if (!(object instanceof HashMap)) {
			return;
		}

		HashMap t_map = (HashMap) object;
		String t_sortId = "c_token";
		Object t_value = t_map.get(t_sortId);
		if (t_map.containsKey(t_sortId)) {
			if (null == t_value || "".equals(String.valueOf(t_value))) {
				t_map.put(t_sortId, UUID.randomUUID().toString());
			}
		}
	}

	// hongliangpan add
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void processSortIdAfter(Object object) {
		if (!(object instanceof HashMap)) {
			return;
		}

		HashMap t_map = (HashMap) object;
		String t_sortId = "c_sort_id";
		String t_id = "c_id";
		if (t_map.containsKey(t_sortId) && t_map.containsKey(t_id)) {
			String table = this.getBaseTable().getTableName();
			String updateSql = "UPDATE " + table + " SET c_sort_id =c_id WHERE c_sort_id <= 0 OR c_sort_id IS NULL";
			QueryUtils.runSql(session, updateSql);
			// TODO
		}
	}

	public void processModifyUser(Object object) {
		if (!(object instanceof HashMap)) {
			return;
		}

		HashMap t_map = (HashMap) object;
		// if (!t_map.containsKey("c_modify_time")) {
		// return;
		// }
		String t_field = "c_create_time";
		Object t_value = t_map.get(t_field);
		if (null == t_value) {
			t_map.put(t_field, new Date());
		}

		String t_userName = ContextUtils.getLoginUser();
		t_field = "c_create_user";
		t_value = t_map.get(t_field);
		if (null == t_value) {
			t_map.put(t_field, t_userName);
		}

		t_field = "c_modify_time";
		t_map.put(t_field, new Date());
		t_field = "c_modify_user";
		t_map.put(t_field, t_userName);
	}

	// **************************************************************************
	// Object loading
	// **************************************************************************

	public void loadObjects() {
		super.loadObjects();
		processExpired();
	}

	// hongliangpan add
	public void processExpired() {
		for (Object row : objects) {
			processExpired(row);
		}
	}

	// hongliangpan add
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void processExpiredOldDelete(Object row) {
		Map rowMap = (HashMap) row;
		Date t_time = (Date) rowMap.get("c_warranty_end");
		if (null != t_time) {
			rowMap.put("c_is_expired", t_time.before(DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH)));
		}
	}

	// hongliangpan add
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void processExpired(Object row) {
		Map rowMap = (HashMap) row;
		Date t_time = (Date) rowMap.get("c_warranty_end");
		if (null != t_time) {
			rowMap.put("c_is_expired", t_time.before(DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH)));
		}
	}

	// hongliangpan add
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void processExpired4Warranty(Object row) {
		Map rowMap = (HashMap) row;
		Date t_timeEnd = (Date) rowMap.get("c_warranty_end");
		Date t_timeBegin = (Date) rowMap.get("c_warranty_begin");
		if (null != t_timeEnd && null != t_timeBegin) {
			long timeBetween = (DateUtils.truncate(t_timeEnd, Calendar.DAY_OF_MONTH).getTime()

			- DateUtils.truncate(t_timeBegin, Calendar.DAY_OF_MONTH).getTime()) / 86400000;
			// / 1000 / 60 / 60 / 24
			timeBetween = (long) Math.ceil(((timeBetween + 1) / 30f));
			rowMap.put("c_warranty", String.valueOf(timeBetween));
			rowMap.put("c_is_expired", t_timeEnd.before(DateUtils.truncate(new Date(), Calendar.DAY_OF_MONTH)));
		}
	}

	// hongliangpan copy from 4.0.10
	// **************************************************************************
	// ExportSearch
	// **************************************************************************
	// --------------------------------------------------------------------------
	// Export
	// --------------------------------------------------------------------------

	protected static final String TEMPLATE_FOP_SEARCH = "templateFOP-Search.xsl";
	protected static final String TEMPLATE_FOP_READ = "templateFOP-Read.xsl";

	@Button(list = "crud-search", key = "commons.exportExcel", order = 9, group = "crud", icon = " icon-th ")
	public Resolution exportSearchExcel() {
		try {
			TempFileService fileService = TempFileService.getInstance();
			TempFile tempFile = fileService.newTempFile("application/vnd.ms-excel", crudConfiguration.getSearchTitle()
					+ ".xls");
			OutputStream outputStream = tempFile.getOutputStream();
			exportSearchExcel(outputStream);
			outputStream.flush();
			outputStream.close();
			return fileService.stream(tempFile);
		} catch (Exception e) {
			logger.error("Excel export failed", e);
			SessionMessages.addErrorMessage(getMessage("commons.export.failed"));
			// return new RedirectResolution(getDispatch().getOriginalPath());
			return new RedirectResolution(getReturnUrl());
		}
	}

	public void exportSearchExcel(OutputStream outputStream) {
		setupSearchForm();
		loadObjects();
		setupTableForm4ExportAll(Mode.VIEW);

		writeFileSearchExcel(outputStream);
	}

	protected void setupTableForm4ExportAll(Mode mode) {
		TableFormBuilder4ExportAll tableFormBuilder = new TableFormBuilder4ExportAll(classAccessor);
		configureTableFormSelectionProviders(tableFormBuilder);

		int nRows;
		if (objects == null) {
			nRows = 0;
		} else {
			nRows = objects.size();
		}

		configureTableFormBuilder(tableFormBuilder, mode, nRows);
		tableForm = buildTableForm(tableFormBuilder);

		if (objects != null) {
			tableForm.readFromObject(objects);
			refreshTableBlobDownloadHref();
		}
	}

	private void writeFileSearchExcel(OutputStream outputStream) {
		WritableWorkbook workbook = null;
		try {
			WorkbookSettings workbookSettings = new WorkbookSettings();
			workbookSettings.setUseTemporaryFileDuringWrite(false);
			workbook = Workbook.createWorkbook(outputStream, workbookSettings);
			String title = crudConfiguration.getSearchTitle();
			if (StringUtils.isBlank(title)) {
				title = "export";
			}
			WritableSheet sheet = workbook.createSheet(title, 0);

			addHeaderToSearchSheet(sheet);

			int i = 1;
			for (TableForm.Row row : tableForm.getRows()) {
				exportRows(sheet, i, row);
				i++;
			}

			workbook.write();
		} catch (IOException e) {
			logger.warn("IOException", e);
			SessionMessages.addErrorMessage(e.getMessage());
		} catch (RowsExceededException e) {
			logger.warn("RowsExceededException", e);
			SessionMessages.addErrorMessage(e.getMessage());
		} catch (WriteException e) {
			logger.warn("WriteException", e);
			SessionMessages.addErrorMessage(e.getMessage());
		} finally {
			try {
				if (workbook != null)
					workbook.close();
			} catch (Exception e) {
				logger.warn("IOException", e);
				SessionMessages.addErrorMessage(e.getMessage());
			}
		}
	}

	// **************************************************************************
	// ExportRead
	// **************************************************************************

	@Button(list = "crud-read", key = "commons.exportExcel", order = 9)
	public Resolution exportReadExcel() {
		try {
			TempFileService fileService = TempFileService.getInstance();
			TempFile tempFile = fileService.newTempFile("application/vnd.ms-excel", crudConfiguration.getReadTitle()
					+ ".xls");
			OutputStream outputStream = tempFile.getOutputStream();
			exportReadExcel(outputStream);
			outputStream.flush();
			outputStream.close();
			return fileService.stream(tempFile);
		} catch (Exception e) {
			logger.error("Excel export failed", e);
			SessionMessages.addErrorMessage(getMessage("commons.export.failed"));
			// return new RedirectResolution(getDispatch().getOriginalPath());
			return new RedirectResolution(getReturnUrl());
		}
	}

	public void exportReadExcel(OutputStream outputStream) throws IOException, WriteException {
		setupSearchForm();

		loadObjects();

		setupForm(Mode.VIEW);
		form.readFromObject(object);

		writeFileReadExcel(outputStream);
	}

	private void writeFileReadExcel(OutputStream outputStream) throws IOException, WriteException {
		WritableWorkbook workbook = null;
		try {
			WorkbookSettings workbookSettings = new WorkbookSettings();
			workbookSettings.setUseTemporaryFileDuringWrite(false);
			workbook = Workbook.createWorkbook(outputStream, workbookSettings);
			WritableSheet sheet = workbook.createSheet(crudConfiguration.getReadTitle(), workbook.getNumberOfSheets());

			addHeaderToReadSheet(sheet);

			int i = 1;
			for (FieldSet fieldset : form) {
				int j = 0;
				for (Field field : fieldset.fields()) {
					addFieldToCell(sheet, i, j, field);
					j++;
				}
				i++;
			}
			workbook.write();
		} catch (IOException e) {
			logger.warn("IOException", e);
			SessionMessages.addErrorMessage(e.getMessage());
		} catch (RowsExceededException e) {
			logger.warn("RowsExceededException", e);
			SessionMessages.addErrorMessage(e.getMessage());
		} catch (WriteException e) {
			logger.warn("WriteException", e);
			SessionMessages.addErrorMessage(e.getMessage());
		} finally {
			try {
				if (workbook != null)
					workbook.close();
			} catch (Exception e) {
				logger.warn("IOException", e);
				SessionMessages.addErrorMessage(e.getMessage());
			}
		}
	}

	private WritableCellFormat headerExcel() {
		WritableFont fontCell = new WritableFont(WritableFont.ARIAL, 12, WritableFont.BOLD, true);
		return new WritableCellFormat(fontCell);
	}

	private void exportRows(WritableSheet sheet, int i, TableForm.Row row) throws WriteException {
		int j = 0;
		for (Field field : row) {
			addFieldToCell(sheet, i, j, field);
			j++;
		}
	}

	private void addHeaderToReadSheet(WritableSheet sheet) throws WriteException {
		WritableCellFormat formatCell = headerExcel();
		int i = 0;
		for (FieldSet fieldset : form) {
			for (Field field : fieldset.fields()) {
				sheet.addCell(new jxl.write.Label(i, 0, field.getLabel(), formatCell));
				i++;
			}
		}
	}

	private void addHeaderToSearchSheet(WritableSheet sheet) throws WriteException {
		WritableCellFormat formatCell = headerExcel();
		int l = 0;
		for (TableForm.Column col : tableForm.getColumns()) {
			sheet.addCell(new jxl.write.Label(l, 0, col.getLabel(), formatCell));
			l++;
		}
	}

	private void addFieldToCell(WritableSheet sheet, int i, int j, Field field) throws WriteException {
		if (field instanceof NumericField) {
			NumericField numField = (NumericField) field;
			if (numField.getValue() != null) {
				Number number;
				BigDecimal decimalValue = numField.getValue();
				if (numField.getDecimalFormat() == null) {
					number = new Number(j, i, decimalValue == null ? null : decimalValue.doubleValue());
				} else {
					NumberFormat numberFormat = new NumberFormat(numField.getDecimalFormat().toPattern());
					WritableCellFormat writeCellNumberFormat = new WritableCellFormat(numberFormat);
					number = new Number(j, i, decimalValue == null ? null : decimalValue.doubleValue(),
							writeCellNumberFormat);
				}
				sheet.addCell(number);
			}
		} else if (field instanceof PasswordField) {
			jxl.write.Label label = new jxl.write.Label(j, i, PasswordField.PASSWORD_PLACEHOLDER);
			sheet.addCell(label);
		} else if (field instanceof DateField) {
			DateField dateField = (DateField) field;
			DateTime dateCell;
			Date date = dateField.getValue();
			if (date != null) {
				DateFormat dateFormat = new DateFormat(dateField.getDatePattern());
				WritableCellFormat wDateFormat = new WritableCellFormat(dateFormat);
				dateCell = new DateTime(j, i, dateField.getValue() == null ? null : dateField.getValue(), wDateFormat);
				sheet.addCell(dateCell);
			}
		} else {
			jxl.write.Label label = new jxl.write.Label(j, i, field.getStringValue());
			sheet.addCell(label);
		}
	}

	// **************************************************************************
	// exportSearchPdf
	// **************************************************************************

	@Button(list = "crud-search", key = "commons.exportPdf", order = 8, group = "crud", icon = " icon-share ")
	public Resolution exportSearchPdf() {
		try {
			// final File tmpFile = File.createTempFile(crudConfiguration.getName() + ".search", ".pdf");
			TempFileService fileService = TempFileService.getInstance();
			TempFile tempFile = fileService.newTempFile(MimeTypes.APPLICATION_PDF, crudConfiguration.getSearchTitle()
					+ ".pdf");
			OutputStream outputStream = tempFile.getOutputStream();
			exportSearchPdf(outputStream);
			outputStream.flush();
			outputStream.close();
			return fileService.stream(tempFile);
		} catch (Exception e) {
			logger.error("PDF export failed", e);
			SessionMessages.addErrorMessage(getMessage("commons.export.failed"));
			// return new RedirectResolution(getDispatch().getOriginalPath());
			return new RedirectResolution(getReturnUrl());
		}
	}

	public void exportSearchPdf(OutputStream outputStream) throws FOPException, IOException, TransformerException {

		setupSearchForm();

		loadObjects();

		setupTableForm4ExportAll(Mode.VIEW);

		FopFactory fopFactory = FopFactory.newInstance();
		// hongliangpan add
		try {
			String fopConfig = "fop.xml";
			String filePath = CrudAction4ItsProject.class.getClassLoader().getResource(fopConfig).getPath();
			fopFactory.setUserConfig(filePath);
			// String fonts = "/fonts"; 的上级目录
			fopFactory.setFontBaseURL(new File(filePath).getParent());
		} catch (SAXException e) {
			logger.error(e.getMessage(), e);
		}
		InputStream xsltStream = null;
		try {
			Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, outputStream);

			xsltStream = getSearchPdfXsltStream();

			// Setup XSLT
			TransformerFactory Factory = TransformerFactory.newInstance();
			Transformer transformer = Factory.newTransformer(new StreamSource(xsltStream));

			// Set the value of a <param> in the stylesheet
			transformer.setParameter("versionParam", "2.0");

			// Setup input for XSLT transformation
			Reader reader = composeXmlSearch();
			Source src = new StreamSource(reader);

			// Resulting SAX events (the generated FO) must be piped through to
			// FOP
			Result res = new SAXResult(fop.getDefaultHandler());

			// Start XSLT transformation and FOP processing
			transformer.transform(src, res);
			reader.close();

			outputStream.flush();
		} finally {
			IOUtils.closeQuietly(xsltStream);
		}
	}

	/**
	 * Returns a stream producing the contents of a XSLT document to produce the PDF export of the current search
	 * results.
	 */
	protected InputStream getSearchPdfXsltStream() {
		String templateFop = TEMPLATE_FOP_SEARCH;
		return getXsltStream(templateFop);
	}

	/**
	 * Returns a XSLT stream by searching for a file first in this action's directory, then at the root of the
	 * classpath.
	 * 
	 * @param templateFop the file to search for
	 * @return the stream
	 */
	protected InputStream getXsltStream(String templateFop) {
		// hongliangpan change dir 原来是 webapp\WEB-INF\pages\t_customer 改为 webapp\WEB-INF
		File dir = pageInstance.getDirectory();
		String rootPath = "WEB-INF";
		if (dir.getPath().indexOf(rootPath) > 0) {
			dir = new File(dir.getPath().substring(0, dir.getPath().indexOf(rootPath) + 8));
		}
		File fopFile = new File(dir, templateFop);
		if (fopFile.exists()) {
			logger.debug("Custom FOP template found: {}", fopFile);
			try {
				return new FileInputStream(fopFile);
			} catch (FileNotFoundException e) {
				throw new Error(e);
			}
		} else {
			logger.debug("Using default FOP template: {}", templateFop);
			ClassLoader cl = getClass().getClassLoader();
			return cl.getResourceAsStream(templateFop);
		}
	}

	/**
	 * Composes an XML document representing the current search results.
	 * 
	 * @return
	 * @throws IOException
	 */
	protected Reader composeXmlSearch() throws IOException {
		XmlBuffer xb = new XmlBuffer();
		xb.writeXmlHeader("UTF-8");
		xb.openElement("class");
		xb.openElement("table");
		xb.write(crudConfiguration.getSearchTitle());
		xb.closeElement("table");

		double[] columnSizes = setupXmlSearchColumnSizes();

		for (double columnSize : columnSizes) {
			xb.openElement("column");
			xb.openElement("width");
			xb.write(columnSize + "em");
			xb.closeElement("width");
			xb.closeElement("column");
		}

		for (TableForm.Column col : tableForm.getColumns()) {
			xb.openElement("header");
			xb.openElement("nameColumn");
			xb.write(col.getLabel());
			xb.closeElement("nameColumn");
			xb.closeElement("header");
		}

		for (TableForm.Row row : tableForm.getRows()) {
			xb.openElement("rows");
			for (Field field : row) {
				xb.openElement("row");
				xb.openElement("value");
				xb.write(field.getStringValue());
				xb.closeElement("value");
				xb.closeElement("row");
			}
			xb.closeElement("rows");
		}

		xb.closeElement("class");

		return new StringReader(xb.toString());
	}

	/**
	 * <p>
	 * Returns an array of column sizes (in characters) for the search export.<br />
	 * By default, sizes are computed comparing the relative sizes of each column, consisting of the header and the
	 * values produced by the search.
	 * </p>
	 * <p>
	 * Users can override this method to compute the sizes using a different algorithm, or hard-coding them for a
	 * particular CRUD instance.
	 * </p>
	 */
	protected double[] setupXmlSearchColumnSizes() {
		double[] headerSizes = new double[tableForm.getColumns().length];
		for (int i = 0; i < headerSizes.length; i++) {
			TableForm.Column col = tableForm.getColumns()[i];
			int length = StringUtils.length(col.getLabel());
			headerSizes[i] = length;
		}

		double[] columnSizes = new double[tableForm.getColumns().length];
		for (TableForm.Row row : tableForm.getRows()) {
			int i = 0;
			for (Field field : row) {
				int size = StringUtils.length(field.getStringValue());
				double relativeSize = ((double) size) / tableForm.getRows().length;
				columnSizes[i++] += relativeSize;
			}
		}

		double totalSize = 0;
		for (int i = 0; i < columnSizes.length; i++) {
			double effectiveSize = Math.max(columnSizes[i], headerSizes[i]);
			columnSizes[i] = effectiveSize;
			totalSize += effectiveSize;
		}
		while (totalSize > 75) {
			int maxIndex = 0;
			double max = 0;
			for (int i = 0; i < columnSizes.length; i++) {
				if (columnSizes[i] > max) {
					max = columnSizes[i];
					maxIndex = i;
				}
			}
			columnSizes[maxIndex] -= 1;
			totalSize -= 1;
		}
		while (totalSize < 70) {
			int minIndex = 0;
			double min = Double.MAX_VALUE;
			for (int i = 0; i < columnSizes.length; i++) {
				if (columnSizes[i] < min) {
					min = columnSizes[i];
					minIndex = i;
				}
			}
			columnSizes[minIndex] += 1;
			totalSize += 1;
		}
		return columnSizes;
	}

	// **************************************************************************
	// ExportRead
	// **************************************************************************

	/**
	 * Composes an XML document representing the current object.
	 * 
	 * @return
	 * @throws IOException
	 */
	protected Reader composeXmlPort() throws IOException, WriteException {
		setupSearchForm();

		loadObjects();

		setupTableForm(Mode.VIEW);
		setupForm(Mode.VIEW);
		form.readFromObject(object);

		XmlBuffer xb = new XmlBuffer();
		xb.writeXmlHeader("UTF-8");
		xb.openElement("class");
		xb.openElement("table");
		xb.write(crudConfiguration.getReadTitle());
		xb.closeElement("table");

		for (FieldSet fieldset : form) {
			xb.openElement("tableData");
			xb.openElement("rows");

			for (Field field : fieldset.fields()) {
				xb.openElement("row");
				xb.openElement("nameColumn");
				xb.write(field.getLabel());
				xb.closeElement("nameColumn");

				xb.openElement("value");
				xb.write(field.getStringValue());
				xb.closeElement("value");
				xb.closeElement("row");

			}
			xb.closeElement("rows");
			xb.closeElement("tableData");
		}

		xb.closeElement("class");

		return new StringReader(xb.toString());
	}

	public void exportReadPdf(File tempPdfFile) throws FOPException, IOException, TransformerException {
		setupSearchForm();

		loadObjects();

		setupTableForm(Mode.VIEW);

		FopFactory fopFactory = FopFactory.newInstance();

		FileOutputStream out = null;
		InputStream xsltStream = null;
		try {
			out = new FileOutputStream(tempPdfFile);

			Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, out);

			xsltStream = getXsltStream(TEMPLATE_FOP_READ);

			// Setup XSLT
			TransformerFactory Factory = TransformerFactory.newInstance();
			Transformer transformer = Factory.newTransformer(new StreamSource(xsltStream));

			// Set the value of a <param> in the stylesheet
			transformer.setParameter("versionParam", "2.0");

			// Setup input for XSLT transformation
			Reader reader = composeXmlPort();
			Source src = new StreamSource(reader);

			// Resulting SAX events (the generated FO) must be piped through to
			// FOP
			Result res = new SAXResult(fop.getDefaultHandler());

			// Start XSLT transformation and FOP processing
			transformer.transform(src, res);

			reader.close();
			out.flush();
		} catch (Exception e) {
			logger.warn("IOException", e);
			SessionMessages.addErrorMessage(e.getMessage());
		} finally {
			IOUtils.closeQuietly(xsltStream);
			try {
				if (out != null)
					out.close();
			} catch (Exception e) {
				logger.warn("IOException", e);
				SessionMessages.addErrorMessage(e.getMessage());
			}
		}
	}

	@Button(list = "crud-read", key = "commons.exportPdf", order = 8)
	public Resolution exportPdf() {
		try {
			final File tmpFile = File.createTempFile("export." + crudConfiguration.getName(), ".read.pdf");
			exportReadPdf(tmpFile);
			FileInputStream fileInputStream = new FileInputStream(tmpFile);
			return new StreamingResolution("application/pdf", fileInputStream) {
				@Override
				protected void stream(HttpServletResponse response) throws Exception {
					super.stream(response);
					if (!tmpFile.delete()) {
						logger.warn("Temporary file {} could not be deleted", tmpFile.getAbsolutePath());
					}
				}
			}.setFilename(crudConfiguration.getReadTitle() + ".pdf");
		} catch (Exception e) {
			logger.error("PDF export failed", e);
			SessionMessages.addErrorMessage(getMessage("commons.export.failed"));
			// return new RedirectResolution(getDispatch().getOriginalPath());
			return new RedirectResolution(getReturnUrl());

		}
	}
}
