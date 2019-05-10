package com.spring.framework.arche.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DBHelper {

	private static final String URL = "jdbc:mysql://localhost:3306/emall?useUnicode=true&characterEncoding=utf8";
	private static final String DB_USERNAME = "lucas";
	private static final String DB_PASSWORD = "lucas123";
	
	// 类加载时，加载驱动
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException("加载数据库驱动失败");
		}
	}
	
	//持有connection，避免每次创建connection的性能消耗
	private Connection conn = null;
	
	/**
	 * 新增操作
	 * @param sql
	 * @param params
	 */
	public void add(String sql, Object... params) {
		modify(sql, params);
	}
	/**
	 * 删除操作
	 * @param sql
	 * @param params
	 */
	public void remove(String sql, Object... params) {
		modify(sql, params);
	}

	/**
	 * 修改操作
	 * @param sql
	 * @param params
	 */
	public void modify(String sql, Object... params) {
		PreparedStatement pstmt = null;
		try {

			pstmt = prepareAndSetSql(sql, params);

			pstmt.executeUpdate();

		} catch (SQLException e) {
			throw new RuntimeException("SQL异常", e);
		} finally {
			closePreparedStatement(pstmt);
		}
	}
	
	public <T> T queryOne(String sql, Extractor<T> extractor, Object... params) {
		 List<T> t = query(sql,extractor,params);
		 if( t.size()!=0 && t.size()>1) {
			 throw new RuntimeException("查询结果数据量不符合预期");
		 }
		return t.size()==0?null:t.get(0);
	}

	/**
	 * 统计总条数
	 */
	public int count(String sql, Object... params) {
		PreparedStatement pstmt = null;
		try {

			pstmt = prepareAndSetSql(sql, params);

			ResultSet rs = pstmt.executeQuery();
			
			return rs.next()?rs.getInt(1):0;
		} catch (SQLException e) {
			throw new RuntimeException("SQL异常", e);
		} finally {
			closePreparedStatement(pstmt);
		}
	}
	
	/**
	 * 查询操作
	 * @param sql
	 * @param extractor
	 * @param params
	 * @return
	 */
	public <T> List<T> query(String sql, Extractor<T> extractor, Object... params) {
		List<T> resultList = new ArrayList<T>();
		PreparedStatement pstmt = null;
		try {

			pstmt = prepareAndSetSql(sql, params);

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				T t = extractor.extract(rs);
				resultList.add(t);
			}
			
		} catch (SQLException e) {
			throw new RuntimeException("SQL异常", e);
		} finally {
			closePreparedStatement(pstmt);
		}
		return resultList;
	}
	
	private PreparedStatement prepareAndSetSql(String sql, Object... params) throws SQLException {
		PreparedStatement pstmt;
		pstmt = getConnection().prepareStatement(sql);
		// 循环设置参数
		for (int i = 0; i < params.length; i++) {
			pstmt.setObject(i + 1, params[i]);
		}
		System.out.println("SQL： "+pstmt);
		return pstmt;
	}
	
	private void closePreparedStatement(PreparedStatement pstmt) {
		try {
			if (pstmt != null) {
				pstmt.close();
			}
		} catch (SQLException e) {
			throw new RuntimeException("关闭PreparedStatement时异常", e);
		}
	}
	
	/*
	 * 获取并且持有链接，减少因创建链接造成的性能消耗；如需关闭或者重新获取链接，可以调用cleanup方法
	 */
	private Connection getConnection() {
		if (conn == null) {
			try {
				conn = DriverManager.getConnection(URL, DB_USERNAME, DB_PASSWORD);
			} catch (SQLException e) {
				throw new RuntimeException("获得Connection时异常", e);
			}
		}
		return conn;
	}

	/** 
	 * 清理操作，关闭链接
	 */
	public void cleanup() {
		System.out.println("关闭链接，清理资源");
		try {
			if(conn!=null) {
				conn.close();
			}
		}catch (SQLException e) {
			throw new RuntimeException("关闭Connection异常", e);
		}
		conn=null;
	}
	
	/**
	 * 数据解压器
	 * 
	 * @author Administrator
	 *
	 * @param <T>
	 */
	public static interface Extractor<T> {
		/**
		 * 将ResultSet中的数据转换成List
		 * 
		 * @param rs
		 * @return
		 * @throws SQLException
		 */
		T extract(ResultSet rs) throws SQLException;
	}

}
