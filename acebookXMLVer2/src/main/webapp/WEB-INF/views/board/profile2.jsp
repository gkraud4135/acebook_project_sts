<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- <%@ page import="" %> --%>
<%HttpSession session = request.getSession(false); %>
<!DOCTYPE html>

<html>

<head>
<!-- css , font, jq  -->
<style>
allbody{padding: 400px;}
</style>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<meta charset="UTF-8">
<title></title>
</head>
<body>
<div class="allbody">
<h2>랜덤 회원 프로필 변경</h2>
<form onsubmit="프로필변경()" action="randomprofile" enctype="multipart/form-data" method="post">
<input type="file" name="attachFiles" id="myfile"/><br>
<input type="submit" value="등록"/>
<input type="button" onclick="location.href='testplus'" value="취소"/>
<input type="hidden" name="state" value="프로필"/>


</form>
<br>

</div>


</body>
</html>

<script>
function 프로필변경(){
	   //필수 입력확인
	   var profile=document.querySelector("#myfile");
	   if(profile.value==""){
	      alert("변경할 프로필을 선택해주세요")
	      return false;}
	   alert('완료');
	   return true;}
</script>
