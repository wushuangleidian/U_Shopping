<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>

	<head>
		<base href="<%=basePath%>">
		<meta charset="utf-8" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<title></title>
		<link rel="stylesheet" href="css/reset.css" />
		<link rel="stylesheet" href="css/login.css" />
		<script type="text/javascript" src="js/jquery-3.3.1.min.js"></script>
		<script type="text/javascript" src="js/login.js"></script>
		<script type="text/javascript">
			//跳出框架
			if (window != top) {
			　　top.location.href = location.href; 
			} 
		</script>
	</head>

	<body>
		<div class="page">
			<div class="loginwarrp">
				<div class="logo">优爱购商家后台登陆</div>
				<div class="login_form">
					<form id="Login" name="Login" method="post" onsubmit="" action="user">
						<input type="hidden" name="m" value='login'>
						<li class="login-item">
							<span>用户名：</span>
							<input type="text" id="username" name="userName" value="abc" class="login_input">
							<span id="count-msg" class="error"></span>
						</li>
						<li class="login-item">
							<span>密　码：</span>
							<input type="password" id="password" name="password" value="123" class="login_input">
							<span id="password-msg" class="error"></span>
						</li>
						<li style="margin:10px 0px;">${mess }</li>
						<li class="login-item verify">
							<span>验证码：</span>
							<input type="text" name="CheckCode" class="login_input verify_input">
							</li>
							<img src="codeimg?m=str&w=130&h=38&fs=28" border="0" class="verifyimg" onclick="changeImg(this)" title="点击换一张" />
							<div class="clearfix"></div>
							<li class="login-sub">
								<input type="submit" name="Submit" value="登录" />
								<input type="reset" name="Reset" value="重置" />
							</li>
					</form>
					
				</div>
			</div>
		</div>
		<script type="text/javascript">
			window.onload = function() {
				var config = {
					vx: 4,
					vy: 4,
					height: 2,
					width: 2,
					count: 100,
					color: "121, 162, 185",
					stroke: "100, 200, 180",
					dist: 6000,
					e_dist: 20000,
					max_conn: 10
				}
				CanvasParticle(config);
			}
		</script>
		<script type="text/javascript" src="js/canvas-particle.js"></script>
	</body>

</html>
