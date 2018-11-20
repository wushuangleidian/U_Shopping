package cn.uaigou.entity;

import java.util.Date;

public class Admin {

	private int id;//自增唯一标识
	private String userName;//用户名
	private String password;//密码
	private String email;//邮箱，用于修改密码
	private int power;//权限,1销售，2仓库，3运营，0超级管理员
	private Date createTime;//创建时间
	private Date updateTime;//最后一次修改时间
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getPower() {
		return power;
	}
	public void setPower(int power) {
		this.power = power;
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
	public Admin(int id, String userName, String password, String email,
			int power, Date createTime, Date updateTime) {
		super();
		this.id = id;
		this.userName = userName;
		this.password = password;
		this.email = email;
		this.power = power;
		this.createTime = createTime;
		this.updateTime = updateTime;
	}
	public Admin() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
