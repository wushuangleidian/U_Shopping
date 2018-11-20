package cn.uaigou.daoimpl;

import java.util.List;

import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.junit.Test;

import cn.uaigou.db.TxQueryRunner;
import cn.uaigou.entity.Gclass;
import cn.uaigou.entity.Tag;
import cn.uaigou.entity.TagExtend;

public class DemoDao {

	private TxQueryRunner qr = new TxQueryRunner();
	
	@Test
	public void query(){
		
		
		String sql = "select * from tag where id=?";
		TagExtend tagExtend = qr.query(sql,1 , new BeanHandler<TagExtend>(TagExtend.class));
		
		qr = new TxQueryRunner();
		String sql2 = "select * from gclass where gid=?";
		Gclass oneGc = qr.query(sql, 1, new BeanHandler<Gclass>(Gclass.class));
		System.out.println(oneGc);
		
		qr = new TxQueryRunner();
		String sql3 = "select * from gclass where gid=?";
		Gclass twoGc = qr.query(sql, tagExtend.getTwoGid(), new BeanHandler<Gclass>(Gclass.class));
		
		Tag tag = tagExtend;
		tag.setOneGc(oneGc);
		tag.setTwoGc(twoGc);
		
		System.out.println(tag+","+tag.getId()+","+tag.getTagName()+","+tag.getTj()+","+tag.getOneGc()+","+tag.getTwoGc());
		
	}
	
}
