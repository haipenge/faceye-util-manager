package com.faceye.feature.util.html;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.faceye.feature.util.regexp.RegexpUtil;

/**
 * Html处理
 * @author @haipenge 
 * haipenge@gmail.com
*  Create Date:2014年7月10日
 */
public class HtmlUtil {
	private Logger logger = LoggerFactory.getLogger(getClass());
	private static HtmlUtil INSTANCE = null;

	private HtmlUtil() {

	}

	public static HtmlUtil getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new HtmlUtil();
		}
		return INSTANCE;
	}

	/**
	 * 脱掉content中的html标记
	 * @todo
	 * @param content
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2014年7月10日
	 */
	public String replaceHtml(String content) {
		String res = "";
		res = this.replace(content, RegexpConstants.REPLACE_DIV);
		// res = this.replace(res, RegexpConstants.HTML_DIV_END);
		res = this.replace(res, RegexpConstants.HTML_SCRIPT);
		res = this.replace(res, RegexpConstants.HTML_NOTE);
//		res = this.replace(res, RegexpConstants.REPLACE_ALL_A_HREF);
		res=this.replace(res, RegexpConstants.REPLACE_ALL_A_HREF_LEFT);
		res=this.replace(res, RegexpConstants.REPLACE_ALL_A_HREF_RIGHT);
		res = this.replace(res, RegexpConstants.PATTERN_URL);
		res = this.replace(res, RegexpConstants.REPLACE_ALL_IMG);
		res = this.replace(res, RegexpConstants.REPLACE_FLASE);
		res = this.replace(res, RegexpConstants.HTML_INPUT);
		// res = this.replace(res, RegexpConstants.BLANK_LINE);
		res = this.replace(res, RegexpConstants.HTML_INS);
		res = this.replace(res, RegexpConstants.HTML_BUTTON);
		res = this.replace(res, RegexpConstants.REPLACE_EMPTY_P);
		res = this.replace(res, RegexpConstants.REPLACE_EMPTY_SPAN);
		res = this.replace(res, RegexpConstants.REPLACE_EMPTY_LI);
		res = this.replace(res, RegexpConstants.REPLACE_EMPTY_H);
		res = this.replace(res, RegexpConstants.REPLACE_EMPTY_UL);
		res = this.replace(res, RegexpConstants.REPLACE_ALL_IFRAME);
		res = this.replace(res, RegexpConstants.REPLACE_BR, "<br/>");
		res = this.replace(res, RegexpConstants.REPLACE_BR_, "<br/>");
		res = this.replace(res, RegexpConstants.REPLACE_H_, "<br/>");
		res = this.replace(res, RegexpConstants.REPLACE_BR_2, "<br/>");
		res = this.replace(res, RegexpConstants.REPLACE_FONT_SIZE);
		res = this.replace(res, RegexpConstants.REPLACE_EMPTY_B);
		res = this.replace(res, RegexpConstants.REPLACE_STRONG);
		res = this.replace(res, RegexpConstants.REPLACE_SPAN);
		res = this.replace(res, RegexpConstants.REPLACE_BR_2, "<br/>");
		res = this.replace(res, RegexpConstants.REPLACE_BR_2, "<br/>");
		res = this.removeBlankLine(res);
		res=this.replaceTable(res);
		return res;
	}
	
	
	private String replaceTable(String content){
		String res=content;
		res=this.replace(res, "<[\\s\\/]*?table[^>]*?>");
		res=this.replace(res, "<[\\s\\/]*?tbody[^>]*?>");
		res=this.replace(res, "<[\\s\\/]*?thead[^>]*?>");
		res=this.replace(res, "<[\\s\\/]*?tr[^>]*?>");
		res=this.replace(res, "<[\\s\\/]*?th[^>]*?>");
		res=this.replace(res, "<[\\s\\/]*?td[^>]*?>");
		return res;
	}

	/**
	 * 删除空白行
	 * @todo
	 * @param content
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年1月7日
	 */
	public String removeBlankLine(String content) {
		String res = "";
		BufferedReader bufferReader = null;
		StringBuilder sb = new StringBuilder();
		if (StringUtils.isNotEmpty(content)) {
			bufferReader = new BufferedReader(new StringReader(content));
			String s;
			try {
				while ((s = bufferReader.readLine()) != null) {
					String x = new String(s);
					if (StringUtils.isNotEmpty(StringUtils.trim(x))) {
						sb.append(StringUtils.trim(s));
						sb.append("\n");
					}
					// buffer.append("\n");
				}
			} catch (IOException e) {
				logger.error(">>--->" + e.toString());
			} finally {
				try {
					bufferReader.close();
				} catch (IOException e) {
					logger.error(">>--->" + e.toString());
				}
			}
			// buffer.append("\n");
			res = sb.toString();
		}
		return res;
	}

	public String replaceAll(String content) {
		content = this.replace(content, RegexpConstants.HTML_ALL);
		content = this.replace(content, " ");
		// 过滤掉nbsp;
		content = this.replace(content, "&nbsp;");
		content = this.replace(content, "&rdquo;");
		content = this.replace(content, "&amp;");
		// 去除所有回车换行
		content = this.replace(content, "(\r\n|\r|\n|\n\r)");
		return content;
	}

	public String replace(String content, String regex) {
		String res = "";
		if (StringUtils.isNotEmpty(content) && StringUtils.isNotEmpty(regex)) {
			res = content.replaceAll(regex, "");
		}
		return res;
	}

	public String replace(String content, String regex, String replaceWith) {
		String res = "";
		if (StringUtils.isNotEmpty(content) && StringUtils.isNotEmpty(regex)) {
			res = content.replaceAll(regex, replaceWith);
		}
		return res;
	}

	/**
	 * 取得页面中的链接
	 * @todo
	 * @param content
	 * @return
	 * @author:@haipenge
	 * haipenge@gmail.com
	 * 2015年2月25日
	 */
	public List<Map<String, String>> getLinks(String content) {
		List<Map<String, String>> links = new ArrayList<Map<String, String>>();
		try {
			links = RegexpUtil.match(content, RegexpConstants.DISTIL_A_HREF);
		} catch (Exception e) {
			logger.error(">>FaceYe throws Exception: --->" + e.toString());
		}
		return links;
	}

	// /**
	// * 取得网页编码
	// * @todo
	// * @param content
	// * @return
	// * @author:@haipenge
	// * haipenge@gmail.com
	// * 2015年1月2日
	// */
	// public String getCharset(String content){
	// String charset="";
	// MetaData meta=this.getMetas(content);
	// if(null!=meta){
	// String contentType=meta.get("Content-Type");
	// if(StringUtils.isNotEmpty(contentType)){
	// String [] contentTypeArray=contentType.split(";");
	// if(null!=contentTypeArray&&contentTypeArray.length>0){
	// for(String element:contentTypeArray){
	// element=StringUtils.trim(element);
	// if(element.indexOf("charset")!=-1){
	// String[] charsetGroup=element.split("=");
	// if(null!=charsetGroup&&charsetGroup.length>=2){
	// charset=charsetGroup[1];
	// }
	// }
	// }
	// }
	// }
	// }
	// return charset;
	// }
	//
	public String getCharset(Header[] headers) {
		String res = "";
		if (null != headers) {
			for (Header header : headers) {
				String name = header.getName();
				String value = header.getValue();
				// logger.debug(">>FaceYe --> Heaer:"+name+"="+value);
				if (StringUtils.isNotEmpty(name) && StringUtils.isNotEmpty(value)) {
					if (StringUtils.equals(name.toLowerCase(), "Content-Type".toLowerCase())) {
						value = value.toLowerCase();
						if (value.indexOf("utf") != -1) {
							res = "UTF-8";
						} else if (value.indexOf("gbk") != -1) {
							res = "gbk";
						} else if (value.indexOf("gb2312") != -1) {
							res = "gb2312";
						}
					}
				}
			}
		}
		return res;
	}
}
