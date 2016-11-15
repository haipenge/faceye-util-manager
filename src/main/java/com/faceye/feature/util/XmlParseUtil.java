package com.faceye.feature.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * XMl解析工具
 * @author @haipenge 
 * @联系:haipenge@gmail.com
 * 创建时间:2015年7月8日
 */
public class XmlParseUtil {

	private static Logger logger = LoggerFactory.getLogger(XmlParseUtil.class);

	/**
	 * 将xml解析为map结构,只能解析一层xml结构,不处理多层结构
	 * 
	 * @todo,多层xml的解析
	 * @param xml
	 * @return
	 * @author:@haipenge
	 * 联系:haipenge@gmail.com
	 * 创建时间:2015年7月8日
	 */
	public static Map toMap(String xml) {
		Map map = new HashMap();
		if (StringUtils.isNotEmpty(xml)) {
			toMap(map, xml);
		}
		return map;
	}

	private static void toMap(Map map, String xml) {
		Document doc;
		try {
			doc = DocumentHelper.parseText(xml);
			Element rootElement = doc.getRootElement();
			List<Element> elements = rootElement.elements();
			if (CollectionUtils.isNotEmpty(elements)) {
				for (Element element : elements) {
					String name = element.getName();
					String value = element.getText();
					map.put(name, value);
				}
			}
		} catch (DocumentException e) {
			logger.error(">>FaceYe throws Exception: --->", e);
		}
	}

}
