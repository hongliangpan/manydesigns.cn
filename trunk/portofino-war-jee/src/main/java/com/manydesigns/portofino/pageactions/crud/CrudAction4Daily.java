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

import java.util.Date;
import java.util.HashMap;

import net.sourceforge.stripes.action.Resolution;

import org.hibernate.exception.ConstraintViolationException;

import com.manydesigns.elements.ElementsThreadLocals;
import com.manydesigns.elements.Mode;
import com.manydesigns.elements.fields.DateField;
import com.manydesigns.elements.fields.Field;
import com.manydesigns.portofino.buttons.annotations.Button;
import com.manydesigns.portofino.pageactions.PageActionName;
import com.manydesigns.portofino.pageactions.annotations.ConfigurationClass;
import com.manydesigns.portofino.pageactions.annotations.ScriptTemplate;
import com.manydesigns.portofino.pageactions.annotations.SupportsDetail;
import com.manydesigns.portofino.pageactions.crud.configuration.CrudConfiguration;
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
@SupportsPermissions({ CrudAction4Daily.PERMISSION_CREATE, CrudAction4Daily.PERMISSION_EDIT,
		CrudAction4Daily.PERMISSION_DELETE })
@RequiresPermissions(level = AccessLevel.VIEW)
@ScriptTemplate("script_template.groovy")
@ConfigurationClass(CrudConfiguration.class)
@SupportsDetail
@PageActionName("Crud")
public class CrudAction4Daily extends CrudAction4ItsProject {

	@Override
	protected void doSave(Object object) {
		try {

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

	@Override
	protected void doUpdate(Object object) {
		try {
			processShortName(object);
			processSortId(object);
			processToken(object);
			processExpired4Warranty(object);
			processModifyUser(object);
			session.update(baseTable.getActualEntityName(), object);
		} catch (ConstraintViolationException e) {
			processException(e);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void processModifyUser(Object object) {
		super.processModifyUser(object);
		HashMap t_map = (HashMap) object;
		String t_userName = ContextUtils.getLoginUser();
		String t_field = "c_user";
		Object t_value = t_map.get(t_field);
		if (null == t_value) {
			t_map.put(t_field, t_userName);
		}

		t_field = "c_date";
		t_value = t_map.get(t_field);
		if (null == t_value) {
			t_map.put(t_field, new Date());
		}

	}

	@Button(list = "crud-search", key = "create.new", order = 1, type = Button.TYPE_SUCCESS, icon = Button.ICON_PLUS
			+ Button.ICON_WHITE, group = "crud")
	@RequiresPermissions(permissions = PERMISSION_CREATE)
	public Resolution create() {
		setupForm(Mode.CREATE);
		object = (Object) classAccessor.newInstance();
		createSetup(object);
		form.readFromObject(object);

		setDefaultValue();

		return getCreateView();
	}

	public void setDefaultValue() {
		Field field = form.findFieldByPropertyName("c_date");
		if (null != field && field instanceof DateField) {
			((DateField) field).setValue(new Date());
		}
	}
}
