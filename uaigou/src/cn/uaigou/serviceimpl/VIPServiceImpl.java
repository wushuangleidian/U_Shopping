package cn.uaigou.serviceimpl;

import java.util.List;

import cn.uaigou.dao.IDao;
import cn.uaigou.entity.VIP;
import cn.uaigou.factory.BeanBuilderFactory;
import cn.uaigou.service.IService;

public class VIPServiceImpl implements IService<VIP>{
	
	private IDao<VIP> dao = BeanBuilderFactory.daoBuilder("cn.uaigou.daoimpl.VIPDaoImpl");

	@Override
	public boolean add(VIP vip) {
		return dao.add(vip);
	}

	@Override
	public boolean update(VIP vip) {
		return dao.update(vip);
	}

	@Override
	public boolean delete(int id) {
		return dao.delete(id);
	}

	@Override
	public List<VIP> queryAll() {
		return dao.queryAll();
	}

	@Override
	public List<VIP> queryAllByPage(int start, int pageSize) {
		
		//分页的业务逻辑
		
		return dao.queryAllByPage(start, pageSize);
	}

	@Override
	public int queryCount() {
		return dao.queryCount();
	}

	@Override
	public VIP queryById(int id) {
		return dao.queryById(id);
	}

	@Override
	public VIP queryByNo(String no) {
		return dao.queryByNo(no);
	}

}
