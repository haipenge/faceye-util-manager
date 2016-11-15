package com.faceye.feature.util.ant;

import java.io.File;

import org.apache.tools.ant.DefaultLogger;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.ProjectHelper;

/**
 * 执行ant脚本的工具类
 * @author @haipenge 
 * @联系:haipenge@gmail.com
 * 创建时间:2015年4月17日
 */
public class Runner {
	/**
	 * 执行ant 脚本
	 * @todo
	 * @param buildXmlPath
	 * @param target
	 * @param level
	 * @return
	 * @author:@haipenge
	 * 联系:haipenge@gmail.com
	 * 创建时间:2015年4月17日
	 */
	public static String exec(String buildXmlPath, String target) {
		String res = "";
		File buildXml = new File(buildXmlPath);
		Project project = new Project();
		DefaultLogger consoleLogger = new DefaultLogger();
		consoleLogger.setErrorPrintStream(System.err);
		consoleLogger.setOutputPrintStream(System.out);
		consoleLogger.setMessageOutputLevel(Project.MSG_INFO);
		project.addBuildListener(consoleLogger);
		ProjectHelper helper = ProjectHelper.getProjectHelper();
		helper.parse(project, buildXml);
		project.executeTarget(target);
		project.fireBuildFinished(null);
		return res;
	}
}
