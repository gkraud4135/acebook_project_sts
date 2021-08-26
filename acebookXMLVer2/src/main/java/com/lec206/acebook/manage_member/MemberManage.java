package com.lec206.acebook.manage_member;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.lec206.acebook.dataservice_member.IMemberDAO;
import com.lec206.acebook.util.BusinessResult;
import com.lec206.acebook.util.ERRORCODE;
import com.lec206.acebook.vo_member.Login;
import com.lec206.acebook.vo_member.Member;
import com.lec206.acebook.vo_member.MemberPost;
import com.lec206.acebook.vo_member.MemberSchool;

@Service
public class MemberManage implements IMemberManage {
	
	@Autowired IMemberDAO memberDAO;
	@Autowired JavaMailSender sender;

	@Override
	@Transactional
	public BusinessResult preparesaveMember(Member member) {
		
		//업무(입력받은 preparesaveMember)
		
		//1-1업무규칙검사(없음)
	
		//1-2업무실행
		
		if(member.getName() == null) {
			
			System.out.println("회원가입실패");

			return new BusinessResult(ERRORCODE.존재하지않는회원,"존재하지 않는 회원");
			
		}
		
		//DB이용여부(memberDAO.preparesave);
		memberDAO.preparesave(member);
		
		//email 인증
        String from = "acebookmaster@gmail.com";
        String to = member.getId();
        
        try {
        	
            MimeMessage mail = sender.createMimeMessage();
            
            MimeMessageHelper mailHelper = new MimeMessageHelper(mail,true,"UTF-8");
            // true는 멀티파트 메세지를 사용하겠다는 의미
            
            Random rand = new Random();
            String numStr = ""; //난수가 저장될 변수
            
            for(int i=0;i<4;i++) {
                
                //0~9 까지 난수 생성
                String ran = Integer.toString(rand.nextInt(10));
     
                numStr += ran;
            }

            System.out.println(numStr+"인증키");
            
            mailHelper.setFrom(from);

            mailHelper.setSubject("회원가입 이메일 인증");
            mailHelper.setText(new StringBuffer().append("<h1>[이메일 인증]</h1>")
            .append("<p>아래 링크를 클릭하시면 이메일 인증이 완료됩니다.</p>")
            .append("<a href='http://localhost:9001/member/signUpConfirm?email=")
            .append(to)
            .append("&authKey=")
            .append(numStr)
            .append("&sn=")
            .append(member.getSn())
            .append("' target='_blenk'>이메일 인증 확인</a>")
            .toString(),true);
            mailHelper.setTo(to);
            
            sender.send(mail);
            
        } catch(Exception e) {
        	
            e.printStackTrace();
            
        }
		
		return new BusinessResult();
		
	}

	@Override
	@Transactional
	public BusinessResult idisin(String id) {
		
		boolean 사용가능;

		//업무
		
		//1-1업무규칙검사[아이디유효성검사]
		
		if(id!=null) {
			
			 if(id.length()!=id.replaceAll(" ", "").length()) {
			      
	    		  return new BusinessResult(ERRORCODE.아이디공백, "아이디에 공백을 넣을수 없어요");
	    		  
	    	  }
	    	  
			if(!id.matches("^[A-z|0-9]([A-z|0-9]*)(@)([A-z]*)(\\.)([A-z]*)$")) {
				
				return new BusinessResult(ERRORCODE.이메일형식,"아이디가 이메일 형식이 아니에요!");
				
			}
		}
		
		//1-2업무실행
		
		//DB이용여부(memberDAO.isin);

			사용가능 = memberDAO.isin(id);
		      
			return new BusinessResult(사용가능);
		}

	@Override
	@Transactional
	public BusinessResult findSimpleMember(int sn) {
		
		//업무(findSimpleMember)

		Member member = null;
		
		//1-1업무규칙검사()
		
		//1-2업무실행
		
		if(memberDAO.findSimple(sn) instanceof Member) {
			
			member = new Member();
			
			//DB이용여부(memberDAO.findSimple);
			
			member = memberDAO.findSimple(sn);

		}
		
		//memberDAO로 찾은 회원이 없을경우 
		if(member == null) {
			
			return new BusinessResult(ERRORCODE.존재하지않는회원,"존재하지 않는 회원");
		}
		
		return new BusinessResult(member);
	}

	@Override
	@Transactional
	public BusinessResult saveSchool(MemberSchool memberschool) {
		
		//업무
		
		//1-1업무규칙검사(DB에 학교정보가 등록됫는지 확인후, 저장혹은 수정)
	
		//1-2업무실행
		
		//DB이용여부(memberDAO.schoolcounter);
		
		if(memberDAO.schoolcounter(memberschool.getSn().getSn())==0) {
			
			System.out.println("등록된 학교정보가 없어요!");
			
			//DB이용여부(memberDAO.schoolsave);
			memberDAO.schoolsave(memberschool);
			
			return new BusinessResult();
			
		}
		
		//DB에 저장된 학교정보가 있을경우에는 update를 함.
		//DB이용여부(memberDAO.schoolupdate);
		memberDAO.schoolupdate(memberschool);
		
		return new BusinessResult();
	}

	@Override
	@Transactional
	public BusinessResult savePost(MemberPost memberpost) {
		
		//업무
		
		//1-1업무규칙검사(DB에 주소정보가 등록됫는지 확인후, 저장혹은 수정)
	
		//1-2업무실행
		
		//DB이용여부(memberDAO.postcounter);
		
		if(memberDAO.postcounter(memberpost.getSn().getSn())==0) {
			
			System.out.println("등록된 학교정보가 없어요!");
			
			//DB이용여부(memberDAO.postsave);
			memberDAO.postsave(memberpost);
			
			return new BusinessResult();
			
		}
		
		System.out.println("등록된 학교정보가 있어요!");
		
		//DB에 저장된 주소정보가 있을경우에는 update를 함.
		//DB이용여부(memberDAO.postupdate);
		memberDAO.postupdate(memberpost);
		
		return new BusinessResult();
	}

	@Override
	@Transactional
	public BusinessResult loginService(String id, String password, String ip) {
		
		//업무
		Login login = null;
		
		//1-1업무규칙검사

		//DB이용여부(memberDAO.logcounter,memberDAO.login, memberDAO.logcheck,memberDAO.loginstate)
				
		//인증중인 회원인지 확인{
		
		HashMap<String, Object> state = memberDAO.logincheck(id, password);
		
		if(state != null) {
			
			System.out.println("인증중인 회원 loginService 시도!");
			
			return new BusinessResult(ERRORCODE.인증중,"이메일 인증부터 해주세요");
		}
		
		
		//loginService 시작{
		
		state = new HashMap<String, Object>();
		
		state = memberDAO.login(id, password);
		
		//비밀번호, 혹은 아이디가 존재하지 않을경우
		
		if(state == null) {

			return new BusinessResult(ERRORCODE.존재하지않는회원, "해당정보가 존재하지 않습니다.");
			
		}
		
		//1-2업무실행
		
		//loginService을 성공하였을경우
		
		login = new Login();
		
		login.setSn(memberDAO.findBySn((Integer)state.get("sn")));
		
		//아이피 loginService 조회
		int check = memberDAO.checkip(ip,(int)state.get("sn"));
		
		//아이피 loginService 저장
		if(check==0) {memberDAO.saveip(ip,(int)state.get("sn"));}
		

		//loginService을 처음했을경우 loginServicetable에 저장 {
		
		//loginService을 처음햇을경우에 상세정보입력창으로 보내줄 예정!
		
		if(memberDAO.logcounter(login.getSn().getSn())==0) {
		
			memberDAO.loginsave(login.getSn().getSn());
			
			memberDAO.loginstate(login.getSn().getSn());
			
			System.out.println("loginService성공+첫loginService");
			
			return new BusinessResult(state,ERRORCODE.첫로그인,"처음 loginService대상자");
		
		} //첫loginService종료}
		
		//loginService 1회 이후부터는 loginService정보만 업데이트 시켜줌{
		
		try {
			
			memberDAO.loginstate(login.getSn().getSn());
			
			} catch(Exception e) { 

			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}
		
		//}loginService 정상작동완료
		
		return new BusinessResult(state);
		
	}

	@Override
	@Transactional
	public BusinessResult logoutService(int sn) {
		
		//업무

		//1-1업무규칙검사

		//1-2업무실행
		
		//DB이용여부(memberDAO.logoutstate)

		memberDAO.logoutstate(sn);
		
		System.out.println(sn+"번 회원 logout처리 완료!");
		
		return new BusinessResult();
		
	}
	
	@Override
	@Transactional
	public BusinessResult findmemberByip(String ip) {
		
		//업무
		
		//1-1업무규칙검사
		

		//1-2업무실행

		//DB이용여부(loginDAO.logout)

		List<Member> member = memberDAO.findmemberByip(ip);
		
		if(member==null) {
			
			return new BusinessResult(ERRORCODE.존재하지않는회원);
			
		}
		
		return new BusinessResult(member);

	}
	
	@Override
	@Transactional
	public BusinessResult deleteip(int sn) {
		
		//업무
		
		//1-1업무규칙검사
		

		//1-2업무실행

		//DB이용여부(loginDAO.logout)

		memberDAO.deleteip(sn);

		return new BusinessResult();

	}
	
	@Override
	@Transactional
	public BusinessResult selectAll() {
		
		//업무
		
		//1-1업무규칙검사
		

		//1-2업무실행

		//DB이용여부(loginDAO.logout)
		List<Member> members = memberDAO.selectAll();
		
		System.out.println(members.size()+"크기");
		
		return new BusinessResult(members);
	}

	@Override
	@Transactional
	public BusinessResult testinsert(Member member) {
		
		memberDAO.testinsert(member);
		
		return new BusinessResult();
		
	}

	@Override
	public BusinessResult testSelect() {
		
		List<Member> members = memberDAO.testSelectAll();
		
		return new BusinessResult(members);
	}
	
	
	

}