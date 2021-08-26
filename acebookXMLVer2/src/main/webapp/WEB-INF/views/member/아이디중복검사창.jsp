<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>아이디 중복 검사</title>
</head>
<body>
<form action="id" method="post">
아이디<input type="text" name="id" id="id" value="${id}" /><input type="submit" value="중복검사"/>
</form>
<!--결과 출력  -->
	
 ${msg} 

 <c:if test="${use=='false'}">
	 <br> <button onclick="id사용()">사용</button>
  
   </c:if> 
<button onclick="self.close()">취소</button>
 
</body>
</html>



<script>
function id사용(){
	
	let texId=document.querySelector("#id");
	
	let id=texId.value;
	alert(id);
	opener.아이디를받다(id);
	close();
}
</script>

