<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>支付宝扫码支付页面</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
		<link rel="stylesheet" href="layui/css/layui.css" />
		<link rel="stylesheet" href="css/shopping.css" />
		<link rel="stylesheet" href="css/buy.css" />
		<script type="text/javascript" src="js/jquery-3.3.1.min.js"></script>
		<script type="text/javascript" src="layer/layer.js"></script>
		<script type="text/javascript" src="layui/layui.js"></script>
	<style type="text/css">
		
	</style>
	<script type="text/javascript">
		/* layui.use('form', function() {
			var form = layui.form;
				
			//监听提交
			form.on('submit(formDemo)', function(data) {
				parent.layer.close(index);
			});
		}); */
		
		function successPay(){
			var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
			parent.layer.close(index);
		}
	</script>
  </head>
  
  <body>
    <div style="text-align: center;height: 300px;line-height: 300px;">
    	<img alt="" src="${fileName }">
    	<a href="javascript:successPay();" class="layui-btn layui-btn-normal" style="position: absolute;top:260px;left:100px;">已完成支付</a>
    </div>
  </body>
</html>
