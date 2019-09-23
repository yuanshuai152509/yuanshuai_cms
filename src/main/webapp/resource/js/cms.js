
//个人中心,左侧导航栏的点击事件
$(function(){
	$("ul li").click(function(){
		var url =$(this).children().attr('data');
		var li = $('ul li');
			//移除目前高亮的样式
		li.removeClass('list-group-item-warning');
		$(this).addClass("list-group-item list-group-item-warning");
		$("#center").load(url)
		
	})
	
	
})
//管理员中心为导航栏添加点击事件
$(function(){
	
	$(".channel").click(function(){
		//获取点击的url
		var url =$(this).attr("data");
		//在中间区域加载url
		$("#content-wrapper").load(url);
		
	})
	
	
	
})