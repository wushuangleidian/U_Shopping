package cn.uaigou.servlet;

import java.util.Date;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.JsonObject;

import cn.uaigou.entity.VIP;
import cn.uaigou.factory.BeanBuilderFactory;
import cn.uaigou.service.IService;
import cn.uaigou.serviceimpl.VIP2ServiceImpl;
import cn.uaigou.utils.BaseServlet;
import cn.uaigou.utils.BeanUtil;
import cn.uaigou.utils.CreateStrUtil;
import cn.uaigou.utils.MailSendUtil;

/**
 * 处理VIP的Servlet类
 * @author Administrator
 *
 */
@SuppressWarnings("serial")
@WebServlet("/vip")
public class VIPServlet extends BaseServlet {

	@SuppressWarnings("unchecked")
	private IService<VIP> service = BeanBuilderFactory.serviceBuiler("cn.uaigou.serviceimpl.VIPServiceImpl");
	
	/**
	 * 会员注册
	 * @param requets
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String reg(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		//获取页面表单参数
		VIP vip = BeanUtil.getObject(request.getParameterMap(), VIP.class);
		
		//生成会员编号，并为会员对象赋值
		String vipNo = CreateStrUtil.getNewInstance().getVipNo();
		vip.setVipNo(vipNo);
		
		//把注册的会员对象添加到数据库
		boolean rel = service.add(vip);
		//注册成功
		if(rel){
			/*
			 * 发送验证邮箱
			 */
			//生成验证用的字符串
			String str = CreateStrUtil.getNewInstance().getYanZhengStr();
			String emailTitle = "优爱购会员注册激活";
			String emailText = "亲爱的"+vip.getVipName()+"您好，请点击下方链接，激活您的账户！<br>"+
								"<a href='http://javaee.51vip.biz/uaigou/vip?m=yz&no="+vipNo
								+"&name="+vip.getVipName()+"&code="+str+"'>" +
										"点击激活</a>";
			String toEmail = vip.getEmail();
			
			//发送邮件
			MailSendUtil msu = new MailSendUtil();
			boolean b = msu.sendHtmlMail(toEmail, emailTitle, emailText);
			if(b){
				//查询已保存的vip对象
				vip = service.queryByNo(vipNo);
				//把验证字符串赋值给vip对象，并存入数据库
				vip.setYzStr(str);
				boolean yzRel = service.update(vip);
				if(yzRel){
					request.setAttribute("mess", "<font color='green'>注册成功，请到注册的邮箱中激活该账号！</font>");
				}else{
					request.setAttribute("mess", "存储数据时出现了错误！");
				}
			}else{
				request.setAttribute("mess", "激活邮件发送失败！");
			}
			return "jsp/result.jsp";
		}else{
			request.setAttribute("mess", "注册失败！");
			return "jsp/reg.jsp";
		}
		
	}
	
	/**
	 * 注册时激活用的验证方法
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String yz(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		//接收验证的参数
		String no = request.getParameter("no");
		String name = request.getParameter("name");
		String code = request.getParameter("code");
		
		//查询数据库
		VIP vip =  service.queryByNo(no);
		if(vip!=null && vip.getVipName().equals(name) && vip.getYzStr().equals(code)){
			//激活用户
			vip.setYzStr("");
			vip.setStatus(1);
			vip.setUpdateTime(new Date());
			service.update(vip);
			
			request.setAttribute("mess", "<font color='green'>账户激活成功！</font><a href='jsp/index.jsp'>返回首页</a>");
			return "jsp/result.jsp";
		}else if(vip!=null && vip.getStatus()==1){
			request.setAttribute("mess", "该用户已经激活，无需重复激活！");
			return "jsp/result.jsp";
		}
		
		return "index.html";
	}
	
	/**
	 * 验证用户名是否已经存在
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String yzName(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		String name = request.getParameter("name");
		VIP vip = ((VIP2ServiceImpl)service).findByName(name);
		if(vip!=null){
			response.getWriter().print("1");
		}
		
		return null;
	}
	
	/**
	 * 验证邮箱是否已经存在
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String yzEmail(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		String email = request.getParameter("email");
		VIP vip = ((VIP2ServiceImpl)service).findByEmail(email);
		if(vip!=null){
			response.getWriter().print("1");
		}
		return null;
	}
	
	/**
	 * 会员登录前查询
	 * 用途页面跳转时验证
	 */
	public String showLogin(HttpServletRequest request,HttpServletResponse response)throws Exception{
		//判断用户是否已经登录
		VIP vip = (VIP) request.getSession().getAttribute("vip");
		if(vip!=null){
			return "index.html";
		}
		
		//获取已保存的cookie
		Cookie[] cs = request.getCookies();
		if(cs!=null && cs.length>0){
			for (Cookie cookie : cs) {
				if(cookie.getName().equals("vipName")){
					request.setAttribute("vipName", cookie.getValue());
				}
				if(cookie.getName().equals("password")){
					request.setAttribute("password", cookie.getValue());
				}
				if(cookie.getName().equals("isPwd")){
					request.setAttribute("isPwd", cookie.getValue());
				}
			}
		}
		
		return "jsp/login.jsp";
	}
	
	/**
	 * 会员登录
	 */
	public String login(HttpServletRequest request,HttpServletResponse response)throws Exception{
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");
		String isPwd = request.getParameter("isPwd");
		VIP vip = new VIP();
		vip.setPassword(password);
		if(userName.contains("@")){
			vip.setEmail(userName);
		}else{
			vip.setVipName(userName);
		}
		
		//查询数据库
		vip = ((VIP2ServiceImpl)service).login(vip);
		//没有查询到用户，登录失败
		if(vip==null){
			request.setAttribute("mess", "<font color='red' style='margin:10px;'>账号或密码错误</font>");
			return "jsp/login.jsp";
		}
		
		//保存session
		HttpSession session = request.getSession();
		session.setAttribute("vip", vip);
		
		//判断用户是否激活，未激活跳转到激活页面
		if(vip.getStatus()==0){
			request.setAttribute("mess", vip.getVipName()+"您好，您的账号尚未激活，请点击发送激活邮件" +
					"<a href='sendEmail?m=jh'>现在激活</a>，<a href='jsp/index.jsp'>稍后激活</a>");
			return "jsp/result.jsp";
		}
		
		//记住密码，保存cookie
		if(isPwd!=null && isPwd.equals("on")){
			Cookie c1 = new Cookie("vipName", userName);
			Cookie c2 = new Cookie("password", password);
			Cookie c3 = new Cookie("isPwd", "1");
			c1.setMaxAge(60*60*24*7);
			c2.setMaxAge(60*60*24*7);
			c3.setMaxAge(60*60*24*7);
			response.addCookie(c1);
			response.addCookie(c2);
			response.addCookie(c3);
		}else{
			//不记住密码
			Cookie[] cs = request.getCookies();
			if(cs!=null && cs.length>0){
				for (Cookie cookie : cs) {
					if(cookie.getName().equals("vipName")){
						cookie.setMaxAge(0);
						response.addCookie(cookie);
					}
					if(cookie.getName().equals("password")){
						cookie.setMaxAge(0);
						response.addCookie(cookie);
					}
					if(cookie.getName().equals("isPwd")){
						cookie.setMaxAge(0);
						response.addCookie(cookie);
					}
				}
			}
		}
		
		Object obj = session.getAttribute("ref");
		if(obj!=null){
			String ref = String.valueOf(obj);
			return "r:"+ref;
		}
		
		return "r:index.html";
	}
	
	/**
	 * 验证用户是否激活
	 */
	public String jihuo(HttpServletRequest request,HttpServletResponse response)throws Exception{
		Object obj = request.getSession().getAttribute("vip");
		if(obj!=null){
			VIP vip = (VIP) obj;
			//判断用户是否激活，未激活跳转到激活页面
			if(vip.getStatus()==0){
				request.setAttribute("mess", vip.getVipName()+"您好，您的账号尚未激活，请点击发送激活邮件" +
						"<a href='sendEmail?m=jh'>现在激活</a>，<a href='jsp/index.jsp'>稍后激活</a>");
				return "jsp/result.jsp";
			}else if(vip.getStatus()==1){
				return "r:index.html";
			}
		}
		return "r:jsp/login.jsp";
	}
	
	/**
	 * 验证用户是否已经登录
	 * 用于前台ajax方式验证
	 */
	public String isLogin(HttpServletRequest request,HttpServletResponse response)throws Exception{
		VIP vip = (VIP) request.getSession().getAttribute("vip");
		
		if(vip!=null){
			JsonObject jo = new JsonObject();
			jo.addProperty("result", 1);
			jo.addProperty("vipname", vip.getVipName());
			jo.addProperty("vipno", vip.getVipNo());
			response.getWriter().print(jo.toString());
		}else{
			JsonObject jo = new JsonObject();
			jo.addProperty("result", 0);
			response.getWriter().print(jo.toString());
		}
		
		return null;
	}
	
	/**
	 * 会员退出
	 */
	public String exit(HttpServletRequest request,HttpServletResponse response)throws Exception{
		//清除session
		request.getSession().removeAttribute("vip");
		return "r:vip?m=showLogin";
	}
	
	/**
	 * 设置新密码
	 */
	public String setPwd(HttpServletRequest request,HttpServletResponse response)throws Exception{
		
		String password = request.getParameter("password");
		
		//获取session中保存的邮箱
		String email = (String) request.getSession().getAttribute("email");
		if(email==null || email.equals("")){
			request.setAttribute("mess", "<font color='red'>非法操作，</font><a href='jsp/index.jsp'>去首页</a>");
			return "jsp/result.jsp";
		}
		
		VIP vip = ((VIP2ServiceImpl)service).findByEmail(email);
		vip.setPassword(password);
		
		//修改密码
		boolean rel = service.update(vip);
		if(rel){
			request.setAttribute("mess", "<font color='green'>密码修改成功，</font><a href='vip?m=showLogin'>现在去登录</a>");
			return "jsp/result.jsp";
		}else{
			request.setAttribute("mess", "<font color='red'>修改失败</font>");
			return "jsp/set_newPwd.jsp";
		}
		
		
	}
}
