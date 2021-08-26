<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false" %>
<%HttpSession session= request.getSession(false);%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<form onsubmit="return 프로필변경()" action="board" enctype="multipart/form-data"  method="post">
<label for="myfile">
<div style="background-color:yellow; width:90px;">이미지변경</div></label>
<br><input type="text" id="contents" name="contents" placeholder="설명"/>
<input type="file" title="changeimg" name="attachFiles" id="myfile" style="display:none"/><br>
<img src ="/board/attach/${profile}" id="myimg" height="200" width="200"/>
<input type="hidden" name="writer.sn" value="<%=session.getAttribute("sn")%>"/>
<input type="hidden" name="writer.name" value="<%=session.getAttribute("name")%>"/>
<input type="hidden" name="state" value="프로필"/>   <!-- 프로필변경임을 구분 -->
<input type="submit" id="submit" value="저장" />
</form>



<input id="close" type="hidden" value="${message}"></input>
</body>
</html>
<script>

window.onload = function (){ 
	   
	let 종료 = document.querySelector("#close");
	
      if(종료.value=="close"){
		
    	parent.document.location.reload();
		parent.close_layer();
    	  
      }

}
function 그림파일읽어출력하기(이벤트) {
    var 그림파일 = 이벤트.target.files[0]; 
      if (!그림파일.type.match('image.*')) { alert("욱! 그림이 아니예요!");      }
      var 파일리더 = new FileReader();
       파일리더.onload = function(한그림파일) {
    	  var myimg=document.getElementById("myimg");
          myimg.src=한그림파일.currentTarget.result;
      };     
      
        파일리더.readAsDataURL(그림파일);}

  document.getElementById('myfile').addEventListener('change', 그림파일읽어출력하기, false);

  function 프로필변경(){
	   //필수 입력확인
	   var profile=document.querySelector("#myfile");
	   if(profile.value==""){
	      alert("변경할 프로필을 선택해주세요")
	      return false;}
	   return true;}

</script>