<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
	<head>
		<base href="<%=basePath %>">
		<meta charset="UTF-8">
		<title>优爱购 - 平台登录</title>
		<link rel="stylesheet" href="layui/css/layui.css" />
		<script type="text/javascript" src="js/jquery-3.3.1.min.js" ></script>
		<script type="text/javascript" src="layer/layer.js"></script>
		<script type="text/javascript" src="layui/layui.js" ></script>
		<link rel="stylesheet" href="css/header.css" />
		<link rel="stylesheet" href="css/login-reg.css" />
		<link rel="stylesheet" href="css/yzmStyle.css" />
		<script type="text/javascript" src="js/yzm.js" ></script>
		<script>
			$(".back-index").click(function(){
			});
			
			//登录验证
			function loginYz(){
				/**
				 *验证用户名
				 */
				//验证用户名不为空
				var name = $("#my-login-name").val();
				if(name.trim()==""){
					layer.tips('用户名不能为空', '#my-login-name', {
	  					tips: [3, 'red']
					});
					return false;
				}else if(!name.match("^([a-zA-Z]{3,})|([0-9a-zA-Z]+[@][a-zA-Z0-9]+[.](([c][o][m])|([c][n])|([c][o][m][.][c][n])|([o][r][g])))$")){//验证用户的格式
					layer.tips('用户名必须是大于3位的字符串', '#my-login-name', {
	  					tips: [3, 'red']
					});
					return false;
				}
				 
				 /**
				 *验证密码
				 */
				 var pwd = $("#my-login-pwd").val();
				//验证密码非空
				if(pwd.trim()==""){
					layer.tips('密码不能为空', '#my-login-pwd', {
	  					tips: [3, 'red']
					});
					return false;
				}
				//验证密码格式
				if(!pwd.match("^[0-9a-zA-Z]{6,16}$")){
					layer.tips('密码为6-16个字符', '#my-login-pwd', {
	  					tips: [3, 'red']
					});
					return false;
				}
			
				//验证滑块
				if(!hkrel){
					$("#login-result").text("验证无效，重新滑动验证！").show()
					return false;
				}
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
				<span>会员登陆</span>
				<form action="vip?m=login" onsubmit="return loginYz()" method="post">
					<p>
						<input id="my-login-name" class="login-inp" type="text" name="userName" value="${vipName}" placeholder="输入用户名/邮箱" />
					</p>
					<p>
						<input id="my-login-pwd" class="login-inp" type="password" name="password" value="${password}"  placeholder="输入密码"/>
					</p>
					<font id="yz-font" style="top:210px;">验证：</font>
					<div id="hkdiv" style="top: 200px;right: 50px;">
						<font>按住滑块，拖到最右侧</font>
						<div id="green"></div>
						<div id="kuai">>></div>
					</div>
					<p style="margin-top: 90px;margin-bottom: 5px;  line-height: 40px;">
					
						<c:choose>
							<c:when test="${isPwd==1 }">
								<input id="isPwd" checked="checked" type="checkbox" name="isPwd" style="cursor:pointer;display:block;float:left ;width: 20px;height: 20px;margin-top: 10px;">
							</c:when>
							<c:otherwise>
								<input id="isPwd" type="checkbox" name="isPwd" style="cursor:pointer;display:block;float:left ;width: 20px;height: 20px;margin-top: 10px;">
							</c:otherwise>
						</c:choose>
						
						<font style="font-size: 15px;display: block;line-height: 40px;text-indent: 10px;">记住密码</font>
					</p>
					<p style="margin-top:10px;margin-bottom: 5px;">
						<input class="login-submit" type="submit" value="登录" />
					</p> 
				</form>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				${mess }
				<!--会员注册、忘记密码-->
				<div id="login-result" style="font-size:14px;height: 15px;margin-top:-10px;"></div>
				<div class="my-reg">
					<a href="jsp/forgetPwd.jsp">忘记密码？</a>&nbsp;&nbsp;&nbsp;&nbsp;
					<a href="jsp/reg.jsp">去注册</a>
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

