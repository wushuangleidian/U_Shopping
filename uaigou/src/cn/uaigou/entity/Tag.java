package cn.uaigou.entity;

public class Tag {

	private int id;
	private String tagName;
	private Gclass oneGc;
	private Gclass twoGc;
	private int tj;//推荐状态，0不推荐，1推荐
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getTagName() {
		return tagName;
	}
	public void setTagName(String tagName) {
		this.tagName = tagName;
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
	public int getTj() {
		return tj;
	}
	public void setTj(int tj) {
		this.tj = tj;
	}
	
	public Tag(int id, String tagName, Gclass oneGc, Gclass twoGc, int tj) {
		super();
		this.id = id;
		this.tagName = tagName;
		this.oneGc = oneGc;
		this.twoGc = twoGc;
		this.tj = tj;
	}
	public Tag() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "Tag [id=" + id + ", tagName=" + tagName + ", oneGc=" + oneGc
				+ ", twoGc=" + twoGc + ", tj=" + tj + "]";
	}
	
	
}
