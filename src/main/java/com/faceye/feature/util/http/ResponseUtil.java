package com.faceye.feature.util.http;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 响应工具类
 * @author @haipenge 
 * @联系:haipenge@gmail.com
 * 创建时间:2015年9月26日
 */
public class ResponseUtil {
	private static Logger logger = LoggerFactory.getLogger(ResponseUtil.class);

	/**
	 * 输出Json
	 * @todo
	 * @param response
	 * @param json
	 * @author:@haipenge
	 * 联系:haipenge@gmail.com
	 * 创建时间:2015年9月26日
	 */
	public static void json(HttpServletResponse response, String json) {
		response.setContentType("text/json");
		response.setHeader("Cache-Control", "no-cache");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.write(json);
		} catch (IOException e) {
			logger.error(">>FaceYe throws Exception: --->", e);
		} finally {
			if (out != null) {
				out.flush();
				out.close();
			}
		}
	}

	/**
	 * 输出xml
	 * @todo
	 * @param response
	 * @param xml
	 * @author:@haipenge
	 * 联系:haipenge@gmail.com
	 * 创建时间:2015年9月26日
	 */
	public static void xml(HttpServletResponse response, String xml) {
		response.setContentType("text/xml;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.write(xml);
		} catch (IOException e) {
			logger.error(">>FaceYe throws Exception: --->", e);
		} finally {
			if (out != null) {
				out.flush();
				out.close();
			}
		}
	}
}
