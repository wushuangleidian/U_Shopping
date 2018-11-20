package cn.uaigou.daoimpl;

import java.util.Date;
import java.util.List;

import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import sun.security.provider.MD5;

import cn.uaigou.dao.IDao;
import cn.uaigou.db.TxQueryRunner;
import cn.uaigou.entity.VIP;

public class VIPDaoImpl implements IDao<VIP> {

	private TxQueryRunner qr = new TxQueryRunner();
	
	/**
	 * 添加会员的方法
	 */
	@Override
	public boolean add(VIP vip) {
		String sql = "insert into vip(vipno,vipname,email,createtime,password) values(?,?,?,?,md5(?))";
		Object[] params = {vip.getVipNo(),vip.getVipName(),vip.getEmail(),new Date(),vip.getPassword()};
		int rel = qr.update(sql, params);
		if(rel>0) return true;
		return false;
	}

	/**
	 * 修改会员
	 */
	@Override
	public boolean update(VIP vip) {
		String sql = "update vip set password=md5(?),tximg=?,email=?,status=?,yzstr=?,balance=?,updatetime=? where id=? or vipno=?";
		Object[] params = {vip.getPassword(),vip.getTximg(),vip.getEmail(),vip.getStatus(),vip.getYzStr(),
				vip.getBalance(),vip.getUpdateTime(),vip.getId(),vip.getVipNo()};
		int rel = qr.update(sql, params);
		if(rel>0) return true;
		return false;
	}

	/**
	 * 删除会员
	 */
	@Override
	public boolean delete(int id) {
		String sql = "delete from vip where id=?";
		int i = qr.update(sql, id);
		if(i>0) return true;
		return false;
	}

	/**
	 * 查询所有会员，不分页
	 */
	@Override
	public List<VIP> queryAll() {
		String sql = "select * from vip";
		return qr.query(sql, new BeanListHandler<VIP>(VIP.class));
	}

	/**
	 * 查询所有会员，分页
	 */
	@Override
	public List<VIP> queryAllByPage(int start, int pageSize) {
		String sql = "select * from vip order by createtime desc limit ?,?";
		return qr.query(sql, new Object[]{start,pageSize}, new BeanListHandler<VIP>(VIP.class));
	}

	/**
	 * 查询总记录数
	 */
	@Override
	public int queryCount() {
		String sql = "select count(*) from vip";
		return qr.query(sql, new ScalarHandler());
	}

	/**
	 * 根据ID查询会员对象
	 */
	@Override
	public VIP queryById(int id) {
		String sql = "select * from vip where id=?";
		return qr.query(sql, id, new BeanHandler<VIP>(VIP.class));
	}

	/**
	 * 根据会员编号查询会员对象
	 */
	@Override
	public VIP queryByNo(String no) {
		String sql = "select * from vip where vipno=?";
		return qr.query(sql, no, new BeanHandler<VIP>(VIP.class));
	}

}
