package cn.uaigou.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.uaigou.entity.VIP;
import cn.uaigou.factory.BeanBuilderFactory;
import cn.uaigou.service.IService;
import cn.uaigou.serviceimpl.VIP2ServiceImpl;
import cn.uaigou.utils.BaseServlet;
import cn.uaigou.utils.Captcha;
import cn.uaigou.utils.MailSendUtil;

/**
 * 验证用的Servlet类
 * @author Administrator
 *
 */
@WebServlet("/yz")
public class YanZhengServlet extends BaseServlet {
	
	
	/**
	 * 验证码验证
	 */
	public String code(HttpServletRequest request,HttpServletResponse response)throws Exception{
		
		String code = request.getParameter("code");
		
		//从session中获取验证码
		String code2 = (String) request.getSession().getAttribute("code");
		
		//如果是forgetPwd2页面请求来的
		String f = request.getParameter("f");
		if(f!=null && f.equals("forgetPwd2")){
			if(code!=null && code.equals(code2)){
				//验证码正确
				return "r:jsp/set_newPwd.jsp";
			}else{
				//验证失败
				request.setAttribute("mess", "<font color='red'>验证码有误</font>");
				return "jsp/forgetPwd2.jsp";
			}
		}

		
		if(code!=null && code.equals(code2)){
			//验证码正确
			response.getWriter().print("1");
		}else{
			//验证失败
			response.getWriter().print("0");
		}
		
		return null;
	}

	/**
	 * 发送验证邮件
	 * @return
	 */
	public String email(HttpServletRequest request,HttpServletResponse response)throws Exception{
		
		String email = request.getParameter("email");
		//判断非空
		if(email==null){
			request.setAttribute("mess", "<font color='red'>邮箱不能为空</font>");
			return "jsp/forgetPwd.jsp";
		}else{
			//验证邮箱是否存在
			IService<VIP> service = BeanBuilderFactory.serviceBuiler("cn.uaigou.serviceimpl.VIPServiceImpl");
			VIP vip = ((VIP2ServiceImpl)service).findByEmail(email);
			if(vip==null){
				request.setAttribute("mess", "<font color='red'>此邮箱不存在</font>");
				return "jsp/forgetPwd.jsp";
			}else{
				MailSendUtil msu = new MailSendUtil();
				
				//生成验证码
				String code = Captcha.getNewIntence().randomStr(6);
				//把验证码保存到session
				request.getSession().setAttribute("code", code);
				//发送邮件
				String content = "您的验证码是：<font color='red'><b>"+code+"</b></font>，此验证30分钟内有效！";
				boolean b = msu.sendHtmlMail(email, "用户找回密码", content);
				if(!b){
					//发送失败
					request.setAttribute("mess", "<font color='red'>邮件发送失败</font>");
					return "jsp/forgetPwd.jsp";
				}else{
					request.getSession().setAttribute("email", email);
					return "r:jsp/forgetPwd2.jsp";
				}
			}
		}
		
	}
}
