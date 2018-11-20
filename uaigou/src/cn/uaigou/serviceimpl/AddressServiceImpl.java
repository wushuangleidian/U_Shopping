package cn.uaigou.serviceimpl;

import java.util.List;

import org.apache.commons.dbutils.handlers.BeanListHandler;

import cn.uaigou.dao.IDao;
import cn.uaigou.daoimpl.AddressDaoImpl;
import cn.uaigou.entity.Address;
import cn.uaigou.factory.BeanBuilderFactory;
import cn.uaigou.service.IService;

/**
 * 处理收货地址相关的Service实现类
 * @author Administrator
 *
 */
public class AddressServiceImpl implements IService<Address> {

	private IDao<Address> dao = BeanBuilderFactory.daoBuilder("cn.uaigou.daoimpl.AddressDaoImpl");
	
	@Override
	public boolean add(Address t) {
		return dao.add(t);
	}

	@Override
	public boolean update(Address t) {
		// TODO Auto-generated method stub
		return dao.update(t);
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return dao.delete(id);
	}

	@Override
	public List<Address> queryAll() {
		// TODO Auto-generated method stub
		return dao.queryAll();
	}

	@Override
	public List<Address> queryAllByPage(int start, int pageSize) {
		// TODO Auto-generated method stub
		return dao.queryAllByPage(start, pageSize);
	}

	@Override
	public int queryCount() {
		// TODO Auto-generated method stub
		return dao.queryCount();
	}

	@Override
	public Address queryById(int id) {
		// TODO Auto-generated method stub
		return dao.queryById(id);
	}

	@Override
	public Address queryByNo(String no) {
		// TODO Auto-generated method stub
		return dao.queryByNo(no);
	}

	/**
	 * 根据会员编号查询收货地址
	 * @param vipno
	 * @return
	 */
	public List<Address> queryByVipno(String vipno){
		return ((AddressDaoImpl)dao).queryByVipno(vipno);
	}
}
