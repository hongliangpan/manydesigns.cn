<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes-dynattr.tld"%>
<jsp:useBean id="actionBean" scope="request" type="com.manydesigns.portofino.pageactions.custom.CustomAction"/>
<stripes:layout-render
	name="/theme/templates/${actionBean.pageInstance.layout.template}/normal.jsp">
</stripes:layout-render>