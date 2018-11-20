package cn.uaigou.servlet;

import java.io.PrintWriter;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.JsonObject;

import cn.uaigou.entity.Shopping;
import cn.uaigou.entity.ShoppingExtend;
import cn.uaigou.entity.VIP;
import cn.uaigou.factory.BeanBuilderFactory;
import cn.uaigou.service.IService;
import cn.uaigou.serviceimpl.ShoppingServiceImpl;
import cn.uaigou.utils.BaseServlet;
import cn.uaigou.utils.BeanUtil;
import cn.uaigou.utils.DwrPush;
/**
 * 处理购物车相关的Servlet类
 * @author Administrator
 *
 */
@WebServlet("/shop")
public class ShoppingServlet extends BaseServlet {

	private IService<Shopping> service = BeanBuilderFactory.serviceBuiler("cn.uaigou.serviceimpl.ShoppingServiceImpl");
	
	/**
	 * 添加购物车方法
	 */
	public String add(HttpServletRequest request,HttpServletResponse response)throws Exception{
		
		//获取会话对象
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		
		//获取用户的来源
		String ref = request.getHeader("Referer");
		
		String url = request.getScheme()+"://"+request.getServerName();// htt://域名用于判断来源是否为当前站点
		
		if(ref==null){//当来源为空时
			return "r:jsp/index.jsp";
		}else if(ref.startsWith(url)){
			//保存来源URL
			session.setAttribute("ref", ref);
		}else{
			//从其他地方过来的
			return "r:"+ref;
		}
		
		//判断会员是否登录
		Object obj = session.getAttribute("vip");
		if(obj==null){//用户未登录时
			JsonObject jo = new JsonObject();
			jo.addProperty("result", "0");
			jo.addProperty("count", -1);
			out.print(jo.toString());
			return null;
		}
		
		VIP vip = (VIP) obj;
		if(vip.getStatus()==0){
			//未激活
			JsonObject jo = new JsonObject();
			jo.addProperty("result", "2");
			jo.addProperty("count", -1);
			out.print(jo.toString());
			return null;
		}
		
		String vipNo = vip.getVipNo();
		
		//获取加购商品参数
		String goodsNo = request.getParameter("goodsNo");
		String goodsNum = request.getParameter("goodsNum");
		int num;
		if(goodsNum!=null && !goodsNum.trim().equals("")){
			num = Integer.valueOf(goodsNum);
		}else{
			num = 1;
		}
		String guige = request.getParameter("guige");
		
		//封装购物车实体类对象
		Shopping shop = new Shopping();
		shop.setVipNo(vipNo);
		shop.setGoodsNo(goodsNo);
		shop.setGoodsNum(num);
		shop.setGuige(guige);
		
		//判断该商品是否已经加入购物车
		Shopping s2 = ((ShoppingServiceImpl)service).queryIsCZ(shop);
		if(s2!=null){
			//如果已存在，修改商品数量
			num += s2.getGoodsNum();
			s2.setGoodsNum(num);
			//修改数据库
			service.update(s2);
		}else{
			//不存在，添加商品
			service.add(shop);
		}
		
		//向JavaScript的shopping()方法推送数据
		int count = ((ShoppingServiceImpl)service).queryCountByVipno(vipNo);
		JsonObject jo = new JsonObject();
		jo.addProperty("result", "1");
		jo.addProperty("count", count);
		jo.addProperty("vipno", vipNo);
		out.print(jo.toString());
		return null;
	}
	
	/**
	 * 查询购物车中商品的数量
	 */
	public String queryCount(HttpServletRequest request,HttpServletResponse response)throws Exception{
		
		HttpSession session = request.getSession();
		Object obj = session.getAttribute("vip");
		if(obj!=null){
			VIP vip = (VIP) obj;
			int count = ((ShoppingServiceImpl)service).queryCountByVipno(vip.getVipNo());
			JsonObject jo = new JsonObject();
			jo.addProperty("result", "1");
			jo.addProperty("count", count);
			jo.addProperty("vipno", vip.getVipNo());
			response.getWriter().print(jo.toString());
		}
		return null;
	}
	
	/**
	 * 查看指定会员的购物车商品
	 */
	public String show(HttpServletRequest request,HttpServletResponse response)throws Exception{
		
		Object obj = request.getSession().getAttribute("vip");
		if(obj==null){
			return "r:jsp/login.jsp";
		}
		
		String vipno = request.getParameter("vipno");
		
		//查询购物车商品
		List<ShoppingExtend> list = ((ShoppingServiceImpl)service).queryByVipno(vipno);
		
		request.setAttribute("shoppingList", list);
		return "jsp/shopping.jsp";
	}
	
	/**
	 * 修改购物车指定商品的购买数量
	 */
	public String update(HttpServletRequest request,HttpServletResponse response)throws Exception{
		
		Shopping s = BeanUtil.getObject(request.getParameterMap(), Shopping.class);
		
		//修改数量
		service.update(s);
		
		return null;
	}
	
	/**
	 * 删除购物车中的单个商品
	 */
	public String delOne(HttpServletRequest request,HttpServletResponse response)throws Exception{
		
		String sid = request.getParameter("sid");
		if(sid!=null && !sid.trim().equals("")){
			boolean rel = service.delete(Integer.valueOf(sid));
			if(rel){
				response.getWriter().print(1);
			}else{
				response.getWriter().print(0);
			}
		}
		
		return null;
	}
	
	/**
	 * 批量删除购物车中的商品
	 */
	public String batchDel(HttpServletRequest request,HttpServletResponse response)throws Exception{
		
		String[] sid = request.getParameterValues("ids");
		int[] sids = new int[sid.length];
		for (int i = 0; i < sid.length; i++) {
			sids[i] = Integer.valueOf(sid[i]);
		}
		
		//批量删除
		int[] rel = ((ShoppingServiceImpl)service).batchDelete(sids);
		int a=0;
		for (int i : rel) {
			a+=i;
		}
		
		if(a<sid.length){
			response.getWriter().print(0);
		}else{
			response.getWriter().print(1);
		}
		
		return null;
	}
	
}
