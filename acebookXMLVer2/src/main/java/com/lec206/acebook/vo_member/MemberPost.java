package com.lec206.acebook.vo_member;

import com.lec206.acebook.util.BIRTH;

public class MemberPost {

	//원시변수
	String post;
	String address;
	String addresstail;
	BIRTH birthplace;
	
	//참조변수
	Member sn;
	
	//생성자[필요할경우 추가로 기입]
	
	//getter , setter
	public String getPost() {return post;}
	public void setPost(String post) {this.post = post;}

	public String getAddress() {return address;}
	public void setAddress(String address) {this.address = address;}

	public String getAddresstail() {return addresstail;}
	public void setAddresstail(String addresstail) {this.addresstail = addresstail;}

	public Member getSn() {return sn;}
	public void setSn(Member sn) {this.sn = sn;}
	
	public BIRTH getBirthplace() {return birthplace;}
	public void setBirthplace(BIRTH birthplace) {this.birthplace = birthplace;}

}