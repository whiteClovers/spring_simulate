package com.xq.fstore.bas.aop.data;

import java.util.List;

public class Aspect {
	private String id ;
	private String ref ;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRef() {
		return ref;
	}
	public void setRef(String ref) {
		this.ref = ref;
	}
	private Pointcut pointcut ;
	private List<Position> listPosition ;
	
	public Pointcut getPointcut() {
		return pointcut;
	}
	public void setPointcut(Pointcut pointcut) {
		this.pointcut = pointcut;
	}
	public List<Position> getListPosition() {
		return listPosition;
	}
	public void setListPosition(List<Position> listPosition) {
		this.listPosition = listPosition;
	}
	@Override
	public String toString() {
		return "Aspect [id=" + id + ", ref=" + ref + ", pointcut=" + pointcut + ", listPosition=" + listPosition + "]";
	}
	
}
