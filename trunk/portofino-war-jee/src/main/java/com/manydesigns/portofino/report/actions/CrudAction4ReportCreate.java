package com.manydesigns.portofino.report.actions;

import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;

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
 * 根据定义模板，创建报表<br>
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
@SupportsPermissions({ CrudAction4ReportCreate.PERMISSION_CREATE, CrudAction4ReportCreate.PERMISSION_EDIT,
		CrudAction4ReportCreate.PERMISSION_DELETE })
@RequiresPermissions(level = AccessLevel.VIEW)
@ScriptTemplate("script_template.groovy")
@ConfigurationClass(CrudConfiguration.class)
@SupportsDetail
@PageActionName("Crud")
public class CrudAction4ReportCreate extends CrudAction4ReportView {

	@Button(list = "crud-read", key = "insertRptField", order = 3, icon = Button.ICON_EDIT + Button.ICON_WRENCH, group = "crud")
	@RequiresPermissions(permissions = PERMISSION_EDIT)
	public Resolution insertRptField() {
		insertDetail(object);
		QueryUtils.commit(persistence, DbUtils4Its.getDbName());
		return new RedirectResolution(appendSearchStringParamIfNecessary(context.getActionPath()));
	}

	@Override
	protected void createPostProcess(Object object) {
		super.createPostProcess(object);
		insertDetail(object);
	}

	public void insertDetail(Object object) {
		ReportCreateUtils.insertRptField(persistence, object);
		ReportCreateUtils.insertRptPeriod(persistence, object);
		ReportCreateUtils.insertRptParam(persistence, object);
	}

	@Override
	protected void editPostProcess(Object object) {
		super.editPostProcess(object);
		insertDetail(object);
	}
}
