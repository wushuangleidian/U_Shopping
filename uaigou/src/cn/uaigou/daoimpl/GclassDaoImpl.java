package cn.uaigou.daoimpl;

import java.util.List;

import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.uaigou.dao.IDao;
import cn.uaigou.db.TxQueryRunner;
import cn.uaigou.entity.Gclass;

/**
 * 处理品类相关的Dao实现类
 * @author Administrator
 *
 */
public class GclassDaoImpl implements IDao<Gclass> {
	
	private TxQueryRunner qr = new TxQueryRunner();

	@Override
	public boolean add(Gclass gc) {
		String sql = "insert into gclass(name,pid,createtime) values(?,?,now())";
		Object[] params = {gc.getName(),gc.getPid()};
		int rel = qr.update(sql, params);
		if(rel>0) return true;
		return false;
	}

	@Override
	public boolean update(Gclass gc) {
		String sql = "update gclass set name=?,pid=?,sx=?,updatetime=now() where gid=?";
		Object[] params = {gc.getName(),gc.getPid(),gc.getSx(),gc.getGid()};
		int rel = qr.update(sql, params);
		if(rel>0) return true;
		return false;
	}

	@Override
	public boolean delete(int id) {
		String sql = "delete from gclass where gid=?";
		int rel = qr.update(sql, id);
		if(rel>0) return true;
		return false;
	}

	@Override
	public List<Gclass> queryAll() {
		String sql = "select * from gclass order by sx,createtime desc";
		return qr.query(sql, new BeanListHandler<Gclass>(Gclass.class));
	}

	@Override
	public List<Gclass> queryAllByPage(int start, int pageSize) {
		return null;
	}

	@Override
	public int queryCount() {
		String sql = "select count(*) from gclass";
		return qr.query(sql, new ScalarHandler());
	}

	@Override
	public Gclass queryById(int id) {
		String sql = "select * from gclass where gid=?";
		return qr.query(sql, id,new BeanHandler<Gclass>(Gclass.class));
	}
	
	/**
	 * 查询某一类别的子分类
	 */
	public List<Gclass> queryByPid(int pid){
		String sql = "select * from gclass where pid=?";
		return qr.query(sql, pid,new BeanListHandler<Gclass>(Gclass.class));
	}

	/**
	 * 批量修改顺序
	 */
	public int[] batch(List<Gclass> gcList){
		String sql = "update gclass set name=?,pid=?,sx=?,updatetime=now() where gid=?";
		
		Object[][] params = new Object[gcList.size()][4];
		for (int i = 0; i < gcList.size(); i++) {
			Object[] objArr = params[i];
			Gclass gc = gcList.get(i);
			objArr[0] = gc.getName();
			objArr[1] = gc.getPid();
			objArr[2] = gc.getSx();
			objArr[3] = gc.getGid();
		}
		
		return qr.batch(sql, params);
	}
	
	@Override
	public Gclass queryByNo(String no) {
		return null;
	}
	
	/**
	 * 关于首页的一级分类展示查询
	 * @param len 要展示的一级分类数量
	 * @return
	 */
	public List<Gclass> queryOfIndex(int len){
		String sql = "select * from gclass where pid=0 order by sx asc limit 0,?";
		return qr.query(sql, len, new BeanListHandler<Gclass>(Gclass.class));
	}

}
