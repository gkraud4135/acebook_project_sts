package com.lec206.acebook.vo_board;

import com.lec206.acebook.vo_member.Member;

public class BoardLike {
	
	//원시변수
	int like;
	int report;
	
	//참조변수
	Member writer;
	Board board;
	
	//get,set
	public Member getWriter() {return writer;}
	public void setWriter(Member writer) {this.writer = writer;}
	
	public Board getBoard() {return board;}
	public void setBoard(Board board) {this.board = board;}
	
	public int getLike() {return like;}
	public void setLike(int like) {this.like = like;}
	
	public int getReport() {return report;}
	public void setReport(int report) {this.report = report;}
	
}
