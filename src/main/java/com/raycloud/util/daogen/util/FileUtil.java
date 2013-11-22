package com.raycloud.util.daogen.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

public class FileUtil {
	/** 日志记录 */
	private final static Logger logger = Logger.getLogger(FileUtil.class);
	
	/**
	 * 根据指定路径获取所有文件夹列表(不包括文件,或隐藏文件夹)
	 * @param filepath
	 * @param ext
	 * @return 返回List，如果传入的路径不对或扩展名为空，则返回空List。
	 */
	public static List<String> getFileList(String filepath){
		if(StringUtils.isEmpty(filepath)) return Collections.emptyList();
		File file = new File(filepath);
		if(!file.exists()||!file.isDirectory()) return Collections.emptyList();
		File[] files = file.listFiles();
		List<String> fileList = new ArrayList<String>();
		for (int i=0;i<files.length;i++){
			if(files[i].isDirectory()&&!files[i].isHidden()){
				fileList.add(files[i].getName());
			}
		}
		return fileList;
	}
	
	/**
	 * 根据指定路径获取指定扩展名的文件列表(不包括文件夹)
	 * @param filepath
	 * @param ext
	 * @return 返回List，如果传入的路径不对，则返回空List；如果扩展名为空则返回所有文件
	 */
	public static List<String> getFileListWithExt(String filepath,String ext){
		if(StringUtils.isEmpty(filepath)) return Collections.emptyList();
		File file = new File(filepath);
		if(!file.exists()||!file.isDirectory()) return Collections.emptyList();
		File[] files = file.listFiles();
		List<String> fileList = new ArrayList<String>();
		if(StringUtils.isEmpty(ext)) ext = "";	//如果ext为空，设置为"",则会返回所有文件
		for (int i=0;i<files.length;i++){
			if("".equals(ext)||ext.equals(getExt(files[i].getName()))){
				if(files[i].isFile()) fileList.add(files[i].getName());
			}
		}
		return fileList;
	}
	
	/**
	 * 获取去除后缀名后的文件名
	 * 例如：<br>
	 * <ul></li>
	 * <li>test.vm -> test</li>
	 * <li>test -> test</li>
	 * <li>null -> ""</li>
	 * <li>"" -> ""</li>
	 * </ul>
	 * @param filename
	 * @return 如果传入的文件名为空则返回空字符串"",如果文件名没有扩展名，则返回原文件名。
	 */
	public static String getFilenameWithoutExt(String filename){
		if(filename==null||filename.isEmpty()) return "";
		if(filename.lastIndexOf(".")>-1){
			return filename.substring(0,filename.lastIndexOf("."));
		}else{
			return filename;
		}
	}
	
	/**
	 * 获取文件扩展名<br>
	 * 例如：<br>
	 * <ul></li>
	 * <li>test.vm -> .vm</li>
	 * <li>test -> ""</li>
	 * <li>null -> ""</li>
	 * <li>"" -> ""</li>
	 * </ul>
	 * @param filename
	 * @return 如果传入的文件名为空则返回空字符串"",如果文件名没有扩展名，则返回""。
	 */
	public static String getExt(String filename){
		if(filename==null||filename.isEmpty()) return "";
		if(filename.lastIndexOf(".")>-1){
			return filename.substring(filename.lastIndexOf("."));
		}else{
			return "";
		}
	}
	
	/**
	 * 根据传入的路径创建文件夹,如果路径不存在则创建之。
	 * @param filepath
	 * @return true or false 
	 */
	public static boolean mkDirs(String filepath){
		File file = new File(filepath);
		if(file.exists()) return true;
		return file.mkdirs();
	}
	
	/**
	 * 复制文件
	 * @param sourceFile
	 * @param targetFile
	 * @return true or false
	 */
	public static boolean copyFile(File sourceFile, File targetFile){
		if(sourceFile==null||targetFile==null){
			logger.error("传入的文件拷贝的源文件或目标文件为空！");
			return false;
		}
		try {
			// 新建文件输入流并对它进行缓冲
			FileInputStream input = new FileInputStream(sourceFile);
			BufferedInputStream inBuff = new BufferedInputStream(input);
	
			// 新建文件输出流并对它进行缓冲
			FileOutputStream output = new FileOutputStream(targetFile);
			BufferedOutputStream outBuff = new BufferedOutputStream(output);
	
			// 缓冲数组
			byte[] b = new byte[1024 * 5];
			int len;
			while ((len = inBuff.read(b)) != -1) {
				outBuff.write(b, 0, len);
			}
			// 刷新此缓冲的输出流
			outBuff.flush();
	
			// 关闭流
			inBuff.close();
			input.close();
			outBuff.close();
			output.close();
		} catch (FileNotFoundException e) {
			logger.error("文件拷贝的源文件或目标文件不存在！",e);
			return false;
		} catch (IOException e) {
			logger.error("文件拷贝时发生IO错误！",e);
			return false;
		}
		return true;
	}

	/**
	 * 复制文件夹
	 * @param sourceDir
	 * @param targetDir
	 * @return true or false
	 */
	public static boolean copyDirectiory(String sourceDir, String targetDir){
		if(StringUtils.isBlank(sourceDir)||StringUtils.isBlank(targetDir)){
			logger.error("目录拷贝的源目录或目标目录不存在！");
			return false;
		}
		// 新建目标目录
		new File(targetDir).mkdirs();
		// 获取源文件夹当前下的文件或目录
		File sourceFileDir = new File(sourceDir);
		if(!sourceFileDir.exists()||!sourceFileDir.isDirectory()||sourceFileDir.isHidden()){
			logger.warn("源目录不存在,或没有要拷贝的目录文件,或是隐藏文件如(.svn)！不进行拷贝，路径是：" + sourceDir);
			return false;
		}
		File[] files = sourceFileDir.listFiles();
		if(files==null||files.length<=0) return false;
		for (int i = 0; i < files.length; i++) {
			if (files[i].isFile()) {
				// 源文件
				File sourceFile = files[i];
				// 目标文件
				File targetFile = new File(
						new File(targetDir).getAbsolutePath() + File.separator
								+ files[i].getName());
				copyFile(sourceFile, targetFile);
			}
			if (files[i].isDirectory()) {
				// 准备复制的源文件夹
				String dir1 = sourceDir + "/" + files[i].getName();
				// 准备复制的目标文件夹
				String dir2 = targetDir + "/" + files[i].getName();
				copyDirectiory(dir1, dir2);
			}
		}
		return true;
	}
	
	/**
	 * 删除目录下所有指定后缀的文件名
	 * @param filepath 文件目录路径
	 * @param ext
	 * @throws java.io.IOException
	 */
	public static boolean delExtFile(String filepath,String ext){
		if(StringUtils.isBlank(filepath)||StringUtils.isBlank(ext)){
			logger.error("删除文件目录为空，或未指定要删除的文件扩展名！");
			return false;
		}
		File f = new File(filepath);//定义文件路径       
		if(!f.exists()){
			logger.error("删除文件目录不存在！");
			return false;
		}
		if(f.isDirectory()){//判断是文件还是目录
		    if(f.listFiles().length==0){//若目录下没有文件则返回成功  
		        return true;  
		    }
		    //若有则把文件放进数组，并判断是否有下级目录  
	        File delFile[]=f.listFiles();  
	        int i =f.listFiles().length;  
	        for(int j=0;j<i;j++){  
	            if(delFile[j].isDirectory()){  //递归调用del方法并取得子目录路径  
	            	delExtFile(delFile[j].getAbsolutePath(),ext);
	            }else{	//判断扩展名进行删除  
	            	if(delFile[j].getName().endsWith(ext)){
	            		delFile[j].delete();//删除文件  
	            	}
	            }
	        }  
		}
		return true;
	}  

	/**
	 * 读取文件匹配的行
	 * @param filename
	 * @param startWith 行开始
	 * @return
	 */
	public static String findLine(String filename, String startWith) {
		if (StringUtils.isBlank(filename)) {
			logger.error("文件名为空！");
			return "";
		}
		File file = new File(filename);
		if (!file.exists() || !file.isFile()) {
			logger.error("文件不存在或不是文件！");
			return "";
		}
		try {
			FileReader reader = new FileReader(file);
			BufferedReader br = new BufferedReader(reader);
			String s1 = null;
			while ((s1 = br.readLine()) != null) {
				if (StringUtils.isNotBlank(s1) && s1.startsWith(startWith)) {
					return s1;
				}
			}
			br.close();
			reader.close();
		} catch (FileNotFoundException e) {
			logger.error("文件读取找不到出错！异常是：" + e);
		} catch (IOException e) {
			logger.error("文件读取IO出错！异常是：" + e);
		}
		return "";
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String packageStr = FileUtil.findLine(ClassLoader.getSystemResource("dao/taobaodao/src/main/java/DAO.java.vm").getPath(), "package");
		System.out.println(packageStr);
		packageStr = packageStr.substring(packageStr.indexOf("$!{gb.packageName}"),packageStr.indexOf(";"));
		System.out.println(packageStr.replace("$!{gb.packageName}", "com.taobao.trip").replace(".", "/"));
	}

}
