package cn.uaigou.db;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class JDBCUtil {
	//使用文件的默认配置，必须要有c3p0-config.xml配置文件
	private static ComboPooledDataSource dataSource = new ComboPooledDataSource();
	//创建独立的连接，并且是支持多线程并发访问
	private static ThreadLocal<Connection> tl = new ThreadLocal<Connection>();
	
	/**
	 * 使用连接池返回一个连接对象
	 */
	public static Connection getConnection() throws SQLException{
		Connection conn = tl.get();//获取当前线程中的连接对象
		if(conn!=null) return conn;//判断连接是否存在
		return dataSource.getConnection();
	}
	
	/**
	 * 返回一个连接对象
	 */
	public static DataSource getDataSource(){
		return dataSource;
	}
	
	/**
	 * 手动开启事务
	 * @throws SQLException 
	 */
	public static void beginTransaction() throws SQLException{
		Connection conn = tl.get();//获取当前线程中的连接对象
		
		if(conn!=null) throw new SQLException("事务已经开启，不能重复开启事务");
		
		conn = getConnection();//获得连接
		conn.setAutoCommit(false);//设置手动开启事务
		
		tl.set(conn);//把连接保存到当前线程中
	}
	
	/**
	 * 提交事务
	 * @throws SQLException 
	 */
	public static void commitTransaction() throws SQLException{
		Connection conn = tl.get();//获取当前线程中的连接对象
		if(conn == null) throw new SQLException("事务未开启，请先开启事务");
		
		//提交事务
		conn.commit();
		conn.close();//归还连接
		
		tl.remove();//清除当前连接对象
		
	}
	
	/**
	 * 事务回滚
	 * @throws SQLException 
	 */
	public static void rollbackTransaction() throws SQLException{
		Connection conn = tl.get();//获取当前线程中的连接对象
		if(conn == null) throw new SQLException("事务未开启，请先开启事务");
		
		//事务回滚
		conn.rollback();
		conn.close();//归还连接
		
		tl.remove();
		
	}
	
	/**
	 * 释放连接
	 * @throws SQLException 
	 */
	public static void releaseConnection(Connection connection) throws SQLException{
		Connection conn = tl.get();//获取当前线程中的连接对象
		/*
		 * 判断传入的连接是否为事务专用连接，如果不是就关闭
		 */
		//如果当前没有事务连接，关闭接收到的参数连接
		if(conn == null) connection.close();
		//如果当前的连接不等于参数连接，关闭参数连接
		if(conn != connection) connection.close();
	}
	
}
