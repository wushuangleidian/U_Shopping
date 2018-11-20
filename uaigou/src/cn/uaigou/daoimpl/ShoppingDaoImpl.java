package cn.uaigou.daoimpl;

import java.util.List;

import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.junit.Test;

import cn.uaigou.dao.IDao;
import cn.uaigou.db.TxQueryRunner;
import cn.uaigou.entity.Shopping;
import cn.uaigou.entity.ShoppingExtend;
/**
 * 处理购物车相关的Dao实现类
 * @author Administrator
 *
 */
public class ShoppingDaoImpl implements IDao<Shopping> {
	
	private TxQueryRunner qr = new TxQueryRunner();

	@Override
	public boolean add(Shopping s) {
		String sql = "insert into shopping(vipno,goodsno,goodsnum,guige,createtime) values(?,?,?,?,now())";
		Object[] params = {s.getVipNo(),s.getGoodsNo(),s.getGoodsNum(),s.getGuige()};
		int rel = qr.update(sql, params);
		if(rel>0) return true;
		return false;
	}

	@Override
	public boolean update(Shopping s) {
		String sql = "update shopping set goodsnum=?,updatetime=now() where sid=?";
		Object[] params = {s.getGoodsNum(),s.getSid()};
		int rel = qr.update(sql, params);
		if(rel>0) return true;
		return false;
	}

	/**
	 * 修改购物车状态为1，当创建订单时调用
	 * @param sid
	 * @return
	 */
	public boolean updateStatus(int[] sid){
		String sql = "update shopping set status=1 where sid=?";
		Object[][] params = new Object[sid.length][1];
		for (int i = 0; i < sid.length; i++) {
			params[i] = new Object[]{sid[i]};
		}
		
		int[] rel = qr.batch(sql, params);
		int i=0;
		for (int k : rel) {
			i+=k;
		}
		if(i==sid.length) return true;
		return false;
	}
	
	@Override
	public boolean delete(int id) {
		String sql = "delete from shopping where sid=?";
		int rel = qr.update(sql, id);
		if(rel>0) return true;
		return false;
	}
	
	/**
	 * 批量删除商品
	 * @param sids
	 * @return
	 */
	public int[] batchDelete(int[] sids){
		String sql = "delete from shopping where sid=?";
		Object[][] params = new Object[sids.length][1];
		for (int i = 0; i < sids.length; i++) {
			params[i] = new Object[]{sids[i]};
		}
		return qr.batch(sql, params);
	}

	/**
	 * 查询指定的商品是否存在
	 */
	public Shopping queryIsCZ(Shopping s){
		String sql = "select * from shopping where goodsno=? and guige=? and vipno=?";
		Object[] params = {s.getGoodsNo(),s.getGuige(),s.getVipNo()};
		return qr.query(sql, params, new BeanHandler<Shopping>(Shopping.class));
	}
	
	@Override
	public List<Shopping> queryAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Shopping> queryAllByPage(int start, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int queryCount() {
		return 0;
	}
	
	/**
	 * 根据VIPNo查询购物车商品数量
	 * @param vipno
	 * @return
	 */
	public int queryCountByVipno(String vipno){
		String sql = "select count(*) from shopping where vipno=? and status=0";
		Object obj = qr.query(sql, vipno, new ScalarHandler());
		return Integer.valueOf(String.valueOf(obj));
	}

	@Override
	public Shopping queryById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Shopping queryByNo(String no) {
		return null;
	}
	
	/**
	 * 根据会员编号查询购物车商品
	 * @param vipno
	 * @return
	 */
	public List<ShoppingExtend> queryByVipno(String vipno){
		String sql = "select s.sid,s.status,s.goodsno,g.stock,g.firstimg,g.title,s.guige,g.price,s.goodsnum from goods g,(select * from shopping where vipno=?) s where g.goodsno=s.goodsno";
		return qr.query(sql, vipno, new BeanListHandler<ShoppingExtend>(ShoppingExtend.class));
	}
	
	/**
	 * 购物车结算前查询
	 * @param sid
	 * @return
	 */
	public List<ShoppingExtend> queryBySid(int[] sid){
		String sql = "select s.sid,s.goodsno,s.status,g.firstimg,g.title,s.guige,g.price,s.goodsnum from goods g,(select * from shopping where sid in (";
				for (int i = 0; i < sid.length; i++) {
					sql += (i==sid.length-1) ? "?" : "?,";
				}
				sql += ")) s where g.goodsno=s.goodsno";
		Object[] params = new Object[sid.length];
		for (int i = 0; i < sid.length; i++) {
			params[i]=sid[i];
		}
		return qr.query(sql, params, new BeanListHandler<ShoppingExtend>(ShoppingExtend.class));
	}
	
}
