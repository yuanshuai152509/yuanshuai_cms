<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>用户列表</title>

<script type="text/javascript">
	function query() {
		

		var url = "/article/selectsByAdmin?status=" + $("[name='status']").val();
		//在中间区域加载url
		$("#content-wrapper").load(url);
	}
	
	
	$(function(){
		//条件的下拉框选中
		$("[name='status']").val('${article.status}')
		
		
	})
</script>
</head>
<body>
	<div class="form-group form-inline">
		审核状态: <select name="status">
			<option value="0">待审</option>
			<option value="1">已审</option>
			<option value="-1">驳回</option>
			<option value="9">全部</option>

		</select> &nbsp;
		<button type="button" class="btn btn-warning" onclick="query()">查询</button>
	</div>


	<table class="table table-bordered">

		<tr>
			<td>序号</td>
			<td>标题</td>
			<td>作者</td>
			<td>状态</td>
			<td>发布时间</td>
			<td>点击量</td>
			<td>热门</td>
			<td>操作</td>
		</tr>
		<c:forEach items="${articles}" var="a" varStatus="i">

			<tr>
				<td>${i.index+1 }</td>
				<td>${a.title }</td>
				<td>${a.user.username }</td>
				<td>${a.status==0?"待审核":a.status==1?"已审核":"未通过" }</td>
				<td><fmt:formatDate value="${a.updated }" pattern="yyyy-MM-dd" /></td>
				<td>${a.hits }</td>
				<td>${a.hot==1?"是":"否"}</td>
				<td><a href="javascript:myOpen(${a.id })">文章详情</a></td>
			</tr>


		</c:forEach>


	</table>

	<div>${pages }</div>
	<script type="text/javascript" src="/resource/js/page.js">
</script>
<script type="text/javascript">
function myOpen(id){
	var url="/article/selectByAdmin?id="+id;
	//在新窗口打开
	window.open(url,"_blank");
	
}

</script>


</body>
</html>