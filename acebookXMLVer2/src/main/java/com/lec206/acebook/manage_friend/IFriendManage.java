package com.lec206.acebook.manage_friend;

import com.lec206.acebook.util.BusinessResult;

public interface IFriendManage {
	
	//return : void
	BusinessResult 친구신청(int mysn, int friendsn);
	
	//return : void
	BusinessResult 친구수락(int mysn, int friendsn);
	
	//return : void
	BusinessResult 친구거절(int mysn, int friendsn);
	
	//return : void
	BusinessResult 친구차단(int mysn, int friendsn);
	
	//return : void
	BusinessResult 친구삭제(int mysn, int friendsn);
	
	//return : List<Friend>
	BusinessResult 받은친구요청(int mysn);
	
	//연동해야함 7-14
	BusinessResult 요청한친구목록(int mysn);
	
	//return : int
	BusinessResult 신청혹은수락(int sn, int friendsn);
	
	//return : int 테스트코드
	BusinessResult 내친구수(int mysn);
	
	//return : List<Friend>
	BusinessResult 내친구목록(int mysn);
	
	//return : List<Friend>
	BusinessResult 친구상태출력(int mysn);
	
	//return : List<Friend>
	BusinessResult 학교로친구추천(int mysn);
	
	//return : List<Friend>
	BusinessResult 나이로친구추천(int mysn);

}
