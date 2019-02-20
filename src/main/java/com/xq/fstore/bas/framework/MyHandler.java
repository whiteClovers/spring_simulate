package com.xq.fstore.bas.framework;

import java.lang.reflect.Method;

public class MyHandler {

	public Object aroundMethod(Object object ,Method method, Object[] args ,String beforeMethodName ,Class forName) throws Exception {
		Object proxy = null ;
		System.out.println("aroundMethod...前");
		
		/*try {
			Method declaredMethod = forName.getDeclaredMethod(beforeMethodName);
			declaredMethod.invoke(forName.newInstance());
		
			proxy = method.invoke(object, args) ;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		Method declaredMethod = forName.getDeclaredMethod(beforeMethodName);
		declaredMethod.invoke(forName.newInstance());
	
		proxy = method.invoke(object, args) ;
		System.out.println("aroundMethod...后");
		
		return proxy ;
	} 

	public void afterMethod(){
		System.out.println("afterMethod...");
		
	}
	
	public void beforeMethod(){
		System.out.println("beforeMethod...");
	
	}
	
	public void afterThrowingMethod(){
		System.out.println("afterThrowingMethod...");
	
	}
	
	public void afterReturningMethod(){
		System.out.println("afterReturningMethod...");
	
	}
}
