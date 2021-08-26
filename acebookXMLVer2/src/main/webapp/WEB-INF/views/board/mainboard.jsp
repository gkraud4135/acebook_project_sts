<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@page import="com.lec206.acebook.vo_member.Member"%>
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
.divbox{width:65%; height:auto; margin: 0 auto; background-color:white; margin-bottom: 2%; border: 1px solid #E2E4E7; border-radius: 5px;}
.divcotents{height:150px; overflow:auto; padding-left:10px; padding-right:10px;}
.profile{border-radius: 30px;}
.pointer{cursor: pointer;}
.contentstop{padding-top: 10px;padding-left: 10px; display:flex;}
.contentsname{padding-top: 10px; padding-right: 30%;}
.boardsave{display:flex; width:64%; height:10%; margin: 0 auto; background-color:white; margin-bottom: 2%; padding-top:1%; padding-left:1%;  border: 1px solid #E2E4E7; border-radius: 5px;}
.rboardsave{width:88%; height:12%; margin: 0 auto; background-color:#F0F2F5; margin-left: 1%; margin-top: 1%; margin-bottom: 2%; padding:1%;   border: 1px solid #E2E4E7; border-radius: 15px;}
.conselect{right:1%; height:10%; margin-top:1%;}

.like{background-color: blue; font-weight:bold; color:white;}
#fixedbtn{position:fixed; left:50%; top:0px;background-color:yellow;}
#wrap {position:fixed; width:500px; height:300px; left:40%; top:40%; background-color:white;border: 1px solid black;}



</style>
<title>AceBook</title>
</head>
<body>
<div id="fixedbtn" onClick="window.location.reload();" style="visibility:hidden;" width="400" height="100"></div>
<div id="wrap" style="visibility:hidden;" width="400" height="100">
</div>
<br><br>
<button type="button" id="boardsave" class="layer"  style="visibility:hidden;">게시물등록창</button>

<div class="boardsave" ><img class="profile pointer" onclick="person(<%=session.getAttribute("sn")%>)" src ="/board/attach/${member.profile}" id="myimg" height="40" width="40"/>
<div class="rboardsave pointer" onclick="open_layer(1,0)">${member.name}님, 오늘은 무슨 생각을 하고 계신가요?</div>
</div>



<div id="divpop" style="position:fixed; top:100; left:400px; z-index:300; visibility:hidden;">
<table cellpadding="5" cellspacing="0" bgcolor="#FFFFFF" border="5" bordercolor="#424242">
<tr><td>
<iframe name="i_frame" src="#" width="450" height="350" marginwidth="0" marginheight="0" frameborder="no" scrolling="auto"></iframe>
</td></tr>

<tr>
<td align="right" height="10" bgcolor="#424242">
<button type="button" onclick="close_layer();">닫기</button>
</td></tr>
</table></div>
<div id="list">

</div>

</body>
</html>

<script>
setInterval(새게시물여부확인,3000);
function 알림(sn,board){
	var content = "";
	if(sn==1){content = "공유하시겠습니까?";}
	if(sn==2){content = "신고하시겠습니까?";}

	
	document.all['wrap'].style.visibility = "visible";
	$("#wrap").empty();
	$("#wrap").prepend(content);
	$("#wrap").append("<button type=\"button\" onclick=\"게시물상태("+sn+","+board+")\">확인</button>");
	$("#wrap").append("<button type=\"button\" onclick=\"closelayer()\">취소</button>");
}

function 게시물상태(sn,board){
	if(sn==1){open_layer(2,board);closelayer();}
	if(sn==2){게시물신고(board);}

}

function closelayer(){
	document.all['wrap'].style.visibility = "hidden";
}

function checknewboard(data){
	$("#fixedbtn").html("새게시물"+data+"개 확인");
	document.all['fixedbtn'].style.visibility = "visible";
	}  
  
  
function open_layer(key,board){
	if(key==1){ var page = "/board";}
	if(key==2){ var page = "/sharing/"+board;}
	if(key==7){ var page = "/likepeople/"+board;}
	document.all['divpop'].style.visibility = "visible";
	document.i_frame.location.replace(page);
	}
function close_layer(){
	 document.all['divpop'].style.visibility = "hidden";
	}
function init() {
	  var getval = $('tag name[name=i_frame]').contents().find('#close').val();
	  alert(getval);
	 }
var once =true;
var out = true;
window.onload = function (){
	
	if(once){게시물로드();once = false;}

}
function move(){
	parent.person(<%=session.getAttribute("sn")%>);
}
function person(sn){
	parent.person(sn);
}

var size =0;
function 게시물로드(){
			var value={size:size};

			 $.ajax({url: "/mainboard/${sn}",
		            success : function(data) {
	                	$("#list").append('<div id="box">');
	                	$.each(data,function(index, item){

	                		$("#list").append('<div class="divbox" id=\"divbox'+item.sn+'\">'+
	                				"<div class=\"contentstop\" style=\"list-style: none;\"><a class=\"pointer\" onclick=\"person("+item.writer.sn+")\"><img class=\"profile\" src =\"/board/attach/"+item.writer.profile+"\" id=\"myimg\" height=\"40\" width=\"40\"/></a>"+
	                				"<a class=\"pointer contentsname\" onclick = \"person("+item.writer.sn+")\">&nbsp<b>"+
	                				item.writer.name+"</b></a>"+
	                				"&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;"+
		                			"<select class=\"conselect\" onchange=\"알림(this.value,"+item.sn+")\">\r\n" + 
		                			"<option value=\"none\" disabled selected> 선택 </option>\r\n" + 
		                			"<option value=\"1\">공유</option>\r\n" + 
		                			"<option value=\"2\">신고</option>\r\n" + 
		                			"</select></div>"
		                			+"<div class=\"divcotents\">내용"+item.contents+"<div id=photo"+item.sn+"></div>"+"</div>"
	                				+item.wdate+"<br>박스번호: box"+size+" 게시물번호:"+item.sn+"<div id=box"+item.sn+">"+"</div>"+"<div id=like"+item.sn+">"+"</div>"+"<input type=\"button\" onclick=\"댓글("+item.sn+","+<%=member.getProfile()%>+",0)\" id=\"comments"+item.sn+"\" value=\"댓글\" /><div id=comment"+item.sn+">"+"</div><div id=photo"+item.sn+">"+"</div>"+'</div>');	                		
	                		좋아요갯수(item.sn);
	                		좋아요여부(item.sn,<%=session.getAttribute("sn")%>);
	                  		사진출력(item.sn);
	                		size = size + 1;
	                	});
	                	본게시물체크(size);
	                	광고글출력();
	                	$("#list").append('</div>');
	                	out = true;
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

function 새게시물여부확인(){
	
	 $.ajax({url:"/checknewboard/"+<%=session.getAttribute("sn")%>,
		  	success : function(data) {
		  		if(data>0){checknewboard(data);}
			    },
			error:function(){
				console.log("실패");
				},
		 	 type:"POST"
		 	 });


}

function 본게시물체크(size){

	 $.ajax({url: "/numberofboard/${sn}",
            success : function(data) {
            	$("#list").append(data);
            },
	    	error:function(result){
	    		console.log("실패");
		    },
            type:"POST",
            data :{size:size},
        });}

function 광고글출력(){
	
	var a = Math.floor(Math.random()*3) + 1;
	if(a==1){
		 $.ajax({url:"/Advertising",
			  	success : function(data) {
			  		$("#list").append(data);
				    },
				error:function(){
					console.log("실패");
					},
			    type:"POST"
			   });
	
	}
}

function 게시물신고(sn){
	 $.ajax({url:"/reportboard/"+sn,
		 	success : function(data) {
		 	$("#divbox"+sn).html(data);
		 	closelayer();
			    },
			error:function(){
				console.log("실패");
				},
 type:"POST"
 });
	
	

}

window.addEventListener('scroll', () => {
	let scrollLocation = document.documentElement.scrollTop; // 현재 스크롤바 위치
	let windowHeight = window.innerHeight; // 스크린 창
	let fullHeight = document.body.scrollHeight; //  margin 값은 포함 x

	if(scrollLocation + windowHeight + 300 >= fullHeight){
		if(out)게시물로드();
		out = false;
		
	}

	
})

</script>