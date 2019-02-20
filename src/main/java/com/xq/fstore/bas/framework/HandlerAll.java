package com.xq.fstore.bas.framework;

public class HandlerAll {

	public void beforeMethod(){
		System.out.println("before..");
		
	}
	
	public void afterMethod(){
		System.out.println("after..");
		
	}
	
	public void afterReturningMethod(){
		System.out.println("afterReturning..");
		
	}
	
	public void afterThrowingMethod(){
		System.out.println("afterThrowing..");
		
	}
}
