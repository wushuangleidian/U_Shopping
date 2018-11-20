package cn.uaigou.serviceimpl;

import cn.uaigou.dao.IDao;
import cn.uaigou.daoimpl.Admin2DaoImpl;
import cn.uaigou.entity.Admin;
import cn.uaigou.factory.BeanBuilderFactory;

/**
 * 对admin的Service的升级：
 * 添加了登录功能
 * @author Administrator
 *
 */
public class Admin2ServiceImpl extends AdminServiceImpl {

	private IDao<Admin> dao = BeanBuilderFactory.daoBuilder("cn.uaigou.daoimpl.AdminDaoImpl");
	
	/**
	 * 管理员登录
	 */
	public Admin login(Admin admin){
		return ((Admin2DaoImpl) dao).login(admin);
	}
	
}
