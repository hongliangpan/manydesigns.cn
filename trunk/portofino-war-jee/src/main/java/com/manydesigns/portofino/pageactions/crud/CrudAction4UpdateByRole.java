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

import java.util.List;

import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;

import com.manydesigns.elements.Mode;
import com.manydesigns.elements.annotations.Enabled;
import com.manydesigns.elements.annotations.InSummary;
import com.manydesigns.elements.annotations.Insertable;
import com.manydesigns.elements.annotations.Searchable;
import com.manydesigns.elements.annotations.Updatable;
import com.manydesigns.elements.annotations.UpdatableByRole;
import com.manydesigns.elements.forms.FormBuilder;
import com.manydesigns.elements.reflection.PropertyAccessor;
import com.manydesigns.portofino.buttons.annotations.Button;
import com.manydesigns.portofino.buttons.annotations.Buttons;
import com.manydesigns.portofino.pageactions.PageActionName;
import com.manydesigns.portofino.pageactions.annotations.ConfigurationClass;
import com.manydesigns.portofino.pageactions.annotations.ScriptTemplate;
import com.manydesigns.portofino.pageactions.annotations.SupportsDetail;
import com.manydesigns.portofino.pageactions.crud.configuration.CrudConfiguration;
import com.manydesigns.portofino.pageactions.crud.reflection.CrudAccessor;
import com.manydesigns.portofino.pageactions.crud.reflection.CrudPropertyAccessor;
import com.manydesigns.portofino.security.AccessLevel;
import com.manydesigns.portofino.security.RequiresPermissions;
import com.manydesigns.portofino.security.SupportsPermissions;
import com.manydesigns.portofino.utils.AccessorUtils;
import com.manydesigns.portofino.utils.ContextUtils;

/**
 * Default AbstractCrudAction implementation. Implements a crud page over a database table, based on a HQL query.
 * 
 * @author Paolo Predonzani - paolo.predonzani@manydesigns.com
 * @author Angelo Lupo - angelo.lupo@manydesigns.com
 * @author Giampiero Granatella - giampiero.granatella@manydesigns.com
 * @author Alessio Stalla - alessio.stalla@manydesigns.com
 */
@SupportsPermissions({ CrudAction4UpdateByRole.PERMISSION_CREATE, CrudAction4UpdateByRole.PERMISSION_EDIT,
		CrudAction4UpdateByRole.PERMISSION_DELETE })
@RequiresPermissions(level = AccessLevel.VIEW)
@ScriptTemplate("script_template.groovy")
@ConfigurationClass(CrudConfiguration.class)
@SupportsDetail
@PageActionName("Crud")
public class CrudAction4UpdateByRole extends CrudAction {

	// **************************************************************************
	// Edit/Update
	// **************************************************************************

	@Buttons({
			@Button(list = "crud-read", key = "edit", order = 1, icon = Button.ICON_EDIT + Button.ICON_WHITE, group = "crud", type = Button.TYPE_SUCCESS),
			@Button(list = "crud-read-default-button", key = "search") })
	@RequiresPermissions(permissions = PERMISSION_EDIT)
	public Resolution edit() {
		setupForm(Mode.EDIT);
		editSetup(object);
		form.readFromObject(object);
		return getEditView();
	}

	protected void setupForm(Mode mode) {
		FormBuilder formBuilder = createFormBuilder();
		configureFormBuilder(formBuilder, mode);
		form = buildForm(formBuilder);
	}

	protected FormBuilder createFormBuilder() {
		CrudAccessor cloneClassAccessor = (CrudAccessor) classAccessor;
		try {
			// BeanUtils.copyProperties(cloneClassAccessor, classAccessor);
			for (PropertyAccessor accessor : cloneClassAccessor.getProperties()) {
				CrudPropertyAccessor crudAcc = (CrudPropertyAccessor) accessor;
				if (crudAcc != null && crudAcc.getCrudProperty().isUpdatable()) {
					boolean t_updateByRole = AccessorUtils.getUpdatableByRole(crudAcc);
					crudAcc.getCrudProperty().setUpdatable(t_updateByRole);
				}
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return new FormBuilder(classAccessor);
	}

	/**
	 * 增加注解 UpdatableByRole <br>
	 * AbstractField 读取Updatable 注解<br>
	 * AbstractCrudAction .setupPropertyEdits<br>
	 * <br>
	 * CrudPropertyAccessor<br>
	 */
	@Override
	protected void setupPropertyEdits() {
		if (classAccessor == null) {
			return;
		}
		PropertyAccessor[] propertyAccessors = classAccessor.getProperties();
		propertyEdits = new CrudPropertyEdit[propertyAccessors.length];
		for (int i = 0; i < propertyAccessors.length; i++) {
			CrudPropertyEdit edit = new CrudPropertyEdit();
			PropertyAccessor propertyAccessor = propertyAccessors[i];
			edit.name = propertyAccessor.getName();
			com.manydesigns.elements.annotations.Label labelAnn = propertyAccessor
					.getAnnotation(com.manydesigns.elements.annotations.Label.class);
			edit.label = labelAnn != null ? labelAnn.value() : null;
			Enabled enabledAnn = propertyAccessor.getAnnotation(Enabled.class);
			edit.enabled = enabledAnn != null && enabledAnn.value();
			InSummary inSummaryAnn = propertyAccessor.getAnnotation(InSummary.class);
			edit.inSummary = inSummaryAnn != null && inSummaryAnn.value();
			Insertable insertableAnn = propertyAccessor.getAnnotation(Insertable.class);
			edit.insertable = insertableAnn != null && insertableAnn.value();
			Updatable updatableAnn = propertyAccessor.getAnnotation(Updatable.class);
			edit.updatable = updatableAnn != null && updatableAnn.value();

			Searchable searchableAnn = propertyAccessor.getAnnotation(Searchable.class);
			edit.searchable = searchableAnn != null && searchableAnn.value();
			// hongliangpan add
			// setupPropertyEditsByRole(edit, propertyAccessor);

			propertyEdits[i] = edit;
		}
	}

	// hongliangpan add
	protected void setupPropertyEditsByRole(CrudPropertyEdit edit, PropertyAccessor propertyAccessor) {
		if (!edit.updatable) {
			return;
		}
		UpdatableByRole updatableByRoleAnn = propertyAccessor.getAnnotation(UpdatableByRole.class);
		if (null == updatableByRoleAnn) {
			return;
		}
		List<String> t_userRole = ContextUtils.getLoginUserRole(persistence);
		if (org.apache.shiro.util.CollectionUtils.isEmpty(t_userRole)) {
			return;
		}
		edit.updatable = false;
		for (String t_role : updatableByRoleAnn.value()) {
			if (t_userRole.contains(t_role)) {
				edit.updatable = true;
				break;
			}
		}
	}

	/**
	 * Returns the Resolution used to show the Create page.
	 */
	protected Resolution getCreateView() { // TODO spezzare in popup/non-popup?
		if (isPopup()) {
			return new ForwardResolution("/m/crud/popup/create.jsp" + getMenuLevel());
		} else {
			return new ForwardResolution("/m/crud/create.jsp" + getMenuLevel());
		}
	}

	public String getMenuLevel() {
		return "?navigation.startingLevel=1";
	}

	/**
	 * Returns the Resolution used to show the Edit page.
	 */
	protected Resolution getEditView() {
		return new ForwardResolution("/m/crud/edit.jsp" + getMenuLevel());
	}

	/**
	 * Returns the Resolution used to show the Read page.
	 */
	protected Resolution getReadView() {
		return forwardTo("/m/crud/read.jsp" + getMenuLevel());
	}

	/**
	 * Returns the Resolution used to show the Search page when this page is embedded in its parent.
	 */
	protected Resolution getEmbeddedReadView() {
		return new ForwardResolution("/m/crud/read.jsp" + getMenuLevel());
	}

	/**
	 * Returns the Resolution used to show the Search page.
	 */
	protected Resolution getSearchView() {
		return forwardTo("/m/crud/search.jsp" + getMenuLevel());
	}

	/**
	 * Returns the Resolution used to show the Search page when this page is embedded in its parent.
	 */
	protected Resolution getEmbeddedSearchView() {
		return new ForwardResolution("/m/crud/search.jsp" + getMenuLevel());
	}

	/**
	 * Returns the Resolution used to display the search results when paginating or sorting via AJAX.
	 */
	protected Resolution getSearchResultsPageView() {
		return new ForwardResolution("/m/crud/datatable.jsp" + getMenuLevel());
	}
}
