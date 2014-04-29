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

import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;

import org.hibernate.exception.ConstraintViolationException;

import com.manydesigns.elements.ElementsThreadLocals;
import com.manydesigns.portofino.buttons.annotations.Button;
import com.manydesigns.portofino.pageactions.PageActionName;
import com.manydesigns.portofino.pageactions.annotations.ConfigurationClass;
import com.manydesigns.portofino.pageactions.annotations.ScriptTemplate;
import com.manydesigns.portofino.pageactions.annotations.SupportsDetail;
import com.manydesigns.portofino.pageactions.crud.configuration.CrudConfiguration;
import com.manydesigns.portofino.security.AccessLevel;
import com.manydesigns.portofino.security.RequiresPermissions;
import com.manydesigns.portofino.security.SupportsPermissions;
import com.riil.itsboard.custom.DbUtils4Its;

/**
 * Default AbstractCrudAction implementation. Implements a crud page over a database table, based on a HQL query.
 * 
 * @author Paolo Predonzani - paolo.predonzani@manydesigns.com
 * @author Angelo Lupo - angelo.lupo@manydesigns.com
 * @author Giampiero Granatella - giampiero.granatella@manydesigns.com
 * @author Alessio Stalla - alessio.stalla@manydesigns.com
 */
@SupportsPermissions({ CrudAction4Dept.PERMISSION_CREATE, CrudAction4Dept.PERMISSION_EDIT,
		CrudAction4Dept.PERMISSION_DELETE })
@RequiresPermissions(level = AccessLevel.VIEW)
@ScriptTemplate("script_template.groovy")
@ConfigurationClass(CrudConfiguration.class)
@SupportsDetail
@PageActionName("Crud")
public class CrudAction4Dept extends CrudAction4ItsProject {

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

	@Button(list = "crud-search", key = "updateDept", order = 3, icon = Button.ICON_EDIT + Button.ICON_WRENCH, group = "crud")
	@RequiresPermissions(permissions = PERMISSION_EDIT)
	public Resolution updateDept() {
		DbUtils4Its.callProcedure(persistence, "{ call updateDeptInfo(1) }");
		return new RedirectResolution(appendSearchStringParamIfNecessary(context.getActionPath()));
	}

	@Override
	protected void createPostProcess(Object object) {

	}

	@Override
	protected void editPostProcess(Object object) {
		// DbUtils.callProcedure(persistence, "{ call updateDeptInfo(1) }");
	}

	public void updateParentInfo() {
		String table = this.getBaseTable().getTableName();
		if ("t_sys_dept".equalsIgnoreCase(table)) {
			new Thread(new Runnable() {

				@Override
				public void run() {
					DbUtils4Its.callProcedure(persistence, "{ call updateDeptInfo(1) }");
				}
			}).start();
		}
	}
}
