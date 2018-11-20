package cn.uaigou.daoimpl;

import java.util.List;

import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.uaigou.dao.IDao;
import cn.uaigou.db.TxQueryRunner;
import cn.uaigou.entity.User;

public class UserDaoImpl implements IDao<User> {

	private TxQueryRunner qr = new TxQueryRunner();
	
	@Override
	public boolean add(User user) {
		
		String sql = "insert into user(name,pwd) values(?,?)";
		Object[] params = {user.getName(),user.getPwd()};
		int i = qr.update(sql, params);
		if(i>0) return true;
		
		return false;
	}

	@Override
	public boolean update(User user) {
		
		String sql = "update user set name=?,pwd=?,balance=balance+? where id=?";
		Object[] params = {user.getName(),user.getPwd(),user.getBalance(),user.getId()};
		int i = qr.update(sql, params);
		if(i>0) return true;
		
		return false;
	}

	@Override
	public boolean delete(int id) {
		
		String sql = "delete from user where id=?";
		int i = qr.update(sql, id);
		if(i>0) return true;
		
		return false;
	}

	@Override
	public List<User> queryAll() {
		
		String sql = "select * from user";
		List<User> list = qr.query(sql, new BeanListHandler<User>(User.class));
		return list;
	}

	@Override
	public List<User> queryAllByPage(int start, int pageSize) {
		
		String sql = "select * from user order by id asc limit ?,?";
		return qr.query(sql, new BeanListHandler<User>(User.class), new Object[]{start,pageSize});
		
	}

	@Override
	public int queryCount() {
		
		String sql = "select count(*) from user";
		return qr.query(sql, new ScalarHandler());
	}

	@Override
	public User queryById(int id) {
		
		String sql = "select * from user where id=?";
		List<User> list = qr.query(sql, new BeanListHandler<User>(User.class), id);
		return list.get(0);
	}

	@Override
	public User queryByNo(String no) {
		return null;
	}

}
