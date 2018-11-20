package cn.uaigou.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.uaigou.utils.BaseServlet;
import cn.uaigou.utils.Captcha;
/**
 * 获取验证码图片的Servlet类
 * @author Administrator
 *
 */
@WebServlet("/codeimg")
public class CodeImageServlet extends BaseServlet {
	
	private Captcha captcha = Captcha.getNewIntence();

	/**
	 * 获取默认的验证码图片，字母+数字
	 * @return
	 */
	public String str(HttpServletRequest request,HttpServletResponse response) throws Exception{
		//清空浏览器缓存，防止浏览器对图片进行记忆存储，避免验证码刷新无效果的问题
		response.setHeader("pragma", "no-cache");
		response.setHeader("cache-control", "no-cache");
		response.setHeader("expires", "0");
		
		//接收尺寸参数
		String w = request.getParameter("w");//宽度
		String h = request.getParameter("h");//高度
		String fs = request.getParameter("fs");//字体大小
		
		//获取验证码
		String code = captcha.defaultImage(response, Integer.valueOf(w), Integer.valueOf(h), Integer.valueOf(fs));
		//把验证码存入session
		request.getSession().setAttribute("code", code);
		
		return null;
	}
	
	/**
	 * 获取算术验证码图片
	 * @return
	 */
	public String math(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		//清空浏览器缓存，防止浏览器对图片进行记忆存储，避免验证码刷新无效果的问题
		response.setHeader("pragma", "no-cache");
		response.setHeader("cache-control", "no-cache");
		response.setHeader("expires", "0");
		
		//接收尺寸参数
		String w = request.getParameter("w");//宽度
		String h = request.getParameter("h");//高度
		String fs = request.getParameter("fs");//字体大小
		
		//获取验证码
		String code = captcha.mathCaptchaImage(response, Integer.valueOf(w), Integer.valueOf(h), Integer.valueOf(fs));
		//把验证码存入session
		request.getSession().setAttribute("code", code);
		
		return "";
	}
	
}
