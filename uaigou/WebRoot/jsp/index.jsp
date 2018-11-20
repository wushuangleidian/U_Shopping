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
		<title>优爱购-官网</title>
		<script type="text/javascript" src="js/jquery-3.3.1.min.js" ></script>
		<script type="text/javascript" src="layer/layer.js"></script>
		<script type="text/javascript" src="layui/layui.js" ></script>
		<link rel="stylesheet" href="layui/css/layui.css" />
		<link rel="stylesheet" href="css/index.css" />
		<!-- 设置网站图标 -->
		<!-- <link rel="icon" href="img/zt.jpg" type="image/x-icon"/> -->
		<script>
			//跳出框架
			if (window != top) {
			   top.location.href = location.href; 
			} 
			$(function(){
				//异步加载分类导航
				$.ajax({
					type:'GET',
					dataType:'json',
					url:'gclass',
					data:'m=queryAll',
					async:false,
					success:function(data){
						var obj = data;//全局json对象
						htmlStr = "<ul>";
						if(obj.result==1){
							var data = obj.data;//data元素，值是array类型
							for(var i=0;i<data.length;i++){
								var oneObj = data[i];//得到一级导航对象
								htmlStr += "<li onmouseenter='showTwo(this)' onmouseleave='hiddenTwo(this)' id='"+oneObj.gid+"'>"+
												oneObj.name+"<span class='layui-icon layui-icon-right'></span>"+
												"<div class='my-daohang2-2' style='top:0px;'>";
								
								//遍历二级导航
								var sonArr = oneObj.sonData;
								for(var j=0;j<sonArr.length;j++){
									var sonObj = sonArr[j];//二级导航对象
									var sonGid = sonObj.gid;
									htmlStr += "<a class='my-daohang2-sonObjTitle' href='u?m=list&twoGid="+sonObj.gid+"' target='_blank'>"+sonObj.name+"</a>";
									htmlStr += "<div class='my-daohang2-tagDiv' style='letter-spacing: 0px;'>";
									//遍历二级导航下的标签
									var tagArr = sonObj.tag;
									for(var k=0;k<tagArr.length;k++){
										var tag = tagArr[k];//标签对象
										if(tag.tj==1){
											htmlStr += "<a style='color:#ff5722;' class='my-daohang2-tagTitle' href='u?m=list&tid="+tag.id+"' target='_blank'>"+tag.tagName+"</a>";
										}else{
											htmlStr += "<a class='my-daohang2-tagTitle' href='u?m=list&tid="+tag.id+"' target='_blank'>"+tag.tagName+"</a>";
										}
									}
									
									htmlStr+="</div>";
								}
								
								htmlStr += "<div class='my-daohang2-rightDiv'>"+
												"<font class='my-daohang2-rightDivTitle'>热销商品</font>";
								var hotArr = oneObj.hotGoods;	
								for ( var m = 0; m < hotArr.length; m++) {
									var goods = hotArr[m];
									htmlStr += "<div id='' class='my-daohang2-goodsImg'><a href='u?m=goodsDetails&no="+goods.no+"' target='_blank'>"+
												"<img src='"+goods.img+"' width='100px' height='100px'>"+
												"<font class='my-daohang2-goodsTitle'>"+goods.title+"</font>"+
												"</a></div>";
								}			
												
												
								htmlStr +=	"</div>";
								
								htmlStr += "</li>";
							}
						}
						
						htmlStr += "</ul>";
						$(".my-daohang2").html(htmlStr);
					}
				});
				
				//设置二级导航右侧热销商品的标题显式
				$(".my-daohang2-goodsTitle").each(function(){
					var len = $(this).text().length;
					var maxwidth = 7;
					if(len>maxwidth){
						var newstr = $(this).text().substring(0,maxwidth)+"...";
						$(this).text(newstr);
					
					}
				});
			
				//获取用户登录信息
				$.ajax({
					type:'GET',
					url:'vip',
					dataType:'json',
					data:'m=isLogin',
					success:function(data){
						if(data.result==1){
							$("#my-username").text(data.vipname+",你好！");
							$("#my-index-login-btn").hide();
							$("#my-index-reg-btn").hide();
						}
					}
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
			
			//热销商品点击事件
			function showHot(no){
				/* location.href = 'u?m=goodsDetails&no='+no; */
				location.assign("u?m=goodsDetails&no="+no);
			}
			
			//展示二级导航
			function showTwo(obj){
				obj.getElementsByClassName("my-daohang2-2")[0].style.display="block";
			} 
			//隐藏二级导航
			function hiddenTwo(obj){
				obj.getElementsByClassName("my-daohang2-2")[0].style.display="none";
			}
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
						<!--全部商品-->
						<div class="layui-col-md3" style="height: 50px;background-color: #FFB800;z-index: 110;">
							<i class="layui-icon layui-icon-spread-left"></i>&nbsp;全部商品
						</div>
						<!--菜单-->
						<div class="layui-col-md9" style="height: 50px;">
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
			
			<!-- 导航轮播部分 -->
			<div class="layui-container" style="height: 430px;">
				<div class="layui-row">
					<!--纵向导航栏-->
					<div class="layui-col-md3">
						<div class="my-daohang2">
							<img alt="" src="img/loding.gif" width="100px" style="display:block;margin:40px auto;">
						</div>
						
					</div>
					
					
					<!--轮播-->
					<div class="layui-col-md6" style="left: 300px;top: 10px;">
						<div class="layui-carousel" id="test1">
						  <div carousel-item>
						    <div>
						    	<img src="img/01.png" width="600px" />
						    </div>
						    <div>
						    	<img src="img/02.jpg" width="600px" />
						    </div>
						    <div>
						    	<img src="img/03.jpg" width="600px" />
						    </div>
						    <div>
						    	<img src="img/04.jpg" width="600px" />
						    </div>
						    <div>
						    	<img src="img/05.jpg" width="600px" />
						    </div>
						  </div>
						</div>
						<div class="my-cximg" id="my-cximg01">
							<img src="img/05.jpg" width="275px" height="125px" />
						</div>
						<div class="my-cximg" id="my-cximg02">
							<img src="img/04.jpg" width="275px" height="125px" />
						</div>
					</div>
					
					<!--用户信息-->
					<div class="layui-col-md3" style="height: 415px;left: 310px;top: 10px;">
						<!--用户登录-->
						<div class="my-userlogin">
							<!--头像-->
							<p id="my-touxiangimg">
								<img src="img/avatar.jpg" />
							</p>
							<p id="my-username">
								Hi！你好
							</p>
							<p id="my-index-login-btn">
								<a href="vip?m=showLogin" style="width: 100px;" class="layui-btn layui-btn-radius layui-btn-primary">登录</a>
							</p>
							<p id="my-index-reg-btn">
								<a href="jsp/reg.jsp" style="width: 100px;" class="layui-btn layui-btn-radius layui-btn-warm">注册</a>
							</p>
						</div>
						<!--公告-->
						<div class="my-announcement">
							<span>公告</span>
							<ul>
								<li>
									<a href="">优爱购商城上线了</a>
								</li>
								<li>
									<a href="">优爱购商城上线了</a>
								</li>
								<li>
									<a href="">优爱购商城上线了</a>
								</li>
								<li>
									<a href="">优爱购商城上线了</a>
								</li>
							</ul>
						</div>
					</div>
				</div>
			</div>
			<!--导航轮播部分end-->
			
			
			<!--促销活动部分-->
			<div class="my-cuxiaodiv">
				
			</div>
			<!--促销活动部分end-->
			
			<!--首页商品展示部分-->
			<c:forEach var="isb" items="${isbList }" varStatus="s">
				<div class="layui-container my-goods" style="margin:10px auto;padding:0px;">
					<!--分类标题-->
					<div class="my-goods-title goods-title-${s.index }" >
						<span class="goods-title" style="width: 300px;text-indent: 10px;text-align: left;">${isb.gc.name }</span>
						<span class="more-goods">
							<a href="u?m=list&oneGid=${isb.gc.gid}" target="_blank">更多</a>
						</span>
					</div>
					<!--商品列表-->
					<div class="my-goods-list layui-row">
						
						<c:forEach var="ges" items="${isb.ges }">
							<c:if test="${not empty ges.id }">
								<div class="goods-mess layui-col-md4">
									<!--产品图片-->
									<div class="goods-img">
										<a href="u?m=goodsDetails&no=${ges.goodsNo }" target="_blank">
											<img src="${ges.firstImg }" width="345px" height="345px" />
										</a>
									</div>
									<!--产品价格-->
									<p class="goods-price">￥${ges.price }</p>
									<!--产品销量-->
									<p class="goods-sellnum">销量：${ges.salNum }笔</p>
									<!--产品标题-->
									<p class="goods-biaoti">
										<a href="u?m=goodsDetails&no=${ges.goodsNo }" target="_blank">
											${ges.title }
										</a>
									</p>
								</div>
							</c:if>
							<c:if test="${empty ges.id }">
								<div class="goods-mess layui-col-md4">
									<!--产品图片-->
									<div class="goods-img">
										<a href="javascript:;">
											<img src="img/02.jpg" />
										</a>
									</div>
									<!--产品价格-->
									<p class="goods-price">￥1399.99</p>
									<!--产品销量-->
									<p class="goods-sellnum">月销：1000笔</p>
									<!--产品标题-->
									<p class="goods-biaoti">
										<a href="javascript:;">
											秋季新款小白鞋子男帆布运动鞋韩版白色休闲鞋百搭潮流板鞋男鞋潮
										</a>
									</p>
								</div>
							</c:if>
						</c:forEach>
						
					</div>
				</div>
			</c:forEach>
			<!--首页商品展示部分end-->
			
			<!-- <div class="my-goods-title goods-title-02"> -->
			
			
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
		layui.use(['element','carousel'], function(){
  			var element = layui.element;
  			var carousel = layui.carousel;
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

