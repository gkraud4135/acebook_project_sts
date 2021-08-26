<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">

#change{

margin-left:70px;
margin-top:35px;
}
.change{
background-color:skyblue;
border:none;
border-radius:3px;
display:flex;
cursor: pointer;
height:70px;
width:300px;
font-size:25px;
padding-left:30px;
padding:20px;
}
.change:hover{
background-color:dodgerblue;

}

</style>
</head>
<body>
<div id="change">
<button class="change" type="button" onclick="location.href='changeschool'">학교변경하기</button><br>
<button class="change" type="button" onclick="location.href='changepost'">주소변경하기</button>
</div>
</body>
</html>