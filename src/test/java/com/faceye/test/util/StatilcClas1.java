package com.faceye.test.util;

public class StatilcClas1 {
  private static String host =null;
  
  
  public static void set(String ip){
	  host=ip;
  }
  
  public String getHost(){
	  return host;
  }
  
}
