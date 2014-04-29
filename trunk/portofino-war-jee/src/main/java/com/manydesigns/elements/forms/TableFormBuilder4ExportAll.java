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

package com.manydesigns.elements.forms;

import com.manydesigns.elements.annotations.Enabled;
import com.manydesigns.elements.reflection.ClassAccessor;
import com.manydesigns.elements.reflection.JavaClassAccessor;
import com.manydesigns.elements.reflection.PropertyAccessor;

/**
 * 导出所有enabled字段 <br>
 * <p>
 * Create on : 2014-1-20<br>
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
public class TableFormBuilder4ExportAll extends TableFormBuilder {
	// **************************************************************************
	// Constructors
	// **************************************************************************

	public TableFormBuilder4ExportAll(Class aClass) {
		this(JavaClassAccessor.getClassAccessor(aClass));
	}

	public TableFormBuilder4ExportAll(ClassAccessor classAccessor) {
		super(classAccessor);
	}

	public boolean isPropertyVisible(PropertyAccessor current) {
		if (skippableProperty(current)) {
			return false;
		}

		if (!isPropertyEnabled(current)) {
			return false;
		}
		// check if field is in summary
		Enabled enabled = current.getAnnotation(Enabled.class);
		if (enabled != null && !enabled.value()) {
			logger.debug("Skipping non-in-summary field: {}", current.getName());
			return false;
		}
		return true;
	}
}
