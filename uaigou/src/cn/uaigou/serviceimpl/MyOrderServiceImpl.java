package cn.uaigou.serviceimpl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.handlers.BeanHandler;

import cn.uaigou.dao.IDao;
import cn.uaigou.daoimpl.MyOrderDaoImpl;
import cn.uaigou.db.JDBCUtil;
import cn.uaigou.entity.MyOrder;
import cn.uaigou.factory.BeanBuilderFactory;
import cn.uaigou.service.IService;

public class MyOrderServiceImpl implements IService<MyOrder> {
	
	private IDao<MyOrder> dao = BeanBuilderFactory.daoBuilder("cn.uaigou.daoimpl.MyOrderDaoImpl");

	@Override
	public boolean add(MyOrder o) {
		
		return false;
	}
	
	/**
	 * 创建单个商品订单，包含事务管理
	 */
	public boolean add(MyOrder o,String goodsno){
		try {
			JDBCUtil.beginTransaction();//开启事务
			
			//创建订单
			boolean b1 = dao.add(o);
			if(!b1) throw new RuntimeException("创建待付款订单时出现异常！");
			//添加当个商品与订单的关联
			boolean b2 = ((MyOrderDaoImpl)dao).addGoTable(goodsno, o.getOrderNo());
			if(!b2) throw new RuntimeException("添加商品与订单关联时出现异常！");
			
			JDBCUtil.commitTransaction();//提交事务
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			try {
				JDBCUtil.rollbackTransaction();//事务回滚
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		return false;
	}
	
	/**
	 * 创建订单，包含事务管理
	 * @param o
	 * @param sid
	 * @return
	 */
	public boolean add(MyOrder o,int[] sid){
		try {
			
			JDBCUtil.beginTransaction();//开启事务
			
			//创建待付款订单
			boolean b1 = dao.add(o);
			if(!b1) throw new RuntimeException("创建待付款订单时出现异常！");
			//添加购物车与订单的关联数据
			boolean b2 = ((MyOrderDaoImpl)dao).addSoTable(sid, o.getOrderNo());
			if(!b2) throw new RuntimeException("添加购物车商品与订单关联时出现异常！");
			//修改购物车状态
			boolean b3 = new ShoppingServiceImpl().updateStatus(sid);
			if(!b3) throw new RuntimeException("修改购物车状态时出现异常！");
			
			JDBCUtil.commitTransaction();//提交事务
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			try {
				JDBCUtil.rollbackTransaction();//事务回滚
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		return false;
	}

	@Override
	public boolean update(MyOrder t) {
		// TODO Auto-generated method stub
		return dao.update(t);
	}

	@Override
	public boolean delete(int id) {
		// TODO Auto-generated method stub
		return dao.delete(id);
	}

	@Override
	public List<MyOrder> queryAll() {
		// TODO Auto-generated method stub
		return dao.queryAll();
	}

	@Override
	public List<MyOrder> queryAllByPage(int start, int pageSize) {
		// TODO Auto-generated method stub
		return dao.queryAllByPage(start, pageSize);
	}

	@Override
	public int queryCount() {
		// TODO Auto-generated method stub
		return dao.queryCount();
	}

	@Override
	public MyOrder queryById(int id) {
		// TODO Auto-generated method stub
		return dao.queryById(id);
	}

	@Override
	public MyOrder queryByNo(String no) {
		// TODO Auto-generated method stub
		return dao.queryByNo(no);
	}

	/**
	 * 支付宝支付校验
	 * @param orderNo 订单编号
	 * @param tm 支付金额
	 * @return
	 */
	public boolean checkAlipay(String orderNo,String tm){
		int i = ((MyOrderDaoImpl)dao).checkAlipay(orderNo, Double.valueOf(tm));
		if(i>0) return true;
		return false;
	}
	
		
	/**
	 * 修改订单支付状态
	 * @param paymoney 实际付款金额
	 * @param paytype 付款方式
	 * @param orderno 订单编号
	 * @return
	 */
	public boolean setPayStatus(double paymoney,String paytype,String orderno){
		int status = ((MyOrderDaoImpl)dao).getStatus(orderno);
		if(status==MyOrder.ORDER_NOTPAY){//校验订单是否为待付款状态
			return ((MyOrderDaoImpl)dao).setPayStatus(paymoney, paytype, orderno);
		}
		return true;
	}
	
	/**
	 * 获取指定订单的当前状态
	 * @param orderNo
	 * @return
	 */
	public int getStatus(String orderNo){
		return ((MyOrderDaoImpl)dao).getStatus(orderNo);
	}
	
}
