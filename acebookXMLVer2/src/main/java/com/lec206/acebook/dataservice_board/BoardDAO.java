package com.lec206.acebook.dataservice_board;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.lec206.acebook.chat.ChatRoom;
import com.lec206.acebook.chat.Chatting;
import com.lec206.acebook.util.BusinessResult;
import com.lec206.acebook.util.게시물상태;
import com.lec206.acebook.vo_board.Attach;
import com.lec206.acebook.vo_board.Board;
import com.lec206.acebook.vo_board.BoardLike;
import com.lec206.acebook.vo_board.Comment;
import com.lec206.acebook.vo_member.Member;

@Repository
public class BoardDAO implements IBoardDAO {
	
	@Autowired SqlSession mapper;

	@Override
	public void save(Board board) {
		
		int getsn = 0;
		getsn = board.getSn();
		mapper.insert("boardMapper.save", board);
		
		//공유 게시물 일때
		if(board.getState()==게시물상태.공유) {
			
			Map<String, Integer> map = new HashMap<String, Integer>();
			map.put("to", board.getSn());  //공유될 (바깥)
			map.put("get", getsn); //공유된 (안에있는)
			
			mapper.insert("boardMapper.savesharing", map);
		}
		
		//첨부파일 존재시
		if(board.getAttachFiles()!=null) {
			mapper.insert("boardMapper.saveAttachs", board.getAttachs());
		}

	}

	@Override
	public List<Board> selectAll(int sn) {
		
		return mapper.selectList("boardMapper.selectAll", sn);
	}

	@Override
	public List<Board> myboard(int sn, int size) {
		
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("sn", sn);  
		map.put("size", size); 
		
		List<Board> boards = mapper.selectList("boardMapper.myboard", map);
		
		return boards;
	}

	@Override
	public List<Board> mainboard(int sn, int size) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("sn", sn);  
		map.put("size", size); 

		return mapper.selectList("boardMapper.mainboard", map);
	}

	@Override
	public int likes(int no) {
		return mapper.selectOne("boardMapper.likes", no);
	}

	@Override
	public void onoffLike(int bno, int mno,int no){// 좋아요 실행,취소 여부
		
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("bno", bno);  //보드 번호
		map.put("mno", mno); //회원 번호
		
		if(no==1) {//no 1 일시 좋아요 취소
			mapper.insert("boardMapper.offlike", map);
		}
		else {//no 0일시 좋아요 실행
			mapper.insert("boardMapper.onlike", map);
		}

	}

	@Override
	public int likeis(int bno, int mno) {
		
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("bno", bno);  //보드 번호
		map.put("mno", mno); //회원 번호
		
		return mapper.selectOne("boardMapper.likeis",map);
	}
	
	@Override
	public Board findboard(int sn) { //게시물한개
		return mapper.selectOne("boardMapper.findboard", sn);
	}

	@Override
	public void savecomment(Comment comment) {
		mapper.insert("boardMapper.savecomment", comment);
		
	}

	@Override
	public List<Comment> findCommentBySn(int sn, int size) {
		
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("sn", sn);
		map.put("size", size);
		
		return mapper.selectList("boardMapper.findCommentBySn", map);
	}

	@Override
	public Comment findComment(int sn) {
		return mapper.selectOne("boardMapper.selectOneComment", sn);
	}

	@Override
	public int comments(int sn) {
		// TODO Auto-generated method stub
		return mapper.selectOne("boardMapper.comments", sn);
	}

	@Override
	public void deletecomment(int sn) {
		mapper.delete("boardMapper.deletecomment", sn);
	}

	@Override
	public int checknewboard(int sn) {
		return mapper.selectOne("boardMapper.checknewboard", sn);
	}
	
	@Override
	public int getsharingsn(int sn) { 
		return mapper.selectOne("boardMapper.getsharingsn",sn);
	}

	@Override
	public void deletesharing(int sn) {
		mapper.delete("boardMapper.deletesharing",sn);
	}
	
	@Override
	public void deleteboard(int sn) {
	}
	
	@Override
	public Attach Advertising() {
		return mapper.selectOne("boardMapper.advertising");
	}
	
	@Override
	public int numberofboard(int sn) {
		return mapper.selectOne("boardMapper.numberofboard",sn);
	}
	
	@Override
	public void viewtimeupdate(int sn) {
		
		mapper.update("boardMapper.viewtimeupdate", sn);
		mapper.update("boardMapper.lasttimeupdate", sn);
		
	}
	
	@Override
	public int findattachsn(int sn) {
		
		return mapper.selectOne("boardMapper.findattachsn",sn);
	}
	
	
	@Override
	public Attach findattach(int sn) {
		Attach attach = mapper.selectOne("boardMapper.findattach", sn);
		return attach;
	}
	
	@Override
	public void deleteAttach(int sn) { //첨부파일삭제
		mapper.delete("boardMapper.deleteAttach", sn);
	}
	
	@Override
	public List<Attach> load(int boardsn) {
		
		return mapper.selectList("boardMapper.loadAttach", boardsn);
	}

	@Override
	public void changeProfile(int profile, int sn) {
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("sn", sn);
		map.put("profile", profile);
		
		mapper.update("boardMapper.changeProfile", map);
	}

	@Override
	public void changeBackProfile(int profile, int sn) {
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("sn", sn);
		map.put("profile", profile);
		
		mapper.update("boardMapper.changeBackProfile", map);
		
	}
	
	@Override
    public List<Member> selectuser(String name) {
		
	    List<Member> members = mapper.selectList("boardMapper.selectuser",name);
        return members;
       
    }

	@Override
	public void reportboard(int sn) { 
		mapper.insert("boardMapper.reportboard",sn);
	}
	
	@Override
	public void modifyboard(Board board) { 
		
		mapper.update("boardMapper.modifyboard", board); //게시물수정
		if(board.getAttachFiles()!=null) {  
			mapper.insert("boardMapper.saveAttachs", board.getAttachs()); //첨부 파일 수정
		}
	}
	
	@Override
	public List<BoardLike> likelist(int sn) {
		
	    return mapper.selectList("boardMapper.likelist", sn);
	    
	 }

	@Override
	public void makechat(String id) {
		
		
		mapper.insert("boardMapper.makechat",id);
	}

	@Override
	public void joinchat(String chatroom_id, int member_sn) {
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("member_sn", member_sn);
		map.put("chatroom_id", chatroom_id);
		
		mapper.insert("boardMapper.joinchat",map);
	}
	
	@Override
	public String chatcheck(int sn1,int sn2){
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("sn1", sn1);
		map.put("sn2", sn2);
		
		return mapper.selectOne("boardMapper.chatcheck", map);
	}
	
	@Override
	public List<Chatting> chatting(String chatroom_id){
		
		return mapper.selectList("boardMapper.chatting", chatroom_id);
	}

	@Override
	public List<Integer> randomboard10(){
		
		return mapper.selectList("boardMapper.randomboard10");
		
	}
	@Override
	public List<Integer> randomonlike(int sn){
		
		return mapper.selectList("boardMapper.randomonlike",sn);
		
	}
	
	

	
}
