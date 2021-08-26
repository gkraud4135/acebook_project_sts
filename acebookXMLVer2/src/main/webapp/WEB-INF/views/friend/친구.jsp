<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
<style>
/*  a:link { color: white; text-decoration: none;} */
  a:hover { color:B2CCCE; text-decoration: none;}
  a:link { color:B2CCCE; text-decoration: none;}
  a { color: skyblue; }
  
.main{
float:left;
}
.jb-wrap {
            width: 200px;
            margin: 10px auto;
            border: none;
            float:left;
            margin-left : 20px;
         }
         .jb-wrap img {
            width: 500px;
            height:150px;
            vertical-align: middle;
         }
         .jb-text {
            padding: 5px 10px;
            width: 200px;
            background-color:#F2F3F5;
            text-align: center;
            border-radius : 3px;
            
         }

.container {
  display: flex;
  flex-wrap:nowrap;
  background: white;
  width: 100%;
  height: 100%;
  padding : auto;
  
}

#leftmenu{

padding : 10;
  height: 2000px;
  width: 20%;
  order: 1;
  background: #F2F3F5;

}

#friendlistmenu{

padding : 10;
  height: 800px;
  width: 100%;
  order: 2;


}



.items {
font-size : 18px;
   margin-left : 7px;
   margin-right : 7px;
  /* margin-top : 8px; */
  text-align: left;
  background: #F2F3F5;
  width : 270px;
  height : 60px;
  border : none;
  border-radius : 10px;
  
}

.items:hover{
/* background: #DDD5F0; */
background: skyblue;
}


</style>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>


<div class="container">

<div id="leftmenu">
<h3>친구</h3>
<button class="items" type="button" onclick="친구요청()">받은친구요청</button>
<button class="items" type="button" onclick="요청한친구()">요청한친구목록</button>
<button class="items" type="button" onclick="나이추천()">나이가 같은 사람</button>
<button class="items" type="button" onclick="학교추천()">학교가 같은 사람</button>
<button class="items" type="button" onclick="모든친구()">모든친구</button>
<button class="items" type="button" onclick="location.href=''">생일</button>
</div>

<div id="friendlistmenu">

</div>

</div>




</body>
</html>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script>

function 추천목록(){
   
}

function 친구요청(){
   $.ajax({url:"requests",
      type:"POST",
      contentType:"application/json; charset=utf-8",//서버에서제이슨으로보내고
      dataType:"text",//서버에서받아오고 text html png 등으로 받을수도있음
      success:function(result){
   $("#friendlistmenu").html(result);
      }});

}


function 모든친구(){
   $.ajax({url:"myfriends",
      type:"POST",
      contentType:"application/json; charset=utf-8",//서버에서제이슨으로보내고
      dataType:"text",//서버에서받아오고 text html png 등으로 받을수도있음
      success:function(result){
   $("#friendlistmenu").html(result);
      }});

}

function 요청한친구(){
   $.ajax({url:"requested",
      type:"POST",
      contentType:"application/json; charset=utf-8",//서버에서제이슨으로보내고
      dataType:"text",//서버에서받아오고 text html png 등으로 받을수도있음
      success:function(result){
   $("#friendlistmenu").html(result);
      }});

}

function 나이추천(){ //나이가같은사람추천
   $.ajax({url:"commendage",
      type:"POST",
      contentType:"application/json; charset=utf-8",//서버에서제이슨으로보내고
      dataType:"text",//서버에서받아오고 text html png 등으로 받을수도있음
      success:function(result){
   $("#friendlistmenu").html(result);
      }});

}

function 학교추천(){ //학교가같은사람추천
   $.ajax({url:"commendschool",
      type:"POST",
      contentType:"application/json; charset=utf-8",//서버에서제이슨으로보내고
      dataType:"text",//서버에서받아오고 text html png 등으로 받을수도있음
      success:function(result){
   $("#friendlistmenu").html(result);
      }});

}
 
function move(sn){
	parent.person(sn);
}

   


</script>