package cn.uaigou.filter;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 后台管理页面请求过滤
 * @author Administrator
 *
 */
public class AdminFilter implements Filter {

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		
		HttpSession session = request.getSession();
		Object obj = session.getAttribute("admin");
		if(obj==null){
			//未登录，重定向到管理员登录页面
			response.sendRedirect(request.getContextPath()+"/jsp/admin_login.jsp");
		}else{
			//管理员已登录
			chain.doFilter(req, resp);
			return;
		}
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

}
