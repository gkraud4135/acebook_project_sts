package com.lec206.acebook.chat;

import java.util.ArrayList;

import org.springframework.web.socket.WebSocketSession;

public class ChatRoom {
	
	String id;
    String roomname;
    ArrayList<WebSocketSession> socatlist= new ArrayList<WebSocketSession>();
	

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
    public String getRoomname() {
		return roomname;
	}
	public void setRoomname(String roomname) {
		this.roomname = roomname;
	}
	public ArrayList<WebSocketSession> getSocatlist() {
		return socatlist;
	}
	public void setSocatlist(ArrayList<WebSocketSession> socatlist) {
		this.socatlist = socatlist;
	}


}
