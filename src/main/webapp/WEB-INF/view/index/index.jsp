<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
<title>CMS系统</title>
</head>
<body>
	<jsp:include page="/WEB-INF/view/common/top.jsp"></jsp:include>

	<div>
		<br />
	</div>
	<div class="container">
		<div class="row">
			<!-- 栏目-->
			<div class="col-md-2 ">
				<ul class="list-group">
					<li class="list-group-item  text-center" id="channel"><a
						class="channel" href="/">热门</a></li>
					<c:forEach items="${channels }" var="c">
						<li class="list-group-item text-center" id="channel${c.id }"><a
							href="/?channelId=${c.id }" class="channel">${c.name }</a></li>
					</c:forEach>
				</ul>

			</div>
			<!-- 中间内容主体区 -->
			<div class="col-md-7 split min_h_500">
			
			    <!--当用户不点击栏目时,默认显示 热点新闻.  即: 轮播图+热点 -->
			  
				<c:if test="${article.channelId==null }">
				<div>
					<!-- 轮播图 -->
					<div class="bd-example">
						<div id="carouselExampleCaptions" class="carousel slide"
							data-ride="carousel">
							<ol class="carousel-indicators">
								<li data-target="#carouselExampleCaptions" data-slide-to="0"
									class="active"></li>
								<li data-target="#carouselExampleCaptions" data-slide-to="1"></li>
								<li data-target="#carouselExampleCaptions" data-slide-to="2"></li>
							</ol>
							<div class="carousel-inner">
							
							   <c:forEach items="${slides }" var="s" varStatus="i">
								<div class="carousel-item ${i.index==0?"active":""} ">
									<img src="/pic/${s.url }" class="d-block w-100" alt="...">
									<div class="carousel-caption d-none d-md-block">
										<h5>${s.title }</h5>
									</div>
								</div>
								</c:forEach>
							</div>
							<a class="carousel-control-prev" href="#carouselExampleCaptions"
								role="button" data-slide="prev"> <span
								class="carousel-control-prev-icon" aria-hidden="true"></span> <span
								class="sr-only">Previous</span>
							</a> <a class="carousel-control-next" href="#carouselExampleCaptions"
								role="button" data-slide="next"> <span
								class="carousel-control-next-icon" aria-hidden="true"></span> <span
								class="sr-only">Next</span>
							</a>
						</div>
					</div>
					<br />
					<!-- 热门文章 -->
					<c:forEach items="${hotArticles}" var="h">
						<ul class="list-unstyled">
							<li class="media"><img src="/pic/${h.picture }" class="mr-3"
								alt="..." style="height: 124px; width: 190px">
								<div class="media-body">
									<h5 class="mt-0 mb-1"><a href="/article/selectByUser?id=${h.id }" target="_blank">${h.title }</a></h5>
									${h.user.nickname } &nbsp;
									<fmt:formatDate value="${h.updated }"
										pattern="yyyy-MM-dd HH:mm:ss" />
								</div></li>
							<hr />
					</c:forEach>
				${pages }
				</div>
                 </c:if>

				<!-- 如果栏目不为空则显示栏目下的分类 -->
				<c:if test="${article.channelId!=null}">
				<div>
                    <!-- 分类 -->
					<div id="category">
						<ul class="nav">
							<li class="nav-item" id="cate"><a class="nav-link" href="/?channelId=${article.channelId }">全部</a>
								</li>
							<c:forEach items="${categorys }" var="c">
								<li class="nav-item" id="cate${c.id}"><a class="nav-link"
								 href="/?channelId=${article.channelId }&categoryId=${c.id}">${c.name }</a>
								</li>
							</c:forEach>

						</ul>
					</div>
					<!-- 栏目下的所有文章 -->
					<div>
					 
						<c:forEach items="${articles}" var="h">
						<ul class="list-unstyled">
							<li class="media"><img src="/pic/${h.picture }" class="mr-3"
								alt="..." style="height: 124px; width: 190px">
								<div class="media-body">
									<h5 class="mt-0 mb-1"><a href="/article/selectByUser?id=${h.id }" target="_blank">${h.title }</a></h5>
									${h.user.nickname } &nbsp;
									<fmt:formatDate value="${h.updated }"
										pattern="yyyy-MM-dd HH:mm:ss" />
								</div></li>
							<hr />
						
					</c:forEach>
						${pages }
					</div>
					</div>
				</c:if>
			</div>
			<!-- 最新文章 -->
			<div class="col-md-3">
				<div class="card" style="width: 18rem;">
					<div class="card-body">
						<h3 class="card-title">最新文章</h3>
						<ul>
						<c:forEach items="${lastArticles }" var="l">
						<li>
							<h6><a href="/article/selectByUser?id=${l.id }" target="_blank">${l.title }</a></h6>
					  </li>
						</c:forEach>
						</ul>
					</div>
				</div>

			</div>

		</div>


	</div>

	</div>



	<br />

	<jsp:include page="/WEB-INF/view/common/footer.jsp" />
	<script type="text/javascript">
		//为左侧频道绑定点击事件
		$(function() {
			

			//为栏目添加高亮的样式
			$("#channel${article.channelId}").addClass(
					'list-group-item-warning');
			$("#cate${article.categoryId}").addClass('list-group-item-warning');
			//分页的点击事件

			$('.page-link').click(function(e) {

				//获取点击的的url
				var url = $(this).attr('data');
				// console.log(url);
				var channelId='${article.channelId}';
				var categoryId='${article.categoryId}'
				//在中间区域显示地址的内容
				location.href = url+"&channelId="+channelId+"&categoryId="+categoryId
			});

		})
	</script>
</body>
</html>