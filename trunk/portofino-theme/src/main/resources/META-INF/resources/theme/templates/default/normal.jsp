<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"
%><%@ page import="com.manydesigns.portofino.pageactions.PageActionLogic"
%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"
%><%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes-dynattr.tld"
%><%@ taglib tagdir="/WEB-INF/tags" prefix="portofino"
%><%@ taglib prefix="mde" uri="/manydesigns-elements"
%><jsp:useBean id="actionBean" scope="request"
               type="com.manydesigns.portofino.pageactions.AbstractPageAction" /><%--
--%><stripes:layout-definition><%--
--%><c:set var="embedded" value="<%= PageActionLogic.isEmbedded(actionBean) %>" scope="page" /><%--
--%><c:if test="${embedded}"><%--
--%><div class="pageHeader">
        <stripes:layout-component name="pageHeader">
            <div class="pull-right">
                <stripes:form action="${actionBean.context.actionPath}" method="post">
                    <input type="hidden" name="returnUrl"
                           value="<c:out value="${actionBean.returnUrl}"/>"/>
                    <portofino:buttons list="pageHeaderButtons" cssClass="btn-mini" />
                </stripes:form>
            </div>
            <h6 class="pageTitle">
                <stripes:layout-component name="pageTitle">
                    <c:out value="${actionBean.pageInstance.description}"/>
                </stripes:layout-component>
            </h6>
        </stripes:layout-component>
    </div>
    <div class="pageBody">
        <stripes:layout-component name="pageBody" />
    </div>
    <div class="pageFooter">
        <stripes:layout-component name="pageFooter" />
    </div><%--
--%></c:if><%--
--%><c:if test="${not embedded}"><%--
--%><!DOCTYPE html>
<html lang="<%= request.getLocale() %>">
    <jsp:include page="/theme/head.jsp">
        <jsp:param name="pageTitle" value="${pageTitle}" />
    </jsp:include>
    <body>
    <jsp:include page="/theme/header.jsp"/>
    <div class="container">
        <div class="row">
        	<table style="width:100%;"><tr><td style="width:160px;" valign="top">
            <div style="margin-left:30px;">
                <portofino:embedded-page-actions list="aboveNavigation" />
                <jsp:include page="/theme/navigation.jsp" />
                <portofino:embedded-page-actions list="belowNavigation" />
            </div>
            </td><td valign="top">
            <div class="content_panel" style="width:auto; margin-right:10px;" >
                <mde:sessionMessages />
                <div class="pageHeader">
                    <stripes:layout-component name="pageHeader">
                    	<jsp:include page="/theme/breadcrumbs.jsp" />
                        <div class="pull-right" style="position:relative;">
                            <stripes:form action="${actionBean.context.actionPath}" method="post">
                                <input type="hidden" name="returnUrl" value="<c:out value="${actionBean.returnUrl}"/>"/>
                                <portofino:buttons list="pageHeaderButtons" cssClass="btn-mini" />
                            </stripes:form>
                        </div>
                        <%--superpan comment <h3 class="pageTitle">
                            <stripes:layout-component name="pageTitle">
                                <c:out value="${actionBean.pageInstance.description}"/>
                            </stripes:layout-component>
                        </h3> --%>
                    </stripes:layout-component>
                </div>
                <div class="pageBody">
                    <stripes:layout-component name="pageBody" />
                </div>
                <div class="pageFooter">
                    <stripes:layout-component name="pageFooter" />
                </div>
                <div class="embeddedPages">
                    <portofino:embedded-page-actions list="default" cssClass="row-fluid" />
                    <div class="row-fluid">
                        <portofino:embedded-page-actions list="contentLayoutLeft" cssClass="span6" />
                        <portofino:embedded-page-actions list="contentLayoutRight" cssClass="span6" />
                    </div>
                    <portofino:embedded-page-actions list="contentLayoutBottom" cssClass="row-fluid" />
                </div>
                <div class="contentFooter">
                    <stripes:layout-component name="contentFooter" />
                </div>
            </div>
        	</td></tr></table>
        </div>
    </div>
    <jsp:include page="/theme/footer.jsp"/>
    </body>
</html>
</c:if>
</stripes:layout-definition>