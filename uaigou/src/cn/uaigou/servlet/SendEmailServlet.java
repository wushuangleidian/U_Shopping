package cn.uaigou.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.uaigou.entity.VIP;
import cn.uaigou.utils.BaseServlet;
import cn.uaigou.utils.MailSendUtil;

/**
 * 发送邮件功能的Servlet类
 * @author Administrator
 *
 */
@WebServlet("/sendEmail")
public class SendEmailServlet extends BaseServlet{

	/**
	 * 发送激活邮件
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String jh(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		VIP vip = (VIP) request.getSession().getAttribute("vip");
		
		MailSendUtil msu = new MailSendUtil();
		String content = "亲爱的"+vip.getVipName()+"您好，请点击下方链接，激活您的账户！<br>"+
				"<a href='http://javaee.51vip.biz/uaigou/vip?m=yz&no="+vip.getVipNo()
				+"&name="+vip.getVipName()+"&code="+vip.getYzStr()+"'>" +
						"点击激活</a>";
		try {
			msu.sendHtmlMail(vip.getEmail(), "优爱购用户激活", content);
			request.setAttribute("mess", "<font color='green'>邮件发送成功，请到注册的邮箱中激活该账号！</font>");
		} catch (Exception e) {
			request.setAttribute("mess", "激活邮件发送失败！<a href='sendEmail?m=jh&email="+vip.getEmail()+"'>重新发送</>");
		} 
		
		return "jsp/result.jsp";
	}
	
}
