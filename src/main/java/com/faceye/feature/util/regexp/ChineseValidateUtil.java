package com.faceye.feature.util.regexp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * 中文校验工具类
 * 
 * @author songhaipeng
 *
 */
public class ChineseValidateUtil {
	private static String REGEXP = "[\\x00-\\xff].+";
	private static Pattern PATTERN = Pattern.compile(REGEXP);

	/**
	 * 判断是否为U8格式中文字符
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isUTF8Chinese(String str) {
		boolean res = true;
		if (StringUtils.isEmpty(str)) {
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
		//排除转码字符
		if (res) {
			res = isCorrectUTF8Character(str);
		}
		return res;
	}

	/**
	 * 是否是汉字，全角字符，半角字符
	 *   CJK Unified Ideographs，总共定义了 74,617 个汉字
	 *   GENERAL_PUNCTUATION 判断中文的“号
	 *   CJK_SYMBOLS_AND_PUNCTUATION 判断中文的。号 
	 *   HALFWIDTH_AND_FULLWIDTH_FORMS 判断中文的，号
	 *   U2000-General Punctuation (百分号，千分号，单引号，双引号等)
	 *   U3000-CJK Symbols and Punctuation ( 顿号，句号，书名号，〸，〹，〺 等；PS: 后面三个字符你知道什么意思吗？ : )    )
	 *   UFF00-Halfwidth and Fullwidth Forms ( 大于，小于，等于，括号，感叹号，加，减，冒号，分号等等)
	 *   UFE30-CJK Compatibility Forms  (主要是给竖写方式使用的括号，以及间断线﹉，波浪线﹌等)
	 *   UFE10-Vertical Forms (主要是一些竖着写的标点符号，    等等)
	 *   CJK Unified Ideographs Extension A, B, C, D 分别都扩展了一些我们平时见都没见过的汉字
	 * 
	 * @param c
	 * @return
	 */
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

	private static boolean isCorrectUTF8Character(String str) {
		boolean res = false;
		Matcher matcher = PATTERN.matcher(str);
		res = matcher.matches();
		return !res;
	}
}
