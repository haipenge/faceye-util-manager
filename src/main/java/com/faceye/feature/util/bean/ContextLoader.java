package com.faceye.feature.util.bean;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @说明:加载器
 * @author songhaipeng
 *
 */
public class ContextLoader {

	private static ApplicationContext context = null;

	private static class ContextLoaderHolder {
		private static final ContextLoader INSTANCE = new ContextLoader();
	}

	private ContextLoader() {
		contextLoader();
	}

	public static final ContextLoader getInstance(){
		return ContextLoaderHolder.INSTANCE;
	}

	public void contextLoader() {
		if (context == null) {
			context = new ClassPathXmlApplicationContext("classpath*:applicationContext.xml");
		}
	}

	public Object getBean(String name) {
		return context.getBean(name);
	}

	public Object getBean(Class clazz) {
		return context.getBean(clazz);
	}
}
