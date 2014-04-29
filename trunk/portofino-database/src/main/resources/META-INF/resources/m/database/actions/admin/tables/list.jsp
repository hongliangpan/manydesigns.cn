<%@ page import="com.manydesigns.portofino.model.database.Table" %>
<%@ page import="java.io.File" %>
<%@ page import="java.text.MessageFormat" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"
         pageEncoding="UTF-8"
%><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"
%><%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld"
%><%@ taglib prefix="mde" uri="/manydesigns-elements"
%><%@ taglib tagdir="/WEB-INF/tags" prefix="portofino"
%><%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<stripes:layout-render name="/m/base/admin-theme/admin-page.jsp">
    <jsp:useBean id="actionBean" scope="request" type="com.manydesigns.portofino.actions.admin.TablesAction"/>
    <stripes:layout-component name="pageTitle">
        Tables
    </stripes:layout-component>
    <stripes:layout-component name="pageBody">
        <stripes:url var="treetablePath"
                     value="/theme/jquery-treetable" />

        <script type="text/javascript" src="${treetablePath}/jquery.treetable.js" >
        </script>

        <!-- superpan add "initialState":"expanded",-->
        <script type="text/javascript">
            $(function() {
                $("#tables").treetable({"clickableNodeNames": true, "expandable":true,"initialState":"expanded", "treeColumn":0, "indent":20 });
                $("button[name=bulkDelete]").click(function() {
                    return confirm('<fmt:message key="are.you.sure" />');
                });
            });
        </script>

        <style type="text/css">
            table.treetable span.indenter {
              display: inline-block;
              margin: 0;
              padding: 0;
              text-align: right;
              user-select: none;
              -khtml-user-select: none;
              -moz-user-select: none;
              -o-user-select: none;
              -webkit-user-select: none;
              width: 19px;
            }

            table.treetable span.indenter a {
              background-position: left center;
              background-repeat: no-repeat;
              display: inline-block;
              text-decoration: none;
              width: 19px;
            }

            table.treetable {
              border: 1px solid #888;
              border-collapse: collapse;
              margin: .6em 0 1.8em 0;
              width: 100%;
            }

            table.treetable caption {
              font-weight: bold;
              margin-bottom: .2em;
            }

            table.treetable thead {
              background: #aaa url(${treetablePath}/images/bg-table-thead.png) repeat-x top left;
            }

            table.treetable thead tr th {
              border: 1px solid #888;
              font-weight: normal;
              padding: .3em 1em .1em 1em;
              text-align: left;
            }

            table.treetable tbody tr td {
              cursor: default;
              padding: .3em 1em;
            }

            table.treetable span {
              background-position: center left;
              background-repeat: no-repeat;
              padding: .2em 0 .2em 1.5em;
            }

            table.treetable span.file {
              background-image: url(${treetablePath}/images/file.png);
            }

            table.treetable span.folder {
              background-image: url(${treetablePath}/images/folder.png);
            }

            table.treetable tr.collapsed span.indenter a {
              background-image: url(${treetablePath}/images/expand.png);
            }

            table.treetable tr.expanded span.indenter a {
              background-image: url(${treetablePath}/images/collapse.png);
            }

            table.treetable tr.selected {
              background-color: #3875d7;
              color: #fff;
            }

            table.treetable tr.collapsed.selected span.indenter a {
              background-image: url(${treetablePath}/images/expand-light.png);
            }

            table.treetable tr.expanded.selected span.indenter a {
              background-image: url(${treetablePath}/images/collapse-light.png);
            }

            table.treetable tr.accept {
              background-color: #a3bce4;
              color: #fff
            }

            table.treetable tr.collapsed.accept td span.indenter a {
              background-image: url(${treetablePath}/images/expand-light.png);
            }

            table.treetable tr.expanded.accept td span.indenter a {
              background-image: url(${treetablePath}/images/collapse-light.png);
            }
        </style>
        <table id="tables" style="width: auto;">
            <tr>
                <th width="20%"><fmt:message key="database/schema" /></th>
                <th width="80%"><fmt:message key="table.entity" /></th>
            </tr>

            <%
                String lastDatabase = null;
                String lastSchema = null;
                List<Table> tables = actionBean.getAllTables();
                for(Table table : tables) {
                    if(table.getPrimaryKey() == null) {
                        continue;
                    }
                    if(table.getDatabaseName().equals(lastDatabase)) {
                        if(!table.getSchemaName().equals(lastSchema)) {
                            lastSchema = table.getSchemaName(); %>
                            <tr data-tt-id="<%= lastDatabase + "---" + lastSchema %>"
                                data-tt-parent-id="<%= lastDatabase %>">
                                <td colspan="2"><%= table.getSchemaName() %></td>
                            </tr><%
                        }
                    } else {
                        String changelogFileNameTemplate = "{0}-changelog.xml";
                        String changelogFileName =
                            MessageFormat.format(
                                changelogFileNameTemplate, table.getDatabaseName() + "-" + table.getSchemaName());
                        File changelogFile = new File(actionBean.getPersistence().getAppDbsDir(), changelogFileName);
                        String schemaDescr = table.getSchemaName();
                        if(changelogFile.isFile()) {
                            schemaDescr += " <img src='" + request.getContextPath() + "/m/database/actions/admin/tables/liquibase_logo_small.gif' /> Liquibase";
                        }

                        lastDatabase = table.getDatabaseName();
                        lastSchema = table.getSchemaName(); %>
                        <tr data-tt-id="<%= lastDatabase %>">
                            <td colspan="2"><%= table.getDatabaseName() %></td>
                        </tr>
                        <tr data-tt-id="<%= lastDatabase + "---" + lastSchema %>"
                            data-tt-parent-id="<%= lastDatabase %>">
                            <td colspan="2"><%= schemaDescr %></td>
                        </tr>
                        <%
                    }
                    String tableDescr = table.getTableName();
                    if(!table.getActualEntityName().equals(table.getTableName())) {
                        tableDescr += " (" + table.getActualEntityName() + ")";
                    }
                    %>
                    <tr data-tt-id="<%= lastDatabase + "---" + lastSchema + "---" + table.getTableName() %>"
                        data-tt-parent-id="<%= lastDatabase + "---" + lastSchema %>">
                        <td></td>
                        <td><a href="<%= lastDatabase %>/<%= lastSchema %>/<%= table.getTableName() %>"
                                ><%= tableDescr %></a></td>
                    </tr><%
                } %>
        </table>
        <stripes:form beanclass="com.manydesigns.portofino.actions.admin.TablesAction"
                      method="post">
            <div class="form-actions">
                <portofino:buttons list="tables-list" />
            </div>
        </stripes:form>
    </stripes:layout-component>
</stripes:layout-render>