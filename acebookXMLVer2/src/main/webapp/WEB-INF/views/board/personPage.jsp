<%@page import="com.lec206.acebook.vo_member.Member"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%HttpSession session= request.getSession(false); %>
<%Member member = (Member)request.getAttribute("member"); %>
<!DOCTYPE html>

<html>
<head>
<!-- css , font, jq  -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<meta charset="UTF-8">
<style type="text/css">
body{background-color:#F0F2F5;}
#box{width:55%; margin: 0 auto;}
.divbox{margin-left:0px; width:70%; height:auto;  background-color:white; margin-bottom: 2%; border: 1px solid #E2E4E7; border-radius: 5px;}
.divcotents{height:150px; overflow:auto; padding-left:10px; padding-right:10px;}
.profile{border-radius: 30px;}
.profile1{border-radius: 100%;}
.pointer{cursor: pointer;}
.contentstop{padding-top: 10px;padding-left: 10px; display:flex;}
.contentsname{padding-top: 10px;}
.boardsave{margin-left:0px; display:flex; width:69%; height:7%;  background-color:white; margin-bottom: 2%; padding-top:1%; padding-left:1%;  border: 1px solid #E2E4E7; border-radius: 5px;}
.rboardsave{width:88%; height:20%; margin: 0 auto; background-color:#F0F2F5; margin-left: 1%; margin-top: 1%; margin-bottom: 2%; padding:1%;   border: 1px solid #E2E4E7; border-radius: 15px;}
.conselect{right:1%; height:10%; margin-top:1%;}


#center{display: flex; flex-wrap:nowrap; width: 100%; padding-top:255px;}
#left{
padding : 10;
  height: 800px;
  width: 50%;
  order: 1;
  margin-right: 1px;
  display:flex;
}
#right{
padding : 10;
  height: 800px;
  width: 50%;
  order: 2;
  margin-left: 1px;
}
.contentleft{width:70%; margin-left:43%; display:flex;}
.ifriendstate{width:100%; border-radius: 5px; border: 1px solid #E2E4E7;}


.top{width:50%; padding-left: 30%;}
.backprofile{left:30%; width:45%; height:200px; position:absolute; border-radius: 20px;}
.myprofile{left:48%; width:10%; height:10%; top:170px; position:absolute; border: 5px solid white;}
.topback{height: 255px; background: linear-gradient(to bottom, gray, white); }
.friendbt{left:63%; top:200px;  position:absolute;}

.like{background-color: blue; font-weight:bold; color:white;}
#wrap {position:fixed; width:500px; height:300px; left:40%; top:40%; background-color:white;border: 1px solid black;}
#sharing {background-color:white;border: 1px solid black;}
/*친구추가 및 차단 css */ 
.btn { display:block; width:150px; height:30px; line-height:40px; border:1px #3399dd solid;; margin:15px auto; background-color:#66aaff; text-align:center; cursor: pointer; color:#333; transition:all 0.9s, color 0.3; } .btn:hover{color:#fff;}
.hover1:hover{ box-shadow:200px 0 0 0 rgba(0,0,0,0.5) inset; }
.hover2:hover{ box-shadow:200px 0 0 0 rgba(0,0,0,0.5) inset; }



</style>
<title><%=member.getName()+"페이지" %></title>
</head>
<body>

<!-- startcon -->

<%if(member.getSn() == (Integer)session.getAttribute("sn")) {%>
<div class="top">
<div><img class="pointer backprofile" onclick="open_layer(5,0)" src ="/board/attach/<%=member.getBackprofile()%>" id="myimg"/></div>
<div><img class="pointer profile1 myprofile" onclick="open_layer(2,0)" src ="/board/attach/<%=member.getProfile()%>" id="myimg" height="100" width="100"/></div>
</div>
<%}%>

<%if(member.getSn() != (Integer)session.getAttribute("sn")) {%>
<div class="top">
<div><img class="backprofile" src ="/board/attach/<%=member.getBackprofile()%>" id="myimg" height="100" width="100"/></div>
<div><img class="profile1 myprofile" src ="/board/attach/<%=member.getProfile()%>" id="myimg" height="100" width="100"/></div>
</div>

<!-- 영웅이 개인페이지 코드{ -->

<!-- 영웅이 개인페이지 코드 -->

<%}%>



<div class="topback">

<c:choose>

<c:when test="${request eq 0}" >
<button class="btn hover1 friendbt" onclick="친구신청()"><b>친구신청</b></button>
</c:when>

<c:when test="${request eq 1}">
<button class="btn hover1 friendbt" onclick="친구수락()"><b>친구수락</b></button>
</c:when>

<c:when test="${request eq 2}">
<button class="btn hover1 friendbt" onclick="">친구</button>
</c:when>

<c:when test="${request eq 4}">
<button class="btn hover1 friendbt" onclick="">신청중</button>
</c:when>

<c:otherwise>
<!-- <button class="btn hover1 friendbt" onclick="">ㅋㅋ</button> -->
</c:otherwise>
</c:choose>

<div id="center" class="contents">
<div id="left">
<div class="contentleft" ><iframe class="ifriendstate" src="/personstate/${member.sn}" name="lframe" onload="Height();" frameborder="0" style="overflow-x:hidden; overflow:auto; width:100%; height:26%;"></iframe></div>
</div>
<div id="right">

<%if(member.getSn() != (Integer)session.getAttribute("sn")) {%>
<input type="button" onclick="open_layer(8,0)" value="채팅">
<%}%>

<%if(member.getSn() == (Integer)session.getAttribute("sn")) {%>
<div class="boardsave" ><img class="profile pointer" onclick="person(<%=session.getAttribute("sn")%>)" src ="/board/attach/${member.profile}" id="myimg" height="40" width="40"/>
<div class="rboardsave pointer" onclick="open_layer(1,0)">${member.name}님, 오늘은 무슨 생각을 하고 계신가요?</div>
</div>
<%}%>

<div id="divpop" style="position:fixed; top:100; left:100px; z-index:300; visibility:hidden;">
<table cellpadding="5" cellspacing="0" bgcolor="#FFFFFF" border="5" bordercolor="#424242">
<tr><td>
<iframe name="i_frame" src="#" width="450" height="350" marginwidth="0" marginheight="0" frameborder="no" scrolling="auto"></iframe>
</td></tr>

<tr>
<td align="right" height="10" bgcolor="#424242">
<button type="button" id="closebt" onclick="close_layer();">닫기</button>
</td></tr>
</table></div>

<div id="list">

</div>
</div>
<div id="wrap" style="visibility:hidden;" width="400" height="100">



</div>
</div>
</div>
<!-- endcon -->
</body>
</html>

<script>
/* 영웅이코드{  */
function 친구신청(){
	$.ajax({
		  url: '/request/'+<%=member.getSn()%>,
		  success: function (data) {
		    console.log(data),
		    alert("신청완료"),
		    window.location.reload()
		  },
		})
	
}

function 친구수락(){
	$.ajax({
		  url: '/accept/'+<%=member.getSn()%>,
		  success: function (data) {
		    console.log(data),
		    alert("친구수락완료"),
		    window.location.reload()
		  },
		})
	
}

function 친구차단(){
	$.ajax({
		  url: '/request/'+<%=member.getSn()%>,
		  success: function (data) {
		    console.log(data),
		    alert("신청완료"),
		    window.location.reload()
		  },
		})
	
}

/* 영웅이코드} */


function 알림(sn,board){
	var content = "";
	if(sn==1){content = "수정하시겠습니까?";}
	if(sn==2){content = "삭제하시겠습니까?";}
	
	document.all['wrap'].style.visibility = "visible";
	$("#wrap").empty();
	$("#wrap").prepend(content);
	$("#wrap").append("<button type=\"button\" onclick=\"게시물상태("+sn+","+board+")\">확인</button>");
	$("#wrap").append("<button type=\"button\" onclick=\"closelayer()\">취소</button>");
}

function 게시물상태(sn,board){
	if(sn==1){open_layer(4,board);closelayer();}
	if(sn==2){게시물삭제(board);}

}

function closelayer(){
	document.all['wrap'].style.visibility = "hidden";
}

function open_layer(key,board){
	document.all['closebt'].style.visibility = "visible";
	if(key==1){ var page = "/board";}
	if(key==2){ var page ="/profile";}
	if(key==3){ var page ="/eachboard/"+board;}
	if(key==4){ var page ="/modifyboard/"+board;}
	if(key==5){ var page ="/backprofile";}
	if(key==7){ var page = "/likepeople/"+board;}
	if(key==8){ var page = "/chatroom/"+<%=session.getAttribute("sn")%>+"/"+<%=member.getSn()%>;
				document.all['closebt'].style.visibility = "hidden";
				}
	
	document.all['divpop'].style.visibility = "visible";
	document.i_frame.location.replace(page);
	}
function close_layer(){
	document.all['closebt'].style.visibility = "hidden";
	 document.all['divpop'].style.visibility = "hidden";
	}
function init() {
	  var getval = $('tag name[name=i_frame]').contents().find('#close').val();
	  alert(getval);
	 }
	
window.onload = function (){
	
	게시물로드(3);

}

var size =-3;
function 게시물로드(plus){
	size = size +parseInt(plus);
		var value={size:size};
		 $.ajax({url: "/profile/${sn}",
	            success : function(data) {
                	$("#list").append('<div id="box">');
                	$.each(data,function(index, item){
                		$("#list").append('<div class="divbox" id=\"boxnum'+item.sn+'\">'
                				+"<div class=\"contentstop\" style=\"list-style: none;\"><a class=\"pointer\" onclick=\"person("+item.writer.sn+")\"><img class=\"profile\" src =\"/board/attach/"+item.writer.profile+"\" id=\"myimg\" height=\"40\" width=\"40\"/></a>"+
                				"<a class=\"pointer contentsname\" onclick = \"person("+item.writer.sn+")\">&nbsp<b>"+
                				item.writer.name+"</b></a>"+
                				"&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;"+
	                			"<select class=\"conselect\" onchange=\"알림(this.value,"+item.sn+")\">\r\n" + 
	                			"<option value=\"none\" disabled selected> 선택 </option>\r\n" + 
	                			"<option value=\"1\">수정</option>\r\n" + 
	                			"<option value=\"2\">삭제</option>\r\n" + 
	                			"</select></div>"
	                			+"<div class=\"divcotents\">내용"+item.contents+"<div id=photo"+item.sn+"></div>"
	                			+" 날짜"+item.wdate
	                			+"<div id=\"sharing"+item.sn+"\"></div>"
	                			+"박스번호: box"+(size+index)+" 게시물번호:"+item.sn+"</div><div id=box"+item.sn+">"+"</div>"+"<div id=like"+item.sn+">"+"</div>"+"<input type=\"button\" onclick=\"댓글("+item.sn+","+<%=member.getProfile()%>+",0)\" id=\"comments"+item.sn+"\" value=\"댓글\" /><div id=comment"+item.sn+" class=\"comment\">"+"</div><div id=photo"+item.sn+">"+"</div>"+'</div>');
                		좋아요갯수(item.sn);
                		좋아요여부(item.sn,<%=session.getAttribute("sn")%>);
                  		사진출력(item.sn);
                		if(item.state=="공유"){공유글(item.sn);}
                	});													
                	$("#list").append('</div>');
	            },
		    	error:function(result){
			     alert("Error404"+result); 
			    },
	         type:"POST",
	         data :value,
	        });}

function 좋아요(like,board,writer){
	var likes={"like":like,"writer":{sn:writer},"board":{sn:board}};
	 $.ajax({url:"/like",  
	     success:function(result,status){
	    	 console.log(result),
	    	 좋아요갯수(board);
	    	 좋아요여부(board,writer);
	     	},
	    	 error:function(){
	    	 console.log("실패하였습니다"); 
	    		},
        type:"POST",
        contentType:"application/json; charset=utf-8",
        data :JSON.stringify(likes)
       });
}

function 좋아요전환(){
	var likes=$('#likes').val();
	console.log("like값="+likes);
	if(likes==1){
		$('#likes').val(0);
		$("#like").removeClass("like");
		console.log("like=1일때");
		}
	else{
		$('#likes').val(1);
		$("#like").addClass("like");
		
		console.log("like=0일때");
		}
	좋아요($('#likes').val());
}

function 좋아요여부(board,writer){
	var likes={"writer":{sn:writer},"board":{sn:board}};
	
	 $.ajax({url:"/likeis",  
	     success:function(result,status){
	    	 console.log(result),
	    	 $("#like"+board).html(result),
	    	 console.log("성공"); 
	     	},
	    	 error:function(){
	    	 console.log("실패"); 
	    		},
        type:"POST",
        contentType:"application/json; charset=utf-8",
        data :JSON.stringify(likes)
       });
	
}

function 좋아요갯수(sn){
	
	 $.ajax({url:"/like/"+sn,
	     success:function(result,status){
	    	 console.log(result),
	    	 $("#box"+sn).html(result),
	    	 console.log("등록되었습니다"); 

	     	},
	    	 error:function(){
	    	 console.log("실패"); 
	    		},
       type:"POST"
      });
}


function 댓글(board,profile,commentsize){

	var or = $("#comments"+board).val(); 
	if(or=="댓글"){
		댓글출력(board,profile,commentsize);
	}
	else{
		$("#comments"+board).val("댓글");
		$("#comment"+board).empty();
	}
}

function 댓글출력(board,profile,commentsize){
	commentsize = commentsize +3;
	 $.ajax({url:"/comment/"+board,
		    data : {"profile":profile,"commentsize":commentsize},
    success : function(data) {
    $("#comment"+board).html(data),
    $("#comments"+board).val("댓글닫기");
	    },
	    error:function(){console.log("실패");},
    type:"POST"
    });
}

function 댓글달기(board){
	var comment={contents:$("#commentcontent"+board).val(), "writer":{sn:<%=session.getAttribute("sn")%>},"board":{sn:board}};
	console.log(comment);
	 $.ajax({url:"/comment",  
	     success:function(result,status){
	    	 $("#commentcontent"+board).val("");
	    	 $("#plus"+board).after(result);
	    	 좋아요갯수(board);
	     	},
	    	 error:function(){
	    	 console.log("실패하였습니다"); 
	    		},
       type:"POST",
       contentType:"application/json; charset=utf-8",
       data :JSON.stringify(comment)
      });
	
	
}

function 댓글삭제(no,board){
	 $.ajax({url:"/deletecom/"+no,
 	success : function(data) {
 	alert(data);
 	$("#eachcomment"+no).remove();
 	좋아요갯수(board);
	    },
	    error:function(){console.log("실패");},
 type:"POST"
 });
	
	

}


function 사진출력(sn){
	
	 $.ajax({url:"/photo/"+sn,
		  	success : function(data) {
		  		$("#photo"+sn).html(data);
			    },
			error:function(){console.log("실패");},
		  type:"POST"
		  });


}
function 게시물삭제(board){
	 $.ajax({url:"/deleteboard/"+board,
		  	success : function(data) {
			alert(data);
		  	location.reload();
			    },
			    error:function(){console.log("실패");},
		  type:"POST"
		  });}

function 공유글(board){
	

	 $.ajax({url:"/sharing/"+board,
		  	success : function(data) {
		  	$("#sharing"+board).html(data);
			    },
			    error:function(){console.log("실패");},
		  type:"POST"
		  });
	
}

	
function person(sn){
	parent.person(sn);
}


window.addEventListener('scroll', () => { 
	let scrollLocation = document.documentElement.scrollTop; // 현재 스크롤바 위치
	let windowHeight = window.innerHeight; // 스크린 창
	let fullHeight = document.body.scrollHeight; //  margin 값은 포함 x

	if(scrollLocation + windowHeight + 300 >= fullHeight){
		게시물로드(3);
		
	}
})

<!--이부분 07 13 추가  -->
</script>