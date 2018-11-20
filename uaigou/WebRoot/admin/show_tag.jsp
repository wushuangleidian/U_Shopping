<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/pagebreak" prefix="page" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>标签管理</title>

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
<link rel="stylesheet" href="css/fenye.css" />
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
	position: absolute;
	top:20px;
	left:180px;
}
</style>
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
						htmlStr += "<option id='"+oneObj.gid+"' value='"+oneObj.gid+"'>"+oneObj.name+"</option>";
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
	
	//添加标签
	function addTag(){
		layer.open({
		  type: 2,
		  title:'添加标签',
		  area: ['700px', '450px'],
		  fixed: false, //不固定
		  maxmin: false,
		  content: 'admin/add_Tag.jsp'
		});
	}
	
	//修改标签
	function updateTag(id,dqy,oneGid,twoGid){
		layer.open({
		  type: 2,
		  title:'修改标签',
		  area: ['700px', '450px'],
		  fixed: false, //不固定
		  maxmin: false,
		  content: 'tag?m=findById&id='+id+'&oneGid='+oneGid+'&twoGid='+twoGid+'&dqy='+dqy
		});
	}
	
	//删除标签
	function delTagObj(id,name,oneGid,twoGid){
		layer.confirm('确定要删除'+name+'标签吗？', {
		  btn: ['删除','取消'] //按钮
		}, function(){
		  location.href = 'tag?m=delete&id='+id+'&oneGid='+oneGid+'&twoGid='+twoGid;
		}, function(){
		  
		});
	}
	
</script>
</head>

<body>
	<div class="">
		<button onm class="layui-btn layui-btn-normal" onclick="addTag()">
			<i class="layui-icon">&#xe608;</i> 添加标签
		</button>
	</div>
	<div style="margin: 10px;">
		<form action="tag" method="get">
			<input type="hidden" name="m" value="queryAll">
			请先搜索要查询的分类：
			<select id="my-oneClass" onchange="selectOne(this)" name="oneGid">
				<option value='0'>请选择</option>
			</select>
			<select id="my-twoClass" name="twoGid">
				<option value='0'>请选择</option>
			</select>
			<input type="submit" class="layui-btn layui-btn-xs" value="搜索">
			&nbsp;&nbsp;
			<c:if test="${not empty oneGc.name }">
				当前搜索分类：${oneGc.name}
			</c:if>
			<c:if test="${not empty twoGc.name }">
				&nbsp;
				<i class="layui-icon layui-icon-right"></i>
				&nbsp;&nbsp;${twoGc.name}
			</c:if>
			
		</form>
	</div>
	
	<div class="layui-form">
	  <table class="layui-table">
	    <colgroup>
	      <col width="150">
	      <col width="200">
	      <col width="200">
	      <col width="150">
	      <col >
	    </colgroup>
	    <thead>
	      <tr>
	        <th>ID</th>
	        <th>标签名</th>
	        <th>推荐</th>
	        <th>操作</th>
	      </tr> 
	    </thead>
	    <tbody>
		    <c:forEach var="tag" items="${pb.pageList }">
		    	<tr>
			        <td>${tag.id }</td>
			        <td>${tag.tagName }</td>
			        <c:if test="${tag.tj==1 }">
			        	<td>
			        		<font color='green'><i class='layui-icon layui-icon-ok-circle'></i></font>
			        	</td>
			        </c:if>
			        <c:if test="${tag.tj==0 }">
			        	<td></td>
			        </c:if>
			        <td>
			        	<c:if test="${tag.tj==1 }">
				        	<a style="color:#FFB800;" href="tag?m=changeTj&id=${tag.id}&tj=0&dqy=${pb.currentPage}&oneGid=${oneGc.gid}&twoGid=${twoGc.gid}">取消</a>
				        </c:if>
				        <c:if test="${tag.tj==0 }">
				        	<a style="color:#1E9FFF;" href="tag?m=changeTj&id=${tag.id}&tj=1&dqy=${pb.currentPage}&oneGid=${oneGc.gid}&twoGid=${twoGc.gid}">推荐</a>
				        </c:if>
				        &nbsp;&nbsp;
				        <c:choose>
				        	<c:when test="${not empty oneGc.gid and not empty twoGc.gid }">
					        	<a class='my-gclass-set' href='javascript:updateTag(${tag.id},${pb.currentPage},${oneGc.gid },${twoGc.gid });'>
									<i class='layui-icon'>&#xe642;</i>
								</a>
				        	</c:when>
				        	<c:otherwise>
				        		<a class='my-gclass-set' href='javascript:updateTag(${tag.id},${pb.currentPage});'>
									<i class='layui-icon'>&#xe642;</i>
								</a>
				        	</c:otherwise>
				        </c:choose>
				        &nbsp;&nbsp;
				        <c:choose>
				        	<c:when test="${not empty oneGc.gid and not empty twoGc.gid }">
					        	<a class='my-gclass-del' href="javascript:delTagObj(${tag.id},'${tag.tagName }',${oneGc.gid },${twoGc.gid });">
									<i class='layui-icon'>&#xe640;</i>
								</a>
				        	</c:when>
				        	<c:otherwise>
				        		<a class='my-gclass-del' href="javascript:delTagObj(${tag.id},'${tag.tagName }');">
									<i class='layui-icon'>&#xe640;</i>
								</a>
				        	</c:otherwise>
				        </c:choose>
				        
						
			        </td>
			     </tr>
		    </c:forEach>
	    </tbody>
	  </table>
	  ${mess }
	</div>
	<div style="text-align: center;margin: 10px;">
		<page:page url="tag?m=queryAll&oneGid=${oneGc.gid}&twoGid=${twoGc.gid}&dqy" totalPage="${pb.totalPage }" currentPage="${pb.currentPage }"/>
	</div>
</body>
</html>
