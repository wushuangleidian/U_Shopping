package cn.uaigou.utils;

import java.util.Map;
import org.apache.commons.beanutils.BeanUtils;


public class BeanUtil {

	public static <T>T getObject(Map map,Class<T> c){
		
		T bean = null;
		try {
			
			bean = c.newInstance();
			BeanUtils.populate(bean, map);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return bean;
	}
	
	
}
