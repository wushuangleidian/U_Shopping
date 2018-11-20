package cn.uaigou.service;

import java.util.List;

/**
 * 处理业务逻辑的Service接口
 * @author Administrator
 *
 */
public interface IService<T> {
	
	/**
	 * 添加方法
	 * @param obj 要添加的对象
	 * @return 添加成功返回true，添加失败返回false
	 */
	public boolean add(T t);
	
	/**
	 * 修改方法
	 * @param obj 要修改的对象
	 * @return 修改成功返回true，修改失败返回false
	 */
	public boolean update(T t);
	
	/**
	 * 删除方法
	 * @param id 要删除对象的id
	 * @return 删除成功返回true，删除失败返回false
	 */
	public boolean delete(int id);
	
	/**
	 * 查询所有对象，不分页
	 * @return 返回所有对象的集合
	 */
	public List<T> queryAll();
	
	/**
	 * 分页查询所有对象
	 * @param start 其实位置
	 * @param pageSize 每页查询的记录数
	 * @return 返回分页查询对象的集合
	 */
	public List<T> queryAllByPage(int start,int pageSize);
	
	/**
	 * 查询总记录数
	 * @return 返回总记录数的值
	 */
	public int queryCount();
	
	/**
	 * 根据ID查询对象
	 * @param id 要查询对象的id
	 * @return 返回查询到的对象
	 */
	public T queryById(int id);
	
	/**
	 * 根据编号查询对象
	 * @param no 要查询对象的编号
	 * @return 返回查询到的对象
	 */
	public T queryByNo(String no);
	
}
