package com.xq.fstore.bas.entity;

public class Grade {
	private String gradeId ;
	private String gradeName ;
	
	private Student student;
	
	
//	public Grade() {
//		System.out.println("Grade 的无参构造方法..");
//	}
	public Student getStudent() {
		return student;
	}
	public void setStudent(Student student) {
		this.student = student;
	}
	public String getGradeId() {
		return gradeId;
	}
	public void setGradeId(String gradeId) {
		this.gradeId = gradeId;
	}
	public String getGradeName() {
		return gradeName;
	}
	public void setGradeName(String gradeName) {
		this.gradeName = gradeName;
	}
	@Override
	public String toString() {
		return "Grade [gradeId=" + gradeId + ", gradeName=" + gradeName + ", student=" + student + "]";
	}
	
}
