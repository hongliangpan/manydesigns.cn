<%@ page contentType="text/html;charset=UTF-8" language="java"	pageEncoding="UTF-8"%>
<%@ page import="com.manydesigns.portofino.modules.DatabaseModule"%>
<%@ page import="com.manydesigns.portofino.persistence.Persistence"%>
<%@ page import="com.manydesigns.portofino.persistence.QueryUtils"%>
<%@ page import="com.manydesigns.elements.stripes.ElementsActionBeanContext" %>
<%@ page import="com.riil.itsboard.utils.GroovyUtils"%>
<%@ page import="com.riil.itsboard.custom.*" %>
<%@ page import="java.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="stripes"
	uri="http://stripes.sourceforge.net/stripes-dynattr.tld"%>
<jsp:useBean id="actionBean" scope="request"
	type="com.manydesigns.portofino.pageactions.custom.CustomAction" />

<stripes:layout-render
	name="/theme/templates/${actionBean.pageInstance.layout.template}/normal.jsp">
<%
	Params params = Params.getParams(request,"customer_matrix");

// 不同部分，项目多一些 订单时间，项目经理等
String[][] statMenus = {
		{"c_customer_pm","客户负责人"},{"c_customer_name","客户"},
		{"c_region1","片区"},{"c_region2","大区"},{"c_region3","省区"},
		{"c_industry1","行业"},{"c_indestry2","细分"},
		{"c_state","状态"},
		{"c_version1","RIIL产品版本"},{"c_version2","BMC产品版本"},
		{"c_client_aspiration","客户意愿"},{"c_is_complaint","投诉客户"},{"c_complaint_time_y","投诉客户(年)"}
		
};
/*
String[][] statMenus = {
		{"pm_name","项目经理"},{"money","合同额"},{"category","ABC类别"},{"region1","片区"},
		{"region2","大区"},{"region3","省区"},{"indestry","行业"},{"state","状态"},
		{"warranty","维保期限"},{"warranty_pass","是否过保"},{"client__aspiration","客户意愿"},{"is_template","是否样板客户"},
		{"is_tmpt_pass","是否具备样板条件"},{"is_difficult","是否难点客户"},{"is_good","是否用的好"},{"version","RIIL产品版本"},
		{},
		{"order_time_y","订单时间(年)"},{"pm_name","订单时间(季度)"},{"order_time_m","订单时间(月)"},
		{"begin_date_y","项目开工时间(年)"},{"begin_date_q","项目开工时间(季度)"},{"begin_date_m","项目开工时间(月)"},
		{"end_date_y","项目竣工时间(年)"},{"end_date_q","项目竣工时间(季度)"},{"end_date_m","项目竣工时间(月)"},
};
*/ 
%>
	<stripes:layout-component name="pageTitle">
	客户矩阵
	</stripes:layout-component>
	<stripes:layout-component name="pageHeader">
	<script type="text/javascript">
	 	$(function() {
	 		$('ul.dropdown-menu').on('click', function(e) {
	 		    if($(this).hasClass('dropdown-menu-form')) {
	 		        e.stopPropagation();
	 		    }
	 		});
		});
	 </script>
<!-- 	 <div style="float:left;">
	 	客户矩阵分析
	 </div>  -->
	 
	<%
	 		List<Map<String, Object>> regions = DbUtils4Its.getRegions(request);
	 	%>
	<!-- 区域选择 -->
	<div style="float:left;">
		<div class="btn-group" style="margin-left: -1px;float:left;">
			<button type="button" class="btn btn-info dropdown-toggle"
				data-toggle="dropdown">
				区域选择:&nbsp;<%=params.getRegion()%>
				<span class="caret"></span>
			</button>
			<ul class="dropdown-menu" role="menu" style="width: 340px;">

				<li style="list-style: none;">
					<div class="btn-group btn-group-xs"  style="margin-left:8px;">
						<button type="button"
							<%="全国".equals(params.getRegion()) ? "class='btn btn-small btn-warning'" : "class='btn btn-small btn-primary'"%>
							onclick="location='<%=params.getPageUrlSingleRegion()%>'">
							全国</button>
						<%
							for(String region : RegionUtils.getRegion1(regions)) {
						%>
							<button type="button"
							<%=region.equals(params.getRegion()) ? "class='btn btn-small btn-warning'" : "class='btn btn-small btn-default'"%>
							onclick="location='<%=params.getPageUrlSingleRegion()%><%=region%>'">
							<%=region%></button>
						<%
							}
						%>
					</div>
				</li>

				<%
					for(String region2 : RegionUtils.getRegion2(regions)) {
				%>

				<li style="list-style: none; margin: 4px 0px;" class="divider"></li>
					<button type="button"  style="margin-left:8px;"
						<%=region2.equals(params.getRegion()) ? "class='btn btn-small btn-warning'" : "class='btn btn-small btn-info'"%>
						onclick="location='<%=params.getPageUrlSingleRegion()%><%=region2%>'">
						<%=region2%>
					</button>

				<div class="btn-group btn-group-xs">
					<%
						for(Map map : RegionUtils.getRegion3(regions,region2)) {
					%>
					<button type="button"
						<%=map.get("c_region3").toString().equals(params.getRegion()) ? "class='btn btn-small btn-warning'" : "class='btn btn-small btn-default'"%>
						onclick="location='<%=params.getPageUrlSingleRegion()%><%=map.get("c_region3").toString()%>'">
						<%=map.get("c_region3")%>
					</button>
					<%
						}
					%>
				</div>
				<%
					}
				%>
			</ul>
		</div>
		<!-- 多区域选择 -->
	<div class="btn-group"  style="margin-left: 5px;float:left;">
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
							<%="全国".equals(params.getRegion()) ? "class='btn btn-small btn-warning'" : "class='btn btn-small btn-primary'"%>
							onclick="location='<%=params.getPageUrlSingleRegion()%>'">
							全国</button>
						<%
							for(String region : RegionUtils.getRegion1(regions)) {
						%>
							<input style="margin-left:5px;" type="checkbox" name="regionsCheckbox" value='<%=region%>' <%=params.checked(region)%>
							>
							<button type="button" style="margin-left:2px;"
							<%=params.isIncludeRegion(region) ? "class='btn btn-small btn-warning'" : "class='btn btn-small btn-default'"%>
							>
							<%=region%></button>
						<%
							}
						%>
						<div class="btn-group" style="margin-left: 25px;">
							<input type="submit" class='btn btn-small btn-success' onclick="location='<%=params.getPageUrl()%>' value="确认">
						</div>
					</div>
				</li>

				<%
					for(String region2 : RegionUtils.getRegion2(regions)) {
				%>

				<li style="list-style: none; margin: 4px 0px;" class="divider"></li>
					<input style="margin-left:5px;" type="checkbox" name="regionsCheckbox" value='<%=region2%>' <%=params.checked(region2)%>>
					<button type="button"  style="margin-left:2px;"
						<%=params.isIncludeRegion(region2) ? "class='btn btn-small btn-warning'" : "class='btn btn-small btn-info'"%>
						>
						<%=region2%>
					</button>

				<div class="btn-group btn-group-xs">
					<%
						for(Map map : RegionUtils.getRegion3(regions,region2)) {
					%>
					<input style="margin-left:5px;" type="checkbox" name="regionsCheckbox" value='<%=map.get("c_region3").toString()%>'  <%=params.checked(map.get("c_region3").toString())%>>
					
					<button type="button" style="margin-left:2px;"
						<%=params.isIncludeRegion(map.get("c_region3").toString()) ? "class='btn btn-small btn-warning'" : "class='btn btn-small btn-default'"%>
						>
						<%=map.get("c_region3")%>
					</button>
					<%
						}
					%>
				</div>
				<%
					}
				%>
			</ul>
			</form>
		</div>
     <div class="btn-group" style="margin-left: 5px;float:left;">
	  <button type="button" class="btn btn-info btn-xs dropdown-toggle" data-toggle="dropdown">
	    纵轴统计设置 <%=params.getStatYName(statMenus)%><span class="caret"></span>
	  </button>
	  <ul class="dropdown-menu" role="menu">
	  	 <%
	  	 	for (String[] menu : statMenus) {
	  	 %>
  	 		<%
  	 			if (menu.length>0) {
  	 		%>
 				<%
 					if (!menu[0].equals(params.getStatY())) {
 				%>
 						<li style="list-style:none;"><a href="/<%=params.getPageId()%>?region=<%=params.getRegion()%>&regions=<%=params.getRegions()%>&statx=<%=params.getStatX()%>&staty=<%=menu[0]%>&sortType=<%=params.getSortType().getId()%>" key="<%=menu[0]%>"><%=menu[1]%></a></li>
 				<%
 					} else {
 				%>
 						<li style="list-style:none;"><span class="label label-success" style="margin-left:18px;"><%=menu[1]%></span></li>
				<%
					}
				%>
  	 		<%
  	 			} else {
  	 		%>
  	 				<li style="list-style:none;" class="divider"></li>
  			<%
  				}
  			%>
	  	<%
	  		}
	  	%>
	  	
	  </ul>
	</div>			
	<div class="btn-group" style="margin-left: 5px;float:left;">
	  <button type="button" class="btn btn-info btn-xs dropdown-toggle" data-toggle="dropdown">
	    横轴统计设置 <%=params.getStatXName(statMenus)%><span class="caret"></span>
	  </button>
	  <ul class="dropdown-menu" role="menu">
	  	 	<%
	  	 		for (String[] menu : statMenus) {
	  	 	%>
	  	 		<%
	  	 			if (menu.length>0) {
	  	 		%>
	  	 				<%
	  	 					if (!menu[0].equals(params.getStatX())) {
	  	 				%>
	  	 						<li style="list-style:none;"><a href="/<%=params.getPageId()%>?region=<%=params.getRegion()%>&regions=<%=params.getRegions()%>&statx=<%=menu[0]%>&staty=<%=params.getStatY()%>&sortType=<%=params.getSortType().getId()%>" key="<%=menu[0]%>"><%=menu[1]%></a></li>
	  	 				<%
	  	 					} else {
	  	 				%>
	  	 						<li style="list-style:none;"><span class="label label-success" style="margin-left:18px;"><%=menu[1]%></span></li>
	  					<%
	  						}
	  					%>
	  	 		<%
	  	 			} else {
	  	 		%>
	  	 				<li style="list-style:none;" class="divider"></li>
	  			<%
	  				}
	  			%>
	  	<%
	  		}
	  	%>
	  </ul>
	</div>			
	
	<div class="btn-group" style="margin-left: 5px;float:left;">
	  <button type="button" class="btn btn-info btn-xs dropdown-toggle" data-toggle="dropdown">
	    排序设置 <%=params.getSortType().getName()%><span class="caret"></span>
	  </button>
	  <ul class="dropdown-menu" role="menu">
	  		<%
	  			for (SortType sortType : SortType.values()) {
	  			  			 if (sortType!=params.getSortType()) {
	  		%>
					<li style="list-style:none;"><a href="/<%=params.getPageId()%>?region=<%=params.getRegion()%>&regions=<%=params.getRegions()%>&statx=<%=params.getStatX()%>&staty=<%=params.getStatY()%>&sortType=<%=sortType.getId()%>" key="<%=sortType.getId()%>"><%=sortType.getName()%></a></li>
			<%
				} else {
			%>
					<li style="list-style:none;"><span class="label label-success" style="margin-left:18px;"><%=sortType.getName()%></span></li>
			<%
				} 
				}
			%>
	  </ul>
	</div>
			
	
	</div>
    
	
	</stripes:layout-component>
	<stripes:layout-component name="pageBody">
	<%
		String sql =
			 "SELECT c.c_state c_state_id, s.c_name c_state, c.c_id c_customer_id,CASE WHEN ( c.c_short_name = NULL OR c.c_short_name = '' ) THEN c.c_name ELSE c.c_short_name END c_name,"+
			 "c.c_name c_full_name,c.c_name c_customer_name, \n" +
			 "c.c_region1, c.c_region2, c.c_region3, c.c_industry1, c.c_industry2, c.c_pm,c.c_pmb, c.c_money, \n" +
			 "c.c_is_abnormal, c.c_warranty, c.c_warranty_end, IF(DATEDIFF(c_warranty_end,NOW())<0,true,false) c_is_expired,\n" +
			 "c.c_client_aspiration, c.c_is_template, c.c_is_template_boutique, c.c_is_difficult, c.c_is_good, c.c_is_template_conditions,\n" +
			 "c.c_version1, c.c_version2, c.c_is_develop, c.c_itsm_level, c.c_itsm_number, c.c_itsm_mode\n" +
			 ",IFNULL(cc.c_is_complaint,'无投诉') c_is_complaint,c_complaint_time,YEAR(c_complaint_time) c_complaint_time_y\n" +
			 "FROM t_customer c\n" +
			 "LEFT JOIN t_dict_state s ON s.c_id=c.c_state\n" +
			 "LEFT JOIN (\n" +
			 "SELECT cu.c_id c_customer_id,'有投诉' c_is_complaint,MAX(co.c_time) c_complaint_time FROM t_customer cu\n" +
			 "INNER JOIN t_customer_complaint co ON co.c_customer_id = cu.c_id\n" +
			 "GROUP BY cu.c_id\n" +
			 ") cc ON cc.c_customer_id = c.c_id\n" +
			 " WHERE {0} "+
			 "ORDER BY s.c_sort_id, c_is_difficult DESC, c_is_good DESC, c_is_template_boutique DESC, c_is_template DESC"
			 ;

		List<Map<String, Object>> mapList =DbUtils4Its.runSqlReturnMap(sql,request);
		//X 轴统计维度值
		List<String> statXValues = MatrixUtils.getStatValues(mapList,params.getStatX(),params.getSortType().getX());
	%>
  			<!-- 内容 -->
        <table class="table table-bordered">
        		<tr style="background-color:#EEEEEE;">
        			  <td>
									<%=params.getRegion()%>
									<span class="badge">总:<%=mapList.size() %></span>
        			  </td>
        			  <% for (String col : statXValues) { %>
        			  	<td><%=col%>&nbsp;&nbsp;&nbsp;<span class="badge"><%=MatrixUtils.getQuantity(mapList,params.getStatX(),col) %></span></td>
        			  <% } %>
        	  </tr>
        	  <% for (String row : MatrixUtils.getStatValues(mapList, params.getStatY(),params.getSortType().getY())) { %>
        		<tr>
        			  <td valign="top"><%=row%>&nbsp;&nbsp;&nbsp;<span class="badge"><%=MatrixUtils.getQuantity(mapList,params.getStatY(),row) %></span></td>
        			  <% for (String col : statXValues) { %>
        			  <td valign="top">
        			  	<!-- 项目单元格列表 -->
        			  	<% 
        			  			List<Map> cellPrjs = MatrixUtils.getStatProject(mapList, params.getStatY(), row, params.getStatX(),col);
        			  			boolean isLarge = cellPrjs.size() > 10 ;
        			  			isLarge = false ; // TODO
        			  			if (isLarge) {
        			  	%>
        			  			<h3><span class="label label-primary"><%=cellPrjs.size()%></span></h3>
        			  	<%	
        			  			}
        			  			
        			  			for (Map map : cellPrjs) {
        			  	%>
        			  	<a href="/t_customer/<%=map.get("c_customer_id")%>" data-roggle="tooltip" title="<%=map.get("c_full_name")%>" target="_blank" name="cell_id_<%=params.getStatX()%><%=params.getStatY()%>" <%=(isLarge? "style='display:none;'" : "")%>>
        			  		<span class="label label-default" style="line-height:12px; margin-right:4px;"><%=MatrixUtils.getNiceName(map) %>
        			  			<% if (MatrixUtils.parseBool(map.get("c_is_difficult"))) { %><span class="label label-important" style="line-height:10px;">难</span><% } %>
        			  			<% if (MatrixUtils.parseBool(map.get("c_is_template"))) { %><span class="label label-success" style="line-height:10px;" >板</span><% } %>
        			  			<% if (MatrixUtils.parseBool(map.get("c_is_template_boutique"))) { %><span class="label label-warning" style="line-height:10px;" >精</span><% } %>
        			  			<% if (MatrixUtils.parseBool(map.get("c_is_good"))) { %><span class="label label-info" style="line-height:10px;" >好</span><% } %>
        			  			<% if (MatrixUtils.parseBool(map.get("c_is_develop"))) { %><span class="label label-important" style="line-height:10px;" >开</span><% } %>
        			  		</span>
        			  	</a>
        			  	<%= (isLarge ? "" : "")%>
        			  	<br>
        			  	<% } %>
        			  </td>
        			  <% } %>
        	  </tr>
        	  <% } %>
        </table>  

    </stripes:layout-component>
</stripes:layout-render>