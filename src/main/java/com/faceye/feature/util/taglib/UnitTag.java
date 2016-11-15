package com.faceye.feature.util.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

/**
 * 单位标签
 * @author @haipenge 
 * @联系:haipenge@gmail.com
 * 创建时间:2015年9月27日
 */
public class UnitTag extends SimpleTagSupport {
	private String value = "";
	private String unit = "件";

	public void doTag() throws JspException, IOException {
		JspWriter out = getJspContext().getOut();
		String html = this.build();
		out.println(html);
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	private String build() {
		StringBuilder sb = new StringBuilder();
		if (value != null) {
			sb.append(value);
		} else {
			sb.append("<font color=\"red\">未知</font>");
		}
		sb.append(unit);
		return sb.toString();
	}
}
