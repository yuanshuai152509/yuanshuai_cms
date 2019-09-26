<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
<link rel="stylesheet" href="/resource/css/bootstrap.min.css">
<title>文章详情</title>
<script
	src="${pageContext.request.contextPath}/resource/js/jquery-3.2.1.js"></script>
</head>
	<!--      	为 CMS 项目扩展文章评论功能； -->
<body>
	<div class="container">
		 <div>
	<!-- 上下 -->
			<button class="btn btn-info" onclick="up()">上一篇</button>
			<button class="btn btn-info" onclick="down()">下一篇</button>
		</div>
		<dl>
			<dt>
				<h1 align="center">${article.title }</h1>
			</dt>
			<dd>${article.user.nickname}
				&nbsp;
				<fmt:formatDate value="${article.created }"
					pattern="yyyy-MM-dd HH:mm:ss" />
			</dd>
			<dd>
				<hr />
			</dd>
			<dd>${article.content }</dd>

		</dl>
		
		<hr>
		
		<br>
		<br>
		
		
		<!-- 从session获取对象 -->
		<c:if test="${sessionScope.user!=null }">
		
		 <h1>评论</h1>

	
	
<hr/>
   <div>
   <form action="" id="form1">
   <textarea rows="10" cols="155" name="content"></textarea>
   <input type="hidden" name="article.id" value="${article.id }">
    <button type="reset" class="btn btn-warning" style="float: right">重置</button>
    
    <input type="button" value="评论" onclick="addComment2()">
 
  </form>
  </div> 
  </c:if>
  
  <c:if test="${sessionScope.user==null }">
  	<h4 style="color: red">请登录后评论</h4>
  </c:if>
  		<hr/>
  <br>
  
	 	<div>
		  
		
			 <dl>
				<c:forEach items="${comments}" var="c">
					<dt>${c.user.nickname }&nbsp; ${c.created }</dt>
					<dd>${c.content }</dd>
					<hr>
				</c:forEach>
				
				
				${pages }
				
			</dl> 
		</div>


	</div>

</body>

<script type="text/javascript">
	//审核文章
	function addComment2() {
	
       var id ='${article.id}';//文章ID
		$.post("/article/comment",$("#form1").serialize(),function(flag){
			if(flag){
				alert("评论成功");
				location.reload();//重新加载页面
			}else{
				alert("评论失败.请检查是否登录");
			}
		})

	}
	$(function(){
		//翻页
		$(".page-link").click(function(){
			//获取点击的url
			var url =$(this).attr("data");
			//在中间区域加载url
			location.href=url+"&id="+'${article.id}';
			
		})
	})

	
	

	function up(){
		$.get("/article/checkPre",{id:'${article.id}',channelId:'${article.channelId}'},function(obj){
			if(obj){//如果有上一篇.
				location.href="/article/selectPre?id="+'${article.id}'+"&channelId="+'${article.channelId}'
	
			}else{
				alert("别点了.到头了!")
			}
		})
		
		
	} 
	
function down(){
	$.get("/article/checkNext",{id:'${article.id}',channelId:'${article.channelId}'},function(obj){
		if(obj){//如果有下一篇.
			location.href="/article/selectNext?id="+'${article.id}'+"&channelId="+'${article.channelId}'
	
		}else{
			alert("别点了.到头了!")
			}
		})
	}	
</script>
</html>