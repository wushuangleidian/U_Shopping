<%@ page language="java" import="java.util.*,cn.uaigou.entity.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'zz.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript" src="js/jquery-3.3.1.min.js" ></script>
	<script>
	</script>
  </head>
  
  <body>
  
  	<%
  		int[] arr = {1,2,3,4,5,6,7,8,9};
  		request.setAttribute("list", arr);
  	 %>
  	 
  	 <c:forEach var="i" items="${list }" begin="0" end="14" varStatus="s">
  	 	${i }-->${s.index }<br>
  	 </c:forEach>
  	
  </body>
</html>
