package com.manydesigns.portofino.utils;

import java.util.List;

import org.apache.shiro.util.CollectionUtils;

import com.manydesigns.elements.annotations.UpdatableByRole;
import com.manydesigns.elements.reflection.ClassAccessor;
import com.manydesigns.portofino.model.Annotation;
import com.manydesigns.portofino.pageactions.crud.reflection.CrudPropertyAccessor;

// hongliangpan add
public class AccessorUtils {
	public static boolean getUpdatableByRole(CrudPropertyAccessor crudAccessor) {
		for (Annotation t_ann : crudAccessor.getCrudProperty().getAnnotations()) {
			if (t_ann.getJavaAnnotationClass().toString().indexOf("UpdatableByRole") > 0) {
				return getUpdatable(t_ann);
			}
		}
		return true;
	}

	public static boolean getUpdatable(Annotation annotation) {
		if (null == annotation) {
			return true;
		}
		List<String> t_userRole = ContextUtils.getLoginUserRole();
		if (CollectionUtils.isEmpty(t_userRole)) {
			return true;
		}
		for (String t_role : annotation.getValues()) {
			if (t_userRole.contains(t_role)) {
				return true;
			}
		}
		return false;
	}

	public static boolean getUpdatable(UpdatableByRole updatableByRoleAnn) {
		if (null == updatableByRoleAnn) {
			return true;
		}
		List<String> t_userRole = ContextUtils.getLoginUserRole();
		if (CollectionUtils.isEmpty(t_userRole)) {
			return true;
		}
		for (String t_role : updatableByRoleAnn.value()) {
			if (t_userRole.contains(t_role)) {
				return true;
			}
		}
		return false;
	}

	public static boolean getUpdatableByRole(ClassAccessor nestedAccessor) {

		UpdatableByRole updatableByRoleAnn = nestedAccessor.getAnnotation(UpdatableByRole.class);
		return getUpdatable(updatableByRoleAnn);
	}

}
