package cn.uaigou.servlet;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.uaigou.entity.Address;
import cn.uaigou.entity.Goods;
import cn.uaigou.entity.MyOrder;
import cn.uaigou.entity.ShoppingExtend;
import cn.uaigou.entity.VIP;
import cn.uaigou.factory.BeanBuilderFactory;
import cn.uaigou.service.IService;
import cn.uaigou.serviceimpl.AddressServiceImpl;
import cn.uaigou.serviceimpl.GoodsServiceImpl;
import cn.uaigou.serviceimpl.MyOrderServiceImpl;
import cn.uaigou.serviceimpl.ShoppingServiceImpl;
import cn.uaigou.utils.BaseServlet;
import cn.uaigou.utils.CreateStrUtil;
/**
 * 处理订单的Servlet类
 * @author Administrator
 *
 */
@WebServlet("/order")
public class OrderServlet extends BaseServlet {

	private IService<MyOrder> service = BeanBuilderFactory.serviceBuiler("cn.uaigou.serviceimpl.MyOrderServiceImpl");
	
	/**
	 * 立即购买的方法
	 */
	public String buy(HttpServletRequest request,HttpServletResponse response)throws Exception{
		
		Object obj = request.getSession().getAttribute("vip");
		VIP vip = null;
		if(obj!=null){
			vip = (VIP) obj;
			if(vip.getStatus()==0){
				return "r:vip?m=jihuo";
			}
		}else{
			String ref = request.getHeader("Referer");
			request.getSession().setAttribute("ref",ref);
			return "r:jsp/login.jsp";
		}
		
		//查询当前会员的收货地址
		List<Address> adrList = new AddressServiceImpl().queryByVipno(vip.getVipNo());
		request.setAttribute("adrList", adrList);
		
		String f = request.getParameter("f");//请求来源，f=goods来自商品详情，f=shop来自购物车
		if(f!=null && f.equals("goods")){//来自商品详情
			
			//查询商品并封装
			String id = request.getParameter("id");//商品ID
			if(id!=null && !id.trim().equals("")){
				Goods goods = new GoodsServiceImpl().queryById(Integer.valueOf(id));
				request.setAttribute("goods", goods);
			}
			
			String guige = request.getParameter("guige");
			request.setAttribute("guige", guige);
			String goodsNum = request.getParameter("goodsNum");
			if(goodsNum!=null && !goodsNum.trim().equals("")){
				request.setAttribute("goodsNum", Integer.valueOf(goodsNum));
			}
			
			return "jsp/buyone.jsp";
		}else if(f!=null && f.equals("shop")){//来自购物车
			String[] sids = request.getParameterValues("sid");//获取购物车的id
			String vipno = vip.getVipNo();
			String tm = request.getParameter("tm");
			
			int[] sid = new int[sids.length];
			for (int i = 0; i < sid.length; i++) {
				sid[i]=Integer.valueOf(sids[i]);
			}
			
			//查询指定的购物车商品
			List<ShoppingExtend> list = new ShoppingServiceImpl().queryBySid(sid);
			request.setAttribute("shopList", list);
			request.setAttribute("tm", tm);
			
			return "jsp/buy.jsp";
		}else{
			return "r:index.html";
		}
		
	}
	
	/**
	 * 接收创建订单的信息，购物车中去付款按钮和详情页中立即购买按钮的提交接口
	 */
	public String toPay(HttpServletRequest request,HttpServletResponse response)throws Exception{
		
		Object obj = request.getSession().getAttribute("vip");
		VIP vip = null;
		if(obj!=null){
			vip = (VIP) obj;
		}else{
			String ref = request.getHeader("Referer");
			request.getSession().setAttribute("ref",ref);
			return "r:jsp/login.jsp";
		}
		
		//创建订单对象
		MyOrder order = new MyOrder();
		order.setVipno(vip.getVipNo());
		
		//生成订单编号
		String orderNo = CreateStrUtil.getNewInstance().getOrderNo();
		order.setOrderNo(orderNo);
		request.setAttribute("orderNo", orderNo);
		
		//收货地址
		String adrid = request.getParameter("adrid");
		if(adrid!=null && !adrid.trim().equals("")){
			int addressId = Integer.valueOf(adrid);
			order.setAdrid(addressId);
			Address adr = new AddressServiceImpl().queryById(addressId);
			request.setAttribute("adr", adr);
		}
		
		//所有商品总价
		String tm = request.getParameter("tm");
		if(tm!=null && !tm.trim().equals("")){
			order.setTotalPrice(Double.valueOf(tm));
			request.setAttribute("tm", tm);
		}
		
		String f = request.getParameter("f");//请求来源
		
		if(f!=null && f.equals("buyone")){//来自于单个商品的立即购买
			
			String goodsno = request.getParameter("goodsNo");
			boolean rel = ((MyOrderServiceImpl)service).add(order, goodsno);
			if(rel){
				Goods goods = new GoodsServiceImpl().queryByNo(goodsno);
				request.setAttribute("goods", goods);
				request.setAttribute("guige", request.getParameter("guige"));
				request.setAttribute("goodsNum", request.getParameter("goodsNum"));
				return "jsp/pay.jsp";
			}else{
				request.setAttribute("mess", "<font color='red'>订单创建失败！</font>");
				return "jsp/result.jsp";
			}
			
		}else{
			//购物车sid
			String[] sids = request.getParameterValues("sid");
			int[] sid = new int[sids.length];
			for (int i = 0; i < sid.length; i++) {
				sid[i] = Integer.valueOf(sids[i]);
			}
			
			//在数据库中创建订单信息
			boolean rel = ((MyOrderServiceImpl)service).add(order, sid);
			
			if(rel){
				//查询要购买的商品
				List<ShoppingExtend> shopList = new ShoppingServiceImpl().queryBySid(sid);
				request.setAttribute("shopList", shopList);
				return "jsp/pay.jsp";
			}else{
				request.setAttribute("mess", "<font color='red'>订单创建失败！</font>");
				return "jsp/result.jsp";
			}
			
		}
		
	}
	
}
