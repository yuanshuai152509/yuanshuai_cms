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
			<c:forEach items="${comments}" var="a">
				<dl>
					<dt>${a.article.title },&nbsp;${a.created }</dt>
					<dd>${a.content }</dd>
				</dl>

				<hr />
			</c:forEach>
		</ul>

		${pages }
	</div>


</body>
<script type="text/javascript">

//去修改
function toUpdate(id){
	$("#center").load("/article/update?id="+id)
	
	
}


//搜索
function query(){
	$("#center").load("/article/selectsByUser?title="+$("[name='title']").val());
}

function update(obj,id){
	var deleted = $.trim($(obj).text())=="删除"?"1":"0";
	
	$.post("/article/update",{id:id,deleted:deleted},function(flag){
		if(flag){
			//删除成功.改变删除按钮的内容及状态
			
			$(obj).text(deleted==1?"恢复":"删除");
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