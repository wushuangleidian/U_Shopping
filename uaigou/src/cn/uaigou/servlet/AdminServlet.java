package cn.uaigou.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.uaigou.entity.Admin;
import cn.uaigou.factory.BeanBuilderFactory;
import cn.uaigou.service.IService;
import cn.uaigou.serviceimpl.Admin2ServiceImpl;
import cn.uaigou.utils.BaseServlet;
import cn.uaigou.utils.BeanUtil;

/**
 * 处理和Admin相关的Servlet类
 * @author Administrator
 *
 */
@WebServlet("/user")
public class AdminServlet extends BaseServlet {

	private IService<Admin> service = BeanBuilderFactory.serviceBuiler("cn.uaigou.serviceimpl.AdminServiceImpl");
	
	/**
	 * 管理员登录
	 */
	public String login(HttpServletRequest request,HttpServletResponse response)throws Exception{
		
		Admin admin = BeanUtil.getObject(request.getParameterMap(), Admin.class);
		admin = ((Admin2ServiceImpl)service).login(admin);
		
		if(admin==null){
			request.setAttribute("mess", "<font color='red'>账号或密码错误</font>");
			return "jsp/admin_login.jsp";
		}
		
		//保存session
		request.getSession().setAttribute("admin", admin);
		
		return "r:admin/main.jsp";
	}
	
}
