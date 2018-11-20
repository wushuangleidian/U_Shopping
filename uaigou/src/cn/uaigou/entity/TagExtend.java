package cn.uaigou.entity;

/**
 * Tag的扩展类
 * @author Administrator
 *
 */
public class TagExtend extends Tag {

	private int oneGid;
	private int twoGid;
	public int getOneGid() {
		return oneGid;
	}
	public void setOneGid(int oneGid) {
		this.oneGid = oneGid;
	}
	public int getTwoGid() {
		return twoGid;
	}
	public void setTwoGid(int twoGid) {
		this.twoGid = twoGid;
	}
	@Override
	public String toString() {
		return "TagExtend [oneGid=" + oneGid + ", twoGid=" + twoGid + "]";
	}
	public TagExtend() {
		super();
		// TODO Auto-generated constructor stub
	}
	public TagExtend(int id, String tagName, Gclass oneGc, Gclass twoGc, int tj) {
		super(id, tagName, oneGc, twoGc, tj);
		// TODO Auto-generated constructor stub
	}
	public TagExtend(int id, String tagName, Gclass oneGc, Gclass twoGc,
			int tj, int oneGid, int twoGid) {
		super(id, tagName, oneGc, twoGc, tj);
		this.oneGid = oneGid;
		this.twoGid = twoGid;
	}
	
	
}
