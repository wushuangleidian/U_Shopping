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
		<title>会员中心-所有订单</title>
		<link rel="stylesheet" href="layui/css/layui.css" />
		<script type="text/javascript" src="layui/layui.js"></script>
		<script type="text/javascript" src="js/jquery-3.3.1.min.js"></script>
		<link rel="stylesheet" href="css/index.css" />
		<style>
			/*左侧菜单列表*/
			
			.my-usercenter-list {
			}
			
			.my-usercenter-list a {
				display: block;
				width: 60%;
				height: 30px;
				text-indent: 50px;
				line-height: 30px;
				font-size: 16px;
				margin: 10px 0px;
			}
			
			.my-usercenter-list a:hover {
				background-color: #FF4400;
				color: #fff;
			}
			/*菜单标题*/
			
			.list-title {
				width: 60%;
				height: 35px;
				font-size: 16px;
				font-weight: bold;
				line-height: 35px;
				margin: 20px 0px 5px;
				text-indent: 30px;
			}
		</style>
		<script>
			$(function() {

				//回到顶部按钮
				$("#my-backtop").click(function() {
					$("html,body").animate({
						scrollTop: 0
					}, 500);
				});

				//鼠标滚动事件，显式回到顶部按钮
			});
		</script>
	</head>

	<body>

		<!-- 引入头部 -->
		<div>
			<jsp:include page="header.jsp"></jsp:include>
		</div>
		<!--引入头部end-->

		<div class="my-index-body" style="background-color: #fff;">
			<!--横向导航栏-->
			<div class="my-daohang1">
				<div class="layui-container">
					<div class="layui-row">
						<!--菜单-->
						<div class="layui-col-md9" style="height: 50px;">
							<a href="">首页</a>
							<a href="">优抢购</a>
							<a href="">聚划算</a>
							<a href="">大转盘</a>
							<a href="">会员折扣</a>
							<a href="">买家秀</a>
						</div>
					</div>
				</div>
			</div>
			<!--横向导航end-->

			<!-- 内容主体  -->
			<div class="layui-container">
				<div class="layui-row">
					<!-- 左侧菜单 -->
					<div class="layui-col-md3 my-usercenter-list">
						<ul>
							<li class="list-title">会员资料</li>
							<li>
								<ul class="erji-list">
									<li>
										<a href="jsp/userCenter.jsp">个人信息</a>
									</li>
									<li>
										<a href="jsp/userAddress.jsp">收货地址</a>
									</li>
								</ul>
							</li>
							<li class="list-title">我的订单</li>
							<li>
								<ul class="erji-list">
									<li>
										<a href="jsp/userOrder.jsp">所有订单</a>
									</li>
									<li>
										<a href="jsp/userOrder.jsp">待付款订单</a>
									</li>
									<li>
										<a href="jsp/userOrder.jsp">待发货订单</a>
									</li>
									<li>
										<a href="jsp/userOrder.jsp">待收货订单</a>
									</li>
									<li>
										<a href="jsp/userOrder.jsp">已完成订单</a>
									</li>
									<li>
										<a href="jsp/userOrder.jsp">已关闭订单</a>
									</li>
								</ul>
							</li>
						</ul>
					</div>
					<!-- 左侧菜单end -->
					
					<!-- 右侧内容 -->
					<div class="layui-col-md9 my-usercenter-content">
						<fieldset class="layui-elem-field layui-field-title" style="margin-top: 50px;">
							<legend>所有订单</legend>
						</fieldset>
	
						<table class="layui-table my-shopping-table" lay-skin="line">
							<colgroup>
								<col width="">
								<col width="100">
								<col width="100">
								<col width="100">
								<col width="100">
								<col width="100">
								<col width="80">
							</colgroup>
							<thead>
								<tr>
									<th style="font-weight: bold;">
										商品信息
									</th>
									<th style="text-align: center;font-weight: bold;">
										单价
									</th>
									<th style="text-align: center;font-weight: bold;">
										数量
									</th>
									<th style="text-align: center;font-weight: bold;">
										合计
									</th>
									<th style="text-align: center;font-weight: bold;">
										订单状态
									</th>
									<th style="text-align: center;font-weight: bold;">
										付款日期
									</th>
									<th style="text-align: center;font-weight: bold;">
										操作
									</th>
								</tr>
							</thead>
							<tbody>
								<tr>
									<td>
										<div style="float: left;">
											<img src="img/zt.jpg" width="50px" /><br />
										</div>
										<p>
											Apple iPhone X (A1865) 全面屏手机 64GB 深空灰色
										</p>
									</td>
									<td align="center">
										<span>￥6999<span>
									</td>
									<td align="center">
										×1件
									</td>
									<td align="center">
										<span>￥6999<span>
									</td>
									<td align="center">
										已发货
									</td>
									<td align="center">
										2018-11-11&nbsp;12:12:12
									</td>
									<td align="center">
										<button class="layui-btn layui-btn-warm">详情</button>
									</td>
								</tr>
								
							</tbody>
						</table>
					</div>
					<!-- 右侧内容end -->
				</div>
			</div>
			<!-- 内容主体end -->

			<!--引入底部-->
		<div class="my-footer" style="margin-top: 100px;">
			<jsp:include page="footer.jsp"></jsp:include>
		</div>
		<!--引入底部end-->

			<!--回到顶部按钮-->
			<div id="my-backtop">
				<span class="layui-icon layui-icon-up"></span>
			</div>

		</div>

	</body>
	<script>
		layui.use(['element', 'carousel', 'laypage'], function() {
			var element = layui.element;
			var carousel = layui.carousel;
			var laypage = layui.laypage;

			//执行一个laypage实例
			laypage.render({
				elem: 'test1',
				count: 100,
				theme: '#FF5722'
			});

			//建造实例
			carousel.render({
				elem: '#test1',
				width: '100%' //设置容器宽度
					,
				arrow: 'always' //始终显示箭头
				//,anim: 'updown' //切换动画方式
			});
		});
	</script>

</html>
