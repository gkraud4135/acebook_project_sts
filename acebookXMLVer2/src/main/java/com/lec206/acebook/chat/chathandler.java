package com.lec206.acebook.chat;

import java.util.HashMap;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lec206.acebook.vo_member.Member;

@Repository
@Component
public class chathandler extends TextWebSocketHandler  {
	
	 @Autowired SqlSession mapper;
			  
	  private final static HashMap<String, ChatRoom> chatRooms = new HashMap<String, ChatRoom>();

	  @Override
	  protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		  String JsonText = message.getPayload();//Payload : 전송하고자 하는 실제 데이터 ex) Serialized Date 등
		  ObjectMapper objectMapper=new ObjectMapper(); 
		  Chatting getChatMessage =objectMapper.readValue(JsonText,Chatting.class);
		  
		  ChatRoom chatroom = new ChatRoom();
		  chatroom =chatroom(getChatMessage.getChatroom_id());
		  
		  Member member = mapper.selectOne("memberMapper.findBySimple", Integer.parseInt(getChatMessage.getWriter().getName()));
		  
		  String textMessageTosocket=member.getName();//우선 보낸사람 
		  if(getChatMessage.getState().equals("ENTER")) {
			  
			  chatroom.getSocatlist().add(session);
			  textMessageTosocket="<b>"+textMessageTosocket+"</b>님이 입장";
		  }
		  else if(getChatMessage.getState().equals("LEAVE")) {
			  textMessageTosocket=textMessageTosocket+"님이 방 나감";
		  }
		  else if(getChatMessage.getState().equals("CHAT")) {
			  
			  textMessageTosocket="<img class=profile src =/board/attach/"+member.getProfile()+" height=25 width=25/><b>"+member.getName()+"</b> : "+getChatMessage.getContents();
			  getChatMessage.getWriter().setSn((Integer.parseInt(getChatMessage.getWriter().getName())));
			  
			  mapper.insert("boardMapper.savechatting", getChatMessage);
		  }
		  
		  //{"data":"별명:잘살아라"}
		  TextMessage textMessage = new TextMessage(objectMapper.writeValueAsString(textMessageTosocket));
		  
		  //모든 방 접속자들에게 보냄
		  for(WebSocketSession webSocketSession : chatroom.getSocatlist()){
			  webSocketSession.sendMessage(textMessage);
	      } 
		  if(getChatMessage.getState().equals("LEAVE")) {
			  session.close();
			  chatroom.getSocatlist().remove(session);
		  }
		  
		  
	  }
	 
	  
	   private ChatRoom chatroom(String id) {
			
			if(chatRooms.get(id)==null) {
				
			ChatRoom chatroom = new ChatRoom();
			chatroom.setId(id);
			chatRooms.put(id, chatroom);
			
			}
			
			return chatRooms.get(id);
	   	   }


}
