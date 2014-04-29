<%@ page contentType="text/html;charset=UTF-8" language="java"
         pageEncoding="UTF-8"
%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"
%><%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes-dynattr.tld"
%><%@taglib prefix="mde" uri="/manydesigns-elements"
%><%@ taglib tagdir="/WEB-INF/tags" prefix="portofino"
%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"
%><jsp:useBean id="actionBean" scope="request" type="com.manydesigns.portofino.pageactions.crud.AbstractCrudAction"
/><stripes:layout-render name="/theme/templates/${actionBean.pageInstance.layout.template}/modal.jsp">
    <stripes:layout-component name="pageTitle">
        <c:out value="${actionBean.editTitle}"/>
    </stripes:layout-component>
    <stripes:layout-component name="pageBody">
        <p><fmt:message key = "in.the.first.column.select.the.fields.you.want.to.edit"/></p>
        <stripes:form action="${actionBean.context.actionPath}" method="post"
                      class="form-horizontal">
            <div class="form-actions">
                <portofino:buttons list="crud-bulk-edit" />
            </div>          
            <mde:write name="actionBean" property="form"/>
            <stripes:hidden name="selection"/>
            <c:if test="${not empty actionBean.searchString}">
                <input type="hidden" name="searchString" value="<c:out value="${actionBean.searchString}"/>"/>
            </c:if>
            <input type="hidden" name="returnUrl" value="<c:out value="${actionBean.returnUrl}"/>"/>
            <div class="form-actions">
                <portofino:buttons list="crud-bulk-edit" />
            </div>
        </stripes:form>
    </stripes:layout-component>
</stripes:layout-render>