package com.faceye.test.util;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.faceye.feature.util.FileUtil;

@RunWith(JUnit4.class)
public class FileUtilTestCase {
	@Test
	public void testRead() throws Exception {
		String[] lines = FileUtil.readText("e:/cdoe.txt");
		FileUtil.write("e:/output.txt", "List<SenderAddress> addrs=new ArrayList<>(0);\n");
		int count=0;
		for(String line:lines){
			StringBuffer sb =new StringBuffer();
			String [] splits=line.split("\t");
			sb.append("SenderAddress addr"+count+"=new SenderAddress();");
			sb.append("\n");
			sb.append("addr"+count+".setProvinceCode(");
			sb.append("\""+splits[0]+"\"");
			sb.append(");");
			sb.append("\n");
			
			sb.append("addr"+count+".setCityCode(");
			sb.append("\""+splits[1]+"\"");
			sb.append(");");
			sb.append("\n");
			
			sb.append("addr"+count+".setCountyCode(");
			sb.append("\""+splits[2]+"\"");
			sb.append(");");
			sb.append("\n");
			
			sb.append("addr"+count+".setProvince(");
			sb.append("\""+splits[3]+"\"");
			sb.append(");");
			sb.append("\n");
			
			sb.append("addr"+count+".setCity(");
			sb.append("\""+splits[4]+"\"");
			sb.append(");");
			sb.append("\n");
			
			sb.append("addr"+count+".setCountry(");
			sb.append("\""+splits[5]+"\"");
			sb.append(");");
			sb.append("\n");
			sb.append("addrs.add(addr"+count+");\n");
			FileUtil.write("e:/output.txt", sb.toString());
			count++;
			
		}
		Assert.assertTrue(lines != null && lines.length > 0);
	}
}
