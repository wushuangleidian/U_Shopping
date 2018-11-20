<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE html>
<html>
<head>
<base href="<%=basePath%>">
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
<title>优爱购后台管理系统</title>
<link rel="stylesheet" href="layui/css/layui.css">
<script type="text/javascript">
    //跳出框架
	if (window != top) {
	　　top.location.href = location.href; 
	}
</script>
</head>
<body class="layui-layout-body">
	<div class="layui-layout layui-layout-admin">
		<div class="layui-header">
			<div class="layui-logo my-title"
				style="color: #fff;font-size: 20px;font-weight: bold;">优爱购后台管理系统</div>
			<!-- 头部区域（可配合layui已有的水平导航） -->
			<ul class="layui-nav layui-layout-left">
				<li class="layui-nav-item"><a href="index.html" target="_blank">商城首页</a></li>
				<li class="layui-nav-item"><a href="javascript:;">会员管理</a>
					<dl class="layui-nav-child">
						<dd>
							<a href="admin/add_vip.html" target="content">添加新会员</a>
						</dd>
						<dd>
							<a href="admin/find_vip.html" target="content">会员查询</a>
						</dd>
						<dd>
							<a href="admin/update_vip.html" target="content">修改密码</a>
						</dd>
					</dl></li>
				<li class="layui-nav-item"><a href="javascript:;">站点管理</a>
					<dl class="layui-nav-child">
						<dd>
							<a href="admin/set_web.html" target="content">网站设置</a>
						</dd>
						<dd>
							<a href="admin/set_email.html" target="content">邮箱设置</a>
						</dd>
					</dl></li>
			</ul>
			<ul class="layui-nav layui-layout-right">
				<li class="layui-nav-item"><a href="javascript:;"> <img
						src="http://t.cn/RCzsdCq" class="layui-nav-img"> admin </a>
					<dl class="layui-nav-child">
						<dd>
							<a href="admin/admin_mess.html" target="content">基本资料</a>
						</dd>
						<dd>
							<a href="admin/admin_pwd.html" target="content">密码设置</a>
						</dd>
					</dl></li>
				<li class="layui-nav-item"><a href="admin/login.html"
					target="_parent">安全退出</a>
				</li>
			</ul>
		</div>

		<div class="layui-side layui-bg-black">
			<div class="layui-side-scroll">
				<!-- 左侧导航区域（可配合layui已有的垂直导航） -->
				<ul class="layui-nav layui-nav-tree" lay-filter="test">
					<li class="layui-nav-item layui-nav-itemed"><a class=""
						href="admin/tongji.html" target="content">控制台</a></li>
					<li class="layui-nav-item layui-nav-itemed"><a class=""
						href="javascript:;">订单管理</a>
						<dl class="layui-nav-child">
							<dd>
								<a href="admin/find_order.html" target="content">待付款订单</a>
							</dd>
							<dd>
								<a href="javascript:;" target="content">待发货订单</a>
							</dd>
							<dd>
								<a href="javascript:;" target="content">已发货订单</a>
							</dd>
							<dd>
								<a href="javascript:;" target="content">已完成订单</a>
							</dd>
						</dl></li>
					<li class="layui-nav-item"><a class="" href="javascript:;" target="content">品类管理</a>
						<dl class="layui-nav-child">
							<dd>
								<a href="admin/show_gclass.jsp" target="content">商品分类管理</a>
							</dd>
							<dd>
								<a href="tag?m=queryAll" target="content">标签管理</a>
							</dd>
						</dl></li>
					<li class="layui-nav-item"><a href="javascript:;">商品管理</a>
						<dl class="layui-nav-child">
							<dd>
								<a href="admin/add_goods_selGclass.jsp" target="content">发布商品</a>
							</dd>
							<dd>
								<a href="goods?m=queryAll" target="content">所有商品</a>
							</dd>
							<dd>
								<a href="goods?m=queryAll&status=1" target="content">在售商品</a>
							</dd>
							<dd>
								<a href="goods?m=queryAll&status=2" target="content">下架商品</a>
							</dd>
							<dd>
								<a href="goods?m=queryAll&tuijian=1" target="content">推荐商品</a>
							</dd>
						</dl></li>
					<li class="layui-nav-item"><a href="javascript:;">促销管理</a>
						<dl class="layui-nav-child">
							<dd>
								<a href="javascript:;">优抢购</a>
							</dd>
							<dd>
								<a href="javascript:;">聚划算</a>
							</dd>
							<dd>
								<a href="javascript:;">幸运大转盘</a>
							</dd>
							<dd>
								<a href="javascript:;">会员折扣</a>
							</dd>
							<dd>
								<a href="javascript:;">优惠券</a>
							</dd>
							<dd>
								<a href="javascript:;">秒杀活动</a>
							</dd>
							<dd>
								<a href="javascript:;">买家秀</a>
							</dd>
						</dl></li>
					<li class="layui-nav-item"><a href="javascript:;">公告管理</a>
						<dl class="layui-nav-child">
							<dd>
								<a href="javascript:;">添加公告</a>
							</dd>
							<dd>
								<a href="javascript:;">公告查询</a>
							</dd>
						</dl></li>
				</ul>
			</div>
		</div>

		<div class="layui-body">
			<!-- 内容主体区域 -->
			<div style="padding: 15px;">
				<iframe name="content" src="admin/tongji.html"
					style="border:0px;width: 96%;height:92%;position: absolute;"></iframe>
			</div>
		</div>

		<div class="layui-footer">
			<!-- 底部固定区域 -->
			© uaigou.cn&nbsp;版权所有
		</div>
	</div>
	<script src="layui/layui.js"></script>
	<script>
		//JavaScript代码区域
		layui.use('element', function() {
			var element = layui.element;

		});
	</script>
</body>
</html>
