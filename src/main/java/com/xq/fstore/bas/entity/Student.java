package com.xq.fstore.bas.entity;

import java.math.BigDecimal;
import java.util.Date;

public class Student {
	private String studentId ;
	private String studentName ;
	private int age ;
	
	private float fum ; 
	private double dum ; 
	private byte bum ; 
	private short sum ; 
	public short getSum() {
		return sum;
	}
	public void setSum(short sum) {
		this.sum = sum;
	}
	private long lum ; 
	private boolean boo ;
	private char ch ; 
	

	public Student() {
		System.out.println("Student 的无参构造方法..");
	}
	@Override
	public String toString() {
		return "Student [studentId=" + studentId + ", studentName=" + studentName + ", age=" + age + ", fum=" + fum
				+ ", dum=" + dum + ", bum=" + bum + ", lum=" + lum + ", boo=" + boo + ", ch=" + ch + ", date=" + date
				+ ", bigDecimal=" + bigDecimal + ", grade=" + grade + "]";
	}
	public float getFum() {
		return fum;
	}
	public void setFum(float fum) {
		this.fum = fum;
	}
	public double getDum() {
		return dum;
	}
	public void setDum(double dum) {
		this.dum = dum;
	}
	public byte getBum() {
		return bum;
	}
	public void setBum(byte bum) {
		this.bum = bum;
	}
	public long getLum() {
		return lum;
	}
	public void setLum(long lum) {
		this.lum = lum;
	}
	public boolean isBoo() {
		return boo;
	}
	public void setBoo(boolean boo) {
		this.boo = boo;
	}
	public char getCh() {
		return ch;
	}
	public void setCh(char ch) {
		this.ch = ch;
	}
	private Date date ;
	
	private BigDecimal bigDecimal ;
	
	public BigDecimal getBigDecimal() {
		return bigDecimal;
	}
	public void setBigDecimal(BigDecimal bigDecimal) {
		this.bigDecimal = bigDecimal;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	private Grade grade ;
	
	public Grade getGrade() {
		return grade;
	}
	public void setGrade(Grade grade) {
		this.grade = grade;
	}
	public String getStudentId() {
		return studentId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}

}
