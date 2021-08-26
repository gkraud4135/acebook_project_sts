package com.lec206.acebook.vo_board;


public class Attach {
	
	//원시변수
	int sn;
	String filename;	
	long filesize;
	
	//참조변수
	Board board;
	
	//get,set
	public int getSn() {return sn;}
	public void setSn(int sn) {this.sn = sn;}

	public Board getBoard() {return board;}
	public void setBoard(Board board) {this.board = board;}
	
	public String getFilename() {return filename;}
	public void setFilename(String filename) {this.filename = filename;}
	
	public long getFilesize() {return filesize;}
	public void setFilesize(long filesize) {this.filesize = filesize;}
	
	
}