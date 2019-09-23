$(function(){
	//为导航栏添加点击事件
	$(".page-link").click(function(){
		//获取点击的url
		var url =$(this).attr("data");
		//在中间区域加载url
		$("#content-wrapper").load(url);
		
	})
	
	
	
})
