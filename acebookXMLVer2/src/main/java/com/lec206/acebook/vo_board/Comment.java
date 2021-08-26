package com.lec206.acebook.vo_board;

import java.util.Date;

import com.lec206.acebook.vo_member.Member;

public class Comment {
	
	//원시변수
	int sn;
	String contents;
	Date wdate;
	
	//참조변수
	Member writer;
	Board board;
	
	//get,set
	public int getSn() {return sn;}
	public void setSn(int sn) {this.sn = sn;}
	
	public Member getWriter() {return writer;}
	public void setWriter(Member writer) {this.writer = writer;}
	
	public Board getBoard() {return board;}
	public void setBoard(Board board) {this.board = board;}
	
	public String getContents() {return contents;}
	public void setContents(String contents) {this.contents = contents;}
	
	public Date getWdate() {return wdate;}
	public void setWdate(Date wdate) {this.wdate = wdate;}

}
