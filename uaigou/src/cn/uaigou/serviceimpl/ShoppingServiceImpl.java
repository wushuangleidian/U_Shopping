package cn.uaigou.serviceimpl;

import java.util.List;

import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.uaigou.dao.IDao;
import cn.uaigou.daoimpl.ShoppingDaoImpl;
import cn.uaigou.entity.Shopping;
import cn.uaigou.entity.ShoppingExtend;
import cn.uaigou.factory.BeanBuilderFactory;
import cn.uaigou.service.IService;
/**
 * 处理购物车相关的业务逻辑
 * @author Administrator
 *
 */
public class ShoppingServiceImpl implements IService<Shopping> {

	private IDao<Shopping> dao = BeanBuilderFactory.daoBuilder("cn.uaigou.daoimpl.ShoppingDaoImpl");
	
	@Override
	public boolean add(Shopping t) {
		return dao.add(t);
	}

	@Override
	public boolean update(Shopping t) {
		return dao.update(t);
	}

	@Override
	public boolean delete(int id) {
		return dao.delete(id);
	}

	/**
	 * 修改购物车状态为1，当创建订单时调用
	 * @param sid
	 * @return
	 */
	public boolean updateStatus(int[] sid){
		return ((ShoppingDaoImpl)dao).updateStatus(sid);
	}
	
	/**
	 * 批量删除商品
	 * @param sids
	 * @return
	 */
	public int[] batchDelete(int[] sids){
		return ((ShoppingDaoImpl)dao).batchDelete(sids);
	}
	
	/**
	 * 查询指定的商品是否存在
	 */
	public Shopping queryIsCZ(Shopping s){
		return ((ShoppingDaoImpl)dao).queryIsCZ(s);
	}
	
	/**
	 * 根据VIPNo查询购物车商品数量
	 * @param vipno
	 * @return
	 */
	public int queryCountByVipno(String vipno){
		return ((ShoppingDaoImpl)dao).queryCountByVipno(vipno);
	}
	
	/**
	 * 根据会员编号查询购物车商品
	 * @param vipno
	 * @return
	 */
	public List<ShoppingExtend> queryByVipno(String vipno){
		return ((ShoppingDaoImpl)dao).queryByVipno(vipno);
	}
	
	@Override
	public List<Shopping> queryAll() {
		return dao.queryAll();
	}

	@Override
	public List<Shopping> queryAllByPage(int start, int pageSize) {
		return dao.queryAllByPage(start, pageSize);
	}

	@Override
	public int queryCount() {
		return dao.queryCount();
	}

	@Override
	public Shopping queryById(int id) {
		return dao.queryById(id);
	}

	@Override
	public Shopping queryByNo(String no) {
		return dao.queryByNo(no);
	}

	/**
	 * 购物车结算前查询
	 * @param sid
	 * @return
	 */
	public List<ShoppingExtend> queryBySid(int[] sid){
		return ((ShoppingDaoImpl)dao).queryBySid(sid);
	}
}
