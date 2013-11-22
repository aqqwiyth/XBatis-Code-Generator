package com.raycloud.util.daogen.util;

public class CharUtil {
	/**
	 * 将字符转换成大写，如a->A
	 * @param c
	 * @return
	 */
	public static char toUpperCase(char c) {
		if (c < 97 || c > 122)
			return c;
		else
			return (char) (c - 32);
	}

	/**
	 * 将字符转换成小写，如A->a
	 * @param c
	 * @return
	 */
	public static char toLowerCase(char c) {
		if (c < 65 || c > 90)
			return c;
		else
			return (char) (c + 32);
	}
	
	public static void main(String[] args) {
		String upperCase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		char[] u = upperCase.toCharArray();
		for(int i=0;i<u.length;i++){
			System.out.print(CharUtil.toLowerCase(u[i])+"("+(int)u[i]+")");
		}
		System.out.println();
		String lowerCase = "abcdefghijklmnopqrstuvwxyz";
		char[] l = lowerCase.toCharArray();
		for(int i=0;i<l.length;i++){
			System.out.print(CharUtil.toUpperCase(l[i])+"("+(int)l[i]+")");
		}
	}
}
