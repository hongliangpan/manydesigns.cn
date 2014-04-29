package com.manydesigns.portofino.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import com.manydesigns.portofino.persistence.Persistence;
import com.manydesigns.portofino.stripes.AbstractActionBean;
import com.manydesigns.portofino.util.DbUtils;

// hongliangpan add
public class ContextUtils extends BaseContextUtils {

	@SuppressWarnings("rawtypes")
	public static String getLoginUser() {
		Subject subject = SecurityUtils.getSubject();
		if (null == subject.getPrincipals()) {
			return "";
		}
		Object t_primaryPrincipal = subject.getPrincipals().getPrimaryPrincipal();
		if (null == t_primaryPrincipal || !(t_primaryPrincipal instanceof HashMap)) {
			return "";
		}
		if (null != ((HashMap) t_primaryPrincipal).get("c_name")) {
			return ((HashMap) t_primaryPrincipal).get("c_name").toString();
		} else {
			return "";
		}
	}

	@SuppressWarnings("rawtypes")
	public static List<String> getLoginUserRole() {
		return getLoginUserRole(DbUtils.getPersistence());
	}

	@SuppressWarnings("rawtypes")
	public static List<String> getLoginUserRole(Persistence persistence) {
		List<String> t_result = new ArrayList<String>();
		String t_userName = getLoginUser();
		if (StringUtils.isBlank(t_userName)) {
			return t_result;
		}
		String sql = "SELECT c_role FROM t_sys_group g\n"
				+ "INNER JOIN t_sys_group_user_rel r ON r.c_group_id=g.c_id\n"
				+ "INNER JOIN t_sys_user u ON u.c_id = r.c_user_id\n" + "WHERE u.c_name ='" + t_userName + "'";
		List<Map<String, Object>> t_roles = DbUtils.runSqlReturnMap(sql, persistence);

		if (null == t_roles || t_roles.size() == 0) {
			return t_result;
		}
		for (Map<String, Object> t_map : t_roles) {
			if (null != t_map.get("c_role")) {
				t_result.add(t_map.get("c_role").toString());
			}
		}
		return t_result;
	}

	public static boolean isCurrentMenu(AbstractActionBean actionBean, String menu) {

		String path = actionBean.getContext().getActionPath();
		if (path.indexOf(menu) >= 0) {
			return true;
		}
		return false;

	}
}
