package cn.uaigou.db;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;

public class TxQueryRunner extends  QueryRunner{

	@Override
	public int[] batch(String sql, Object[][] params) {
		/*
		 * 1.获取连接
		 * 2.执行父类方法，传递连接对象
		 * 3.释放连接
		 * 4.返回值
		 */
		int[] result = null;
		try {
			Connection conn = JDBCUtil.getConnection();
			result = super.batch(conn, sql, params);
			JDBCUtil.releaseConnection(conn);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("在执行批处理时出现了SQL异常！");
		}
		return result;
	}

	@SuppressWarnings("deprecation")
	@Override
	public <T> T query(String sql, Object param, ResultSetHandler<T> rsh){
		
		T result = null;
		try {
			Connection conn = JDBCUtil.getConnection();
			result = super.query(conn, sql, param, rsh);
			JDBCUtil.releaseConnection(conn);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("在执行批处理时出现了SQL异常！");
		}
		return result;
	}

	@SuppressWarnings("deprecation")
	@Override
	public <T> T query(String sql, Object[] params, ResultSetHandler<T> rsh){
		T result = null;
		try {
			Connection conn = JDBCUtil.getConnection();
			result = super.query(conn, sql, params, rsh);
			JDBCUtil.releaseConnection(conn);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("在执行批处理时出现了SQL异常！");
		}
		return result;
	}

	@Override
	public <T> T query(String sql, ResultSetHandler<T> rsh, Object... params){
		T result = null;
		try {
			Connection conn = JDBCUtil.getConnection();
			result = super.query(conn, sql, rsh , params);
			JDBCUtil.releaseConnection(conn);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("在执行批处理时出现了SQL异常！");
		}
		return result;
	}

	@Override
	public <T> T query(String sql, ResultSetHandler<T> rsh) {
		T result = null;
		try {
			Connection conn = JDBCUtil.getConnection();
			result = super.query(conn, sql, rsh);
			JDBCUtil.releaseConnection(conn);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("在执行批处理时出现了SQL异常！");
		}
		return result;
	}

	@Override
	public int update(String sql, Object... params) {
		int result = -1;
		try {
			Connection conn = JDBCUtil.getConnection();
			result = super.update(conn, sql , params);
			JDBCUtil.releaseConnection(conn);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("在执行批处理时出现了SQL异常！");
		}
		return result;
	}

	@Override
	public int update(String sql, Object param) {
		int result = -1;
		try {
			Connection conn = JDBCUtil.getConnection();
			result = super.update(conn, sql, param);
			JDBCUtil.releaseConnection(conn);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("在执行批处理时出现了SQL异常！");
		}
		return result;
	}

	@Override
	public int update(String sql) {
		int result = -1;
		try {
			Connection conn = JDBCUtil.getConnection();
			result = super.update(conn, sql);
			JDBCUtil.releaseConnection(conn);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("在执行批处理时出现了SQL异常！");
		}
		return result;
	}

	
	
}
