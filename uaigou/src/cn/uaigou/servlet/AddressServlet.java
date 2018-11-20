package cn.uaigou.servlet;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;

import cn.uaigou.entity.Address;
import cn.uaigou.factory.BeanBuilderFactory;
import cn.uaigou.service.IService;
import cn.uaigou.utils.BaseServlet;
import cn.uaigou.utils.BeanUtil;
/**
 * 处理地址相关的Servlet类
 * @author Administrator
 *
 */
@WebServlet("/adr")
public class AddressServlet extends BaseServlet {

	private IService<Address> service = BeanBuilderFactory.serviceBuiler("cn.uaigou.serviceimpl.AddressServiceImpl");
	
	/**
	 * 加载三级联动城市数据
	 */
	public String loadCity(HttpServletRequest request,HttpServletResponse response)throws Exception{
		
		InputStream is = request.getServletContext().getResourceAsStream("/js/city.json");
		BufferedReader br = new BufferedReader(new InputStreamReader(is,"utf-8"));
		String str = "";
		StringBuffer sb = new StringBuffer();
		while((str=br.readLine())!=null){
			sb.append(str);
		}
		String data = sb.toString();//json数据
		response.getWriter().print(data);
		return null;
	}
	
	/**
	 * 添加收货地址
	 */
	public String add(HttpServletRequest request,HttpServletResponse response)throws Exception{
		Address adr = BeanUtil.getObject(request.getParameterMap(), Address.class);
		service.add(adr);
		return null;
	}
	
	/**
	 * 修改前查询
	 */
	public String updateBefore(HttpServletRequest request,HttpServletResponse response)throws Exception{
		String adrid = request.getParameter("adrid");
		if(adrid!=null && !adrid.trim().equals("")){
			Address adr = service.queryById(Integer.valueOf(adrid));
			request.setAttribute("adr", adr);
			return "jsp/updateAddress.jsp";
		}
		response.getWriter().print("<font color='red'>没有接收到地址ID！</font>");
		return null;
	}
	
	/**
	 * 修改地址
	 */
	public String update(HttpServletRequest request,HttpServletResponse response)throws Exception{
		Address adr = BeanUtil.getObject(request.getParameterMap(), Address.class);
		service.update(adr);
		return null;
	}
	
	/**
	 * 删除地址
	 */
	public String del(HttpServletRequest request,HttpServletResponse response)throws Exception{
		String adrid = request.getParameter("adrid");
		if(adrid!=null && !adrid.trim().equals("")){
			boolean rel = service.delete(Integer.valueOf(adrid));
			if(rel){
				response.getWriter().print(1);
			}
		}
		return null;
	}
	
}
