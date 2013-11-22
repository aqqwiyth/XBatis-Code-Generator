package com.raycloud.util.daogen;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.raycloud.util.daogen.util.CharUtil;

public class TableBean {
    /** ================表结构信息====================== */
    /** 表名 */
    private String tableName;
    /** 不带表分区的表名 */
    private String pureTableName;
    private String modelName;

    public String getTableName() {
        return tableName;
    }

    public TableBean setTableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    private TableConfig conf;

    public TableBean(String tableName, TableConfig conf) {
        this.conf = conf;
		this.tableName = tableName;
        if (this.conf.getTablePrefix() != null && !this.conf.getTablePrefix().equals("")) {
            this.className = getClassName(this.tableName.replace(this.conf.getTablePrefix(), ""));
        } else {
            this.className = getClassName(this.tableName);
        }
		this.objectName = getObjectName(this.className);
	}

	/** ================根据表结构生成表对象及字段信息====================== */
	/** 类名 */
	private String className;
	/** 类对象名 */
	private String objectName;

	/** 主键 */
	private List<ColBean> pkcol = new ArrayList<ColBean>();
	/** 字段列表 */
	private List<ColBean> colList = new ArrayList<ColBean>();

	private Map<String, ColBean> colMap = new LinkedHashMap<String, ColBean>();

	public List<ColBean> getColList() {
		return colList;
	}

	public TableBean setColList(List<ColBean> colList) {
		this.colList = colList;
		for (ColBean cb : this.colList) {
			this.colMap.put(cb.getColName(), cb);
		}
		return this;
	}

	public Map<String, ColBean> getColMap() {
		return this.colMap;
	}

	public TableBean addColBean(ColBean bean) {
        if(!this.colMap.containsKey(bean.getColName())){
            this.colMap.put(bean.getColName(), bean);
            this.colList.add(bean);
        }
		return this;
	}

	public String getClassName() {
		return className;
	}

	public TableBean setClassName(String className) {
		this.className = className;
		return this;
	}

	public String getObjectName() {
		return objectName;
	}

	public TableBean setObjectName(String objectName) {
		this.objectName = objectName;
		return this;
	}

	public List<ColBean> getPkcol() {
		return pkcol;
	}

	public TableBean addPkcol(ColBean _pkcol) {
        this.pkcol.add(_pkcol);
		return this;
	}

	public TableConfig getConf() {
		return conf;
	}

	public TableBean setConf(TableConfig conf) {
		this.conf = conf;
		return this;
	}

	/**
	 * 根据表名生成对象类名,例如：account_audit -> AcountAudit
	 * 
	 * @param tablename
	 * @return
	 */
	public static String getClassName(String tablename) {
		if ("".equals(tablename) || tablename == null)
			return "";
		char[] a = tablename.toLowerCase().toCharArray();
		StringBuffer sb = new StringBuffer();
		// 首字母转大写
		sb.append(CharUtil.toUpperCase(a[0]));
		// 下划线首字母转大写
		for (int i = 1; i < a.length; i++) {
			char c = a[i];
			if (c == '_' && i < a.length - 1) {
				a[i + 1] = CharUtil.toUpperCase(a[i + 1]);
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	/**
	 * 根据类名生成对象名,例如：FullName->fullName
	 * 
	 * @param classname
	 * @return
	 */
	public static String getObjectName(String classname) {
		char[] a = classname.toCharArray();
		a[0] = CharUtil.toLowerCase(a[0]);
		return new String(a);
	}

	@Override
	public String toString() {
		return tableName + "\t" + conf + "\t" + className + "\t" + objectName
				+ "\t" + pkcol + "\t" + colList + "\t" + colMap;
	}


    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getPureTableName() {
        return tableName.replace("_$tableId$", "");
    }
}