package com.raycloud.util.daogen.util;

import java.io.InputStream;
import java.util.Enumeration;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;

public class PropertiesUtil {
	/** 日志记录 */
	private static Logger logger = Logger.getLogger(PropertiesUtil.class);

	/**
	 * 通过java.util.ResourceBundle获取属性,
	 * 从ClassPath中获取路径，不需要写 “.properties”后缀
	 * @param filename
	 * @return java.util.Properties
	 */
	public static Properties getPropertiesByResourceBundle(String filename) {
		if (filename == null) {
			logger.error("文件名为空");
			return null;
		}

		ResourceBundle rb = null;
		try {
			rb = ResourceBundle.getBundle(filename);
		} catch (MissingResourceException e) {
			logger.error("解析属性文件出错[" + filename + ".properties]", e);
			return null;
		}

		Properties prop = new Properties();
		Enumeration<String> keys = rb.getKeys();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			String value = rb.getString(key);
			prop.put(key, value);
		}
		return prop;
	}
	
	/**
	 * java.util.Properties从java.io.InputStream(ClassLoader.getSystemResourceAsStream)中加载属性
	 * 从ClassPath中获取路径
	 * @param filename
	 * @return java.util.Properties
	 */
	public static Properties getPropertiesByFilename(String filename){
		Properties prop = new Properties();
		try {
			InputStream is = ClassLoader.getSystemResourceAsStream(filename);
			if(is!=null){
				prop.load(is);
				is.close();
			}else{
				logger.error("加载的属性文件Classpath路径不对，为:" + filename);
			}
		} catch (Exception e) {
			logger.error("加载的属性时发生异常!",e);
		}
		return prop;
	}
	
	public static String getProperty(String filename,String key){
		return getPropertiesByFilename(filename).getProperty(key);
	}
	
	
	
	public static void main(String[] args){
		//测试根据ResourceBundle获取Properties
		long s = System.currentTimeMillis();
		Properties p = PropertiesUtil.getPropertiesByResourceBundle("dao/db");
		System.out.println(p);
		System.out.println("atime:"+(System.currentTimeMillis()-s));
		//测试根据Properties.load获取Properties
		s = System.currentTimeMillis();
		Properties p2 = PropertiesUtil.getPropertiesByFilename("dao/db.properties");
		System.out.println(p2);
		System.out.println("btime:"+(System.currentTimeMillis()-s));
		//测试获取单个属性
		System.out.println(PropertiesUtil.getProperty("dao/db.properties","DB_TYPE"));
	}
}
