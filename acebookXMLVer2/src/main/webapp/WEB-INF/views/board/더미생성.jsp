<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false" %>
<%-- <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> --%>
<%-- <%@ page import="" %> --%>

<!DOCTYPE html>

<html>

<head>
<style type="text/css">
.plus{padding:10px; border: 1px solid #E2E4E7; font-size:20px;}
</style>
<!-- css , font, jq  -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<meta charset="UTF-8">
<title></title>
</head>
<body>
<ul>
<li class="plus" onclick="s1()">유저50명 랜덤생성</li>
<li class="plus" onclick="s2()">게시물 랜덤생성</li>
<li class="plus" onclick="s3()">무작위 친구추가</li>
<li class="plus" onclick="s4()">모든유저 로그인</li>
<li class="plus" onclick="s5()">모든유저 로그아웃</li>
<li class="plus" onclick="s6()">친구 게시물 생성</li>
<li class="plus" onclick="s7()">프로필 랜덤 변경</li>
<li class="plus" onclick="s8()">랜덤 댓글 생성</li>
</ul>
</body>
</html>
<script>
function s1(){
	location.href='/testmember'; 
}
function s2(){
	location.href='/testboard'; 
}  
function s3(){  
	location.href='/testfriend'; 
}
function s4(){  
	location.href='/logintest'; 
}
function s5(){  
	location.href='/logouttest'; 
}
function s6(){  
	location.href='/testboard1'; 
}
function s7(){  
	location.href='/randomprofile'; 
}
function s8(){  
	location.href='/randomcomment'; 
	alert('완료');
}
</script>
<!-- javaScript -->