package com.lec206.acebook.manage_board;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lec206.acebook.util.BusinessResult;
import com.lec206.acebook.vo_board.Board;
import com.lec206.acebook.vo_board.Comment;

public interface IBoardManage {

	//-----------------------------------게시물-----------------------------------------------
	//게시물 저장
	BusinessResult insertBoard(Board board);
	
	//회원검색
	BusinessResult outputMember(String name);
	
	//개인 게시물 출력
	BusinessResult personBoard(int sn,int size);
	
	//전체 랜덤 게시물 출력
	BusinessResult randomboard(int sn);
	
	//메인 게시물 출력
	BusinessResult mainboard(int sn,int size);
	
	//좋아요 갯수 출력
	BusinessResult likeCount(int sn);
	
	//댓글 갯수 출력
	BusinessResult commentCount(int sn);
	
	//좋아요 여부 출력
	BusinessResult likeor(int bno, int mno);
	
	//좋아요 실행
	BusinessResult likeProcess(int bno, int mno,int no);
	
	//게시물 검색
	BusinessResult findboard(int sn);
	
	//첨부파일 출력
	BusinessResult getAttachs(int boardsn);
	
	//게시물 번호로 공유된 게시물 가져오기
	BusinessResult sharing(int sn);
	
	//게시물번호로 공유번호 가져오기
	BusinessResult 공유첨부파일(int sn);
	
	//게시물 신고
	BusinessResult reportboard(int sn);
	
	//게시물 수정
	BusinessResult modifyboard(Board board);
	
	//게시물 삭제
	BusinessResult deleteboard(int sn);
	
	//광고글 출력
	BusinessResult Advertising();
	
	//본게시물 체크
	BusinessResult numberofboard(int sn);
	
	//새로운 게시물 갯수 확인
	BusinessResult checknewboard(int sn);
	
	//시청 시간 조정
	BusinessResult viewtimeupdate(int sn);
	
	//댓글 삭제
	BusinessResult deleteCommentService(int sn);
	
	//댓글 등록 후 출력
	BusinessResult insertCommentService(Comment comment);
	
	//댓글 출력
	BusinessResult outputCommentService(int sn,int size);
	
	//좋아요 누른 사람
	BusinessResult checkLikeServcie(int sn);

	//-----------------------------------첨부파일-----------------------------------------------
	//채팅방 생성
	BusinessResult makechat(String id);
	
	//채팅방 서버 생성
	//BusinessResult chatroom(String id);
	
	//채팅방 참가
	BusinessResult joinchat(String chatroom_id, int member_sn);
	
	//채팅방 확인
	BusinessResult chatcheck(int sn1, int sn2);
	
	//채팅 내역 출력
	BusinessResult chatting(String id);
	
	//-----------------------------------첨부파일-----------------------------------------------
	//첨부파일 저장
	BusinessResult insertAttachsServcie(Board board, String id, String path);
	
	//첨부파일 삭제
	BusinessResult deleteAttach(int sn,HttpServletRequest req);
	
	//첨부파일 로드
	void 사진출력(int 첨부번호,HttpServletRequest req, HttpServletResponse rep);
	
	//--------------------------------testcode---------------------------
	//랜덤 게시물 10개
	BusinessResult randomboard10();

	//좋아요 누르지 않은 게시물
	BusinessResult randomonlike(int sn);
	
	

}
