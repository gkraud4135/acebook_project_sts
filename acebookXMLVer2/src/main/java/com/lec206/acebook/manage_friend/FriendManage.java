package com.lec206.acebook.manage_friend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lec206.acebook.dataservice_friend.IFriendDAO;
import com.lec206.acebook.dataservice_member.IMemberDAO;
import com.lec206.acebook.util.BusinessResult;
import com.lec206.acebook.util.ERRORCODE;
import com.lec206.acebook.vo_friend.Friend;
import com.lec206.acebook.vo_member.Member;
import com.lec206.acebook.vo_member.MemberSchool;

@Service
public class FriendManage implements IFriendManage {
	
	@Autowired IFriendDAO friendDAO;
	@Autowired IMemberDAO memberDAO;

	@Override
	@Transactional
	public BusinessResult 친구신청(int my_sn, int friend_sn) {
		//업무
		
		//1-1업무규칙검사
		
		//1-2업무실행
			
		//DB이용여부(friendDAO.request, friendDAO.checkrequest)
		
		//상대방과 자신 둘다 신청중이지 않을경우에만 친구신청가능
		if(friendDAO.checkrequest(my_sn, friend_sn) || friendDAO.checkrequest(friend_sn , my_sn)) {
		
			return new BusinessResult(ERRORCODE.신청중,"이미신청중이거나 신청받았습니다.");
		
		}
		
		friendDAO.request(my_sn, friend_sn);
		
		return new BusinessResult();
	}

	@Override
	@Transactional
	public BusinessResult 친구수락(int mysn, int friendsn) {
		
		//업무
		
		//1-1업무규칙검사
	
		//1-2업무실행

		//DB이용여부(friendDAO.accept)
		
		friendDAO.accept(mysn, friendsn);
		
		return new BusinessResult();
	}

	@Override
	@Transactional
	public BusinessResult 친구거절(int my_sn, int friend_sn) {
		
		//업무
		
		//1-1업무규칙검사
	
		//1-2업무실행

		//DB이용여부(friendDAO.reject)
		
		friendDAO.reject(my_sn, friend_sn);
		
		return new BusinessResult();
	}

	@Override
	@Transactional
	public BusinessResult 친구차단(int my_sn, int friend_sn) {
		
		//업무
		
		//1-1업무규칙검사
	
		//1-2업무실행

		//DB이용여부(friendDAO.block)
		
		friendDAO.block(my_sn, friend_sn);
		
		return new BusinessResult();
	}

	@Override
	@Transactional
	public BusinessResult 친구삭제(int my_sn, int friend_sn) {
		
		//업무
		
		//1-1업무규칙검사
	
		//1-2업무실행

		//DB이용여부(friendDAO.deleteFriend)
		
		friendDAO.deleteFriend(my_sn, friend_sn);
		friendDAO.deleteFriend(friend_sn, my_sn);

		return new BusinessResult();
		
	}

	@Override
	@Transactional
	public BusinessResult 받은친구요청(int mysn) {
		
		//업무
		
		//1-1업무규칙검사
		if(friendDAO.requests(mysn).size()==0) {
			
			return new BusinessResult(ERRORCODE.추천친구없음,"받은 친구요청이 없습니다.");
			
		}
		//1-2업무실행

		//DB이용여부(friendDAO.requests)
		
		List<Friend> friends = friendDAO.requests(mysn);
		
		System.out.println(friends.size());
		
		return new BusinessResult(friends);
	}

	@Override
	@Transactional
	public BusinessResult 요청한친구목록(int mysn) {
		
		//업무
		
		//1-1업무규칙검사
	
		//1-2업무실행

		//DB이용여부(friendDAO.requested)		

		if(friendDAO.requested(mysn).size()==0) {
				
		return new BusinessResult(ERRORCODE.추천친구없음,"요청한 친구 목록이 없습니다.");
				
		}
		
	    List<Friend> friend=friendDAO.requested(mysn);

	    return new BusinessResult(friend);
	}

	@Override
	@Transactional
	public BusinessResult 신청혹은수락(int sn, int friendsn) { //수정요망
		
		//업무
		
		//1-1업무규칙검사
	
		//1-2업무실행

		//DB이용여부(friendDAO.requests)	
		
	      //기본은 친구신청   
	      int 요청확인=0;
	      
	      List<Friend> 내가받은신청목록 =  friendDAO.requests(sn);
	      Member member = memberDAO.findSimple(sn);
	      
	      //상대가 나한태 친구요청 걸엇을경우
	      if(내가받은신청목록 != null) {
	    	  
	      for(Friend friend : 내가받은신청목록) {

	    	  if(friend.getMy_sn().getSn()==friendsn) {
	    		  
	    		  요청확인 = 1;
	    	  }
	      }
	      
	      } else {
	    	  
	    	  요청확인 = 1;
	    	  
	      }
	      
	      //친구인사람 페이지
	      if(member.getFriends()!=null) {
	    	  
	    	 //내 친구목록 출력
	         for(Friend friend : member.getFriends()) {
	        	 
	        	//내친구의 회원번호 == 입력받은 회원의 번호가 같다면 친구임
	            if(friend.getFriend_sn().getSn()==friendsn) {
	            	
	            	요청확인 = 2;
	            }
	         }
	      }
	      
	      //자신의 페이지
	      if(sn==friendsn) {
	         
	         요청확인 = 3;
	         
	      }
	      
	      //
	      if(friendDAO.requested(sn)!=null) {
	         
	         List<Integer> 신청중인번호 = new ArrayList<Integer>();
	         
	         for(Friend friend : friendDAO.requested(sn)) {
	        	 
	        	 if(friend.getFriend_sn().getSn()==friendsn) {
	                  
	        		 신청중인번호.add(friend.getFriend_sn().getSn());
	                  
	               }
	         }
	         
	         if(신청중인번호.size()!=0) {
	            
	            요청확인 = 4;
	            
	         }
	         
	      }
		
	      return new BusinessResult(요청확인);
	}

	@Override
	@Transactional
	public BusinessResult 내친구수(int mysn) {
		
		//업무
		
		//1-1업무규칙검사
	
		//1-2업무실행

		//DB이용여부(friendDAO.friendCounter)
		
		return new BusinessResult(friendDAO.friendCounter(mysn));
	}

	@Override
	public BusinessResult 내친구목록(int mysn) {
		
		//업무
	      
	    //1-1업무규칙검사
	   
	    //1-2업무실행
	      
	    //DB이용여부(memberDAO.findSimple);
		
	      Member member = memberDAO.findSimple(mysn);
	      
	      if(member.getFriends()==null || member.getFriends().size()==0) {
	         
	         return new BusinessResult(ERRORCODE.추천친구없음,"등록된 친구가 없습니다.");
	         
	      }
	      
	      List<Friend> 내친구목록 = member.getFriends();
	      
	      return new BusinessResult(내친구목록);
	   }

	@Override
	public BusinessResult 친구상태출력(int mysn) {
		
		 //업무
	      
	      //1-1업무규칙검사
	   
	      //1-2업무실행
	      
	      //DB이용여부(memberDAO.myfriendstate);
		
		Member member = memberDAO.myfriendstate(mysn);
		
		if(member.getFriends()==null) {
			
			return new BusinessResult(ERRORCODE.존재하지않는회원,"친구가 없어요");
			
		}
		
		return new BusinessResult(member);
	}

	@Override
	public BusinessResult 학교로친구추천(int mysn) {
		
		Member member = memberDAO.findSimple(mysn);
		
		//실제로 추천해줄 친구목록을 보낼 Map(SchoolValue, List추천친구목록)
		Map<String, List<Member>> recommendFriend = new HashMap<String,List<Member>>();
		
		//추천친구목록의 실제데이터
		List<Member> recommendMember = null;
		List<MemberSchool> recoschool = null;

		//업무
	      
	    //1-1업무규칙검사
	   
	    //1-2업무실행
	      
	    //DB이용여부(memberDAO.findBySn);
		
		if(member.getSchool()==null) {
			
			return new BusinessResult(ERRORCODE.추천친구없음,"등록된 학교정보가 없어요!");
			
		}
		
		//회원의 대학교 정보가 있을경우
		if(member.getSchool().getUniversity() != null) {
		//추천친구초기화
		recommendMember = new ArrayList<Member>();
		//가져올 객체 초기화
		recoschool = new ArrayList<MemberSchool>();
		
		//DB이용여부(friendDAO.recommendSchool,1일경우 대학교, 대학교정보 입력, 자신의 회원번호);
		recoschool = friendDAO.recommendSchool(1, member.getSchool().getUniversity(), mysn);
		
		//test용 코드
		String a = recoschool.isEmpty() ? "추천회원이 없습니다" : "추천회원이 있습니다";
		System.out.println(a);
		//test코드 종료
		
		//생성된 memberSchool 객체를 이용하여 member객체 꺼내오기
		for(MemberSchool reco : recoschool) {

			if(reco.getSn() instanceof Member) {
			
			//추천회원객체에 member객체전달
			recommendMember.add(reco.getSn());
			
			}
			
		}
		//완성된 List<Member> 객체를 Map에 넣는다.
		recommendFriend.put("university", recommendMember);
		
		} // end-university reco
		
		
		if(member.getSchool().getHigh() != null) {
			
		recommendMember = new ArrayList<Member>();
		
		recoschool = new ArrayList<MemberSchool>();
			
		recoschool = friendDAO.recommendSchool(2, member.getSchool().getHigh(), mysn);
		
		for(MemberSchool reco : recoschool) {

			if(reco.getSn() instanceof Member) {
			
			recommendMember.add(reco.getSn());
			
			}
			
		}
		
		recommendFriend.put("high", recommendMember);
		
		}
		
		if(member.getSchool().getMiddle() != null) {
			
		recommendMember = new ArrayList<Member>();
		
		recoschool = new ArrayList<MemberSchool>();
			
		recoschool = friendDAO.recommendSchool(3, member.getSchool().getMiddle(), mysn);
		
		for(MemberSchool reco : recoschool) {
			
			if(reco.getSn() instanceof Member) {
			
			recommendMember.add(reco.getSn());
			
			}
			
		}
		
		recommendFriend.put("middle", recommendMember);
		
		}
		
		if(member.getSchool().getRow() != null) {
			
		recommendMember = new ArrayList<Member>();
		
		recoschool = new ArrayList<MemberSchool>();
			
		recoschool = friendDAO.recommendSchool(4, member.getSchool().getRow(), mysn);
		
		for(MemberSchool reco : recoschool) {
			
			if(reco.getSn() instanceof Member) {
			
			recommendMember.add(reco.getSn());
			
			}
			
		}
		
		recommendFriend.put("row", recommendMember);
		
		}

		return new BusinessResult(recommendFriend);
	}

	@Override
	public BusinessResult 나이로친구추천(int mysn) {
		
		//업무
	      
	    //1-1업무규칙검사
	   
	    //1-2업무실행
	      
	    //DB이용여부(memberDAO.findSimple);
		
		Member member = memberDAO.findSimple(mysn);
		
		//if회원의 나이가 없을경우[not null이라 그럴 경우가 없겟지만 혹시나 해서 만들어뒀음.
		if(member.getAge()==null) {
			
			return new BusinessResult(ERRORCODE.존재하지않는회원,"나이가 등록되지 않은 회원이 있다고?");
			
		}
		//나이가 등록될경우 나이로 추천친구 가지고 오는 recommendAge List생성
		List<Member> recommendAge = friendDAO.recommendAge(member.getAge().toString(),mysn);
		
		//객체가 null일경우 == 추천친구가 없다,나이가 같은 회원이 없다
		if(recommendAge == null || recommendAge.size()==0) {
			
			return new BusinessResult(ERRORCODE.추천친구없음,"나이가 같은친구가 없어요!");
					
		}
		
		//객체가 null이 아닐경우 == 추천친구가 있음.
		return new BusinessResult(recommendAge);
	}

}
