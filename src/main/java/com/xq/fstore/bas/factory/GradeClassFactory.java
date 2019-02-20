package com.xq.fstore.bas.factory;

import com.xq.fstore.bas.entity.Grade;

public class GradeClassFactory {
	public static Grade createGrade(){
		System.out.println("类工厂创建前...");
		Grade grade = new Grade() ;
		System.out.println("类工厂创建后...");
		
		return grade ;
	}
}
