package com.lec206.acebook.ui_board;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lec206.acebook.chat.ChatRoom;
import com.lec206.acebook.chat.Chatting;
import com.lec206.acebook.dataservice_board.IBoardDAO;
import com.lec206.acebook.manage_board.IBoardManage;
import com.lec206.acebook.manage_friend.IFriendManage;
import com.lec206.acebook.manage_member.IMemberManage;
import com.lec206.acebook.util.BusinessResult;
import com.lec206.acebook.util.ERRORCODE;
import com.lec206.acebook.util.게시물상태;
import com.lec206.acebook.vo_board.Attach;
import com.lec206.acebook.vo_board.Board;
import com.lec206.acebook.vo_board.BoardLike;
import com.lec206.acebook.vo_board.Comment;
import com.lec206.acebook.vo_member.Member;

@Controller
public class BoardContoller {
	
	@Autowired IBoardManage boardmanage;
	@Autowired IMemberManage membermanage;
	@Autowired IFriendManage friendmanage;

	
	@RequestMapping(value = "board", method = RequestMethod.GET)
	public String getBoard() {
			
		//1.요청페이지(.jsp)
			
		//2.업무
									
		//3.경로지정
		return "/board/게시물등록";
		 
	}
	
	@RequestMapping(value = "board", method = RequestMethod.POST)
	public ModelAndView postboardinsert(@ModelAttribute("board") Board board,HttpSession session) {

			
		//1.요청페이지(게시물등록.jsp)
		ModelAndView mv = new ModelAndView();
		
		String id = (String)session.getAttribute("id");
		// resources/board -> view -> 리소스 폴더 찾을수도 잇으니까  , /resources/board로 해줘야함
		String path = session.getServletContext().getRealPath("/resources/board");
		
		//System.out.println(path+"주소"); 실제주소확인용 : 확인완료
		if(board.getState()==게시물상태.랜덤프로필) {
			id = board.getWriter().getId();
			board.setState(게시물상태.프로필);
		}
		//2.업무
			
		//첨부파일에 보드번호를 저장하기 위해 보드save 먼저 해야함
		BusinessResult br = boardmanage.insertBoard(board);
		//비정상코드입력시
		if(br.getCode()!=ERRORCODE.NORMAL) {}
			
		//첨부파일이 있을경우 파일업로드 실행
		if(board.getAttachFiles()!=null) {
				
	    br = boardmanage.insertAttachsServcie(board, id, path);
	        
		}
									
		//3.경로지정
		
		mv.setViewName("/board/게시물등록");
		if(board.getState()==게시물상태.프로필) {mv.setViewName("/board/profile");}
		if(board.getState()==게시물상태.광고) {mv.setViewName("/acebook");}
		if(board.getState()==게시물상태.백프로필) {mv.setViewName("/board/backprofile");}
		if(board.getState()==게시물상태.공유) {mv.setViewName("/board/게시물공유");}
		mv.addObject("message", "close");


		return mv;
			
	}
	
	 @RequestMapping(value = "personpage/{sn}",method = RequestMethod.GET)
	 public ModelAndView getpersonpage(@PathVariable("sn") int sn, HttpSession session) {
	 
	    //1.요청페이지(acebook.jsp)
	    ModelAndView mv = new ModelAndView();
	    
	    //2.업무
	    BusinessResult br = membermanage.findSimpleMember(sn);
	    
	    //비정상코드입력시
	    if(br.getCode()!=ERRORCODE.NORMAL) {}
	    
	    Member member = (Member)br.getValue();      
	    br = friendmanage.신청혹은수락((int)session.getAttribute("sn"), sn);
	    int 요청확인 = (int)br.getValue();

	    //3.경로지정
	    

	     mv.setViewName("/board/personPage");
	     mv.addObject("request", 요청확인);
	     mv.addObject("member", member);
	     
	    return mv;
	   
	   }
	
	@ResponseBody
	@RequestMapping(value = "selectuser",method = RequestMethod.POST)
    public List<Member> idCheck(@RequestParam String name){
		
		//1.요청페이지(personPage.jsp)
		
		//2.업무
        String search=name.trim();
        BusinessResult br = boardmanage.outputMember(search);
        List<Member> users = (List<Member>)br.getValue();
       
		//비정상코드입력시
     	if(br.getCode()!=ERRORCODE.NORMAL) {System.out.println("error");}
       
     	return users;
    }
	
	@ResponseBody
	@RequestMapping(value = "profile/{sn}",method = RequestMethod.POST)
	public List<Board> profileBoard(@PathVariable int sn,@RequestParam int size) {
		
		//1.요청페이지(personPage.jsp)
		
		//2.업무
		BusinessResult br = boardmanage.personBoard(sn,size);
		List<Board> boards = (List<Board>)br.getValue();
		
		for(Board board : boards) {
			
			if(board.getState()==게시물상태.프로필) {
				System.out.println(board.getSn()+"번 게시물은 프로필게시물");
			}
		}
		//비정상코드입력시
		if(br.getCode()!=ERRORCODE.NORMAL) {System.out.println("error");}
		
		return boards;
	}
	
	@ResponseBody
	@RequestMapping(value = "mainboard/{sn}",method = RequestMethod.POST)
	public List<Board>  mainboard(@PathVariable int sn,@RequestParam int size) {
		
		//1.요청페이지(mainboard.jsp)
		BusinessResult br = null;
		
		//2.업무
	    if(size==0) {//게시물출력 최소 1실행
	    	
	    	br = boardmanage.viewtimeupdate(sn);
	    	if(br.getCode()!=ERRORCODE.NORMAL) {}
	    }
	    
	    br = boardmanage.mainboard(sn, size);
	    
	    if(br.getCode()!=ERRORCODE.NORMAL) {}
	    
	    List<Board> list = null;
	    List<Board> boards = (List<Board>)br.getValue();
	    
	    if(boards.size()!=0) {
	    	
	    	list=boards;
	    	
	    } else {
	    	
	    	br = boardmanage.randomboard(sn);
	    	if(br.getCode()!=ERRORCODE.NORMAL) {}
	    	
	    	List<Board> random = (List<Board>)br.getValue();
	    	list=random;
	    	
	    	}
	    
	    return list;
	    
		}
	
	@RequestMapping(value = "mainboard/{sn}",method = RequestMethod.GET)
	public ModelAndView getMainboard(@PathVariable int sn, HttpSession session) {
		
		//1.요청페이지(acebook.jsp)
		ModelAndView mv = new ModelAndView();
		
		//2.업무
		BusinessResult br = membermanage.findSimpleMember(sn);
		
		//비정상코드입력시
		if(br.getCode()!=ERRORCODE.NORMAL) {}
			
		Member member = (Member)br.getValue();
		
		//3.경로지정
		mv.setViewName("/board/mainboard");
		mv.addObject("member", member);
		    
		return mv;
	
	}
	
	@RequestMapping(value = "profile",method = RequestMethod.GET)
	public ModelAndView getProfile(HttpSession session) {
	   
		 //1.요청페이지(.jsp)   
		 ModelAndView mv = new ModelAndView();
		 
		 //2.업무
		 int sn = (Integer)session.getAttribute("sn");
		 BusinessResult br = membermanage.findSimpleMember(sn);
		 Member member = (Member)br.getValue();
		 
		 //비정상코드입력시
		 if(br.getCode()!=ERRORCODE.NORMAL) {}
		 
		 //3.경로지정
		 mv.setViewName("/board/profile");
		 mv.addObject("profile", member.getProfile());        
	
		 return mv;
	   
	}
	
	@RequestMapping(value = "backprofile",method = RequestMethod.GET)
	public ModelAndView getBackprofile(HttpSession session) {
	   
          //1.요청페이지(.jsp)   
          ModelAndView mv = new ModelAndView();
          
          //2.업무
          int sn = (Integer)session.getAttribute("sn");
          BusinessResult br = membermanage.findSimpleMember(sn);
          Member member = (Member)br.getValue();
          
          //비정상코드입력시
          if(br.getCode()!=ERRORCODE.NORMAL) {}
          
          //3.경로지정
          mv.setViewName("/board/backprofile");
          mv.addObject("profile", member.getBackprofile());        
	    	
		  return mv;
	   
	}

	@RequestMapping(value = "personstate/{sn}",method = RequestMethod.GET)
	public ModelAndView getPersonstate(@PathVariable int sn) {
		
		//1.요청페이지(acebook.jsp)
		ModelAndView mv = new ModelAndView();
		
		//2.업무
		BusinessResult br = membermanage.findSimpleMember(sn);
		
		Member member = (Member)br.getValue();
		
	    mv.setViewName("/board/personstate");
	    mv.addObject("member", member);
		
		return mv;
	}
	
	@ResponseBody
	@RequestMapping(value="/like/{sn}", method=RequestMethod.POST, produces="application/text;charset=utf-8")
	public String postLikeCheck(@PathVariable int sn) {
		//1.요청페이지(personPage.jsp)
		
		//2.업무
		BusinessResult br = boardmanage.likeCount(sn);
		int 좋아요갯수 = (int)br.getValue();
		
		//비정상코드입력시
		if(br.getCode()!=ERRORCODE.NORMAL) {}
		BusinessResult br1 = boardmanage.commentCount(sn);
		int commentCount = (int)br1.getValue();
		
		//비정상코드입력시
		if(br1.getCode()!=ERRORCODE.NORMAL) {}
		

		String html ="";
		html+="<div class=\"pointer profile\" onclick=\"open_layer(7,"+sn+")\"><p>좋아요갯수 : "+좋아요갯수;
		html+="댓글갯수 : "+commentCount+"</p></div>";
		
		return html;
	}
	
	@ResponseBody
	@RequestMapping(value="likeis", method={RequestMethod.POST}, produces="text/plain;charset=UTF-8")
	public String postLikeis(@RequestBody BoardLike likes) {
		
		//1.요청페이지(personPage.jsp)
		
		//2.업무
		int mno =likes.getWriter().getSn();
		int bno =likes.getBoard().getSn();
		BusinessResult br = boardmanage.likeor(bno,mno);
		int 좋아요여부 = (int)br.getValue();
		
		//비정상코드입력시
		if(br.getCode()!=ERRORCODE.NORMAL) {}
		
		String html ="";
		if(좋아요여부==0) {html+="<input type=\"button\" id=\"likebt"+bno+"\" onclick =\"좋아요("+0+","+bno+","+mno+")\" value=\"좋아요\"/>";}
		else {html+="<input type=\"button\" class=\"like\" id=\"likebt"+bno+"\" onclick =\"좋아요("+1+","+bno+","+mno+")\" value=\"좋아요\"/>";}
		
		return html;
	
	}
	
	@ResponseBody
	@RequestMapping(value="like", method={RequestMethod.POST}, produces="text/plain;charset=UTF-8")
	public void postLikeCheck(@RequestBody BoardLike likes) {
		
		//1.요청페이지(personPage.jsp)
		
		//2.업무
		int mno =likes.getWriter().getSn();
		int bno =likes.getBoard().getSn();
		int no = likes.getLike();
		
		BusinessResult br1 =boardmanage.likeProcess(bno, mno, no); //return :x
		
		//비정상코드입력시
		if(br1.getCode()!=ERRORCODE.NORMAL) {}
	
	}
	
	@RequestMapping(value = "sharing/{sn}",method = RequestMethod.GET)
	public ModelAndView getSharing(@PathVariable int sn) {
		
		//1.요청페이지(personPage.jsp)
		ModelAndView mv = new ModelAndView();
		
		//2.업무
		BusinessResult br = boardmanage.findboard(sn);
		
		//비정상코드입력시
		if(br.getCode()!=ERRORCODE.NORMAL) {}
		Board board = (Board)br.getValue();
			
		try {
			BusinessResult br1 = boardmanage.getAttachs(sn);
			//비정상코드입력시
			if(br1.getCode()!=ERRORCODE.NORMAL) {}
			List<Attach> attaches = (List<Attach>)br1.getValue();
			mv.addObject("attach", attaches);   
			}
		catch(Exception e){}
			
		//3.경로지정
		mv.addObject("board", board);
		mv.setViewName("/board/게시물공유");
		
		return mv;
	   
	}

	@ResponseBody
	@RequestMapping(value = "/sharing/{sn}",method = RequestMethod.POST, produces="application/text;charset=utf-8")
	public String postSharing(@PathVariable int sn) {
		
		//1.요청페이지(personPage.jsp)
	
		//2.업무
		BusinessResult br = boardmanage.sharing(sn);
		
		//비정상코드입력시
		if(br.getCode()!=ERRORCODE.NORMAL) {}
		Board board = (Board)br.getValue();
	
		String html ="";
		html +="<div id=\"sharing\">";
		html +="<img class=\"pointer profile\" src =\"/board/attach/"+board.getWriter().getProfile()+"\" id=\"myimg\" height=\"30\" width=\"30\"/>";
		String 작성자 = board.getWriter().getName();
		html +="<b>"+작성자+"</b>"+"<br>"+board.getContents()+"<br>";
		System.out.println(작성자);
		try {
			BusinessResult br2 = boardmanage.공유첨부파일(sn);
			int getsn = (int)br2.getValue();
			BusinessResult br1 = boardmanage.getAttachs(getsn);
			
			//비정상코드입력시
			if(br1.getCode()!=ERRORCODE.NORMAL) {}
			if(br2.getCode()!=ERRORCODE.NORMAL) {}
			List<Attach> attaches = (List<Attach>)br1.getValue();
		
			 if(attaches!=null) {
				 for(Attach attach : attaches) {
				 html +="<img src =\"/board/attach/"+attach.getSn()+"\" id=\"myimg\" height=\"60\" width=\"60\"/>";
				 }
			 }
		
			
			}catch(Exception e){}	
		
			html +="</div>";
			
			
		return html;
	}

	@ResponseBody
	@RequestMapping(value="/reportboard/{sn}", method=RequestMethod.POST, produces="application/text;charset=utf-8")
	public String postReportBoard(@PathVariable int sn) {
		
		//1.요청페이지(personPage.jsp)
		
		//2.업무
		BusinessResult br = boardmanage.reportboard(sn);
		
		//비정상코드입력시
		if(br.getCode()!=ERRORCODE.NORMAL) {}
		
		
		return "이 게시물은 신고된 게시물 입니다";
	}
	
	@RequestMapping(value = "modifyboard/{sn}",method = RequestMethod.GET)
	public ModelAndView getModifyBoard(@PathVariable int sn) {

	   //1.요청페이지(personPage.jsp)
	   ModelAndView mv = new ModelAndView();
	   
	   //2.업무
	   	
	   	BusinessResult br = boardmanage.findboard(sn);
	   //비정상코드입력시
	   	if(br.getCode()!=ERRORCODE.NORMAL) {}
	   	Board board = (Board)br.getValue();
	   	
	   	try {
	   		BusinessResult br1 = boardmanage.getAttachs(sn);
	   	//비정상코드입력시
	   		if(br1.getCode()!=ERRORCODE.NORMAL) {}
	   		List<Attach> attaches = (List<Attach>)br1.getValue();
	   		mv.addObject("attach", attaches);   
	   		}
	   	catch(Exception e){}
	   	
	   //3.경로지정
	   	mv.addObject("board", board);  
	   	mv.setViewName("/board/게시물수정");
       
		return mv;
			
	}
	
	@RequestMapping(value = "/modifyboard", method= RequestMethod.POST)
	public ModelAndView postModifyBoard(@ModelAttribute Board board,HttpSession session) {

		//1.요청페이지(게시물등록.jsp)
		ModelAndView mv = new ModelAndView();
		
		String id = (String)session.getAttribute("id");
		String path = session.getServletContext().getRealPath("upload/board");

		//2.업무
	    //첨부파일에 보드번호를 저장하기 위해 보드save 먼저 해야함
	    BusinessResult br = boardmanage.modifyboard(board);
	    
	    //비정상코드입력시
	    if(br.getCode()!=ERRORCODE.NORMAL) {}
        
	    //첨부파일이 있을경우 파일업로드 실행
	    if(board.getAttachFiles()!=null) {
	    	
	        br = boardmanage.insertAttachsServcie(board, id, path);
	    }
	    						
		//3.경로지정
			
		mv.setViewName("/board/게시물수정");
		mv.addObject("message", "close");

	return mv;
		
	}
	
	@RequestMapping(value = "eachboard/{sn}", method= RequestMethod.GET)
	public ModelAndView getEachBoard(@PathVariable int sn) {

			
	    //1.요청페이지(personPage.jsp)
	    ModelAndView mv = new ModelAndView();
	    	
	    //2.업무
	    try {
		    BusinessResult br = boardmanage.getAttachs(sn);
		    
		    //비정상코드입력시
		    if(br.getCode()!=ERRORCODE.NORMAL) {}
		    
		    List<Attach> attaches = (List<Attach>)br.getValue();
		    mv.addObject("attach", attaches);   
	    	}
	    catch(Exception e){}
	    
	    //3.경로지정
	    mv.setViewName("/board/eachboard");
        
	    return mv;
		
	}
	
	@ResponseBody
	@RequestMapping(value="/deleteattach/{sn}", method=RequestMethod.POST, produces="application/text;charset=utf-8")
	public String postDeleteAttach(@PathVariable int sn,HttpServletRequest req) {
		
		//1.요청페이지(personPage.jsp)
		
		//2.업무
		BusinessResult br = boardmanage.deleteAttach(sn,req);
			
		//비정상코드입력시
		if(br.getCode()!=ERRORCODE.NORMAL) {}
		
		
		return "삭제되었습니다";
	}
	
	@ResponseBody
	@RequestMapping(value="/deleteboard/{sn}", method=RequestMethod.POST, produces="application/text;charset=utf-8")
	public String postDelboard(@PathVariable int sn) {
		
		//1.요청페이지(personPage.jsp)
		
		//2.업무
		BusinessResult br = boardmanage.deleteboard(sn);
		
		//비정상코드입력시
		if(br.getCode()!=ERRORCODE.NORMAL) {}
		
		
		return "삭제되었습니다";
	}
	
	@ResponseBody
	@RequestMapping(value="Advertising", method=RequestMethod.POST, produces="application/text;charset=utf-8")
	public String  Advertising() {
		
		//1.요청페이지(personPage.jsp,mainboard.jsp)
		
		//2.업무
		BusinessResult br = boardmanage.Advertising();
		
		//비정상코드입력시
		if(br.getCode()!=ERRORCODE.NORMAL) {
			
			if(br.getCode()==ERRORCODE.NETWORK_ERROR) { //광고로 등록된 글이 없을경우
				
				System.out.println("등록된 광고 현재 없음");
				return "";
			}
			
		}
		
		Attach 광고 = (Attach)br.getValue();
		
		
		return "<div class=\"divbox\"><img src =\"/board/attach/"+광고.getSn()+"\" id=\"myimg\" height=\"350\" width=\"750\"/><br></div>";
	}
	
	@ResponseBody
	@RequestMapping(value="numberofboard/{sn}", method=RequestMethod.POST, produces="application/text;charset=utf-8")
	public String numberofboard(@PathVariable int sn,@RequestParam int size) {
		
		//1.요청페이지(mainboard.jsp)
		
		//2.업무
		BusinessResult br = boardmanage.numberofboard(sn);
		//비정상코드입력시
		if(br.getCode()!=ERRORCODE.NORMAL) {}
		
		int 갯수 = (int)br.getValue();	
			
		//3.경로지정
		String html ="";
		if(갯수==size) {html+="&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;--------------여기까지 새 게시물입니다-------------";}
	
		return html;
	
	}
	
	@ResponseBody
	@RequestMapping(value = "checknewboard/{sn}", method= RequestMethod.POST)
	public int  checknewboard(@PathVariable int sn) {
		
		//1.요청페이지(mainboard.jsp)
		
		//2.업무
		BusinessResult br = boardmanage.checknewboard(sn);
		int newboard = (int)br.getValue();
		
		//비정상코드입력시
		if(br.getCode()!=ERRORCODE.NORMAL) {}
		return newboard;
		
		}
	
	@ResponseBody
	@RequestMapping(value="/deletecom/{sn}", method=RequestMethod.POST, produces="application/text;charset=utf-8")
	public String postDeleteComment(@PathVariable int sn) {
	    
		//1.요청페이지(personPage.jsp)
	    
	    //2.업무
	    BusinessResult br = boardmanage.deleteCommentService(sn);
	    
	    //비정상코드입력시
	    if(br.getCode()!=ERRORCODE.NORMAL) {}
	    
	    
	    return "삭제되었습니다";
	
	}
	
	@ResponseBody
	@RequestMapping(value="comment", method={RequestMethod.POST}, produces="text/plain;charset=UTF-8")
	public String postInsertComment(@RequestBody Comment comment) {
	    
		//1.요청페이지(personPage.jsp)
	    
	    //2.업무
        System.out.println(comment.getWriter().getSn());
        BusinessResult br =boardmanage.insertCommentService(comment);
        
        //비정상코드입력시
        if(br.getCode()!=ERRORCODE.NORMAL) {}
        Comment 댓글 = (Comment)br.getValue();
        	
        String html ="";
        html +="<div id=\"eachcomment"+댓글.getSn()+"\"><img src =\"/board/attach/"+댓글.getWriter().getProfile()+"\" height=\"30\" width=\"30\"/>"+댓글.getWriter().getName()+": "+댓글.getContents()+"<button type=\"button\" onclick=\"댓글삭제("+comment.getSn()+","+comment.getBoard().getSn()+");\">삭제</button></div>";
	    	
	    return html;
	
	}
	
	@ResponseBody
	@RequestMapping(value="/comment/{sn}", method={RequestMethod.POST}, produces="text/plain;charset=UTF-8")
	public String postOpenCommentList(@PathVariable int sn,@RequestParam Map<String, Object> data,HttpSession session) {//게시물번호
		
		//1.요청페이지(personPage.jsp)
	    String profile = (String) data.get("profile");
	    String commentsize = (String) data.get("commentsize");
	    int size = Integer.parseInt(commentsize);
	    String sessionSn = (String)session.getAttribute("id");
	    int sessionno = (int)session.getAttribute("sn");
	    
	    
	    BusinessResult br2 = membermanage.findSimpleMember(sessionno);
	    Member member = (Member)br2.getValue();
	    
	    //2.업무
	    BusinessResult br = boardmanage.outputCommentService(sn,size);
	    
	    //비정상코드입력시
	    if(br.getCode()!=ERRORCODE.NORMAL) {}
	    
	    List<Comment> list = (List<Comment>)br.getValue();
	    String html ="";
	    html +="<div id=\"plus"+sn+"\"><img src =\"/board/attach/"+member.getProfile()+"\" height=\"30\" width=\"30\"/><input type=\"text\" id=\"commentcontent"+sn+"\"/><button onclick=\"댓글달기("+sn+")\">등록</button></div>";
	    try {
		    for(Comment comment : list) {
		    	
		    	html +="<div id=\"eachcomment"+comment.getSn()+"\"><img src =\"/board/attach/"+comment.getWriter().getProfile()+"\" height=\"30\" width=\"30\"/>"+comment.getWriter().getName()+": "+comment.getContents();
		    	if(sessionSn.equals(comment.getWriter().getId())) {html +="<button type=\"button\" onclick=\"deleteCommentService("+comment.getSn()+","+sn+");\">삭제</button>";}
		    	html +="</div>";
		    }
	    }catch(Exception e){html += "댓글이 없습니다";}
		    
	    html +="<button type=\"button\" onclick=\"댓글출력("+sn+","+profile+","+size+")\" id=pluscomment3 >더보기</button>";
		return html;
	
	}

	@RequestMapping(value = "personfriend/{sn}", method= RequestMethod.GET)
	public ModelAndView getPersonFriend(@PathVariable int sn) {
		
		//1.요청페이지(acebook.jsp)
		ModelAndView mv = new ModelAndView();
		
		//2.업무
		BusinessResult br = membermanage.findSimpleMember(sn);
		
		Member member = (Member)br.getValue();
		
		//비정상코드입력시
	    if(br.getCode()!=ERRORCODE.NORMAL) {}
		
	    mv.setViewName("/board/personfriend");
	    mv.addObject("member", member);
		
		return mv;
	}
	
	
	@RequestMapping("board/attach/{a_no}")
	public void fileDown(@PathVariable("a_no")int 첨부번호,HttpSession session,HttpServletRequest req, HttpServletResponse rep) {
		//1.요청페이지(게시물등록.jsp)
		//2.업무 
			
		boardmanage.사진출력(첨부번호, req, rep);
		// 사진출력 컨트롤 void
		// 첨부번호 = 0 noimage출력, !=0 : 그번호 게시물의 사진출력
	}
	
	@RequestMapping(value = "likepeople/{sn}", method= RequestMethod.GET)
	public ModelAndView getLikePeople(@PathVariable int sn) {
	
		//1.요청페이지(acebook.jsp)
		ModelAndView mv = new ModelAndView();
		//2.업무
		BusinessResult br = boardmanage.checkLikeServcie(sn);
		//비정상코드입력시
		if(br.getCode()!=ERRORCODE.NORMAL) {}
		
		List<BoardLike> boardlikes = (List<BoardLike>)br.getValue();	
		if(boardlikes!=null) {mv.addObject("boardlikes", boardlikes);}
		 
		//3.경로지정
		
	    mv.setViewName("/board/likepeople");
	    
	    
		return mv;
	
	}
	
	@ResponseBody
	@RequestMapping(value="/photo/{sn}", method=RequestMethod.POST, produces="application/text;charset=utf-8")
	public String postOutputPhoto(@PathVariable int sn) {
		//1.요청페이지(personPage.jsp)
		
		String html ="";
		//2.업무
	    try {
	    BusinessResult br = boardmanage.getAttachs(sn);
	    List<Attach> attaches = (List<Attach>)br.getValue();
	    
	    if(attaches.size()<3) {
	    for(Attach attach : attaches) {
	    	
	    	html += "<img src =\"/board/attach/"+attach.getSn()+"\" height=\"70\" width=\"70\"/>";
	    
	    }
	    }
	    else {
	    	html += "<img src =\"/board/attach/"+attaches.get(0).getSn()+"\" height=\"70\" width=\"70\"/>";
	    	html += "<button type=\"button\" onclick=\"open_layer(3,"+sn+");\">사진파일 "+attaches.size()+"개 더보기</button>";
	    }
		
		
		//비정상코드입력시
		if(br.getCode()!=ERRORCODE.NORMAL) {}
		}
		catch(Exception ex){}
	
		return html;
		
	}
	
	@RequestMapping(value="testplus",method = RequestMethod.GET)
	public String testcodeBoard() {
	
		//1.요청페이지(acebook.jsp)
		
		//2.업무
		 
		//3.경로지정
		return "/board/더미생성";
	
	}
	
	@RequestMapping(value="ad",method = RequestMethod.GET)
	public String testcodeAdv() {
	
		//1.요청페이지(acebook.jsp)
		
		//2.업무
		 
		//3.경로지정
		
		return "/ad";
	
	}
	
	@RequestMapping(value="chatroom/{sn1}/{sn2}",method = RequestMethod.GET)
	public String getMakeChatroom(@PathVariable("sn1") int sn1,@PathVariable("sn2") int sn2) {
	
		//1.요청페이지(acebook.jsp)
		
		//2.업무
		BusinessResult br = boardmanage.chatcheck(sn1, sn2);
		String id = (String)br.getValue();
		
		if(br.getCode()==ERRORCODE.채팅방없음){
			
			System.out.println(br.getCode());
			
			id = UUID.randomUUID().toString();
	    	boardmanage.makechat(id);
	    	boardmanage.joinchat(id, sn1);
	    	boardmanage.joinchat(id, sn2);
	    	
		}
		
		
		System.out.println("채팅방있음");
		
		//3.경로지정
    	return "redirect:/chatroom/"+id;
	}
	
	
	
	@RequestMapping(value="chatroom/{chatroomId}",method = RequestMethod.GET)
	public ModelAndView getJoinChat(@PathVariable("chatroomId") String chatroomId) {
	
		//1.요청페이지(acebook.jsp)
		ModelAndView mv=new ModelAndView();
		
		//2.업무
		List<Chatting> ch = null;
		
		BusinessResult br = boardmanage.chatting(chatroomId);
		ch = (List<Chatting>)br.getValue();
		
		//비정상코드입력시
		if(br.getCode()!=ERRORCODE.NORMAL) {}
		
		//3.경로지정
    	mv.addObject("chatroomId",chatroomId);
    	mv.addObject("ch", ch);
    	
    	mv.setViewName("chatting");
    	return mv;
	}
	
	@RequestMapping(value = "randomprofile", method = RequestMethod.GET)
	public String profile() {

		return "/board/profile2";
		 
	}
	
	@RequestMapping(value="randomprofile",method = RequestMethod.POST)
	public String profile(@ModelAttribute("board") Board board, HttpSession session) {
		
		
		//1.요청페이지(profile2.jsp)
		
		BusinessResult br = membermanage.selectAll();
				
		List<Member> 회원들 = (List<Member>)br.getValue();
		for(Member member : 회원들) {
			System.out.println(member.getName());
		}
		
		for(int i=0; i<10; i++) {
	    int 랜덤작성자 = (int)((Math.random()*회원들.size()));
	    Member member = 회원들.get(랜덤작성자);
	    //int ran = (int)(Math.random() * board.getAttachFiles().size());
	    		
		Board newboard = new Board();
		newboard.setWriter(member);
		newboard.setContents(" ");
		newboard.setState(게시물상태.랜덤프로필);
		newboard.setAttachFiles(board.getAttachFiles());
		
		
		postboardinsert(newboard,session);
		
	    }
		
		//2.업무
		 
		//3.경로지정
		
		return "board/더미생성";
	
	}
	
	
	
	//testCode영역
	
	@RequestMapping(value="testboard",method = RequestMethod.GET)
	public String get게시물테스트1() {
	
		String [] 랜덤게시물내용 = {"대한 자신과 용기가 있다 그러므로 그들은 이상의 보배를 능히 품으며 그들의 이상은 아름답고 소담스러운 열매를 맺어 우리 인생을 풍부하게 하는 것이다 보라 청춘을 ! 그들의 몸이 얼마나 튼튼하며 그들의 피부가 얼마나 생생하며",
									"이상! 우리의 청춘이 가장 많이 품고 있는 이상! 이것이야말로 무한한 가치를 가진 것이다 사람은 크고 작고 간에 이상이 있음으로써 용감하고 굳세게 살 수 있는 것이다 석가는 무엇을 위하여 설산에서 고행을 하였으며 예수는",
									"사는가 싶이 살았으며 그들의 그림자는 천고에 사라지지 않는 것이다 이것은 현저하게 일월과 같은 예가 되려니와 그와 같지 못하다 할지라도 창공에 반짝이는 뭇 별과 같이 산야에 피어나는 군영과 같이 이상은 실로 인간의 부패를",
									"곳으로 인도하겠다는 커다란 이상을 품었기 때문이다 그러므로 그들은 길지 아니한 목숨을 사는가 싶이 살았으며 그들의 그림자는 천고에 사라지지 않는 것이다 이것은 현저하게 일월과 같은 예가 되려니와 그와 같지 못하다 할지라도 창공에 반짝이는",
									"못할 바이며 시들어 가는 노년에게서 구하지 못할 바이며 오직 우리 청춘에서만 구할 수 있는 것이다 청춘은 인생의 황금시대다 우리는 이 황금시대의 가치를 충분히 발휘하기 위하여 이 황금시대를 영원히 붙잡아 두기 위하여 힘차게",
									"끝에 스며들어 가는 열락의 소리다이것은 피어나기 전인 유소년에게서 구하지 못할 바이며 시들어 가는 노년에게서 구하지 못할 바이며 오직 우리 청춘에서만 구할 수 있는 것이다 청춘은 인생의 황금시대다 우리는 이 황금시대의 가치를 충분히",
									"천하를 철환하였는가? 밥을 위하여서 옷을 위하여서 미인을 구하기 위하여서 그리하였는가? 아니다 그들은 커다란 이상 곧 만천하의 대중을 품에 안고 그들에게 밝은 길을 찾아 주며 그들을 행복스럽고 평화스러운 곳으로 인도하겠다는 커다란 이상을 품었기",
									"가지에 싹이 트고 꽃 피고 새 우는 봄날의 천지는 얼마나 기쁘며 얼마나 아름다우냐? 이것을 얼음 속에서 불러 내는 것이 따뜻한 봄바람이다 인생에 따뜻한 봄바람을 불어 보내는 것은 청춘의 끓는 피다 청춘의 피가",
									"것은 따뜻한 봄바람이다 풀밭에 속잎나고 가지에 싹이 트고 꽃 피고 새 우는 봄날의 천지는 얼마나 기쁘며 얼마나 아름다우냐? 이것을 얼음 속에서 불러 내는 것이 따뜻한 봄바람이다 인생에 따뜻한 봄바람을 불어 보내는 것은"};
	
		 List<String> 내용들 = new ArrayList<String>();
		 
		 for(int i=0; i<랜덤게시물내용.length; i++) {
			 
			 내용들.add(랜덤게시물내용[i]);
			 
		 }
		
		for(int i=0; i<10; i++) {
			
			Board board = new Board();
			
			int randomcontents = (int)(Math.random()*내용들.size());
			ArrayList<String> 랜덤내용출력 = new ArrayList<String>();
			랜덤내용출력.add(내용들.get(randomcontents));
			board.setContents(랜덤내용출력.get(0));
			
			
			BusinessResult br = membermanage.selectAll();
			List<Member> member = (List<Member>)br.getValue();
			
			int ran = (int)(Math.random()*member.size());
			int 회원번호 = member.get(ran).getSn();
			br = membermanage.findSimpleMember(회원번호);
			
			Member randommember = (Member)br.getValue();
			
			board.setWriter(randommember);
			
			board.setState(게시물상태.전체);
			
			
			br = boardmanage.insertBoard(board);
		}
				
			return "board/더미생성";
			
		}
	
	@RequestMapping(value="testboard1",method = RequestMethod.GET)
	public String randomfreindboard(HttpSession session) {
		
		String [] 랜덤게시물내용 = {"대한 자신과 용기가 있다 그러므로 그들은 이상의 보배를 능히 품으며 그들의 이상은 아름답고 소담스러운 열매를 맺어 우리 인생을 풍부하게 하는 것이다 보라 청춘을 ! 그들의 몸이 얼마나 튼튼하며 그들의 피부가 얼마나 생생하며",
				"이상! 우리의 청춘이 가장 많이 품고 있는 이상! 이것이야말로 무한한 가치를 가진 것이다 사람은 크고 작고 간에 이상이 있음으로써 용감하고 굳세게 살 수 있는 것이다 석가는 무엇을 위하여 설산에서 고행을 하였으며 예수는",
				"사는가 싶이 살았으며 그들의 그림자는 천고에 사라지지 않는 것이다 이것은 현저하게 일월과 같은 예가 되려니와 그와 같지 못하다 할지라도 창공에 반짝이는 뭇 별과 같이 산야에 피어나는 군영과 같이 이상은 실로 인간의 부패를",
				"곳으로 인도하겠다는 커다란 이상을 품었기 때문이다 그러므로 그들은 길지 아니한 목숨을 사는가 싶이 살았으며 그들의 그림자는 천고에 사라지지 않는 것이다 이것은 현저하게 일월과 같은 예가 되려니와 그와 같지 못하다 할지라도 창공에 반짝이는",
				"못할 바이며 시들어 가는 노년에게서 구하지 못할 바이며 오직 우리 청춘에서만 구할 수 있는 것이다 청춘은 인생의 황금시대다 우리는 이 황금시대의 가치를 충분히 발휘하기 위하여 이 황금시대를 영원히 붙잡아 두기 위하여 힘차게",
				"끝에 스며들어 가는 열락의 소리다이것은 피어나기 전인 유소년에게서 구하지 못할 바이며 시들어 가는 노년에게서 구하지 못할 바이며 오직 우리 청춘에서만 구할 수 있는 것이다 청춘은 인생의 황금시대다 우리는 이 황금시대의 가치를 충분히",
				"천하를 철환하였는가? 밥을 위하여서 옷을 위하여서 미인을 구하기 위하여서 그리하였는가? 아니다 그들은 커다란 이상 곧 만천하의 대중을 품에 안고 그들에게 밝은 길을 찾아 주며 그들을 행복스럽고 평화스러운 곳으로 인도하겠다는 커다란 이상을 품었기",
				"가지에 싹이 트고 꽃 피고 새 우는 봄날의 천지는 얼마나 기쁘며 얼마나 아름다우냐? 이것을 얼음 속에서 불러 내는 것이 따뜻한 봄바람이다 인생에 따뜻한 봄바람을 불어 보내는 것은 청춘의 끓는 피다 청춘의 피가",
		"것은 따뜻한 봄바람이다 풀밭에 속잎나고 가지에 싹이 트고 꽃 피고 새 우는 봄날의 천지는 얼마나 기쁘며 얼마나 아름다우냐? 이것을 얼음 속에서 불러 내는 것이 따뜻한 봄바람이다 인생에 따뜻한 봄바람을 불어 보내는 것은"};
		
		List<String> 내용들 = new ArrayList<String>();
		
		for(int i=0; i<랜덤게시물내용.length; i++) {
			
			내용들.add(랜덤게시물내용[i]);
			
		}
		
		for(int i=0; i<10; i++) {
			
			Board board = new Board();
			
			int randomcontents = (int)(Math.random()*내용들.size());
			ArrayList<String> 랜덤내용출력 = new ArrayList<String>();
			랜덤내용출력.add(내용들.get(randomcontents));
			board.setContents(랜덤내용출력.get(0));
			
			
			BusinessResult br = friendmanage.친구상태출력((int)session.getAttribute("sn"));
			Member member = (Member)br.getValue();
			
			int ran = (int)(Math.random()*member.getFriends().size());
			int 회원번호 = member.getFriends().get(ran).getFriend_sn().getSn();
			br = membermanage.findSimpleMember(회원번호);
			
			Member randomfriend = (Member)br.getValue();
			
			board.setWriter(randomfriend);
			
			board.setState(게시물상태.전체);
			
			br = boardmanage.insertBoard(board);
			
		}
		
		return "board/더미생성";
		
	}
	
	@RequestMapping(value="randomcomment",method = RequestMethod.GET)
	public String randomcomment(HttpSession session) {
		
		String [] 랜덤댓글내용 = {"ㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋ",
				"이거봐봐 진짜 웃겨ㅋㅋㅋㅋ",
				"나도 이거 사고 싶다",
				"야 오랜만이다 잘 지내냐",
				"이거 얼마에요",
				"아 댓글 달거 없다",
				"랜덤으로 댓글 다는 중",
				"좋아요 숫자를 누르면 좋아요 누른 사람이 나와요",
				"랜덤 댓글 생성 중"};
		
		List<String> 내용들 = new ArrayList<String>();
		
		for(int i=0; i<랜덤댓글내용.length; i++) {
			
			내용들.add(랜덤댓글내용[i]);
			
		}
		
		for(int i=0; i<50; i++) {
			
			BusinessResult br = boardmanage.randomboard10();
			List<Integer> boardnum = (List<Integer>)br.getValue();
			
			int ransize = (int)(Math.random()*10);
			int boardsn = (int)boardnum.get(ransize);
			
			
			br = membermanage.testSelect();
			List<Member> members = (List<Member>)br.getValue();
			
			ransize = (int)(Math.random()*members.size());
			int membersn = members.get(ransize).getSn();
			
			int randomcontents = (int)(Math.random()*내용들.size());
			ArrayList<String> 랜덤내용출력 = new ArrayList<String>();
			랜덤내용출력.add(내용들.get(randomcontents));
			
			
			Comment comment = new Comment();
				Board board = new Board();
				board.setSn(boardsn);
				
				Member member = new Member();
				member.setSn(membersn);
				
			comment.setBoard(board);
			comment.setWriter(member);
			comment.setContents(랜덤내용출력.get(0));
			
			boardmanage.insertCommentService(comment);
			
		}
		
		return "board/더미생성";
		
	}	
	@RequestMapping(value="randomlike",method = RequestMethod.GET)
	public String randomlike() {
		
		BusinessResult br = membermanage.selectAll();
		List<Member> members = (List<Member>)br.getValue();
		
		
		for(Member member : members) {

			br = boardmanage.randomonlike(member.getSn());
			List<Integer> boardnum = (List<Integer>)br.getValue();
			
			for(Integer boardlist : boardnum) {
				boardmanage.likeProcess(boardlist,member.getSn(),0);
			}
			
		}
		
		return "board/더미생성";
		
	}	

}