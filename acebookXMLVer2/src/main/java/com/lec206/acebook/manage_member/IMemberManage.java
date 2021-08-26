package com.lec206.acebook.manage_member;

import com.lec206.acebook.util.BusinessResult;
import com.lec206.acebook.vo_member.Member;
import com.lec206.acebook.vo_member.MemberPost;
import com.lec206.acebook.vo_member.MemberSchool;

public interface IMemberManage {
	
	//return noting
	BusinessResult preparesaveMember(Member member);
	//return : boolean
	BusinessResult idisin(String id);
	//retrun : member(중요정보 제외)
	BusinessResult findSimpleMember(int sn);
	//return : void
	BusinessResult saveSchool(MemberSchool memberschool);
	//return : void
	BusinessResult savePost(MemberPost memberpost);
	//retrun : state : ({sn : int},{name : String},{id : String},{state:enum})
	BusinessResult loginService(String id, String password,String ip);
	//return : void
	BusinessResult logoutService(int sn);
	//return : List<Member>
	BusinessResult findmemberByip(String ip);
	//return : void
	BusinessResult deleteip(int sn);
	//return : List<Member>
	BusinessResult selectAll();
	
	//test_code 회원자동등록
	BusinessResult testinsert(Member member);
	BusinessResult testSelect();

}
