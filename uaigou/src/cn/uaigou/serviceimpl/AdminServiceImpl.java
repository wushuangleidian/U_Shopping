package cn.uaigou.serviceimpl;

import java.util.List;

import cn.uaigou.dao.IDao;
import cn.uaigou.entity.Admin;
import cn.uaigou.factory.BeanBuilderFactory;
import cn.uaigou.service.IService;

/**
 * 处理Admin的业务逻辑的Service实现类
 * @author Administrator
 *
 */
public class AdminServiceImpl implements IService<Admin> {
	
	private IDao<Admin> dao = BeanBuilderFactory.daoBuilder("cn.uaigou.daoimpl.AdminDaoImpl");

	@Override
	public boolean add(Admin t) {
		return dao.add(t);
	}

	@Override
	public boolean update(Admin t) {
		return dao.update(t);
	}

	@Override
	public boolean delete(int id) {
		return dao.delete(id);
	}

	@Override
	public List<Admin> queryAll() {
		return dao.queryAll();
	}

	@Override
	public List<Admin> queryAllByPage(int start, int pageSize) {
		return dao.queryAllByPage(start, pageSize);
	}

	@Override
	public int queryCount() {
		return dao.queryCount();
	}

	@Override
	public Admin queryById(int id) {
		return dao.queryById(id);
	}

	@Override
	public Admin queryByNo(String no) {
		return dao.queryByNo(no);
	}

	

}
