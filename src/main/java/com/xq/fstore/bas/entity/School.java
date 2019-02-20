package com.xq.fstore.bas.entity;

public class School {
	private String schoolId ;
	private String schoolName ;
	private int num ;
	
	
	public School() {
		System.out.println("School 的无参构造方法..");
	}
	public String getSchoolId() {
		return schoolId;
	}
	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	@Override
	public String toString() {
		return "School [schoolId=" + schoolId + ", schoolName=" + schoolName + ", num=" + num + "]";
	}
	
}
