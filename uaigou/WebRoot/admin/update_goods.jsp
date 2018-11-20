<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>修改商品</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	<script type="text/javascript" src="js/jquery-3.3.1.min.js" ></script>
	<script type="text/javascript" src="layer/layer.js" ></script>
	<script type="text/javascript" src="layui/layui.js" ></script>
	<script type="text/javascript" src="js/ajaxfileupload.js"></script>
	<link rel="stylesheet" type="text/css" href="layui/css/layui.css"/>
	<script type="text/javascript" src="ueditor/ueditor.config.js"></script>
    <!-- 编辑器源码文件 -->
    <script type="text/javascript" src="ueditor/ueditor.all.js"></script>
    <script type="text/javascript" src="ueditor/lang/zh-cn/zh-cn.js"></script>
	<style type="text/css">
		/*隐藏滚动条*/
		::-webkit-scrollbar {
			display:none
		}
		/* 商品主图 */
    	#file{
    		border: 1px dashed #D9D9D9;
    		position: absolute;
    		overflow: hidden;
    		top:30px;
    		border-radius:5px;
    	}
    	#file:hover{
    		border-color:#188AE2;
    	}
    	.myFile{
    		border: 1px solid #000000;
    		display: block;
    		position: relative;
    		width: 150px;
    		height: 150px;
    		z-index: 100;
    		opacity: 0;
    		cursor: pointer;
    	}
    	.span{
    		display: block;
    		position: absolute;
    		z-index: 100;
    		width: 150px;
    		height: 150px;
    		text-align: center;
    		line-height: 150px;
    		color: #E2E2E2;
    		font-size: 50px;
    		font-weight: bold;
    	}
    	/* 删除主图按钮 */
    	.delImg{
    		display: none;
    		position: absolute;
    		display:block;
    		background-color:#000;
    		z-index: 200;
    		width: 40px;
    		height:40px;
    		line-height:40px;
    		text-indent:8px;
    		opacity:0.6;
    		top:0px;
    		right:0px;
    	}
    	.delImg i{
    		color:#fff;
    	}
    	/* 必选的font标签 */
    	.bixuanFont{
    		display:block;
    		float:left;
    		width:15px;
    		height:18px;
    		line-height:27px;
    		text-align:center;
    		font-size: 22px;
    		color: red;
    	}
	</style>
	<script type="text/javascript">
		var layedit;
		var index;
		
		layui.use(['form', 'layedit', 'laydate'], function(){
		    var form = layui.form;
		    var layer = layui.layer;
		     layedit = layui.layedit;
		    var laydate = layui.laydate;
  			
  			//创建富文本编辑器
  		    index = layedit.build("details",{
  		    	height:'500px',
  		    	uploadImage:{
  		    		url:'uploadImg',
  		    		type:'post'
  		    	}
  		    });
  			
		});
		
		$(function(){
			var ue = UE.getEditor('container');//创建百度富文本编辑器 
			var goodsNo = $("#goodsno").val();
			var twoGid = $("#twoGid").val();
			//获取标签
			$.ajax({
				type:'GET',
				url:'goods',
				dataType:'json',
				data:'m=findTagByGoods&goodsNo='+goodsNo+'&twoGid='+twoGid,
				async:false,
				success:function(data){
					if(data.result==1){
						var tagArr = data.data;
						var htmlStr = "";
						if(tagArr!=null && tagArr.length>0){
							for(var i=0;i<tagArr.length;i++){
								var tag = tagArr[i];//标签对象
								if(tag.sel==1){
									htmlStr += " <input type='checkbox' name='tag' title='"+tag.name+"' value='"+tag.id+"' checked>";
								}else if(tag.sel==2){
									htmlStr += " <input type='checkbox' name='tag' title='"+tag.name+"' value='"+tag.id+"'>";
								}
							}
						}
						
						$("#tagsDiv").html(htmlStr);
					}
				}
			});
		});
		
		//商品标题输入事件
		var t;//临时存储标题的变量
		function titleInput(obj){
			var val = obj.value;
			var len = val.length;
			if(len<=40){
				t = val;
				$("#showTitleNum").text(len+"/40");
			}else{
				obj.value = t;
				$("#showTitleNum").html("<font color='red'>标题长度不能超过40个字符</font>");
			}
		}
		
		//异步上传主图
		function uploadImg(id,i){
			$.ajaxFileUpload({
				fileElementId:id,
				url:'upImg',
				type:'POST',
				dataType:'text',
				async:false,
				success:function(data){
					if(data!=null || data.trim()!=""){
						$("#span"+i).html("<img src='"+data+"' width='150px' height='150px'>");
						$("#firstImg"+i).val(data);
						$("#delImg"+i).show();
					}
				}
			
			});
		}
		//删除主图
		function deleteImg(i){
			$("#span"+i).html("+");
			$("#firstImg"+i).val("");
			$("#delImg"+i).hide();
		}
		
		//表单验证
		function yz(){
			//验证商品编号
			var goodsno = $("#goodsno").val();
			if(goodsno==null || goodsno.trim()==''){
				alert("商品编号不能为空，请重现加载当前页面！");
				return false;
			}
			//验证标题
			var title = $("#title").val();
			if(title==null || title.trim()==''){
				alert("商品标题不能为空！");
				$("#title").focus();//让文本框获取焦点
				return false;
			}
			//验证价格
			var price = $("#price").val();
			if(price==null || price.trim()==''){
				alert("商品价格不能为空！");
				$("#price").focus();
				return false;
			}else if(price==0){
				alert("商品价格不能为0！");
				$("#price").focus();
				return false;
			}
			//验证规格
			var guige = $("#guige").val();
			if(guige==null || guige.trim()==''){
				alert("至少要有一个商品规格！");
				$("#guige").focus();
				return false;
			}
			//验证库存
			var stock = $("#stock").val();
			if(stock==null || stock.trim()==''){
				alert("商品库存不能为空！");
				$("#stock").focus();
				return false;
			}else if(parseInt(stock)!=stock){
				alert("商品库存只能是整数！");
				$("#stock").focus();
				return false;
			}else if(parseInt(stock)==0){
				alert("商品库存不能为0！");
				$("#stock").focus();
				return false;
			}
			//验证主图
			var firstImg = $("#firstImg1").val();
			if(firstImg==null || firstImg.trim()==''){
				alert("商品主图不能为空！");
				$("#myFile1").focus();
				return false;
			}
			//验证商品详情
			var detail = UE.getEditor('container').hasContents();//判断富文本编辑器是否有内容
			if(!detail){
				alert("商品详情不能为空！");
				//没有内容，获取焦点
				UE.getEditor('container').focus();
				return false;
			}
			
			return true;
		}
		
		//数字框输入事件
		function numInput(obj){
			var val = obj.value;
			if(isNaN(val)){
				obj.value="";
				alert("只能输入数字");
			}
		}
		
	</script>
  </head>
  
  <body>
   	<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
	  <legend>当前分类：
	  			${goods.oneGc.name}
	  			<i class="layui-icon layui-icon-right"></i>
	  			${goods.twoGc.name }
	  </legend>
	</fieldset>
	 
	<form class="layui-form" action="goods" method="post" onsubmit="return yz()">
		<input type="hidden" name="oneGid" value="${goods.oneGc.gid }">
		<input type="hidden" id="twoGid" name="twoGid" value="${goods.twoGc.gid }">
		<input type="hidden" id="" name="id" value="${goods.id }">
		<input type="hidden" name="m" value="update">
	  <div class="layui-form-item">
	    <label class="layui-form-label">商品编号</label>
	    <div class="layui-input-block" style="line-height: 36px;">
	      <input id="goodsno" type="hidden" name="goodsNo" value="${goods.goodsNo }">
	    	${goods.goodsNo }
	    </div>
	  </div>
	  <div class="layui-form-item">
	    <label class="layui-form-label">商品标签</label>
	    <div class="layui-input-block" id="tagsDiv">
	     	
	    </div>
	  </div>
	  <div class="layui-form-item">
	    <label class="layui-form-label"><font class="bixuanFont">*</font>商品标题</label>
	    <div class="layui-input-block">
	      <input type="text" name="title" id="title" value="${goods.title }"  lay-verify="title" oninput="titleInput(this)" autocomplete="off" placeholder="请输入商品标题" class="layui-input">
	    	<span id="showTitleNum">0/40</span>
	    </div>
	  </div>
	  <div class="layui-form-item layui-form-text">
	    <label class="layui-form-label">商品副标题</label>
	    <div class="layui-input-block">
	      <textarea placeholder="请输入副标题内容" name="subHead" class="layui-textarea">${goods.subHead}</textarea>
	    </div>
	  </div>
	  <div class="layui-form-item">
	    <label class="layui-form-label">促销语</label>
	    <div class="layui-input-block">
	      <input type="text" name="cuxiao" lay-verify="title" value="${goods.cuxiao}" autocomplete="off" placeholder="请输入商品促销语" class="layui-input">
	    </div>
	  </div>
	  <div class="layui-form-item">
	    <div class="layui-inline">
	      <label class="layui-form-label"><font class="bixuanFont">*</font>商品价格</label>
	      <div class="layui-input-inline" style="width: 100px;">
	        <input type="text" id="price" name="price" value="${goods.price}" placeholder="￥" oninput="numInput(this)" autocomplete="off" class="layui-input">
	      </div>
	    </div>
	  </div>
	  <div class="layui-form-item">
	    <label class="layui-form-label"><font class="bixuanFont">*</font>商品规格</label>
	    <div class="layui-input-block">
	      <input type="text" id="guige" name="guige" lay-verify="title" value="${goods.guige}" autocomplete="off" placeholder="请输入商品规格" class="layui-input">
	    	<span style="color: #888;">每个规格之间使用英文逗号","隔开，至少要有一个规格</span>
	    </div>
	  </div>
	   <div class="layui-form-item">
	    <div class="layui-inline">
	      <label class="layui-form-label"><font class="bixuanFont">*</font>商品库存</label>
	      <div class="layui-input-inline" style="width: 100px;">
	        <input type="text" id="stock" name="stock" oninput="numInput(this)" value="${goods.stock}" placeholder="件" autocomplete="off" class="layui-input">
	      </div>
	    </div>
	  </div>
	  <div class="layui-form-item" style="height: 260px;">
	    <div class="layui-inline">
	      <label class="layui-form-label"><font class="bixuanFont">*</font>商品主图</label>
	      <div class="layui-input-inline" style="width: 100px;">
	        <div id="file">
	    		<a id="delImg1" class="delImg" href='javascript:;' type="button" onclick="deleteImg(1)">
	    			<i class="layui-icon layui-icon-delete" style="font-size:22px;color: #fff;"></i>
	    		</a>
				<span id="span1" class="span" style="font-size:22px;">
					<img src='${goods.firstImg}' width='150px' height='150px'>
				</span>
				<input id="firstImg1" type="hidden" name="firstImg" value="${goods.firstImg}">
				<input id="myFile1" class="myFile" type="file" name="myFile" onchange="uploadImg('myFile1',1)" />
			</div>
			 <div id="file" style="left:180px;">
			 	<c:if test="${not empty img2 }">
					<a id="delImg2" class="delImg" href='javascript:;' type="button" onclick="deleteImg(2)">
	    				<i class="layui-icon layui-icon-delete" style="font-size:22px;"></i>
	    			</a>
				</c:if>
				<c:if test="${empty img2 }">
					<a id="delImg2" class="delImg" href='javascript:;' type="button" onclick="deleteImg(2)" style="display:none;">
	    				<i class="layui-icon layui-icon-delete" style="font-size:22px;"></i>
	    			</a>
				</c:if>
				<span id="span2" class="span">
					<c:if test="${not empty img2 }">
						<img src='${img2}' width='150px' height='150px'>
					</c:if>
					<c:if test="${empty img2 }">
					+
					</c:if>
				</span>
				<input id="firstImg2" type="hidden" name="images" value="${img2}">
				<input id="myFile2" class="myFile" type="file" name="myFile" onchange="uploadImg('myFile2',2)" />
			</div>
			 <div id="file" style="left:360px;">
	    		<c:if test="${not empty img3 }">
					<a id="delImg3" class="delImg" href='javascript:;' type="button" onclick="deleteImg(3)">
	    				<i class="layui-icon layui-icon-delete" style="font-size:22px;"></i>
	    			</a>
				</c:if>
				<c:if test="${empty img3 }">
					<a id="delImg3" class="delImg" href='javascript:;' type="button" onclick="deleteImg(3)" style="display:none;">
	    				<i class="layui-icon layui-icon-delete" style="font-size:22px;"></i>
	    			</a>
				</c:if>
				<span id="span3" class="span">
					<c:if test="${not empty img3 }">
						<img src='${img3}' width='150px' height='150px'>
					</c:if>
					<c:if test="${empty img3 }">
					+
					</c:if>			
				</span>
				<input id="firstImg3" type="hidden" name="images" value="${img3}">
				<input id="myFile3" class="myFile" type="file" name="myFile" onchange="uploadImg('myFile3',3)" />
			</div>
			 <div id="file" style="left:540px;">
	    		<c:if test="${not empty img4 }">
					<a id="delImg4" class="delImg" href='javascript:;' type="button" onclick="deleteImg(4)">
	    				<i class="layui-icon layui-icon-delete" style="font-size:22px;"></i>
	    			</a>
				</c:if>
				<c:if test="${empty img4 }">
					<a id="delImg4" class="delImg" href='javascript:;' type="button" onclick="deleteImg(4)" style="display:none;">
	    				<i class="layui-icon layui-icon-delete" style="font-size:22px;"></i>
	    			</a>
				</c:if>
				<span id="span4" class="span">
					<c:if test="${not empty img4 }">
						<img src='${img4}' width='150px' height='150px'>
					</c:if>
					<c:if test="${empty img4 }">
					+
					</c:if>			
				</span>
				<input id="firstImg4" type="hidden" name="images" value="${img4}">
				<input id="myFile4" class="myFile" type="file" name="myFile" onchange="uploadImg('myFile4',4)" />
			</div>
			 <div id="file" style="left:720px;">
	    		<c:if test="${not empty img5 }">
					<a id="delImg5" class="delImg" href='javascript:;' type="button" onclick="deleteImg(5)">
	    				<i class="layui-icon layui-icon-delete" style="font-size:22px;"></i>
	    			</a>
				</c:if>
				<c:if test="${empty img5 }">
					<a id="delImg5" class="delImg" href='javascript:;' type="button" onclick="deleteImg(5)" style="display:none;">
	    				<i class="layui-icon layui-icon-delete" style="font-size:22px;"></i>
	    			</a>
				</c:if>
				<span id="span5" class="span">
					<c:if test="${not empty img5 }">
						<img src='${img5}' width='150px' height='150px'>
					</c:if>
					<c:if test="${empty img5 }">
					+
					</c:if>			
				</span>
				<input id="firstImg5" type="hidden" name="images" value="${img5}">
				<input id="myFile5" class="myFile" type="file" name="myFile" onchange="uploadImg('myFile5',5)" />
			</div>
			<div style="position:absolute;top:200px;width:200px;color:#888;">点击图片即可修改</div>
	      </div>
	    </div>
	  </div>
	  
	  <!-- LayUI的富文本编辑器 -->
	  <!-- <div class="layui-form-item layui-form-text">
	    <label class="layui-form-label"><font class="bixuanFont">*</font>商品详情</label>
	    <div class="layui-input-block">
	      <textarea class="layui-textarea layui-hide" name="details" lay-verify="content" id="details"></textarea>
	    </div>
	  </div> -->
	    <!-- 百度富文本编辑器 -->
	   <div class="layui-form-item layui-form-text">
	    <label class="layui-form-label"><font class="bixuanFont">*</font>商品详情</label>
	    <div class="layui-input-block"  style="width: 1200px;">
	    	 <textarea id="container" style="height: 100%;width: 100%;" name="container" type="text/plain">${goods.details}</textarea>
	    </div>
	  </div> 
	  
	  <div class="layui-form-item">
	    <label class="layui-form-label">是否上架</label>
	    <div class="layui-input-block">
	      <c:if test="${goods.status==1 }">
	        <input type="radio" name="status" value="1" title="是" checked>
	      	<input type="radio" name="status" value="2" title="否">
	      </c:if>
	      <c:if test="${goods.status==2 }">
	        <input type="radio" name="status" value="1" title="是">
	      	<input type="radio" name="status" value="2" title="否" checked>
	      </c:if>
	     
	    </div>
	  </div>
	  
	   <div class="layui-form-item">
	    <label class="layui-form-label">商品推荐</label>
	    <div class="layui-input-block">
	    	<c:if test="${goods.tuijian==1}">
		      <input type="radio" name="tuijian" value="1" title="推荐" checked>
		      <input type="radio" name="tuijian" value="2" title="不推荐">
	    	</c:if>
	    	<c:if test="${goods.tuijian==2}">
	    	<input type="radio" name="tuijian" value="1" title="推荐">
		      <input type="radio" name="tuijian" value="2" title="不推荐" checked>
	    	</c:if>
	    </div>
	  </div>
	  
	  <div class="layui-form-item">
	    <div class="layui-input-block">
	      <button class="layui-btn layui-btn-normal" lay-submit="" lay-filter="demo1">修改商品</button>
	    </div>
	  </div>
	</form>
  </body>
</html>
