package com.faceye.feature.util.security;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
 * Md5编码处理类，sign方法中加入了签名串排序
 * @author vincent
 *
 */
public class Md5 {

	private static Md5 md5 = new Md5();

	public static Md5 getInstance() {
		return md5;
	}

	public static String sign(Map<String, String> params, String privateKey) {
		List keys = new ArrayList(params.keySet());
		Collections.sort(keys);
		StringBuffer content = new StringBuffer();
		for (int i = 0; i < keys.size(); ++i) {
			String key = (String) keys.get(i);
            if ("sign".equalsIgnoreCase(key)) {
                continue;
            }
            
            String value = (String) params.get(key);
            if (StringUtils.isNotEmpty(value) && !"null".equalsIgnoreCase(value)) {
                content.append((content.length() == 0 ? "" : "&") + key + "=" + value);
            } else {
//                content.append((i == 0 ? "" : "&") + key + "=");
            }
		}
		String signcontent = StringUtils.isEmpty(privateKey)?content.toString():content + privateKey;

		return encrypt(signcontent);
	}
	
	public static String sign(String url, String privateKey) {			
		String signcontent = StringUtils.isEmpty(privateKey)?url:url + privateKey;

		return encrypt(signcontent);
	}

	public static String bytesToHexString(byte[] bArray) {
		StringBuffer sb = new StringBuffer(bArray.length);

		for (int i = 0; i < bArray.length; ++i) {
			String sTemp = Integer.toHexString(0xFF & bArray[i]);
			if (sTemp.length() < 2)
				sb.append(0);
			sb.append(sTemp.toUpperCase());
		}
		return sb.toString();
	}

	public static byte[] hexToByte(String hex) {
		byte[] ret = new byte[8];
		byte[] tmp = hex.getBytes();
		for (int i = 0; i < 8; ++i) {
			ret[i] = unitBytes(tmp[(i * 2)], tmp[(i * 2 + 1)]);
		}
		return ret;
	}

	public static byte unitBytes(byte src0, byte src1) {
		byte _b0 = Byte.decode("0x" + new String(new byte[] { src0 }))
				.byteValue();
		_b0 = (byte) (_b0 << 4);
		byte _b1 = Byte.decode("0x" + new String(new byte[] { src1 }))
				.byteValue();
		byte ret = (byte) (_b0 ^ _b1);
		return ret;
	}

	public static String encrypt(String source) {
		MessageDigest md = null;
		byte[] bt = source.getBytes();
		try {
			bt = source.getBytes("UTF-8");
			md = MessageDigest.getInstance("MD5");
			md.update(bt);
			return bytesToHexString(md.digest());
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		return null;
	}

	public static void main(String[] args) {
		
		Map map=new HashMap<String, String>();
		map.put("1", "1");
		map.put("5", "5");
		map.put("3", "");
		map.put("4", null);
		map.put("2", "2");
		map.put("6", "6");
		map.put("sign", "5555");
		for(int i=0;i<10;i++){
		String mysig = sign(map,"123456abcdefG");
		System.out.println(mysig);
		}
	}
}
