package cn.uaigou.serviceimpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.handlers.BeanListHandler;

import cn.uaigou.dao.IDao;
import cn.uaigou.daoimpl.GoodsDaoImpl;
import cn.uaigou.entity.Goods;
import cn.uaigou.entity.GoodsExtend;
import cn.uaigou.entity.GoodsSearchBean;
import cn.uaigou.entity.PageBean;
import cn.uaigou.entity.Tag;
import cn.uaigou.factory.BeanBuilderFactory;
import cn.uaigou.service.IService;
import cn.uaigou.utils.QueryByPageUtil;

/**
 * 处理Goods的Service的实现类
 * 
 * @author Administrator
 * 
 */
public class GoodsServiceImpl implements IService<Goods> {

	private IDao<Goods> dao = BeanBuilderFactory
			.daoBuilder("cn.uaigou.daoimpl.GoodsDaoImpl");

	@Override
	public boolean add(Goods t) {
		return dao.add(t);
	}

	@Override
	public boolean update(Goods t) {
		 return dao.update(t);
	}

	/**
	 * 修改上下架状态
	 */
	public boolean setStatus(int status,int id){
		return ((GoodsDaoImpl)dao).setStatus(status, id);
	}
	
	/**
	 * 修改推荐状态
	 */
	public boolean setTuijian(int tuijian,int id){
		return ((GoodsDaoImpl)dao).setTuijian(tuijian, id);
	}
	
	@Override
	public boolean delete(int id) {
		return dao.delete(id);
	}

	@Override
	public List<Goods> queryAll() {
		return dao.queryAll();
	}

	@Override
	public List<Goods> queryAllByPage(int start, int pageSize) {
		return dao.queryAllByPage(start, pageSize);
	}

	/**
	 * 分页查询所有商品
	 * 
	 * @return
	 */
	public PageBean<Goods> findAllByPage(int start, int pageSize) {
		PageBean<Goods> pb = new PageBean<Goods>();
		QueryByPageUtil.getPageBean(pb, start, pageSize, dao);
		return pb;
	}

	@Override
	public int queryCount() {
		return dao.queryCount();
	}

	@Override
	public Goods queryById(int id) {
		return dao.queryById(id);
	}

	@Override
	public Goods queryByNo(String no) {
		return dao.queryByNo(no);
	}

	/**
	 * 添加商品标签
	 */
	public boolean addGoodsTag(String gno, int[] tid) {
		return ((GoodsDaoImpl) dao).addGoodsTag(gno, tid);
	}

	/**
	 * 根据商品编号删除对应的标签
	 * @param goodsNo
	 * @return
	 */
	public boolean deleteByGoodsNo(String goodsNo){
		return ((GoodsDaoImpl) dao).deleteByGoodsNo(goodsNo);
	}
	
	/**
	 * 商品搜索
	 */
	public PageBean<Goods> search(GoodsSearchBean gs, int pc, int pageSize) {
		PageBean<Goods> pb = new PageBean<Goods>();
		// 当前页
		pb.setCurrentPage(pc);
		// 每页记录数
		pb.setPageSize(pageSize);
		
		//用于拼接SQL的可变字符串
		StringBuffer sb = new StringBuffer("select * from goods where 1=1 ");
		//用于存储参数的集合
		List<Object> list = new ArrayList<Object>();
		
		//商品标题
		String title = gs.getTitle();
		if(title!=null && !title.trim().equals("")){
			sb.append("and title like ? ");
			list.add("%"+title+"%");
		}
		
		//一级分类id
		int oneGid = gs.getOneGid();
		if(oneGid>0){
			sb.append("and oneGid=? ");
			list.add(oneGid);
		}
		
		//二级分类id
		int twoGid = gs.getTwoGid();
		if(twoGid>0){
			sb.append("and twoGid=? ");
			list.add(twoGid);
		}
		
		//标签id
		int tid = gs.getTid();
		//商品编号
		String goodsNo = gs.getGoodsNo();
		//tid没有值，goodsno有值
		if(tid<=0 && goodsNo!=null && !goodsNo.trim().equals("")){
			sb.append("and goodsno=? ");
			list.add(goodsNo);
		}else if(tid>0 && goodsNo!=null && !goodsNo.trim().equals("")){
			//tid有值，goodsno有值
			sb.append("and (goodsno=? or  goodsno in (select gno from gttable where tid=?)) ");
			list.add(goodsNo);
			list.add(tid);
		}else if(tid>0 && (goodsNo==null || goodsNo.equals(""))){
			//tid有值，goodsno没有值
			sb.append(" and goodsno in (select gno from gttable where tid=?) ");
			list.add(tid);
		}
		
		//上下架
		Integer status = gs.getStatus();
		if(status!=null && status!=0){//不等于默认值
			sb.append("and status=? ");
			list.add(status);
		}
		
		//推荐
		Integer tuijian = gs.getTuijian();
		if(tuijian!=null && tuijian!=0){
			sb.append("and tuijian=?");
			list.add(tuijian);
		}
		
		/**
		 * 在此就可以执行查询所有商品总数的SQL
		 */
		String sql = sb.toString().replace("*", "count(*)");
		Object[] params = list.toArray();
		int count = ((GoodsDaoImpl)dao).findCount(sql, params);
		
		
		//价格排序
		String priceSort = gs.getPriceSort();
		if(priceSort!=null && !priceSort.trim().equals("")){
			sb.append(" order by price "+priceSort+",");
		}
		
		//销量排序
		String salSort = gs.getSalSort();
		if(salSort!=null && !salSort.trim().equals("")){
			sb.append("salnum "+salSort+",");
		}
		
		//库存排序
		String stockSort = gs.getStockSort();
		if(stockSort!=null && !stockSort.trim().equals("")){
			sb.append("stock "+stockSort+" ");
		}
		
		sb.append(" limit ?,?");
		int start = (pc - 1) * pageSize;
		list.add(start);
		list.add(pageSize);
		
		
		/*
		 *在此执行查询商品 
		 */
		String sql2 = sb.toString();
		Object[] params2 = list.toArray();
		
		// 总页数
		int totalPage = count % pageSize == 0 ? count / pageSize : count/ pageSize + 1;
		pb.setTotalPage(totalPage);
				
		// 每页数据
		List<Goods> goodsList = ((GoodsDaoImpl)dao).search(sql2, params2);
		pb.setPageList(goodsList);
		
		return pb;
	}
	
	/**
	 * 用于首页展示商品的查询
	 * @param params
	 * @return
	 */
	public List<GoodsExtend> queryOfIndex(Object[] params){
		return ((GoodsDaoImpl)dao).queryOfIndex(params);
	}
	
	/**
	 * 按照一级分类查询热销商品
	 * @param oneGid 一级分类gid
	 * @param len 要查询的商品数量
	 * @return
	 */
	public List<Goods> queryByOneGid(int oneGid,int start,int len){
		return ((GoodsDaoImpl)dao).queryByOneGid(oneGid,start,len,null);
	}
	
	/**
	 * 按照一级分类Gid分页查询
	 * @param oneGid
	 * @param start
	 * @param pageSize
	 * @return
	 */
	public PageBean<Goods> findByOnegidOfSearch(int oneGid,int pc,int pageSize,String sx){
		PageBean<Goods> pb = new PageBean<Goods>();
		pb.setCurrentPage(pc);//当前页
		pb.setPageSize(pageSize);//每页记录数
		int count = ((GoodsDaoImpl)dao).queryCountByOneGid(oneGid);
		pb.setCountNum(count);//总记录数
		int totalPage = count%pageSize==0 ? count/pageSize : count/pageSize+1;
		pb.setTotalPage(totalPage);//总页数
		int start = (pc-1)*pageSize;//起始位置
		List<Goods> pageList = ((GoodsDaoImpl)dao).queryByOneGid(oneGid, start, pageSize,sx);
		pb.setPageList(pageList);//每页数据
		return pb;
	}
	
	/**
	 * 按照二级分类Gid分页查询
	 * @param oneGid
	 * @param start
	 * @param pageSize
	 * @return
	 */
	public PageBean<Goods> findByTwogidOfSearch(int twoGid,int pc,int pageSize,String sx){
		PageBean<Goods> pb = new PageBean<Goods>();
		pb.setCurrentPage(pc);//当前页
		pb.setPageSize(pageSize);//每页记录数
		int count = ((GoodsDaoImpl)dao).queryCountByTwoGid(twoGid);
		pb.setCountNum(count);//总记录数
		int totalPage = count%pageSize==0 ? count/pageSize : count/pageSize+1;
		pb.setTotalPage(totalPage);//总页数
		int start = (pc-1)*pageSize;//起始位置
		List<Goods> pageList = ((GoodsDaoImpl)dao).queryByTwoGid(twoGid, start, pageSize,sx);
		pb.setPageList(pageList);//每页数据
		return pb;
	}
	
	/**
	 * 根据标签ID查询商品集合
	 * @param tid
	 * @return
	 */
	public PageBean<Goods> queryByTid(int tid,int pc,int pageSize,String sx){
		PageBean<Goods> pb = new PageBean<Goods>();
		pb.setCurrentPage(pc);//当前页
		pb.setPageSize(pageSize);//每页记录数
		int count = ((GoodsDaoImpl)dao).queryCountByTid(tid);
		pb.setCountNum(count);//总记录数
		int totalPage = count%pageSize==0 ? count/pageSize : count/pageSize+1;
		pb.setTotalPage(totalPage);//总页数
		int start = (pc-1)*pageSize;//起始位置
		List<Goods> pageList = ((GoodsDaoImpl)dao).queryByTid(tid, start, pageSize,sx);
		pb.setPageList(pageList);//每页数据
		return pb;
	}
	
	/**
	 * 根据关键词查询商品集合
	 * @param tid
	 * @return
	 */
	public PageBean<Goods> queryByKw(String kw,int pc,int pageSize,String sx){
		PageBean<Goods> pb = new PageBean<Goods>();
		pb.setCurrentPage(pc);//当前页
		pb.setPageSize(pageSize);//每页记录数
		int count = ((GoodsDaoImpl)dao).queryContByKw(kw);
		pb.setCountNum(count);//总记录数
		int totalPage = count%pageSize==0 ? count/pageSize : count/pageSize+1;
		pb.setTotalPage(totalPage);//总页数
		int start = (pc-1)*pageSize;//起始位置
		List<Goods> pageList = ((GoodsDaoImpl)dao).queryByKw(kw, start, pageSize,sx);
		pb.setPageList(pageList);//每页数据
		return pb;
	}
	
}
