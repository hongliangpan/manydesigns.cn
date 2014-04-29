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

package com.riil.itsboard.job;

import javax.servlet.ServletContext;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.manydesigns.portofino.di.Inject;
import com.manydesigns.portofino.modules.BaseModule;
import com.manydesigns.portofino.modules.DatabaseModule;
import com.manydesigns.portofino.persistence.Persistence;
import com.manydesigns.portofino.persistence.QueryUtils;

/**
 * @author Paolo Predonzani - paolo.predonzani@manydesigns.com
 * @author Angelo Lupo - angelo.lupo@manydesigns.com
 * @author Giampiero Granatella - giampiero.granatella@manydesigns.com
 * @author Alessio Stalla - alessio.stalla@manydesigns.com
 */
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class DbUpdateJob implements Job {
	public static final String copyright = "Copyright (c) 2005-2013, ManyDesigns srl";

	public static final String JOB_KEY = "DB_JOB";

	public static final Logger logger = LoggerFactory.getLogger(DbUpdateJob.class);

	public static final String SQL_UPDATE_IS_EXPIRED = "UPDATE t_customer SET c_is_expired=IF(DATEDIFF(c_warranty_end,NOW())<=0,1,0)";
	@Inject(BaseModule.SERVLET_CONTEXT)
	public ServletContext servletContext;

	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		try {
			Persistence persistence = (Persistence) servletContext.getAttribute(DatabaseModule.PERSISTENCE);
			// TODO get from context
			String t_databaseName = "riil_its_board";
			QueryUtils.runSqlDml(persistence.getSession(t_databaseName), SQL_UPDATE_IS_EXPIRED);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

}
