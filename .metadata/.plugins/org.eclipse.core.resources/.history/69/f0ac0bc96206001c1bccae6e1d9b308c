<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- <%@ page import="" %> --%>

<!DOCTYPE html>

<html>

<head>
<!-- css , font, jq  -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<meta charset="UTF-8">
<style type="text/css">
.profile{border-radius: 30px;}
.pointer{cursor: pointer;}
.box{border: 1px solid #E2E4E7;}
#layer01{background-color: white;}
</style>
<title></title>
</head>
<body>
<div><b>좋아요</b></div>
   <c:if test="${not empty boardlikes}">
   <ul>
      <c:forEach var="like" items="${boardlikes}">
		<li class="pointer box" onclick="move(${like.writer.sn})" ><img class="profile" src ="/board/attach/${like.writer.profile}" height="50" width="50"/><b>${like.writer.name}</b></li>
      </c:forEach>
      </ul>
   </c:if>
   
   <c:if test="${empty boardlikes}">
		좋아요가 없습니다.
   </c:if>
   



</body>
</html>
<script>
function move(sn){
	parent.person(sn);
}


	




</script>
<!-- javaScript -->