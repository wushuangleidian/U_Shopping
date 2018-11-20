package cn.uaigou.daoimpl;

import java.util.List;

import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import cn.uaigou.db.TxQueryRunner;
import cn.uaigou.entity.VIP;

/**
 * 操作VIP的Dao实现类，
 * 扩展了根据姓名查询用户、根据邮箱查询会员
 * @author Administrator
 *
 */
public class VIP2DaoImpl extends VIPDaoImpl{

	private TxQueryRunner qr = new TxQueryRunner();
	
	/**
	 * 根据姓名查询会员信息
	 * @param name
	 * @return
	 */
	public VIP findByName(String name){
		String sql = "select * from vip where vipname=?";
		return qr.query(sql, name, new BeanHandler<VIP>(VIP.class));
	}
	
	/**
	 * 根据邮箱查询会员信息
	 * @param email
	 * @return
	 */
	public VIP findByEmail(String email){
		String sql = "select * from vip where email=?";
		return qr.query(sql, email, new BeanHandler<VIP>(VIP.class));
	}
	
	/**
	 * 会员登录
	 * @param user 要查询的会对象
	 * @return
	 */
	public VIP login(VIP vip){
		String sql = "select * from vip where (vipname=? or email=?) and password=md5(?)";
		Object[] params = {vip.getVipName(),vip.getEmail(),vip.getPassword()};
		return qr.query(sql, params, new BeanHandler<VIP>(VIP.class));
	}
	
}
