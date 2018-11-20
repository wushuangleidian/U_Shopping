package cn.uaigou.serviceimpl;

import java.util.List;

import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.junit.Test;

import cn.uaigou.dao.IDao;
import cn.uaigou.daoimpl.TagDaoImpl;
import cn.uaigou.entity.PageBean;
import cn.uaigou.entity.Tag;
import cn.uaigou.factory.BeanBuilderFactory;
import cn.uaigou.service.IService;
import cn.uaigou.utils.QueryByPageUtil;

/**
 * 处理Tag标签相关的Service实现类
 * 
 * @author Administrator
 * 
 */
public class TagServiceImpl implements IService<Tag> {

	private IDao<Tag> dao = BeanBuilderFactory
			.daoBuilder("cn.uaigou.daoimpl.TagDaoImpl");

	@Override
	public boolean add(Tag t) {
		// TODO Auto-generated method stub
		return dao.add(t);
	}

	@Override
	public boolean update(Tag t) {
		// TODO Auto-generated method stub
		return dao.update(t);
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return dao.delete(id);
	}

	@Override
	public List<Tag> queryAll() {
		// TODO Auto-generated method stub
		return dao.queryAll();
	}

	@Override
	public List<Tag> queryAllByPage(int start, int pageSize) {
		
		PageBean<Tag> pb = new PageBean<Tag>();
		
		QueryByPageUtil.getPageBean(pb, start, pageSize, dao);
		
		return dao.queryAllByPage(start, pageSize);
	}

	/**
	 * 分页查询所有标签
	 * @param start
	 * @param pageSize
	 * @return
	 */
	public PageBean<Tag> findAllByPage(int start, int pageSize){
		PageBean<Tag> pb = new PageBean<Tag>();
		QueryByPageUtil.getPageBean(pb, start, pageSize, dao);
		return  pb;
	}
	
	/**
	 * 分页查询所有标签
	 */
	public PageBean<Tag> showAll(int gid,int pc) {
		PageBean<Tag> pb = new PageBean<Tag>();
		// 封装当前页
		pb.setCurrentPage(pc);
		// 当前页
		pb.setCurrentPage(pc);
		// 每页记录数
		int pageSize=10;
		pb.setPageSize(pageSize);
		// 总记录数
		int count = queryCountByGid(gid);
		pb.setCountNum(count);
		// 总页数
		int totalPage = count % pageSize == 0 ? count / pageSize : count
				/ pageSize + 1;
		pb.setTotalPage(totalPage);
		// 每页数据
		int start = (pc - 1) * pageSize;
		List<Tag> list = ((TagDaoImpl)dao).queryByGidForPage(gid, start, pageSize);
		pb.setPageList(list);
		return pb;
	}

	@Override
	public int queryCount() {
		// TODO Auto-generated method stub
		return dao.queryCount();
	}

	/**
	 * 查询指定分类下的标签数量
	 */
	public int queryCountByGid(int towGid){
		return ((TagDaoImpl)dao).queryCountByGid(towGid);
	}
	
	@Override
	public Tag queryById(int id) {
		return dao.queryById(id);
	}

	@Override
	public Tag queryByNo(String no) {
		// TODO Auto-generated method stub
		return dao.queryByNo(no);
	}
	
	/**
	 * 根据分类的gid查询对应的标签，不分页
	 * @param gid
	 * @param start
	 * @param pageSize
	 * @return
	 */
	public List<Tag> queryByGid(int gid) {
		return ((TagDaoImpl)dao).queryByGid(gid);
	}
	
	/**
	 * 根据商品编号查询已选中的标签集合
	 * @param no
	 * @return
	 */
	public List<Tag> findByGoodsNo(String no){
		return ((TagDaoImpl)dao).findByGoodsNo(no);
	}

	/**
	 * 根据一级分类gid查询标签对象
	 * @param gid
	 * @return
	 */
	public List<Tag> queryByOneGid(int gid){
		return ((TagDaoImpl)dao).queryByOneGid(gid);
	}

	/**
	 * 根据某一个标签的ID查询相同二级分类下所有标签集合
	 * @param id
	 * @return
	 */
	public List<Tag> queryByTwoGidOtherId(int id){
		return ((TagDaoImpl)dao).queryByTwoGidOtherId(id);
	}
	
	/**
	 * 根据商品关键词搜索出来的相关标签集合
	 */
	public List<Tag> queryByGoodsKw(String kw){
		return ((TagDaoImpl)dao).queryByGoodsKw(kw);
	}
}
