<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- <%@ page import="" %> --%>
<%HttpSession session = request.getSession(false); %>
<!DOCTYPE html>

<html>

<head>
<!-- css , font, jq  -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<meta charset="UTF-8">
<title></title>
<style>
#wrap {background-color:white;border: 1px solid black;}
</style>
</head>
<body>
<form onsubmit="return 게시물등록하다()" action="/board" enctype="multipart/form-data" method="post">
공개범위<select name="state">
    <option value="공유" selected="selected">공유</option>
		</select><br>
내용<input type="text" name="contents"></input><br>
<input type="submit" value="등록"/>
<ul id=attachlist></ul>
<input type="hidden" name="writer.sn" value="<%=session.getAttribute("sn")%>"/>
<input type="hidden" name="sn" value="${board.sn}"/>
</form>
<br>
<button onclick="첨부요소추가하다()">첨부추가</button>
<br>
<div id="wrap">
<img src ="/board/attach/${board.writer.profile}" id="myimg" height="30" width="30"/>
${board.writer.name}<br>
내용 : ${board.contents}

   <c:if test="${not empty attach}">

      <ul>
      <c:forEach var="attach" items="${attach}" varStatus="status">
		<li id="attach${status.index}" ><img src ="/board/attach/${attach.sn}" height="200" width="200"/>
		</li>
      </c:forEach>
      </ul>
   </c:if>
</div>		
<br>



<input id="close" type="hidden" value="${message}"></input>
</body>
</html>

<script>
function 첨부파일삭제(sn,attach){
	alert(attach);
	 $.ajax({url:"/deleteattach/"+attach,
		 	success : function(data) {
		 	alert(data);
		 	$("#attach"+sn).remove();
			    },
			    error:function(){console.log("실패");},
		 type:"POST"
		 });
}
window.onload = function (){ 
	   
	let 종료 = document.querySelector("#close");
	
      if(종료.value=="close"){
    	  
    	parent.close_layer();
    	parent.move();
		
		
    	  
      }

}

function 첨부요소추가하다()
{
	var 첨부리스트=document.querySelector("#attachlist");

	if(첨부리스트.childNodes.length>0 && 

	    첨부리스트.childNodes[첨부리스트.childNodes.length-1].childNodes[0].value==""){
		return 0;
	}
	var li        = document.createElement("li");
	var fileInput = document.createElement("input");
	fileInput.type="file";
	fileInput.accept="image/png, image/jpeg";
	var btnDelete = document.createElement("input");
	btnDelete.type="button";
	btnDelete.value="삭제";
	btnDelete.containerLi=li;
	btnDelete.addEventListener("click",function(event){

		var li=event.target.containerLi;
		var ul=li.parentNode;
		ul.removeChild(li);		
	});
	li.appendChild(fileInput);
	li.appendChild(btnDelete);
	첨부리스트.appendChild(li);
}

function 게시물등록하다(){
	var 첨부리스트=document.querySelector("#attachlist");
	for(var idx=0; idx<첨부리스트.childNodes.length; idx++){
		var fileInput = 첨부리스트.childNodes[idx].childNodes[0];
		fileInput.name="attachFiles["+idx+"]";
	}	
	return true;	
}

</script>