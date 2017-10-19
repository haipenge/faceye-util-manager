package com.faceye.feature.service.export.excel.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

public class DHeader implements Serializable {
    /**
     * 表格名称
     */
	private String title="";
	/**
	 * 表头
	 */
	private List<DCell> headers=null;
	public DHeader(){
		if(CollectionUtils.isEmpty(headers)){
			headers=new ArrayList<DCell>(0);
		}
	}
	
	public DHeader add(DCell cellHeader){
		headers.add(cellHeader);
		return this;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<DCell> getHeaders() {
		return headers;
	}

	public void setHeaders(List<DCell> headers) {
		this.headers = headers;
	}
	
	
}
