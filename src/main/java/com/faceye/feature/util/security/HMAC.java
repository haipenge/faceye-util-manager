package com.faceye.feature.util.security;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class HMAC {

	public static final String KEY_MAC = "HmacMD5"; 
	
	public static String initHMACKey() throws NoSuchAlgorithmException{
		
		KeyGenerator keyGenerator = KeyGenerator.getInstance(KEY_MAC); 
		SecretKey secretKey = keyGenerator.generateKey();
			
		return new String(secretKey.getEncoded());
	}
	
	public static String encryptHMAC(String data,String key) throws NoSuchAlgorithmException, InvalidKeyException{
		SecretKey secretKey = new SecretKeySpec(key.getBytes(), KEY_MAC);
		Mac mac = Mac.getInstance(secretKey.getAlgorithm());
		mac.init(secretKey);
		
		return new String(mac.doFinal(data.getBytes()));
	}
	
	public static String encryptHMAC(byte[] data,String key) throws NoSuchAlgorithmException, InvalidKeyException{
		SecretKey secretKey = new SecretKeySpec(key.getBytes(), KEY_MAC);
		Mac mac = Mac.getInstance(secretKey.getAlgorithm());
		mac.init(secretKey);
		
		return new String(mac.doFinal(data));
	}
	
	
	
	public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException, InvalidKeyException {
		
		byte[] a = new byte[]{'p','a','r','a','m','=','i','m','p','o','r','t','1','2','3'};
		String data = "param=import123";
		String key = initHMACKey();
		
		String result = Base64.encode(encryptHMAC(a,key));
		
		System.out.println("BASE64加密后 : " + result);
		System.out.println("BASE64解密后 : " + Base64.decode(result));
		
	}
	
	
}
