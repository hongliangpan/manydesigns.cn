package com.manydesigns.portofino.report.actions;

import java.util.HashMap;
import java.util.UUID;

import net.sourceforge.stripes.action.Resolution;

import com.manydesigns.portofino.pageactions.PageActionName;
import com.manydesigns.portofino.pageactions.annotations.ConfigurationClass;
import com.manydesigns.portofino.pageactions.annotations.ScriptTemplate;
import com.manydesigns.portofino.pageactions.annotations.SupportsDetail;
import com.manydesigns.portofino.pageactions.crud.CrudAction4ItsProject;
import com.manydesigns.portofino.pageactions.crud.configuration.CrudConfiguration;
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
public class CrudAction4Report extends CrudAction4ItsProject {

	// hongliangpan add
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void processSortId(Object object) {
		if (!(object instanceof HashMap)) {
			return;
		}

		processId(object);
		super.processSortId(object);
	}

	public void processId(Object object) {
		HashMap t_map = (HashMap) object;
		String t_id = "c_id";
		Object t_value = t_map.get(t_id);
		// if (t_map.containsKey(t_id)) {
		if (null == t_value || "".equals(String.valueOf(t_value))) {
			t_map.put(t_id, UUID.randomUUID().toString());
		}
		// }
	}

	@Override
	public Resolution execute() {
		return super.execute();
	}
}
