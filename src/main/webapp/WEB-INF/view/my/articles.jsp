<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
<title>我的文章列表</title>

</head>
<body>
	<div class="container">
		<ul class="list-unstyled">
			<c:forEach items="${articles}" var="a">
				<li class="media form-group"><img src="/pic/${a.picture }" class="mr-3"
					alt="no pic" style="width: 120px; height: 80px">
					<div class="media-body">
						<h4 class="mt-0 mb-1"><a href="javascript:myOpen(${a.id })"> ${a.title }</a></h4>
						<h5 class="mt-0 mb-1">${a.user.nickname }&nbsp; <fmt:formatDate value="${a.updated }" pattern="yyyy-MM-dd HH:mm:ss"/> </h5>
					  <span style="float: right">
					   <c:if test="${a.deleted==0}">
					  <button type="button" class="btn btn-success" onclick="update(this,${a.id})">删除</button>
					  </c:if>
					     <c:if test="${a.deleted==1}">
					  <button type="button" class="btn btn-warning" onclick="update(this,${a.id})">恢复</button>
					  </c:if>
					  </span>
					  
					</div></li>

<hr/>
			</c:forEach>
		</ul>
		
		${pages }
	</div>


</body>
<script type="text/javascript">

function update(obj,id){
	var deleted = $.trim($(obj).text())=="删除"?"1":"0";
	
	$.post("/article/update",{id:id,deleted:deleted},function(flag){
		if(flag){
			//删除成功.改变删除按钮的内容及状态
			
			$(obj).text(deleted==1?"恢复":"删除");
		//	$(obj).attr('disabled',true);
			$(obj).attr('class',deleted==1?"btn btn-warning":"btn btn-success");
		}
	})
}


 function myOpen(id){
	 window.open("/article/selectByUser?id="+id,"_blank");
 }

 $(function(){
		//为导航栏添加点击事件
		$(".page-link").click(function(){
			//获取点击的url
			var url =$(this).attr("data");
			//在中间区域加载url
			$("#center").load(url);
			
		})
		
		
		
	})

</script>
</html>