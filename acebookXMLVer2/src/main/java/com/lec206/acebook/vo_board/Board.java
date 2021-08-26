package com.lec206.acebook.vo_board;

import java.util.*;

import org.springframework.web.multipart.MultipartFile;

import com.lec206.acebook.util.게시물상태;
import com.lec206.acebook.vo_member.Member;


public class Board {

	//원시변수
	private int sn;
	private String contents;
	private String wdate;
	private 게시물상태 state;
	
	//참조변수
	private Member writer;
	
	//첨부그림파일
	private List<MultipartFile> attachFiles;
	private List<Attach> attachs;
	//해쉬태그저장
	private List<Hashtag> hashtags;
	private String tags;
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}
	
	//get,set
	public int getSn() {return sn;}
	public void setSn(int sn) {this.sn = sn;}
	
	public Member getWriter() {return writer;}
	public void setWriter(Member writer) {this.writer = writer;}
	
	public String getContents() {return contents;}
	public void setContents(String contents) {this.contents = contents;}
	
	public String getWdate() {return wdate;}
	public void setWdate(String wdate) {this.wdate = wdate;}
	

	public List<MultipartFile> getAttachFiles() {return attachFiles;}
	public void setAttachFiles(List<MultipartFile> attachFiles) {this.attachFiles = attachFiles;}
	
	public 게시물상태 getState() {return state;}
	public void setState(게시물상태 state) {this.state = state;}
	
	//첨부파일 get set 
	public List<Attach> getAttachs() {
		
		//{첨부된 파일이 있고, 첨부파일변수가 비어잇을경우
		if(attachFiles!=null&&attachs==null) {
			this.attachs = new ArrayList<Attach>();
			for(MultipartFile attachfile : attachFiles) {
				Attach attach = new Attach();
				
				attach.setFilename(attachfile.getOriginalFilename());
				attach.setFilesize((attachfile.getSize()));
				
				attach.setBoard(this);
				attachs.add(attach);
				
				}
			
			return attachs;
			//첨부파일이 잇을경우 종료}
			}
		
		return attachs;
	}
	
	public void setAttachs(List<Attach> attachs) {
		
		for(Attach attach:attachs) {
			
			//첨부사진에 현재의 게시물번호를 저장
			attach.setBoard(this);
			
			}
		
			this.attachs = attachs;
		}
	public List<Hashtag> getHashtags() {return hashtags;}
	
	public void setHashtags(List<Hashtag> hashtags) {
		
		for(Hashtag hashtag : hashtags) {
			
			hashtag.setBoard(this);
		}
		
		this.hashtags = hashtags;
	}
	public String getTags() {
		return tags;
	}
	public void setTags(String tags) {
		this.tags = tags;
	}

}