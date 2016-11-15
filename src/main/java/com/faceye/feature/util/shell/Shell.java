package com.faceye.feature.util.shell;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Shell调用工具
 * @author @haipenge 
 * @联系:haipenge@gmail.com
 * 创建时间:2015年4月17日
 */
public class Shell {
	private static Logger logger = LoggerFactory.getLogger(Shell.class);

	/**
	 * 运行shell
	 * @todo
	 * @param scriptUri
	 * @return
	 * @author:@haipenge
	 * 联系:haipenge@gmail.com
	 * 创建时间:2015年4月17日
	 */
	public static String runShell(String scriptUri) {
		String res = "";
		try {
			exec(scriptUri);
		} catch (IOException e) {
			logger.error(">>FaceYe throws Exception: --->", e);
		} catch (InterruptedException e) {
			logger.error(">>FaceYe throws Exception: --->", e);
		}
		return res;
	}

	public static String runCommand(String command) {
		StringBuilder sb = new StringBuilder();
		String res = "";
		Process process;
		try {
			process = Runtime.getRuntime().exec(command);
			InputStreamReader ir = new InputStreamReader(process.getInputStream());
			LineNumberReader input = new LineNumberReader(ir);
			String line;
			process.waitFor();
			while ((line = input.readLine()) != null) {
				sb.append(line);
				sb.append("\r\n");
			}
		} catch (IOException e) {
			logger.error(">>FaceYe throws Exception: --->", e);
		} catch (InterruptedException e) {
			logger.error(">>FaceYe throws Exception: --->", e);
		}

		res = sb.toString();
		logger.debug(">>Command :" + command + ",result is:" + res);
		return res;
	}

	private static String exec(String scriptUrl) throws IOException, InterruptedException {
		StringBuilder sb = new StringBuilder();
		String res = "";
		Process process;
		String command = "/bin/sh " + scriptUrl;
		process = Runtime.getRuntime().exec(new String[] { "/bin/sh", "-c", "sh " + scriptUrl }, null, null);
		// process=Runtime.getRuntime().exec("java -version");
		// process=Runtime.getRuntime().exec(command);
		// process.waitFor();
		InputStreamReader ir = new InputStreamReader(process.getInputStream());
		LineNumberReader input = new LineNumberReader(ir);
		String line;
		process.waitFor();
		while ((line = input.readLine()) != null) {
			sb.append(line);
			sb.append("\r\n");
		}
		res = sb.toString();
		logger.debug(">>Command " + scriptUrl + ",exec result is:" + res);
		return res;
	}
}
