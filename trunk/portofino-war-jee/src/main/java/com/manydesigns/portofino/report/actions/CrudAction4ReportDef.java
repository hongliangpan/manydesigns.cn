package com.manydesigns.portofino.report.actions;

import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;

import com.manydesigns.elements.ElementsThreadLocals;
import com.manydesigns.elements.Mode;
import com.manydesigns.elements.messages.SessionMessages;
import com.manydesigns.portofino.buttons.annotations.Button;
import com.manydesigns.portofino.pageactions.PageActionName;
import com.manydesigns.portofino.pageactions.annotations.ConfigurationClass;
import com.manydesigns.portofino.pageactions.annotations.ScriptTemplate;
import com.manydesigns.portofino.pageactions.annotations.SupportsDetail;
import com.manydesigns.portofino.pageactions.crud.configuration.CrudConfiguration;
import com.manydesigns.portofino.persistence.QueryUtils;
import com.manydesigns.portofino.security.AccessLevel;
import com.manydesigns.portofino.security.RequiresPermissions;
import com.manydesigns.portofino.security.SupportsPermissions;
import com.riil.itsboard.custom.DbUtils4Its;

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
@SupportsPermissions({ CrudAction4ReportDef.PERMISSION_CREATE, CrudAction4ReportDef.PERMISSION_EDIT,
		CrudAction4ReportDef.PERMISSION_DELETE })
@RequiresPermissions(level = AccessLevel.VIEW)
@ScriptTemplate("script_template.groovy")
@ConfigurationClass(CrudConfiguration.class)
@SupportsDetail
@PageActionName("Crud")
public class CrudAction4ReportDef extends CrudAction4Report {

	@Button(list = "crud-search", key = "mergeReport", order = 3, icon = Button.ICON_EDIT + Button.ICON_WRENCH, group = "crud")
	@RequiresPermissions(permissions = PERMISSION_EDIT)
	public Resolution mergeReport() {
		// TODO 取选择的ids 创建一张新的报表，名称TODO,跳转到 编辑页面
		if (selection == null || selection.length == 0) {
			SessionMessages.addWarningMessage(ElementsThreadLocals.getText("no.object.was.selected"));
			return new RedirectResolution(returnUrl, false);
		}

		if (selection.length == 1) {
			pk = selection[0].split("/");
			String url = context.getActionPath() + "/" + getPkForUrl(pk);
			url = appendSearchStringParamIfNecessary(url);
			return new RedirectResolution(url).addParameter("returnUrl", returnUrl).addParameter("edit");
		}

		setupForm(Mode.BULK_EDIT);

		return new ForwardResolution("/m/crud/bulk-edit.jsp");

	}

	@Button(list = "crud-read", key = "insertRptDefField", order = 3, icon = Button.ICON_EDIT + Button.ICON_WRENCH, group = "crud")
	@RequiresPermissions(permissions = PERMISSION_EDIT)
	public Resolution insertRptDefField() {
		ReportDefFiledUtils.insertRptDefField(persistence, object);
		ReportPeriodUtils.insertRptDefPeriod(persistence, object);
		ReportPeriodUtils.insertRptDefParam(persistence, object);
		QueryUtils.commit(persistence, DbUtils4Its.getDbName());
		return new RedirectResolution(appendSearchStringParamIfNecessary(context.getActionPath()));
	}

	@Override
	protected void createPostProcess(Object object) {
		super.createPostProcess(object);
		// ReportPeriodUtils.insertRptDefPeriod(persistence, object);
		// ReportPeriodUtils.insertRptDefParam(persistence, object);
	}

	@Override
	protected void editPostProcess(Object object) {
		// TODO Auto-generated method stub
		super.editPostProcess(object);
		// ReportPeriodUtils.insertRptDefPeriod(persistence, object);
		// ReportPeriodUtils.insertRptDefParam(persistence, object);
	}
}
