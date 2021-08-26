package com.lec206.acebook.vo_member;

import java.util.Date;

import com.lec206.acebook.util.로그인상태;

public class Login {
	
	//원시변수
	로그인상태 state;
	Date loginTime;
	Date logoutTIme;
	Date viewTime;
	Date lastViewTime;

	//참조변수
	Member sn;
	
	//생성자[필요할경우 추가로 기입]
	
	//getter , setter
	public 로그인상태 getState() {return state;}
	public void setState(로그인상태 state) {this.state = state;}

	public Date getLoginTime() {return loginTime;}
	public void setLoginTime(Date loginTime) {this.loginTime = loginTime;}

	public Date getLogoutTIme() {return logoutTIme;}
	public void setLogoutTIme(Date logoutTIme) {this.logoutTIme = logoutTIme;}

	public Date getViewTime() {return viewTime;}
	public void setViewTime(Date viewTime) {this.viewTime = viewTime;}

	public Date getLastViewTime() {return lastViewTime;}

	public void setLastViewTime(Date lastViewTime) {this.lastViewTime = lastViewTime;}

	public Member getSn() {return sn;}
	public void setSn(Member sn) {this.sn = sn;}
	
}