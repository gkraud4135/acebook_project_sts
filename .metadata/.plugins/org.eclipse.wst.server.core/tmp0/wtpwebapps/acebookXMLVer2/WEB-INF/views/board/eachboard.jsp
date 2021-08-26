<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- <%@ page import="" %> --%>

<!DOCTYPE html>

<html>

<head>
<!-- css , font, jq  -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<meta charset="UTF-8">
<title></title>
</head>
<body>
	
   <c:if test="${not empty attach}">

      <ul>
      <c:forEach var="attach" items="${attach}">
		<li><img src ="/board/attach/${attach.sn}" height="200" width="200"/></li>
      </c:forEach>
      </ul>
   </c:if>
</body>
</html>

<!-- javaScript -->