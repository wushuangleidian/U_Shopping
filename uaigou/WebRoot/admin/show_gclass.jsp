<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>商品分类管理</title>

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
<style type="text/css">
/*隐藏滚动条*/
::-webkit-scrollbar {
	display:none
}

/* 添加按钮 */
.my-gclass-addBtn{
	position:fixed;
	background-color:#fff;
	top:0px;
	width:100%;
	height: 60px;
	line-height: 60px;
}
.my-gclass-addBtn button{
	margin-top: 10px;
}

/* 分类列表 */
.my-gclass-body {
	margin: 80px 20px 20px 40px;
}

.my-gclass-add {
	color: #01AAED;
}

.my-gclass-del {
	color: #FF5722;
}

.my-gclass-set {
	color: #5FB878;
}

.my-gclass-one {
	border: 1px solid #e5e5e5;
	height: 40px;
	font-size: 18px;
	font-weight: bold;
	line-height: 40px;
	text-indent: 20px;
	margin: 5px 0px;
}

.my-gclass-two li {
	border: 1px solid #e5e5e5;
	height: 40px;
	font-size: 16px;
	line-height: 40px;
	text-indent: 70px;
	margin: 5px 0px;
}
.my-gclass-submit{
	position: fixed;
	top:15px;
	left:180px;
}
</style>
<script type="text/javascript">
		
		$(function(){
			//获取分类信息的数据
			$.ajax({
				type:'GET',
				url:'gclass',
				dataType:'json',
				data:'m=queryAll',
				success:function(data){
					var obj = data;//全局json对象
					var htmlStr = "<ul>";
					if(obj.result==1){
						var data = obj.data;//data元素，值是array类型
						for(var i=0;i<data.length;i++){
							var oneObj = data[i];
							//封装一级分类
							htmlStr +=  "<input type='hidden' name='gid' value='"+oneObj.gid+"'>"
										+"<li class='my-gclass-one'>"+ oneObj.name + "&nbsp;&nbsp;&nbsp;&nbsp;" +
											"<a class='my-gclass-add' href='javascript:addGclass("+oneObj.gid+");'>" +
												"<i class='layui-icon'>&#xe654;</i>" +
											"</a>" +"&nbsp;"+
											"<a class='my-gclass-del' href='javascript:delGclass("+oneObj.gid+",\""+oneObj.name+"\");'>" +
												"<i class='layui-icon'>&#xe640;</i>" +
											"</a>" +"&nbsp;"+
											"<a class='my-gclass-set' href='javascript:updateName(\""+oneObj.name+"\","+oneObj.gid+");'>" +
												"<i class='layui-icon'>&#xe642;</i>" +
											"</a>" +"&nbsp;&nbsp;&nbsp;"+
											"<input onmou class='my-gclass-sxinput' type='text' style='width:40px;' name='sx' value='"+oneObj.sx+"'>"+
										"</li>";
							//封装二级分类
							var sonArr = oneObj.sonData;
							for(var j=0;j<sonArr.length;j++){
								var sonObj = sonArr[j];
								//封装一级分类
								htmlStr +=  "<input type='hidden' name='gid' value='"+sonObj.gid+"'>"
											+"<li class='my-gclass-two'><ul><li>"+ sonObj.name + "&nbsp;&nbsp;" +
												"<a class='my-gclass-del' href='javascript:delGclass("+sonObj.gid+",\""+sonObj.name+"\");'>" +
													"<i class='layui-icon'>&#xe640;</i>" +
												"</a>" +"&nbsp;"+
												"<a class='my-gclass-set' href='javascript:updateName(\""+sonObj.name+"\","+sonObj.gid+");'>" +
													"<i class='layui-icon'>&#xe642;</i>" +
												"</a>" +"&nbsp;&nbsp;&nbsp;"+
												"<input onmouseenter='showMess()' type='text' style='width:40px' name='sx' value='"+sonObj.sx+"'>"+
											"</li></ul></li>";
							}
						}
						
						htmlStr += "</ul>";
					}else if(obj.result==0){
						htmlStr+="<font color='red'>没有查询到数据！</font>";
					}
					
					
					$(".my-gclass-body").html(htmlStr);
				}
			});
		});
	
		//添加一级分类
		function addGclass(pid){
			layer.prompt({title: '请输入分类名称：', formType: 3, offset:'100px'},function(text, index){
				  layer.close(index);//关闭弹框
				  if(text.trim()!=''){
					 $.ajax({
						type:'GET',
						url:'gclass',
						dataType:'text',
						data:'m=add&pid='+pid+'&name='+text,
						async:false,
						success:function(data){
						    if(data=='1'){
						    	layer.alert('添加成功', {
								  icon: 6,
								  shade :0.1,
								  shadeClose :false,
								  offset:'100px'
							    });
							    location.reload();//刷新当前页面
						    }else if(data=='0'){
						    	layer.alert('添加失败', {
								  icon: 5,
								  shade :0.1,
								  shadeClose :false,
								  offset:'100px'
							    });
						    }
							
						}
					 });
				  }else{
				  	layer.msg('品类名称不能为空', function(){
					});
				  }
				 
			});
		}
		
		//删除分类
		function delGclass(id,name){
			layer.confirm('确定要删除'+name+'分类吗？', {
			    btn: ['删除', '取消'],
			    offset:'100px'
			  },function(){
			  	 $.ajax({
			  	 	type:'GET',
					url:'gclass',
					dataType:'text',
					data:'m=delete&id='+id,
					async:false,
					success:function(data){
					 		if(data=='1'){
						    	layer.alert('删除成功', {
								  icon: 6,
								  shade :0.1,
								  shadeClose :false,
								  offset:'100px'
							    });
							    location.reload();//刷新当前页面
						    }else if(data=='0'){
						    	layer.alert('删除失败', {
								  icon: 5,
								  shade :0.1,
								  shadeClose :false,
								  offset:'100px'
							    });
						    }else if(data=='2'){
						   		 layer.alert('当前分类下有子分类，不能直接删除！', {
								  icon: 5,
								  shade :0.1,
								  shadeClose :false,
								  offset:'100px'
							    });
						    }
					}
			  	 });
			  	 layer.close(index);//关闭弹框
			  },function(){
			  	
			});
		}
		
		//修改分类名称
		function updateName(name,gid){
			layer.prompt({title: '修改'+name+'分类的名称：', formType: 3, offset:'100px'},function(text, index){
				  layer.close(index);//关闭弹框
				  if(text.trim()!=''){
					 $.ajax({
						type:'GET',
						url:'gclass',
						dataType:'text',
						data:'m=update&gid='+gid+'&name='+text,
						async:false,
						success:function(data){
						    if(data=='1'){
						    	layer.alert('修改成功', {
								  icon: 6,
								  shade :0.1,
								  shadeClose :false,
								  offset:'100px'
							    });
							    location.reload();//刷新当前页面
						    }else if(data=='0'){
						    	layer.alert('修改失败', {
								  icon: 5,
								  shade :0.1,
								  shadeClose :false,
								  offset:'100px'
							    });
						    }
							
						}
					 });
				  }else{
				  	layer.msg('分类名称不能为空', function(){
					});
				  }
				 
			});
		}
	</script>
</head>

<body>
	<div class="my-gclass-addBtn">
		<button onm class="layui-btn layui-btn-normal" onclick="addGclass(0)">
			<i class="layui-icon">&#xe608;</i> 添加一级分类
		</button>
	</div>
	
	<form action="gclass" method="get">
		<input type="hidden" name='m' value="setSx">
		<div class="my-gclass-body">
			<img alt="" src="img/load.gif">
		</div>
		<input class="my-gclass-submit layui-btn layui-btn-sm" type="submit" value="保存排序">
	</form>

</body>
</html>
