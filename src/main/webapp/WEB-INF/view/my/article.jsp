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
<body>
	<div class="container">
		<dl>
			<dt>
				<h1 align="center">${article.title }</h1>
			</dt>
			<dd>${article.user.nickname}
				&nbsp;
				<fmt:formatDate value="${article.updated }"
					pattern="yyyy-MM-dd HH:mm:ss" />
			</dd>
			<dd>
				<hr />
			</dd>
			<dd>${article.content }</dd>

		</dl>
		<h1>评论</h1>
<hr/>
  <div>
   <form action="" id="form1">
   <textarea rows="10" cols="155" name="content"></textarea>
   <input type="hidden" name="article.id" value="${article.id }">
    <button type="reset" class="btn btn-warning" style="float: right">重置</button>
    
    <input type="button" value="评论" onclick="addComment2()">
   <!--  <button type="button" onclick="addComment2()">aa</button>
    <button type="button" class="btn btn-info" onclick="addComment2()" style="float: right">评论</button> -->
  </form>
  </div>
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
		alert(0)
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
</script>
</html>