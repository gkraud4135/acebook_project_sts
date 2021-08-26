package com.lec206.acebook.vo_board;

public class Hashtag {
	
	//원시변수
	int sn;
	String hashName;
	
	//참조변수
	Board board;
	
	//get,set
	public int getSn() {return sn;}
	public void setSn(int sn) {this.sn = sn;}
	
	public Board getBoard() {return board;}
	public void setBoard(Board board) {this.board = board;}
	
	public String getHashName() {return hashName;}
	public void setHashName(String hashName) {this.hashName = hashName;}
	
}