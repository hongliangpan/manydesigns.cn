<%@ page import="java.util.HashMap" %>
<%@ page import="com.manydesigns.elements.xml.XhtmlBuffer" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"
       pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="stripes" 
       uri="http://stripes.sourceforge.net/stripes-dynattr.tld"%>
<%@taglib prefix="mde" uri="/manydesigns-elements"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="portofino" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<stripes:layout-render name="/bugs/t_blog_subject/portlet.jsp">
    <jsp:useBean id="actionBean" scope="request" 
       type="com.manydesigns.portofino.pageactions.crud.CrudAction"/>
    <stripes:layout-component name="portletTitle">
        <% HashMap obj = (HashMap)actionBean.object;%>
        <%= obj.get("title")%>
    </stripes:layout-component>
    <stripes:layout-component name="portletBody">
        <!-- Print the post -->
        <%
            HashMap obj = (HashMap)actionBean.object;
            XhtmlBuffer buffer = new XhtmlBuffer();
            buffer.openElement("em");
            buffer.write((String) obj.get("c_summary"));
            buffer.closeElement("em");
            buffer.writeBr();
            buffer.writeBr();
            buffer.writeNoHtmlEscape((String) obj.get("c_body"));
            buffer.writeBr();
            buffer.openElement("strong");
            buffer.write((String) obj.get("c_author"));
            buffer.closeElement("strong");
            buffer.writeBr();
            out.println(buffer.toString());
        %>

        <c:if test="${not empty actionBean.searchString}">
            <input type="hidden" name="searchString"
             value="<c:out value="${actionBean.searchString}"/>"/>
        </c:if>
    </stripes:layout-component>
    <stripes:layout-component name="portletFooter">
        <div class="crudReadButtons">
            <portofino:buttons list="crud-read" cssClass="portletButton" />
        </div>
        <stripes:submit name="print" value="Print" 
          disabled="true" class="portletButton"/>-->
        <script type="text/javascript">
            $(".crudReadButtons button[name=delete]").click(function() {
                return confirm ('<fmt:message key="commons.confirm" />');
            });
        </script>
    </stripes:layout-component>
</stripes:layout-render>