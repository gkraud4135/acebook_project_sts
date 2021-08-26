package com.lec206.acebook.dataservice_member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.lec206.acebook.vo_friend.Friend;
import com.lec206.acebook.vo_member.Login;
import com.lec206.acebook.vo_member.Member;
import com.lec206.acebook.vo_member.MemberPost;
import com.lec206.acebook.vo_member.MemberSchool;

@Repository
public class MemberDAO implements IMemberDAO{
	
	@Autowired SqlSession mapper;
	
	@Override
	public void preparesave(Member member) {
		
		mapper.insert("memberMapper.preparesave", member);
	}

	@Override
	public boolean isin(String id) {

		return mapper.selectOne("memberMapper.isin",id);	
	}

	@Override
	@Transactional
	public void codeupdate(String code,String id) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", code);
		map.put("id", id);
		mapper.update("memberMapper.codeupdate",map);
	}

	@Override
	@Transactional
	public int codecheck(String code, String id) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("code", code);
		map.put("id", id);

		return mapper.update("memberMapper.codecheck",map);
	}

	@Override
	public Member findBySn(int sn) {
		
		return mapper.selectOne("memberMapper.findBySn",sn);
	}

	@Override
	@Transactional
	public Member findSimple(int sn) {
		
		//findSimple 이용할경우 회원상세정보가 등록되잇을경우 다 넣어줌
		Member member = new Member();
		member = mapper.selectOne("memberMapper.findBySimple",sn);

		//회원의 등록된 로그인정보가 있을경우
		if(this.findLoginBySn(sn) != null) {
			Login login = new Login();
			
			login = mapper.selectOne("memberMapper.findLoginBySn",sn);
			
			member.setLogin(login);
			
		}
		
		//회원의 등록된 학교정보가 있을경우
		if(this.findSchool(sn) != null) {
			
			MemberSchool school = new MemberSchool();

			school = mapper.selectOne("memberMapper.findSchool",sn);
			
			member.setSchool(school);
			
		}
		
		//회원의 등록된 주소정보가 있을경우
		if(this.findPost(sn) != null) {
			
			MemberPost post = new MemberPost();
			
			post = mapper.selectOne("memberMapper.findPost",sn);
			
			member.setPost(post);
			
		}
		
		//회원의 등록된 친구가 있을경우
		if(this.findFriendBySn(sn) != null) {
			
			List<Friend> friends = new ArrayList<Friend>();
			
			friends = mapper.selectList("memberMapper.findFriendBySn",sn);
			
			member.setFriends(friends);
			
		}
		

		return member;
	}

	@Override
	public List<Member> selectAll() {
		
		return mapper.selectList("memberMapper.selectAll");
	}

	@Override
	public void schoolsave(MemberSchool memberschool) {
		
		mapper.insert("memberMapper.saveSchool",memberschool);
	}

	@Override
	public void schoolupdate(MemberSchool memberschool) {
		
		mapper.update("memberMapper.updateSchool",memberschool);
	}

	@Override
	public int schoolcounter(int sn) {

		return mapper.selectOne("memberMapper.schoolCounter",sn);
	}

	@Override
	public void postsave(MemberPost memberpost) {
		
		mapper.insert("memberMapper.savePost",memberpost);
	}

	@Override
	public void postupdate(MemberPost memberpost) {
		
		mapper.update("memberMapper.updatePost",memberpost);
	}

	@Override
	public int postcounter(int sn) {

		return mapper.selectOne("memberMapper.postCounter",sn);
	}

	//로그인
	@Override
	public HashMap<String, Object> login(String id, String password) {
		
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("id", id);
		map.put("password", password);
		
		return mapper.selectOne("memberMapper.login", map);
	}

	@Override
	@Transactional
	public HashMap<String, Object> logincheck(String id, String password) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("password", password);
		
		return mapper.selectOne("memberMapper.logincheck", map);
	}

	@Override
	public int logcounter(int sn) {

		return mapper.selectOne("memberMapper.logcounter",sn);
	}

	@Override
	public void loginsave(int sn) {
		
		mapper.insert("memberMapper.loginsave", sn);
		
	}

	@Override
	public void loginstate(int sn) {
		
		mapper.update("memberMapper.loginstate",sn);
	}
	
	@Override
	public void logoutstate(int sn) {
		
		mapper.update("memberMapper.logoutstate",sn);
	}

	@Override
	public Login findLoginBySn(int sn) {
		
		return mapper.selectOne("memberMapper.findLoginBySn",sn);
	}

	@Override
	public MemberSchool findSchool(int sn) {
		
		return mapper.selectOne("memberMapper.findSchool",sn);
	}

	@Override
	public MemberPost findPost(int sn) {
		
		return mapper.selectOne("memberMapper.findPost",sn);
	}

	@Override
	public List<Friend> findFriendBySn(int sn) {

		return mapper.selectList("memberMapper.findFriendBySn",sn);
	}

	@Override
	@Transactional
	public Member myfriendstate(int sn) {
		
		Member Member = mapper.selectOne("memberMapper.findBySimple",sn);
		
		if(this.findFriendBySn(sn).size()!=0) {
			
			List<Friend> friends = mapper.selectList("memberMapper.findFriendBySn",sn);
			
			Member.setFriends(friends);
			
			for(Friend friend : Member.getFriends()) {
				
				Login login = mapper.selectOne("memberMapper.findLoginBySn",friend.getFriend_sn().getSn());
				
				friend.getFriend_sn().setLogin(login);
				
			}
			
		}
		
		return Member;
	}
	
	@Override
	@Transactional
	public void saveip(String ip,int sn) {
		
		Map<String , Object> map = new HashMap<String, Object>();
		map.put("ip", ip);
		map.put("sn", sn);
		
		mapper.insert("memberMapper.saveip", map);
	}
	
	@Override
	@Transactional
	public int checkip(String ip,int sn) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ip", ip);
		map.put("sn", sn);
		
		return mapper.selectOne("memberMapper.checkip", map);
		
	}
	
	@Override
	@Transactional
	public List<Member> findmemberByip(String ip) {

		return mapper.selectList("memberMapper.findmemberByip", ip);
		
	}
	
	@Override
	@Transactional
	public void deleteip(int sn) {

		mapper.delete("memberMapper.deleteip", sn);
		
	}
	
	//test_code
	@Override
	public void testinsert(Member member) {
		
		mapper.insert("memberMapper.testinsert", member);
		
	}

	@Override
	public List<Member> testSelectAll() {
	
		return mapper.selectList("memberMapper.selectAlltest");
	}
	//test_code
}
