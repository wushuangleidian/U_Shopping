<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
<meta charset="UTF-8">
<title>${goods.title}-商品详情-优爱购</title>
<meta name="description" content="${goods.title }">
<script type="text/javascript" src="js/jquery-3.3.1.min.js"></script>
<script type="text/javascript" src="layer/layer.js"></script>
<script type="text/javascript" src="layui/layui.js"></script>
<link rel="stylesheet" type="text/css" href="layui/css/layui.css" />
<link rel="stylesheet" href="css/index.css" />
<link rel="stylesheet" href="css/goodsMess.css" />
<script type='text/javascript' src='dwr/engine.js'></script>
<script type='text/javascript' src='dwr/interface/DwrPush.js'></script>
<script type='text/javascript' src='dwr/util.js'></script>
<script>
	$(function() {
		dwr.engine.setActiveReverseAjax(true);

		//回到顶部按钮
		$("#my-backtop").click(function() {
			$("html,body").animate({
				scrollTop : 0
			}, 500);
		});

		/*主图切换*/
		$(".my-goodsMess-images").children("img").mouseenter(function() {
			var src = $(this).attr("src");
			$("#firstImg").attr("src", src);
			$(this).css({
				"border-color" : "red"
			});
		});
		$(".my-goodsMess-images").children("img").mouseleave(function() {
			$(this).css({
				"border-color" : "#eee"
			});
		});
		/*选择商品规格*/
		$(".my-goodsMess-guige").click(function() {
			$(this).siblings().removeClass("my-goodsMess-guige-sel");
			$(this).addClass("my-goodsMess-guige-sel");
		});
		/*购买商品数量*/
		$("#subGoodsNum").click(function() {
			var val = $("#goodsNum").val();
			var stock = $("#goodsKucun").text();
			//减法
			if(val>1){
				val--;
				$("#goodsNum").val(val);
				if(val<=stock){
					$("#addShoppingBtn").removeClass("layui-btn-disabled");
					$("#addShoppingBtn").removeAttr("disabled");
					$("#payBtn").removeClass("layui-btn-disabled");
					$("#payBtn").removeAttr("disabled");
				}
			}
			
		});
		$("#addGoodsNum").click(function() {
			var val = $("#goodsNum").val();
			var stock = $("#goodsKucun").text();
			val++;
			$("#goodsNum").val(val);
			if (val > stock) {
				$("#addShoppingBtn").addClass("layui-btn-disabled");
				$("#payBtn").addClass("layui-btn-disabled");
				$("#addShoppingBtn").attr("disabled", "disabled");
				$("#payBtn").attr("disabled", "disabled");

				layer.tips('您所填写的宝贝数量超过库存！', '#goodsNum', {
					tips : [ 3, '#ff5722' ]
				});

			} else {
				$("#addShoppingBtn").removeClass("layui-btn-disabled");
				$("#addShoppingBtn").removeAttr("disabled");
				$("#payBtn").removeClass("layui-btn-disabled");
				$("#payBtn").removeAttr("disabled");
			}
		});
	}

	);

	//数量输入事件
	function numInput(obj) {
		var val = obj.value;
		if (isNaN(val) || (!isNaN(val) && val < 1)) {
			obj.value = '1';
			val = 1;
		}
		var stock = $("#goodsKucun").text();
		if (val > stock) {
			$("#addShoppingBtn").addClass("layui-btn-disabled");
			$("#payBtn").addClass("layui-btn-disabled");
			$("#addShoppingBtn").attr("disabled", "disabled");
			$("#payBtn").attr("disabled", "disabled");

			layer.tips('您所填写的宝贝数量超过库存！', '#goodsNum', {
				tips : [ 3, '#ff5722' ]
			});

		} else {
			$("#addShoppingBtn").removeClass("layui-btn-disabled");
			$("#addShoppingBtn").removeAttr("disabled");
			$("#payBtn").removeClass("layui-btn-disabled");
			$("#payBtn").removeAttr("disabled");
		}

	}

	//添加购物车事件
	function addShopping(goodsNo) {
		var goodsNum = $("#goodsNum").val();
		var guige = $(".my-goodsMess-guige-sel").text();
		if (guige == null || guige == '') {
			layer.msg('请选择规格！');
			return;
		}
		$
				.ajax({
					type : 'GET',
					url : 'shop',
					dataType : 'json',
					data : 'm=add&goodsNo=' + goodsNo + '&goodsNum=' + goodsNum
							+ '&guige=' + guige,
					async : false,
					success : function(data) {
						if (data.result == 0) {
							//未登录
							location.assign("jsp/login.jsp");
						} else if (data.result == 1) {
							var mess = "{\"result\":" + data.result
									+ ",\"count\":" + data.count
									+ ",\"vipno\":\"" + data.vipno + "\"}";
							DwrPush.shopping(mess);
							layer.msg('成功加入购物车！');
						} else if (data.result == 2) {
							//用户未激活
							location.assign("vip?m=jihuo");
						}
					}
				});
	}
	
	//立即购买商品按钮点击事件
	function buyGoods(id){
		var guige = $(".my-goodsMess-guige-sel").text();
		if (guige == null || guige == '') {
			layer.msg('请选择规格！');
			return;
		}
		
		var goodsNum = $("#goodsNum").val();
		
		var url = "order?m=buy&f=goods&id="+id+"&guige="+guige+"&goodsNum="+goodsNum;
		
		location.href=url;
	}
</script>
</head>
<body>

	<!-- 引入头部 -->
	<div>
		<jsp:include page="header.jsp"></jsp:include>
	</div>
	<!--引入头部end-->

	<!--横向导航栏-->
	<div class="my-daohang1">
		<div class="layui-container">
			<div class="layui-row">
				<!--菜单-->
				<div class="layui-col-md9" style="height: 50px;">
					<a href="">首页</a> <a href="">优抢购</a> <a href="">聚划算</a> <a href="">大转盘</a>
					<a href="">会员折扣</a> <a href="">买家秀</a>
				</div>
			</div>
		</div>
	</div>
	<!--横向导航end-->

	<!--商品信息-->
	<div class="layui-container" style="top:30px;bottom: 30px;">
		<div class="layui-row">
			<div class="layui-col-md6">
				<div class="my-goodsMess-firstImg">
					<img id="firstImg" src="${goods.firstImg }" />
				</div>
				<div class="my-goodsMess-images">
					<img src="${goods.firstImg }" />
					<c:forEach var="img" items="${images }">
						<img src="${img }" />
					</c:forEach>
				</div>
			</div>
			<div class="layui-col-md6">
				<div class="my-goodsmessage" style="padding-top:0px;">
					<p class="my-goodsMess-title">
						<!-- 商品标题 -->
						${goods.title }
						<!-- <img src="images/shangpinxiangqing/X11.png" /> -->
					</p>
					<p class="my-goodsMess-subhead">
						<!-- 副标题 -->
						${goods.subHead }
					</p>
				</div>
				<div class="my-goodsmessage my-goodsMess-cuxiao">
					<p>
						售价 <span class="my-goodsMess-price">￥<b>${goods.price }</b>
						</span>
					</p>
					<p>
						促销<span class="my-goodsMess-cxmess">${goods.cuxiao }</span>
					</p>
					<p>
						服务<span class="my-goodsMess-fuwu">30天无忧退货&nbsp;&nbsp;48小时快速退款&nbsp;&nbsp;满88元免邮</span>
					</p>
				</div>
				<div class="my-goodsmessage">
					规格 &nbsp;&nbsp;&nbsp;&nbsp;
					<c:forEach var="g" items="${guige }">
						<span class="my-goodsMess-guige">${g }</span>
					</c:forEach>
					<!-- <span class="my-goodsMess-guige my-goodsMess-guige-sel">红色</span> -->
				</div>
				<div class="my-goodsmessage">
					数量 &nbsp;&nbsp;&nbsp;&nbsp;
					<button id="subGoodsNum" class="my-goodsMess-numbtn">-</button>
					<input type="text" name="num" value="1" id="goodsNum"
						oninput="numInput(this)" />
					<button id="addGoodsNum" class="my-goodsMess-numbtn">+</button>
					&nbsp;&nbsp; 库存：<span id="goodsKucun" class="my-goodsMess-kucun">${goods.stock
						}</span>
				</div>
				<div class="my-goodsmessage">
					<button id="payBtn" onclick="buyGoods(${goods.id})" class="layui-btn layui-btn-normal">立即购买</button>
					&nbsp;&nbsp;
					<button id="addShoppingBtn" class="layui-btn layui-btn-danger"
						onclick="addShopping('${goods.goodsNo}')">
						<i class="layui-icon layui-icon-cart"></i> 加入购物车
					</button>
				</div>
				<div class="my-goodsmessage">
					<div style="float: left;height: 35px;line-height: 35px;">分享：</div>
					<div class="bdsharebuttonbox">
						<a href="#" class="bds_more" data-cmd="more"></a><a href="#"
							class="bds_qzone" data-cmd="qzone" title="分享到QQ空间"></a><a
							href="#" class="bds_weixin" data-cmd="weixin" title="分享到微信"></a><a
							href="#" class="bds_tsina" data-cmd="tsina" title="分享到新浪微博"></a><a
							href="#" class="bds_tqq" data-cmd="tqq" title="分享到腾讯微博"></a>
					</div>
					<script>
						window._bd_share_config = {
							"common" : {
								"bdSnsKey" : {},
								"bdText" : "",
								"bdMini" : "2",
								"bdMiniList" : false,
								"bdPic" : "",
								"bdStyle" : "1",
								"bdSize" : "24"
							},
							"share" : {}
						};
						with (document)
							0[(getElementsByTagName('head')[0] || body)
									.appendChild(createElement('script')).src = 'http://bdimg.share.baidu.com/static/api/js/share.js?v=89860593.js?cdnversion='
									+ ~(-new Date() / 36e5)];
					</script>
				</div>
			</div>
		</div>
	</div>
	<!--商品信息end-->

	<!-- 产品详情 -->
	<div class="layui-container" style="top:100px;">
		<fieldset class="layui-elem-field layui-field-title"
			style="margin-top: 20px;">
			<legend>产品详情</legend>
		</fieldset>
		<div class="layui-row">
			<div class="layui-col-md3" style="border: 1px solid #E5E5E5;">
				<p class="my-goodsMess-kefu">在线客服：</p>
				<a href='tencent://message/?uin=445098355&Site=SamBow&Menu=yes'>
					<img id="qqkefu" style="cursor: pointer"
					src="http://wpa.qq.com/pa?p=1:445098355:1" alt="在线客服"> </a>
			</div>
			<div class="layui-col-md9"
				style="padding-left: 50px;font-size: 16px;overflow: hidden;">
				${goods.details }</div>
		</div>
	</div>
	<!-- 产品详情end -->

	<!--引入底部-->
	<div class="my-footer" style="margin-top: 150px;">
		<jsp:include page="footer.jsp"></jsp:include>
	</div>
	<!--引入底部end-->

	<!--回到顶部按钮-->
	<div id="my-backtop">
		<span class="layui-icon layui-icon-up"></span>
	</div>

</body>
</html>
