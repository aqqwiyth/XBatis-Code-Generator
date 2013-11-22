package com.raycloud.util.daogen;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import com.raycloud.util.daogen.util.StringUtil;

public class DbConn {
	/** 日志记录 */
	private final static Logger logger = Logger.getLogger(DbConn.class);

	/** Mysql JDBC 驱动程序 */
	private final static String DB_DRIVER_MYSQL = "com.mysql.jdbc.Driver";

	/** Connection对象 */
	private Connection conn = null;

	/** Statement对象 */
	private Statement stmt = null;

	/** 数据库连接是否OK **/
	private boolean isInit;

	private Settings settings;

	public Settings getSettings() {
		return settings;
	}

	public void setSettings(Settings settings) {
		this.settings = settings;
	}

	public boolean isInit() {
		return isInit;
	}

	public void setInit(boolean isInit) {
		this.isInit = isInit;
	}

	/**
	 * 构造函数
	 */
	public DbConn(Settings settings) {
		this.settings = settings;
		init();
	}

	/**
	 * 获取DB链接实例
	 *
	 * @return
	 */
	private void init() {
		logger.info("初始化数据库[" + settings.getDbName() + "]连接！");
		StringBuffer strConn = new StringBuffer();
		try {
			if (settings.getDbType() == Settings.DB_TYPE_MYSQL) {
				settings.setDriver(DB_DRIVER_MYSQL);
				Class.forName(DB_DRIVER_MYSQL);
				strConn.append("jdbc:mysql://")
						.append(settings.getUrl())
						.append(':')
						.append(settings.getPort())
						.append('/')
						.append(settings.getDbName());
			}
			System.out.println(strConn.toString());
			conn = DriverManager.getConnection(strConn.toString(),
					settings.getDbUser(), settings.getDbPwd());
		} catch (ClassNotFoundException e) {
			logger.error("JDBC驱动程序出错[" + DB_DRIVER_MYSQL + "]", e);
			isInit = false;
			return;
		} catch (SQLException e) {
			logger.error(
					"链接数据库出错[" + strConn.toString() + ", "
							+ settings.getDbUser() + ", " + settings.getDbPwd()
							+ "]", e);
			isInit = false;
			return;
		}
		logger.info("初始化数据库[" + settings.getDbName() + "]连接【OK】！");
		printDatabaseMetaData();
		isInit = true;
	}

	/**
	 * 执行SQL
	 *
	 * @param sql
	 * @return
	 */
	public int execute(String sql) {
		int iRet = 0;
		try {
			stmt = null;
			stmt = conn.createStatement();
		} catch (SQLException e) {
			logger.error("创建Statement出错[" + e.getErrorCode() + "]", e);
			return e.getErrorCode();
		}
		try {
			stmt.execute(sql);
		} catch (SQLException e) {
			logger.error("执行SQL失败[sql = " + sql + "]", e);
			iRet = e.getErrorCode();
		} finally {
			closeStmt();
		}
		return iRet;
	}

	/**
	 * 执行更新SQL
	 *
	 * @param sql
	 * @return
	 */
	public int executeUpdate(String sql) {
		int iRet = 0;
		try {
			stmt = null;
			stmt = conn.createStatement();
		} catch (SQLException e) {
			logger.error("创建Statement出错[" + e.getErrorCode() + "]", e);
			return (e.getErrorCode());
		}
		try {
			iRet = stmt.executeUpdate(sql);
		} catch (SQLException e) {
			logger.error("执行SQL失败[sql = " + sql + "]", e);
			iRet = e.getErrorCode();
		} finally {
			closeStmt();
		}
		return iRet;
	}

	/**
	 * 执行查询SQL
	 *
	 * @param sql
	 * @return
	 */
	public ResultSet executeQuery(String sql) {
		ResultSet rs = null;
		try {
			stmt = null;
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
		} catch (SQLException e) {
			logger.error("创建Statement出错[" + e.getErrorCode() + "]", e);
			return null;
		}
		try {
			rs = stmt.executeQuery(sql);
		} catch (SQLException e) {
			logger.error("执行SQL失败[sql = " + sql + "]", e);
			closeStmt();
			rs = null;
		}
		return rs;
	}

	/**
	 * 获取表结构元数据
	 *
	 * @param sql
	 * @return
	 * @throws java.sql.SQLException
	 */
	public ResultSetMetaData getTableMetaData(String sql) throws SQLException {
		stmt = null;
		stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		ResultSet rs = stmt.executeQuery(sql);
		ResultSetMetaData rmd = rs.getMetaData();
		return rmd;
	}

	/**
	 * 获取数据库元数据
	 *
	 * @return
	 */
	public DatabaseMetaData getDatabaseMetaData() {
		if (this.databaseMetaData != null)
			return this.databaseMetaData;
		DatabaseMetaData dbmd = null;
		try {
			dbmd = conn.getMetaData();
		} catch (SQLException e) {
			logger.error("获取DataBaseMetaData元数据出错", e);
		}
		return this.databaseMetaData = dbmd;
	}

	private DatabaseMetaData databaseMetaData;

	private DatabaseMetaData printDatabaseMetaData() {
		DatabaseMetaData dbmd = null;
		ResultSet rs = null;
		try {
			dbmd = conn.getMetaData();
			/*
			 * 获取当前数据库的数据类型信息。返回18列数据,如下所示
			 */
			rs = dbmd.getTypeInfo();
			logger.info("===============================数据库支持的数据类型:");
			while (rs.next()) {
				logger.info("类型名称【"
						+ StringUtil.genLengthStr(rs.getString(1), 20)
						+ "】SqlType【"
						+ StringUtil.genLengthStr(rs.getString(2), 5)
						+ "】最大精度【"
						+ StringUtil.genLengthStr(rs.getString(3), 10) + "】");
			}
			/* 获取数据库信息 */
			String dbType = dbmd.getDatabaseProductName(); // 获取当前数据库是什么数据库。如mysql等。返回的是字符串。
			String dbVersion = dbmd.getDatabaseProductVersion(); // 获得数据库的版本。返回的字符串。
			String driverName = dbmd.getDriverName(); // 获得驱动程序的名称。返回字符串。
			String driverVersion = dbmd.getDriverVersion(); // 获得驱动程序的版本。返回字符串。
			logger.info("数据库类型【" + dbType + "】数据库版本【" + dbVersion + "】数据库驱动名称【"
					+ driverName + "】数据库驱动程序版本【" + driverVersion + "】");

		} catch (SQLException e) {
			logger.error("获取DataBaseMetaData元数据出错", e);
		}
		return dbmd;
	}

	/**
	 * 关闭连接
	 */
	public void closeConn() {
		try {
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			logger.error("关闭连接出错", e);
		}
		return;
	}

	/**
	 * 关闭Statement
	 */
	public void closeStmt() {
		try {
			if (stmt != null) {
				stmt.close();
			}
		} catch (SQLException e) {
			logger.error("关闭Statement出错", e);
		}
		return;
	}
}
