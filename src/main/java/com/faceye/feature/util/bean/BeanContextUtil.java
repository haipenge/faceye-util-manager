package com.faceye.feature.util.bean;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Spring bean的管理
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2014年7月8日
 */
@Component
public class BeanContextUtil implements ApplicationContextAware {

	private static ApplicationContext applicationContext = null;

	private static BeanContextUtil beanContextUtil = null;

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	@SuppressWarnings("unchecked")
	public static  <T> T getBean(String beanName) {
		T bean = null;
		if (StringUtils.isNotEmpty(beanName)) {
			bean = (T) applicationContext.getBean(beanName);
		}
		return bean;
	}
	
	public static <T> T getBean(Class<T> clazz){
		T bean=null;
		bean=applicationContext.getBean(clazz);
		return bean;
	}
	
	public String [] getBeanDefinitionNames(){
		return this.applicationContext.getBeanDefinitionNames();
	}
	
	public static ApplicationContext getApplicationContext(){
		return applicationContext;
	}
	
//	public static BeanContextUtil getInstance() {
//		if (beanContextUtil == null) {
//			beanContextUtil = (BeanContextUtil) applicationContext.getBean("beanContextUtil");
//		}
//		return beanContextUtil;
//	}
}
