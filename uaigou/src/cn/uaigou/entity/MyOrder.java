package cn.uaigou.entity;
 
import java.io.Serializable;
import java.util.Date;
 
public class MyOrder implements Serializable {
	
	public static final int ORDER_NOTPAY = 1;//待付款
	public static final int ORDER_PAY = 2;//已付款
	public static final int ORDER_SEND = 3;//已发货
	public static final int ORDER_SUCCESS = 4;//已完成
	public static final int ORDER_CLOSE = 5;//已关闭
	public static final int ORDER_DELETE = 6;//已删除
	
	private Integer id;//唯一标识
	  
	private String orderNo;//订单编号
	  
	private String vipno;//会员编号
	  
	private Integer adrid;//收货地址id
	  
	private Double totalPrice;//所有商品总价
	  
	private Double payMoney;//付款金额
	  
	private Integer status;//订单状态，1待付款，2,已付款，3已发货，4已完成,，5已关闭，6已删除
	  
	private String payType;//付款方式
	  
	private String expName;//快递公司名称
	  
	private String expNo;//快递单号
	  
	private Date createTime;//订单创建时间
	  
	private Date updateTime;//最后一次修改时间
	  
	public Integer getId() {
		 return id;
	}
	
	public void setId(Integer id) {
		 this.id = id;
	}
	
	public String getOrderNo() {
		 return orderNo;
	}
	
	public void setOrderNo(String orderNo) {
		 this.orderNo = orderNo;
	}
	
	public String getVipno() {
		 return vipno;
	}
	
	public void setVipno(String vipno) {
		 this.vipno = vipno;
	}
	
	public Integer getAdrid() {
		 return adrid;
	}
	
	public void setAdrid(Integer adrid) {
		 this.adrid = adrid;
	}
	
	public Double getTotalPrice() {
		 return totalPrice;
	}
	
	public void setTotalPrice(Double totalPrice) {
		 this.totalPrice = totalPrice;
	}
	
	public Double getPayMoney() {
		 return payMoney;
	}
	
	public void setPayMoney(Double payMoney) {
		 this.payMoney = payMoney;
	}
	
	public Integer getStatus() {
		 return status;
	}
	
	public void setStatus(Integer status) {
		 this.status = status;
	}
	
	public String getPayType() {
		 return payType;
	}
	
	public void setPayType(String payType) {
		 this.payType = payType;
	}
	
	public String getExpName() {
		 return expName;
	}
	
	public void setExpName(String expName) {
		 this.expName = expName;
	}
	
	public String getExpNo() {
		 return expNo;
	}
	
	public void setExpNo(String expNo) {
		 this.expNo = expNo;
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

	public MyOrder() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}