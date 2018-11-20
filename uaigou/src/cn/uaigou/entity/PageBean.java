package cn.uaigou.entity;

import java.util.List;

/**
 * 分页实体类
 * @author Administrator
 * @param <T>
 */
public class PageBean<T> {
	
	private int currentPage;//当前页数
	private int totalPage;//总页数
	private int pageSize;//每页记录数
	private int countNum;//总记录数
	private List<T> pageList;//封装的每页数据
	
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getCountNum() {
		return countNum;
	}
	public void setCountNum(int countNum) {
		this.countNum = countNum;
	}
	public List<T> getPageList() {
		return pageList;
	}
	public void setPageList(List<T> pageList) {
		this.pageList = pageList;
	}
	public PageBean(int currentPage, int totalPage, int pageSize, int countNum,
			List<T> pageList) {
		super();
		this.currentPage = currentPage;
		this.totalPage = totalPage;
		this.pageSize = pageSize;
		this.countNum = countNum;
		this.pageList = pageList;
	}
	public PageBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	

}
