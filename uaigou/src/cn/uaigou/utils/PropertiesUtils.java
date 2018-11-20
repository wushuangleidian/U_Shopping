package cn.uaigou.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 读取properties配置文件的工具类
 * @author Administrator
 *
 */
public class PropertiesUtils {

	/**
	 * 获取Properties对象的方法
	 * @param filePath 配置文件的路径，文件如果在src下，书写格式为: /xxx.properties
	 * @return 返回加载后的Properties对象
	 */
	public static Properties getProp(String filePath){
		Properties prop = null;
		try {
			InputStream is = PropertiesUtils.class.getResourceAsStream(filePath);
			prop = new Properties();
			prop.load(is);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("读取"+filePath+"文件时出现了异常！");
		}
		return prop;
	}
	
}
