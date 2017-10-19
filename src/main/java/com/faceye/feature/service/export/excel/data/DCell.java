package com.faceye.feature.service.export.excel.data;

import java.io.Serializable;
/**
 * Excel 一个单元格数据
 * @author songhaipeng
 *
 */
public class DCell<T> implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8464786506875885596L;
	/**
	 * Cell值
	 */
	private T v = null;

	public T getV() {
		return v;
	}

	public void setV(T v) {
		this.v = v;
	}
	
}
