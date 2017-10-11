package com.faceye.feature.service.export.excel.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Excel一行记录
 * @author songhaipeng
 *
 */
public class DRecord implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1310656616594878459L;
	private List<DCell> cells=null;
	
	public DRecord(){
		if(cells==null){
			cells=new ArrayList<DCell>();
		}
	}
	/**
	 * 向记录添加Cell数据
	 * @param cell
	 * @return
	 */
	public DRecord add(DCell cell){
		if(cell!=null){
			cells.add(cell);
		}
		return this;
	}
	public List<DCell> getCells() {
		return cells;
	}
	public void setCells(List<DCell> cells) {
		this.cells = cells;
	}
	
	
}
