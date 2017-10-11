package com.faceye.feature.service.export.excel.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.faceye.feature.service.export.excel.ExcelService;
import com.faceye.feature.service.export.excel.data.DCell;
import com.faceye.feature.service.export.excel.data.DRecord;
import com.faceye.feature.service.export.excel.data.DSheet;

/**
 * 将数据导出到Excel
 * 
 * @author songhaipeng
 *
 */
@Service
public class ExcelServiceImpl implements ExcelService {

	private Logger logger = LoggerFactory.getLogger(ExcelServiceImpl.class);

	/**
	 * 导出数据到Excel
	 */
	public void export(DSheet dsheet, OutputStream stream) {
		// 创建工作薄
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 创建工作表
		HSSFSheet sheet = workbook.createSheet("sheet1");
		// 构建表头
		int rowIndex = 0;
		HSSFRow rowHeader = sheet.createRow(rowIndex);
		for (int i = 0; i < dsheet.getHeader().getHeaders().size(); i++) {
			String value = "";
			if (dsheet.getHeader().getHeaders().get(i).getV() != null) {
				value = dsheet.getHeader().getHeaders().get(i).getV().toString();
			}
			rowHeader.createCell(i).setCellValue(value);
		}
		rowIndex++;
		// 向sheet填充数据
		for (int i = 0; i < dsheet.getRecords().size(); i++) {
			HSSFRow row = sheet.createRow(rowIndex);
			DRecord dRecord = dsheet.getRecords().get(i);
			for (int j = 0; j < dRecord.getCells().size(); j++) {
				DCell dCell = dRecord.getCells().get(j);
				String value = "";
				if (dCell != null && dCell.getV() != null) {
					value = dCell.getV().toString();
				}
				row.createCell(j).setCellValue(value);
			}
			rowIndex++;
		}
		// File xlsFile = new File("d:/poi.xls");
		// FileOutputStream xlsStream=null;
		try {
			if(stream==null){
				stream=this.getDefaultOutputStream();
			}
			workbook.write(stream);
		} catch (FileNotFoundException e) {
			logger.error(">>Exception:" + e);
		} catch (IOException e) {
			logger.error(">>Exception:" + e);
		} finally {
			if (stream != null) {
				try {
					stream.flush();
					stream.close();
				} catch (IOException e) {
					logger.error(">>Exception:" + e);
				}
			}
		}

	}

	/**
	 * 导出数据到excel
	 */
	public void export(DSheet dsheet) {
		OutputStream stream = this.getDefaultOutputStream();
		this.export(dsheet, stream);
	}

	private OutputStream getDefaultOutputStream() {
		File xlsFile = new File("d:/poi.xls");
		FileOutputStream stream = null;
		try {
			stream = new FileOutputStream(xlsFile);
		} catch (FileNotFoundException e) {
			logger.error(">>Exception:" + e);
		}
		return stream;
	}

}
