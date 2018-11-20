<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>添加标签</title>

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
						var htmlStr = "<option value='0'>请选择</option>";
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
				var htmlStr = "<option value='0'>请选择</option>";
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
	
	var index = parent.layer.getFrameIndex(window.name);
	//表单验证
	function yz(){
		var oneGid = $("#my-oneClass").val();
		var twoGid = $("#my-twoClass").val();
		if(twoGid=='0'){
			layer.alert('选择分类', {
				icon: 5,
				shade :0.1,
				shadeClose :false,
				offset:'100px'
			});
			return false;
		}
		
		var tagName = $("#tagName").val();
		if(tagName==null || tagName.trim()==''){
			layer.alert('标签名不能为空', {
				icon: 5,
				shade :0.1,
				shadeClose :false,
				offset:'100px'
			});
			return false;
		}
		
		//获取是否推荐
		var tjs = document.getElementsByName("tj");
		var tj;
		for(var i=0;i<tjs.length;i++){
			if(tjs[i].checked == true){
				tj = tjs[i].value;
			}
		}
		
		$.ajax({
			type:'GET',
			url:'tag',
			dataType:'text',
			data:'m=add&oneGid='+oneGid+'&twoGid='+twoGid+'&tagName='+tagName+'&tj='+tj,
			success:function(data){
				if(data=='1'){
					parent.layer.msg('添加成功！');
    				parent.layer.close(index);
				}else if(data=='0'){
					parent.layer.msg('添加失败！');
    				parent.layer.close(index);
				}
			}
		
		});
		
		return false;
	
	}
	
	</script>
</head>

<body>
	<style>
		.my-addTag{
			height: 40px;
			line-height: 40px;
			font-size: 16px;
			text-indent: 20px;
			margin: 10px 0px;
			color:#666;
		}
	</style>
	<form action="" onsubmit="return yz()" method="get">
		<div class="my-addTag">
			<input type="hidden" name="m" value="queryAll"> 请选择分类： 
			<select id="my-oneClass" onchange="selectOne(this)">
				<option value='0'>请选择</option>
			</select> 
			<select id="my-twoClass" name="gid">
				<option value='0'>请选择</option>
			</select> 
		</div>
		<div class="my-addTag">
			请输入标签名：<input id="tagName" type="text" style="width: 100px;height: 35px;" name="tagName">
		</div>
		<div class="my-addTag">
			是否推荐：
			<input type="radio" name="tj" value="0" checked="checked">否
			<input type="radio" name="tj" value="1">是
		</div>
		<div class="my-addTag">
			<input type="submit" class="layui-btn layui-btn-normal" value="添加">
		</div>
	</form>

</body>
</html>
