package com.faceye.feature.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.junit.Assert;

/**
 * 使用通配符加载资源文件(国际化文件)
 * @author @haipenge 
 * @联系:haipenge@gmail.com
 * 创建时间:2015年4月16日
 */
public class RegexPropertyMessageResources extends ReloadableResourceBundleMessageSource {
	org.springframework.context.support.ResourceBundleMessageSource r = null;
	org.springframework.context.support.ReloadableResourceBundleMessageSource ss = null;
	private Logger logger = LoggerFactory.getLogger(getClass());

	private String[] basenames = new String[0];
	private PathMatchingResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();

	// public RegexPropertyMessageResources() {
	//
	// }
	//
	// public RegexPropertyMessageResources(String basename) {
	// this(new String[] { basename });
	// }
	//
	// public RegexPropertyMessageResources(String[] basenames) {
	// setBasenames(basenames);
	// }
	@Override
	public void setBasenames(String... basenames) {
		List<String> names = new ArrayList<String>(0);
		if (basenames != null) {
			this.basenames = new String[basenames.length];
			for (int i = 0; i < basenames.length; i++) {
				String basename = basenames[i];
				try {
					Resource[] resources = patternResolver.getResources(basename);
					if (resources != null) {
						for (Resource resource : resources) {
							String name = resource.getFilename();
							// String path=basename.replace(name, "");
							 //String path="classpath*:i18n/";
							String path = "classpath:/i18n/";
							
//							String path="i18n/";
							// logger.debug(">>i18n regex name is:"+name+",path is :"+resource.getFile().getAbsolutePath());
							String i18n = path + name.substring(0, name.lastIndexOf("_zh_CN"));
//							String i18n=path+name;
							logger.debug("I18N --> is :" + i18n);
							names.add(i18n);
						}
					}
				} catch (IOException e) {
					logger.error(">>FaceYe throws Exception: --->", e);
				}
				Assert.hasText(basename, "Basename must not be empty");
				// this.basenames[i] = basename.trim();
			}
			super.setBasenames(names.toArray(new String[names.size()]));
		} else {
			this.basenames = new String[0];
		}
	}

}
