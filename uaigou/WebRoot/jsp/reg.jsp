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
		<title>优爱购 - 平台登录</title>
		<script type="text/javascript" src="js/jquery-3.3.1.min.js" ></script>
		<script type="text/javascript" src="layer/layer.js"></script>
		<script type="text/javascript" src="layui/layui.js" ></script>
		<link rel="stylesheet" href="layui/css/layui.css" />
		<link rel="stylesheet" href="css/header.css" />
		<link rel="stylesheet" href="css/login-reg.css" />
		<link rel="stylesheet" href="css/yzmStyle.css" />
		<script type="text/javascript" src="js/yzm.js" ></script>
		<script>
		
			//验证的变量
			var vipName=false;
			var email=false;
		
			$(function(){
				//用户名焦点事件
				$("#my-reg-username").focus(function(){
					layer.tips('用户名不小于3位', '#my-reg-username');
				});
				$("#my-reg-username").blur(function(){
					var val = $(this).val();
					$.ajax({
						type:'GET',
						url:'vip',
						dataType:'text',
						data:'m=yzName&name='+val,
						async:false,
						success:function(data){
							if(data=='1'){
								layer.tips('用户名已经存在了', '#my-reg-username', {
		  							tips: [3, 'red']
								});
								vipName=false;
							}else{
								vipName=true;
							}
						}
					});
					
				});
				
				//密码焦点事件
				$("#my-reg-pwd").focus(function(){
					layer.tips('密码为6-16个字符', '#my-reg-pwd');
				});
				
				//邮箱焦点事件
				$("#my-reg-email").focus(function(){
					layer.tips('请输入正确的邮箱地址', '#my-reg-email');
				});
				$("#my-reg-email").blur(function(){
					var val = $(this).val();
					$.ajax({
						type:'GET',
						url:'vip',
						dataType:'text',
						data:'m=yzEmail&email='+val,
						async:false,
						success:function(data){
							if(data=='1'){
								layer.tips('该邮箱已经存在了', '#my-reg-email', {
		  							tips: [3, 'red']
								});
								email=false;
							}else{
								email=true;
							}
						}
					});
					
				});
			});
		
		
		
			$(".back-index").click(function(){
			});
			
			//登录验证
			function regYz(){
			
				/**
				*验证用户名
				*/
				//验证用户名不为空
				var name = $("#my-reg-username").val();
				if(name.trim()==""){
					layer.tips('用户名不能为空', '#my-reg-username', {
	  					tips: [3, 'red']
					});
					return false;
				}else if(!name.match("^[a-zA-Z]{3,}$")){//验证用户的格式
					layer.tips('用户名必须是大于3位的字符串', '#my-reg-username', {
	  					tips: [3, 'red']
					});
					return false;
				}else{
					//验证重复
					var val = $("#my-reg-username").val();
					$.ajax({
						type:'GET',
						url:'vip',
						dataType:'text',
						data:'m=yzName&name='+val,
						async:false,
						success:function(data){
							if(data=='1'){
								layer.tips('用户名已经存在了', '#my-reg-username', {
		  							tips: [3, 'red']
								});
								vipName=false;
							}else{
								vipName=true;
							}
						}
					});
				}
				if(!vipName){
					layer.tips('用户名已经存在了', '#my-reg-username', {
	  					tips: [3, 'red']
					});
					return false;
				}
				
				/**
				 *验证密码
				 */
				var pwd = $("#my-reg-pwd").val();
				//验证密码非空
				if(pwd.trim()==""){
					layer.tips('密码不能为空', '#my-reg-pwd', {
	  					tips: [3, 'red']
					});
					return false;
				}
				//验证密码格式
				if(!pwd.match("^[0-9a-zA-Z]{6,16}$")){
					layer.tips('密码为6-16个字符', '#my-reg-pwd', {
	  					tips: [3, 'red']
					});
					return false;
				}
				
				
				/**
				 *验证邮箱
				 */
				 var mail = $("#my-reg-email").val();
				 //验证邮箱非空
				 if(mail.trim()==""){
				 	layer.tips('邮箱不能为空', '#my-reg-email', {
	  					tips: [3, 'red']
					});
					return false;
				 }else if(!mail.match("^[0-9a-zA-Z]+[@][a-zA-Z0-9]+[.](([c][o][m])|([c][n])|([c][o][m][.][c][n])|([o][r][g]))$")){
					//验证邮箱规则
					layer.tips('邮箱格式不正确！', '#my-reg-email', {
	  					tips: [3, 'red']
					});
					return false;
				}else{
					//验证重复
					var val = $("#my-reg-email").val();
					$.ajax({
						type:'GET',
						url:'vip',
						dataType:'text',
						data:'m=yzEmail&email='+val,
						async:false,//同步操作，可以为全局变量赋值
						success:function(data){
							if(data=='1'){
								layer.tips('该邮箱已经存在了', '#my-reg-email', {
		  							tips: [3, 'red']
								});
								email=false;
							}else{
								email=true;
							}
						}
					});
				}
				
				//验证邮箱
				if(!email){
					layer.tips('该邮箱已经存在了', '#my-reg-email', {
	  					tips: [3, 'red']
					});
					return false;
				}
				
				
				
				//滑块验证
				if(!hkrel){
					$("#login-result").text("验证无效，重新滑动验证！").show();
					return false;
				}
				
				return true;
			}
		</script>
	</head>
	<body>
		<!--显示logo-->
		<div class="layui-col-md2" style="left: 20%;">
			<div class="my-logo back-index">
				<span>购</span>
			</div>
			<div class="back-index my-logo-font" style="top:25px;left: 60px;">
				优爱购
			</div>
		</div>
		<!--显示logo end-->
		
		<div class="my-body">
		<!--登录-->
		<div class="my-logindiv">
			
			<img src="img/bg.png" width="100%" />
			<div class="my-login-inp">
				<span>会员注册</span>
				<form action="vip?m=reg" onsubmit="return regYz()" method="post">
					<p>
						<input id="my-reg-username" class="login-inp" type="text" name="vipName" placeholder="输入用户名" />
					</p>
					<p>
						<input id="my-reg-pwd" class="login-inp" type="password" name="password"  placeholder="输入密码"/>
					</p>
					<p>
						<input id="my-reg-email" class="login-inp" type="email" name="email"  placeholder="输入邮箱"/>
					</p>
					<font id="yz-font">验证：</font>
						<div id="hkdiv" style="top: 250px;right: 50px;">
							<font>按住滑块，拖到最右侧</font>
							<div id="green"></div>
							<div id="kuai">>></div>
						</div>
					<p style="margin-top: 90px;margin-bottom: 5px;">
						<input class="login-submit" type="submit" value="注册" style="background-color: #FF5722;" />
					</p> 
				</form>
				<!--会员注册、忘记密码-->
				<div id="login-result"></div>
				<div class="my-reg" style="width: 210px;">
					<a href="vip?m=showLogin">已有账号，现在去登录</a>
				</div>
			</div>
		</div>
		<!--登录end-->
		
		<!--引入底部-->
		<div class="my-footer" style="margin-top: 100px;">
			<jsp:include page="footer.jsp"></jsp:include>
		</div>
		<!--引入底部end-->
		</div>
	</body>
</html>

