package com.raycloud.util.daogen.util;

import java.io.BufferedWriter;
import java.io.FileWriter;

import org.apache.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;

public class VelocityTemplate {
	/** 日志记录 */
	private final static Logger logger = Logger.getLogger(VelocityTemplate.class);

	/**通过类静态方法初始化模版配置*/
	static {
		try {
			String velocityConf = ClassLoader.getSystemResource("velocity.properties").getPath();
			Velocity.init(velocityConf);
			logger.info("初始化Velocity成功，配置路径为："+velocityConf);
		} catch (Exception e) {
			logger.error("初始化Velocity时发生异常！", e);
		}
	}

	/**
	 * 根据模版和参数生成指定的文件
	 * @param template
	 * @param outfileName
	 * @param context
	 * @throws Exception
	 */
	private static void mergeTemplate(Template template, String outfileName,
			VelocityContext context) throws Exception {
		/*
		 * Now have the template engine process your template using the data
		 * placed into the context. Think of it as a 'merge' of the template and
		 * the data to produce the output stream.
		 */
		BufferedWriter writer = new BufferedWriter(new FileWriter(outfileName));

		if (template != null)
			template.merge(context, writer);

		/*
		 * flush and cleanup
		 */
		writer.flush();
		writer.close();
	}

	/**
	 * 获取并解析模版
	 * @param templateFile
	 * @return template
	 * @throws Exception
	 */
	private static Template buildTemplate(String templateFile) {
		Template template = null;
		try {
			template = Velocity.getTemplate(templateFile);
		} catch (ResourceNotFoundException rnfe) {
			logger.error("buildTemplate error : cannot find template " + templateFile);
		} catch (ParseErrorException pee) {
			logger.error("buildTemplate error in template " + templateFile + ":" + pee);
		} catch (Exception e) {
			logger.error("buildTemplate error in template " + templateFile + ":" + e);
		}
		return template;
	}

	/**
	 * 根据模版名称和参数生成指定的文件
	 * @param templateName
	 * @param outfileName
	 * @param context
	 * @throws Exception
	 */
	public static void mergeTemplate(String templateName, String outfileName,
			VelocityContext context) {
		Template t = buildTemplate(templateName);
		try {
			mergeTemplate(t, outfileName, context);
		} catch (Exception e) {
			logger.error("mergeTemplate[" + outfileName + "] error in template " + templateName + ":" + e);
		}
	}

}
