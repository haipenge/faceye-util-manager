package com.faceye.feature.util.bean;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * 类工具
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2015年2月27日
 */
public class ClassUtil {

	/**
	 * 添加项目根目录到classpath
	 * @todo
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws MalformedURLException
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年2月27日
	 */
	public static void addDir2Classpath() throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, MalformedURLException {
		addDir2Classpath("./");
	}

	/**
	 * 添加目录到classpath
	 * @todo
	 * @param dir
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws MalformedURLException
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年2月27日
	 */
	public static void addDir2Classpath(String dir) throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, MalformedURLException {
		File programRootDir = new File(dir);
		URLClassLoader classLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
		Method add = URLClassLoader.class.getDeclaredMethod("addURL", new Class[] { URL.class });
		add.setAccessible(true);
		add.invoke(classLoader, programRootDir.toURI().toURL());
	
	}
}
