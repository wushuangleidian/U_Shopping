<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>

<head>
<base href="<%=basePath%>">
<meta charset="UTF-8">
<title>付款</title>
<link rel="stylesheet" href="layui/css/layui.css" />
<link rel="stylesheet" href="css/shopping.css" />
<link rel="stylesheet" href="css/buy.css" />
<script type="text/javascript" src="js/jquery-3.3.1.min.js"></script>
<script type="text/javascript" src="layer/layer.js"></script>
<script type="text/javascript" src="layui/layui.js"></script>
<style>
/*购买商品信息*/
.my-pay-goodsMess { /* height: 100px; */
	
}

.my-pay-goodsMess p {
	height: 50px;
	line-height: 50px;
	text-indent: 50px;
}
/*付款方式图标*/
.paytype-image {
	display: block;
	float: left;
	width: 180px;
	height: 55px;
	margin: 20px 20px;
	cursor: pointer;
	border: 2px solid #fff;
	opacity: 0.6;
}

.payimg-sel {
	border: 2px solid #FF4400;
	opacity: 1;
}

.paytype-image:hover {
	opacity: 1;
}
</style>
<script>
			$(function() {
				//选择付款方式
				$(".paytype-image").click(function() {
					$(this).addClass("payimg-sel");
					$(this).siblings().removeClass("payimg-sel");
				});

				//点击付款按钮
				$("#buyBtn").click(function() {
					var orderNo = $("#orderNo").val();
					var tm = $("#my-totalMoney").text();
					var title = $("#title").val();
					var goodsNum = $("#goodsNum").val();
					//location.href = 'alipay2?orderNo='+orderNo+'&tm='+tm+'&title='+title;
					
					//判断支付方式
					var zhifuType = $(".payimg-sel").attr("alt");
					if(zhifuType=='支付宝'){
						layer.open({
						  type: 2,
						  area: ['320px', '380px'],
						  fixed: true, //不固定
						  maxmin: false,
						  title: '打开支付宝扫码支付',
						  anim:1,
						  scrollbar:false,//禁止滚动
						  shadeClose:false,
						  content: 'zhifu?m=alipay&orderNo='+orderNo+'&tm='+tm+'&title='+title+'&goodsNum='+goodsNum,
						  end:function(){
						  	//校验支付状态
						  	$.ajax({
								type:'GET',
								url:'zhifu',
								dataType:'text',
								data:'m=checkpay&orderNo='+orderNo,
								async:false,
								success:function(data){
									if(data==2){
										//已付款
										location.href = "jsp/userOrder.jsp";
									}else if(data==1){
										layer.msg("支付失败！");
									}
								}
							});
						  }
						});	
					}else if(zhifuType=='微信'){
						layer.msg("暂不支持微信支付！");
						return;
					}else{
						if(Number(tm)>=0.1){
							layer.confirm('当前为测试接口，请选择网银测试商品？', {
							  btn: ['继续支付','重选商品'] //按钮
							}, function(){
							  var urlStr = 'zhifu?m=yeepay&orderNo='+orderNo+'&tm='+tm+'&title='+title+'&FrpId='+zhifuType;
							  open(urlStr);
							  layer.confirm('是否已完成付款？', {
								btn: ['已付款', '遇到问题'] //按钮
							  }, function() {
									//已完成付款
									$.ajax({
										type:'GET',
										url:'zhifu',
										dataType:'text',
										data:'m=checkpay&orderNo='+orderNo,
										async:false,
										success:function(data){
											if(data==2){
												//已付款
												location.href = "jsp/userOrder.jsp";
											}else if(data==1){
												layer.msg("支付失败！");
											}
										}
									});
							  }, function() {
									//未完成付款
								   layer.msg('请重新选择支付方式！');
							  });
							  
							}, function(){
							  
							});
						}else{
							 var urlStr = 'zhifu?m=yeepay&orderNo='+orderNo+'&tm='+tm+'&title='+title+'&FrpId='+zhifuType;
							 open(urlStr);
							 layer.confirm('是否已完成付款？', {
								btn: ['已付款', '遇到问题'] //按钮
							  }, function() {
									//已完成付款
									$.ajax({
										type:'GET',
										url:'zhifu',
										dataType:'text',
										data:'m=checkpay&orderNo='+orderNo,
										async:false,
										success:function(data){
											if(data==2){
												//已付款
												location.href = "jsp/userOrder.jsp";
											}else if(data==1){
												layer.msg("支付失败！");
											}
										}
									});
							  }, function() {
									//未完成付款
								    layer.msg("请重新选择支付方式！");
							  });
						}
					}
					
				});

			});
		</script>

</head>

<body>

	<!-- 引入头部 -->
	<div>
		<jsp:include page="header.jsp"></jsp:include>
	</div>
	<!--引入头部end-->

	<hr style="height: 10px; background-color: #FF4400;" />

	<!--商品信息部分-->
	<div class="layui-container">
		<fieldset class="layui-elem-field layui-field-title"
			style="margin-top: 50px;">
			<legend>商品信息</legend>
		</fieldset>
		<div class="layui-row">
			<div class="layui-col-md12 my-pay-goodsMess">
				&nbsp;&nbsp;&nbsp;&nbsp;购买商品：
				<c:forEach var="s" items="${shopList }">
					<c:if test="${s.status==1 }">
						<input type="hidden" id="title" value="${s.title}">
						<input type="hidden" id="orderNo" value="${orderNo }">
						<input type="hidden" id="goodsNum" value="${s.goodsNum }">
						<p>
							<img src="${s.firstImg}" width="40px" /> ${s.title }&nbsp;&nbsp;
							${s.guige }&nbsp;&nbsp; ￥${s.price }元×${s.goodsNum }件&nbsp;&nbsp;
							总价：￥
							<fmt:formatNumber type="number" value="${s.price*s.goodsNum }"
								pattern="0.00" maxFractionDigits="2" />
							<br>
						</p>
					</c:if>
				</c:forEach>
				<c:if test="${not empty goods.title }">
					<input type="hidden" id="title" value="${goods.title}">
					<input type="hidden" id="orderNo" value="${orderNo }">
					<input type="hidden" id="goodsNum" value="${goodsNum }">
					<p>
						<img src="${goods.firstImg}" width="40px" /> ${goods.title
						}&nbsp;&nbsp; ${guige }&nbsp;&nbsp; ￥${goods.price }元×${goodsNum
						}件&nbsp;&nbsp; 总价：￥
						<fmt:formatNumber type="number" value="${tm }" pattern="0.00"
							maxFractionDigits="2" />
						<br>
					</p>
				</c:if>
				<p>收件人：${adr.name}&nbsp;&nbsp; ${adr.phone }&nbsp;&nbsp;
					${adr.sheng }&nbsp;&nbsp;${adr.shi }&nbsp;&nbsp;${adr.qu
					}&nbsp;&nbsp; ${adr.adr }</p>
			</div>
		</div>

	</div>
	<!--商品信息end-->

	<!--付款方式-->
	<div class="layui-container" style="top:-30px;">
		<div class="layui-row">
			<div class="layui-col-md12">
				<fieldset class="layui-elem-field layui-field-title"
					style="margin-top: 50px;">
					<legend>付款方式</legend>
				</fieldset>
				<img class="paytype-image payimg-sel" src="img/pay/pay_zfb.png"
					alt="支付宝" /> <img class="paytype-image" src="img/pay/pay_wx.png"
					alt="微信" /> <img class="paytype-image" src="img/pay/pay_jsyh.png"
					alt="CCB-NET-B2C" /> <img class="paytype-image"
					src="img/pay/pay_gsyh.png" alt="ICBC-NET-B2C" /> <img
					class="paytype-image" src="img/pay/pay_nyyh.png" alt="ABC-NET-B2C" />
				<img class="paytype-image" src="img/pay/pay_jtyh.png"
					alt="BOCO-NET-B2C" /> <img class="paytype-image"
					src="img/pay/pay_zgyh.png" alt="BOC-NET-B2C" /> <img
					class="paytype-image" src="img/pay/pay_zsyh.png"
					alt="CMBCHINA-NET-B2C" /> <img class="paytype-image"
					src="img/pay/pay_zxyh.png" alt="ECITIC-NET-B2C" /> <img
					class="paytype-image" src="img/pay/pay_gdyh.png" alt="CEB-NET-B2C" />

			</div>
		</div>
	</div>
	<!--付款方式end-->

	<!--支付部分-->
	<div class="layui-container" style="top: -20px;">
		<div class="layui-row  my-shopping-buy">
			<div class="layui-col-md12">
				<div class="my-buyCheck"></div>
				<div class="my-buyBtn">
					总价： ￥<font id="my-totalMoney"
						style="font-size: 20px;font-weight: bold;color:#FF4400;">${tm
						}</font>
					<button id="buyBtn" class="my-shopping-buyBtn">支付</button>
				</div>
			</div>

		</div>
	</div>
	<!--支付部分end-->
	<!--引入底部-->
	<div class="my-footer" style="margin-top: 100px;">
		<jsp:include page="footer.jsp"></jsp:include>
	</div>
	<!--引入底部end-->
</body>

</html>
