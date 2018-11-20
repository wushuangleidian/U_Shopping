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
		<title>修改收货地址</title>
		<link rel="stylesheet" href="layui/css/layui.css" />
		<script type="text/javascript" src="js/jquery-3.3.1.min.js"></script>
		<script type="text/javascript" src="layer/layer.js"></script>
		<script type="text/javascript" src="layui/layui.js" ></script>
		<style type="text/css">
			/* 城市三级联动下拉框 */
				.my-shouhuo-address{
					height: 35px;
					margin: 0px 5px;
					border-color: #D2D2D2;
				}
		</style>
		<script type="text/javascript">
			//城市数据
			var cityData;
			$(function(){
				var shengName = $("#shengName").val();
				$.ajax({
					type:'GET',
					url:'adr',
					dataType:'json',
					data:'m=loadCity',
					async:false,
					success:function(data){
						cityData = data;
						var htmlStr = "<option value='"+shengName+"'>"+shengName+"</option>";
						var shengArr = cityData.sheng;
						for(var i=0;i<shengArr.length;i++){
							var sheng = shengArr[i];
							htmlStr += "<option id='"+sheng.code+"' value='"+sheng.shengName+"'>"+sheng.shengName+"</option>";
						}
						$("#my-sheng").html(htmlStr);
					}
				});
				
				//省份的选择事件
				$("#my-sheng").change(function(){
					var val = $("#my-sheng option:selected").val();
					var shengCode = $("#my-sheng option:selected").attr("id");
					var htmlStr = "<option value=''>请选择市</option>";
					var shiObj = cityData.shi;
					for(var shi in shiObj){
						if(shi==shengCode){
							var shiArr = shiObj[shi];
							for(var i=0;i<shiArr.length;i++){
								var sArr = shiArr[i];
								htmlStr += "<option id='"+sArr[1]+"' value='"+sArr[0]+"'>"+sArr[0]+"</option>";
							}
						}
					}
					$("#my-shi").html(htmlStr);
				});
				
				//市的选择事件
				$("#my-shi").change(function(){
					var val = $("#my-shi option:selected").val();
					var shiCode = $("#my-shi option:selected").attr("id");
					var htmlStr = "<option value=''>请选择县区</option>";
					var quObj = cityData.qu;
					for(var qu in quObj){
						if(qu==shiCode){
							var quArr = quObj[qu];
							for(var i=1;i<quArr.length;i++){
								var qArr = quArr[i];
								htmlStr += "<option id='"+qArr[1]+"' value='"+qArr[0]+"'>"+qArr[0]+"</option>";
							}
						}
					}
					
					$("#my-qu").html(htmlStr);
				});
				
			});
			
		</script>
	</head>

	<body>
			

		<form action="adr" style="width: 650px;margin-top: 20px;" method="post">
			<input type="hidden" name="m" value="update">
			<input type="hidden" name="adrid" value="${adr.adrid }">
			<input type="hidden" name="vipno" value="${sessionScope.vip.vipNo}">
			<div class="layui-form-item">
				<label class="layui-form-label">收件人</label>
				<div class="layui-input-block">
					<input id="userName" type="text" name="name" value="${adr.name }" required lay-verify="required" placeholder="请输入姓名" autocomplete="off" class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">电话</label>
				<div class="layui-input-block">
					<input type="phone" name="phone" value="${adr.phone }" required lay-verify="required" placeholder="请输入电话" autocomplete="off" class="layui-input">
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">地区</label>
					<input type="hidden" id="shengName" value="${adr.sheng }">
			      <select class="my-shouhuo-address" name="sheng" id="my-sheng">
			        <option value='${adr.sheng }'>${adr.sheng }</option>
			      </select>
			      <select class="my-shouhuo-address" name="shi" id="my-shi">
			        <option value='${adr.shi }'>${adr.shi }</option>
			      </select>
			      <select class="my-shouhuo-address" name="qu" id="my-qu">
			        <option value="${adr.qu }">${adr.qu }</option>
			      </select>
			</div>
			<div class="layui-form-item layui-form-text">
				<label class="layui-form-label">详细地址</label>
				<div class="layui-input-block">
					<textarea name="adr" placeholder="请输入内容" class="layui-textarea">${adr.adr}</textarea>
				</div>
			</div>
			<div class="layui-form-item">
				<div class="layui-input-block">
					<button class="layui-btn layui-btn-normal" style="width:100px;" lay-submit lay-filter="formDemo">修改</button>
				</div>
			</div>
		</form>

		<script>
			//Demo
			layui.use('form', function() {
				var form = layui.form;
				var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
				//监听提交
				form.on('submit(formDemo)', function(data) {
					//var val = $("#userName").val();
					//parent.layer.msg('收件人 ' + val + ' 添加成功！');
					parent.layer.close(index);
				});
				
			});
			
		</script>

	</body>

</html>
