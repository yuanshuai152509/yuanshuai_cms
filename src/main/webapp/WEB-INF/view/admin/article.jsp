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
     <dt><h1 align="center">${article.title }</h1></dt>
     <dd>${article.user.nickname} &nbsp;<fmt:formatDate value="${article.updated }" pattern="yyyy-MM-dd HH:mm:ss"/> </dd>
      <dd style="float: right;"><button type="button" class="btn btn-success" onclick="update(${article.id},1)" ${article.status==1?"disabled":"" }>通过</button> 
      <button type="button"  class="btn btn-warning" onclick="update(${article.id},-1)"  ${article.status==-1?"disabled":""}>驳回</button> 
      </dd>
     <dd><hr/></dd>
     <dd>${article.content }</dd>
    
    </dl>
  
  
  </div>

</body>

<script type="text/javascript">
//审核文章
function update(id,status){
	
	$.post("/article/update",{id:id,status:status},function(flag){
		if(flag){
			alert("操作成功!");
			window.close();//关闭当前页面
		}else{
			alert("操作失败!")
		}
	})
	
}

</script>
</html>