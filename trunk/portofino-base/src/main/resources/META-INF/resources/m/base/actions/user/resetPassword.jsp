<%@ page contentType="text/html;charset=UTF-8" language="java"
         pageEncoding="UTF-8"
%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"
%><%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes-dynattr.tld"
%><%@ taglib prefix="mde" uri="/manydesigns-elements"
%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<stripes:layout-render name="/theme/templates/dialog/modal.jsp">
    <jsp:useBean id="actionBean" scope="request" type="com.manydesigns.portofino.actions.user.LoginAction"/>
    <stripes:layout-component name="pageTitle">
        	密码重置
    </stripes:layout-component>
    <stripes:layout-component name="pageTitle">
        	密码重置
    </stripes:layout-component>
    <stripes:layout-component name="pageBody">
        <stripes:form action="${actionBean.context.actionPath}" method="post">
            <input type="hidden" name="token" id="token" value="${actionBean.token}" />
            <input type="password" name="newPassword" id="newPassword" class="input-block-level"
                   placeholder="<fmt:message key='new.password'/>" />
            <input type="password" name="confirmNewPassword" id="confirmNewPassword" class="input-block-level"
                   placeholder="<fmt:message key='confirm.new.password'/>" />
            <div class="login-buttons spacingTop">
                <button type="submit" id="resetPassword2" name="resetPassword2" class="btn btn-primary">重置密码</button>
            </div>
            <input type="hidden" name="returnUrl" value="<c:out value="${actionBean.returnUrl}"/>"/>
            <input type="hidden" name="cancelReturnUrl" value="<c:out value="${actionBean.cancelReturnUrl}"/>"/>
        </stripes:form>
        <script type="text/javascript">
            $('#newPassword').focus();
        </script>
    </stripes:layout-component>
</stripes:layout-render>