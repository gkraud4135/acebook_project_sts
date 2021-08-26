package com.lec206.acebook.vo_member;

import java.sql.Date;
import java.util.List;

import com.lec206.acebook.util.GENDER;
import com.lec206.acebook.util.회원상태;
import com.lec206.acebook.vo_friend.Friend;


public class Member {
	
	//원시변수
		int sn;
		String id;
		String password;
		String name;
		Date age; // select year(now())-year(age)+1 as age from member where sn=?; = age값
		GENDER gender;
		String friendcount; // select count(*) from friend where 
		회원상태 state;
		Date rdate;
		int profile;
		int backprofile;
		String code;
		
		//참조변수
		MemberPost post;
		MemberSchool school;
		Login login;
		List<Friend> friends;
		
		//생성자[필요할경우 추가로 기입]
		
		//getter , setter
		public int getSn() {return sn;}
		public void setSn(int sn) {this.sn = sn;}
		
		public String getId() {return id;}
		public void setId(String id) {this.id = id;}
		
		public String getPassword() {return password;}
		public void setPassword(String password) {this.password = password;}
		
		public String getName() {return name;}
		public void setName(String name) {this.name = name;}
		
		public Date getAge() {return age;}
		public void setAge(Date age) {this.age = age;}
		
		public String getFriendcount() {return friendcount;}
		public void setFriendcount(String friendcount) {this.friendcount = friendcount;}
		
		public 회원상태 getState() {return state;}
		public void setState(회원상태 state) {this.state = state;}
		
		public Date getRdate() {return rdate;}
		public void setRdate(Date rdate) {this.rdate = rdate;}
		
		public GENDER getGender() {return gender;}
		public void setGender(GENDER gender) {this.gender = gender;}
		
		//참조 get set
		public MemberPost getPost() {return post;}
		public void setPost(MemberPost post) { post.setSn(this); this.post = post;}
		
		public MemberSchool getSchool() {return school;}
		public void setSchool(MemberSchool school) { school.setSn(this); this.school = school;}
		
		public Login getLogin() {return login;}
		public void setLogin(Login login) {login.setSn(this); this.login=login;}
		
		public int getProfile() {return profile;}
		public void setProfile(int profile) {this.profile = profile;}
		
		public int getBackprofile() {return backprofile;}
		public void setBackprofile(int backprofile) {this.backprofile = backprofile;}
		
		public List<Friend> getFriends() {return friends;}
		public void setFriends(List<Friend> friends) {this.friends = friends;}
		
		public String getCode() {return code;}
		public void setCode(String code) {this.code = code;}
		
	}
