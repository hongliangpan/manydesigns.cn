<%@ taglib prefix="mde" uri="/manydesigns-elements" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="actionBean" scope="request"
             type="com.manydesigns.portofino.actions.admin.page.PageAdminAction"/>
<div class="dialog-copy-page modal hide">
    <div class="modal-header">
        <button name="closeCopyPageButton" type="button" class="close" aria-hidden="true">&times;</button>
        <h4><fmt:message key="copy.to"/></h4>
    </div>
    <div class="modal-body form-horizontal">
        <input type="hidden" name="copyPage" value="action" />
        <mde:write name="actionBean" property="copyForm"/>
    </div>
    <div class="modal-footer">
        <button name="cancelCopyPageButton" type="button" class="btn">
            <fmt:message key="cancel" />
        </button>
        <button name="confirmCopyPageButton" type="button" class="btn btn-primary">
            <fmt:message key="copy" />
        </button>
    </div>
</div>