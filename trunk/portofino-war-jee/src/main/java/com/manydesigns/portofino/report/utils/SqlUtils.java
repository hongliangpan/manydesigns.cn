package com.manydesigns.portofino.report.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;

import com.manydesigns.portofino.report.pojo.DbColumn;
import com.riil.itsboard.custom.DbUtils4Its;

/**
 * {class description} <br>
 * <p>
 * Create on : 2014-3-3<br>
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
public class SqlUtils {
	/**
	 * 解析选择的表
	 */
	public static String parseTables(String sql) {
		String regex;

		if (isContains(sql, "(?i)\\s+where\\s+")) {
			regex = "(?i)(from)(.+)(where)";
		} else {
			regex = "(?i)(from)(.+)($)";
		}

		return getMatchedString(regex, sql);
	}

	/**
	 * 看word是否在lineText中存在，支持正则表达式
	 * 
	 * @param lineText
	 * @param word
	 * @return
	 */
	private static boolean isContains(String lineText, String word) {
		Pattern pattern = Pattern.compile(word, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(lineText);

		return matcher.find();
	}

	public static List<DbColumn> getDbColumns(String sql) {
		if (StringUtils.isBlank(sql)) {
			return new ArrayList<DbColumn>();
		}
		if (sql.toLowerCase().indexOf(" limit ") <= 0) {
			sql = sql + " limit 1";
		} else {
			sql = sql.substring(0, sql.toLowerCase().indexOf(" limit ")) + " limit 1";
		}
		List<DbColumn> t_result = runSqlGetFileds(DbUtils4Its.getSession(), sql, null);
		if (t_result.size() > 0) {
			return t_result;
		}

		return parseColumns(sql);
	}

	public static List<DbColumn> parseColumns(String sql) {
		List<DbColumn> t_result = new ArrayList<DbColumn>();
		if (StringUtils.isBlank(sql)) {
			return t_result;
		}

		String regex = "(select)(.+)(from)";
		List<String> t_fieldNames = getMatchedStrings(regex, sql);
		for (String t_name : t_fieldNames) {
			DbColumn t_filed = new DbColumn();
			t_filed.setName(t_name);
			t_result.add(t_filed);
		}
		return t_result;
	}

	public static List<DbColumn> runSqlGetFileds(Session session, final String queryString, final Object[] parameters) {
		final List<DbColumn> t_result = new ArrayList<DbColumn>();

		try {
			session.doWork(new Work() {
				public void execute(Connection connection) throws SQLException {
					PreparedStatement stmt = connection.prepareStatement(queryString);
					ResultSet rs = null;
					try {
						rs = stmt.executeQuery();
						com.mysql.jdbc.ResultSetMetaData md = (com.mysql.jdbc.ResultSetMetaData) rs.getMetaData();
						int fileds = md.getColumnCount();
						// 或者 连接参数 &useOldAliasMetadataBehavior=true
						md.useOldAliasBehavior = true;
						for (int t_i = 1; t_i <= fileds; t_i++) {
							DbColumn t_column = new DbColumn();

							t_column.setName(md.getColumnName(t_i));
							t_column.setJdbcType(md.getColumnTypeName(t_i));
							t_column.setColumnType(md.getColumnTypeName(t_i));
							t_result.add(t_column);
						}
						md.useOldAliasBehavior = false;
					} finally {
						if (null != rs) {
							rs.close();
						}
						if (null != stmt) {
							stmt.close();
						}
					}
				}
			});
		} catch (HibernateException e) {
			session.getTransaction().rollback();
			session.beginTransaction();
			throw e;
		}

		return t_result;
	}

	/**
	 * 从文本text中找到regex首次匹配的字符串，不区分大小写
	 * 
	 * @param regex： 正则表达式
	 * @param text：欲查找的字符串
	 * @return regex首次匹配的字符串，如未匹配返回空
	 */
	private static List<String> getMatchedStrings(String regex, String text) {
		Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);

		Matcher matcher = pattern.matcher(text);
		List<String> t_result = new ArrayList<String>();
		while (matcher.find()) {
			String[] t_split = matcher.group(2).split(",");
			for (String t_field : t_split) {
				if (t_field.indexOf("(?i) AS ") > 0) {
					t_field = t_field.replaceAll("(?i) AS ", " ");
				}
				if (t_field.indexOf(" ") > 0) {
					t_field = t_field.substring(t_field.lastIndexOf(" "));
				}
				t_result.add(t_field.trim());
			}
		}

		return t_result;
	}

	/**
	 * 从文本text中找到regex首次匹配的字符串，不区分大小写
	 * 
	 * @param regex： 正则表达式
	 * @param text：欲查找的字符串
	 * @return regex首次匹配的字符串，如未匹配返回空
	 */
	private static String getMatchedString(String regex, String text) {
		Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);

		Matcher matcher = pattern.matcher(text);

		while (matcher.find()) {
			return matcher.group(2);
		}

		return null;
	}

	public static void main(String[] args) {
		String sql = "select IFNULL(s.c_name,'未知') c_state,count(0) c_count from t_project p INNER JOIN t_customer c ON c.c_id=p.c_customer_id LEFT JOIN t_dict_state s ON p.c_state=s.c_id WHERE 1=1 GROUP BY s.c_id ORDER BY s.c_sort_id";
		System.out.println(getDbColumns(sql));
	}
}
