package cn.uaigou.daoimpl;

import java.util.List;

import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.uaigou.dao.IDao;
import cn.uaigou.db.TxQueryRunner;
import cn.uaigou.entity.Admin;

/**
 * 处理管理员用户的Dao实现类
 * @author Administrator
 *
 */
public class AdminDaoImpl implements IDao<Admin>{

	private TxQueryRunner qr = new TxQueryRunner();

	@Override
	public boolean add(Admin admin) {
		String sql = "insert into amdin(insert into admin(username,password,email,power,createtime) values(?,md5(?),?,?,now()))";
		Object[] params = {admin.getUserName(),admin.getPassword(),admin.getEmail(),admin.getPower()};
		int rel = qr.update(sql, params);
		if(rel>0) return true;
		return false;
	}

	@Override
	public boolean update(Admin admin) {
		String sql = "update admin set password=?,email=?,power=?,updatetime=now() where id=? ";
		Object[] params = {admin.getPassword(),admin.getEmail(),admin.getPower(),admin.getId()};
		int rel = qr.update(sql, params);
		if(rel>0) return true;
		return false;
	}

	@Override
	public boolean delete(int id) {
		String sql = "delete from admin where id=?";
		int rel = qr.update(sql, id);
		if(rel>0) return true;
		return false;
	}

	@Override
	public List<Admin> queryAll() {
		String sql = "select * from admin";
		return qr.query(sql, new BeanListHandler<Admin>(Admin.class));
	}

	@Override
	public List<Admin> queryAllByPage(int start, int pageSize) {
		String sql = "select * from admin order by createtime desc limit ?,?";
		Object[] params = {start,pageSize};
		return qr.query(sql,params,new BeanListHandler<Admin>(Admin.class));
	}

	@Override
	public int queryCount() {
		String sql = "select count(*) from admin";
		return qr.query(sql,new ScalarHandler());
	}

	@Override
	public Admin queryById(int id) {
		String sql = "select * from admin where id=?";
		return qr.query(sql, id, new BeanHandler<Admin>(Admin.class));
	}

	@Override
	public Admin queryByNo(String no) {
		return null;
	}
	
}
