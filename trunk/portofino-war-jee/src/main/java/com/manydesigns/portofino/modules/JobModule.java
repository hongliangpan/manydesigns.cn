package com.manydesigns.portofino.modules;

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

import javax.servlet.ServletContext;

import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.manydesigns.portofino.di.Inject;
import com.manydesigns.portofino.menu.MenuBuilder;

/*
 * @author Paolo Predonzani     - paolo.predonzani@manydesigns.com
 * @author Angelo Lupo          - angelo.lupo@manydesigns.com
 * @author Giampiero Granatella - giampiero.granatella@manydesigns.com
 * @author Alessio Stalla       - alessio.stalla@manydesigns.com
 */
public class JobModule implements Module {
	public static final String copyright = "Copyright (c) 2005-2013, ManyDesigns srl";

	// **************************************************************************
	// Fields
	// **************************************************************************

	@Inject(BaseModule.SERVLET_CONTEXT)
	public ServletContext servletContext;

	@Inject(BaseModule.PORTOFINO_CONFIGURATION)
	public Configuration configuration;

	@Inject(BaseModule.ADMIN_MENU)
	public MenuBuilder adminMenu;

	protected ModuleStatus status = ModuleStatus.CREATED;

	// **************************************************************************
	// Logging
	// **************************************************************************

	public static final Logger logger = LoggerFactory.getLogger(JobModule.class);

	@Override
	public String getModuleVersion() {
		return ModuleRegistry.getPortofinoVersion();
	}

	@Override
	public int getMigrationVersion() {
		return 1;
	}

	@Override
	public double getPriority() {
		return 20;
	}

	@Override
	public String getId() {
		return "dbJob";
	}

	@Override
	public String getName() {
		return "dbJob";
	}

	@Override
	public int install() {
		return 1;
	}

	@Override
	public void init() {

		status = ModuleStatus.ACTIVE;
	}

	@Override
	public void start() {
		// Quartz integration (optional)
		try {
			// In classe separata per permettere al modulo di essere caricato anche in assenza di Quartz a runtime
			// TODO 更新是否过保
			// DbScheduler.scheduleNowForOnetime("dbGroup");
			// DbScheduler.schedule("dbGroup");

		} catch (NoClassDefFoundError e) {
			logger.debug(e.getMessage(), e);
			logger.info("Quartz is not available, db job scheduler not started");
		} catch (Exception e) {
			logger.debug(e.getMessage(), e);
			logger.info("Quartz is not available, db job scheduler not started");
		}
		status = ModuleStatus.STARTED;
	}

	@Override
	public void stop() {
		status = ModuleStatus.STOPPED;
	}

	@Override
	public void destroy() {
		status = ModuleStatus.DESTROYED;
	}

	@Override
	public ModuleStatus getStatus() {
		return status;
	}
}
