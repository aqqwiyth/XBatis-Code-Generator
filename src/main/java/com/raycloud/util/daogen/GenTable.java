package com.raycloud.util.daogen;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.raycloud.util.daogen.util.StringUtil;

public class GenTable {
	
	//注意：静态类变量在类加载是最先执行，其次类字段实在对象初始化是执行，最后对象初始化在执行构造函数。

	/** 日志记录 */
	private final Logger logger = Logger.getLogger(GenTable.class);
	
	private DbConn conn;
	private Settings settings;
	private List<String> allTableName;

	private String alltables;
    /**
     * <表名称,主键有序集合>
     *
     */
	private Map<String,Set<String>> allTablePK;
	
	public GenTable(DbConn dbConn){
		conn = dbConn;
		settings = dbConn.getSettings();
		allTableName = getTableName();
		alltables = allTableName.toString().replaceAll("\\[", "'").replaceAll("\\]", "'").replaceAll(", ", "','");
//		allTablePK = getAllTablePK();
        allTablePK = getAllTablePKBYIndex();
	}
	

	/**
	 * 根据表名生成表对象
	 *
     * @param tableName
     * @param conf
     * @return
	 */
	public TableBean prepareTableBean(String tableName, TableConfig conf) {
        if (conf == null) conf = TableConfig.DEFAULT;
		//验证及转换表名
		if(StringUtils.isBlank(tableName)) return null;
		tableName = tableName.toLowerCase();
		logger.info("开始生成表对象，表名：" + tableName);
		if(!allTableName.contains(tableName)){
			logger.error("表["+tableName+"]不存在,不生成此表DAO层代码！");
			return null;
		}
		TableBean tableBean = new TableBean(tableName, conf);
		//设置各个列对象	
		this.setTableColumn(tableBean);
		//转换Bean对象
		this.convertTableBean(tableBean);
		//校验Bean对象
		if(!checkTableBean(tableBean)){
			return null;
		}
		logger.info("生成表对象成功，表名：" + tableName);
		return tableBean;
	}

    /**
     * 用于检查表是否符合创建条件，其他自行实现
     * @param tableBean
     * @return
     */
	private boolean checkTableBean(TableBean tableBean){
		// 校验主键是否为空
		if (tableBean.getPkcol().size() == 0) {
			logger.error("表["+tableBean.getTableName()+"]自增主键不存在,不生成此表DAO层代码！");
			return false;
		}
		return true;
	}

	/**
	 * 转化表对象各个字段列类型
	 * @param tableBean
	 * @return
	 */
	private TableBean convertTableBean(TableBean tableBean) {
		List<ColBean> ll = tableBean.getColList();
		for (Iterator<ColBean> it = ll.iterator(); it.hasNext();) {
			ColBean cb = it.next();
			cb.setPropertyName(ColBean.getPropName(cb.getColName()));
			cb.setMethodName(ColBean.getMethodName(cb.getPropertyName()));
			cb.setPropertyType(SqlType2Feild.mapJavaType(cb.getColSQLType()));
		}
		return tableBean;
	}

	/**
	 * 获取数据库中所有表名
	 * @return
	 */
	public List<String> getTableName() {
		if(allTableName!=null&&!allTableName.isEmpty()) return allTableName;
		DatabaseMetaData dbmd = null;
		ResultSet rs = null;
		List<String> tableList = new ArrayList<String>();
		try {
			dbmd = conn.getDatabaseMetaData();	

			/*获取所有指定表的元信息，参数(catalog,schemaPattern,tableNamePattern,types)
			 * 返回5列数据,如下所示(schema,catalog,table_name,table_type,?)*/
			String[] types = { "TABLE" };	//类型，可以是"TABLE", "VIEW", "SYSTEM TABLE", "GLOBAL TEMPORARY", "LOCAL TEMPORARY", "ALIAS", "SYNONYM"
			rs = dbmd.getTables(null, null, "%", types);
			logger.info("============================获取所有表结构信息：");
			while (rs.next()) {
				tableList.add(rs.getString(3).trim());
				logger.info("Schema名【" + StringUtil.genLengthStr(rs.getString(1),10)
						+"】表名【"+ StringUtil.genLengthStr(rs.getString(3),25)
						+"】表类型【"+ StringUtil.genLengthStr(rs.getString(4),10)
						+"】表注释【"+ StringUtil.genLengthStr(rs.getString(5),30)+"】");	//Mysql无法通过此方式获取表注释
			}
		} catch (SQLException e) {
			logger.error("获取所有指定表的元信息出错", e);
		}
		
		if(!Gen.tconfig.keySet().isEmpty()){	//如果指定了表，则返回指定表
			List<String> tabs = new ArrayList<String>();
			for(String t:Gen.tconfig.keySet()){
				if(tableList.contains(t)){
					tabs.add(t);
				}else{
					logger.error("指定的表["+t+"]不存在，不生成此表！");
				}
			}
			tableList = tabs;	//根据指定参数返回生成表
		}
		return tableList;
	}

	/**
	 * 获取所有表的主键
	 * @return
	 */
	private Map<String, List<String>> getAllTablePK(){
		HashMap<String, List<String>> map = new HashMap<String, List<String>>();
		DatabaseMetaData dbmd = null;
		ResultSet rs = null;
		try {
			dbmd = conn.getDatabaseMetaData();	
			/*获取表的主键信息，参数(catalog,schemaPattern,tableName)一定要指定表名称，否则返回将出错。
			 *返回6列数据,如下所示
			 *DatabaseMetaData.getPrimaryKeys(catalog, schema, table) 获取的结果不是根据键顺序而是根据列名的首字母排序的
			 */
			logger.info("============================获取所有表主键信息：");
            for(String t:allTableName){
				rs = dbmd.getPrimaryKeys(null, null, t);
                Map<Integer,String> keyIndexMap = new HashMap<Integer,String>();
                while (rs.next()) {
                    keyIndexMap.put(rs.getInt(5),rs.getString(4));
					logger.info("Schema名【" + StringUtil.genLengthStr(rs.getString(1),10)
							+"】表名【"+ StringUtil.genLengthStr(rs.getString(3),25)
							+"】主键列名【"+ StringUtil.genLengthStr(rs.getString(4),10)
							+"】主键列序号【"+ StringUtil.genLengthStr(rs.getString(5),2)
							+"】主键名称【"+ StringUtil.genLengthStr(rs.getString(6),15)+"】");
				}
                /**似乎没有API获取主键的顺序，这里默认主键的顺序为列的顺序**/
                map.put(t.toLowerCase(), keySequence(keyIndexMap));
			}
		} catch (SQLException e) {
			logger.error("获取所有指定表的主键信息出错", e);
		}
		return map;
	}

    /**
     * 从索引获取主键
     * @return
     */
    private Map<String, Set<String>> getAllTablePKBYIndex(){
        HashMap<String, Set<String>> map = new HashMap<String, Set<String>>();
        DatabaseMetaData dbmd;
        ResultSet rs;
        try {
            dbmd = conn.getDatabaseMetaData();
            logger.info("============================从索引获取主键:");
            for(String t:allTableName){
                rs = dbmd.getIndexInfo(null, null, t, true,false);
                 Set<String> set =new LinkedHashSet<String>();
                while (rs.next()) {
                    set.add(rs.getString("COLUMN_NAME"));
                }
                map.put(t.toLowerCase(), set);
            }
        } catch (SQLException e) {
            logger.error("获取所有指定表的主键信息出错", e);
        }
        return map;
    }

    /**
     * 主键列排序
     * @param keyIndexMap <seq_index,key_name>
     * @return
     */
    private List<String> keySequence(Map<Integer, String> keyIndexMap) {
        List<Integer> index = new ArrayList<Integer>(keyIndexMap.keySet());
        Collections.sort(index, new Comparator() {
            @Override
            public int compare(Object arg0, Object arg1) {
                int muti0 = (Integer) arg0;
                int muti1 = (Integer) arg1;
                if (muti0 < muti1) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });
        return new ArrayList<String>(keyIndexMap.values());
    }

    /**
	 * 获取所有指定表的字段
	 * @return
	 */
	private TableBean setTableColumn(TableBean tableBean){
		DatabaseMetaData dbmd = null;
		ResultSet rs = null;
		try {
			dbmd = conn.getDatabaseMetaData();	
			/*获取所有指定表字段的元信息，参数(catalog,schemaPattern,tableNamePattern,types)
			 * 返回23列数据,如下所示*/
			rs = dbmd.getColumns(null, null, tableBean.getTableName(), "%");
			while (rs.next()) {
				logger.info("表名【" + StringUtil.genLengthStr(rs.getString(3),25)
						+"】列名【"+ StringUtil.genLengthStr(rs.getString(4),15)
						+"】列sqltype【"+ StringUtil.genLengthStr(rs.getInt(5),3)
						+"】列typename【"+ StringUtil.genLengthStr(rs.getString(6),10)
						+"】列precision【"+ StringUtil.genLengthStr(rs.getInt(7),5)
						+"】列scale【"+ StringUtil.genLengthStr(rs.getInt(9),5)
						+"】列isNullable【"+ StringUtil.genLengthStr(rs.getInt(11),2)
						+"】列isNullable2【"+ StringUtil.genLengthStr(rs.getString(18),3)
						+"】列comment【"+ StringUtil.genLengthStr(rs.getString(12),20)
						+"】列defaultValue【"+ StringUtil.genLengthStr(rs.getString(13),5)
						+"】列isAutocrement【"+ StringUtil.genLengthStr(rs.getString(23),5)+"】");
				ColBean cb = new ColBean();
				String colName = rs.getString(4);
                /**去掉列名小写cb.setColName(colName.toLowerCase());**/
                cb.setColName(colName);
				cb.setColType(rs.getString(6));	//列类型
				cb.setColComment(StringUtils.isBlank(rs.getString(12))?cb.getColName():rs.getString(12));	//列注释
				cb.setNullable(rs.getInt(11)==0?false:true);	//是否可为空
				cb.setDefaultValue(rs.getString(13));
				cb.setPrecision(rs.getInt(7));	//字段长度	
				cb.setScale(rs.getInt(9));
				cb.setAutoIncrement("YES".equals(rs.getString(23))?true:false);//是否自增字段
				// 设置列SQL类型
				int sqltype = rs.getInt(5);
				cb.setColSQLType(sqltype);
				if(!as.containsKey(sqltype)){
					as.put(sqltype, "typeName:" + rs.getString(6) + " -> className:【无】");
				}
				Set<String> pkfieldNameSet = allTablePK.get(tableBean.getTableName());
                boolean isPrimaryKey = pkfieldNameSet.contains(colName);
                cb.setPK(isPrimaryKey);
                tableBean.addColBean(cb);
			}
            /**根据主键列名获取列对象**/
            Set<String> pkfieldNameSet = allTablePK.get(tableBean.getTableName());
            for (String pkName : pkfieldNameSet) {
                for (ColBean colBean : tableBean.getColList()) {
                    if(colBean.getColName().equals(pkName)){
                        tableBean.addPkcol(colBean);
                    }
                }
            }
            /**主键提前**/
            tableBean.getColList().removeAll(tableBean.getPkcol());
            tableBean.getColList().addAll(0,tableBean.getPkcol());
		} catch (SQLException e) {
			logger.error("获取所有指定表的字段出错", e);
		}
		return tableBean;
	}
	
	//本次执行所有字段映射类型
	public Map<Integer,String> as = new HashMap<Integer,String>();


}
