package cn.uaigou.daoimpl;

import java.util.List;

import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.junit.Test;

import cn.uaigou.dao.IDao;
import cn.uaigou.db.TxQueryRunner;
import cn.uaigou.entity.Gclass;
import cn.uaigou.entity.Goods;
import cn.uaigou.entity.GoodsExtend;

/**
 * 处理商品的Dao实现类
 * @author Administrator
 *
 */
public class GoodsDaoImpl implements IDao<Goods> {

	private TxQueryRunner qr = new TxQueryRunner();
	
	@Override
	public boolean add(Goods g) {
		String sql = "insert into goods" +
				"(goodsno,oneGid,twoGid,title,subhead,price,stock,firstimg,images,details,guige,cuxiao,status,tuijian,createtime) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,now())";
		Object[] params = {g.getGoodsNo(),g.getOneGc().getGid(),g.getTwoGc().getGid(),g.getTitle(),
				g.getSubHead(),g.getPrice(),g.getStock(),g.getFirstImg(),g.getImages(),g.getDetails(),
				g.getGuige(),g.getCuxiao(),g.getStatus(),g.getTuijian()};
		int rel = qr.update(sql, params);
		if(rel>0) return true;
		return false;
	}

	@Override
	public boolean update(Goods g) {
		String sql = "update goods set " +
				"oneGid=?,twoGid=?,title=?,subhead=?,price=?,stock=?,firstimg=?,images=?,details=?," +
				"guige=?,cuxiao=?,status=?,tuijian=?,updatetime=now() where id=?";
		Object[] params = {g.getOneGc().getGid(),g.getTwoGc().getGid(),g.getTitle(),
				g.getSubHead(),g.getPrice(),g.getStock(),g.getFirstImg(),g.getImages(),g.getDetails(),
				g.getGuige(),g.getCuxiao(),g.getStatus(),g.getTuijian(),g.getId()};
		int rel = qr.update(sql, params);
		if(rel>0) return true;
		return false;
	}

	/**
	 * 修改上下架状态
	 */
	public boolean setStatus(int status,int id){
		String sql = "update goods set status=?,updatetime=now() where id=?";
		int rel = qr.update(sql, new Object[]{status,id});
		if(rel>0) return true;
		return false;
	}
	
	/**
	 * 修改推荐状态
	 */
	public boolean setTuijian(int tuijian,int id){
		String sql = "update goods set tuijian=?,updatetime=now() where id=?";
		int rel = qr.update(sql, new Object[]{tuijian,id});
		if(rel>0) return true;
		return false;
	}
	
	@Override
	public boolean delete(int id) {
		String sql = "delete from goods where id=?";
		int rel = qr.update(sql, id);
		if(rel>0) return true;
		return false;
	}

	@Override
	public List<Goods> queryAll() {
		String sql = "select * from goods order by createtime desc";
		return qr.query(sql, new BeanListHandler<Goods>(Goods.class));
	}

	/**
	 * 分页查询
	 */
	@Override
	public List<Goods> queryAllByPage(int start, int pageSize) {
		String sql = "select * from goods order by salnum desc,createtime desc limit ?,?";
		return qr.query(sql, new Object[]{start,pageSize}, new BeanListHandler<Goods>(Goods.class));
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public int queryCount() {
		String sql = "select count(*) from goods";
		Object obj = qr.query(sql, new ScalarHandler());
		return Integer.valueOf(String.valueOf(obj));
	}

	@Override
	public Goods queryById(int id) {
		String sql = "select * from goods where id=?";
		GoodsExtend ge = qr.query(sql, id, new BeanHandler<GoodsExtend>(GoodsExtend.class));
		//查询一级分类对象和二级分类对象
		Object[] params = {ge.getOneGid(),ge.getTwoGid()};
		List<Gclass> gList = qr.query("select * from gclass where gid=? or gid=? order by gid asc",params,new BeanListHandler<Gclass>(Gclass.class));
		Goods goods = ge;
		goods.setOneGc(gList.get(0));
		goods.setTwoGc(gList.get(1));
		return goods;
	}

	@Override
	public Goods queryByNo(String no) {
		String sql = "select * from goods where goodsno=?";
		GoodsExtend ge = qr.query(sql, no, new BeanHandler<GoodsExtend>(GoodsExtend.class));
		//查询一级分类对象和二级分类对象
		Object[] params = {ge.getOneGid(),ge.getTwoGid()};
		List<Gclass> gList = qr.query("select * from gclass where gid=? or gid=? order by gid asc",params,new BeanListHandler<Gclass>(Gclass.class));
		Goods goods = ge;
		goods.setOneGc(gList.get(0));
		goods.setTwoGc(gList.get(1));
		return goods;
	}
	
	/**
	 * 添加商品标签
	 */
	public boolean addGoodsTag(String gno,int[] tid){
		String sql = "insert into gtTable(gno,tid) values(?,?)";
		Object[][] params = new Object[tid.length][2];
		for (int i = 0; i < tid.length; i++) {
			params[i] = new Object[]{gno,tid[i]};
		}
		int[] rel = qr.batch(sql, params);
		int k = 0;
		for (int i = 0; i < rel.length; i++) {
			k+=rel[i];
		}
		if(k>=tid.length) return true;
		return false;
	}
	
	/**
	 * 根据商品编号删除对应的标签
	 * @param goodsNo
	 * @return
	 */
	public boolean deleteByGoodsNo(String goodsNo){
		String sql = "delete from gttable where gno=?";
		int rel = qr.update(sql, goodsNo);
		if(rel>0) return true;
		return false;
	}
	
	/**
	 * 按照搜索条件查询商品总数量
	 */
	public int findCount(String sql,Object[] params){
		Object obj = qr.query(sql, params, new ScalarHandler());
		return Integer.valueOf(String.valueOf(obj));
	}
	
	/**
	 * 按照搜索条件查询商品对象的集合
	 */
	public List<Goods> search(String sql,Object[] params){
		return qr.query(sql, params, new BeanListHandler<Goods>(Goods.class));
	}
	
	/**
	 * 用于首页展示商品的查询
	 * @param params
	 * @return
	 */
	public List<GoodsExtend> queryOfIndex(Object[] params){
		String sql = "select * from goods where oneGid in (";
		for (int i = 0; i < params.length; i++) {
			sql += (i==params.length-1) ? "?" : "?,";
		}
		sql += ") and status=1 and tuijian=1 order by salnum desc,createtime desc";
		List<GoodsExtend> ges = qr.query(sql, params, new BeanListHandler<GoodsExtend>(GoodsExtend.class));
		
		return ges;
	}
	
	/**
	 * 按照一级分类查询热销商品
	 * @param oneGid 一级分类gid
	 * @param len 要查询的商品数量
	 * @return
	 */
	public List<Goods> queryByOneGid(int oneGid,int start,int len,String sx){
		String sql;
		if(sx!=null && !"".equals(sx.trim())){
			sql = "select * from goods where oneGid=? order by price "+sx+" limit ?,?";
		}else{
			sql = "select * from goods where oneGid=? order by salnum desc limit ?,?";
		}
		return qr.query(sql, new Object[]{oneGid,start,len}, new BeanListHandler<Goods>(Goods.class));
	}
	
	/**
	 * 按照一级分类gid查询商品数量
	 * @param oneGid
	 * @return
	 */
	public int queryCountByOneGid(int oneGid){
		String sql = "select count(*) from goods where oneGid=?";
		Object obj = qr.query(sql, oneGid, new ScalarHandler());
		return Integer.valueOf(String.valueOf(obj));
	}
	
	/**
	 * 按照二级分类查询商品集合
	 * @param twoGid 二级分类gid
	 * @param len 要查询的商品数量
	 * @return
	 */
	public List<Goods> queryByTwoGid(int twoGid,int start,int len,String sx){
		String sql;
		if(sx!=null && !sx.trim().equals("")){
			sql = "select * from goods where twoGid=? order by price "+sx+" limit ?,?";
		}else{
			sql = "select * from goods where twoGid=? order by salnum desc limit ?,?";
		}
		return qr.query(sql, new Object[]{twoGid,start,len}, new BeanListHandler<Goods>(Goods.class));
	}
	
	/**
	 * 按照二级分类gid查询商品数量
	 * @param oneGid
	 * @return
	 */
	public int queryCountByTwoGid(int twoGid){
		String sql = "select count(*) from goods where twoGid=?";
		Object obj = qr.query(sql,twoGid, new ScalarHandler());
		return Integer.valueOf(String.valueOf(obj));
	}
	
	/**
	 * 根据标签ID查询商品集合
	 * @param tid
	 * @return
	 */
	public List<Goods> queryByTid(int tid,int start,int pageSize,String sx){
		String sql;
		if(sx!=null && !sx.trim().equals("")){
			sql = "select * from goods where goodsno in (select gno from gttable where tid=?) order by price "+sx+" limit ?,?";
		}else{
			sql = "select * from goods where goodsno in (select gno from gttable where tid=?) order by salnum desc limit ?,?";
		}
		return qr.query(sql, new Object[]{tid,start,pageSize}, new BeanListHandler<Goods>(Goods.class));
	}
	
	/**
	 * 根据标签ID查询商品的数量
	 * @param tid
	 * @return
	 */
	public int queryCountByTid(int tid){
		String sql = "select count(*) from goods where goodsno in (select gno from gttable where tid=?)";
		Object obj = qr.query(sql, tid, new ScalarHandler());
		return Integer.valueOf(String.valueOf(obj));
	}
	
	/**
	 * 根据关键词查询商品集合
	 * @param kw
	 * @return
	 */
	public List<Goods> queryByKw(String kw,int start,int pageSize,String sx){
		String sql = "";
		if(sx!=null && !sx.trim().equals("")){
			sql = "select * from goods where title like ? order by price "+sx+" limit ?,?";
		}else{
			sql = "select * from goods where title like ? order by salnum desc limit ?,?";
		}
		return qr.query(sql, new Object[]{"%"+kw+"%",start,pageSize}, new BeanListHandler<Goods>(Goods.class));
	}
	
	/**
	 * 根据关键词查询商品数量
	 * @param kw
	 * @return
	 */
	public int queryContByKw(String kw){
		String sql = "select count(*) from goods where title like ?";
		Object obj = qr.query(sql,"%"+kw+"%", new ScalarHandler());
		return Integer.valueOf(String.valueOf(obj));
	}
	
}
