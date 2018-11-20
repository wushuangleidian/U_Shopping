package cn.uaigou.daoimpl;

import org.apache.commons.dbutils.handlers.BeanHandler;

import cn.uaigou.db.TxQueryRunner;
import cn.uaigou.entity.Admin;

/**
 * 对admin操作的升级：
 * 添加了登录功能
 * @author Administrator
 *
 */
public class Admin2DaoImpl extends AdminDaoImpl {
	
	private TxQueryRunner qr = new TxQueryRunner();
	
	/**
	 * 管理员登录
	 */
	public Admin login(Admin admin){
		String sql = "select * from admin where username=? and password=md5(?)";
		Object[] params = {admin.getUserName(),admin.getPassword()};
		return qr.query(sql, params, new BeanHandler<Admin>(Admin.class));
	}

}
