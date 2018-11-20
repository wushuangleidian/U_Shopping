package cn.uaigou.entity;

/**
 * 封装商品搜索的实体类
 * @author Administrator
 *
 */
public class GoodsSearchBean {

	private String title;//商品标题
	private String goodsNo;//商品编号
	private int oneGid;//一级分类gid
	private int twoGid;//二级分类gid
	private int tid;//标签id
	private String priceSort;//价格排序
	private String stockSort;//库存排序
	private String salSort;//销量排序
	private Integer status;//商品上下架状态
	private Integer tuijian;//推荐
	
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getTuijian() {
		return tuijian;
	}
	public void setTuijian(Integer tuijian) {
		this.tuijian = tuijian;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getGoodsNo() {
		return goodsNo;
	}
	public void setGoodsNo(String goodsNo) {
		this.goodsNo = goodsNo;
	}
	public int getOneGid() {
		return oneGid;
	}
	public void setOneGid(int oneGid) {
		this.oneGid = oneGid;
	}
	public int getTwoGid() {
		return twoGid;
	}
	public void setTwoGid(int twoGid) {
		this.twoGid = twoGid;
	}
	public int getTid() {
		return tid;
	}
	public void setTid(int tid) {
		this.tid = tid;
	}
	public String getPriceSort() {
		return priceSort;
	}
	public void setPriceSort(String priceSort) {
		this.priceSort = priceSort;
	}
	public String getStockSort() {
		return stockSort;
	}
	public void setStockSort(String stockSort) {
		this.stockSort = stockSort;
	}
	public String getSalSort() {
		return salSort;
	}
	public void setSalSort(String salSort) {
		this.salSort = salSort;
	}
	public GoodsSearchBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
