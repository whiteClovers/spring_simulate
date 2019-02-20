package com.xq.fstore.bas.data;

import java.util.List;

public class Bean {
	private String id ;
	private String className ;
	private String scope ;
	private String factory_bean ;
	private String factory_method ;
	private String init_lazy ;
	
	private List<Property> listProperty ;

	
	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getFactory_bean() {
		return factory_bean;
	}

	public void setFactory_bean(String factory_bean) {
		this.factory_bean = factory_bean;
	}

	public String getFactory_method() {
		return factory_method;
	}

	public void setFactory_method(String factory_method) {
		this.factory_method = factory_method;
	}

	public String getInit_lazy() {
		return init_lazy;
	}

	public void setInit_lazy(String init_lazy) {
		this.init_lazy = init_lazy;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public List<Property> getListProperty() {
		return listProperty;
	}

	public void setListProperty(List<Property> listProperty) {
		this.listProperty = listProperty;
	}

	@Override
	public String toString() {
		return "Bean [id=" + id + ", className=" + className + ", scope=" + scope + ", factory_bean=" + factory_bean
				+ ", factory_method=" + factory_method + ", init_lazy=" + init_lazy + ", listProperty=" + listProperty
				+ "]";
	}
	
}
