package com.lec206.acebook.dataservice_member;

import java.util.HashMap;
import java.util.List;

import com.lec206.acebook.vo_friend.Friend;
import com.lec206.acebook.vo_member.Login;
import com.lec206.acebook.vo_member.Member;
import com.lec206.acebook.vo_member.MemberPost;
import com.lec206.acebook.vo_member.MemberSchool;

public interface IMemberDAO {
	
	//회원저장준비
	void preparesave(Member member);
	//중복검사
	boolean isin(String id);
	//인증코드저장
	void codeupdate(String code, String id);
	//회원승인중비
	int codecheck(String code, String id);
	
	//회원가입종료

	//로그인시 이용
	Member findBySn(int sn);
	//정보검색시 이용
	Member findSimple(int sn);
	//모든회원검색시 이용
	List<Member> selectAll();

	//회원색인종료
	
	//학교정보{
	void schoolsave(MemberSchool memberschool);
	void schoolupdate(MemberSchool memberschool);
	int schoolcounter(int sn);
	MemberSchool findSchool(int sn);
	//학교정보}
	//주소정보{
	void postsave(MemberPost memberpost);
	void postupdate(MemberPost memberpost);
	int postcounter(int sn);
	MemberPost findPost(int sn);
	//주소정보}
	
	
	//로그인정보{
	void loginsave(int sn);
	Login findLoginBySn(int sn);
	HashMap<String,Object> login(String id, String password);
	HashMap<String,Object> logincheck(String id, String password);
	int logcounter(int sn);
	void loginstate(int sn);
	void logoutstate(int sn);
	//로그인정보}
	
	//친구정보{
	List<Friend> findFriendBySn(int sn);
	Member myfriendstate(int sn);
	//친구정보}
	
	//ip관리{
	void saveip(String ip,int sn);
	int checkip(String ip,int sn);
	List<Member> findmemberByip(String ip);
	void deleteip(int sn);
	//ip관리}
	
	//test_code{
	void testinsert(Member member);
	List<Member> testSelectAll();
	
	//친구아닌리스트
	List<Integer> notfriendlist(int sn);
	
	//}test_code
	


}
