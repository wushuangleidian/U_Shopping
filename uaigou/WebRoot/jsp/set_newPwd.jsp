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
		<title>设置新密码</title>
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
			
			//表单验证
			function yzForm(){
				var pwd1 = $("#pwd1").val();
				var pwd2 = $("#pwd2").val();
				//判断密码非空
				if(pwd1==null || pwd1.trim()==''){
					layer.tips('密码不能为空', '#pwd1', {
					  tips: [3,'red']
					});
					return false;
				}
				if(!pwd1.match("^[0-9a-zA-Z]{6,16}$")){
					layer.tips('密码为6-16个字符', '#pwd1', {
	  					tips: [3, 'red']
					});
					return false;
				}
				if(pwd2==null || pwd2.trim()==''){
					layer.tips('确认密码不能为空', '#pwd2', {
					  tips: [3,'red']
					});
					return false;
				}
				if(pwd2!=pwd1){
					layer.tips('确认密码不正确', '#pwd2', {
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
				<form action="vip?m=setPwd" onsubmit="return yzForm()" method="post">
					<p class="forget-inp">
						请输入密码：<input id="pwd1" type="password" name="password">
					</p>
					<p class="forget-inp">
						确认密码：<input id="pwd2" type="password" >
					</p>
					<p class="forget-inp">
						<button type="submit" class="layui-btn layui-btn-normal">提交</button>
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