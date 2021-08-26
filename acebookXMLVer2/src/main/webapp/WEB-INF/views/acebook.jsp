<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="com.lec206.acebook.vo_member.Member"%>
<%-- <%@ page import="" %> --%>
<%
   HttpSession session= request.getSession(false);
	if(session!=null&& session.getAttribute("sn")==null){session=null;}
%>
<%Member member = (Member)request.getAttribute("member"); %>

<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">

<!-- css , font, jq  -->
<style>
*{
    margin: 0 auto;
    padding: 0;
    font-size: 1rem;
    color: gray;
}
body{ background-color: #F0F2F5;}
#nav{
	background-color: white;
  width: 100%;
  display: flex;
  flex-flow: column nowrap;
  border: 0px solid green;
  margin:0px;
  height:auto;
  border-bottom:1px solid #E9EBEE;
  box-shadow: rgba(0, 0, 0, 0.15) 1.95px 1.95px 2.6px;
}
#con{
  width: 100%;
  display: flex;
  flex-wrap:nowrap;
}
#left{
padding : 10;
  height: 800px;
  width: 30%;
  order: 1;
}
#middle{
padding : 10;
  height: 800px;
  width: 100%;
  order: 2;
}
#right{
padding : 10;
  height: 800px;
  width: 30%;
  order: 3;
}
#personleft{
padding : 10;
  height: 800px;
  width: 20%;
  order: 1;
}
.button2{
background-color: LightGray;
  border: 1px;
  color: black;
  padding: 10px 60px;
  text-align: center;
  text-decoration: none;
  display: flex;
  font-size: 20px;
  margin: 4px 2px;
  cursor: pointer;
}
.button2:hover{
background-color: white;
}
.divbar{display:flex; width: 100%; height: 50px; margin:0px;}
.divbarindex{padding:3px; margin:0px;  padding-left:5%; padding-right:5%; width:150px;display:flex;}
.pointer{cursor: pointer;}
.barindexbottom{border-bottom:4px solid #1877F2;}
.divbarleft{left:0px; margin-left:5px; margin-right:5px; display:flex;}
.divbarright{right:0px; margin-right:5px;  display:flex;}
.divbarmiddle{display:flex; margin-left:255px; padding-top:3px;}
#search{width:70%; height:30px; margin-top:6%; margin-left:6%;background-color: #F0F2F5; border-radius: 50px;}
.searchholder{padding-left: 20px;}
#list{height: auto; padding: 10px; }
.searchbutton{margin-top:2%; width:200px; height:80%; background-color: #F0F2F5; border-radius: 50px;}
.searchicon{padding-top:6%;padding-left:5%; margin-left:3%;}
.navname{left:0px; padding-top: 20px; margin:0px;}
.navname1{left:0px; padding-top: 6px; margin:0px;}
.divmiddlebox{padding-left: 10px; margin:0px;}
.divrightbox{margin:0px; padding-top: 10px; display:flex;}
.profile{border-radius: 30px;}
.ipbox{display: flex;  width:50%; margin-top:20px; flex-flow: row wrap;}
.eachip{width:33%; flex-grow: 3; padding: 0px; border-radius: 50px;}
#ipdiv{position:absolute; padding-top:50px; padding:0px; visibility:hidden; top:35%; left:45%; width:auto; height:auto; background-color:white; border: 1px solid #E2E4E7;}
.mainbox{display: flex; margin-top:100px;}
#searchbar{padding-left:5px; position:absolute; visibility:hidden; width:18%; background-color: white; box-shadow: rgba(0, 0, 0, 0.15) 1.95px 1.95px 2.6px;}

.newmember{
background-color:MediumSeaGreen;
border:none;
color:white;
border-radius:3px;
height:35px;
width:150px;
margin-top:7px;
}
.newmember:hover{
background-color:seagreen;
}
.lgb{
background-color:dodgerblue;
border:none;
color:white;
border-radius:3px;
margin:3px;
font-size:17px;
width:70px;
height:27px;
margin-top:10px;
}
</style>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>

<meta charset="UTF-8">
<title>

AceBook
</title>
</head>

<body>
<div>



<div id="nav">
	<div id="searchbar">
	<i onclick="closesearch()" class="fa fa-arrow-left fa-2x pointer" aria-hidden="true"></i>
	<input type="text" id="search" placeholder="유저검색"/>
	<div id="list"></div>
	</div>
<div class="divbar">
<% if(session!=null){%>
<div class="divbarleft">
<img class="pointer" onclick="main(${member.sn})" src ="/resources/img/acebooklogo.png"  height="50" width="50"/>
<div class="pointer searchbutton" ><i class="fa fa-search fa-1x searchicon" aria-hidden="true"><a class="searchholder"><b>Acebook 검색</b></a></i>
</div>
<a class="navname">${member.name}님 접속 환영합니다.</a> 
<%-- <c:if test="${sessionScope.sn ne null}">
세션잇어요!
</c:if> --%>
</div>

<div class="divbarmiddle">
<div class="divbarindex pointer barindexbottom" id="index1" onclick="main(<%=session.getAttribute("sn")%>)"><i class="fa fa-home  fa-2x " aria-hidden="true" ></i></div>
<div class="divbarindex pointer" id="index2" onclick="friend(<%=session.getAttribute("sn")%>)"><i class="fa fa-users  fa-2x" aria-hidden="true" ></i></div>
<div class="divbarindex pointer" id="index3" onclick="person(<%=session.getAttribute("sn")%>)"><i class="fa fa-user  fa-2x" aria-hidden="true" ></i></div>
</div>
<%} else {%> 
<form onsubmit="로그인()" action="main" method="post">id <input type="text" name="id" id="id" /> password <input type="password" name="password" id="password" /><input type="submit" class="lgb" value="로그인" /></form>
<button onclick="open_layer(1,0)" class="newmember">새 계정 만들기</button>
<%} %>


<div class="divbarright">
<% if(session!=null){%>

<div class="divrightbox pointer" onclick="person(${member.sn})"><img class=" profile" src ="/board/attach/${member.profile}"  height="30" width="30"/><a class="navname1"><b>${member.name}</b></a></div>
<div class="divrightbox pointer"><button onclick="location.href='/logout'">로그아웃</button></div>
<%}%>  

</div>
</div>
</div>







<c:if test="${test eq true}">
<div id="con">
<div id="left">
<iframe src="/testplus" name="lframe" onload="Height();" frameborder="0" scrolling="no" style="overflow-x:hidden; overflow:auto; width:100%; height:100%;"></iframe>
</div>
<div id="middle">
<iframe src="/mainboard/<%=session.getAttribute("sn")%>" name="mframe" onload="Height();" frameborder="0" style="overflow-x:hidden; overflow:auto; width:100%; height:100%;"></iframe>
</div>
<div id="right">
<iframe src="/friendstate" name="rframe" onload="Height();" frameborder="0" style="overflow-x:hidden; overflow:auto; width:100%; height:100%;"></iframe>
</div>

</div>
</c:if>
	
	
   <c:if test="${not empty members}">
   <div>
    <div class="mainbox"><b>최근로그인기록</b></div>
  	<div class="ipbox">
      <c:forEach var="member" items="${members}">
		<div class="eachip pointer" onclick="iplogin('${member.id}','${member.name}',${member.profile})">
		<img src ="/board/attach/${member.profile}" height="200" width="200"/><br>
		<a><b>${member.name}</b></a><button type="button" onclick="iplogdelete(${member.sn})">삭제</button>
		</div>
      </c:forEach>
 	 </div>
 	</div>
   </c:if>
   
   <div id="ipdiv">
   <form action="main" method="post">
   <input type="hidden" name="id" id="ipid"/> 
   <img class=" profile"  id="ipprofile" height="150" width="200"/><br>
   <input type="text" id="ipname" readonly="readonly" /><br>
   <input type="password" id="ippassword" name="password" placeholder="비밀번호입력"/><br>
   <input type="submit" value="로그인" /><button type="button" onclick="closeiplogin()">취소</button>
   </form>
   </div>

<div id="divpop" style="position:absolute; top:100px;; left:400px; z-index:300; visibility:hidden;">
<table cellpadding="5" cellspacing="0" bgcolor="lightgrey" border="1px;" bordercolor="lightgrey">
<tr><td>
<iframe name="i_frame" src="#" width="600px" height="700px" marginwidth="0" marginheight="0" frameborder="no" scrolling="auto"></iframe>
</td></tr>

<tr>
<td align="right" height="1px;" bgcolor="lightgrey">
<button type="button" class="button2" onclick="close_layer();">취소</button>
</td></tr>
</table></div>



<input type="hidden" id="msg" value="${msg}">

</div>
</body>
</html>

<!-- javaScript -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script>
function open_layer(key,board){
	if(key==1){ var page = "/member";}
	if(key==2){ var page = "/openinputadd/"+board;}
	document.all['divpop'].style.visibility = "visible";
	document.i_frame.location.replace(page);
	}
function close_layer(){
	 document.all['divpop'].style.visibility = "hidden";
	}
function closelayer(){
	 document.all['divpop'].style.visibility = "hidden";
}
function 로그인(){
var id = document.querySelector("#id");
if(id.value==""){
	alert("아이디가 입력되지 않앗어요!");
	return false;
}
var password = document.querySelector("#password");
if(password.value==""){
	alert("비밀번호가 입력되지 않앗어요!");
	return false;
}
return true;
}
function iplogin(id,name,profile){
	document.all['ipdiv'].style.visibility = "visible";
	$('#ipid').val(id);
	$('#ipname').val(name);
	$("#ippassword").focus();
	jQuery('#ipprofile').attr("src","board/attach/"+profile);
}
function iplogdelete(sn){
	location.href="/iplogdelete/"+sn;
}
function closeiplogin(){
	$('#ippassword').val("");
	document.all['ipdiv'].style.visibility = "hidden";
}
function person(sn){
	document.mframe.location.replace("/personpage/"+sn);
	$('#left').attr('id', 'zero1');
	$('#right').attr('id', 'zero2');
	$('iframe[name=rframe]').hide();
	$('iframe[name=lframe]').hide();
	$('#index1').attr('class', 'divbarindex pointer');
	$('#index2').attr('class', 'divbarindex pointer');
	$('#index3').attr('class', 'divbarindex pointer barindexbottom');
}
function main(sn){
	document.lframe.location.replace("/testplus");
	document.mframe.location.replace("/mainboard/"+sn);
	$('#zero1').attr('id', 'left');
	$('#zero2').attr('id', 'right');
	$('#index1').attr('class', 'divbarindex pointer barindexbottom');
	$('#index2').attr('class', 'divbarindex pointer ');
	$('#index3').attr('class', 'divbarindex pointer');
	$('iframe[name=rframe]').show();
	$('iframe[name=lframe]').show();
}
function friend(sn){
	document.mframe.location.replace("/friend");
	$('#left').attr('id', 'zero1');
	$('#right').attr('id', 'zero2');
	$('iframe[name=rframe]').hide();
	$('iframe[name=lframe]').hide();
	$('#index1').attr('class', 'divbarindex pointer');
	$('#index2').attr('class', 'divbarindex pointer barindexbottom');
	$('#index3').attr('class', 'divbarindex pointer');
}
$(document).ready(function(){
	   var outHtml = '';
	    $('#search').keyup(function(){//무한검색
	        if ( $('#search').val().length > 0) {//length 로 글자수 지정해주기
	            var search = $(this).val();
	        
	            // ajax 실행
	            $.ajax({ 
	                type : 'POST',
	                url : '/selectuser',
	                data:{name: search},
	                success : function(data) { 
	                   $("#list").empty();
	                   $.each(data,function(index, item){
	                      $("#list").append('<div class="pointer" onclick = "search('+item.sn+')">'+'<img class="profile" src="/board/attach/'+item.profile+'" height="30" width="30"/>'+item.name+'</div>');
	                      /*alert(item.name);*/ });
	                },
	                 errer : function(data){
	                	console.log('SelectUserError');
	                } 
	            }); // end ajax
	        }
	    }); // end keyup
	}); 			
	
	
//아이프레임
function calcHeight() {
		var the_height = document.getElementsByName('iframe').contentWindow. document.body.scrollHeight;
		document.getElementById('iframe').height = the_height; 
		document.getElementById('iframe').style.overflow = "hidden"; }
	
$(".searchbutton").on('click', function(event){
	document.all['searchbar'].style.visibility = "visible";
	$("#search").focus();
});
function search(sn){
	person(sn);
	closesearch();
}
function closesearch(){
	document.all['searchbar'].style.visibility = "hidden";
	$('#list').empty();
	$('#search').val("");
}
window.onload = function(){
	if($('#msg').val() !=""){alert($('#msg').val());}
	}
</script>