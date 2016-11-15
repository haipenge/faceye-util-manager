package com.faceye.feature.util.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 * 货币标签
 * @author @haipenge 
 * @联系:haipenge@gmail.com
 * 创建时间:2015年9月27日
 */
public class CurrencyTag extends SimpleTagSupport {
	private Float value = null;

	public void doTag() throws JspException, IOException {
		JspWriter out = getJspContext().getOut();
		String html = this.build();
		out.println(html);
	}

	public Float getValue() {
		return value;
	}

	public void setValue(Float value) {
		this.value = value;
	}

	private String build() {
		StringBuilder sb = new StringBuilder();
		sb.append("￥&nbsp;");
		if (value != null) {
			sb.append(value);
		} else {
			sb.append("<font color=\"red\">价格未标注</font>");
		}
		sb.append("&nbsp;元");
		return sb.toString();
	}
}
