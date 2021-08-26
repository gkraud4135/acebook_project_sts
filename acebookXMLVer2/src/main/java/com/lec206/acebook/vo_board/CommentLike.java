package com.lec206.acebook.vo_board;

import com.lec206.acebook.vo_member.Member;

public class CommentLike {
	
	//원시변수
	int like;
	int report;
	
	//참조변수
	Comment comment;
	Member writer;
	
	//get,set
	public Comment getComment() {return comment;}
	public void setComment(Comment comment) {this.comment = comment;}
	
	public Member getWriter() {return writer;}
	public void setWriter(Member writer) {this.writer = writer;}
	
	public int getLike() {return like;}
	public void setLike(int like) {this.like = like;}
	
	public int getReport() {return report;}
	public void setReport(int report) {this.report = report;}
	
}