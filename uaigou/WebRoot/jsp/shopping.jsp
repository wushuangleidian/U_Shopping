<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
		<title>购物车</title>
		<link rel="stylesheet" href="layui/css/layui.css" />
		<link rel="stylesheet" href="css/shopping.css" />
		<script type="text/javascript" src="js/jquery-3.3.1.min.js"></script>
		<script type="text/javascript" src="layer/layer.js"></script>
		<script type="text/javascript" src="layui/layui.js"></script>
		
		<script>
			$(function() {

			});

			//数量输入事件
			function inputNum(obj,price,sid) {
				var tp = $("#my-totalPrice"+sid).text();
				var stock = $("#my-stock"+sid).val();//要显式的库存数据
				var checkBox = document.getElementById("my-checkBox"+sid);//当前商品的复选框对象
				var totalMoney = $("#my-totalMoney").text();//所有商品总价
				var ranMoney = Number(totalMoney)-Number(tp);//临时所有商品总价
				var price = $("#my-price"+sid).text();//当前商品单价
				var val = obj.value;//当前输入框的数值
				if(isNaN(val)) {//当输入的值为非数字
					val = 1;
					obj.value=val;
				}
				if(!isNaN(val) && val < 1) {//当输入的值小于1时
					val = 1;
					obj.value=1;
				}
				
				
				tp = val*price;//计算当前商品购买总价
				$("#my-totalPrice"+sid).text(tp.toFixed(2));
				
				//当输入的购买商品数量超出库存是，提示
				if(Number(val)>Number(stock)){//当输入的购买数量大于库存时
					if(checkBox.checked){
						$("#my-totalMoney").text(ranMoney.toFixed(2));//当超出库存时，所有总价-之前的该商品购买总价
					}
					$("#my-checkBox"+sid).attr("disabled", "disabled");//设置复选框不可选
					document.getElementById("my-checkBox"+sid).checked=false;//取消复选框勾选
					$("#my-stockMess"+sid).show();//显式库存不足提示
				}else{//当输入的购买数量小于等于库存时
					$("#my-checkBox"+sid).removeAttr("disabled");//删除复选框不可选
					$("#my-stockMess"+sid).hide();//隐藏库存不足提示
					if(checkBox.checked){//当前商品被选中
						ranMoney = Number(ranMoney)+Number(tp);//重新计算所有商品总价
						$("#my-totalMoney").text(ranMoney.toFixed(2));
					}
				}
				
				updateNum(sid,val);//将购买数量存入数据库
				
			}
			
			//加法按钮事件
			function sumNum(obj,price,sid){
				var stock = $("#my-stock"+sid).val();//库存
				var input = obj.previousSibling;//数量输入框对象
				var checkBox = document.getElementById("my-checkBox"+sid);//当前商品的复选框对象
				var totalMoney = $("#my-totalMoney").text();//所有商品总价
				var price = $("#my-price"+sid).text();//当前商品单价
				var num = input.value;//输入框的数值
				num++;
				input.value=num;
				var tp = $("#my-totalPrice"+sid).text();
				tp = num*price;//计算该商品的总价
				$("#my-totalPrice"+sid).text(tp.toFixed(2));
				
				if (Number(num) > Number(stock)) {//当购买数量大于库存时
					if(checkBox.checked){//当商品被选中时，修改所有商品的总价
						var m = Number(totalMoney)-Number(price)*Number(stock);
						$("#my-totalMoney").text(m.toFixed(2));
					}
					$("#my-checkBox"+sid).attr("disabled", "disabled");//设置为不可选
					document.getElementById("my-checkBox"+sid).checked=false;//取消勾选
					$("#my-stockMess"+sid).show();
					
				} else {//购买数量小于等于库存时
					$("#my-checkBox"+sid).removeAttr("disabled");
					$("#my-stockMess"+sid).hide();
					if(checkBox.checked){//当商品被选中时，修改所有商品的总价
						var m = Number(totalMoney)+Number(price);
						$("#my-totalMoney").text(m.toFixed(2));
					}
				}
				updateNum(sid,num);//修改数据库的购买数量
			}
			//减法按钮事件
			function subNum(obj,price,sid){
				var stock = $("#my-stock"+sid).val();//库存
				var price = $("#my-price"+sid).text();//商品单价
				var totalMoney = $("#my-totalMoney").text();//所有商品总价
				//var checkBox = $("#my-checkBox"+sid);
				var checkBox = document.getElementById("my-checkBox"+sid);//当前商品的复选框
				var input = obj.nextSibling;//输入框
				var num = input.value;//输入框的值
				if(Number(num)>1){
					num--;
					input.value=num;
					var tp = $("#my-totalPrice"+sid).text();
					tp = num*price;//计算该商品购买总价
					$("#my-totalPrice"+sid).text(tp.toFixed(2));
					if(Number(num)<=Number(stock)){//当小于等于库存时，消除不可选状态
						$("#my-checkBox"+sid).removeAttr("disabled");
						$("#my-stockMess"+sid).hide();
					}
					updateNum(sid,num);//修改数据库中的购买数量
					if(checkBox.checked){//当商品被选中时，修改所有商品的总价
						var m = Number(totalMoney)-Number(price);
						$("#my-totalMoney").text(m.toFixed(2));
					}
				}
			}
			
			//修改购物车商品数量的方法
			function updateNum(sid,num){
				$.ajax({
					type:'GET',
					url:'shop',
					data:'m=update&sid='+sid+'&goodsNum='+num,
					async:false
				});
			}
			
			//全选按钮事件
			function allCheck(obj){
				var tm=0;//总价
				var cb = document.getElementsByClassName("checkBox");//商品复习框对象数组
				if(obj.checked){//当全选按钮被选中时
					for(var i=0;i<cb.length;i++){//遍历所有商品复选框
						var checkBox = cb[i];//复选框对象
						if(checkBox.disabled){//当复选框为不可选状态时
							checkBox.checked = false;//取消复选框勾选
						}else{
							checkBox.checked = true;//勾选复选框
							//计算当前被选商品的购买总价
							var sid = checkBox.value;//获取当前复选框的值
							var totalPrice = $("#my-totalPrice"+sid).text();//获取当前商品总价
							tm+=Number(totalPrice);
						}
					}
				}else{
					for(var i=0;i<cb.length;i++){
						cb[i].checked = false;
					}
				}
				$("#my-totalMoney").text(tm.toFixed(2));
			}
			
			//单个商品点击事件
			function clickCheckBox(obj){
				var sid = obj.value;//当前商品的sid
				var totalPrice = $("#my-totalPrice"+sid).text();//当前商品的购买总价
				var totalMoney = $("#my-totalMoney").text();//所有商品的购买总价
				if(obj.checked){
					//选中该商品
					var m = Number(totalMoney)+Number(totalPrice);
					 $("#my-totalMoney").text(m.toFixed(2));
				}else{
					 //选中该商品
					 var m = Number(totalMoney)-Number(totalPrice);
					 $("#my-totalMoney").text(m.toFixed(2));
				}
			}
			
			//删除单个商品
			function delOne(sid){
				layer.confirm('确定要删除商品吗？', {
				  btn: ['删除','取消'] //按钮
				}, function(){
				  $.ajax({
				  	type:'GET',
				  	url:'shop',
				  	dataType:'text',
				  	data:'m=delOne&sid='+sid,
				  	async:false,
				  	success:function(data){
				  		if(data==1){
				  			layer.msg('删除成功！');
				  			location.reload();
				  		}else if(data==0){
				  			layer.msg('删除失败！');
				  		}
				  	}
				  });
				}, function(){
				  
				});
			}
			
			//批量删除提交验证
			function delYz(){
				var isDel = false;
				layer.confirm('确定要删除这些商品吗？', {
				  btn: ['删除','取消'] //按钮
				}, function(){
					isDel=true;
				}, function(){
				});
				
				return isDel;
			}
			
			//批量删除选中的商品
			function delSelGoods(){
				var ids = document.getElementsByClassName("my-checkBox-sel");
				if(ids.length<=0){
					return;
				}
				var delurl = "";
				var k=0;
				for(var i=0;i<ids.length;i++){
					var cb = ids[i];//复选框对象
					if(cb.checked){
						//当被选中时，获取value
						var sid = cb.value;
						delurl+="&ids="+sid;
						k++;
					}
				}
				
				if(k==0){
					layer.msg("请先选择要删除的商品！");
					return;
				}
				
				layer.confirm('确定要删除这些商品吗？', {
				  btn: ['删除','取消'] //按钮
				}, function(){
				  $.ajax({
				  	type:'GET',
				  	url:'shop',
				  	dataType:'text',
				  	data:'m=batchDel'+delurl,
				  	async:false,
				  	success:function(data){
				  		if(data==1){
				  			layer.msg('删除成功！');
				  			location.reload();
				  		}else if(data==0){
				  			layer.msg('删除失败！');
				  		}
				  	}
				  });
				}, function(){
				  
				}); 
				
			}
			
			//结算商品
			function jiesuan(){
				var cbs = document.getElementsByClassName("my-checkBox-sel");//获取所有可选的复选框对象数组
				var urlStr = "order?m=buy&f=shop";
				var k=0;
				for(var i=0;i<cbs.length;i++){
					var checkBox = cbs[i];
					if(checkBox.checked){//当被选中时
						var sid = checkBox.value;//得到商品的sid
						urlStr += "&sid="+sid;
						k++;
					}
				}
				
				if(k==0){
					layer.msg("请先选择商品！");
					return;
				}
				var totalMoney = $("#my-totalMoney").text();//所有商品总价
				urlStr += "&tm="+totalMoney;
				
				location.assign(urlStr);
				
			}
			
		</script>
	</head>

	<body>
		<!-- 引入头部 -->
		<div>
			<jsp:include page="header.jsp"></jsp:include>
		</div>
		<!--引入头部end-->
		
		<hr style="height: 10px; background-color: #FF4400;" />
		
		<!--购物车订单-->
		<div class="layui-container" style="top:-30px;">
			<div class="layui-row">
				<div class="layui-col-md12">
					<fieldset class="layui-elem-field layui-field-title" style="margin-top: 50px;">
						<legend>购物车</legend>
					</fieldset>

					<table class="layui-table my-shopping-table" lay-skin="line">
						<colgroup>
							<col width="160">
							<col width="">
							<col width="100">
							<col width="100">
							<col width="150">
							<col width="100">
							<col width="80">
						</colgroup>
						<thead>
							<tr>
								<th style="font-weight: bold;">
									<input class="checkBox" type="checkbox" onclick="allCheck(this)" /> 全选
								</th>
								<th style="font-weight: bold;">
									商品信息
								</th>
								<th style="text-align: center;font-weight: bold;">
									库存
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
									操作
								</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="s" items="${shoppingList }">
							<c:if test="${s.status==0 }">
							<tr>
								<td>
									<c:if test="${s.goodsNum>s.stock }">
										<input class="checkBox" id="my-checkBox${s.sid}" name="ids" value="${s.sid}" disabled="disabled" type="checkbox" />
									</c:if>
									<c:if test="${s.goodsNum<=s.stock }">
										<input class="checkBox my-checkBox-sel" id="my-checkBox${s.sid}" name="ids" value="${s.sid}" type="checkbox" onclick="clickCheckBox(this)"/>
									</c:if>
									<a href="u?m=goodsDetails&no=${s.goodsNo}" target="_blank">
										<img src="${s.firstImg }" width="80px" />
									</a>
								</td>
								<td>
									<a href="u?m=goodsDetails&no=${s.goodsNo}" target="_blank">
										${s.title }&nbsp;&nbsp;${s.guige }
									</a>
								</td>
								<td align="center">
									<input type="hidden" id="my-stock${s.sid}" value="${s.stock}">
									<span id="">${s.stock}<span>
								</td>
								<td align="center">
									￥<span id="my-price${s.sid}">${s.price }<span>
								</td>
								<td align="center">
									<button type="button" id="subBtn" onclick="subNum(this,${s.price},${s.sid})">-</button><input id="goods-num" type="text" value="${s.goodsNum}" oninput="inputNum(this,${s.price},${s.sid})" /><button type="button" id="sumBtn" onclick="sumNum(this,${s.price},${s.sid})">+</button>
									<br>
									<c:if test="${s.goodsNum>s.stock }">
										<span id="my-stockMess${s.sid}" style="color:red;">库存不足</span>
									</c:if>
									<c:if test="${s.goodsNum<=s.stock }">
										<span id="my-stockMess${s.sid}" style="color:red;display: none;">库存不足</span>
									</c:if>
								</td>
								<td align="center">
									￥<span id="my-totalPrice${s.sid}"><fmt:formatNumber type="number" value="${s.price*s.goodsNum }" pattern="0.00" maxFractionDigits="2"/><span>
								</td>
								<td align="center">
									<a href="javascript:delOne(${s.sid});">删除</a>
								</td>
							</tr>
							</c:if>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
		<!--购物车订单end-->
		
		<!--结算部分-->
		<div class="layui-container" style="top: -20px;">
			<div class="layui-row  my-shopping-buy">
				<div class="layui-col-md12">
					<div class="my-buyCheck">
						<input class="checkBox" type="checkbox" onclick="allCheck(this)" /> 全选
						<!-- <button class="layui-btn layui-btn-sm layui-btn-danger" type="submit" onsubmit="return delYz()" formaction="shop?m=batchDel" formmethod="get" >
							<i class="layui-icon layui-icon-delete"></i>
							删除选中
						</button> -->
						<a href="javascript:delSelGoods();">
							<i class="layui-icon layui-icon-delete"></i>
							删除选中
						</a>
					</div>
					<div class="my-buyBtn">
						总价：
						￥<font id="my-totalMoney" style="font-size: 20px;font-weight: bold;color: red;">0</font>
						<button type="button" id="buyBtn" class="my-shopping-buyBtn" onclick="jiesuan()">去结算</button>
					</div>
				</div>
				
			</div>
		</div>
		<!--结算部分end-->
		
		<!--引入底部-->
		<div class="my-footer" style="margin-top: 100px;">
			<jsp:include page="footer.jsp"></jsp:include>
		</div>
		<!--引入底部end-->
	</body>

</html>
