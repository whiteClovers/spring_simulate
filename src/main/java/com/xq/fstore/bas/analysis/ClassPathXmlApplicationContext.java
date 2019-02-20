package com.xq.fstore.bas.analysis;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.xq.fstore.bas.aop.data.Aop;
import com.xq.fstore.bas.aop.data.Aspect;
import com.xq.fstore.bas.aop.data.AspectAuto;
import com.xq.fstore.bas.aop.data.Pointcut;
import com.xq.fstore.bas.aop.data.Position;
import com.xq.fstore.bas.data.Bean;
import com.xq.fstore.bas.data.Property;
import com.xq.fstore.bas.framework.MyHandler2;

public class ClassPathXmlApplicationContext implements ApplicationContext{
	List<Bean> listBean ; 
	List<Aop> listAop ; 
	Map<String,Object> map = new HashMap<String,Object>(); 
	AspectAuto aspectAuto = new AspectAuto();
	Object loadFactory ;

	public Object initLazy(){
		Object loadFactory = null ;
		for (Bean bean : listBean) {
			if ("false".equals(bean.getInit_lazy())) {
				if (bean.getFactory_method()!=null) {
					if (bean.getFactory_bean()!=null) {
						// (对象)动态工厂bean
						String factoryBeanId = bean.getFactory_bean();
						String classFactoryMethod = bean.getFactory_method();
						
						Bean dynamic = getClassBean(factoryBeanId);
						
						try {
							loadFactory = loadFactory(classFactoryMethod, dynamic.getClassName());
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} 
					}else{
						// (类)静态工厂bean
						try {
							loadFactory = loadFactory(bean.getFactory_method(), bean.getFactory_bean());
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} 
					}
				}
			}
		}
		return loadFactory ;
	}
	
	public ClassPathXmlApplicationContext(String xmlPath) {
		listAop = initAop(xmlPath);
		
		/*System.out.println("aspectAuto expose-proxy:"+aspectAuto.getExpose_proxy());
		System.out.println("=======");
		for (Aop aop : listAop) {
			System.out.println("第一层=====");
			System.out.println("aop proxy-target-class:"+aop.getProxy_target_class());
			List<Aspect> listAspect = aop.getListAspect();
			for (Aspect aspect : listAspect) {
				System.out.println("第二层=====");
				System.out.println("aspect== id:"+aspect.getId()+", ref:"+aspect.getRef()+"\n, pointcut"+aspect.getPointcut());
				List<Position> listPosition = aspect.getListPosition();
				System.out.println("position==");
				System.out.println("第三层=====");
				for (Position position : listPosition) {
					System.out.println("position: method:"+position.getMethod()+", pointref:"+position.getPointcut_ref());
				}
			}
		}*/
		
		listBean = initApp(xmlPath) ;
		loadFactory = initLazy();
		/*System.out.println("listBean:");
		for (Bean bean : listBean) {
			System.out.println("beanId :"+bean.getId()+" beanClass :"+bean.getClassName()+" scope :"+bean.getScope()+"\n"
				+" factory method :"+bean.getFactory_method()+" factory bean :"+bean.getFactory_bean()+" lazy init :"+bean.getInit_lazy());
			List<Property> listProperty = bean.getListProperty();

			for (Property property : listProperty) {
				System.out.println(property);
			}
			
		}*/
	}

	public ClassPathXmlApplicationContext(String[] xmlPaths) {
		listBean = initApp(xmlPaths[0]) ;
		listAop = initAop(xmlPaths[0]);
		loadFactory = initLazy();
	}
	
	// 初始化，对applicationContext.xml进行解析，读取aop节点信息
	public List initAop(String xmlPath){
		SAXReader reader = new SAXReader();
		Document document = null ;
		try {
			document = reader.read("src/"+xmlPath);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Element rootElement = document.getRootElement();
		Iterator rootIterator = rootElement.elementIterator();
		
		List<Aop> listAop = new ArrayList<Aop>(); //////
		while(rootIterator.hasNext()){
			Element element = (Element)rootIterator.next();
			
			if ("bean".equals(element.getQualifiedName())) {
				
			}
			// 进入aop:aspectj-autoproxy的环节
			else if ("aop:aspectj-autoproxy".equals(element.getQualifiedName())) {
				if (element.attribute("expose-proxy")!=null) {
					aspectAuto.setExpose_proxy(element.attribute("expose-proxy").getValue());
				}else {
					aspectAuto.setExpose_proxy("false");
				}
				
			}
			// 进入aop:config的环节
			else if ("aop:config".equals(element.getQualifiedName())) {
				
				Aop aop = new Aop();
				if (element.attribute("proxy-target-class")!=null) {
					aop.setProxy_target_class(element.attribute("proxy-target-class").getValue());
				}
				Iterator elementIterator = element.elementIterator();
			
				List<Aspect> listAspect = new ArrayList<Aspect>(); //////
				while(elementIterator.hasNext()){
					Element element2 = (Element)elementIterator.next();
					
					Aspect aspect = new Aspect();
					aspect.setId(element2.attribute("id").getValue());
					aspect.setRef(element2.attribute("ref").getValue());
					
					Iterator elementIterator2 = element2.elementIterator();
					
					List<Position> listPosition = new ArrayList<Position>(); //////
					while(elementIterator2.hasNext()){
						Element element3 = (Element)elementIterator2.next();
						
						// 进入aop:pointcut环节
						if ("aop:pointcut".equals(element3.getQualifiedName())) {
							Pointcut pointcut = new Pointcut();
							pointcut.setId(element3.attribute("id").getValue());
							pointcut.setExpression(element3.attribute("expression").getValue());
							
							aspect.setPointcut(pointcut);
						}
						// 进入position环节
						else {
							Position position = new Position();
							position.setPositionType(element3.getQualifiedName());
							position.setMethod(element3.attribute("method").getValue());
							position.setPointcut_ref(element3.attribute("pointcut-ref").getValue());
							
							listPosition.add(position);
						}
					}
					aspect.setListPosition(listPosition);
					listAspect.add(aspect);
				}
				aop.setListAspect(listAspect);
				listAop.add(aop);
			}else {
				System.out.println("其它节点..");
			}
		}
		
		
		return listAop ;
	}
	
	// aop代理
	public Object aopProxy(Bean classBean) throws Exception{
		Object object = null ;
		for (Aop aop : listAop) {
			List<Aspect> listAspect = aop.getListAspect();
			for (Aspect aspect : listAspect) {
				String aspectRef = aspect.getRef(); // 获取到proxy引用
				
				String proxyClassName = null ;  // 获取到proxy类名
				for (Bean bean : listBean) {
					if (aspectRef.equals(bean.getId())) {
						proxyClassName = bean.getClassName() ;
					}
				}
//				System.out.println("proxy类名: "+proxyClassName);
				
				Pointcut pointcut = aspect.getPointcut();
				String expression = pointcut.getExpression();  // pointcut 的expression
				String pointcutId = pointcut.getId();  // pointcut 的pointcutId
				// location
				List<Position> listPosition = aspect.getListPosition();
				/*String positionMethod = null ; 
				String pointcut_ref = null ;
				
				List<String> listPositionType = new ArrayList<>();
				for (Position position : listPosition) {
					positionMethod = position.getMethod();  // position的method
//					System.out.println("positionMethod :"+positionMethod);
					pointcut_ref = position.getPointcut_ref(); // position的pointcut_ref
				}*/
					// 代理开始
					// 获取proxy类对象
					Class<?> clazz = Class.forName(proxyClassName);
					
					// 获取被代理类
					String substring = expression.substring(expression.length()-13,expression.length()-7);
					
					Bean targetBean = null ;
					for (Bean bean : listBean) {
						if (bean.getClassName().endsWith(substring)) {
							targetBean = bean ;
						}
					}
					
					if (targetBean.equals(classBean)) {
						Class<?> targetClass = Class.forName(targetBean.getClassName());
						object = targetClass.newInstance() ;
//						String positionType = position.getPositionType();
					
						// 被代理对象  ，代理类名   ，listPosition
						String proxy_target_class = aop.getProxy_target_class(); // aop:config 配置
						String expose_proxy = aspectAuto.getExpose_proxy();
						if (proxy_target_class!=null) {
							if ("false".equals(proxy_target_class)) {
								// jdk proxy 
								MyHandler2 handler2 = new MyHandler2(object , proxyClassName ,listPosition);
							    Object newProxyInstance = Proxy.newProxyInstance(object.getClass().getClassLoader(), object.getClass().getInterfaces(), handler2);
							    
							    return newProxyInstance ;
							}
							// cglib
							else {
								// 进入cglib
								System.out.println("进入cglib");
								
							}
						}else {
							if ("false".equals(expose_proxy)) {
								// jdk proxy 
								MyHandler2 handler2 = new MyHandler2(object , proxyClassName ,listPosition);
							    Object newProxyInstance = Proxy.newProxyInstance(object.getClass().getClassLoader(), object.getClass().getInterfaces(), handler2);
							    
							    return newProxyInstance ;
							}else {
								// 进入cglib
								System.out.println("进入cglib");
								
							}
						}
						
						
					
				}
			}
		}
		return object ;
	}
	
	
	// 初始化，对applicationContext.xml进行解析
	public List initApp(String xmlPath){
		// 获取SAXReader对象
		SAXReader reader = new SAXReader();
		Document document = null ; 
		try {
			// 读取文件（路劲从src开始）
//			document = reader.read("src\\applicationContext.xml");
			document = reader.read("src/"+xmlPath);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 
		Element root = document.getRootElement();
		
		Iterator rootIterator = root.elementIterator();
		
		List<Bean> listbean = new ArrayList<Bean>() ;
		while(rootIterator.hasNext()){
			Element elementRoot = (Element)rootIterator.next();
		if ("bean".equals(elementRoot.getName())) {
				
//			List<Attribute> attributes = elementRoot.attributes(); //暂时无用
			
			Bean bean = new Bean();
			bean.setId(elementRoot.attribute("id").getValue());
			
			if (elementRoot.attribute("class")!=null) {
				bean.setClassName(elementRoot.attribute("class").getValue());
			}
			
//			String scopeValue = elementRoot.attribute("scope").getValue();
			if (elementRoot.attribute("scope")!=null) {
				bean.setScope(elementRoot.attribute("scope").getValue());
			}else {
				bean.setScope("singleton");
			}
			
//			String factoryMethodValue = elementRoot.attribute("factory-method").getValue();
//			String factoryBeanValue = elementRoot.attribute("factory-bean").getValue();
//			String lazyInitValue = elementRoot.attribute("lazy-init").getValue();

			if (elementRoot.attribute("factory-bean")!=null) {
				bean.setFactory_bean(elementRoot.attribute("factory-bean").getValue());
			}
			
			if (elementRoot.attribute("factory-method")!=null) {
				bean.setFactory_method(elementRoot.attribute("factory-method").getValue());
			}
			
			if (elementRoot.attribute("lazy-init")!=null) {
				if (!"default".equals(elementRoot.attribute("lazy-init").getValue())) {
					bean.setInit_lazy(elementRoot.attribute("lazy-init").getValue());
				}else {
					bean.setInit_lazy("false");
				}
				
			}else {
				bean.setInit_lazy("false");
			}
			
			listbean.add(bean);
		
			Iterator beanIterator = elementRoot.elementIterator();//propetity
			
			List<Property> listProperty = new ArrayList<Property>();
			while(beanIterator.hasNext()){
				Element properties = (Element) beanIterator.next();
				
//				List<Attribute> attributes2 = elementSec.attributes(); //暂时无用
				
				
				Property property = new Property();
				property.setName(properties.attribute("name").getValue());
				if ((properties.attribute("value")) != null) {
					property.setValue(properties.attribute("value").getValue());
				}
				if ((properties.attribute("ref")) != null) {
					property.setRef(properties.attribute("ref").getValue());
				}
			
				listProperty.add(property);
			}
			bean.setListProperty(listProperty);
		}
	}
		return listbean ;
	}
	
	// 根据beanId获取bean
	public Bean getClassBean(String beanId){
		Bean back = null ;
		for (Bean bean : listBean) {
			if (beanId.equals(bean.getId())) {
				back = bean ;
			}
		}
		return back;
	}
	
	public boolean judgeTargetBean(Bean classBean) throws ClassNotFoundException{
		boolean b = false ;
		for (Aop aop : listAop) {
			List<Aspect> listAspect = aop.getListAspect();
			for (Aspect aspect : listAspect) {
				String aspectRef = aspect.getRef(); // 获取到proxy引用
			
				Pointcut pointcut = aspect.getPointcut();
				String expression = pointcut.getExpression();  // pointcut 的expression
//				
				// 获取被代理类
				String substring = expression.substring(expression.length()-13,expression.length()-7);
				
				if (classBean.getClassName().endsWith(substring)) {
					b = true ;
				}
				
			}
		}
		return b ;
	}
	
	
	// 获取bean
	@Override
	public Object getBean(String beanId) throws Exception {
		Bean classBean = getClassBean(beanId) ;
		
		if (classBean == null) {
			throw new Exception("该beanId不存在");
		}
		
		Object aopProxy = null ;
		if (classBean.getFactory_bean()==null && classBean.getFactory_method()==null) {
			Class<?> clazz = Class.forName(classBean.getClassName());
			// 普通bean
			if (judgeTargetBean(classBean)) {
				aopProxy = aopProxy(classBean);
			}else {
				return mormalBean(classBean ,clazz);	
			}

		}
		
		// 先判断是工厂bean还是普通bean
		// 再判断是静态工厂bean还是动态工厂bean
		if (classBean.getFactory_method()!=null) {
			if (classBean.getFactory_bean()!=null) {
				// (对象)动态工厂bean
				String factoryBeanId = classBean.getFactory_bean();
				String classFactoryMethod = classBean.getFactory_method();
				
				Bean bean = getClassBean(factoryBeanId);
				return staticFactory(bean ,classBean ,classFactoryMethod);
			}else{
				// (类)静态工厂bean
				String classFactoryMethod = classBean.getFactory_method();
				return staticFactory(classBean ,classBean ,classFactoryMethod);
			}
		}
		
		return aopProxy ;
	}

	// 第一个bean是工厂bean，第二个bean是目标类的bean
	public Object staticFactory(Bean bean ,Bean classBean ,String classFactoryMethod) throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, NoSuchMethodException, InvocationTargetException {
		String classFactoryName = bean.getClassName();
		
		Class<? extends Object> class1 ;
		if ("true".equals(classBean.getInit_lazy())) {
			Object object3 = loadFactory(classFactoryMethod, classFactoryName);
			
			class1 = object3.getClass();
		}else {
			class1 = loadFactory.getClass();
		}
		
		
		return mormalBean(classBean ,class1);
	}

	// 加载factory方法
	public Object loadFactory(String classFactoryMethod, String classFactoryName) throws ClassNotFoundException,
			InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
		Class<?> staticFactory = Class.forName(classFactoryName);
		Object object = staticFactory.newInstance();
		
		// 调用createMethod方法，返回创建的对象object2
		Method declaredMethod = staticFactory.getDeclaredMethod(classFactoryMethod);
		Object object2 = declaredMethod.invoke(object);
		return object2;
	}

	public Object mormalBean(Bean classBean, Class<?> clazz) {
		if ("prototype".equals(classBean.getScope())) {
			return getBeanPrototype(classBean ,clazz);
		}else {
			return getBeanSingleton(classBean ,clazz);
		}
	}

	private Object getBeanSingleton(Bean classBean, Class<?> clazz) {
		Object object ;
		String beanId = classBean.getId();
		try {
			if (map.get(beanId)!=null) {
				return map.get(beanId);
			}else {
				object = setValue(clazz,classBean.getId());
				map.put(beanId, object);
				return object ;	
			}
	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new RuntimeException("singleton error");
		}
		
	}

	private Object getBeanPrototype(Bean classBean, Class<?> clazz) {
		try {
			Object object = setValue(clazz,classBean.getId());
			return object ;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new RuntimeException("prototype error");
		}
	}

	// 将字段拼成set方法名
	public String addSet(String str){
		String maxStr = str.substring(0,1).toUpperCase();
		return "set" + maxStr + str.substring(1,str.length());
	}
	
	// 为属性注入值
	private Object setValue(Class<?> clazz,String beanId) throws Exception {
		Object object = clazz.newInstance();
		
		for (Bean bean : listBean) {
			if (beanId.equals(bean.getId())) {
				List<Property> listProperty = bean.getListProperty();
				for (Property property : listProperty) {
					Field declaredField = clazz.getDeclaredField(property.getName());
					Class<?> type = declaredField.getType();
					String simpleName = type.getSimpleName();
					String methodName = addSet(property.getName());
					
					Method declaredMethod = clazz.getDeclaredMethod(methodName, type);
//					System.out.println("declaredMethodName :"+declaredMethod);
					
					String value = property.getValue();
//					System.out.println("value :"+value);
					String fieldSimpleName = declaredField.getType().getSimpleName();
//					System.out.println("fieldSimpleName :"+fieldSimpleName);
//					System.out.println("ref: 2333333333"+property.getRef());
					if (property.getRef() != null){
//							System.out.println("对象类型");
					
							Object object2 = getBean(property.getRef());
							declaredMethod.invoke(object, object2);
					
					}else{
						if ("String".equals(fieldSimpleName)) {
//							System.out.println("string类型");
							
							declaredMethod.invoke(object, value);
							
						}else if ("int".equals(fieldSimpleName)) {
//							System.out.println("int类型");
							
							int num = Integer.valueOf(value);
							declaredMethod.invoke(object, num);
							
						}else if ("BigDecimal".equals(fieldSimpleName)) {
//							System.out.println("BigDecimal类型");
							
							BigDecimal bigDecimal = new BigDecimal(value);
							declaredMethod.invoke(object, bigDecimal);
							
						}else if ("Date".equals(fieldSimpleName)) {
//							System.out.println("Date类型");
							
							SimpleDateFormat dateFormat = new SimpleDateFormat("yy-MM-dd");
							Date parse = dateFormat.parse(value);
							declaredMethod.invoke(object, parse);
							
						}else if ("float".equals(fieldSimpleName)) {
//							System.out.println("Date类型");
							
							Float float1 = Float.parseFloat(value);
							declaredMethod.invoke(object, float1);
							
						}else if ("double".equals(fieldSimpleName)) {
//							System.out.println("Date类型");
							
							Double float1 = Double.parseDouble(value);
							declaredMethod.invoke(object, float1);
							
						}else if ("byte".equals(fieldSimpleName)) {
//							System.out.println("Date类型");
							
							Byte float1 = Byte.parseByte(value);
							declaredMethod.invoke(object, float1);
							
						}else if ("short".equals(fieldSimpleName)) {
//							System.out.println("Date类型");
							
							Short float1 = Short.parseShort(value);
							declaredMethod.invoke(object, float1);
							
						}else if ("long".equals(fieldSimpleName)) {
//							System.out.println("Date类型");
							
							Long float1 = Long.parseLong(value);
							declaredMethod.invoke(object, float1);
							
						}else if ("boolean".equals(fieldSimpleName)) {
//							System.out.println("Date类型");
							
							Boolean float1 = Boolean.parseBoolean(value);
							declaredMethod.invoke(object, float1);
							
						}else if ("char".equals(fieldSimpleName)) {
//							System.out.println("Date类型");
							
							char[] float1 = value.toCharArray();
							declaredMethod.invoke(object,float1[0]);
							
						}else {
							System.out.println("others");
						}
					}
				}	
			}
		}
		return object ;
	}
	
}
