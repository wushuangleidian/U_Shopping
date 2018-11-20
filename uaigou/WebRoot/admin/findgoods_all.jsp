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
    
    <title>查询所有商品</title>
    
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
		/* 搜索区域 */
		.my-goods-search{
			height: 45px;
			line-height: 45px;
		}
		/* 搜索结果 */
		.my-search-tableTitle th{
			font-weight: bold;
			text-align: center;
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
						$("#oneGid").html(htmlStr);
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
								htmlStr += "<option id='"+sonObj.gid+"' value='"+sonObj.gid+"'>"+sonObj.name+"</option>";
							}
						}
					}
					
					$("#twoGid").html(htmlStr);
					
				}
			}
		}
		
		//二级分类的选择事件
		function selectTwo(obj){
			var ops = obj.getElementsByTagName("option");
			var htmlStr = "<option value='0'>请选择</option>";
			for(var i=0;i<ops.length;i++){
				var op = ops[i];
				if(op.selected == true){
					var twogid = op.id;//二级分类的gid
					
					var dataArr = jsonObj.data;//得到一级分类的对象数组
					for(var j=0;j<dataArr.length;j++){
						var oneObj = dataArr[j];//得到一级对象
						var sonArr = oneObj.sonData;//得到二级分类的数组
						for(var k=0;k<sonArr.length;k++){
							var sonObj = sonArr[k];//得到二级分类对象
							if(sonObj.gid==twogid){
								var tags = sonObj.tag;//得到标签对象数组
								
								for(var m=0;m<tags.length;m++){
									var tag = tags[m];
									htmlStr += "<option id='' value='"+tag.id+"'>"+tag.tagName+"</option>";
								}
							}
							
						}
					}
				}
			}
			$("#tid").html(htmlStr);
		}
		
		//删除商品
		function deleteGoods(id){
			var rel = confirm('确定要删除吗？');
			if(rel){
				$.ajax({
					type:'GET',
					url:'goods',
					data:'m=set&id='+id+'&delete=1',
					dataType:'text',
					async:false,
					success:function(data){
						if(data==1){
							location.reload();
						}
					}
				});
			}
		}
	</script>
  </head>
  
  <body>
    <fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
	  <legend>查询所有商品</legend>
	</fieldset>
	
	<!-- 搜索区域 -->
	<div class="layui-container">
		<form action="goods" method="post">
			<input type="hidden" name="m" value="queryAll">
			<input type="hidden" name="status" value="${status}">
			<input type="hidden" name="tuijian" value="${tuijian}">
		 <div class="layui-row my-goods-search">
		 	商品标题：<input type="text" name="title" id="" style="width: 200px;height: 30px;font-size: 15px;">
		 	&nbsp;&nbsp;&nbsp;&nbsp;
		 	商品编号：<input type="text" name="goodsNo" id="" style="width: 150px;height: 30px;font-size: 15px;">
		 	&nbsp;&nbsp;&nbsp;&nbsp;
		 	一级分类:
		 	<select id="oneGid" name="oneGid" onchange="selectOne(this)">
		 		<option value='0'>请选择</option>
		 	</select>
		 	&nbsp;&nbsp;&nbsp;&nbsp;
		 	二级分类:
		 	<select id="twoGid" name="twoGid" onchange="selectTwo(this)">
		 		<option value='0'>请选择</option>
		 	</select>
		 	&nbsp;&nbsp;&nbsp;&nbsp;
		 	商品标签:
		 	<select id="tid" name="tid">
		 		<option value='0'>请选择</option>
		 	</select>
		 </div>
		 <div class="layui-row my-goods-search">
		 	价格：
		 	<select name="priceSort">
		 		<option value="asc">价格升序</option>
		 		<option value="desc">价格降序</option>
		 	</select>
		 	&nbsp;&nbsp;&nbsp;&nbsp;
		 	库存：
		 	<select name="stockSort">
		 		<option value="asc">库存降序</option>
		 		<option value="desc">库存升序</option>
		 	</select>
		 	&nbsp;&nbsp;&nbsp;&nbsp;
		 	销量：
		 	<select name="salSort">
		 		<option value="asc">销量降序</option>
		 		<option value="desc">销量升序</option>
		 	</select>
		 	&nbsp;&nbsp;&nbsp;&nbsp;
		 	<button type="submit" class="layui-btn layui-btn-sm layui-btn-normal" style="width: 80px;font-size:16px;">
		 		<i class="layui-icon layui-icon-search"></i>搜索
		 	</button>
		 </div>
		 </form>
	</div>
	<!-- 搜索区域end -->
	
	<!-- 搜索结果区域 -->
	<div class="layui-form">
	  <table class="layui-table">
	    <colgroup>
	      <col width="460">
	      <col width="100">
	      <col width="200">
	      <col width="80">
	      <col width="80">
	      <col width="80">
	      <col width="70">
	      <col>
	    </colgroup>
	    <thead>
	      <tr class="my-search-tableTitle">
	        <th>商品标题</th>
	        <th>价格</th>
	        <th>规格</th>
	        <th>库存</th>
	        <th>销量</th>
	        <th>上下架</th>
	        <th>推荐</th>
	        <th>操作</th>
	      </tr> 
	    </thead>
	    <tbody>
	    	<c:forEach var="g" items="${pb.pageList }">
		      <tr>
		        <td>
		        	<img alt="" src="${g.firstImg }" width="50px" height="50px">
		        	${g.title }
		        </td>
		        <td style="text-align: center;">${g.price }</td>
		        <td style="text-align: center;">${g.guige }</td>
		        <td style="text-align: center;">${g.stock }</td>
		        <td style="text-align: center;">${g.salNum }</td>
		        <td style="text-align: center;">
		        	<c:if test="${g.status==1 }">
		        		<font color="#1E9FFF">在售</font>
		        	</c:if>
		        	<c:if test="${g.status==2 }">
		        		<font color="#2F4056">下架</font>
		        	</c:if>
		        </td>
		        <td style="text-align: center;">
		        	<c:if test="${g.tuijian==1 }">
		        		<i class="layui-icon layui-icon-ok-circle" style="color:#009688;font-size:18px;"></i>
		        	</c:if>
		        </td>
		        <td style="text-align: center;">
		        	<!-- 设置上下架 -->
		        	<c:if test="${g.status==1 }">
		        		<a href="goods?m=set&setStatus=2&id=${g.id}&title=${gs.title}&goodsNo=${gs.goodsNo}&oneGid=${gs.oneGid}&twoGid=${gs.twoGid}&tid=${gs.tid}&priceSort=${gs.priceSort}&stockSort=${gs.stockSort}&salSort=${gs.salSort}&status=${status}&tuijian=${tuijian}&dqy=${pb.currentPage}" style="color:#FF5722;">
		        		下架</a>
		        	</c:if>
		        	<c:if test="${g.status==2 }">
		        		<a href="goods?m=set&setStatus=1&id=${g.id}&title=${gs.title}&goodsNo=${gs.goodsNo}&oneGid=${gs.oneGid}&twoGid=${gs.twoGid}&tid=${gs.tid}&priceSort=${gs.priceSort}&stockSort=${gs.stockSort}&salSort=${gs.salSort}&status=${status}&tuijian=${tuijian}&dqy=${pb.currentPage}" style="color:#1E9FFF;">
		        		上架</a>
		        	</c:if>
		        	&nbsp;
		        	<!-- 设置推荐 -->
		        	<c:if test="${g.tuijian==1 }">
		        		<a href="goods?m=set&setTuijian=2&id=${g.id}&title=${gs.title}&goodsNo=${gs.goodsNo}&oneGid=${gs.oneGid}&twoGid=${gs.twoGid}&tid=${gs.tid}&priceSort=${gs.priceSort}&stockSort=${gs.stockSort}&salSort=${gs.salSort}&status=${status}&tuijian=${tuijian}&dqy=${pb.currentPage}" style="color:#FF5722;">
		        		取消推荐</a>
		        	</c:if>
		        	<c:if test="${g.tuijian==2 }">
		        		<a href="goods?m=set&setTuijian=1&id=${g.id}&title=${gs.title}&goodsNo=${gs.goodsNo}&oneGid=${gs.oneGid}&twoGid=${gs.twoGid}&tid=${gs.tid}&priceSort=${gs.priceSort}&stockSort=${gs.stockSort}&salSort=${gs.salSort}&status=${status}&tuijian=${tuijian}&dqy=${pb.currentPage}" style="color:#1E9FFF;">
		        		设为推荐</a>
		        	</c:if>
		        	&nbsp;
		        	<!-- 修改 -->
		        	<a href="goods?m=updateBefore&goodsNo=${g.goodsNo}" class="layui-btn layui-btn-sm layui-btn-danger">
		        		<i class="layui-icon layui-icon-edit"></i>
		        	</a>
		        	<!-- 删除 -->
		        	<a href="javascript:deleteGoods(${g.id});" class="layui-btn layui-btn-sm layui-btn-primary">
		        		<i class="layui-icon layui-icon-delete"></i>
		        	</a>
		        </td>
		      </tr>
	    	</c:forEach>
	    </tbody>
	  </table>
	</div>
	<!-- 搜索结果end -->
	
	<!-- 分页 -->
	<div>
		<c:choose>
			<c:when test="${not empty status and status!=0 }">
				<page:page url="goods?m=queryAll&title=${gs.title}&goodsNo=${gs.goodsNo}&oneGid=${gs.oneGid}&twoGid=${gs.twoGid}&tid=${gs.tid}&priceSort=${gs.priceSort}&stockSort=${gs.stockSort}&status=${status}&salSort=${gs.salSort}&dqy" totalPage="${pb.totalPage }" currentPage="${pb.currentPage }"/>
			</c:when>
			<c:when test="${not empty tuijian and tuijian!=0 }">
				<page:page url="goods?m=queryAll&title=${gs.title}&goodsNo=${gs.goodsNo}&oneGid=${gs.oneGid}&twoGid=${gs.twoGid}&tid=${gs.tid}&priceSort=${gs.priceSort}&stockSort=${gs.stockSort}&tuijian=${tuijian}&salSort=${gs.salSort}&dqy" totalPage="${pb.totalPage }" currentPage="${pb.currentPage }"/>
			</c:when>
			<c:otherwise>
				<page:page url="goods?m=queryAll&title=${gs.title}&goodsNo=${gs.goodsNo}&oneGid=${gs.oneGid}&twoGid=${gs.twoGid}&tid=${gs.tid}&priceSort=${gs.priceSort}&stockSort=${gs.stockSort}&salSort=${gs.salSort}&dqy" totalPage="${pb.totalPage }" currentPage="${pb.currentPage }"/>
			</c:otherwise>
		</c:choose>
		
	</div>
	<!-- 分页end -->
  </body>
</html>
