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

		var url = "/user/selects?username=" + $("[name='username']").val();
		//在中间区域加载url
		$("#content-wrapper").load(url);
	}
</script>
</head>
<body>
	<div class="form-group form-inline">
		用户名:<input type="text" class="form-control" name="username"
			value="${username }"> &nbsp;
		<button type="button" class="btn btn-warning" onclick="query()">查询</button>
	</div>


	<table class="table table-bordered">

		<tr>
			<td>序号</td>
			<td>用户名</td>
			<td>昵称</td>
			<td>生日</td>
			<td>注册时间</td>
			<td>用户状态</td>
		</tr>
		<c:forEach items="${users}" var="user" varStatus="i">

			<tr>
				<td>${i.index+1 }</td>
				<td>${user.username }</td>
				<td>${user.nickname }</td>
				<td><fmt:formatDate value="${user.birthday }"
						pattern="yyyy-MM-dd" /></td>
				<td><fmt:formatDate value="${user.created }"
						pattern="yyyy-MM-dd HH:mm:ss" /></td>
				<td>
				<c:if test="${user.locked==0 }">
				 <button type="button" class="btn btn-success" onclick="update(${user.id},this)">正常</button>
				
				</c:if>
				<c:if test="${user.locked==1 }">
				 <button type="button" class="btn btn-warning" onclick="update(${user.id},this)">禁用</button>
				
				
				</c:if>
				
				
				</td>
			</tr>


		</c:forEach>


	</table>

	<div>
		${pages }
	</div>
<script type="text/javascript" src="/resource/js/page.js">
</script>

<script type="text/javascript">
//改用用户状态
function  update(id,obj){
	//要更新的状态
	var locked =$(obj).text()=="正常"?1:0;
	$.post("/user/update",{id:id,locked:locked},function(flag){
		if(flag){
			//alert("操作成功");
			 $(obj).text($(obj).text()=="正常"?"禁用":"正常");
			 $(obj).attr("class",$(obj).text()=="正常"?"btn btn-success":"btn btn-warning")
			
		}else{
			alert("操作失败")
		}
	})
	
	
}


</script>


</body>
</html>