<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
	<head>
		<base href="<%=basePath %>">
		<meta charset="UTF-8">
		<title>公共底部</title>
		<link rel="stylesheet" href="layui/css/layui.css" />
		<script type="text/javascript" src="layui/layui.js" ></script>
		<style>
			/*公共底部div*/
			.my-footdiv{
				border-top:1px solid #DDDDDD;
				padding-top: 30px;
				padding-bottom: 30px;
				background-color: #F8F8F8;
				height: 220px;
				margin-top: 30px;
			}
			/*信息列表*/
			.my-messdiv{
				border-bottom: 1px solid #E6E6E6;
				padding-bottom: 20px;
			}
			.my-mess-list span{
				display: block;
				width: 100%;
				height: 30px;
				font-size: 16px;
				font-weight: bold;
				text-align: center;
				line-height: 30px;
			}
			.my-mess-list li{
				display: block;
				height: 28px;
				width: 100%;
				text-align: center;
				line-height: 30px;
			}
			.my-mess-list a:hover{
				color: #FF4400;
			}
			/*推广二维码*/
			.my-tuiguang-wx{
				text-align: center;
				color: #666666;
				font-size: 16px;
				font-weight: bold;
			}
			.my-wximg{
				margin: 10px auto 0px;
				width: 100px;
				height: 100px;
			}
			/*底部备案*/
			.my-beian p{
				text-align: center;
				height: 25px;
				line-height: 25px;
				font-size: 12px;
				margin-top: 10px;
				color: #666685;
			}
		</style>
	</head>
	<body>
		
		<!--公共底部-->
		<div class="my-footdiv" >
			<!--信息列表-->
			<div class="layui-container my-messdiv">
				<div class="layui-row">
					<div class="layui-col-md2 my-mess-list">
						<span>买家帮助</span>
						<ul>
							<li>
								<a href="">新手指南</a>
							</li>
							<li>
								<a href="">服务保障</a>
							</li>
							<li>
								<a href="">常见问题</a>
							</li>
							<li>
								<a href="">在线帮助</a>
							</li>
						</ul>
					</div>
					<div class="layui-col-md2 my-mess-list">
						<span>关于我们</span>
						<ul>
							<li>
								<a href="">平台简介</a>
							</li>
							<li>
								<a href="">联系我们</a>
							</li>
							<li>
								<a href="">招贤纳士</a>
							</li>
							<li>
								<a href="">平台入驻</a>
							</li>
						</ul>
					</div>
					<div class="layui-col-md2 my-mess-list">
						<span>关注我们</span>
						<ul>
							<li>
								<a href="">新浪微博</a>
							</li>
							<li>
								<a href="">腾讯微博</a>
							</li>
							<li>
								<a href="">QQ空间</a>
							</li>
						</ul>
					</div>
					<div class="layui-col-md6">
						<div class="layui-row">
							<div class="layui-col-md6 my-tuiguang-wx">
								优爱购微信公众号
								<div class="my-wximg">
									<img src="img/code.png" width="100px" height="100px" />
								</div>
							</div>
							<div class="layui-col-md6 my-tuiguang-wx">
								优爱购客户端下载
								<div class="my-wximg">
									<img src="img/code.png" width="100px" height="100px" />
								</div>
							</div>
						</div>
						
					</div>
				</div>
				
			</div>
			<!--信息列表end-->
			
			<!--底部备案-->
			<div class="my-beian">
				<p>
					Copyright&nbsp;©2018 &nbsp; uaigou.cn &nbsp; 电信与信息服务业务经营许可证100798号 经营性网站备案信息 
				</p>
				<p>
					京ICP备11031139号 &nbsp; 京公网安备110108006045  &nbsp; 客服电话：400-8158-666  &nbsp;文明办网文明上网举报电话：010-82615762 &nbsp; 违法不良信息举报中心
				</p>
			</div>
			
		</div>
		
	</body>
</html>

