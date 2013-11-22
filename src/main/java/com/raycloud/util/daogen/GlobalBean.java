package com.raycloud.util.daogen;

import java.util.List;

/**
 * 系统生成时Put的全局信息
 */
public class GlobalBean {

	/** 当前时间，生成类时间用 */
	private String nowDate;
	
	/** 当前用户，生成类用 */
	private String userName;
	
	/** 工程包路径，生成类用 */
	private String packageName;
	
	/** 工程所有表List */
	private List<String> tableNames;
	
	private String objSuffix="";
	public String getNowDate() {
		return nowDate;
	}

	public void setNowDate(String nowDate) {
		this.nowDate = nowDate;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public List<String> getTableNames() {
		return tableNames;
	}

	public void setTableNames(List<String> tableNames) {
		this.tableNames = tableNames;
	}

	public String getObjSuffix() {
		return objSuffix;
	}

	public void setObjSuffix(String objSuffix) {
		this.objSuffix = objSuffix;
	}
	
}
