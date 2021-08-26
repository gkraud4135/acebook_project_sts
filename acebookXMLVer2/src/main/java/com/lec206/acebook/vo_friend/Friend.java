package com.lec206.acebook.vo_friend;

import com.lec206.acebook.util.친구상태;
import com.lec206.acebook.vo_member.Member;

public class Friend {
	
	//원시변수
	친구상태 state; //상태 : 친구인지, 차단인지 등

	//생성자[필요할경우 추가로 기입]

	//참조변수
	Member my_sn; //나의 회원번호
	Member friend_sn; //친구 회원번호
	
	//getter , setter
	public 친구상태 getState() {return state;}
	public void setState(친구상태 state) {this.state = state;}
	
	public Member getMy_sn() {return my_sn;}
	public void setMy_sn(Member my_sn) {this.my_sn = my_sn;}
	
	public Member getFriend_sn() {return friend_sn;}
	public void setFriend_sn(Member friend_sn) {this.friend_sn = friend_sn;}

}