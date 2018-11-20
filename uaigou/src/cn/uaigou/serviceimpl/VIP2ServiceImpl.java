package cn.uaigou.serviceimpl;

import cn.uaigou.dao.IDao;
import cn.uaigou.daoimpl.VIP2DaoImpl;
import cn.uaigou.entity.VIP;
import cn.uaigou.factory.BeanBuilderFactory;

public class VIP2ServiceImpl extends VIPServiceImpl {
	
	private IDao<VIP> dao = BeanBuilderFactory.daoBuilder("cn.uaigou.daoimpl.VIPDaoImpl");
	
	/**
	 * 根据姓名查询会员信息
	 * @param name
	 * @return
	 */
	public VIP findByName(String name){
		return ((VIP2DaoImpl)dao).findByName(name);
	}
	
	/**
	 * 根据邮箱查询会员信息
	 * @param email
	 * @return
	 */
	public VIP findByEmail(String email){
		return ((VIP2DaoImpl)dao).findByEmail(email);
	}
	
	/**
	 * 会员登录
	 * @param user 要查询的会对象
	 * @return
	 */
	public VIP login(VIP vip){
		return ((VIP2DaoImpl)dao).login(vip);
	}

}
