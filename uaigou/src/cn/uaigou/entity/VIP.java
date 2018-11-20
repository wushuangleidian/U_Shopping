package cn.uaigou.entity;

import java.util.Date;

public class VIP {

	private int id;//唯一标识
	private String vipNo;//会员编号
	private String vipName;//用户名，用户登录
	private String password;//登录用的密码
	private String tximg;//头像图片URL
	private String email;//邮箱地址，用于激活或修改密码
	private int status;//状态：0未激活，1已激活
	private String yzStr;//验证时用的字符串
	private double balance;//余额，默认值为0
	private Date createTime;//创建时间
	private Date updateTime;//最后一次修改时间
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getVipNo() {
		return vipNo;
	}
	public void setVipNo(String vipNo) {
		this.vipNo = vipNo;
	}
	public String getVipName() {
		return vipName;
	}
	public void setVipName(String vipName) {
		this.vipName = vipName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getTximg() {
		return tximg;
	}
	public void setTximg(String tximg) {
		this.tximg = tximg;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getYzStr() {
		return yzStr;
	}
	public void setYzStr(String yzStr) {
		this.yzStr = yzStr;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
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
	public VIP(int id, String vipNo, String vipName, String password,
			String tximg, String email, int status, String yzStr,
			double balance, Date createTime, Date updateTime) {
		super();
		this.id = id;
		this.vipNo = vipNo;
		this.vipName = vipName;
		this.password = password;
		this.tximg = tximg;
		this.email = email;
		this.status = status;
		this.yzStr = yzStr;
		this.balance = balance;
		this.createTime = createTime;
		this.updateTime = updateTime;
	}
	public VIP() {
	}
	@Override
	public String toString() {
		return "VIP [id=" + id + ", vipNo=" + vipNo + ", vipName=" + vipName
				+ ", password=" + password + ", tximg=" + tximg + ", email="
				+ email + ", status=" + status + ", yzStr=" + yzStr
				+ ", balance=" + balance + ", createTime=" + createTime
				+ ", updateTime=" + updateTime + "]";
	}
	
	
}
