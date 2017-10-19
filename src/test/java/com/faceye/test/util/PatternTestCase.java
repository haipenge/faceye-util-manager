package com.faceye.test.util;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class PatternTestCase {
    private String str="ESINFO";
	@Test
	public void testChinese() throws Exception {
		boolean res = false;
		String regexp = "[0-9A-Za-zu4e00-u9fa5].+";
		regexp = "[\\x01-\\x7f]|[\\xc2-\\xdf][\\x80-\\xbf]|[\\xe0-\\xef][\\x80-\\xbf]{2}|[\\xf0-\\xff][\\x80-\\xbf]{3}";
		regexp = "[\uDB40DC00-\uDB7FDFFF]";
		regexp = "\\w+";
		regexp = "[u4e00-u9fa5]";
		regexp = "[u4e00-u9fa5]";
		regexp = "w.+";
		regexp = "^[\u4E00-\u9FA5]+$";
		String str = "匹配并捕获文本到自动命名的 组 里34AADNs在，dx（）()--sl顺949 0s--";
		// str="匹配并捕获fds3文本到自动4命名的组里fs";
		// Pattern patern = Pattern.compile(regexp);
		// Matcher matcher = patern.matcher(str);
		// res = matcher.matches();
		Assert.assertTrue(isUTF8Chinese(str));
	}

	@Test
	public void testUnChinese() throws Exception {
		boolean res = true;
		String regexp = "[0-9A-Za-zu4e00-u9fa5].+";
		regexp = "^[\u4E00-\u9FA5]+$";
		String str = "®����ҵ԰����·52�����ǲ���������֪�����";
		Assert.assertFalse(isUTF8Chinese(str));
	}
	
	@Test
	public void testMatchUTF8() throws Exception {
		boolean res = false;
		String str = "\\xF0\\x9F\\x91\\x8C";
		res = isUTF8Chinese(str);
		str = "匹配并捕获文本到自动命名的 组 里34AADNs在，dx（）()--sl顺949 0s--";
		res = isUTF8Chinese(str);
		str = "®����ҵ԰����·52�����ǲ���������֪�����";
		res = isUTF8Chinese(str);
		Assert.assertTrue(res);

	}

	private boolean isUTF8Chinese(String str) {
		boolean res = true;
		if(StringUtils.isEmpty(str)){
			return res;
		}
		char[] chars = str.toCharArray();
		for (char c : chars) {
			res = isChinese(c);
			if (!res) {
				// 是否为数字
				res = Character.isDigit(c);
			}
			if (!res) {
				// 是否为字母（含符号，半角）33-126
				// 仅字母：(v >= 65 && v <= 90) || (v >= 97 && v <= 122)
				int v = (int) c;
				if (v >= 32 && v <= 126) {
					res = true;
				}
			}
			if (!res) {
				// 是否为字母（全角）
				int v = (int) c;
				if (v >= 65281 && v <= 65374) {
					res = true;
				}
			}
			if (!res) {
				// 是否为空格
				int v = (int) c;
				res = v == 12288;
			}

			if (!res) {
				break;
			}
		}
		if(res){
			res=isCorrectUTF8Character(str);
		}
		return res;
	}

	private static boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_B
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_C
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_D
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS_SUPPLEMENT
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_FORMS
				|| ub == Character.UnicodeBlock.VERTICAL_FORMS) {
			return true;
		}
		return false;
	}

	/**
	 * 是否为正确的可识别U8码
	 */
	String regexp = "[\\x00-\\xff].+";
	Pattern patern = Pattern.compile(regexp);
	private boolean isCorrectUTF8Character(String str) {
		boolean res = false;
		Matcher matcher = patern.matcher(str);
		res = matcher.matches();
		return !res;
	}
	@Test
	public void testEquals() throws Exception{
		String s="ESINFO";
		Assert.assertTrue("ESINFO".equals(str));
	}
}
