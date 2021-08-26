package com.lec206.acebook.vo_member;

public class MemberSchool {
	
	//원시변수
		String row;
		String middle;
		String high;
		String university;
		
		//참조변수
		Member sn;

		//생성자[필요할경우 추가로 기입]
		
		//getter , setter
		public String getRow() {return row;}
		public void setRow(String row) {this.row = row;}

		public String getMiddle() {return middle;}
		public void setMiddle(String middle) {this.middle = middle;}

		public String getHigh() {return high;}
		public void setHigh(String high) {this.high = high;}

		public String getUniversity() {return university;}
		public void setUniversity(String university) {this.university = university;}

		public Member getSn() {return sn;}
		public void setSn(Member sn) {this.sn = sn;}

	}