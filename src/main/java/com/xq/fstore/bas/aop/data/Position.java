package com.xq.fstore.bas.aop.data;

public class Position {
	private String positionType ;
	public String getPositionType() {
		return positionType;
	}
	public void setPositionType(String positionType) {
		this.positionType = positionType;
	}
	private String method ;
	private String pointcut_ref ;
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getPointcut_ref() {
		return pointcut_ref;
	}
	public void setPointcut_ref(String pointcut_ref) {
		this.pointcut_ref = pointcut_ref;
	}
	@Override
	public String toString() {
		return "Position [positionType=" + positionType + ", method=" + method + ", pointcut_ref=" + pointcut_ref + "]";
	}
	
}
