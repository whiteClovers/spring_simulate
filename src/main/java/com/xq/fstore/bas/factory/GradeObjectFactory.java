package com.xq.fstore.bas.factory;

import com.xq.fstore.bas.entity.Grade;

public class GradeObjectFactory {
	public Grade createGrade(){
		System.out.println("对象工厂创建前...");
		Grade grade = new Grade() ;
		System.out.println("对象工厂创建后...");
		
		return grade ;
	}
}
