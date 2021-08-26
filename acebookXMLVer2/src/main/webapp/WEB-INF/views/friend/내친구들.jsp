<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false" %>
<!DOCTYPE html>
<html>
<head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<meta charset="UTF-8">

<title>내친구들의상태</title>
<style type="text/css">
.profile{border-radius: 30px;}
.pointer{cursor: pointer;}
body{background-color:#F0F2F5;}</style>
</head>
<body>

<div id="friendstate">

</div>

</body>
</html>

<script>

window.onload = function (){
	setInterval(모든친구,3000);
}


function 모든친구(){
	
	$.ajax({url:"friendstate",
		type:"POST",
		contentType:"application/json; charset=utf-8",//서버에서제이슨으로보내고
		dataType:"text",//서버에서받아오고 text html png 등으로 받을수도있음
		success:function(result){
	$("#friendstate").html(result);
		}});

}
		
function person(sn){
	parent.person(sn);
}
</script>