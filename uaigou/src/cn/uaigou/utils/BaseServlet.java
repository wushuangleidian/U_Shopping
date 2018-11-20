package cn.uaigou.utils;


import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BaseServlet extends HttpServlet {

	@Override
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		//设置请求和响应编码
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		// 接收参数，代表要执行的方法
		String m = request.getParameter("m");

		if (m == null) {
			throw new RuntimeException("m参数没有信息");
		}

		// 通过反射找到对应的方法
		Class c = this.getClass();
		Method method = null;
		try {
			method = c.getMethod(m, HttpServletRequest.class,
					HttpServletResponse.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(m + "方法没有找到！");
		}

		if (method != null) {

			// 通过反射执行方法
			try {
				Object obj = method.invoke(c.newInstance(), request, response);
				//当返回值为空，或空字符串时，不做任何操作
				if(obj==null || obj.equals("")){
					return;
				}
				
				// 根据返回值的内容进行页面跳转
				String url = String.valueOf(obj);
				// 判断url
				boolean rel = url.contains(":");
				if (rel) {
					// 得到冒号前面的字符
					int index = url.indexOf(":");
					String ch = url.substring(0, index);

					// 获取页面名称
					String pageUrl = url.substring(index + 1);
					if (ch.equals("f")) {
						// 请求转发
						request.getRequestDispatcher(pageUrl).forward(request,
								response);
					} else if (ch.equals("r")) {
						// 重定向
						response.sendRedirect(pageUrl);
					}

				} else {
					// 默认使用请求转发
					request.getRequestDispatcher(url)
							.forward(request, response);
				}

			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(m + "方法执行时出现异常！");
			}

		}
	}
	
}
