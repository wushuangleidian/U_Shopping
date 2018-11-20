package cn.uaigou.entity;

import java.util.Date;

public class Address {

		private Integer adrid;//唯一标识
		  
		private String vipno;//会员编号
		  
		private String name;//收件人姓名
		  
		private String phone;//电话
		  
		private String sheng;//省
		  
		private String shi;//市
		  
		private String qu;//区
		  
		private String adr;//详细地址
		  
		private Date createTime;//
		  
		private Date updateTime;//
		  
		public Integer getAdrid() {
			 return adrid;
		}
		
		public void setAdrid(Integer adrid) {
			 this.adrid = adrid;
		}
		
		public String getVipno() {
			 return vipno;
		}
		
		public void setVipno(String vipno) {
			 this.vipno = vipno;
		}
		
		public String getName() {
			 return name;
		}
		
		public void setName(String name) {
			 this.name = name;
		}
		
		public String getPhone() {
			 return phone;
		}
		
		public void setPhone(String phone) {
			 this.phone = phone;
		}
		
		public String getSheng() {
			 return sheng;
		}
		
		public void setSheng(String sheng) {
			 this.sheng = sheng;
		}
		
		public String getShi() {
			 return shi;
		}
		
		public void setShi(String shi) {
			 this.shi = shi;
		}
		
		public String getQu() {
			 return qu;
		}
		
		public void setQu(String qu) {
			 this.qu = qu;
		}
		
		public String getAdr() {
			 return adr;
		}
		
		public void setAdr(String adr) {
			 this.adr = adr;
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

		public Address() {
			super();
			// TODO Auto-generated constructor stub
		}

		@Override
		public String toString() {
			return "Address [adrid=" + adrid + ", vipno=" + vipno + ", name="
					+ name + ", phone=" + phone + ", sheng=" + sheng + ", shi="
					+ shi + ", qu=" + qu + ", adr=" + adr + ", createTime="
					+ createTime + ", updateTime=" + updateTime + "]";
		}

	
}
