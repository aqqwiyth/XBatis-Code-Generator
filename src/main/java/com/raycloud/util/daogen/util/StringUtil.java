package com.raycloud.util.daogen.util;

public class StringUtil {
	
	public static String toUpperCase(String str){
		return str==null?"":str.toUpperCase();
	}
	
	/**
	 * 根据指定长度生成字符串，过长截取，不够用空格补足。
	 * @param instr 一个英文字母、汉字、标点，都算是一个长度。汉字转成getBytes占4个长度
	 * @param len
	 * @return 指定长度的字符串
	 */
	public static String genLengthStr(String instr,int len){
		if(instr==null) return getBlankLen(len);
		if(instr.length()>len){
			return instr.substring(0,len);
		}else{
			return instr + getBlankLen(len-instr.getBytes().length);
		}
	}
	
	public static String genLengthStr(Integer inte,int len){
		return genLengthStr(getObjString(inte),len);
	}
	
	public static String genLengthStr(Boolean bol,int len){
		return genLengthStr(getObjString(bol),len);
	}
	
	private static String getObjString(Object obj){
		return obj==null?null:obj.toString();
	}
	
	private static String getBlankLen(int len){
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<len;i++){
			sb.append(" ");
		}
		return sb.toString();
	}

}
