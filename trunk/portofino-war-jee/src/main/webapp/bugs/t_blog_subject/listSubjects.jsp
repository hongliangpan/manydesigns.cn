<%@ page import="com.google.inject.internal.Objects" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="com.manydesigns.elements.xml.XhtmlBuffer" %>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="stripes"
    uri="http://stripes.sourceforge.net/stripes-dynattr.tld"%>
<%@taglib prefix="mde" uri="/manydesigns-elements"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="portofino" %>
<stripes:layout-render
	name="/theme/templates/${actionBean.pageInstance.layout.template}/normal.jsp">
 <jsp:useBean id="actionBean" scope="request"
   type="com.manydesigns.portofino.pageactions.crud.CrudAction"/>
 <stripes:layout-component name="portletTitle">
   <c:out value="${actionBean.crudConfiguration.searchTitle}"/>
 </stripes:layout-component>
 <stripes:layout-component name="portletBody">
  <div id="posts" >
   <portofino:buttons list="crud-search" cssClass="portletButton" />
   <%
     XhtmlBuffer buffer = new XhtmlBuffer();
     int i = 0;
     for (HashMap object : (List<HashMap>)actionBean.objects){
       buffer.writeH2((String)object.get("title"));
       buffer.write(StringUtils.abbreviate(
        (String) object.get("summary"),300));
       buffer.writeBr();
       buffer.writeAnchor("post/"+object.get("id"), "Read");
       buffer.writeHr();
       i++;
       if(i==10)
         break;
     }
     out.println(buffer.toString());
     %>
   </div>
<script type="text/javascript">
   result = 10;
   isLoading=false;
   $(window).scroll(function () {
      if ($(window).scrollTop() + $(window).height() > 
            $('#posts').height() - 5 && !isLoading ) {
         isLoading = true;
         $.ajax({
            type: 'GET',
            url: "post?jsonSearchData=1&firstResult="+
                  result+"&maxResults=10",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (msg) {
            for (var i = 0; i < msg.Result.length; i++) {
                  $("div#posts").append('<h2>'+
                   msg.Result[i].title.value+
                   '</h2>'+msg.Result[i].summary.value+'<br/>'+
                   '<a href="post/'+msg.Result[i].id.value+
                   '">Read</a><hr/>');
                }
                isLoading = false;
                }
              });
              result = result+10;
            }
         });

</script>
  <portofino:buttons list="crud-search" cssClass="portletButton" />
  </stripes:layout-component>
</stripes:layout-render>