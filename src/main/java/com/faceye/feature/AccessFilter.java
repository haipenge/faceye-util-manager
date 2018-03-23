package com.faceye.feature;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @author songhaipeng
 *
 */
public class AccessFilter implements Filter {

	public void destroy() {
	}

	/**
	 * 对Http method 进行过滤,放行get,post操作,其它操作一概拒绝
	 */
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {
		boolean isAllowMethod=false;
		HttpServletRequest request=(HttpServletRequest) arg0;
		HttpServletResponse response =(HttpServletResponse) arg1;
		String method=request.getMethod();
		if(method!=null && !method.equals("")){
			if(method.equalsIgnoreCase("GET")|| method.equalsIgnoreCase("POST")){
				isAllowMethod=true;
			}
		}
		if(!isAllowMethod){
			response.setStatus(405);
			return;
		}else{
			arg2.doFilter(request, response);
		}
	}

	public void init(FilterConfig arg0) throws ServletException {
	}

}
