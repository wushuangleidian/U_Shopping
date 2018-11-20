package cn.uaigou.entity;

import java.util.List;

public class IndexShowBean {

	private Gclass gc;//一级分类对象
	private GoodsExtend[] ges;//一级分类下的商品
	public Gclass getGc() {
		return gc;
	}
	public void setGc(Gclass gc) {
		this.gc = gc;
	}
	
	public GoodsExtend[] getGes() {
		return ges;
	}
	public void setGes(GoodsExtend[] ges) {
		this.ges = ges;
	}
	public IndexShowBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
