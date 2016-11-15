package com.faceye.feature.util.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.apache.commons.lang3.StringUtils;

/**
 * 布尔值显示标签
 * @author @haipenge 
 * @联系:haipenge@gmail.com
 * 创建时间:2015年4月17日
 */
public class BooleanTag extends SimpleTagSupport {
	public Boolean value = false;
	public String show = "";

	public Boolean getValue() {
		return value;
	}

	public void setValue(Boolean value) {
		this.value = value;
	}

	public String getShow() {
		String res=show;
		if(StringUtils.isNotEmpty(show)&&StringUtils.contains(show, "|")){
			String [] str=StringUtils.split(show, "|");
			if(str!=null && str.length==2){
				if(this.getValue()){
					res=str[0];
				}else{
					res=str[1];
				}
			}
		}
		return res;
	}

	public void setShow(String show) {
		this.show = show;
	}

	public void doTag() throws JspException, IOException {
		JspWriter out = getJspContext().getOut();
		String html = this.build();
		out.println(html);
	}

	private String build() {
		StringBuilder sb = new StringBuilder("");
		String defaultClass = "label label-primary";
		if (!this.getValue()) {
			defaultClass = "label label-danger";
		}
		sb.append("<span class=\"");
		sb.append(defaultClass);
		sb.append("\">");
		if (this.getValue()) {
			sb.append(StringUtils.isEmpty(this.getShow()) ? "是" : this.getShow());
		} else {
			sb.append(StringUtils.isEmpty(this.getShow()) ? "否" : this.getShow());
		}
		sb.append("</span>");
		return sb.toString();
	}
}
