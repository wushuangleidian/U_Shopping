package cn.uaigou.entity;

import java.util.Date;

/**
 * 购物车商品实体类
 * @author Administrator
 *
 */
public class Shopping {

	private int sid;//唯一标识
	private String vipNo;//会员编号
	private String goodsNo;//商品编号
	private int goodsNum;//购买商品数量
	private String guige;//购买商品规格
	private int status;//状态，0为购物车商品，1为已下单商品
	private Date createTime;//创建时间
	private Date updateTime;//最后修改时间
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public String getVipNo() {
		return vipNo;
	}
	public void setVipNo(String vipNo) {
		this.vipNo = vipNo;
	}
	public String getGoodsNo() {
		return goodsNo;
	}
	public void setGoodsNo(String goodsNo) {
		this.goodsNo = goodsNo;
	}
	public int getGoodsNum() {
		return goodsNum;
	}
	public void setGoodsNum(int goodsNum) {
		this.goodsNum = goodsNum;
	}
	public String getGuige() {
		return guige;
	}
	public void setGuige(String guige) {
		this.guige = guige;
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
	public Shopping() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
