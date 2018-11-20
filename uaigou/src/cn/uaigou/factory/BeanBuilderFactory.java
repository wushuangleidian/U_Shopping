package cn.uaigou.factory;

import java.util.Properties;

import cn.uaigou.dao.IDao;
import cn.uaigou.service.IService;
import cn.uaigou.utils.PropertiesUtils;

/**
 * 创建对象的工厂类
 * @author Administrator
 *
 */
public class BeanBuilderFactory {

	/**
	 * 创建Dao对象的工厂实现方法
	 * @param key web.properties配置文件的中的键
	 * @return 返回创建成功的IDao实现类对象
	 */
	public static IDao daoBuilder(String key){
		
		Properties prop = PropertiesUtils.getProp("/beanfactory.properties");
		IDao idao = null;
		try {
			Class c = Class.forName(prop.getProperty(key));
			Object obj = c.newInstance();
			if(obj instanceof IDao){
				idao = (IDao) obj;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("在daoBuilder()方法构建"+key+"的时候出现了异常！");
		}
		
		return idao;
	}
	
	/**
	 * 创建Service对象的工厂实现方法
	 * @param key key web.properties配置文件的中的键
	 * @return 返回创建成功的Service实现类对象
	 */
	public static IService serviceBuiler(String key){
		IService iservice = null;
		Properties prop = PropertiesUtils.getProp("/beanfactory.properties");
		try {
			Class c = Class.forName(prop.getProperty(key));
			Object obj = c.newInstance();
			if(obj instanceof IService){
				iservice = (IService) obj;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return iservice;
	}
	
}
