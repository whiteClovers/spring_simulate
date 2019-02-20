package com.xq.fstore.bas.framework;

import org.aspectj.lang.ProceedingJoinPoint;

public class AroundHandler {
	public Object aroundMethod(ProceedingJoinPoint pjp) throws Throwable{
		System.out.println("扩展前...");
		Object object = pjp.proceed();
		System.out.println("扩展后...");
		
		return object ;
	}
}
