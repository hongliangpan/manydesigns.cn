<%@ page contentType="text/html;charset=UTF-8" language="java"
         pageEncoding="UTF-8"
%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"
%><%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes-dynattr.tld"
%><%@ taglib prefix="mde" uri="/manydesigns-elements"
%><%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<stripes:layout-render name="/theme/templates/dialog/modal.jsp">
    <jsp:useBean id="actionBean" scope="request" type="com.manydesigns.portofino.actions.user.LoginAction"/>
    <stripes:layout-component name="pageTitle">
        忘记密码?
    </stripes:layout-component>
    <stripes:layout-component name="pageTitle">
        忘记密码?
    </stripes:layout-component>
    <stripes:layout-component name="pageBody">
	    通过邮箱重置密码
        <stripes:form action="${actionBean.context.actionPath}" method="post" class="spacingTop">
            <input type="text" name="email" id="email" class="input-block-level"
                   placeholder="<fmt:message key='email'/>" />
            <div class="login-buttons marginTop20px">
                <button type="submit" name="forgotPassword2" class="btn btn-primary">Next</button>
                <button type="submit" name="cancel" class="btn btn-link">Cancel</button>
            </div>
            <input type="hidden" name="returnUrl" value="<c:out value="${actionBean.returnUrl}"/>"/>
            <input type="hidden" name="cancelReturnUrl" value="<c:out value="${actionBean.cancelReturnUrl}"/>"/>
        </stripes:form>
        <script type="text/javascript">
            $('#email').focus();
        </script>
        <div>
            <hr />
            	如果您在几分钟内没有收到邮件，请先检查一下垃圾邮件。邮件发送地址是:
        </div>
    </stripes:layout-component>
</stripes:layout-render>