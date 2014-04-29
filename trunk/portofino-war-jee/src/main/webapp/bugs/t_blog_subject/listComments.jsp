<%@ page import="java.util.List" %>
<%@ page import="com.manydesigns.elements.xml.XhtmlBuffer" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="javax.xml.crypto.Data" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" 
         uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="stripes" 
         uri="http://stripes.sourceforge.net/stripes-dynattr.tld"%>
<%@taglib prefix="mde" uri="/manydesigns-elements"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="portofino" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<stripes:layout-render name="/skins/${skin}/portlet.jsp">
    
    <jsp:useBean id="actionBean" scope="request" 
         type="com.manydesigns.portofino.pageactions.crud.CrudAction"/>
    
    <stripes:layout-component name="portletTitle">
        <c:out value="${actionBean.crudConfiguration.searchTitle}"/>
    </stripes:layout-component>

    <stripes:layout-component name="portletBody">
       <%
           SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
           XhtmlBuffer buffer = new XhtmlBuffer();
           for (HashMap object : (List<HashMap>)actionBean.objects){
               buffer.openElement("strong");
               buffer.write((String) object.get("author"));
               buffer.writeBr();
               buffer.write(df.format((Date)object.get("date")));
               buffer.closeElement("strong");
               buffer.writeParagraph((String)object.get("comment"));
               buffer.writeBr();
               buffer.writeHr();
           }
           out.println(buffer.toString());
       %>

            <h1>Add a new comment</h1>
        <form enctype="multipart/form-data" 
           action="<%= ((HashMap)actionBean
            .getOgnlContext().get("post")).get("id")%>/comment"
             method="post">
            <fieldset class="mde-form-fieldset mde-no-legend">
                <table class="mde-form-table">
                    <tbody>
                    <tr>
                        <th>* Comment</th>
                        <td><textarea id="comment" type="text" 
                              name="comment" class="mde-text-field"
                              maxlength="2147483647" cols="45" rows=5>
                            </textarea>
                        </td>
                    </tr>
                    <tr>
                        <th><label for="author" class="mde-field-label">
                            <span class="required">*</span>
                            Author:</label>
                        </th>
                        <td>
                            <input id="author" type="text" name="author" 
                              class="mde-text-field" 
                              maxlength="100" size="45">
                        </td>
                    </tr>
                    </tbody>
                </table>
            </fieldset>

            <input type="hidden" name="cancelReturnUrl" 
    value="/post/<%= ((HashMap)actionBean.getOgnlContext()
             .get("post")).get("id")%>"/>
            <input type="hidden" name="post" 
    value="<%= ((HashMap)actionBean.getOgnlContext()
             .get("post")).get("id")%>"/>
            <input type="submit" value="Add comment" name="save" 
    class="ui-button ui-widget ui-state-default ui-corner-all 
           portletButton ui-button-text-only" 
    role="button" aria-disabled="false"/>
        </form>
    </stripes:layout-component>
</stripes:layout-render>