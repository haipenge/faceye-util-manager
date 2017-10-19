package com.faceye.feature.service.export.excel.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

/**
 * Sheet数据记录
 * @author songhaipeng
 *
 */
public class DSheet implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7013413571708877136L;

	private DHeader header=null;
	
	private List<DRecord> records=null;
	
	public DSheet(){
		if(CollectionUtils.isEmpty(records)){
			records=new ArrayList<DRecord>(0);
		}
	}
	/**
	 * 向sheet中添加记录
	 * @param record
	 * @return
	 */
	public DSheet add(DRecord record){
		records.add(record);
		return this;
	}
	public DHeader getHeader() {
		return header;
	}
	public void setHeader(DHeader header) {
		this.header = header;
	}
	public List<DRecord> getRecords() {
		return records;
	}
	public void setRecords(List<DRecord> records) {
		this.records = records;
	}
	
}
