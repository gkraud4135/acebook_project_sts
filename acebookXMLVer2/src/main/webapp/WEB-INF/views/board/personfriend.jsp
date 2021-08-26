<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- <%@ page import="" %> --%>

<!DOCTYPE html>

<html>

<head>
<!-- css , font, jq  -->
<!-- <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script> -->
<meta charset="UTF-8">
<title></title>
</head>
<body>
<c:choose>
<c:when test="${member.friends eq null}">
등록된 친구가 없습니다.
</c:when>
<c:otherwise>
<c:forEach var="friend" items="${member.friends}" varStatus="index">
 <img src ="/board/attach/${friend.friend_sn.profile}" height="30px" width="30px"/>${friend.friend_sn.name}
</c:forEach>
</c:otherwise>
</c:choose>

</body>
</html>

<!-- javaScript -->