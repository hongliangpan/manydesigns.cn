<%@ page contentType="application/pdf;charset=UTF-8"%>
<%@ page import="net.sf.jasperreports.engine.*"%>
<%@ page import="java.util.*"%>
<%@ page import="java.io.*"%>
<%@ page import="java.sql.*"%>
<%
 //报表编译之后生成的.jasper文件的存放位置
 File reportFile =new File(this.getServletContext().
getRealPath("/report/sample.jasper"));
 
 String url="jdbc:mysql://localhost:3306/db";
 Class.forName("com.mysql.jdbc.Driver");
 Map parameters =new HashMap();
 //"SQLSTR"是报表中定义的一个参数名称,其类型为String型
 parameters.put("SQLSTR",
 "select * from employee where employee_id like 'Z%'");
 Connection conn = DriverManager.getConnection(url,
"username","password");
 
 byte[] bytes=JasperRunManager.
runReportToPdf(reportFile.getPath(),parameters,conn);
 
 response.setContentType("application/pdf");
 response.setContentLength(bytes.length);
 
 ServletOutputStream outStream = response.getOutputStream();
 outStream.write(bytes,0,bytes.length);
 outStream.flush();
 outStream.close();
 out.clear();
 out = pageContext.pushBody();
%>