package com.faceye.feature.util.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.lang3.StringUtils;

public class SHA {

	public static final String KEY_SHA = "SHA";
	
	public static String encrypt(String source) throws NoSuchAlgorithmException{
		
		if(StringUtils.isEmpty(source)){
			return null;
		}
		
		
		MessageDigest sha = MessageDigest.getInstance(KEY_SHA);
		sha.update(source.getBytes());
		
		return String.valueOf(sha.digest());
	}
	
	
	public static void main(String[] args) throws NoSuchAlgorithmException {
		
		String test = "嘘,这是秘密！";
		String test2 = "make a word for encrypt test.123456";
		
		
		System.out.println("中文加密后:" + SHA.encrypt(test));		
		System.out.println("英文加密后:" + SHA.encrypt(test2));
		
				
	}
	
}
