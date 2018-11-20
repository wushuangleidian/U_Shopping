package cn.uaigou.daoimpl;

import java.util.List;

import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.junit.Test;

import cn.uaigou.dao.IDao;
import cn.uaigou.db.TxQueryRunner;
import cn.uaigou.entity.MyOrder;

public class MyOrderDaoImpl implements IDao<MyOrder> {
	
	private TxQueryRunner qr = new TxQueryRunner();

	/**
	 * 创建待付款订单
	 */
	@Override
	public boolean add(MyOrder o) {
		String sql = "insert into myorder(orderno,vipno,adrid,totalprice,status,createtime) values(?,?,?,?,1,now())";
		Object[] params = {o.getOrderNo(),o.getVipno(),o.getAdrid(),o.getTotalPrice()};
		int rel = qr.update(sql, params);
		if(rel>0) return true;
		return false;
	}
	
	/**
	 * 添加购物车与订单关联表
	 * @param sid
	 * @param orderno
	 * @return
	 */
	public boolean addSoTable(int[] sid,String orderno){
		String sql = "insert into sotable(sid,orderno,createtime) values(?,?,now())";
		Object[][] params = new Object[sid.length][2];
		for (int i = 0; i < sid.length; i++) {
			params[i] = new Object[]{sid[i],orderno};
		}
		int[] rel = qr.batch(sql, params);
		int i=0;
		for (int k : rel) {
			i+=k;
		}
		if(i==sid.length) return true;
		return false;
	}
	
	/**
	 * 添加当个商品与订单的关联数据
	 * @param goodsno
	 * @param orderno
	 * @return
	 */
	public boolean addGoTable(String goodsno,String orderno){
		String sql = "insert into gotable(goodsno,orderno,createtime) values(?,?,now())";
		int rel = qr.update(sql, new Object[]{goodsno,orderno});
		if(rel>0) return true;
		return false;
	}

	@Override
	public boolean update(MyOrder o) {
		String sql = "update myorder set totalprice=?,status=?,updatetime=now() where id=?";
		Object[] params = {o.getTotalPrice(),o.getStatus(),o.getId()};
		int rel = qr.update(sql, params);
		if(rel>0) return true;
		return false;
	}

	@Override
	public boolean delete(int id) {
		String sql = "update myorder set status=5,updatetime=now() where id=?";
		int rel = qr.update(sql, id);
		if(rel>0) return true;
		return false;
	}

	@Override
	public List<MyOrder> queryAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MyOrder> queryAllByPage(int start, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int queryCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public MyOrder queryById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MyOrder queryByNo(String no) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * 支付宝支付校验
	 * @param orderNo 订单编号
	 * @param tm 支付金额
	 * @return
	 */
	public int checkAlipay(String orderNo,double tm){
		String sql = "select count(*) from myorder where orderno=? and totalprice=?";
		Object obj = qr.query(sql, new Object[]{orderNo,tm}, new ScalarHandler());
		return Integer.valueOf(String.valueOf(obj));
	}
	
	/**
	 * 修改订单状态
	 * @param paymoney 实际付款金额
	 * @param paytype 付款方式
	 * @param orderno 订单编号
	 * @return
	 */
	public boolean setPayStatus(double paymoney,String paytype,String orderno){
		String sql = "update myorder set paymoney=?,paytype=?,status=2,updatetime=now() where orderno=?";
		int rel = qr.update(sql, new Object[]{paymoney,paytype,orderno});
		if(rel>0) return true;
		return false;
	}
	
	/**
	 * 获取指定订单的当前状态
	 * @param orderNo
	 * @return
	 */
	public int getStatus(String orderNo){
		String sql = "select status from myorder where orderno=?";
		Object obj = qr.query(sql, orderNo, new ScalarHandler());
		return Integer.valueOf(String.valueOf(obj));
	}
	
}
