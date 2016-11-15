package com.faceye.feature.util.security;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

/**
 * 安全工具类，主要提供MD5签名、验签，客户密钥的加、解密方法
 * @author vincent
 *
 */
public class EncryptUtil {

//	/**
//	 * MD5签名方法
//	 * @param params 需要签名的字段MAP
//	 * @return 签名串
//	 */
//	public static String md5Sign4CifPay(Map<String, String> params){
//		
//		return Md5.sign(makeUrl(params), Constants.CIFPAY_PRIVATE_MD5_KEY_STR);
//	}
//
//	/**
//	 * MD5签名方法(含凯撒解密)
//	 * @param params 需要签名的字段MAP
//	 * @param privateKey 未解密的客户密钥
//	 * @return 签名串
//	 */
//	public static String md5Sign(Map<String, String> params, String privateKey){
//		if(null!=params && null!=privateKey){
//			String realKey=decrypt(privateKey);
//			
//			if(StringUtils.isNotEmpty(realKey)){		
//				return Md5.sign(makeUrl(params), realKey);
//			}
//		}
//		return null;
//	}
//
//	
//	/**
//	 * MD5验签方法(银信证传入参数签名规则)
//	 * @param params 需要签名的字段MAP
//	 * @param sign 需要验证的签名串
//	 * @param privateKey 未解密的客户密钥
//	 * @return true为验证通过，false为验证失败
//	 */
//	public static boolean md5Verify4ValidateCifPay(Map<String, String> params,String sign){
//		if(null!=params  && null != sign){
//			String realSign = md5Sign4CifPay(params);
//			
//			if(StringUtils.isNotEmpty(realSign) && realSign.equalsIgnoreCase(sign))
//				return true;
//		}
//		return false;
//	}
//	
//		
//	/**
//	 * MD5验签方法
//	 * @param params 需要签名的字段MAP
//	 * @param sign 需要验证的签名串
//	 * @param privateKey 未解密的客户密钥
//	 * @return true为验证通过，false为验证失败
//	 */
//	public static boolean md5Verify(Map<String, String> params,String sign, String privateKey){
//		if(null!=params && null!=privateKey && null!=sign){
//			String realSign=md5Sign(params, privateKey);
//			if(StringUtils.isNotEmpty(realSign) && realSign.equalsIgnoreCase(sign))
//				return true;
//		}
//		return false;
//	}

	/**
	 * 将字符串先做base64编码，然后再做Caesar加密	
	 * @param key  待加密串
	 * @return 已加密串
	 */
	public static String encrypt(String key){
		if(StringUtils.isNotEmpty(key)){
			String base64Str=Base64.encode(key);
			return Caesar.encrypt(base64Str);
		}
		return null;
	}

	/**
	 * 对做Caesar和base64的加密串解密
	 * @param key 待解密串
	 * @return  已解密串
	 */
	public static String decrypt(String key){
		if(StringUtils.isNotEmpty(key)){
			String decryptStr=Caesar.decrypt(key);
			return Base64.decode(decryptStr);
		}
		return null;
	}
	
	
	
	public static String makeUrl(Map<String,String> params){
		List keys = new ArrayList(params.keySet());
		Collections.sort(keys);
		StringBuffer content = new StringBuffer();
		
		for (int i = 0; i < keys.size(); ++i) {
			String key = (String) keys.get(i);
			
            if ("sign".equalsIgnoreCase(key)) {
                continue;
            }
            
            //null值或空值不添加
            String value = (String) params.get(key);
            if (StringUtils.isNotEmpty(value) && !"null".equalsIgnoreCase(value)) {
                content.append((content.length() == 0 ? "" : "&") + key + "=" + value);
            } else {
//                content.append((i == 0 ? "" : "&") + key + "=");
            }
		}
		
		
		return content.toString();
	
	}
	
	/**
	 * 按银信证约定拼装参数
	 * */
	public static String makeSignString(Map<String,String> params){
		List keys = new ArrayList(params.keySet());
		Collections.sort(keys);
		StringBuffer content = new StringBuffer();
		
		for (int i = 0; i < keys.size(); ++i) {
			String key = (String) keys.get(i);
			
            if ("sign".equalsIgnoreCase(key)) {
                continue;
            }
            
            //null值或空值不添加
            String value = (String) params.get(key);
            if (StringUtils.isNotEmpty(value)) {
                content.append(value);
            } 
		}
		
		return content.toString();
	
	}


	public static void main(String[] args){
		Map map=new HashMap<String, String>();
		map.put("bankTradeID", "1387889707824");
		map.put("validaTime", "2013-12-25 20:55:08");
		map.put("sign", "910E01C3F4CF6CA931802C9378CD0154");
		map.put("creditNo", "CIB*0.00RMB*20131224N9707");
		map.put("recode", "1");
		map.put("LCState", "1");
		map.put("openCreditTime", "2013-12-24 20:55:08");
		map.put("buyerStarNO", "%E5%B0%8F%E7%B1%B3");
		map.put("reMsg", "");
		map.put("tradeID", null);
		map.put("bankCode", "CIB");
//		System.out.println(md5Sign(map, "RLwUA3yHFkeWRtqsFEg="));
		
		
	}
}
