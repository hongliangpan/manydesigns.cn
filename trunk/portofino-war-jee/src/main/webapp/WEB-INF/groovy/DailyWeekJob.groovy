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


import groovy.sql.GroovyRowResult

import java.sql.SQLException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.List
import java.util.Map

import org.apache.shiro.util.CollectionUtils
import org.quartz.CronScheduleBuilder
import org.quartz.CronTrigger
import org.quartz.DisallowConcurrentExecution
import org.quartz.Job
import org.quartz.JobBuilder
import org.quartz.JobDetail
import org.quartz.JobExecutionContext
import org.quartz.JobExecutionException
import org.quartz.PersistJobDataAfterExecution
import org.quartz.Scheduler
import org.quartz.SchedulerException
import org.quartz.TriggerBuilder
import org.quartz.impl.StdSchedulerFactory
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import com.manydesigns.portofino.persistence.Persistence
import com.manydesigns.portofino.utils.GroovyDbUtils

/**
 * @author Paolo Predonzani - paolo.predonzani@manydesigns.com
 * @author Angelo Lupo - angelo.lupo@manydesigns.com
 * @author Giampiero Granatella - giampiero.granatella@manydesigns.com
 * @author Alessio Stalla - alessio.stalla@manydesigns.com
 */
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class DailyWeekJob implements Job {
	public static final String copyright = "Copyright (c) 2005-2013, ManyDesigns srl";

	public static final String MAIL_SENDER_KEY = "daily.mail.sender";
	public static final String MAIL_IDS_TO_MARK_AS_SENT = "mail.sender.idsToMarkAsSent";

	public static final int DEFAULT_POLL_INTERVAL = 1000;
	public static final Logger logger = LoggerFactory.getLogger(DailyWeekJob.class);

	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		MailProducer mailProducer = new MailProducer();
		mailProducer.execute();
	}

	/**
	 * Utility method to schedule the job at a fixed interval.
	 */
	public static void schedule(String group) throws SchedulerException {
		Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
		JobDetail job = JobBuilder.newJob(DailyWeekJob.class).withIdentity(MAIL_SENDER_KEY, group).build();

		CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity(MAIL_SENDER_KEY, group)
				.withSchedule(CronScheduleBuilder.cronSchedule("0 * * * * ?")).build();

		scheduler.scheduleJob(job, cronTrigger);
	}
}

public class MailProducer {

	public static final Logger logger = LoggerFactory.getLogger(MailProducer.class);
	public Persistence persistence;
	public DailyDao dao;

	public MailProducer(Persistence persistence) {
		super();
		this.persistence = persistence;
		dao = new DailyDao(persistence);
	}
	public MailProducer() {
		super();
		dao = new DailyDao();
	}
	public void execute() {
		try {
			Map<String, List<Date>> t_unwriterDays=	calcUnwritersDays();
			t_unwriterDays.each { user,days->
				SimpleDateFormat	sdf1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				System.out.println(user+sdf1.format(new Date()));
				days.each {  unwrite_day -> 
				SimpleDateFormat	sdf = new SimpleDateFormat("yyyy-MM-dd");
					System.out.println(user+"没写日报"+sdf.format(unwrite_day));
				}
			}
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}
	}

	public Map<String, List<Date>> calcUnwritersDays() throws SQLException {
		Date now = new Date();
		Map<String, List<Date>> t_result = new HashMap<String, List<Date>>();
		Map<String,GroovyRowResult> t_users = dao.getUsers();
		List<Date> t_workDays = dao.getWorkDays(now);

		Map<String,List<Date>> t_writerDays = dao.getWriters(now);
		t_users.each { userName ,user ->
			calcOneUserUnwriteDays(t_writerDays, user, t_workDays, t_result);
		}

		return t_result;
	}

	private calcOneUserUnwriteDays(Map<String,List<Date>> writerDays, GroovyRowResult user, List<Date> workDays, Map<String, List<Date>> result) {
		List<GroovyRowResult> t_writeDays=writerDays.get(user.c_name);
		List<Date>	t_unwriteDays= new ArrayList<Date>();
		// 一周都没有日报
		if(CollectionUtils.isEmpty(t_writeDays)){
			t_unwriteDays= workDays;
		}
		else{
			t_unwriteDays=workDays.minus(t_writeDays);
		}
		if(!CollectionUtils.isEmpty(t_unwriteDays)){
			result.put(user.c_name, t_unwriteDays)
		}
	}
}

public class DailyDao {

	protected static final String S_USER_SQL = "SELECT u.c_name,u.c_display_name,c_email FROM t_sys_user u  INNER JOIN t_sys_dept d ON u.c_dept_id =d.c_id WHERE d.c_name LIKE '%项目开发%'"

	protected static final String S_WORK_DAYS_SQL = "SELECT c_work_day FROM (\n" \
		+ "SELECT subdate(curdate(),date_format(curdate(),'%w')-1) c_work_day  FROM DUAL\n" + "UNION\n"\
		+ "SELECT subdate(curdate(),date_format(curdate(),'%w')-2)   FROM DUAL\n" + "UNION\n"\
		+ "SELECT subdate(curdate(),date_format(curdate(),'%w')-3)   FROM DUAL\n" + "UNION\n"\
		+ "SELECT subdate(curdate(),date_format(curdate(),'%w')-4)   FROM DUAL\n" + "UNION\n"\
		+ "SELECT subdate(curdate(),date_format(curdate(),'%w')-5)   FROM DUAL\n" + "UNION\n"\
		+ "SELECT subdate(curdate(),date_format(curdate(),'%w')-6)   FROM DUAL\n" + "UNION\n"\
		+ "SELECT subdate(curdate(),date_format(curdate(),'%w')-7)   FROM DUAL\n" + ") w\n" + "WHERE\n" + "(\n"\
		+ "WEEKDAY(c_work_day)<=4\n" + "OR\n"\
		+ "c_work_day IN (SELECT c_date FROM t_pm_day_off WHERE c_is_day_off!=1)\n" + ")\n" + "AND\n"\
		+ "c_work_day NOT IN (SELECT c_date FROM t_pm_day_off WHERE c_is_day_off=1)";

	protected static final String S_WRITE_DAILY_SQL = "SELECT c_user,c_date FROM t_pm_daily d \n" + "WHERE \n" + "(\n" \
		+ "d.c_date>= subdate(curdate(),date_format(curdate(),'%w')-1)\n" + "AND\n"\
		+ "d.c_date<=subdate(curdate(),date_format(curdate(),'%w')-7)\n" + ")\n" + "GROUP BY c_user,c_date";


	/**
	 * 写日报的用户及时间.
	 *
	 * @param date
	 * @return
	 * @throws SQLException
	 */
	public Map<String,List<GroovyRowResult>> getWriters(Date date) throws SQLException {
		Map<String,List<Date>> t_result = new HashMap<String,List<Date>>();
		List<GroovyRowResult> writers=GroovyDbUtils.getSql().rows(S_WRITE_DAILY_SQL);
		for (GroovyRowResult writer : writers) {
			List<GroovyRowResult> userWriteDays=t_result.get(writer.c_user);
			if(null==userWriteDays){
				userWriteDays=new ArrayList<GroovyRowResult>();
				t_result.put(writer.c_user, userWriteDays.c_date);
			}
			userWriteDays.add(writer);
		}
		return t_result;
		// return List<Map<String, Object>> DbUtils4Its.runSqlReturnMap(S_WRITE_DAILY_SQL, persistence);
	}

	/**
	 * 工作日 .
	 *
	 * @param date
	 * @return
	 * @throws SQLException
	 */
	public List<Date> getWorkDays(Date date) throws SQLException {
		List<Date> t_result = new ArrayList<Date>();
		List<GroovyRowResult> users= GroovyDbUtils.getSql().rows(S_WORK_DAYS_SQL);
		for (GroovyRowResult workDay : users) {
			t_result.add(workDay.c_work_day);
		}
		return t_result;
	}

	/**
	 * 用户及邮件信息.
	 *
	 * @return
	 */
	public Map<String,GroovyRowResult> getUsers() throws SQLException {
		Map<String,GroovyRowResult> t_userMap = new HashMap<String,GroovyRowResult>();
		List<GroovyRowResult> users=GroovyDbUtils.getSql().rows(S_USER_SQL);
		for (GroovyRowResult user : users) {
			t_userMap.put(user.c_name, user);
		}
		return t_userMap;
	}
}