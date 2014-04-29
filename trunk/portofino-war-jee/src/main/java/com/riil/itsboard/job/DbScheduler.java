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

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Paolo Predonzani - paolo.predonzani@manydesigns.com
 * @author Angelo Lupo - angelo.lupo@manydesigns.com
 * @author Giampiero Granatella - giampiero.granatella@manydesigns.com
 * @author Alessio Stalla - alessio.stalla@manydesigns.com
 */
public class DbScheduler {
	public static final String copyright = "Copyright (c) 2005-2013, ManyDesigns srl";

	public static final Logger logger = LoggerFactory.getLogger(DbScheduler.class);

	/**
	 * Utility method to schedule the job at a fixed interval.
	 */
	public static void schedule(String group) throws SchedulerException {
		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
		JobDetail job = JobBuilder.newJob(DbUpdateJob.class).withIdentity(DbUpdateJob.JOB_KEY, group).build();
		// 4小时调用一次 TODO 配置文件
		String cronExpression = "0 1 0/4 * * ?";
		Trigger trigger = TriggerBuilder.newTrigger().withIdentity("db.job.trigger", group).startNow()
				.withSchedule(CronScheduleBuilder.cronSchedule(cronExpression)).build();

		scheduler.scheduleJob(job, trigger);
	}

	/**
	 * Utility method to schedule the job at a fixed interval.
	 */
	public static void scheduleNowForOnetime(String group) throws SchedulerException {
		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
		JobDetail job = JobBuilder.newJob(DbUpdateJob.class).withIdentity(DbUpdateJob.JOB_KEY + "temp", group).build();
		Trigger trigger = TriggerBuilder.newTrigger().withIdentity("db.job.trigger.now", group).startNow()
				.withSchedule(SimpleScheduleBuilder.repeatSecondlyForTotalCount(1)).build();

		scheduler.scheduleJob(job, trigger);
	}
}
