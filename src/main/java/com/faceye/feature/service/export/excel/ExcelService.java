package com.faceye.feature.service.export.excel;

import java.io.OutputStream;

import com.faceye.feature.service.export.excel.data.DSheet;

public interface ExcelService {

	public void export(DSheet dsheet);
	
	
	public void export(DSheet dsheet,OutputStream stream);
	
}
