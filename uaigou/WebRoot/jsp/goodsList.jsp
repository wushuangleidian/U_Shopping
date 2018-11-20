<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/pagebreak" prefix="page" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
	<head>
		<base href="<%=basePath%>">
		<meta charset="UTF-8">
		<title>产品列表</title>
		<link rel="stylesheet" href="layui/css/layui.css" />
		<script type="text/javascript" src="layui/layui.js" ></script>
		<script type="text/javascript" src="js/jquery-3.3.1.min.js" ></script>
		<link rel="stylesheet" href="css/index.css" />
		<link rel="stylesheet" href="css/fenye.css" />
		<style>
				/*二级导航*/
				.my-list-ejdh{
					border: 1px solid #E5E5E5;
					height: 120px;
					top: 20px;
				}
				/*二级导航下的分类*/
				.my-list-ejdh-a{
					display: block;
					float: left;
					/* width: 80px; */
					height: 25px;
					text-align: center;
					line-height: 25px;
					margin: 5px 15px;
				}
				.my-list-ejdh a:hover{
					color: #FF4400;
				}
				/*排序*/
				.my-list-paixu{
					border: 1px solid #E5E5E5;
					height: 50px;
					background-color: #fff;
					top:30px;
				}
				.my-list-paixu-a{
					display: block;
					width: 100px;
					height: 50px;
					text-align: center;
					line-height: 50px;
					font-size: 14px;
					float: left;
				}
				.my-list-paixu-a:hover{
					background-color: #FF4400;
					color: #fff;
				}
			</style>
		<script>
			$(function(){
				//纵向导航列表的二级导航的显示和隐藏
				$(".my-daohang2 li").mouseenter(function(){
					$(".my-daohang2-2").show();
				});
				$(".my-daohang2 li").mouseleave(function(){
					$(".my-daohang2-2").hide();
				});
				$(".my-daohang2-2").mouseenter(function(){
					$(this).show();
				});
				$(".my-daohang2-2").mouseleave(function(){
					$(this).hide();
				});
				
				//轮播下方促销图片的悬停事件
				$(".my-cximg").mouseenter(function(){
					$(this).css({"border-color":"#FF5722"});
				});
				$(".my-cximg").mouseleave(function(){
					$(this).css({"border-color":"#FFF"});
				});
				
				//回到顶部按钮
				$("#my-backtop").click(function(){
					$("html,body").animate({
						 scrollTop: 0
					},500);
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
		
		<div class="my-index-body">
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
			
			
			<!--促销活动部分-->
			<div class="my-cuxiaodiv">
				
			</div>
			<!--促销活动部分end-->
			
			<!-- 二级导航部分 -->
			<div class="my-list-ejdh layui-container">
				<p>
					<strong class="my-list-ejdh-a" style="display: block;height: 110px;">分类：</strong>
					<c:forEach var="tag" items="${tagList }" begin="0" end="34" >
						<a href="u?m=list&tid=${tag.id}" class="my-list-ejdh-a">${tag.tagName}</a>
					</c:forEach>
				</p>
			</div>
			<!--排序-->
			<div class="my-list-paixu layui-container">
				<!-- <a href="" class="my-list-paixu-a">按销量</a> -->
				<!-- <a href="" class="my-list-paixu-a">按评分</a> -->
				<a href="u?m=list&oneGid=${oneGid}&twoGid=${twoGid}&tid=${tid}&kw=${kw}&sx=asc" class="my-list-paixu-a">价格升序</a>
				<a href="u?m=list&oneGid=${oneGid}&twoGid=${twoGid}&tid=${tid}&kw=${kw}&sx=desc" class="my-list-paixu-a">价格降序</a>
			</div>
			<!--二级导航部分end-->
			
			<!--商品展示部分-->
			<div class="layui-container my-goods" style="border-radius: 0px;top: 40px;">
				<!--商品列表-->
				<div class="my-goods-list layui-row">
					<c:forEach var="goods" items="${pb.pageList }">
						<div class="goods-mess layui-col-md4">
							<!--产品图片-->
							<div class="goods-img">
								<a href="u?m=goodsDetails&no=${goods.goodsNo}" target="_blank">
									<img src="${goods.firstImg }" width="345px"/>
								</a>
							</div>
							<!--产品价格-->
							<p class="goods-price">￥${goods.price }</p>
							<!--产品销量-->
							<p class="goods-sellnum">销量：${goods.salNum }笔</p>
							<!--产品标题-->
							<p class="goods-biaoti">
								<a href="u?m=goodsDetails&no=${goods.goodsNo }" target="_blank">
									${goods.title }
								</a>
							</p>
						</div>
					</c:forEach>
				
				</div>
			</div>
			<!--商品展示部分end-->
			
			<!-- 分页 -->
			<div class="my-pagebrake" style="height: 80px;text-align: center;margin-top: 50px;padding-left:30%; ">
				<page:page url="u?m=list&oneGid=${oneGid}&twoGid=${twoGid}&tid=${tid}&kw=${kw}&dqy" totalPage="${pb.totalPage }" currentPage="${pb.currentPage }"/>
			</div>
			<!-- 分页end -->
			
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
		layui.use(['element','carousel','laypage'], function(){
  			var element = layui.element;
  			var carousel = layui.carousel;
  			var laypage = layui.laypage;
  
			  //执行一个laypage实例
			  laypage.render({
			    elem: 'test1'
			    ,count: 100
			    ,theme: '#FF5722'
			  });
  			
			  //建造实例
			  carousel.render({
			    elem: '#test1'
			    ,width: '100%' //设置容器宽度
			    ,arrow: 'always' //始终显示箭头
			    //,anim: 'updown' //切换动画方式
			  });
		});
	</script>
</html>

