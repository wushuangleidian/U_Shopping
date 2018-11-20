package cn.uaigou.entity;

import java.util.Date;
import java.util.List;

public class Gclass {

	private int gid;//唯一标识，自增
	private String name;//分类名称
	private int pid;//上级分类的id
	private int sx;//排序
	private Date createTime;
	private Date updateTime;
	private List<Tag> tagList;
	
	
	public List<Tag> getTagList() {
		return tagList;
	}
	public void setTagList(List<Tag> tagList) {
		this.tagList = tagList;
	}
	public int getSx() {
		return sx;
	}
	public void setSx(int sx) {
		this.sx = sx;
	}
	public int getGid() {
		return gid;
	}
	public void setGid(int gid) {
		this.gid = gid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
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
	public Gclass(int gid, String name, int pid, Date createTime,
			Date updateTime) {
		super();
		this.gid = gid;
		this.name = name;
		this.pid = pid;
		this.createTime = createTime;
		this.updateTime = updateTime;
	}
	public Gclass() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "Gclass [gid=" + gid + ", name=" + name + ", pid=" + pid
				+ ", sx=" + sx + ", createTime=" + createTime + ", updateTime="
				+ updateTime + ", tagList=" + tagList + "]";
	}
	public Gclass(int gid) {
		super();
		this.gid = gid;
	}
	
	
}
