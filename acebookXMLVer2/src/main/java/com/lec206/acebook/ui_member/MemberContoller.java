package com.lec206.acebook.ui_member;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.lec206.acebook.manage_member.IMemberManage;
import com.lec206.acebook.util.BusinessResult;
import com.lec206.acebook.util.ERRORCODE;
import com.lec206.acebook.util.GENDER;
import com.lec206.acebook.vo_member.Member;
import com.lec206.acebook.vo_member.MemberPost;
import com.lec206.acebook.vo_member.MemberSchool;

@Controller
public class MemberContoller {
	
	@Autowired IMemberManage membermanage;
	
	@RequestMapping(value = "main", method = RequestMethod.GET)
	public ModelAndView getAcebook(HttpServletRequest request,HttpSession session) {
		
		//1.요청페이지(acebook.jsp)
		ModelAndView mv = new ModelAndView();
		
		//2.업무(메인페이지 요청)
		System.out.println("acebook으로 이동중");
		String ip=request.getRemoteAddr();
		
	    BusinessResult br = membermanage.findmemberByip(ip);
	    if(br.getCode()==ERRORCODE.존재하지않는회원){System.out.println("Null: ip history");}
	    List<Member> members = (List<Member>)br.getValue();
	    
	    
	    if(session.getAttribute("sn")!=null) {
			br = membermanage.findSimpleMember((Integer)session.getAttribute("sn"));

			Member member = (Member)br.getValue();

			//}일반 로그인 처리
			
	        //3.경로지정
			//get방식 메인 접근시 main에 member객체를 전달
			mv.addObject("test",true);
			mv.addObject("member", member);
			mv.setViewName("acebook");
			
			return mv;
	    }
	    
	    mv.addObject("members", members);
		
		//3.경로지정
		mv.setViewName("acebook");
		return mv;
		
	}
	
	@RequestMapping(value = "main", method = RequestMethod.POST)
	public ModelAndView postAcebook(String id, String password,HttpSession session,HttpServletRequest request) {
		
		//요청페이지(acebook.jsp)
		ModelAndView mv = new ModelAndView();
		
	    //2.업무(로그인업무)
		String ip = request.getRemoteAddr();
		
		BusinessResult br = membermanage.loginService(id, password, ip);

		//비정상코드입력시
		if(br.getCode()!=ERRORCODE.NORMAL) {
			
			//회원상태가 회원이 아니라 인증중일경우
			if(br.getCode()==ERRORCODE.인증중) {
				
				mv.setViewName("acebook");
				mv.addObject("error",br.getCode());
				mv.addObject("msg", br.getMsg());
				
				return mv;

			}

			//없는 회원일 경우[비밀번호,아이디 틀렷을시 이쪽으로 이동됨]
			if(br.getCode()==ERRORCODE.존재하지않는회원) { 
				
				mv.setViewName("acebook");
				mv.addObject("error",br.getCode());
				mv.addObject("msg", br.getMsg());
				
				//ip로그인처리{
			    br = membermanage.findmemberByip(ip);
			    if(br.getCode()==ERRORCODE.존재하지않는회원){System.out.println("Null: ip history");}
			    List<Member> members = (List<Member>)br.getValue();
			    
			    mv.addObject("members", members);
				//}ip로그인처리
				
				return mv;
				
			}
			
			//첫로그인일경우에는 회원기본정보 등록창으로 보내줌
			if(br.getCode()==ERRORCODE.첫로그인) {
				
				HashMap<String, Object> state = (HashMap<String, Object>)br.getValue();
				session.setAttribute("sn", state.get("sn"));
				session.setAttribute("name", state.get("name"));
				session.setAttribute("id", state.get("id"));
				mv.setViewName("member/학교등록");
				return mv;
				
			}
			
		} //예외처리 종료

		//일반 로그인 처리{
		HashMap<String, Object> state = (HashMap<String, Object>)br.getValue();
		
		session.setAttribute("sn", state.get("sn"));
		session.setAttribute("name", state.get("name"));
		session.setAttribute("id", state.get("id"));
		
		br = membermanage.findSimpleMember((Integer)state.get("sn"));

		Member member = (Member)br.getValue();

		//}일반 로그인 처리
		
        //3.경로지정
		//정상로그인시 main에 member객체를 전달
		mv.addObject("test",true);
		mv.addObject("member", member);
		mv.setViewName("acebook");
		System.out.println(mv.getViewName()+"으로 이동중");
		
		return mv;
		
	}
	
	@RequestMapping("logout")
	public ModelAndView logoutService(HttpSession session) {
		
		//1.요청페이지(acebook.jsp)

		ModelAndView mv = new ModelAndView();
		
		//2.업무(session에서 로그인정보 삭제)
		BusinessResult br = membermanage.logoutService((Integer)session.getAttribute("sn"));
		
		//비정상코드 입력시
		if(br.getCode()!=ERRORCODE.NORMAL) {
			
		}
		
		if(session!=null) {
  			
			//세션이 살아잇을경우 세션을 무효화시킴? 제거시킴?
	     	session.invalidate();
	      			
	      }
		
		//3.경로지정
		mv.setViewName("redirect:/main");
		System.out.println("logoutService완료");
			
		return mv;
	}
	
	@RequestMapping(value = "member", method = RequestMethod.GET)
	public String getInsertMember() {
		
		//요청페이지(acebook.jsp)
		
	    //2.업무(회원등록준비)
		
        //3.경로지정
		System.out.println("회원등록창으로 이동중");
		
		return "member/회원등록창";
		
	}
	
	@RequestMapping(value="member", method = RequestMethod.POST)
	public ModelAndView postInsertMember(Member member) {
			
		//1.요청페이지(회원등록창.jsp)
		ModelAndView mv = new ModelAndView();
			
		//2.업무(입력받은 회원데이터 저장)
		BusinessResult br = membermanage.preparesaveMember(member);
				
		//비정상코드입력시
		if(br.getCode()!=ERRORCODE.NORMAL) {}
									
		//3.경로지정
		mv.addObject("message", "close");
		mv.setViewName("member/회원등록창");
		System.out.println(mv.getViewName()+"으로 이동중");
			
		return mv;
		
	}

	@RequestMapping(value="id", method = RequestMethod.GET)
	public String getidisin() {
			
		//1.요청페이지(회원등록창.jsp)
				
		//2.업무
	
		//3.경로지정
	
		System.out.println("아이디중복검사창으로 이동중");
			
		return "member/아이디중복검사창";
		
	}
	
	@RequestMapping(value="id", method = RequestMethod.POST)
	public ModelAndView postidisin(String id) {
			
		//1.요청페이지(아이디중복검사.jsp)
		ModelAndView mv = new ModelAndView();
		
		//사용가능==true : 아이디중복,사용불가 
		//사용가능==false : 아이디사용가능
		boolean 사용가능 = true;
		
		//에러코드처리시에 뷰페이지 설정을 위해서, 위에다가 뷰페이지를 지정해줌.
		mv.setViewName("member/아이디중복검사창");
				
		//2.업무
		BusinessResult br = membermanage.idisin(id);
		
		//비정상코드입력시
		if(br.getCode()!=ERRORCODE.NORMAL) {
			
			//아이디 공백시
			if(br.getCode()==ERRORCODE.아이디공백) {
				
				mv.addObject("msg",br.getMsg());
				mv.addObject("use",사용가능);
				mv.addObject("id",id);
				
				return mv;
				
			}
			
			//이메일 형식이 아닐시
			if(br.getCode()==ERRORCODE.이메일형식) {
				
				mv.addObject("msg",br.getMsg());
				mv.addObject("use",사용가능);
				mv.addObject("id",id);

				return mv;
				
			}
			
		}

		//3.경로지정
		mv.addObject("msg","사용가능한 아이디입니다.");
		mv.addObject("use",사용가능=false);
		mv.addObject("id",id);

		return mv;
		
	}
	
	@RequestMapping(value="addschool", method = RequestMethod.GET)
	public String getsaveSchool() {
			
		//1.요청페이지(acebook.jsp)
				
		//2.업무(로그인 회원 학교정보 등록 혹은 수정으로 이동)
	
		//3.경로지정
		System.out.println("saveSchool 으로 이동중");
			
		return "/member/학교등록";
		
	}
	
	@RequestMapping(value="addschool", method = RequestMethod.POST)
	public ModelAndView postsaveSchool(MemberSchool memberschool, HttpSession session) {
			
		//1.요청페이지(member/saveSchool.jsp)
		
		ModelAndView mv = new ModelAndView();
			
		//2.업무(회원객체 넣어주고 학교 등록혹은 수정)
		
		int sn = (Integer)session.getAttribute("sn");
				
		BusinessResult br = membermanage.findSimpleMember(sn);
		
		memberschool.setSn((Member)br.getValue());
		
		br = membermanage.saveSchool(memberschool);
				
		//비정상코드입력시
		if(br.getCode()!=ERRORCODE.NORMAL) {}
									
		//3.경로지정
		//학교정보 입력시 savePost으로 이동
		mv.setViewName("/member/학교등록");
		
		return mv;
	
	}
	
	@RequestMapping(value="addpost", method = RequestMethod.GET)
	public String getsavePost() {
				
		//1.요청페이지(savePost.jsp)
				
		//2.업무
					
			//비정상코드입력시
			//if(br.getCode()!=ERRORCODE.NORMAL) {}
										
		//3.경로지정
		System.out.println("savePost 으로 이동중");
		
		return "/member/주소등록";
			
		}
	
	@RequestMapping(value="addpost", method = RequestMethod.POST)
	public ModelAndView postsavePost(MemberPost memberpost, HttpSession session) {
		
		//1.요청페이지(member/saveSchool.jsp)
		ModelAndView mv = new ModelAndView();
		int sn = (Integer)session.getAttribute("sn");
			
		//2.업무(회원객체 넣어주고 학교 등록혹은 수정)				
		BusinessResult br = membermanage.findSimpleMember(sn);
		
		memberpost.setSn((Member)br.getValue());
		
		mv.addObject("member", (Member)br.getValue());
		
		br = membermanage.savePost(memberpost);
				
		//비정상코드입력시
		if(br.getCode()!=ERRORCODE.NORMAL) {
			
		}
									
		//3.경로지정
		//주소정보 입력시 메인으로 이동
		mv.setViewName("redirect:/main");

		return mv;
			
		}
	
	@RequestMapping("change")
	public String getChangeState(HttpSession session) {
		   
		//1.요청페이지()
		   
		//2.업무(받은 요청에 따라 학교수정 혹은 주소수정으로 연결)
		   
		//3.경로지정
		return "/member/상세정보변경창";		   
	}
	
	@RequestMapping(value="changeschool", method = RequestMethod.GET)
	public ModelAndView getChangeSchool(HttpSession session) {
		
		//1.요청페이지(상세정보변경창.jsp)
		ModelAndView mv = new ModelAndView();
		Member member  = null;
				
		//2.업무(학교정보수정요청, 회원번호로 등록정보 입력시켜주기)
		int sn = (Integer)session.getAttribute("sn");
		BusinessResult br = membermanage.findSimpleMember(sn);
		
		//비정상코드입력시
		if(br.getCode()!=ERRORCODE.NORMAL) {
			
		}
		
		member = new Member();
		
		member = (Member)br.getValue();
		

		//3.경로지정
		mv.addObject("member",member);
		mv.setViewName("/member/학교변경창");
		System.out.println(mv.getViewName()+"으로 이동중");
		
		return mv;
		
	}
	
	@RequestMapping(value="changeschool", method = RequestMethod.POST)
	public ModelAndView postChangeSchool(MemberSchool memberschool, HttpSession session) {
			
		//1.요청페이지(상세정보변경창.jsp)
		ModelAndView mv = new ModelAndView();
		int sn = (Integer)session.getAttribute("sn");

		//2.업무(학교정보 수정시켜주기)
		BusinessResult br = membermanage.findSimpleMember(sn);
		
		memberschool.setSn((Member)br.getValue());
		
		br = membermanage.saveSchool(memberschool);
		
		//비정상코드입력시
		if(br.getCode()!=ERRORCODE.NORMAL) {}

		//3.경로지정
		mv.addObject("member", (Member)br.getValue());
		mv.setViewName("/member/상세정보변경완료");
		
		return mv;
		
	}
	
	@RequestMapping(value = "changepost", method = RequestMethod.GET)
	public ModelAndView getChangePost(HttpSession session) {

		 //1.요청페이지(주소변경창.jsp)
		ModelAndView mv = new ModelAndView();
		int sn = (int)session.getAttribute("sn");
		
		 //2.업무
		BusinessResult br = membermanage.findSimpleMember(sn);
		
		//주소변경창 가기전 기존에 있던 주소 정보를 주소변경창 value값에 넣어주기위한 작업
		if(br.getCode()!=ERRORCODE.NORMAL) {}
		Member member = (Member)br.getValue();
		
		//3.경로지정
		mv.setViewName("/member/주소변경창");
		mv.addObject("member", member);
		
		return mv;
	   
	}
	   
	@RequestMapping(value="changepost",method = RequestMethod.POST)
	public ModelAndView postChangePost(MemberPost memberpost, HttpSession session) {
				
		//1.요청페이지(주소등록.jsp)
		ModelAndView mv = new ModelAndView();
		int sn = (int)session.getAttribute("sn");
			
		//2.업무
		BusinessResult br = membermanage.findSimpleMember(sn);
		//주소정보 저장전에 session에서 회원번호로 회원 객체정보저장
		memberpost.setSn((Member)br.getValue());
		//만들어진 회원주소정보 저장
		br = membermanage.savePost(memberpost);
		
		if(br.getCode()!=ERRORCODE.NORMAL) {}
									
		//3.경로지정
		mv.setViewName("/member/상세정보변경완료");
		mv.addObject("member", memberpost);
			
		return mv;
			
	}

	@RequestMapping("iplogdelete/{sn}")
	public ModelAndView iplogdelete(@PathVariable int sn) {
		
		//1.요청페이지(acebook.jsp)
		ModelAndView mv = new ModelAndView();
		
		//2.업무
		BusinessResult br = membermanage.deleteip(sn);

		//3.경로지정
		mv.setViewName("redirect:/main");

		return mv;

	}

    //test_code{
    @RequestMapping(value="testmember", method = RequestMethod.GET)
    public String get회원등록테스트() {
             
          //1.요청페이지(.jsp)
             
          //2.업무
             
             //회원 더미데이터 작성 랜덤이름 작성
	            String[] 성 = {"김","박","이","최","오","성","진","목","판","강","윤"};
	            String 상현 = "상현"; 
	            String 성상현; 
	            String 학명 = "학명"; 
	            String 이학명; 
	            String 영웅 = "영웅";
	            String 이영웅; 
	            List<String> 이름들 = new ArrayList<String>();
	            for(int i=0; i<성.length; i++) {
	            
	            성상현 = 성[i]+상현;
	            이름들.add(성상현);
	            이학명 = 성[i]+학명;
	            이름들.add(이학명);
	            이영웅 = 성[i]+영웅;
	            이름들.add(이영웅);
	
              }
               //랜덤이름 작성완료
               
               //랜덤출생년월일 작성
               List<Date> 생년월일들 = new ArrayList<Date>();
               for(int i=1980; i<1999; i++) {
                  String 형변환 = String.valueOf(i);
                  형변환 = 형변환+"-"+(int)((Math.random()*10000)%10+1)+"-"+(int)((Math.random()*30000)%30+1);
                  Date 생년월일 = Date.valueOf(형변환);
                  생년월일들.add(생년월일);
                  
               }
               
               BusinessResult br = membermanage.testSelect();
               
               List<Member> members = (List<Member>)br.getValue();
               
               for(int i = members.size()+1; i<members.size()+11; i++) {
                  
                  Member member = new Member();
                  ArrayList<String> 랜덤이름 = new ArrayList<String>();
                  ArrayList<Date> 랜덤생일 = new ArrayList<Date>(); 

                  int randomname = (int)(Math.random()*이름들.size());
                  랜덤이름.add(이름들.get(randomname));
                  System.out.println("입력받은 아이디 = test"+i);
                  member.setId("test"+i);
                  //i번째에 추가된 랜덤이름을 맴버이름으로 저장
                  member.setName(랜덤이름.get(0));
                  member.setPassword("1234");
                  member.setGender(GENDER.남자);
                  int randomage = (int)(Math.random()*생년월일들.size());
                  랜덤생일.add(생년월일들.get(randomage));
                  //i번째에 추가된 랜덤생일을 맴버생일로 저장
                  member.setAge(랜덤생일.get(0));
                  
                  br = membermanage.testinsert(member);
                  
               }
    
             //비정상코드입력시
             //if(br.getCode()!=ERRORCODE.NOMAL) {}
                               
          //3.경로지정

          return "board/더미생성";
          
       }
	
    @RequestMapping(value="logintest", method = RequestMethod.GET)
    public String 로그인test(HttpServletRequest request) {
       
       BusinessResult br = membermanage.testSelect();
       
       List<Member> 회원들 =  (List<Member>)br.getValue();
       
       회원들.stream();
       
       String ip = request.getRemoteAddr();
       
          for(Member member : 회원들) {
             
          br = membermanage.loginService(member.getId(), member.getPassword(), ip);
          
          }
       
             
       return "board/더미생성";

       }

    @RequestMapping(value="logouttest", method = RequestMethod.GET)
    public String logoutServicetest() {
       BusinessResult br = membermanage.testSelect();
       
       List<Member> 회원들 =  (List<Member>)br.getValue();
    
          for(Member member : 회원들) {
             
          br = membermanage.logoutService(member.getSn());
          
          }
       
             
       return "board/더미생성";

       }
    
  //test_code
	
	
	
	//@RequestMapping(value="setupGET", method = RequestMethod.GET)
	public String geturl() {
			
		//1.요청페이지(.jsp)
				
		//2.업무
	
		//3.경로지정
	
			//System.out.println("viewname 으로 이동중");
			
		return "setupurl";
		
	}

	//@RequestMapping(value="setupPOST", method = RequestMethod.POST)
	public ModelAndView posturl() {
			
		//1.요청페이지(회원등록창.jsp)
		
		ModelAndView mv = new ModelAndView();
			
		//2.업무
				
		//BusinessResult br = 
				
		//비정상코드입력시
		
			//if(br.getCode()!=ERRORCODE.NORMAL) {}
			System.out.println("post");
									
		//3.경로지정
			
			//mv.addObject("keyname", object);
			//System.out.println(mv.getViewName()+"으로 이동중");
		
		return mv;
	
	}
	
	
}