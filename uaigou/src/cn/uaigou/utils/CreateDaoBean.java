package cn.uaigou.utils;

/**
 * 处理Dao中的实体类对象转换工具
 * @author Administrator
 *
 */
public class CreateDaoBean {

	/**
	 * 转换实体对象的方法
	 * @param obj dao中接收的参数
	 * @param c 要转换的对象的Class类型
	 * @return 要得到的类型对象
	 */
	public static <E>E getObject(Object obj,Class<E> c){
		
		E e = null;
		try {
			e = c.newInstance();
			e = (E)obj;
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		return e;
	}
	
}
