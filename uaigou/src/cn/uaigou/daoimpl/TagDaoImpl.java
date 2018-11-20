package cn.uaigou.daoimpl;

import java.util.List;

import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.junit.Test;

import cn.uaigou.dao.IDao;
import cn.uaigou.db.TxQueryRunner;
import cn.uaigou.entity.Gclass;
import cn.uaigou.entity.Tag;
import cn.uaigou.entity.TagExtend;

public class TagDaoImpl implements IDao<Tag> {

	private TxQueryRunner qr = new TxQueryRunner();
	
	@Override
	public boolean add(Tag tag) {
		String sql = "insert into tag(tagname,oneGid,twoGid,tj) values(?,?,?,?)";
		Object[] params = {tag.getTagName(),tag.getOneGc().getGid(),tag.getTwoGc().getGid(),tag.getTj()};
		int rel = qr.update(sql, params);
		if(rel>0) return true;
		return false;
	}

	@Override
	public boolean update(Tag tag) {
		String sql = "update tag set tagname=?,oneGid=?,twoGid=?,tj=? where id=?";
		Object[] params = {tag.getTagName(),tag.getOneGc().getGid(),tag.getTwoGc().getGid(),tag.getTj(),tag.getId()};
		int rel = qr.update(sql, params);
		if(rel>0) return true;
		return false;
	}

	@Override
	public boolean delete(int id) {
		String sql = "delete from tag where id=?";
		int rel = qr.update(sql, id);
		if(rel>0) return true;
		return false;
	}

	@Override
	public List<Tag> queryAll() {
		String sql = "select * from tag order by id";
		return qr.query(sql, new BeanListHandler<Tag>(Tag.class));
	}

	@Override
	public List<Tag> queryAllByPage(int start, int pageSize) {
		String sql = "select * from tag order by id limit ?,?";
		return qr.query(sql, new Object[]{start,pageSize},new BeanListHandler<Tag>(Tag.class));
	}
	
	/**
	 * 根据分类的gid查询对应的标签，分页
	 * @param gid
	 * @param start
	 * @param pageSize
	 * @return
	 */
	public List<Tag> queryByGidForPage(int gid,int start, int pageSize) {
		String sql = "select * from tag where twoGid=? order by id limit ?,?";
		return qr.query(sql, new Object[]{gid,start,pageSize},new BeanListHandler<Tag>(Tag.class));
	}
	
	/**
	 * 根据分类的gid查询对应的标签，不分页
	 * @param gid
	 * @param start
	 * @param pageSize
	 * @return
	 */
	public List<Tag> queryByGid(int gid) {
		String sql = "select * from tag where twoGid=?";
		return qr.query(sql, gid,new BeanListHandler<Tag>(Tag.class));
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public int queryCount() {
		String sql = "select count(*) from tag";
		Object obj = qr.query(sql, new ScalarHandler());
		int i = Integer.valueOf(String.valueOf(obj));
		return i;
	}
	
	/**
	 * 查询指定分类下的标签数量
	 */
	public int queryCountByGid(int towGid){
		String sql = "select count(*) from tag where twoGid=?";
		Object obj = qr.query(sql, towGid, new ScalarHandler());
		int i = Integer.valueOf(String.valueOf(obj));
		return i;
	}

	@Override
	public Tag queryById(int id) {
		String sql = "select * from tag where id=?";
		TagExtend tagExtend = qr.query(sql,id , new BeanHandler<TagExtend>(TagExtend.class));
		
		String sql2 = "select * from gclass where gid=?";
		Gclass oneGc = qr.query(sql2, tagExtend.getOneGid(), new BeanHandler<Gclass>(Gclass.class));
		
		String sql3 = "select * from gclass where gid=?";
		Gclass twoGc = qr.query(sql3, tagExtend.getTwoGid(), new BeanHandler<Gclass>(Gclass.class));
		
		Tag tag = tagExtend;
		tag.setOneGc(oneGc);
		tag.setTwoGc(twoGc);
		
		return tag;
	}
	
	@Override
	public Tag queryByNo(String no) {
		return null;
	}
	
	/**
	 * 根据商品编号查询已选中的标签集合
	 * @param no
	 * @return
	 */
	public List<Tag> findByGoodsNo(String no){
		String sql = "select * from tag where id in (select tid from gttable where gno=?)";
		return qr.query(sql, no, new BeanListHandler<Tag>(Tag.class));
	}
	
	/**
	 * 根据一级分类gid查询标签对象
	 * @param gid
	 * @return
	 */
	public List<Tag> queryByOneGid(int gid){
		String sql = "select * from tag where oneGid=?";
		return qr.query(sql, gid, new BeanListHandler<Tag>(Tag.class));
	}
	
	/**
	 * 根据某一个标签的ID查询相同二级分类下所有标签集合
	 * @param id
	 * @return
	 */
	public List<Tag> queryByTwoGidOtherId(int id){
		String sql = "select * from tag where twoGid=(select twoGid from tag where id=?)";
		return qr.query(sql, id, new BeanListHandler<Tag>(Tag.class));
	}
	
	/**
	 * 根据商品关键词搜索出来的相关标签集合
	 */
	public List<Tag> queryByGoodsKw(String kw){
		String sql = "select * from tag where id in (select tid from gttable where gno in (select goodsno from goods where title like ?))";
		return qr.query(sql, "%"+kw+"%", new BeanListHandler<Tag>(Tag.class));
	}

}
