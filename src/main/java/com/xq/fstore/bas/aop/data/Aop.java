package com.xq.fstore.bas.aop.data;

import java.util.List;

public class Aop {
	private List<Aspect> listAspect ;
	private String proxy_target_class ;
	
	public String getProxy_target_class() {
		return proxy_target_class;
	}
	public void setProxy_target_class(String proxy_target_class) {
		this.proxy_target_class = proxy_target_class;
	}
	
	public List<Aspect> getListAspect() {
		return listAspect;
	}
	public void setListAspect(List<Aspect> listAspect) {
		this.listAspect = listAspect;
	}
	@Override
	public String toString() {
		return "Aop [listAspect=" + listAspect + ", proxy_target_class=" + proxy_target_class + "]";
	}
	
}
