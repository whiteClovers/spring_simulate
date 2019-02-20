package com.xq.fstore.bas.test;

import org.junit.Test;

import com.xq.fstore.bas.analysis.ApplicationContext;
import com.xq.fstore.bas.analysis.ClassPathXmlApplicationContext;
import com.xq.fstore.bas.dao.IGradeDao;
import com.xq.fstore.bas.entity.Grade;

public class TestAOP {
	@Test
	public void test01() throws Exception{
		ApplicationContext ctx = new ClassPathXmlApplicationContext("main/resources/applicationContextAOP.xml");
//		ApplicationContext ctx2 = new ClassPathXmlApplicationContext(new String[]{"main/resources/applicationContext.xml"});
		
//		Grade grade = (Grade)ctx.getBean("grade");
//		System.out.println(grade);
		
		Grade grade = (Grade)ctx.getBean("grade");
		System.out.println(grade);
		
		IGradeDao gradeDao = (IGradeDao)ctx.getBean("gradeDao");
//		System.out.println(gradeDao.getClass());
		
//		Grade grade = new Grade();
//		grade.setGradeId("001");
//		
//		System.out.println(grade);
		gradeDao.insert(grade);
//		System.out.println(gradeDao.getClass());
		
	}
	
}
