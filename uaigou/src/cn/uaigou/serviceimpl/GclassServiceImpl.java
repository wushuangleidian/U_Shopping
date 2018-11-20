package cn.uaigou.serviceimpl;

import java.util.List;

import org.apache.commons.dbutils.handlers.BeanListHandler;

import cn.uaigou.dao.IDao;
import cn.uaigou.daoimpl.GclassDaoImpl;
import cn.uaigou.entity.Gclass;
import cn.uaigou.factory.BeanBuilderFactory;
import cn.uaigou.service.IService;

public class GclassServiceImpl implements IService<Gclass> {
	
	private IDao<Gclass> dao = BeanBuilderFactory.daoBuilder("cn.uaigou.daoimpl.GclassDaoImpl");

	@Override
	public boolean add(Gclass t) {
		return dao.add(t);
	}

	@Override
	public boolean update(Gclass t) {
		return dao.update(t);
	}

	@Override
	public boolean delete(int id) {
		return dao.delete(id);
	}

	@Override
	public List<Gclass> queryAll() {
		return dao.queryAll();
	}

	@Override
	public List<Gclass> queryAllByPage(int start, int pageSize) {
		return dao.queryAllByPage(start, pageSize);
	}

	@Override
	public int queryCount() {
		return dao.queryCount();
	}

	@Override
	public Gclass queryById(int id) {
		return dao.queryById(id);
	}

	/**
	 * 查询某一类别的子分类
	 */
	public List<Gclass> queryByPid(int pid){
		return ((GclassDaoImpl)dao).queryByPid(pid);
	}
	@Override
	public Gclass queryByNo(String no) {
		return dao.queryByNo(no);
	}
	
	/**
	 * 批量修改顺序
	 */
	public int[] batch(List<Gclass> gcList){
		return ((GclassDaoImpl)dao).batch(gcList);
	}

	/**
	 * 关于首页的一级分类展示查询
	 * @param len 要展示的一级分类数量
	 * @return
	 */
	public List<Gclass> queryOfIndex(int len){
		return ((GclassDaoImpl)dao).queryOfIndex(len);
	}
	
}
