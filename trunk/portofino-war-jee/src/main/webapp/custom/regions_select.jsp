<%@ page contentType="text/html;charset=UTF-8" language="java"	pageEncoding="UTF-8"%>
<%@ page import="com.manydesigns.portofino.modules.DatabaseModule"%>
<%@ page import="com.manydesigns.portofino.persistence.Persistence"%>
<%@ page import="com.manydesigns.portofino.persistence.QueryUtils"%>
<%@ page import="com.riil.itsboard.custom.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="stripes"
	uri="http://stripes.sourceforge.net/stripes-dynattr.tld"%>
<%@ page import="java.util.*"%>
<jsp:useBean id="actionBean" scope="request"
	type="com.manydesigns.portofino.pageactions.custom.CustomAction" />
<stripes:layout-render
	name="/theme/templates/${actionBean.pageInstance.layout.template}/normal.jsp">
	<stripes:layout-component name="pageTitle">
    </stripes:layout-component>
	<stripes:layout-component name="pageBody">
<%
	String pageId = "project_overview";

	Params params = Params.getParams(request,pageId);

	String contextPath = getServletContext().getContextPath();
	String regionParam = params.getRegion();
	String pageParam =  params.getPageId();
	String pageUrl = params.getPageUrl();
	List<Map<String, Object>> regions = DbUtils4Its.getRegions(request);
%>

<form action="<%=pageUrl%>">
		<div class="btn-group" style="margin-left: -2px;">
			<button type="button" class="btn btn-info dropdown-toggle"
				data-toggle="dropdown">
				选择区域:&nbsp;<%=regionParam%>
				<span class="caret"></span>
			</button>
			<ul class="dropdown-menu" role="menu" style="width: 340px;">

				<li style="list-style: none;">
					<div class="btn-group btn-group-xs" style="margin-left:8px;">
						<button type="button"
							<%="全国".equals(regionParam) ? "class='btn btn-small btn-warning'" : "class='btn btn-small btn-primary'" %>
							onclick="location='<%=pageUrl%>'">
							全国</button>
						<% for(String region : RegionUtils.getRegion1(regions)) { %>
						<input type="checkbox" name="regionsCheckbox" value='<%=region%>'><button type="button"
							<%=region.equals(regionParam) ? "class='btn btn-small btn-warning'" : "class='btn btn-small btn-default'" %>
							onclick="location='<%=pageUrl%><%=region%>'">
							<%=region%></button>
						<% } %>
					</div>
				</li>

				<% for(String region2 : RegionUtils.getRegion2(regions)) { %>

				<li style="list-style: none; margin: 4px 0px;" class="divider"></li>
					<input type="checkbox" name="regionsCheckbox" value='<%=region2%>'><button type="button"  style="margin-left:8px;"
						<%=region2.equals(regionParam) ? "class='btn btn-small btn-warning'" : "class='btn btn-small btn-info'" %>
						onclick="location='<%=pageUrl%><%=region2%>'">
						<%=region2%>
					</button>

				<div class="btn-group btn-group-xs">
					<% for(Map map : RegionUtils.getRegion3(regions,region2)) { %>
					<input type="checkbox" name="regionsCheckbox" value='<%=map.get("c_region3").toString()%>'><button type="button"
						<%=map.get("c_region3").toString().equals(regionParam) ? "class='btn btn-small btn-warning'" : "class='btn btn-small btn-default'" %>
						onclick="location='<%=pageUrl%><%=map.get("c_region3").toString()%>'">
						<%=map.get("c_region3")%>
					</button>
					<% } %>
				</div>
				<% } %>
			</ul>
			<input type="submit" value="确认">
		</div>
		
</form>
	</stripes:layout-component>

</stripes:layout-render>