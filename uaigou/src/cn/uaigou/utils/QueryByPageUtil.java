package cn.uaigou.utils;

import java.util.List;

import cn.uaigou.dao.IDao;
import cn.uaigou.entity.PageBean;
import cn.uaigou.entity.Tag;

/**
 * 封装分页对象的工具类
 * @author Administrator
 *
 */
public class QueryByPageUtil {

	/**
	 * 封装方法
	 * @param pb 要封装的PageBean对象
	 * @param pc 当前页
	 * @param pageSize 每页记录数
	 * @param dao 要查询对象的Dao
	 */
	public static void getPageBean(PageBean pb,int pc,int pageSize,IDao dao){
		//当前页
		pb.setCurrentPage(pc);
		//每页记录数
		pb.setPageSize(pageSize);
		//总记录数
		int count = dao.queryCount();
		pb.setCountNum(count);
		//总页数
		int totalPage = count%pageSize==0 ? count/pageSize : count/pageSize+1;
		pb.setTotalPage(totalPage);
		//每页数据
		int start = (pc-1)*pageSize;
		List<Tag> list = dao.queryAllByPage(start, pageSize);
		pb.setPageList(list);
	}
	
}
