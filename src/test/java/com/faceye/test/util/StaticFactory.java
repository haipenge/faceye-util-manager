package com.faceye.test.util;

public class StaticFactory {
  
	
	public static void main(String[] args) {
		StatilcClas1 s1=new StatilcClas1();
		s1.set("a");
		System.out.println(s1.getHost());
		StatilcClas1 s2 =new StatilcClas1();
		s2.set("b");
		System.out.println(s2.getHost());
		System.out.println(s1.getHost());
	}
	
	public static void main(){
		
	}
}
