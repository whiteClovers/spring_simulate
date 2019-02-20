package com.xq.fstore.bas.framework;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

import com.xq.fstore.bas.aop.data.Position;

public class MyHandler2 implements InvocationHandler {
	private Object target ;
	private List<Position> listPosition ;
	private List<String> listLocation = new ArrayList<>() ;
	private String ClassName ;
	
	// 被代理对象  ，代理类名   ，listPosition
	public MyHandler2(Object target ,String ClassName ,List<Position> listPosition) {
		super();
		this.target = target ;
		this.ClassName = ClassName ;
		this.listPosition = listPosition ;
		
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		for (Position position : listPosition) {
			String positionType = position.getPositionType();
			listLocation.add(positionType);
		}
		Class<?> forName = Class.forName(ClassName);
		
		Object object = null ;
//		System.out.println("position :"+position);
		
			try{
				// before方法
				if (listLocation.contains("aop:before") && !(listLocation.contains("aop:around"))) {
					for (Position position : listPosition) {
						String methodName = position.getMethod() ;	
						String positionType = position.getPositionType();
						if ("aop:before".equals(positionType)) {
							Method declaredMethod = forName.getDeclaredMethod(methodName);
							declaredMethod.invoke(forName.newInstance());
						}
					}
				}
				
				// around
				if (!(listLocation.contains("aop:before")) && listLocation.contains("aop:around")) {
					for (Position position : listPosition) {
						String methodName = position.getMethod() ;	
						String positionType = position.getPositionType();
						if ("aop:around".equals(positionType)) {
							Method declaredMethod2 = forName.getDeclaredMethod(methodName ,Object.class ,Method.class ,args.getClass());
							Object object2 = declaredMethod2.invoke(forName.newInstance() ,target ,method ,args);
							System.out.println(object2);
						}
					}
					
				}
				
				// before+around
				if (listLocation.contains("aop:before") && listLocation.contains("aop:around")) {
					String beforeMethodName = null ;
					for (Position position : listPosition) {
						String methodName = position.getMethod() ;	
						String positionType = position.getPositionType();
						if ("aop:before".equals(positionType)) {
							beforeMethodName = methodName ;
						}
					}
					for (Position position : listPosition) {
						String methodName = position.getMethod() ;	
						String positionType = position.getPositionType();
						if ("aop:around".equals(positionType)) {
							Method declaredMethod2 = forName.getDeclaredMethod(methodName ,Object.class ,Method.class ,
									args.getClass() ,beforeMethodName.getClass() ,forName.getClass());
							Object object2 = declaredMethod2.invoke(forName.newInstance() ,target ,method ,args ,beforeMethodName ,forName);
						}
					}
				}
				
				for (Position position : listPosition) {
					String methodName = position.getMethod() ;	
					String positionType = position.getPositionType();
					if ("aop:after-returning".equals(positionType)) {
						Method declaredMethod = forName.getDeclaredMethod(methodName);
						declaredMethod.invoke(forName.newInstance());
					}
				}
				
			}catch (Exception e) {
				for (Position position : listPosition) {
					String methodName = position.getMethod() ;	
					String positionType = position.getPositionType();
					if ("aop:after-throwing".equals(positionType)) {
						Method declaredMethod = forName.getDeclaredMethod(methodName);
						declaredMethod.invoke(forName.newInstance());
					}
				}
				throw new RuntimeException("错误！！");
			}finally {
				for (Position position : listPosition) {
					String methodName = position.getMethod() ;	
					String positionType = position.getPositionType();
					if ("aop:after".equals(positionType)) {
						Method declaredMethod = forName.getDeclaredMethod(methodName);
						declaredMethod.invoke(forName.newInstance());
					}
				}
			}
		
		return object;
	}

}
