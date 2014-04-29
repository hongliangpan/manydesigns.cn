<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="stripes" uri="http://stripes.sourceforge.net/stripes.tld" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset="UTF-8">
<title></title>
<script type="text/javascript" src="/theme/jquery/jquery.min.js"></script>
<script type="text/javascript">
   $(document).ready(function(){
        alert("f");        
        $("#subAjax").click(function(){
        name=$("#name").val();
        des=$("#des").val();
          $.ajax({
           type:"post",
           url:"${pageContext.request.contextPath}/actions/echarts/ajaxTest",
           data:"name="+name+"&describ="+des,
           success:function(result){                
                $('<p></p>')
               .html(result)
                .css('background', '#F0F0F0')
                .appendTo("body");                
           }
          })
        return true;
        })
       
   });
</script>
</head>
<body>
  <p>我要测试ajax</p>
  <stripes:form action="${pageContext.request.contextPath}/actions/echarts/ajaxTest">
  <table>
   <tr>
   <td>用户名:</td><td> <stripes:text name="name" id="name"></stripes:text></td>
   </tr>
   <tr>
   <td>说明:</td><td><stripes:text name="describ" id="des"/></td>
   </tr>
  </table> 
  <stripes:button name="subAjax" id="subAjax" value="提交"></stripes:button>  
  </stripes:form>
</body>
</html>