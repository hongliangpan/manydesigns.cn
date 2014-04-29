package com.manydesigns.portofino.report.actions;

import java.util.HashMap;

import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;

import org.apache.commons.lang.StringUtils;
import org.hibernate.exception.ConstraintViolationException;

import com.manydesigns.elements.ElementsThreadLocals;
import com.manydesigns.portofino.buttons.annotations.Button;
import com.manydesigns.portofino.pageactions.PageActionName;
import com.manydesigns.portofino.pageactions.annotations.ConfigurationClass;
import com.manydesigns.portofino.pageactions.annotations.ScriptTemplate;
import com.manydesigns.portofino.pageactions.annotations.SupportsDetail;
import com.manydesigns.portofino.pageactions.crud.CrudAction4ItsProject;
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

@SupportsPermissions({ CrudAction4ReportType.PERMISSION_CREATE, CrudAction4ReportType.PERMISSION_EDIT,
		CrudAction4ReportType.PERMISSION_DELETE })
@RequiresPermissions(level = AccessLevel.VIEW)
@ScriptTemplate("script_template.groovy")
@ConfigurationClass(CrudConfiguration.class)
@SupportsDetail
@PageActionName("Crud")
public class CrudAction4ReportType extends CrudAction4ItsProject {
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void processSortId(Object object) {
		if (!(object instanceof HashMap)) {
			return;
		}

		HashMap t_map = (HashMap) object;
		String t_sortId = "c_code";
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
		super.processSortId(object);
	}

	@Override
	protected void doSave(Object object) {
		try {

			processSortId(object);
			processToken(object);
			processExpired4Warranty(object);
			processModifyUser(object);
			// processExpired(object);
			session.save(baseTable.getActualEntityName(), object);
			// updateParentInfo();
		} catch (ConstraintViolationException e) {
			processException(e);
		} catch (Exception e) {
			logger.warn("Constraint violation in update", e);
			throw new RuntimeException(ElementsThreadLocals.getText("save.failed.because.constraint.violated")
					+ e.getMessage());
		}
	}

	@Override
	protected void doUpdate(Object object) {
		try {
			processShortName(object);
			processSortId(object);
			processToken(object);
			processExpired4Warranty(object);
			processModifyUser(object);
			// processExpired(object);
			session.update(baseTable.getActualEntityName(), object);
			// updateParentInfo();
		} catch (ConstraintViolationException e) {
			processException(e);
		}
	}

	@Button(list = "crud-search", key = "updateReportType", order = 3, icon = Button.ICON_EDIT + Button.ICON_WRENCH, group = "crud")
	@RequiresPermissions(permissions = PERMISSION_EDIT)
	public Resolution updateReportType() {
		DbUtils4Its.callProcedure(persistence, "{ call reportTypeUpdate() }");
		return new RedirectResolution(appendSearchStringParamIfNecessary(context.getActionPath()));
	}

	@Override
	protected void createPostProcess(Object object) {

	}

	@Override
	protected void editPostProcess(Object object) {
		// DbUtils.callProcedure(persistence, "{ call reportTypeUpdate(1) }");
	}

	public void updateParentInfo() {
		String table = this.getBaseTable().getTableName();
		if ("t_rpt_type".equalsIgnoreCase(table)) {
			new Thread(new Runnable() {

				@Override
				public void run() {
					DbUtils4Its.callProcedure(persistence, "{ call reportTypeUpdate() }");
				}
			}).start();
		}
	}
}
