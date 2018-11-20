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
		<title>忘记密码</title>
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
			/* 忘记密码表单 */
			.forget-inp{
				text-align: left;
				height: 50px;
				line-height: 50px;
				text-indent: 300px;
				margin: 10px auto;
			}
			.forget-inp input{
				height: 30px;
			}
		</style>
		<script>
			$(function() {

			});
			
			//更换验证码图片
			function changeImg(){
				var codeImg = document.getElementById("codeImg");
				codeImg.src = "codeimg?m=math&w=100&h=40&fs=22&d="+new Date().getTime();
			}
			
			//设定验证码的默认值
			var code = false;
			
			//表单验证
			function yzForm(){
				var code = $("#codeInput");
				
				//非空验证
				if(code==null || code.trim()==''){
					layer.tips('验证码不能为空', '#codeInput', {
					  tips: [3,'red']
					});
					return false;
				}
				
				return true;
			}
			
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
				<form action="yz?m=code" onsubmit="return yzForm()" method="post">
					<input type="hidden" name="f" value="forgetPwd2">
					<p class="forget-inp">
						<font color="green">友情提示：</font>验证码已发送，请去邮箱获取！
					</p>
					<p class="forget-inp">
						请输入验证码：<input id="codeInput" style="width: 80px;" type="text" name="code">
					</p>
					<p class="forget-inp">
						<button type="submit" class="layui-btn layui-btn-normal">验证</button>
					</p>
					<p class="forget-inp">
						${mess }
					</p>
				</form>
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