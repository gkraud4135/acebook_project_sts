package com.lec206.acebook.dataservice_board;


import java.util.List;

import com.lec206.acebook.chat.ChatRoom;
import com.lec206.acebook.chat.Chatting;
import com.lec206.acebook.vo_board.Attach;
import com.lec206.acebook.vo_board.Board;
import com.lec206.acebook.vo_board.BoardLike;
import com.lec206.acebook.vo_board.Comment;
import com.lec206.acebook.vo_member.Member;


public interface IBoardDAO {
	
	
	//게시물 ---------------------------------------------------------
	
	//게시물 저장
	void save(Board nBoard);

	//전체공개 게시물출력
	List<Board> selectAll(int sn);
	
	//내 게시물출력
	List<Board> myboard(int sn,int size);
	
	//메인 게시물 출력
	List<Board> mainboard(int sn,int size);
	
	//게시물당 좋아요갯수 조회
	int likes(int no);
	
	//좋아요 실행,취소
	void onoffLike(int bno, int mno,int no);
	
	//좋아요 여부
	int likeis(int bno, int mno);
	
	//게시물 검색
	public Board findboard(int sn);
	
	//댓글 저장
	void savecomment(Comment comment);
	
	//댓글들 출력
	List<Comment> findCommentBySn(int sn,int size);
	
	//댓글 한개 출력
	Comment findComment(int sn);
	
	//게시물 댓글 갯수 조회
	int comments(int sn);
	
	//댓글 삭제
	void deletecomment(int sn);
	
	//새로운 게시물 갯수 확인
	int checknewboard(int sn);
	
	//유저검색
	List<Member> selectuser(String name);
	
	//게시물 번호로 공유번호 가져오기
	int getsharingsn(int sn) ;
	
	//게시물 신고하기
	void reportboard(int sn);
	
	//게시물 수정하기
	void modifyboard(Board board);
	
	//공유 삭제
	void deletesharing(int sn);
	
	//게시물삭제
	void deleteboard(int sn);
	
	//광고 게시물출력
	Attach Advertising();
	
	//본 게시물 확인
	int numberofboard(int sn);
	
	//게시물 시청 시간 조정
	void viewtimeupdate(int sn);
	
	//좋아요 누른 인원
	List<BoardLike> likelist(int sn);
	
	//파일-----------------------------------------------------------
	//첨부 파일 로드
	Attach findattach(int sn);
	
	//첨부파일 삭제
	void deleteAttach(int sn);
	
	//보드번호로 게시물 번호 찾기
	int findattachsn(int sn);
	
	//첨부파일들 찾기
	List<Attach> load(int boardsn);
	
	//프로필---------------------------------------------------------
	void changeProfile(int profile,int sn);
	void changeBackProfile(int profile,int sn);
	
	//채팅---------------------------------------------------------
	//채팅방 생성
	void makechat(String id);
	//채팅 참가
	void joinchat(String chatroom_id,int member_sn);
	//채팅방 확인
	String chatcheck(int sn1,int sn2);
	//채팅 내용 출력
	List<Chatting> chatting(String chatroom_id);
	
	//testcode----------------------------------
	//랜덤 게시물 10개
	List<Integer> randomboard10();
	
}