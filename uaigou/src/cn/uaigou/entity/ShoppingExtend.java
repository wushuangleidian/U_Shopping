package cn.uaigou.entity;

public class ShoppingExtend extends Shopping {

	private String firstImg;//商品主图
	private String title;//商品标题
	private double price;//商品价格
	private int stock;//库存
	
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public String getFirstImg() {
		return firstImg;
	}
	public void setFirstImg(String firstImg) {
		this.firstImg = firstImg;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public ShoppingExtend() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
