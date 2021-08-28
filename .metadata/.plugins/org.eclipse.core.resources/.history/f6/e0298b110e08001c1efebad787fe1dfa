<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<style>

</style>

<script>
var webSocket;
var txtChatMessage;
var chatMessages;
var chatroomID="${chatroomId}";
window.onload = function (){
	//객체로 사용할 메세지
	txtChatMessage = document.querySelector("#chat_message");
	//출력용 
	chatMessages = document.querySelector("#chat_messages");
	webSocket = new WebSocket("ws://localhost:9001/chat");
	webSocket.onopen=function(){
		webSocket.send(JSON.stringify({chatroom_id:chatroomID, state:"ENTER",writer:{name:<%=session.getAttribute("sn")%>}}));
	};
	webSocket.onclose=function(){
	
	}
	webSocket.onmessage=function(result){		
		let gottenmessage = result.data;
		chatMessages.innerHTML = gottenmessage+ "<br>" +chatMessages.innerHTML  ;		
	}

}

function outfromroom(){
	webSocket.send(JSON.stringify({chatroom_id:chatroomID, state:"LEAVE",writer:{name:<%=session.getAttribute("sn")%>}}));
	//webSocket.close();
	parent.close_layer();
}
function sendmessage(){
	let message = txtChatMessage.value;
	txtChatMessage.value="";
	txtChatMessage.focus();
	
	webSocket.send(JSON.stringify({chatroom_id:chatroomID, state:"CHAT", contents:message ,writer:{name:<%=session.getAttribute("sn")%>}}))
}
</script>

<title>Insert title here</title>
</head>
<body>
<div>
<h1>채팅방</h1>
<br>
<button onclick="outfromroom()">나가다</button><br>
<input type="text" id="chat_message"/><button onclick="sendmessage()">보내기</button><br>
   

<p id="chat_messages">

   <c:if test="${not empty ch}">
      <c:forEach var="ch" items="${ch}" >
		"<img class="profile" src ="/board/attach/${ch.writer.profile}" height="25" width="25"/><b>${ch.writer.name}</b> : ${ch.contents}"<br>
      </c:forEach>
   </c:if>

</p>
</div>
</body>
</html>