package cn.uaigou.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import cn.uaigou.daoimpl.VIPDaoImpl;

/**
 * 生成字符串的工具类
 * @author Administrator
 *
 */
public class CreateStrUtil {

	private static CreateStrUtil csu = new CreateStrUtil();
	private CreateStrUtil(){
		
	}
	
	/**
	 * 用于获取当前类的单例对象
	 * @return 返回该类的单例对象
	 */
	public static CreateStrUtil getNewInstance(){
		return csu;
	}
	
	/**
	 * 生成会员编号
	 * @return
	 */
	public String getVipNo(){
		//生成随机6位数字
		Random ran = new Random();
		String vipNo = null;
		while(true){
			int rel = ran.nextInt(899999)+100000;
			vipNo = String.valueOf(rel);
			
			//去数据库中查询是否重复
			Object obj = new VIPDaoImpl().queryByNo(vipNo);
			if(obj==null){
				break;
			}
		}
		
		return vipNo;
	}
	
	/**
	 * 生成激活用的验证字符串
	 * @return
	 */
	public String getYanZhengStr(){
		UUID uuid = UUID.randomUUID();
		String uuidStr = uuid.toString();
		uuidStr = uuidStr.replace("-", "")+new Date().getTime();
		return uuidStr;
	}
	
	/**
	 * 生成商品编号
	 * 商品编号生成规则：yyyyMMddHHmmss+3位随机数
	 */
	public String getGoodsNo(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String data = sdf.format(new Date());
		Random ran = new Random();
		int i = ran.nextInt(900)+100;
		return data+i;
	}
	
	/**
	 * 生成订单编号
	 */
	public String getOrderNo(){
		Random ran = new Random();
		String no =String.valueOf(new Date().getTime())+(ran.nextInt(9000)+1000);
		return no;
	}
	
}
