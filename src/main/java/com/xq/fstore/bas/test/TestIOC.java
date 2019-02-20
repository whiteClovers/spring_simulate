package com.xq.fstore.bas.test;
//
import org.junit.Test;

import com.xq.fstore.bas.analysis.ApplicationContext;
import com.xq.fstore.bas.analysis.ClassPathXmlApplicationContext;
import com.xq.fstore.bas.entity.Grade;;

public class TestIOC {
	
	@Test
	public void test01() throws Exception{
		ApplicationContext ctx = new ClassPathXmlApplicationContext("main/resources/applicationContext.xml");
//		ApplicationContext ctx = new ClassPathXmlApplicationContext(new String[]{"main/resources/applicationContext.xml"});
		
		Grade grade = (Grade)ctx.getBean("grade");
		System.out.println(grade);
		
		Grade grade2 = (Grade)ctx.getBean("grade");
		System.out.println(grade2);
		
		System.out.println(grade == grade2);
		
//		Grade grade3 = (Grade)ctx.getBean("grade");
//		System.out.println(grade3);
//		
//		System.out.println(grade == grade3);
		
//		Student student2 = (Student)ctx2.getBean("student2");
//		System.out.println(student2);
		
//		Student student = (Student)ctx2.getBean("student2");
//		System.out.println(student);
		
		
	}
	
}
