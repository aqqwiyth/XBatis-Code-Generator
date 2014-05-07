package com.raycloud.util.daogen;

import com.raycloud.util.daogen.util.FileUtil;
import com.raycloud.util.daogen.util.StringUtil;
import com.raycloud.util.daogen.util.VelocityTemplate;
import java.util.*;
import java.util.Map.Entry;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;

/**
 * 代码生成入口
 */
public class Gen {
    /**
     * 日志记录
     */
    private final static Logger logger = Logger.getLogger(Gen.class);
    // Source目录，从ClassPath中获取
    private final static String SOURCE_IN_PATH = ClassLoader.getSystemResource("").getPath();

    // 生成的Maven结构的代码路径
    private final static String PATH_JAVA = "/src/main/java/";
    private final static String PATH_RESOURCES = "/src/main/resources/";

    private GenTable genTable;
    private GlobalBean globalBean;
    private Settings settings;
    public static Map<String, TableConfig> tconfig = new HashMap<String, TableConfig>();

    public Gen(DbConn dbConn) {
        genTable = new GenTable(dbConn);
        globalBean = new GlobalBean();
        settings = dbConn.getSettings();
    }

    /**
     * 对于dao的生成，暂时特殊定制，硬编码。key 为数据库表名，暂不全量配置
     */
    static {
//                tconfig.put("jdp_tb_refund", TableConfig.build("jdp_tb_refund"));
//                tconfig.put("jdp_tb_trade", TableConfig.build("jdp_tb_trade"));
//        tconfig.put("tb_trade", TableConfig.build("tb_trade"));
//        tconfig.put("tb_order", TableConfig.build("tb_order"));
//        tconfig.put("tb_trade_rate", TableConfig.build("tb_trade_rate"));
//        tconfig.put("tb_trade_refund", TableConfig.build("tb_trade_refund"));
//        tconfig.put("tb_promotion", TableConfig.build("tb_promotion"));

//        tconfig.put("assign_info", TableConfig.build("assign_info"));
//        tconfig.put("system_user", TableConfig.build("system_user"));
//        tconfig.put("tag", TableConfig.build("tag"));
//        tconfig.put("tag_detail", TableConfig.build("tag_detail"));
//        tconfig.put("user", TableConfig.build("user"));
//        tconfig.put("tb_order", TableConfig.build("tb_order"));
//        tconfig.put("promotion", TableConfig.build("promotion"));
                tconfig.put("send_message", TableConfig.build("send_message"));

//        tconfig.put("slow_request", TableConfig.build("slow_request"));
//        tconfig.put("time_info", TableConfig.build("time_info"));

//        tconfig.put("monitor_item", TableConfig.build("monitor_item"));

//        tconfig.put("account", TableConfig.build("account"));
//        tconfig.put("account_order", TableConfig.build("account_order"));
//
//        tconfig.put("fetch_config", TableConfig.build("fetch_config"));
//        tconfig.put("slave_setting", TableConfig.build("slave_setting"));
//
//        tconfig.put("search_keyword_result", TableConfig.build("search_keyword_result").setSplitTable(true));
//        tconfig.put("top_category_result", TableConfig.build("top_category_result").setSplitTable(true));

//        tconfig.put("category_keywd", TableConfig.build("category_keywd").setSplitTable(true));

//          tconfig.put("search_keyword_result", TableConfig.build("search_keyword_result"));
//          tconfig.put("top_category_result", TableConfig.build("top_category_result"));
//          tconfig.put("account_order", TableConfig.build("account_order"));
//            tconfig.put("fetch_config", TableConfig.build("fetch_config"));

//            tconfig.put("user", TableConfig.build("user"));



//          tconfig.put("system_info", TableConfig.build("system_info"));
//        tconfig.put("performance", TableConfig.build("performance"));


//        tconfig.put("tb_order", TableConfig.build("tb_order").setSplitTable(true));
//        tconfig.put("tb_refund", TableConfig.build("tb_refund").setSplitTable(true));
//        tconfig.put("tb_trade", TableConfig.build("tb_trade").setSplitTable(true));
//        tconfig.put("tao_user_dianzhang", TableConfig.build("tao_user_dianzhang"));

//
//        tconfig.put("tb_refund_json", TableConfig.build("tb_refund_json").setSplitTable(true));
//        tconfig.put("tb_trade_json", TableConfig.build("tb_trade_json").setSplitTable(true));

//        tconfig.put("ad", TableConfig.build("ad"));
//        tconfig.put("ad_position", TableConfig.build("ad_position"));
//        tconfig.put("user", TableConfig.build("user"));

//        tconfig.put("user_cid", TableConfig.build("user_cid"));

//        tconfig.put("erp_user", TableConfig.build("erp_user").setTablePrefix("erp_").addQueryMethodAndCol("getUserByVisitorId", new String[] { "visitor_id" })
//                                                             .addQueryMethodAndCol("getUserByUserId", new String[]{"user_id"})
//                                                             .addQueryMethodAndCol("getUserByNickName", new String[]{"name"})
//                                                             .setPrimaryKey("visitor_id"));
//        tconfig.put("erp_shop", TableConfig.build("erp_shop").setTablePrefix("erp_").addQueryMethodAndCol("getUserByVisitorId", new String[] { "visitor_id" })
//                                                             .addQueryMethodAndCol("getUserByUserId", new String[]{"user_id"})
//                                                             .addQueryMethodAndCol("getUserByNickName", new String[]{"nick_name"})
//                                                             .setPrimaryKey("visitor_id"));
//        tconfig.put("erp_trade",
//                TableConfig.build("erp_trade").setSplitTable(true).setTablePrefix("erp_").setName("erp_trade").addQueryMethodAndCol("getTrade", new String[]{"shop_id", "trade_id"})
//                                                                   .addQueryMethodAndCol("getTradeList", new String[]{"shop_id", "trade_create"}));
//
//
//        tconfig.put("erp_performance", TableConfig.build("erp_performance").setSplitTable(true).setTablePrefix("erp_"));
//
//
//        tconfig.put("erp_performance_index", TableConfig.build("erp_performance_index").setTablePrefix("erp_").addQueryMethodAndCol("getByShopId", new String[] { "shop_id" }));
        // tconfig.put("cuisine", TableConfig.build("cuisine")
        // .setDelByKey(true).setDelByKeys(true).setNeedPage(true).setUpdateObj(true)
        // .addQueryMethodAndCol("getCuisineByName", new String[]{"name"})
        // );
        // tconfig.put("dish", TableConfig.build("dish")
        // .setDelByKey(true).setDelByKeys(true).setNeedPage(true).setUpdateObj(true)
        // .addQueryMethodAndCol("getDishByName", new String[]{"name"})
        // );
        // tconfig.put("food", TableConfig.build("food")
        // .setDelByKey(true).setDelByKeys(true).setNeedPage(true).setUpdateObj(true)
        // );
        // tconfig.put("food_cuisine", TableConfig.build("food_cuisine")
        // .setDelByKey(true).setDelByKeys(true).setNeedPage(true).setUpdateObj(true)
        // );
        // tconfig.put("food_dish", TableConfig.build("food_dish")
        // .setDelByKey(true).setDelByKeys(true).setNeedPage(true).setUpdateObj(true)
        // );
        // tconfig.put("restaurant", TableConfig.build("restaurant")
        // .setDelByKey(true).setDelByKeys(true).setNeedPage(true).setUpdateObj(true)
        // );
        // tconfig.put("passport", TableConfig.build("passport")
        // .setDelByKey(true).setDelByKeys(true).setNeedPage(true).setUpdateObj(true)
        // .addQueryMethodAndCol("getUserPassportByEmail", new
        // String[]{"email"})
        // .addQueryMethodAndCol("getUserPassportByMobile", new
        // String[]{"mobile"})
        // .addQueryMethodAndCol("getUserPassportByLoginname", new
        // String[]{"loginname"})
        // .addQueryMethodAndCol("getUserPassportByNickname", new
        // String[]{"nickname"})
        // );
    }

    /**
     * 生成入口
     *
     * @param args
     */
    public static void main(String[] args) {
        // 生成DAO代码
        Gen.doGenDB();
    }

    private static void doGenDB() {
        logger.info("开始执行DAO代码生成===========================");
        // 初始化系统环境
        Settings settings = new Settings();
        if (!settings.initSystemParam()) {
            logger.error("系统初始化失败！");
            return;
        }
        // 初始化数据库连接
        DbConn dbConn = new DbConn(settings);
        if (!dbConn.isInit()) {
            logger.error("数据库连接创建失败！");
            return;
        }
        Gen gen = new Gen(dbConn);
        // 设置工程的全局变量
        gen.globalBean.setNowDate(DateFormatUtils.format(new Date(), "yyyy-MM-dd"));// 设置系统生成时间
        gen.globalBean.setUserName("Ou zhouyou (ouzhouyou@gmail.com)");// 设置系统当前用户
        gen.globalBean.setPackageName(settings.getJavaPackage());// 设置Java_Package路径
        // 生成指定数据库的指定表或所有表数据访问层代码
        String tabName;
        List<String> tableList = gen.genTable.getTableName();
        // 创建系统目录结构
        FileUtil.copyDirectiory(SOURCE_IN_PATH + settings.getTmplPath(), settings.getGenPath() + settings.getTmplPath());
        FileUtil.delExtFile(settings.getGenPath() + settings.getTmplPath(), ".vm"); // 删除拷贝过去的VM文件
        // 循环生成所有表数据访问代码，返回类对象并设置类对象列表
        logger.info("共有" + tableList.size() + "个表需要生成数据访问层.");
        List<TableBean> tbList = new ArrayList<TableBean>();
        TableBean tb;
        for (int i = 0; i < tableList.size(); i++) {
            tabName = tableList.get(i);
            logger.info("第" + (i + 1) + "个表[" + tabName + "]数据访问层正在生成中...");
            tb = gen.doGenTable(tabName);
            if (tb != null)
                tbList.add(tb);
        }
        logger.info("实际生成" + tbList.size() + "个表的数据访问层！");
        // 根据类对象列表，生成全局配置及基类代码文件
        gen.doGenCFG(tbList);

        dbConn.closeConn(); // 关闭数据库链接

        logger.info("所映射的字段有：");
        if (!gen.genTable.as.isEmpty()) {
            Iterator<Entry<Integer, String>> it = gen.genTable.as.entrySet().iterator();
            while (it.hasNext()) {
                Entry<Integer, String> entry = it.next();
                logger.info("sqltype:" + entry.getKey() + " -> " + entry.getValue());
            }
        }
        logger.info("结束执行DAO代码生成===========================");

    }

    /**
     * 生成指定表的数据访问层
     *
     * @param tablename
     * @return 表类名
     */
    private TableBean doGenTable(String tablename) {
        TableConfig conf = Gen.tconfig.get(tablename);
        TableBean tableBean = genTable.prepareTableBean(tablename, conf);
        if (conf.isSplitTable()) {
            // 添加分表的参数
            tableBean.setTableName(tableBean.getTableName().concat("_$splitTableName$"));
        }
        VelocityContext ctx = new VelocityContext();
        ctx.put("tbb", tableBean); // 设置表对象
        ctx.put("gb", globalBean); // 设置全局信息
        ctx.put("sysInit", settings); // 设置系统信息
        ctx.put("stringUtil", new StringUtil()); // 设置StringUtil
        try {
            // 生成Java代码
            String javaVmDir = SOURCE_IN_PATH + settings.getTmplPath() + PATH_JAVA;
            String javaDir = settings.getGenPath() + settings.getTmplPath() + PATH_JAVA;
            List<String> javaVmList = FileUtil.getFileListWithExt(javaVmDir, ".vm");
            String createFilename, packageDir = "", packageStr;
            for (String vmFilename : javaVmList) {
                if (vmFilename.startsWith("Base"))
                    continue; // 基类代码跳过
                if (vmFilename.startsWith("Result")) {
                    this.doSpecialVM(ctx, vmFilename, javaVmDir, javaDir);
                    continue;
                }
                createFilename = FileUtil.getFilenameWithoutExt(vmFilename);
                if (createFilename.startsWith("DO.")) {
                    createFilename = createFilename.replace("DO", "");
                }
                packageStr = FileUtil.findLine(javaVmDir + "/" + vmFilename, "package");
                if (StringUtils.isNotBlank(packageStr)) {
                    packageStr = packageStr
                            .substring(packageStr.indexOf("$!{gb.packageName}"), packageStr.indexOf(";"));
                    packageDir = packageStr.replace("$!{gb.packageName}", globalBean.getPackageName())
                            .replace(".", "/");
                }
                FileUtil.mkDirs(javaDir + packageDir);
                VelocityTemplate.mergeTemplate(settings.getTmplPath() + PATH_JAVA + "/" + vmFilename, javaDir
                        + packageDir + "/" + tableBean.getClassName() + createFilename, ctx);
                logger.info(tableBean.getClassName() + createFilename);
            }
            // 生成SqlMap配置文件
            String sqlmapVm = settings.getTmplPath() + PATH_RESOURCES + "sqlmap/-sqlmap.xml.vm";
            String sqlmapDir = settings.getGenPath() + settings.getTmplPath() + PATH_RESOURCES + "sqlmap/";
            VelocityTemplate.mergeTemplate(sqlmapVm, sqlmapDir + tableBean.getPureTableName() + "-sqlmap.xml", ctx);
        } catch (Exception e) {
            logger.error("表[" + tablename + "]生成出错，异常是:", e);
        }
        return tableBean;
    }

    private void doSpecialVM(VelocityContext ctx, String vmFilename, String javaVmDir, String javaDir) {
        String packageDir = "";
        String createFilename = FileUtil.getFilenameWithoutExt(vmFilename);
        String packageStr = FileUtil.findLine(javaVmDir + "/" + vmFilename, "package");
        if (StringUtils.isNotBlank(packageStr)) {
            packageStr = packageStr.substring(packageStr.indexOf("$!{gb.packageName}"), packageStr.indexOf(";"));
            packageDir = packageStr.replace("$!{gb.packageName}", globalBean.getPackageName()).replace(".", "/");
        }
        FileUtil.mkDirs(javaDir + packageDir);
        VelocityTemplate.mergeTemplate(settings.getTmplPath() + PATH_JAVA + "/" + vmFilename, javaDir + packageDir
                + "/" + createFilename, ctx);
    }

    /**
     * 生成所有表的IbatisDAO配置及基类代码文件
     *
     * @param tbList
     */
    private void doGenCFG(List<TableBean> tbList) {
        VelocityContext ctxCfg = new VelocityContext();
        ctxCfg.put("tbbList", tbList);
        ctxCfg.put("gb", globalBean); // 设置全局信息
        ctxCfg.put("sysInit", settings); // 设置系统信息
        ctxCfg.put("stringUtil", new StringUtil()); // 设置StringUtil

        try {
            // 生成Java基类代码
            String javaVmDir = SOURCE_IN_PATH + settings.getTmplPath() + PATH_JAVA;
            String javaDir = settings.getGenPath() + settings.getTmplPath() + PATH_JAVA;
            List<String> javaVmList = FileUtil.getFileListWithExt(javaVmDir, ".vm");
            String createFilename, packageDir = "", packageStr;
            for (String vmFilename : javaVmList) {
                if (!vmFilename.startsWith("Base"))
                    continue; // 非基类代码跳过
                createFilename = FileUtil.getFilenameWithoutExt(vmFilename);
                packageStr = FileUtil.findLine(javaVmDir + "/" + vmFilename, "package");
                if (StringUtils.isNotBlank(packageStr)) {
                    packageStr = packageStr
                            .substring(packageStr.indexOf("$!{gb.packageName}"), packageStr.indexOf(";"));
                    packageDir = packageStr.replace("$!{gb.packageName}", globalBean.getPackageName())
                            .replace(".", "/");
                }
                FileUtil.mkDirs(javaDir + packageDir);
                VelocityTemplate.mergeTemplate(settings.getTmplPath() + PATH_JAVA + "/" + vmFilename, javaDir
                        + packageDir + "/" + createFilename, ctxCfg);
            }
        } catch (Exception e) {
            logger.error("生成所有表的IbatisDAO配置及基类代码文件，异常是:", e);
        }
    }
}
