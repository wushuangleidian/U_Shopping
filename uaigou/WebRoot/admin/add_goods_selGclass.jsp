<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>添加商品前的分类选择</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript" src="js/jquery-3.3.1.min.js"></script>
	<script type="text/javascript" src="layer/layer.js"></script>
	<script type="text/javascript" src="layui/layui.js"></script>
	<link rel="stylesheet" href="layui/css/layui.css" />
	<script type="text/javascript">
		var jsonObj;

		$(function(){
			$.ajax({
					type:'GET',
					url:'gclass',
					dataType:'json',
					data:'m=queryAll',
					async:false,
					success:function(data){
						var obj = data;//全局json对象
						if(obj.result==1){
							var data = obj.data;//data元素，值是array类型
							jsonObj = obj;
							var htmlStr = "";
							for(var i=0;i<data.length;i++){
								var oneObj = data[i];//一级分类对象
								htmlStr += "<option id='"+oneObj.gid+"'  value='"+oneObj.gid+"'>"+oneObj.name+"</option>";
							}
							$("#my-oneClass").html(htmlStr);
						}
					}
				});
				
		});		
	
		//一级分类的选择事件
		function selectOne(obj){
			var ops = obj.getElementsByTagName("option");
			for(var i=0;i<ops.length;i++){
				var op = ops[i];
				if(op.selected == true){
					var gid = op.id;
					//查询二级分类
					var dataArr = jsonObj.data;
					var htmlStr = "";
					for(var j=0;j<dataArr.length;j++){
						var oneObj = dataArr[j];//得到一级对象
						if(oneObj.gid==gid){
							var sonArr = oneObj.sonData;
							for(var k=0;k<sonArr.length;k++){
								var sonObj = sonArr[k];
								htmlStr += "<option id='' value='"+sonObj.gid+"'>"+sonObj.name+"</option>";
							}
						}
					}
					
					$("#my-twoClass").html(htmlStr);
					
				}
			}
		}
		
		//表单验证
		function yz(){
			var oneGid = $("#my-oneClass").val();
			var twoGid = $("#my-twoClass").val();
			if(oneGid==''){
				layer.msg('请选择一级分类', function(){
				});
				return false;
			}
			if(twoGid==''){
				layer.msg('请选择二级分类', function(){
				});
				return false;
			}
			return true;
		}
	
	</script>

  </head>
  
  <body>
  	<style>
  		#my-oneClass,#my-twoClass{
  			display:block;
  			float:left;
  			width: 225px;
  			height: 300px;
  			margin: 20px 30px;
  		}
  	</style>
    <form action="goods" onsubmit="return yz()" method="post">
		<div class="my-addTag">
			<input type="hidden" name="m" value="addBefore"> 
			请选择分类： <br>
			<select id="my-oneClass" multiple="multiple" onchange="selectOne(this)" name="oneGid">
			</select> 
			<p style="float: left;height: 300px;line-height: 300px;">
				<i class="layui-icon layui-icon-next"></i>
			</p>
			<select id="my-twoClass" multiple="multiple" name="twoGid">
			</select> 
		</div>
		<div class="my-addTag" style="clear: both;margin-left:200px;">
			<input type="submit" class="layui-btn layui-btn-normal" style="width: 150px;" value="发布商品">
		</div>
	</form>
  </body>
</html>
