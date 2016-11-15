package com.faceye.feature.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.lang3.StringUtils;

/**
 * 路径工具
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2014年7月7日
 */
public class PathUtil {
	// @Value("#{property['spider.root.path']}")
	// private static String root = "";
	// @Value("#{property['spider.root.crawl.path']}")
	// private static String crawlPath = "/work/work/spider/crawl";
	private static AtomicLong atomicLong = new AtomicLong(0);
	private static SimpleDateFormat YEAR_FORMAT = new SimpleDateFormat("yyyy");
	private static SimpleDateFormat MONTH_FORMAT = new SimpleDateFormat("MM");
	private static SimpleDateFormat DAY_FORMAT = new SimpleDateFormat("dd");
	private static SimpleDateFormat DAY_HOUR_DATE_FORMAT = new SimpleDateFormat("yyyyMMdd-HH");
	private static SimpleDateFormat DAY_HOUR_SECOUD_DATE_FORMAT = new SimpleDateFormat("yyyyMMdd-HHmmss");

	public static String generatePath() throws Exception {
		String res = "";
		res = DAY_HOUR_DATE_FORMAT.format(new Date());
		return res;
	}

	/**
	 * 根据URL生成目录
	 * @todo
	 * @param url
	 * @return
	 * @throws Exception
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年7月8日
	 */
	synchronized public static String generatePath(String url) throws Exception {
		String path = "";
		StringBuilder res = new StringBuilder();
		res.append("/");
		res.append(generatePath());
		res.append("/");
		if (StringUtils.isNotEmpty(url)) {
			url = url.substring(url.indexOf("//") + 2);
			if (url.indexOf("/") != -1) {
				url = url.substring(0, url.indexOf("/"));
			}
			String[] parts = url.split("\\.");
			if (parts != null && parts.length > 0) {
				for (int i = parts.length - 1; i >= 0; i--) {
					if (!StringUtils.equalsIgnoreCase(parts[i], "www")) {
						res.append(parts[i]);
						res.append("/");
					}
				}
			}
		}
		path = res.toString();

		return path;
	}

	/**
	 *  动态生成文件名
	 * @todo
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年1月20日
	 */
	synchronized public static String generateFileName() {
		if (atomicLong.get() >= Long.MAX_VALUE) {
			atomicLong.set(0);
		}
		StringBuilder filename = new StringBuilder("");
		filename.append(DAY_HOUR_SECOUD_DATE_FORMAT.format(new Date()));
		filename.append("-");
		filename.append(atomicLong.incrementAndGet());
		return filename.toString();
	}

	/**
	 * 动态生成文件目录
	 * @todo
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年1月20日
	 */
	synchronized public static String generateDynamicDirs() {
		// Date now=new Date();
		String dir = "/";
		// dir+=Calendar.YEAR;
		dir += Calendar.getInstance().get(Calendar.YEAR);
		dir += "/";
		// dir+=(Calendar.MONTH+1);
		dir += Calendar.getInstance().get(Calendar.MONTH) + 1;
		dir += "/";
		// dir+=Calendar.DAY_OF_MONTH;
		dir += Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
		dir += "/";
		return dir;
	}

	/**
	 * 创建目录	
	 * @todo
	 * @param path
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年4月10日
	 */
	public static boolean mkdir(String path) {
		boolean res = false;
		File file = new File(path);
		if (!file.exists()) {
			res = file.mkdirs();
		}
		return res;
	}
	/**
	 * 删除目录
	 * @todo
	 * @param path
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年4月10日
	 */
	public static boolean deleteDir(String path){
		boolean res=false;
		File file=new File(path);
		if(file.exists()){
			file.delete();
		}
		return res;
	}

}
