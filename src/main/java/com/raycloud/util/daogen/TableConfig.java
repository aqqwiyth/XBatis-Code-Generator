package com.raycloud.util.daogen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author microboss
 *
 * Maizhibin修改内容
 *  1. 添加是否分表设置
 *  2. 添加自定义主键
 */
public class TableConfig {

    public static final TableConfig DEFAULT = new TableConfig();

    // 设置主键，默认为表的主键
    private String primaryKey;

    // 设置表前缀，如erp_
    private String tablePrefix;

    /**更新字段**/
    private String updField = "upd_time";
    /**新增字段**/
    private String addField = "add_time";

    private String name;

    // 是否分表，如将Trade表分为Trade_1, Trade_2, Trade_3
    private boolean splitTable = false;
    /**可以自定义字段查询**/
    private boolean customField = true;

    //get
    private boolean getByKey  = true;
    private boolean getByKeys = true;
    private boolean needPage  = true;

    //delete
    private boolean delByKey  = true;
    private boolean delByKeys = true;

    //update
    private boolean updateKey = true;
    private boolean updateKeys = false;

    /**
     * key 为方法名,如包含List,则生成list，String[]为过滤字段，关于排序和个数，limit请暂时另行添加
     */
    private Map<String, String[]> selectMap = new HashMap<String, String[]>();

    /**
     * key 为方法名结尾为，String[]为过滤字段，关于排序和个数，limit请暂时另行添加,建立一个三元map。
     */
    //private Map<String,String[]> updateMap = new HashMap<String,String[]>();

    private Set<String[]> updateCols = new HashSet<String[]>();

    //queryPage
    private List<String> orderCol = new ArrayList<String>();

    private TableConfig() {}

    public static TableConfig build(String name) {
        return new TableConfig().setName(name);
    }

    public String getName() {
        return name;
    }

    public TableConfig setName(String name) {
        this.name = name;
        return this;
    }

    public TableConfig addQueryMethodAndCol(String method,String[] wheres){
		this.selectMap.put(method, wheres);
		return this;
	}

	/**
	 * 增加排序规则，适用于分页
	 * @param colName
	 * @return
	 */
	public TableConfig addQueryOrderBy(String colName){
		this.orderCol.add(colName);
		return this;
	}

	public boolean isGetByKey() {
		return getByKey;
	}

	public TableConfig setGetByKey(boolean getByKey) {
		this.getByKey = getByKey;
		return this;
	}

	public boolean isGetByKeys() {
		return getByKeys;
	}

	public TableConfig setGetByKeys(boolean getByKeys) {
		this.getByKeys = getByKeys;
		return this;
	}

	public boolean isNeedPage() {
		return needPage;
	}

	public TableConfig setNeedPage(boolean needPage) {
		this.needPage = needPage;
		return this;
	}

	public boolean isDelByKey() {
		return delByKey;
	}

	public TableConfig setDelByKey(boolean delByKey) {
		this.delByKey = delByKey;
		return this;
	}

	public boolean isDelByKeys() {
		return delByKeys;
	}

	public TableConfig setDelByKeys(boolean delByKeys) {
		this.delByKeys = delByKeys;
		return this;
	}

    public boolean isUpdateKeys() {
        return updateKeys;
    }

    public TableConfig setUpdateKeys(boolean updateKeys) {
        this.updateKeys = updateKeys;
        return this;
    }

    public boolean isUpdateKey() {
        return updateKey;
    }

    public TableConfig setUpdateKey(boolean updateKey) {
        this.updateKey = updateKey;
        return this;
    }

    public Set<String[]> getUpdateCols() {
		return updateCols;
	}

	public void setUpdateCols(Set<String[]> updateCols) {
		this.updateCols = updateCols;
	}

	public List<String> getOrderCol() {
		return orderCol;
	}

	public Map<String, String[]> getSelectMap() {
		return selectMap;
	}

    public boolean isSplitTable() {
        return splitTable;
    }

    public TableConfig setSplitTable(boolean splitTable) {
        this.splitTable = splitTable;
        return this;
    }

    public TableConfig setPrimaryKey(String key) {
        this.primaryKey = key;
        return this;
    }

    public String getKey() {
        return primaryKey;
    }

    public String getTablePrefix() {
        return tablePrefix;
    }

    public TableConfig setTablePrefix(String tablePrefix) {
        this.tablePrefix = tablePrefix;
        return this;
    }

    public String getUpdField() {
        return updField;
    }

    public TableConfig setUpdField(String updField) {
        this.updField = updField;
        return this;
    }

    public String getAddField() {
        return addField;
    }

    public TableConfig setAddField(String addField) {
        this.addField = addField;
        return this;
    }

    public boolean isCustomField() {
        return customField;
    }

    public TableConfig setCustomField(boolean customField) {
        this.customField = customField;
        return this;
    }
}
