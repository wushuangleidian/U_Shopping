package cn.uaigou.entity;

import java.util.Date;

/**
 * 商品实体类
 * @author Administrator
 *
 */
public class Goods {

	private int id;//唯一标识
	private String goodsNo;//商品编号
	private Gclass oneGc;//一级分类对象
	private Gclass twoGc;//二级分类对象
	private String title;//商品标题
	private String subHead;//商品副标题
	private double price;//商品价格
	private int stock;//库存
	private int salNum;//销量
	private double score;//评分
	private String firstImg;//主图
	private String images;//其他主图
	private String details;//详情
	private int status;//上下架状态，0下架，1在售
	private int tuijian;//推荐，0不推荐，1推荐（如果为1，则展示在首页）
	private String cuxiao;//促销语
	private String guige;//规格
	private Date createTime;//创建时间
	private Date updateTime;//最后修改时间
	
	
	public String getCuxiao() {
		return cuxiao;
	}

	public void setCuxiao(String cuxiao) {
		this.cuxiao = cuxiao;
	}

	public String getGuige() {
		return guige;
	}

	public void setGuige(String guige) {
		this.guige = guige;
	}

	public Goods() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGoodsNo() {
		return goodsNo;
	}

	public void setGoodsNo(String goodsNo) {
		this.goodsNo = goodsNo;
	}

	public Gclass getOneGc() {
		return oneGc;
	}

	public void setOneGc(Gclass oneGc) {
		this.oneGc = oneGc;
	}

	public Gclass getTwoGc() {
		return twoGc;
	}

	public void setTwoGc(Gclass twoGc) {
		this.twoGc = twoGc;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubHead() {
		return subHead;
	}

	public void setSubHead(String subHead) {
		this.subHead = subHead;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	public int getSalNum() {
		return salNum;
	}

	public void setSalNum(int salNum) {
		this.salNum = salNum;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public String getFirstImg() {
		return firstImg;
	}

	public void setFirstImg(String firstImg) {
		this.firstImg = firstImg;
	}

	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	
	public int getTuijian() {
		return tuijian;
	}

	public void setTuijian(int tuijian) {
		this.tuijian = tuijian;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public String toString() {
		return "Goods [id=" + id + ", goodsNo=" + goodsNo + ", oneGc=" + oneGc
				+ ", twoGc=" + twoGc + ", title=" + title + ", subHead="
				+ subHead + ", price=" + price + ", stock=" + stock
				+ ", salNum=" + salNum + ", score=" + score + ", firstImg="
				+ firstImg + ", images=" + images + ", details=" + details
				+ ", status=" + status + ", tuijian=" + tuijian + ", cuxiao="
				+ cuxiao + ", guige=" + guige + ", createTime=" + createTime
				+ ", updateTime=" + updateTime + "]";
	}

	

	
	
	

}
