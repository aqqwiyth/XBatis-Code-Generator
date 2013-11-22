/**
 * 
 */
package com.raycloud.util.daogen;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * @author microboss
 *
 */
public class SqlType2Feild {
	private final static Logger logger = Logger.getLogger(SqlType2Feild.class);
	
	private static Map<Integer,String> sqltype2Feild = new HashMap<Integer,String>();
	static {
		sqltype2Feild.put(Types.VARCHAR, "String");
		sqltype2Feild.put(Types.DATE, "Date");	//java.util.Date
		//mysql
		sqltype2Feild.put(Types.BIT, "Byte");
		sqltype2Feild.put(Types.TINYINT, "Byte");
		sqltype2Feild.put(Types.SMALLINT, "Integer");
		sqltype2Feild.put(Types.INTEGER, "Integer");
		sqltype2Feild.put(Types.BIGINT, "Long");
		sqltype2Feild.put(Types.REAL, "Float");
		sqltype2Feild.put(Types.DOUBLE, "Double");
		
		sqltype2Feild.put(Types.CHAR, "String");
		sqltype2Feild.put(Types.LONGVARCHAR, "String");
		
		sqltype2Feild.put(Types.BINARY, "Blob");	//java.sql.Blob
		sqltype2Feild.put(Types.VARBINARY, "Blob");	//java.sql.Blob
		sqltype2Feild.put(Types.LONGVARBINARY, "Blob");	//java.sql.Blob
		
		sqltype2Feild.put(Types.TIME, "Date");	//java.util.Date
		sqltype2Feild.put(Types.TIMESTAMP, "Date");	//java.util.Date
		//oracle
		sqltype2Feild.put(Types.NUMERIC, "Long");
		sqltype2Feild.put(Types.FLOAT, "BigDecimal");	//java.math.BigDecimal
		sqltype2Feild.put(Types.DECIMAL, "BigDecimal");	//java.math.BigDecimal
		sqltype2Feild.put(Types.CLOB, "Clob");	//java.sql.Clob
	}

	public static String mapJavaType(int sqltype) {
		String javaType = sqltype2Feild.get(sqltype);
		if(StringUtils.isNotBlank(javaType)){
			return javaType;
		}else{
			logger.error("字段没有对应的Java类型，SQL_TYPE：" + sqltype);
			return "String";
		}
	}
}
