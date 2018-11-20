<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
	<head>
		<base href="<%=basePath%>">
		<meta charset="UTF-8">
		<title>单个商品结算</title>
		<link rel="stylesheet" href="layui/css/layui.css" />
		<link rel="stylesheet" href="css/shopping.css" />
		<link rel="stylesheet" href="css/buy.css" />
		<script type="text/javascript" src="js/jquery-3.3.1.min.js"></script>
		<script type="text/javascript" src="layer/layer.js" ></script>
		<script type="text/javascript" src="layui/layui.js" ></script>
		<script>
			
			$(function(){
				
				//地址栏鼠标悬停事件，显示编辑和删除按钮
				$(".my-showAddress").mouseenter(function(){
					$(this).children(".my-address-edit").show();
				});
				$(".my-showAddress").mouseleave(function(){
					$(this).children(".my-address-edit").hide();
				});
				//地址栏点击事件
				$(".my-showAddress").click(function(){
					$(this).addClass("sel");
					$(this).siblings().removeClass("sel");
				});
  				
			});
			
			//添加收货地址
			function addAdr(){
				layer.open({
					  type: 2,
					  area: ['700px', '450px'],
					  fixed: true, //不固定
					  maxmin: false,
					  title: '新增收货地址',
					  anim:1,
					  /*scrollbar:false,*///禁止滚动
					  shadeClose:true,
					  content: 'jsp/shouhuo.jsp',
					  end:function(){
					  	location.reload();
					  }
				});	
			
			}
			
			//修改收货地址
			function updateAdr(adrid){
				layer.open({
					  type: 2,
					  area: ['700px', '450px'],
					  fixed: true, //不固定
					  maxmin: false,
					  title: '新增收货地址',
					  anim:1,
					  /*scrollbar:false,*///禁止滚动
					  shadeClose:true,
					  content: 'adr?m=updateBefore&adrid='+adrid,
					  end:function(){
					  	location.reload();
					  }
				});	
			}
			
			//删除地址
			function delAdr(adrid){
				//询问框
				layer.confirm('确定要删除吗？', {
				  btn: ['删除','取消'] //按钮
				}, function(){
				  $.ajax({
				  	type:'GET',
				  	url:'adr',
				  	dataType:'text',
				  	data:'m=del&adrid='+adrid,
				  	success:function(data){
				  		if(data==1){
				  			location.reload();
				  		}
				  	}
				  });
				}, function(){
				});
			}
			
			//点击付款按钮
			function myPay(){
				var adrid = $(".sel").attr("id");//收货地址的ID
				if(typeof(adrid)=='undefined'){
					layer.msg("请先选择收货地址！");
					return;
				}
				
				var guige = $("#guige").val();
				var goodsNum = $("#goodsNum").val();
				var urlStr = "order?m=toPay&f=buyone&adrid="+adrid+"&guige="+guige+"&goodsNum="+goodsNum;
				
				var goodsNo = $("#goodsNo").val();
				urlStr += "&goodsNo="+goodsNo;
				
				var tm = $("#my-totalMoney").text();//所有商品总价
				urlStr += "&tm="+tm;
				
				//跳转
				location.href = urlStr;
				
			}
			
		</script>
		
	</head>
	<body>
		<div id="bg"></div>
		<div id="addAdrInp">
			<h2>添加收件人</h2>
			<p>收件人：<input type="text" id="name" /></p>
			<p>电话：<input type="text" id="phone" /></p>
			<p>地址：<input type="text" id="adr" /></p>
			<p><button id="buy-subBtn">添加</button></p>
		</div>
		
		<!-- 引入头部 -->
		<div>
			<jsp:include page="header.jsp"></jsp:include>
		</div>
		<!--引入头部end-->
		
		<hr style="height: 10px; background-color: #FF4400;" />
		
		<!--收货地址部分-->
		<div class="layui-container">
			<fieldset class="layui-elem-field layui-field-title" style="margin-top: 50px;">
				<legend>收货地址</legend>
			</fieldset>
			<div class="layui-row">
				<div class="layui-col-md12" style="padding:20px;">
					<!-- 添加收货地址按钮 -->
					<a id="addAddress" class="layui-btn layui-btn-normal" href="javascript:addAdr();">
						<i class="layui-icon layui-icon-add-1"></i>
						新增收货地址
					</a><br />
					<!-- 显式收货地址,被选中的收货地址div的class中添加'sel'值 -->
					<c:forEach var="a" items="${adrList}"> 
						<div class="my-showAddress" id="${a.adrid }" >
							<p style="border-bottom:1px solid gainsboro;">
								${a.name }（收）&nbsp;&nbsp;${a.phone }
							</p>
							<p>
								${a.sheng }&nbsp;${a.shi }&nbsp;${a.qu }
							</p>
							<p>
								${a.adr }
							</p>
							<p class="my-address-edit">
								<a href="javascript:updateAdr(${a.adrid});">编辑</a>
								<a href="javascript:delAdr(${a.adrid });">删除</a>
							</p>
						</div>
					</c:forEach> 
					
				</div>
			</div>
			
		</div>
		<!--收货地址end-->
		
		<!--商品清单-->
		<div class="layui-container" style="top:-30px;">
			<div class="layui-row">
				<div class="layui-col-md12">
					<fieldset class="layui-elem-field layui-field-title" style="margin-top: 50px;">
						<legend>商品清单</legend>
					</fieldset>

					<table class="layui-table my-shopping-table" lay-skin="line">
						<colgroup>
							<col width="160">
							<col width="">
							<col width="100">
							<col width="100">
							<col width="100">
						</colgroup>
						<thead>
							<tr>
								<th style="font-weight: bold;">
									
								</th>
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
							</tr>
						</thead>
						<tbody>
								<input type="hidden" id="goodsNo" value="${goods.goodsNo }">
								<input type="hidden" id="guige" value="${guige }">
								<input type="hidden" id="goodsNum" value="${goodsNum }">
								<tr>
									<td>
										<a href="u?m=goodsDetails&no=${goods.goodsNo}" target="_blank">
											<img src="${goods.firstImg }" width="80px" />
										</a>
									</td>
									<td>
										<a href="u?m=goodsDetails&no=${goods.goodsNo}" target="_blank">
											${goods.title }&nbsp;&nbsp;${guige }
										</a>
									</td>
									<td align="center">
										<span>￥${goods.price }<span>
									</td>
									<td align="center">
										×${goodsNum }件
									</td>
									<td align="center">
										￥<fmt:formatNumber type="number" value="${goods.price*goodsNum }" pattern="0.00" maxFractionDigits="2"/>
									</td>
								</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
		<!--商品清单end-->
		
		<!--付款部分-->
		<div class="layui-container" style="top: -20px;">
			<div class="layui-row  my-shopping-buy">
				<div class="layui-col-md12">
					<div class="my-buyCheck">
					</div>
					<div class="my-buyBtn">
						总价：
						￥<font id="my-totalMoney" style="font-size: 20px;font-weight: bold;color:#FF4400;"><fmt:formatNumber type="number" value="${goods.price*goodsNum }" pattern="0.00" maxFractionDigits="2"/></font>
						<button id="buyBtn" class="my-shopping-buyBtn" onclick="myPay()">去付款</button>
					</div>
				</div>
				
			</div>
		</div>
		<!--付款部分end-->
		<!--引入底部-->
		<div class="my-footer" style="margin-top: 100px;">
			<jsp:include page="footer.jsp"></jsp:include>
		</div>
		<!--引入底部end-->
	</body>
</html>

