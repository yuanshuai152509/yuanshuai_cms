<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="/resource/jquery-3.2.1.js"></script>
</head>
<body>

<style type="text/css">
input{
	width:400px;
	height:70px;
	font-size:36px;
	font-weight:900;
	}
</style>

<script type="text/javascript" language="javascript">
     var sid;
	 var items=new Array("王硕","程远扬","李坤明","袁帅","闫宁","冯亚旭","郭星阳","文彦超","许楠","张帅康","王加鑫","徐佳","于佳康","赵威","张霜霜","郭恒飞","袁恒","张智","于博剑","杨欣宇","衣豪雄","云佳赫","李星德","赵帅玺","张洋","黄兵","张紫沿","张潮冰","王飞","赵泽昆");
	 
	function begin(){
		show();
		clearInterval(sid);
	 sid=setInterval("show()",50);
	 
		}
		
	function show(){
		
		var list=document.getElementById("name1");
		var index=parseInt(Math.random()*items.length);
		//alert(items[index]);
		
		$("#name1").text(items[index]);
		}
		
	function end(){
		clearInterval(sid);
		}
</script>

</head>

<body  style="text-align:center">
<input type="button" value="开始点名" onclick="begin()"   />
<div>
     <div>
	  <span id="name1" >aaa</span>
	 
	 </div
	<img src="img/0.jpg" style="width:396px;height:400px"  id="img1"/>
</div>
<input type="button" value="停止" onclick="end()"  />
</body>
</html>