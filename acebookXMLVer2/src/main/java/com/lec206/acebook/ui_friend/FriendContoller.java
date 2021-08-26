package com.lec206.acebook.ui_friend;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lec206.acebook.manage_friend.IFriendManage;
import com.lec206.acebook.util.BusinessResult;
import com.lec206.acebook.util.ERRORCODE;
import com.lec206.acebook.util.로그인상태;
import com.lec206.acebook.vo_friend.Friend;
import com.lec206.acebook.vo_member.Member;

@Controller
public class FriendContoller {
	
	@Autowired IFriendManage friendmanage;
	
	@RequestMapping("friend")
	public String friend() {
		
		return "friend/친구";
		
	}
	
	@ResponseBody
	@RequestMapping("request/{sn}")
	public String test(@PathVariable int sn,HttpSession session) {
	         
	   //1.요청페이지(personpage.jsp)
	
	   //2.업무
	   int 친구신청자번호=(int)session.getAttribute("sn");
	      BusinessResult br = friendmanage.친구신청(친구신청자번호,sn);   
	      
	      //비정상코드입력시
	      if(br.getCode()!=ERRORCODE.NORMAL) {}
	   
	   return "";
	
	}
	
	
	@ResponseBody
	@RequestMapping(value = "accept/{sn}", method = RequestMethod.GET)
	public String 친구수락(@PathVariable int sn,HttpSession session) {
				
		//1.요청페이지(.jsp)
		
		//2.업무
		int 내번호 = (Integer)session.getAttribute("sn");
		
		BusinessResult br = friendmanage.친구수락(내번호,sn);
		//비정상 요청시
		if(br.getCode()!=ERRORCODE.NORMAL) {}

								
		//3.경로지정

		return " ";
	
	}
	
	@ResponseBody
	@RequestMapping(value="myfriends",method= {RequestMethod.POST},produces="text/plain;charset=UTF-8")
	public String 내친구목록(HttpSession session) {
		
		//1.요청페이지(.jsp)
		
		//2.업무 
		
			int 내번호=(Integer)session.getAttribute("sn");
			String html="";			
			html+="<h2>모든친구목록</h2><hr>";
			
			BusinessResult br = friendmanage.내친구목록(내번호);
			
			 if(br.getCode()!=ERRORCODE.NORMAL) {
		            
		            if(br.getCode()==ERRORCODE.추천친구없음) {
		               
		               html+=br.getMsg();
		               
		               html+="<hr>";

		               return html;
		               
		            }
			 }
			 
			List<Friend> friends = (List<Friend>)br.getValue();

		            html+="<div id=\"main\" >";

				for(Friend friend : friends) {
									
					//html+=friend.getFriend_sn().getSn();	
					
					html+="<div class=\"jb-wrap\">";
					
					html+="<div class=\"jb-image\">";
					html+="<img onclick=\"move("+friend.getFriend_sn().getSn()+")\" src=\"/board/attach/"+friend.getFriend_sn().getProfile()+"\"class=\"img-thumbnail\" width=\"500px\" height=\"500px\"/>";					
					html+="</div>";
					
					html+="<div class=\"jb-text\">";					
					html+= "<a href=personpage/"+friend.getFriend_sn().getSn()+">";
					html+=friend.getFriend_sn().getName();					
					html+="</a>";
					html+="</div>";				
					
//					if((member.getFriends().indexOf(friend)+1)%7==0) {html += "<br>";}
									
					html+="</div>";
				}
				
				html+="</div><hr>";		
											

			return html;
	
	}
	
	@RequestMapping(value = "friendstate", method = RequestMethod.GET)
	public String 친구들출력() {
		
		//1.요청페이지(.jsp)
		
		//2.업무
		
		//3.경로지정
		
		return "friend/내친구들"; 
	}
	
	@ResponseBody
	@RequestMapping(value="friendstate",method= {RequestMethod.GET, RequestMethod.POST},produces="text/plain;charset=UTF-8")
	 public String 내친구상태(HttpSession session) {
	          
	    //1.요청페이지(.jsp)
	    
	    //2.업무

	       String html="";
	       html+="<h2>내친구상태</h2><hr>";
	       
	       int sn = (Integer)session.getAttribute("sn");
	    
	       BusinessResult br = friendmanage.친구상태출력(sn);
	       
	       if(br.getCode()!=ERRORCODE.NORMAL) {
	          
	          if(br.getCode()==ERRORCODE.존재하지않는회원) {
	             
	             html+="<h2>"+br.getMsg()+"</h2><hr>";

	             return html;
	             
	          }
	       }
	       
	       Member member = (Member)br.getValue();

	          for(Friend friend : member.getFriends()) {
	             html+="<ul>";
	             if(friend.getFriend_sn().getLogin().getState()==로그인상태.접속중) {
	             
	             html+="<img src=\"/resources/img/online.png\" width=\"30px\" height=\"30px\">";
	             
	             } else {
	             
	             html += "<img src=\"/resources/img/offline.png\" width=\"30px\" height=\"30px\">";
	             
	             }
	             
	             html+="<img class=\"pointer profile\" onclick=\"person("+friend.getFriend_sn().getSn()+")\"src=\"/board/attach/"+friend.getFriend_sn().getProfile()+"\" height=\"50px\" width=\"50px\" />";
	             html+=friend.getFriend_sn().getName();            
	             
	             //html+= "<a href=personpage/"+friend.getFriend_sn().getSn()+">"; 1:1 대화열기 가능할듯
	             
	             html+="</a>";
	             html+="</li>";
	             html+="</ul>";
	             html+="<hr>";
	          }
	          
	          //3.경로지정

	    return html;
	 
	 }
	
	@RequestMapping(value="requests",method= {RequestMethod.POST},produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String 받은친구요청(HttpSession session) {
		
		int 내회원번호=(int)session.getAttribute("sn");
		String html="";
		html+="<h2>받은친구요청</h2><hr>";
		
		BusinessResult br=friendmanage.받은친구요청(내회원번호);
		
		//비정상코드입력시
		if(br.getCode()!=ERRORCODE.NORMAL) {
			
			if(br.getCode()==ERRORCODE.추천친구없음) {
				
				html+=br.getMsg();
				
				return html;
				
			}
			
		}
		List<Friend> friends = (List<Friend>)br.getValue();

		//2.업무
			
			html+="<div id=\"main\" >";

				
			for(Friend friend : friends) {
					html+="<div class=\"jb-wrap\">";
					html+="<div class=\"jb-image\">";
					html+="<img onclick=\"move("+friend.getMy_sn().getSn()+")\" src=\"/board/attach/"+friend.getMy_sn().getProfile()+"\"class=\"img-thumbnail\" width=\"500px\" height=\"500px\"/>";
					html+="</div>";	
					html+="<div class=\"jb-text\">";
//					html+=friend.getMy_sn().getSn();				
					html+= "<a href=personpage/"+friend.getMy_sn().getSn()+">";				
					html+=friend.getMy_sn().getName();							
					html+="</a>";		
					html+="</div>";
					html+="</div>";
			}
					html+="</div><hr>";
			

		
		return html;
	
	}
	
	@ResponseBody
	@RequestMapping(value="requested",method= {RequestMethod.POST},produces="text/plain;charset=UTF-8")
	public String 요청한친구목록(HttpSession session) {
	   
	   int 내회원번호 = (Integer)session.getAttribute("sn");
	   
	   String html="";
	   html+="<h2>요청한친구목록</h2><hr>";
	   
	   BusinessResult br = friendmanage.요청한친구목록(내회원번호);
	   
	   if(br.getCode()!=ERRORCODE.NORMAL) {
	 	  
	 	  if(br.getCode()==ERRORCODE.추천친구없음) {
	 		  
	 		  html += br.getMsg()+"<br><hr>";
	 		  
	 		  return html;
	 		  
	 	  }
	 	  
	   }

	  List<Friend> friends = (List<Friend>)br.getValue();
	
	 	  	html+="<div id=\"main\" >";
	 	  	
	 	  	for(Friend friend : friends) {
	 	  		
	    	  	html+="<div class=\"jb-wrap\">";
				html+="<div class=\"jb-image\">";
	 //    	    html+=friends.getFriend_sn().getSn();            
				html+="<img onclick=\"move("+friend.getFriend_sn().getSn()+")\" src=\"/board/attach/"+friend.getFriend_sn().getProfile()+"\"class=\"img-thumbnail\" width=\"500px\" height=\"500px\"/>";
				html+="</div>";
				html+="<div class=\"jb-text\">";					
				html+= "<a href=personpage/"+friend.getFriend_sn().getSn()+">";
				html+=friend.getFriend_sn().getName();                  
				html+="</a>";
				html+="</div>";
				html+="</div>";
	      
	 	  	}
	 	  	
	   html+="</div><hr>";
	   
	
	   return html;
	   
	}
	   
	@ResponseBody
	@RequestMapping(value="requested1",method= {RequestMethod.POST},produces="text/plain;charset=UTF-8")
	public void 요청한친구목록1(HttpSession session) {
	   
	}
	
	@ResponseBody
	@RequestMapping(value="commendschool",method= {RequestMethod.POST},produces="text/plain;charset=UTF-8")
	public String 학교추천(HttpSession session) {
			
		int sn = (Integer)session.getAttribute("sn");
		
		BusinessResult br=friendmanage.학교로친구추천(sn);
		
		String html="";
		
		html+="<h2>학교가 같은 알 수도 있는 사람</h2><hr>";
		
		if(br.getCode()!=ERRORCODE.NORMAL) {
			
			if(br.getCode()==ERRORCODE.추천친구없음) {
				
				html += br.getMsg()+"<br>";
				
				html+="회원추가정보 입력하고 추천친구를 찾아보세요<br>";
				
				html+="<a href=\"/addpost\">회원정보추가하기</a>";
				
				return html;
				
			}

		}
		
		HashMap<String,List<Member>> recommend = (HashMap<String,List<Member>>)br.getValue();
		html+="<div id=\"main\" >";
		
		//넘겨받은 객체에 대학교로 친구추천이 비어잇을경우
		if(recommend.get("university")==null) {
			
		html+="<h2>추천할수있는 대학교 친구가 없어요!</h2><hr>";
			
		} else {			//추천할사람잇을경우
			
			System.out.println("작동완료");
			
		}
		
		//넘겨받은 객체에 고등학교 친구추천이 비어잇을경우
		if(recommend.get("high")==null) {
		
		html+="<h2>추천할수있는 고등학교 친구가 없어요!</h2><hr>";

		} else { 			//추천할사람잇을경우 
			
			System.out.println("작동완료");
			
		}
		
		//넘겨받은 객체에 중학교 친구추천이 비어잇을경우
		if(recommend.get("middle")==null) {
			
		html+="<h2>추천할수있는 중학교 친구가 없어요!</h2><hr>";
			
		} else {			//추천할사람잇을경우
			
			System.out.println("작동완료");
			
		}
		
		//넘겨받은 객체에 초등학교 친구추천이 비어잇을경우
		if(recommend.get("row")==null) {
			
		html+="<h2>추천할수있는 초등학교 친구가 없어요!</h2><hr>";
			
		} else {			//추천할사람잇을경우
			
			System.out.println("작동완료");
			
		}
		/*
		for(Member member : recommend.get("middle")) {
			html+="<div class=\"jb-wrap\">";
			html+="<div class=\"jb-image\">";
			html+="<img src=\"/board/attach/"+member.getProfile()+"\"class=\"img-thumbnail\" width=\"100px\" height=\"100px\"/>";
			html+="</div>";
			html+="<div class=\"jb-text\">";
			html+= "<a href=personpage/"+member.getSn()+">";
			html+=member.getName();						
			html+="</a>";
			html+="</div>";					
			html+="</div>";
			System.out.println(member.getId());
			
		}
		
		
		html+="</div>";
*/
		return html;
	}
	
	@ResponseBody
	@RequestMapping(value="commendage",method= {RequestMethod.POST},produces="text/plain;charset=UTF-8")
	public String 같은나이(HttpSession session) {
		
		int 회원번호 = (Integer)session.getAttribute("sn");
		
		String html="";
		
		html+="<h2>나이가 같은 알 수도 있는 사람</h2><hr>";
		
		BusinessResult br = friendmanage.나이로친구추천(회원번호);
		
		if(br.getCode()!=ERRORCODE.NORMAL) {
			
			if(br.getCode()==ERRORCODE.존재하지않는회원) {
				
				System.out.println(br.getMsg());
				
				html += br.getMsg();
				
				html += "<hr>";
				
				return html;
				
			}
			
			if(br.getCode()==ERRORCODE.추천친구없음) {

				html += br.getMsg();
				
				html += "<hr>";
				
				return html;
				
			}
		}

		List<Member> 추천친구 = (List<Member>)br.getValue();
		
		for(Member member : 추천친구) {
			html+="<div id=\"main\" >";
			html+="<div class=\"jb-wrap\">";
			html+="<div class=\"jb-image\">";
			html+="<img onclick=\"move("+member.getSn()+")\" src=\"/board/attach/"+member.getProfile()+"\"class=\"img-thumbnail\" width=\"500px\" height=\"500px\"/>";
			html+="</div>";
			html+="<div class=\"jb-text\">";	
			html+= "<a href=personpage/"+member.getSn()+">";
			html+=member.getName();
			html+="</a>";
			html+="</div>";					
			html+="</div>";
			
		}
		html+="</div><hr>";
		
		
		return html;
	}
	
	//testCode
	/*
	@RequestMappin("testfriend")
	public String get친구테스트() {
				
		//1.요청페이지(.jsp)
		
		//2.업무
		BusinessResult br = 회원관리자.전체회원출력();
		
		List<Member> members = (List<Member>)br.getValue();
		//친구관계 더미데이터
		//1번 회원부터 5번회원까지 친구더미데이터 생성 준비
		for(int j = 1; j<2; j++) {
			List<Integer> counter = new ArrayList<Integer>();
			System.out.println(j+" 번 회원 친구 추가중 현제 카운터 크기는? "+counter.size());
			br = 회원관리자.회원정보조회(j);
			Member member = (Member)br.getValue();
			int my_sn = j;
			
			
			if(member.getFriends()!=null) {
				
				counter.add(my_sn);
				
				for(Friend friend : member.getFriends()) {
					
					counter.add(friend.getFriend_sn().getSn());
					
					System.out.println("counter가 이미 친구인"+friend.getFriend_sn().getSn()+"를 저장했어요!");
					
				}
				
				}
			
				System.out.println(counter.size()+"현제 카운터의 크기입니다!");
			
		for(int i=0; i<5; i++) {
			
			int friend_sn;
			Integer 확인번호=0;
			Integer	랜덤회원=0;
			int counting=0;
			
			while(true) {
				
				System.out.println("다시 시작!");
				
				랜덤회원 = (int)((Math.random()*members.size())+1);
				
				확인번호 = 랜덤회원;
				
				for(Integer count : counter) {

					if(count==확인번호) {
						
						System.out.print(count+"저장된 번호");
						System.out.println(확인번호+"돌고잇는 번호");
						
						System.out.println(확인번호+"랜덤번호");
						
						counting += 1;
					
					}
					
					if(counter.indexOf(count)==counter.size()-1 && counting==0) {
						
						System.out.println("증복되지 않은 회원을 찾앗어요!");

					}

					}
				
					friend_sn = 확인번호;
					System.out.println(확인번호+"번 회원은 친구신청을 안했어요!");
		            br = friendmanage.친구신청(my_sn, friend_sn);
		            br = friendmanage.친구수락(friend_sn, my_sn);
		            counter.add(friend_sn);
		            
		               System.out.println(확인번호+"를 counter에 추가하고 while문을 종료합니다!");
		               System.out.println(counter.size()+"현제 카운터의 크기입니다.");
		               break;
				}

			}
		}

			

		return "board/더미생성";
	
	}
	*/

}