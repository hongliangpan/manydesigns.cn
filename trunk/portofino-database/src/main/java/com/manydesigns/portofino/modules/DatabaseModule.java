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

package com.manydesigns.portofino.modules;

import java.io.File;
import java.util.List;

import javax.servlet.ServletContext;

import liquibase.database.DatabaseFactory;
import liquibase.snapshot.DatabaseSnapshotGenerator;
import liquibase.snapshot.DatabaseSnapshotGeneratorFactory;
import liquibase.sqlgenerator.SqlGeneratorFactory;

import org.apache.commons.configuration.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.manydesigns.portofino.actions.admin.ConnectionProvidersAction;
import com.manydesigns.portofino.actions.admin.ReloadModelAction;
import com.manydesigns.portofino.actions.admin.TablesAction;
import com.manydesigns.portofino.database.platforms.DatabasePlatformsRegistry;
import com.manydesigns.portofino.di.Inject;
import com.manydesigns.portofino.di.Injections;
import com.manydesigns.portofino.liquibase.sqlgenerators.PortofinoSelectFromDatabaseChangeLogLockGenerator;
import com.manydesigns.portofino.menu.MenuBuilder;
import com.manydesigns.portofino.menu.SimpleMenuAppender;
import com.manydesigns.portofino.persistence.Persistence;
import com.manydesigns.portofino.util.DbUtils;

/*
 * @author Paolo Predonzani     - paolo.predonzani@manydesigns.com
 * @author Angelo Lupo          - angelo.lupo@manydesigns.com
 * @author Giampiero Granatella - giampiero.granatella@manydesigns.com
 * @author Alessio Stalla       - alessio.stalla@manydesigns.com
 */
public class DatabaseModule implements Module {
	public static final String copyright = "Copyright (c) 2005-2013, ManyDesigns srl";

	// **************************************************************************
	// Fields
	// **************************************************************************

	@Inject(BaseModule.SERVLET_CONTEXT)
	public ServletContext servletContext;

	@Inject(BaseModule.PORTOFINO_CONFIGURATION)
	public Configuration configuration;

	@Inject(BaseModule.APPLICATION_DIRECTORY)
	public File applicationDirectory;

	@Inject(BaseModule.ADMIN_MENU)
	public MenuBuilder adminMenu;

	protected Persistence persistence;

	protected ModuleStatus status = ModuleStatus.CREATED;

	// **************************************************************************
	// Constants
	// **************************************************************************

	public static final String PERSISTENCE = "com.manydesigns.portofino.modules.DatabaseModule.persistence";
	public static final String DATABASE_PLATFORMS_REGISTRY = "com.manydesigns.portofino.modules.DatabaseModule.databasePlatformsRegistry";
	// Liquibase properties
	public static final String LIQUIBASE_ENABLED = "liquibase.enabled";

	// **************************************************************************
	// Logging
	// **************************************************************************

	public static final Logger logger = LoggerFactory.getLogger(DatabaseModule.class);

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
		return 10;
	}

	@Override
	public String getId() {
		return "database";
	}

	@Override
	public String getName() {
		return "Database";
	}

	@Override
	public int install() {
		return 1;
	}

	@Override
	public void init() {
		if (configuration.getBoolean(LIQUIBASE_ENABLED, true)) {
			logger.info("Setting up Liquibase");
			DatabaseFactory databaseFactory = DatabaseFactory.getInstance();
			logger.debug("Clearing Liquibase database factory registry");
			databaseFactory.clearRegistry();
			logger.debug("Clearing Liquibase database snapshot generator factory registry");
			List<DatabaseSnapshotGenerator> registry = DatabaseSnapshotGeneratorFactory.getInstance().getRegistry();
			registry.clear();
			SqlGeneratorFactory.getInstance().register(new PortofinoSelectFromDatabaseChangeLogLockGenerator());
		} else {
			logger.info("Liquibase is disabled");
		}

		logger.info("Initializing persistence");
		DatabasePlatformsRegistry databasePlatformsRegistry = new DatabasePlatformsRegistry(configuration);

		persistence = new Persistence(applicationDirectory, configuration, databasePlatformsRegistry);
		Injections.inject(persistence, servletContext, null);
		servletContext.setAttribute(DATABASE_PLATFORMS_REGISTRY, databasePlatformsRegistry);
		servletContext.setAttribute(PERSISTENCE, persistence);

		appendToAdminMenu();
		// hongliangpan add
		DbUtils.setPersistence(persistence);
		status = ModuleStatus.ACTIVE;
	}

	protected void appendToAdminMenu() {
		SimpleMenuAppender group;
		SimpleMenuAppender link;

		group = SimpleMenuAppender.group("dataModeling", null, "Data modeling", 3.0);
		adminMenu.menuAppenders.add(group);

		link = SimpleMenuAppender.link("dataModeling", "connectionProviders", null, "Connection providers",
				ConnectionProvidersAction.URL_BINDING, 1.0);
		adminMenu.menuAppenders.add(link);
		link = SimpleMenuAppender.link("dataModeling", "tables", null, "Tables", TablesAction.BASE_ACTION_PATH, 2.0);
		adminMenu.menuAppenders.add(link);
		link = SimpleMenuAppender.link("dataModeling", "reloadModel", null, "Reload model",
				ReloadModelAction.URL_BINDING, 3.0);
		adminMenu.menuAppenders.add(link);
	}

	@Override
	public void start() {
		persistence.loadXmlModel();
		status = ModuleStatus.STARTED;
	}

	@Override
	public void stop() {
		persistence.shutdown();
		status = ModuleStatus.STOPPED;
	}

	@Override
	public void destroy() {
		logger.info("ManyDesigns Portofino database module stopping...");
		logger.info("ManyDesigns Portofino database module stopped.");
		status = ModuleStatus.DESTROYED;
	}

	@Override
	public ModuleStatus getStatus() {
		return status;
	}
}
