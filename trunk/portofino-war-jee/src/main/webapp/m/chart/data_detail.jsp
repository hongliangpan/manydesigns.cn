<%@ page contentType="text/html;charset=UTF-8" language="java"
	pageEncoding="UTF-8"%>
<%@ page import="com.manydesigns.portofino.modules.DatabaseModule"%>
<%@ page import="com.manydesigns.portofino.persistence.Persistence"%>
<%@ page import="com.manydesigns.portofino.persistence.QueryUtils"%>
<%@ page
	import="com.manydesigns.portofino.pageactions.chart.ChartDataQueryUtils"%>
<%@ page
	import="com.manydesigns.elements.stripes.ElementsActionBeanContext"%>
<%@ page import="com.riil.itsboard.utils.GroovyUtils"%>
<%@ page import="com.riil.itsboard.custom.*"%>
<%@ page import="java.util.*"%>
<%@ page import="org.apache.commons.lang.StringEscapeUtils"%><%@ taglib
	prefix="c" uri="http://java.sun.com/jsp/jstl/core"%><%@ taglib
	prefix="stripes"
	uri="http://stripes.sourceforge.net/stripes-dynattr.tld"%><%@ taglib
	prefix="mde" uri="/manydesigns-elements"%><%@ taglib prefix="fmt"
	uri="http://java.sun.com/jsp/jstl/fmt"%>
		<%
	String sql =ChartDataQueryUtils.getQuerySql(request) ;
	 List<Map<String, Object>> mapList =DbUtils4Its.runSqlReturnMap(sql,request);
	 List<String> columns = DbUtils4Its. getColumnName(mapList,sql);
	 List<List<String>> rows =  DbUtils4Its.getRowValues(mapList,sql);
%><a href="Javascript:window.history.go(-1)" name="cell_id_">返回</a>
		<table class="" border="2px" bordercolor="#666666" cellspacing="0px"
			style="width: 100%;">
			<tr style="background-color: #EEEEEE;">
				<td><span class="badge">总:<%=mapList.size() %></span></td>
				<% for (String column : columns) { %>
				<td><%=column%></td>
				<% } %>
			</tr>
			<%int i=0; for (List<String> row : rows) {i++; %>
			<tr>
				<td valign="top"><span class="badge"><%=i%></span>
				</td>
				<% for (String value : row) { %>
				<td valign="top"><%=value %></td>
				<% } %>
			</tr>
			<% } %>
		</table>
		<a href="Javascript:window.history.go(-1)" name="cell_id_1">返回</a>
