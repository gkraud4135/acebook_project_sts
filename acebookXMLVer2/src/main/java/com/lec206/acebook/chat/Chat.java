package com.lec206.acebook.chat;

import java.util.List;

import com.lec206.acebook.vo_member.Member;

public class Chat {
	

	//참조변수
	private ChatRoom chatroom_sn;
	private List<Member> member_sn;
	
	public ChatRoom getChatroom_sn() {
		return chatroom_sn;
	}
	public void setChatroom_sn(ChatRoom chatroom_sn) {
		this.chatroom_sn = chatroom_sn;
	}
	public List<Member> getMember_sn() {
		return member_sn;
	}
	public void setMember_sn(List<Member> member_sn) {
		this.member_sn = member_sn;
	}


}
