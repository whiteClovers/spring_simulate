package com.xq.fstore.bas.aop.data;

public class Pointcut {
	private String id ;
	private String expression ;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getExpression() {
		return expression;
	}
	public void setExpression(String expression) {
		this.expression = expression;
	}
	@Override
	public String toString() {
		return "Pointcut [id=" + id + ", expression=" + expression + "]";
	}
	
}
