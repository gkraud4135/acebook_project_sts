package com.lec206.acebook.util;

import java.util.List;

public class BusinessResult {

	//필드맴버
	ERRORCODE code=ERRORCODE.NORMAL;			//요청실패시 ENUM으로 코드번호를 받을 예정
	String msg=null;	//
	Object value=null;	//
	
	//기본생성자
	public BusinessResult() {
		
		this.code = ERRORCODE.NORMAL;
		this.msg = null;
		this.value = null;
		
	}
	
	//업무성공하면서 객체 반환하고 싶을시
	public BusinessResult(Object value) {
		
		this.code = ERRORCODE.NORMAL;
		this.msg = null;
		this.value = value;
		
	}
	
	public BusinessResult(List<Object> value) {
		
		this.code = ERRORCODE.NORMAL;
		this.msg = null;
		this.value = value;
		
	}
	
	//업무실패시
	public BusinessResult(ERRORCODE code, String msg) {
		
		this.code = code;
		this.msg = msg;
		
	}
	
	public BusinessResult(Object value,ERRORCODE code, String msg) {
		
		this.code = code;
		this.msg = msg;
		this.value = value;
		
	}
	
	//getter

	public ERRORCODE getCode() { return code; }

	public String getMsg() { return msg; }

	public Object getValue() { return value; }
	
	
	
	

}
