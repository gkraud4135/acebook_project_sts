package com.lec206.acebook.dataservice_friend;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.lec206.acebook.vo_friend.Friend;
import com.lec206.acebook.vo_member.Member;
import com.lec206.acebook.vo_member.MemberSchool;

@Repository
public class FriendDAO implements IFriendDAO {
	
	@Autowired SqlSession mapper;

	@Override
	public void request(int mysn, int friendsn) {
		
		Map<String,Integer> map = new HashMap<String, Integer>();
		
		map.put("my_sn", mysn);
		map.put("friend_sn", friendsn);

		//신청할경우 신청중으로 나와야되어서 insert문에서 state = 신청중으로 입력됨
		mapper.insert("friendMapper.request",map);
		
	}

	@Override
	public boolean checkrequest(int mysn, int friendsn) {
		
		Map<String,Integer> map = new HashMap<String, Integer>();
		
		map.put("my_sn", mysn);
		map.put("friend_sn", friendsn);
		
		return mapper.selectOne("friendMapper.checkrequest",map);
	}

	@Override
	public List<Friend> requests(int mysn) {
		
		return mapper.selectList("friendMapper.requests",mysn);
		
	}

	@Override
	public void accept(int mysn, int friendsn) {
		
		Map<String,Integer> map = new HashMap<String, Integer>();
		map.put("my_sn", mysn);
		map.put("friend_sn", friendsn);
		
		mapper.insert("friendMapper.accept",map);

		//친구신청수락햇을경우 신청받은 사람의 상태를 친구로 업데이트
		mapper.update("friendMapper.beFriend",map);
		
	}

	@Override
	public void reject(int mysn, int friendsn) {
		
		Map<String,Integer> map = new HashMap<String, Integer>();
		map.put("my_sn", mysn);
		map.put("friend_sn", friendsn);
		
		mapper.delete("friendMapper.reject",map);
		
	}

	@Override
	public void block(int mysn, int friendsn) {
		
		Map<String,Integer> map = new HashMap<String, Integer>();
		
		map.put("my_sn", mysn);
		map.put("friend_sn", friendsn);
		
		mapper.update("friendMapper.block",map);
		
	}

	@Override
	@Transactional
	public void deleteFriend(int mysn, int friendsn) {
		
		Map<String,Integer> map = new HashMap<String, Integer>();
		
		map.put("my_sn", mysn);
		map.put("friend_sn", friendsn);
		//친구삭제
		mapper.delete("friendMapper.deleteFriend",map);
		
		map.put("my_sn", friendsn);
		map.put("friend_sn", mysn);
		//상대방에게서도 삭제
		mapper.delete("friendMapper.deleteFriend",map);
		
	}

	@Override
	public List<Member> selectFriends(int sn) {
		
		return mapper.selectList("friendMapper.selectFriends",sn);
	}

	@Override
	public int friendCounter(int mysn) {

		return mapper.selectOne("friendMapper.friendCounter",mysn);
	}

	@Override
	public List<Friend> requested(int mysn) {
		
		return mapper.selectList("friendMapper.requested",mysn);
		
	}

	@Override
	public List<MemberSchool> recommendSchool(int schooltype,String schoolvalue,int sn) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("sn", sn);
		map.put("schooltype", schooltype);
		map.put("schoolvalue", schoolvalue);
		
		return mapper.selectList("friendMapper.recommendSchool",map);
		
	}

	@Override
	public List<Member> recommendAge(String age,int sn) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("age", age);
		map.put("sn", sn);
		
		return mapper.selectList("friendMapper.sameage",map);
		
	}

}