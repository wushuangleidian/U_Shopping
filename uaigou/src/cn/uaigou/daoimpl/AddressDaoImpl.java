package cn.uaigou.daoimpl;

import java.util.List;

import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import cn.uaigou.dao.IDao;
import cn.uaigou.db.TxQueryRunner;
import cn.uaigou.entity.Address;

/**
 * 处理收货地址相关的Dao实现类
 * @author Administrator
 *
 */
public class AddressDaoImpl implements IDao<Address> {

	private TxQueryRunner qr = new TxQueryRunner();
	
	@Override
	public boolean add(Address adr) {
		String sql = "insert into address(vipno,name,phone,sheng,shi,qu,adr,createtime) values(?,?,?,?,?,?,?,now())";
		Object[] params = {adr.getVipno(),adr.getName(),adr.getPhone(),adr.getSheng(),adr.getShi(),adr.getQu(),adr.getAdr()};
		int rel = qr.update(sql, params);
		if(rel>0) return true;
		return false;
	}

	@Override
	public boolean update(Address adr) {
		String sql = "update address set name=?,phone=?,sheng=?,shi=?,qu=?,adr=?,updatetime=now() where adrid=?";
		Object[] params = {adr.getName(),adr.getPhone(),adr.getSheng(),adr.getShi(),adr.getQu(),adr.getAdr(),adr.getAdrid()};
		int rel = qr.update(sql, params);
		if(rel>0) return true;
		return false;
	}

	@Override
	public boolean delete(int id) {
		String sql = "delete from address where adrid=?";
		int rel = qr.update(sql, id);
		if(rel>0) return true;
		return false;
	}

	@Override
	public List<Address> queryAll() {
		return null;
	}

	@Override
	public List<Address> queryAllByPage(int start, int pageSize) {
		return null;
	}

	@Override
	public int queryCount() {
		return 0;
	}

	@Override
	public Address queryById(int id) {
		String sql = "select * from address where adrid=?";
		return qr.query(sql, id, new BeanHandler<Address>(Address.class));
	}

	@Override
	public Address queryByNo(String vipno) {
		return null;
	}
	
	/**
	 * 根据会员编号查询收货地址
	 * @param vipno
	 * @return
	 */
	public List<Address> queryByVipno(String vipno){
		String sql = "select * from address where vipno=? order by createtime desc";
		return qr.query(sql, vipno, new BeanListHandler<Address>(Address.class));
	}

}
