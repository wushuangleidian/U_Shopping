<%@ page language="java" import="java.util.*,cn.uaigou.entity.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	Object obj = session.getAttribute("vip");
	VIP vip = new VIP();
	if(obj!=null){
		vip = (VIP)obj;
	}
%>

<!DOCTYPE html>
<html>
	<head>
		<base href="<%=basePath%>">
		<meta charset="UTF-8">
		<title></title>
		<link rel="stylesheet" href="layui/css/layui.css" />
		<script type="text/javascript" src="layui/layui.js" ></script>
		<script type="text/javascript" src="js/jquery-3.3.1.min.js" ></script>
		<link rel="stylesheet" href="css/header.css" />
		<script type='text/javascript' src='dwr/engine.js'></script>
		<script type='text/javascript' src='dwr/interface/DwrPush.js'></script>
		<script type='text/javascript' src='dwr/util.js'></script>
		<script>
			var vipNo = <%=vip.getVipNo()%>;
		
			$(function(){
				//激活Dwr
				dwr.engine.setActiveReverseAjax(true);
			
				//当页面加载完成之后，判断用户是否已经登录
				$.ajax({
					type:'GET',
					url:'vip',
					dataType:'json',
					data:'m=isLogin',
					success:function(data){
						if(data.result==1){
							$("#my-login-btn").html("<a id='' href='javascript:;'>"+data.vipname+"</a><a href='vip?m=exit'>退出</a>");
						}
					}
				
				});
			
				//查询当前用户的购物车数量
				$.ajax({
					type:'GET',
					url:'shop',
					dataType:'json',
					data:'m=queryCount',
					success:function(data){
						if(data.result==1){
							var mess = "{\"result\":"+data.result+",\"count\":"+data.count+",\"vipno\":\""+data.vipno+"\"}";
							shopping(mess);
						}
					}
				});
			
				//下拉导航的显式与隐藏
				$("#xl").mouseenter(function(){
					$(".xldh").show();
				});
				$("#xl").mouseleave(function(){
					$(".xldh").hide();
				});
				$(".xldh").mouseenter(function(){
					$(".xldh").show();
				});
				$(".xldh").mouseleave(function(){
					$(this).hide();
				});
				
			});
			
			//动态显示购物车数量
			function shopping(data){
				var obj = JSON.parse(data);
				var result = obj.result;
				if(result==1){
					var vipno = obj.vipno;
					if(vipNo!=null && vipNo==vipno){
						$("#my-shopping-num").html(obj.count);
					}
				}
			}
			
			//点击购物车按钮
			function showShopping(vipno){
				location.assign("shop?m=show&vipno="+vipno);
			}
			
			//退出判断
			/* function exit(){
				var rel = confirm("确定要退出吗？");
				if(rel){
					location.href="vip?m=exit";
				}
			} */
		</script>
	</head>
	<body>
		
		<!-- 头部start -->
		<div class="my-header">
			<p id="my-login-btn" class="my-login">
				<input type="hidden" id="my-header-vipno" value="">
				<a href="vip?m=showLogin" target="_blank">请登录</a>
				<a href="jsp/reg.jsp" target="_blank">在线注册</a>
			</p>
			<p class="my-order">
				<a href="index.html" target="_parent">优爱购首页</a>
				<a href="jsp/userOrder.jsp" target="_parent">我的订单</a>
				<a href="jsp/userCenter.jsp" target="_parent">会员中心</a>
				<a href="jsp/admin_login.jsp" target="_blank">商家后台</a>
				<!--<a href="javascript:;" id="xl">站点导航</a>-->
			</p>
			<!--下拉导航-->
			<div class="xldh"></div>
			
		</div>
		
		<!-- 搜索部分start -->
		<div class="layui-container" style="height: 120px;">
			<div class="layui-row layui-col-space15">
				<!--显示logo-->
				<div class="layui-col-md2" style="">
					<div class="my-logo">
						<span>购</span>
					</div>
					<div class="my-logo-font">
						优爱购
					</div>
				</div>
				
				<!--搜索框-->
				<div class="layui-col-md8" style="">
					<form action="u" method="get">
						<input type="hidden" name="m" value="list">
						<input class="my-search" placeholder="搜索产品关键词" type="text" value="${kw}" name="kw" />
						<!--搜索按钮-->
						<button type="submit" class="my-search-btn" formtarget="_blank" style="border:0px;">
							<i class="layui-icon layui-icon-search"></i>
						</button>
					</form>
				</div>
				
				<div class="layui-col-md2" style="">
					<!--购物车-->
					<div class="my-shopping" onclick="showShopping('${sessionScope.vip.vipNo}')">
						<span class="layui-icon layui-icon-cart"></span>
						购物车
						<span class="layui-badge" id="my-shopping-num">0</span>
					</div>
					<!--购物车详情-->
					<div class="my-shopping-mess">
						
					</div>
					
				</div>
			</div>
		</div>
		<!-- 搜索部分end -->
		
		
	</body>
	
	<script>
		layui.use('element', function(){
  			var element = layui.element;
		});
	</script>
</html>

