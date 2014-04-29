<%@ page contentType="text/html;charset=UTF-8"%>
<%@ page import="net.sf.jasperreports.engine.*"%>
<%@ page import="java.util.*"%>
<%@ page import="java.io.*"%>
<%@ page import="java.sql.*"%>
<%
 //报表编译之后生成的.jasper文件的存放位置
 File reportFile =new File(this.getServletContext().            
getRealPath("/report/sample.jasper"));
 
 Map parameters =new HashMap();
 //"SQLSTR"是报表中定义的参数名称,其类型为String型
 //设置SQLSTR参数的内容,根据需要赋值sql语句
 parameters.put("SQLSTR","select * from t_customer");   
 Class.forName("com.mysql.jdbc.Driver");
 Connection conn =DriverManager.getConnection(
			"jdbc:mysql://127.0.0.1:3306/itsboard200?useUnicode=true&amp;amp;characterEncoding=UTF-8", "riil",
	"r4rfde32wsaq1");
 JasperRunManager.runReportToHtmlFile(reportFile.getPath(),     
parameters,conn);
 response.sendRedirect("report/sample.html");                      
%>