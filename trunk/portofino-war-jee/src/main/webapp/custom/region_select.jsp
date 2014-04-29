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
	String pageUrl = params.getPageUrlSingleRegion();
	List<Map<String, Object>> regions = DbUtils4Its.getRegions(request);
%>
<script type="text/javascript">
 	$(function() {
 		$('ul.dropdown-menu').on('click', function(e) {
 		    if($(this).hasClass('dropdown-menu-form')) {
 		        e.stopPropagation();
 		    }
 		});
	});
</script>
<div style="float:left;">
		<div class="btn-group" style="margin-left: -2px;float:left;">
			<button type="button" class="btn btn-info dropdown-toggle"
				data-toggle="dropdown">
				区域选择:&nbsp;<%=regionParam%>
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
							<button type="button"
							<%=region.equals(regionParam) ? "class='btn btn-small btn-warning'" : "class='btn btn-small btn-default'" %>
							onclick="location='<%=pageUrl%><%=region%>'">
							<%=region%></button>
						<% } %>
					</div>
				</li>

				<% for(String region2 : RegionUtils.getRegion2(regions)) { %>

				<li style="list-style: none; margin: 4px 0px;" class="divider"></li>
					<button type="button"  style="margin-left:8px;"
						<%=region2.equals(regionParam) ? "class='btn btn-small btn-warning'" : "class='btn btn-small btn-info'" %>
						onclick="location='<%=pageUrl%><%=region2%>'">
						<%=region2%>
					</button>

				<div class="btn-group btn-group-xs">
					<% for(Map map : RegionUtils.getRegion3(regions,region2)) { %>
					<button type="button"
						<%=map.get("c_region3").toString().equals(regionParam) ? "class='btn btn-small btn-warning'" : "class='btn btn-small btn-default'" %>
						onclick="location='<%=pageUrl%><%=map.get("c_region3").toString()%>'">
						<%=map.get("c_region3")%>
					</button>
					<% } %>
				</div>
				<% } %>
			</ul>
		</div>
	<!-- 多区域选择 -->
	<div class="btn-group"  style="float:left;" >
		<form action="<%=params.getPageUrl()%>">
			<button type="button" class="btn btn-info btn-xs dropdown-toggle" 
				data-toggle="dropdown">
				多区域选择:&nbsp;<%=params.getRegionDisplay()%>
				<span class="caret"></span>
			</button>
			<ul class="dropdown-menu dropdown-menu-form" role="menu" style="width: 480px;">

				<li style="list-style: none;">
					<div class="btn-group btn-group-xs"  style="margin-left:8px;">
						<button type="button"
							<%="全国".equals(params.getRegion()) ? "class='btn btn-small btn-warning'" : "class='btn btn-small btn-primary'" %>
							onclick="location='<%=params.getPageUrlSingleRegion()%>'">
							全国</button>
						<% for(String region : RegionUtils.getRegion1(regions)) { %>
							<input style="margin-left:5px;" type="checkbox" name="regionsCheckbox" value='<%=region%>' <%=params.checked(region)%>
							>
							<button type="button" style="margin-left:2px;"
							<%=params.isIncludeRegion(region) ? "class='btn btn-small btn-warning'" : "class='btn btn-small btn-default'" %>
							onclick="location='<%=params.getPageUrlSingleRegion()%><%=region%>'">
							<%=region%></button>
						<% } %>
						<div class="btn-group" style="margin-left: 25px;">
							<input type="submit" class='btn btn-small btn-success' onclick="location='<%=params.getPageUrl()%>' value="确认">
						</div>
					</div>
				</li>

				<% for(String region2 : RegionUtils.getRegion2(regions)) { %>

				<li style="list-style: none; margin: 4px 0px;" class="divider"></li>
					<input style="margin-left:5px;" type="checkbox" name="regionsCheckbox" value='<%=region2%>' <%=params.checked(region2)%>>
					<button type="button"  style="margin-left:2px;"
						<%=params.isIncludeRegion(region2) ? "class='btn btn-small btn-warning'" : "class='btn btn-small btn-info'" %>
						onclick="location='<%=params.getPageUrlSingleRegion()%><%=region2%>'">
						<%=region2%>
					</button>

				<div class="btn-group btn-group-xs">
					<% for(Map map : RegionUtils.getRegion3(regions,region2)) { %>
					<input style="margin-left:5px;" type="checkbox" name="regionsCheckbox" value='<%=map.get("c_region3").toString()%>'  <%=params.checked(map.get("c_region3").toString())%>>
					
					<button type="button" style="margin-left:2px;"
						<%=params.isIncludeRegion(map.get("c_region3").toString()) ? "class='btn btn-small btn-warning'" : "class='btn btn-small btn-default'" %>
						onclick="location='<%=params.getPageUrlSingleRegion()%><%=map.get("c_region3").toString()%>'">
						<%=map.get("c_region3")%>
					</button>
					<% } %>
				</div>
				<% } %>
			</ul>
			</form>
		</div>
	</div>
	</stripes:layout-component>

</stripes:layout-render>