<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>

	<head>
		<base href="<%=basePath%>">
		<meta charset="UTF-8">
		<title>结果页面</title>
		<link rel="stylesheet" href="layui/css/layui.css" />
		<link rel="stylesheet" href="css/shopping.css" />
		<link rel="stylesheet" href="css/buy.css" />
		<script type="text/javascript" src="js/jquery-3.3.1.min.js"></script>
		<script type="text/javascript" src="layer/layer.js"></script>
		<script type="text/javascript" src="layui/layui.js"></script>
		<style>
			.my-payrel a{
				color:#FF5722;
			}
			.my-payrel a:HOVER {
				color:red;
			}
		</style>
		<script>
			$(function() {
				

			});
		</script>

	</head>

	<body>

		<!-- 引入头部 -->
		<div>
			<jsp:include page="header.jsp"></jsp:include>
		</div>
		<!--引入头部end-->

		<hr style="height: 10px; background-color: #FF4400;" />

		<!-- 结果信息展示 -->
		<div class="layui-container">
			<div class="my-payrel" style="height: 100px;text-align: center;line-height: 100px;font-size: 18px">
				${mess }
			</div>
		</div>
		<!-- 结果信息展示end -->
		<!--引入底部-->
		<div class="my-footer" style="margin-top: 100px;">
			<jsp:include page="footer.jsp"></jsp:include>
		</div>
		<!--引入底部end-->
	</body>

</html>