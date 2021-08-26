package com.lec206.acebook.dataservice_friend;

import java.util.List;

import com.lec206.acebook.vo_friend.Friend;
import com.lec206.acebook.vo_member.Member;
import com.lec206.acebook.vo_member.MemberSchool;

public interface IFriendDAO {
	
	//친구신청
	void request(int mysn,int friendsn);
	
	//친구신청을 해놧는지 확인하는 메서드
	boolean checkrequest(int mysn, int friendsn);
	
	//친구신청목록확인
	List<Friend> requests(int mysn);
	
	//친구신청수락
	void accept(int mysn,int friendsn);
	
	//친구신청거절
	void reject(int mysn, int friendsn);
	
	//상태 > 차단
	void block(int mysn, int friendsn);
	
	//친구삭제
	void deleteFriend(int mysn, int friendsn);
	
	//나의친구목록저장용
	List<Member> selectFriends(int sn);
	
	//나의친구수저장용
	int friendCounter(int mysn);
	
	//내가 신청한 친구목록
	List<Friend> requested(int mysn);
	
	//학교로 친구 추천
	List<MemberSchool> recommendSchool(int schooltype,String schoolvalue,int sn);
	
	//나이로 친구 추천
	List<Member> recommendAge(String age, int sn);

}
