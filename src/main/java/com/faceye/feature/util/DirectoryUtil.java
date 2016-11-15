package com.faceye.feature.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 文件目录操作工具
 * 
 * @author @haipenge haipenge@gmail.com Create Date:2015年4月10日
 */
public class DirectoryUtil {
	private static Logger logger = LoggerFactory.getLogger(DirectoryUtil.class);

	/**
	 * 复制文件
	 * 
	 * @todo
	 * @param sourceFile
	 * @param targetFile
	 * @throws IOException
	 * @author:@haipenge haipenge@gmail.com 2015年4月10日
	 */
	public static void copyFile(File sourceFile, File targetFile) throws IOException {
		BufferedInputStream inBuff = null;
		BufferedOutputStream outBuff = null;
		try {
			logger.debug(">>FaceYe -->Copy source file :" + sourceFile.getName());
			// 新建文件输入流并对它进行缓冲
			inBuff = new BufferedInputStream(new FileInputStream(sourceFile));
			// 新建文件输出流并对它进行缓冲
			outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));
			// 缓冲数组
			byte[] b = new byte[1024 * 5];
			int len;
			while ((len = inBuff.read(b)) != -1) {
				outBuff.write(b, 0, len);
			}
			// 刷新此缓冲的输出流
			outBuff.flush();
		} catch (Exception e) {
			logger.error(">>FaceYe --> copy file exception :", e);
		} finally {
			// 关闭流
			if (inBuff != null)
				inBuff.close();
			if (outBuff != null)
				outBuff.close();
		}
	}

	/**
	 * 复制文件夹
	 * 
	 * @todo
	 * @param sourceDir
	 * @param targetDir
	 * @throws IOException
	 * @author:@haipenge haipenge@gmail.com 2015年4月10日
	 */
	public static void copyDirectiory(String sourceDir, String targetDir) throws IOException {
		copyDirectiory(sourceDir, targetDir, false);
	}

	public static void copyDirectiory(String sourceDir, String targetDir, Boolean isReWrite) throws IOException {
		// 新建目标目录
		(new File(targetDir)).mkdirs();
		// 获取源文件夹当前下的文件或目录
		File[] file = (new File(sourceDir)).listFiles();
		if (file != null) {
			for (int i = 0; i < file.length; i++) {
				if (file[i].isFile()) {
					// 源文件
					File sourceFile = file[i];
					// 目标文件
					File targetFile = new File(new File(targetDir).getAbsolutePath() + File.separator + file[i].getName());
					if (!targetFile.exists() || isReWrite) {
						copyFile(sourceFile, targetFile);
					}
				}
				if (file[i].isDirectory()) {
					// 准备复制的源文件夹
					String dir1 = sourceDir + "/" + file[i].getName();
					// 准备复制的目标文件夹
					String dir2 = targetDir + "/" + file[i].getName();
					copyDirectiory(dir1, dir2, isReWrite);
				}
			}
		}
	}

	public static List<String> getDirectoryFileUrls(String dir, String fileSuffix, List<String> result) {
		File[] files = (new File(dir)).listFiles();
		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				if (files[i].isFile()) {
					File file = files[i];
					String url = file.getAbsolutePath();
					if (StringUtils.endsWith(url, fileSuffix)) {
						result.add(url);
					}
				}else{
					getDirectoryFileUrls(files[i].getAbsolutePath(),fileSuffix,result);
				}
			}
		}
		return result;
	}
	
	public static String [] getChildrenDirs(String path){
		String [] res=null;
		File[] files = (new File(path)).listFiles();
		List<String> paths=new ArrayList<String>();
		if(files!=null){
			for(File file:files){
				if(file.isDirectory()){
					paths.add(file.getAbsolutePath());
				}
			}
			res=paths.toArray(new String[paths.size()]);
		}
		
		return res;
	}

	/**
	 * 
	 * @param srcFileName
	 * @param destFileName
	 * @param srcCoding
	 * @param destCoding
	 * @throws IOException
	 */
	public static void copyFile(File srcFileName, File destFileName, String srcCoding, String destCoding) throws IOException {// 把文件转换为GBK文件
		BufferedReader br = null;
		BufferedWriter bw = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(srcFileName), srcCoding));
			bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(destFileName), destCoding));
			char[] cbuf = new char[1024 * 5];
			int len = cbuf.length;
			int off = 0;
			int ret = 0;
			while ((ret = br.read(cbuf, off, len)) > 0) {
				off += ret;
				len -= ret;
			}
			bw.write(cbuf, 0, off);
			bw.flush();
		} finally {
			if (br != null)
				br.close();
			if (bw != null)
				bw.close();
		}
	}

	/**
	 * 删除文件(夹)
	 * 
	 * @param filepath
	 * @throws IOException
	 */
	public static void delete(String filepath) throws IOException {
		logger.debug(">>FaceYe Delete File path is:" + filepath);
		File f = new File(filepath);// 定义文件路径
		if (f.exists() && f.isDirectory()) {// 判断是文件还是目录
			if (f.listFiles().length == 0) {// 若目录下没有文件则直接删除
				f.delete();
			} else {// 若有则把文件放进数组，并判断是否有下级目录
				File delFile[] = f.listFiles();
				int i = f.listFiles().length;
				for (int j = 0; j < i; j++) {
					if (delFile[j].isDirectory()) {
						delete(delFile[j].getAbsolutePath());// 递归调用del方法并取得子目录路径
					}
					delFile[j].delete();// 删除文件
				}
			}
		}
	}

	/**
	 * 批量替换
	 * 
	 * @todo
	 * @param dir/filepath
	 * @param pattern
	 * @param replaceWith
	 * @author:@haipenge haipenge@gmail.com 2015年4月10日
	 */
	public static void replace(String dir, String pattern, String replacement) {
		Map<String, String> map = new HashMap();
		logger.debug(">>FaceYe do replace now:dir:" + dir + ",pattern:" + pattern + ",replacement:" + replacement);
		map.put("pattern", pattern);
		map.put("replacement", replacement);
		List<Map<String, String>> replaces = new ArrayList();
		replaces.add(map);
		replace(dir, replaces);
	}

	public static void replace(String dir, List<Map<String, String>> replaces) {

		File file = new File(dir);
		String lineSeparator = System.getProperty("line.separator", "\r\n");
		if (file.exists()) {
			if (file.isDirectory()) {
				File[] subFiles = file.listFiles();
				for (int i = 0; i < subFiles.length; i++) {
					replace(subFiles[i].getAbsolutePath(), replaces);
				}
			} else {
				try {
					List<String> lines = IOUtils.readLines(new FileReader(dir));
					StringBuilder sb = new StringBuilder();
					for (String line : lines) {
						sb.append(line);
						sb.append(lineSeparator);
					}
					String content = sb.toString();
					for (Map<String, String> map : replaces) {
						String pattern = map.get("pattern");
						String replacement = map.get("replacement");
						content = content.replaceAll(pattern, replacement);
					}
					FileUtils.writeStringToFile(file, content, "UTF-8", false);
					// FileUtils.write(file, content, "UTF-8", false);
				} catch (FileNotFoundException e) {
					logger.error(">>FaceYe throws Exception: --->", e);
				} catch (IOException e) {
					logger.error(">>FaceYe throws Exception: --->", e);
				}
			}
		} else {
			logger.debug(">>FaceYE,dir :" + dir + ",not exist.");
		}
	}

}
