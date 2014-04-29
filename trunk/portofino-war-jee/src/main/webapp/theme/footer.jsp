<%@ page import="com.manydesigns.portofino.modules.ModuleRegistry" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld"%>
<jsp:useBean id="portofinoConfiguration" scope="application"
             type="org.apache.commons.configuration.Configuration"/>
<jsp:useBean id="stopWatch" scope="request"
             type="org.apache.commons.lang.time.StopWatch"/>
<footer>
    <div class="container">
        <div class="pull-right" style="padding-right:12px;">
            <fmt:message key="page.response.time"/>: <c:out value="${stopWatch.time}"/> ms.
        </div>
        &nbsp;&nbsp;&nbsp;&nbsp;
        Powered by <a href="http://www.ruijie.com.cn/">Ruijie - ITS Dept. ver20131225</a>
    </div>
</footer>