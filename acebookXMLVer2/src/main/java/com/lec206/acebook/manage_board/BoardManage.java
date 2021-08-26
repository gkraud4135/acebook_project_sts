package com.lec206.acebook.manage_board;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.lec206.acebook.chat.ChatRoom;
import com.lec206.acebook.chat.Chatting;
import com.lec206.acebook.dataservice_board.IBoardDAO;
import com.lec206.acebook.dataservice_member.MemberDAO;
import com.lec206.acebook.util.BusinessResult;
import com.lec206.acebook.util.ERRORCODE;
import com.lec206.acebook.util.게시물상태;
import com.lec206.acebook.vo_board.Attach;
import com.lec206.acebook.vo_board.Board;
import com.lec206.acebook.vo_board.BoardLike;
import com.lec206.acebook.vo_board.Comment;
import com.lec206.acebook.vo_member.Member;

@Service
public class BoardManage implements IBoardManage {
	
	@Autowired IBoardDAO boardDAO;	
	
	@Override
	@Transactional
	public BusinessResult insertBoard(Board board) {
		
    	//업무
    	
    	//1-1업무규칙검사(들어온 게시물 저장)
    	
    	//1-2업무실행
    
    	//DB이용여부(boardDAO.save())
    	boardDAO.save(board);
    	
    	if(board.getState()==게시물상태.프로필) {	//첨부파일번호, 회원번호
    		int sn = boardDAO.findattachsn(board.getSn());
    		boardDAO.changeProfile(sn,board.getWriter().getSn());
    	}
    	if(board.getState()==게시물상태.백프로필) {
    		int sn = boardDAO.findattachsn(board.getSn());
    		boardDAO.changeBackProfile(sn,board.getWriter().getSn());
    	}
    	
		
		return new BusinessResult();

	}
	
	@Override
	@Transactional
	public BusinessResult outputMember(String name) {
	      
	    //업무
	    
	    //1-1업무규칙검사
	   
	    //1-2업무실행
	    
	    //DB이용여부(memberDAO.selectuser);
	    List<Member> member = boardDAO.selectuser(name);
	    
	    return  new BusinessResult(member);

	     }
	
	@Override
	@Transactional
	public BusinessResult personBoard(int sn,int size) {
		
		//업무
		
		//1-1업무규칙검사
	
		//1-2업무실행

		//DB이용여부()
		List<Board> boards = boardDAO.myboard(sn,size);

		return new BusinessResult(boards);
	}
	
	@Override
	@Transactional
	public BusinessResult randomboard(int sn) {
		
		//업무
		
		//1-1업무규칙검사
	
		//1-2업무실행

		//DB이용여부()
		
		List<Board> boards = boardDAO.selectAll(sn);

		return new BusinessResult(boards);
	}
	
	@Override
	@Transactional
	public BusinessResult mainboard(int sn,int size) {
		
		//업무
		
		//1-1업무규칙검사
	
		//1-2업무실행

		//DB이용여부()
		
		List<Board> boards = boardDAO.mainboard(sn,size);

		return new BusinessResult(boards);
	}
	
	@Override
	@Transactional
	public BusinessResult likeCount(int sn) {
		//업무
		
		//1-1업무규칙검사
	
		//1-2업무실행

		//DB이용여부
		int likes = boardDAO.likes(sn);
		return new BusinessResult(likes);
	}
	
	@Override
	@Transactional
	public BusinessResult commentCount(int sn) {
		
		//업무
		
		//1-1업무규칙검사
		
		//1-2업무실행
		
		//DB이용여부
		int comments = boardDAO.comments(sn);
		return new BusinessResult(comments);
	}
	
	@Override
	@Transactional
	public BusinessResult likeor(int bno, int mno) {
		//업무
		
		//1-1업무규칙검사
	
		//1-2업무실행

		//DB이용여부
		int likeis = boardDAO.likeis(bno, mno);
		return new BusinessResult(likeis);
	}
	
	@Override
	@Transactional
	public BusinessResult likeProcess(int bno, int mno,int no) {
		//업무
		
		//1-1업무규칙검사
	
		//1-2업무실행

		//DB이용여부
		boardDAO.onoffLike(bno, mno, no);
		return new BusinessResult();
	}
	
	@Override
	@Transactional
	public BusinessResult findboard(int sn) {
		
		//업무
		
		//1-1업무규칙검사
		
		//1-2업무실행
		
		//DB이용여부()
		Board board  = boardDAO.findboard(sn);
		return new BusinessResult(board);
	}
	
	
	
	@Override
	@Transactional
	public BusinessResult getAttachs(int boardsn) {

		//DB이용여부(boardDAO.load)
		
		//업무
		List<Attach> attachs = null;
		
		//1-1업무규칙검사
		attachs = boardDAO.load(boardsn);
		
		if(attachs.size()!=0) {
			
			return new BusinessResult(attachs);
			
		}

		//1-2업무실행
		//첨부파일이 없을경우 그냥 리턴함
		return new BusinessResult(); 
		
	}
	
	@Override
	@Transactional
	public BusinessResult sharing(int sn) {
		//업무
		//1-1업무규칙검사
		//1-2업무실행
		//DB이용여부()
		int getsn = boardDAO.getsharingsn(sn);
		Board board  = boardDAO.findboard(getsn);
		return new BusinessResult(board);
	}
	
	@Override
	@Transactional
	public BusinessResult 공유첨부파일(int sn) {
		//업무
		//1-1업무규칙검사
		//1-2업무실행
		//DB이용여부()
		int getsn = boardDAO.getsharingsn(sn);
		return new BusinessResult(getsn);
	}
	
	@Override
	@Transactional
	public BusinessResult reportboard(int sn) {
		//업무
		//1-1업무규칙검사
		//1-2업무실행
		//DB이용여부()
		boardDAO.reportboard(sn);
		
		return new BusinessResult();
	}
	
	@Override
	@Transactional
	public BusinessResult modifyboard(Board board) {
		//업무
		//1-1업무규칙검사
		//1-2업무실행
		//DB이용여부()
		boardDAO.modifyboard(board);
		
		return new BusinessResult();
	}
	
	@Override
	@Transactional
	public BusinessResult deleteboard(int sn) {
		//업무
		//1-1업무규칙검사
		//1-2업무실행
		//DB이용여부()
		int getsn = 0;
		try {getsn = (int)boardDAO.getsharingsn(sn);}catch(Exception e){}
		if (getsn !=0) {boardDAO.deletesharing(sn);}
		boardDAO.deleteboard(sn);
		return new BusinessResult();
	}
	
	@Override
	@Transactional
	public BusinessResult Advertising() {
		//업무
		//1-1업무규칙검사
		//1-2업무실행
		//DB이용여부()
		
		if(boardDAO.Advertising()==null) {
			
			return new BusinessResult(ERRORCODE.NETWORK_ERROR,"광고가 없어요");
			
		}
		
		Attach 광고 = boardDAO.Advertising();
		
		return new BusinessResult(광고);
	}
	
	@Override
	@Transactional
	public BusinessResult numberofboard(int sn) {
		
		//업무
		
		//1-1업무규칙검사
		
		//1-2업무실행
		
		//DB이용여부()
		int 갯수 = boardDAO.numberofboard(sn);
		return new BusinessResult(갯수);
	}

	@Override
	@Transactional
	public BusinessResult checknewboard(int sn) {
		
		//업무
		
		//1-1업무규칙검사
		
		//1-2업무실행
		
		//DB이용여부()
		int 갯수 = boardDAO.checknewboard(sn);
		return new BusinessResult(갯수);
	}
	
	@Override
	@Transactional
	public BusinessResult viewtimeupdate(int sn) {
		
		//업무
		
		//1-1업무규칙검사
		
		//1-2업무실행
		
		//DB이용여부()
		boardDAO.viewtimeupdate(sn);
		return new BusinessResult();
	}

	@Override
	@Transactional
	public BusinessResult deleteCommentService(int sn) {
		
		//업무
		//1-1업무규칙검사
		
		//1-2업무실행
		
		//DB이용여부
		boardDAO.deletecomment(sn);
		return new BusinessResult();

	}
	
	@Override
	@Transactional
	public BusinessResult insertCommentService(Comment comment) {
		//업무
		//1-1업무규칙검사
		
		//1-2업무실행
		
		//DB이용여부
		boardDAO.savecomment(comment);//게시물등록후 pk 리턴
		Comment mycomment = boardDAO.findComment(comment.getSn());//pk로 해당 댓글 리턴
		return new BusinessResult(mycomment);
	}

	@Override
	@Transactional
	public BusinessResult outputCommentService(int sn,int size) {
		//업무
		
		//1-1업무규칙검사
		
		//1-2업무실행
		
		//DB이용여부
		List<Comment> commentlist = boardDAO.findCommentBySn(sn,size);
		return new BusinessResult(commentlist);
	}
	
	   @Override
	   @Transactional
	   public BusinessResult checkLikeServcie(int sn) {
	      
	     //업무
	      
	     //1-1업무규칙검사
	   
	     //1-2업무실행
	      
	     //DB이용여부(memberDAO.selectall);
	     List<BoardLike> member = boardDAO.likelist(sn);
	     return  new BusinessResult(member);
	 }
	
	
	 @Override
	 @Transactional
	 public BusinessResult insertAttachsServcie(Board board, String id, String path) {
	   		
	   	 String 저장경로 = path+"//"+id+"//"+board.getSn();
	       
	       File folder=new File(저장경로);
	       
	       folder.mkdirs();
	       
	       for(MultipartFile multipartfile : board.getAttachFiles()) {
	          
	          File 첨부파일 = new File(저장경로+"//"+multipartfile.getOriginalFilename());
	          
	          System.out.println(첨부파일+"<-파일저장경로");
	          
	       try {
	             
	          multipartfile.transferTo(첨부파일);
	                   
	          } catch (Exception e) {System.out.println("첨부파일에 문제가 생겼어요!");}
	                
	       }
	    
	    return new BusinessResult();
	 }
	 
	 @Override
	 @Transactional
	 public void 사진출력(int 첨부번호,HttpServletRequest req, HttpServletResponse rep) {
		try {
		 	 String encodedFilename="";
		 	 String fullFilePath =null;	
		      String path = req.getSession().getServletContext().getRealPath("/resources/board");	
		      
		 	 if(첨부번호!=0) {
		      Attach attach=boardDAO.findattach(첨부번호);
		      String filename = attach.getFilename();
		     
		      String browser = req.getHeader("User-Agent"); 
		      System.out.println(browser);
		      if (browser.contains("MSIE")||browser.contains("Chrome")) {
		      encodedFilename = URLEncoder.encode(filename, "UTF-8").replaceAll("\\+", "%20");
		      } else if (browser.contains("Firefox")) {
		      encodedFilename = "\"" + new String(filename.getBytes("UTF-8"), "8859_1") + "\"";
		      } else if (browser.contains("Opera")) {
		      encodedFilename = "\"" + new String(filename.getBytes("UTF-8"), "8859_1") + "\"";
		      }
	 
		      fullFilePath=path+"//"+attach.getBoard().getWriter().getId()+"//"+attach.getBoard().getSn()+"//"+attach.getFilename();
		      }
		 	 else{fullFilePath=path+"//"+"noimage.png";}
		 	 		//upload/board/bono.jpg에서 호출
		      rep.setContentType("application/octer-stream: charset=utf-8");
		      rep.setHeader("Content-Transfer-Encoding", "binary");     
		      rep.setHeader("Content-Disposition",
		      "attachment; filename=\""+encodedFilename+"\"");
		      File file=new File(fullFilePath);
		      rep.setHeader("Content-Length", "" + file.length());
		     
		      OutputStream os =rep.getOutputStream();
		      FileInputStream fis=new FileInputStream(file);
		      int 읽은크기=0;
		      byte [] 버퍼 = new byte[1024];
		      while((읽은크기=fis.read(버퍼))!=-1) {//fis가 HDD 파일을 읽어 메모리 버퍼로  데이터 이동
		      os.write(버퍼,0,읽은크기); //os가 메모리 버퍼에서 응답 버퍼로  데이터 이동
		      }
		      fis.close();
		      os.close();
		      }catch(Exception ex) {ex.printStackTrace();}
		
 
	 }
	
    @Override
    @Transactional
    public BusinessResult deleteAttach(int sn,HttpServletRequest req) {
    	
    	//업무
    	
    	//1-1업무규칙검사
    	
    	//1-2업무실행
    	String filePath =null;	
    	String path = req.getSession().getServletContext().getRealPath("/upload/board");
          Attach attach=boardDAO.findattach(sn);
          filePath=path+"//"+attach.getBoard().getWriter().getId()+"//"+attach.getBoard().getSn()+"//"+attach.getFilename();
     
          File deleteFile = new File(filePath);
     
          // 파일이 존재하는지 체크 존재할경우 true, 존재하지않을경우 false
          if(deleteFile.exists()) {
              
              // 파일을 삭제합니다.
              deleteFile.delete(); 
              
              System.out.println("파일을 삭제하였습니다.");
              
          } else {
              System.out.println("파일이 존재하지 않습니다.");
          }
    	//DB이용여부()
    	boardDAO.deleteAttach(sn);//db삭제
    	return new BusinessResult();
    }

	@Override
	public BusinessResult makechat(String id) {
		
		
		
		boardDAO.makechat(id);
		
		return new BusinessResult();
	}

	@Override
	public BusinessResult joinchat(String chatroom_id, int member_sn) {
		 
		boardDAO.joinchat(chatroom_id, member_sn);
		
		return new BusinessResult();
	}
	
	

	@Override
	public BusinessResult chatcheck(int sn1, int sn2) {
		
		String id="";
		id = boardDAO.chatcheck(sn1, sn2);
		
		if(id==null){
			return new BusinessResult(ERRORCODE.채팅방없음,"채팅방없음");
		}
		
		return new BusinessResult(id);
	}
	
	@Override
	public BusinessResult chatting(String id) {
		
		List<Chatting> ch = null;
		ch = boardDAO.chatting(id);
		
		return new BusinessResult(ch);
	}

	
	@Override
	public BusinessResult randomboard10() {
		
		List<Integer> randomboard = null;
		
		randomboard = boardDAO.randomboard10();
		
		return new BusinessResult(randomboard);
		
	}
	
}
