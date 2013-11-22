package com.raycloud.util.daogen;


import com.raycloud.util.daogen.util.CharUtil;

public class ColBean {
	/**================字段结构信息======================*/
	/** 字段列名，例如：full_name */
	private String colName;
	/** 字段注释，例如：姓名全称 */
	private String colComment;
	/** 字段类型名称，例如：VARCHAR2 */
	private String colType;
	/** 字段类型java.sql.Types，例如：Types.VARCHAR*/
	private int colSQLType;
	/** 字段是否可以为空 */
	private boolean nullable;
	/** 字段可为空时的缺省*/
	private String defaultValue;
	/** 字段长度 */
	private int precision;
	/** 字段小数位（float,double)*/
	private int scale;
	/** 是否主键 */
	private boolean isPK = false;
	/** 是否自动增长 */
	private boolean isAutoIncrement = false;

	/**================根据字段结构生成字段对象信息======================*/
	/** 方法名，例如：FullName */
	private String methodName;
	/** 属�?名，例如：fullName */
	private String propertyName;
	/** 属�?类型，例如：String */
	private String propertyType;

	/** 字段类型Mysql映射，例如：VARCHAR */
	private String colTypeForMysql;
	
	public String getColTypeForMysql() {
		return colTypeForMysql;
	}

	public void setColTypeForMysql(String colTypeForMysql) {
		this.colTypeForMysql = colTypeForMysql;
	}
	
	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public int getScale() {
		return scale;
	}

	public void setScale(int scale) {
		this.scale = scale;
	}

	public int getPrecision() {
		return precision;
	}

	public void setPrecision(int precision) {
		this.precision = precision;
	}
	
	public boolean isNullable() {
		return nullable;
	}

	public void setNullable(boolean nullable) {
		this.nullable = nullable;
	}

	public String getColComment() {
		return colComment;
	}

	public void setColComment(String colComment) {
		this.colComment = colComment;
	}

	public String getColName() {
		return colName;
	}

	public void setColName(String colName) {
		this.colName = colName;
	}

	public String getColType() {
		return colType;
	}

	public void setColType(String colType) {
		this.colType = colType;
	}

	public int getColSQLType() {
		return colSQLType;
	}

	public void setColSQLType(int colSQLType) {
		this.colSQLType = colSQLType;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public String getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}

	public boolean isPK() {
		return isPK;
	}

	public void setPK(boolean isPK) {
		this.isPK = isPK;
	}

	public boolean isAutoIncrement() {
		return isAutoIncrement;
	}

	public void setAutoIncrement(boolean isAutoIncrement) {
		this.isAutoIncrement = isAutoIncrement;
	}

	/**
	 * 将字段名转成属性名,例如：full_name->fullName
	 * @param colname
	 * @return
	 */
	public static String getPropName(String colname) {
        /** 取消强制转为小写,colname.toLowerCase()**/
		char[] cs = colname.toCharArray();
		StringBuffer sb = new StringBuffer();
		boolean nextUp = false;
		for(int i=0;i<cs.length;i++){
			if(cs[i]!='_'){
				if(nextUp){
					sb.append(CharUtil.toUpperCase(cs[i]));
					nextUp = false;
				}else{
					sb.append(cs[i]);
				}
			}else{
				nextUp = true;
			}
		}
//		s = s.replaceAll("_", "");
		return sb.toString();
	}

	/**
	 * 根据属性名生成方法名,例如：fullName->FullName
	 * @param propname
	 * @return
	 */
	public static String getMethodName(String propname) {
		char[] a = propname.toCharArray();
		a[0] = CharUtil.toUpperCase(a[0]);
		return new String(a);
	}

}
