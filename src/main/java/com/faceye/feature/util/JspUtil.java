package com.faceye.feature.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.faceye.feature.util.bean.BeanContextUtil;
import com.faceye.feature.util.regexp.RegexpUtil;

public class JspUtil {
	private static Logger logger = LoggerFactory.getLogger(JspUtil.class);
	private static String HOST = "";

	public static String characterEncoding(String arg0) {
		String result = "";
		if (StringUtils.isNotEmpty(arg0)) {
			try {
				result = URLEncoder.encode(arg0, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				logger.error(">>FaceYe:", e);
			}
		}
		return result;
	}

	public static String characterDecoder(String arg0) {
		String result = "";
		if (StringUtils.isNotEmpty(arg0)) {
			try {
				result = URLDecoder.decode(arg0, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				logger.error(">>FaceYe:", e);
			}
		}
		return result;
	}

	public static String getSummary(Object arg0, int length) {
		if (null != arg0) {
			return filterHtmlCharacters(arg0.toString(), length);
		} else {
			return "";
		}
	}

	public static String filterHtmlCharacters(String content, int length) {
		if (StringUtils.isEmpty(content)) {
			return "";
		}
		// 去掉所有html元素,
		String str = content.replaceAll("\\&[a-zA-Z]{1,10};", "").replaceAll("<[^>]*>", "");
		str = str.replaceAll("[(/>)<]", "");
		int len = str.length();
		if (len <= length) {
			return str;
		} else {
			str = str.substring(0, length);
			str += "...";
		}
		return str;
	}

	public static String getDomain(String url) {
		String domain = "";
		if (StringUtils.isNotEmpty(url)&&StringUtils.length(url)>7 &&StringUtils.contains(url, "/")) {
			domain = url.substring(7, url.substring(7).indexOf("/") + 7);
		}
		return domain.toLowerCase();
	}
	
	public static String getDomain(HttpServletRequest request){
		String url=request.getRequestURL().toString();
		return getDomain(url);
	}


	/**
	 * 菜单是否激活
	 * @todo
	 * @param request
	 * @param pattern
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年3月13日
	 */
	public static String isActive(HttpServletRequest request, String regexp) {
		String res = "";
		String url = getUrl(request);
		regexp=StringUtils.replace(regexp, "/", "\\/");
		boolean isMatch = RegexpUtil.isMatch(url, regexp);
		if (isMatch) {
			res = "active";
		}
		return res;
	}

	/**
	 * 取得当前页面的URL
	 * @todo
	 * @param request
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年3月13日
	 */
	public static String getUrl(HttpServletRequest request) {
		String res = "";
		// request.getRequestURL();
		// request.getServletPath()
		// res=request.getServletPath()+(request.getQueryString()==null?"":("?"+request.getQueryString()));
		res = request.getAttribute("javax.servlet.forward.request_uri") == null ? "" : request.getAttribute(
				"javax.servlet.forward.request_uri").toString();
		return res;
	}
	
}
