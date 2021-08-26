<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.lec206.acebook.vo_member.Member"%>
<%-- <%@ page import="" %> --%>
<%HttpSession session= request.getSession(false); %>
<%Member member2 = (Member)request.getAttribute("member"); %>

<!DOCTYPE html>

<html>

<head>
<!-- css , font, jq  -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<meta charset="UTF-8">
<title></title>
<style type="text/css">
body{background-color:white;}
</style>
</head>
<body>
<h1>소개</h1>

이름 : ${member.name}<br>
<!-- 회원의 주소정보 출력 -->
주소 :
<c:choose>
<c:when test="${member.post eq null}">
등록된 주소가 없어요
</c:when>
<c:otherwise>
${member.post.address}
</c:otherwise>
</c:choose>
<br>

<!-- 회원의 학교정보 출력 대학교 -> 초등학교 순으로 등록되잇는 정보중에 하나만 출력, 3항연산자 안에 3항연산자를 넣어서 처리해보았음 -->
학교 : 
<c:choose>
<c:when test="${member.school eq null}">
등록된 학교가 없어요!
</c:when>
<c:otherwise>
${member.school.university eq null ? member.school.high eq null ? member.school.middle eq null ? member.school.row : member.school.middle : member.school.high : member.school.university}
</c:otherwise>
</c:choose>
<br>

출신지 : 
<c:choose>
<c:when test="${member.post.birthplace eq null}">
등록된 출신지가 없어요!
</c:when>
<c:otherwise>
${member.post.birthplace}
</c:otherwise>
</c:choose>
<br>
<%if(member2.getSn() == (Integer)session.getAttribute("sn")) {%>
<button onclick=상세정보변경()>상세정보변경</button><!--이부분 07 13 추가  -->
<%}%>
</body>
</html>
<script>
function 화면갱신(){
	   location.reload();   
	}

function 상세정보변경(){
	   window.open("/change"," ","width=550,height=620")
	}
</script>
<!-- javaScript -->