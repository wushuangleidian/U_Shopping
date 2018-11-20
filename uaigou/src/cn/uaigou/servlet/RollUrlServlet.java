package cn.uaigou.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.demo.trade.config.Configs;

import cn.uaigou.pay.util.PayConfig;
import cn.uaigou.pay.util.PaymentUtil;
import cn.uaigou.serviceimpl.MyOrderServiceImpl;
import cn.uaigou.utils.BaseServlet;

/**
 * 支付回调Servlet类
 * @author Administrator
 *
 */
@WebServlet("/rollurl")
public class RollUrlServlet extends BaseServlet{

	private static final Log logger = LogFactory.getLog(RollUrlServlet.class);
	
	/**
	 * 支付宝回调
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public String alipayRoll(HttpServletRequest req, HttpServletResponse resp){
		
		System.out.println("支付宝回调执行。。。。。");
		
		Map<String,String> params = new HashMap<String, String>();//封装回调参数
		Map<String,String[]> map = req.getParameterMap();//接收支付宝回调请求的参数
		
		//对支付宝回调参数的值解析
		Set<String> keys = map.keySet();
		for (String key : keys) {
			String[] valuesArr =  map.get(key);
			String value = "";
			for (int i = 0; i < valuesArr.length; i++) {
				value += (i==valuesArr.length-1) ? valuesArr[i] : valuesArr[i]+",";
			}
			params.put(key, value);
		}
		
		logger.info("支付宝回调被执行了，params="+params.toString());
		
		//params.remove("sign");
		params.remove("sign_type");
		
		//验证订单号是否存在
		String orderNo = params.get("out_trade_no");
		//验证订单金额是否正确
		String tm = params.get("receipt_amount");
		
		boolean b = new MyOrderServiceImpl().checkAlipay(orderNo, tm);
		logger.info("验证订单验证："+b);
		//校验无误，修改订单状态为已付款
		if(b){
			String paymoney = params.get("buyer_pay_amount");
			boolean b2 = new MyOrderServiceImpl().setPayStatus(Double.valueOf(paymoney), "支付宝", orderNo);
			logger.info("验证订单状态修改："+b2);
			if(b2){
				try {
					resp.getWriter().print("success");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		//校验
		boolean rel;
		/*try {
			rel = AlipaySignature.rsaCheckV2(params, Configs.getAlipayPublicKey(), "utf-8", Configs.getSignType());
			if(!rel){
				logger.info("支付宝被篡改");
				throw new RuntimeException("支付宝信息被篡改！");
			}else{
				//验证订单号是否存在
				String orderNo = params.get("out_trade_no");
				//验证订单金额是否正确
				String tm = params.get("receipt_amount");
				
				boolean b = new MyOrderServiceImpl().checkAlipay(orderNo, tm);
				logger.info("验证订单验证："+b);
				//校验无误，修改订单状态为已付款
				if(b){
					String paymoney = params.get("buyer_pay_amount");
					boolean b2 = new MyOrderServiceImpl().setPayStatus(Double.valueOf(paymoney), "支付宝", orderNo);
					logger.info("验证订单状态修改："+b2);
					if(b2){
						resp.getWriter().print("success");
					}
				}
			}
		} catch (AlipayApiException e) {
			e.printStackTrace();
			throw new RuntimeException("支付宝回调异常！");
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		
		return null;
	}
	
	/**
	 * 易宝网银支付回调
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String yeepayRoll(HttpServletRequest request, HttpServletResponse response)throws Exception{
		
		response.setCharacterEncoding("gb2312");
		response.setContentType("text/html;charset=gb2312");
		
		String p1_MerId = PayConfig.getValue("p1_MerId");
		String r0_Cmd = request.getParameter("r0_Cmd");
		String r1_Code = request.getParameter("r1_Code");
		String r2_TrxId = request.getParameter("r2_TrxId");
		String r3_Amt = request.getParameter("r3_Amt");
		String r4_Cur = request.getParameter("r4_Cur");
		String r5_Pid = request.getParameter("r5_Pid");
		String r6_Order = request.getParameter("r6_Order");
		String r7_Uid = request.getParameter("r7_Uid");
		String  r8_MP= request.getParameter("r8_MP");
		String r9_BType = request.getParameter("r9_BType");
		String hmac = request.getParameter("hmac");
		
		String keyValue = PayConfig.getValue("keyValue");
		
		boolean b = PaymentUtil.verifyCallback(hmac, p1_MerId, r0_Cmd, r1_Code, r2_TrxId, r3_Amt, r4_Cur, r5_Pid, r6_Order, r7_Uid, r8_MP, r9_BType, keyValue);
		if(!b){
			response.getWriter().write("交易签名已被修改！！！");
			return null;
		}
		
		if("1".equals(r1_Code)){  //处理支付成功
			//修改订单状态为已付款
			new MyOrderServiceImpl().setPayStatus(Double.valueOf(r3_Amt), request.getParameter("rb_BankId"), r6_Order);
			
			if("1".equals(r9_BType)){
				// 支付成功
				String mess = "<p style='height: 200px;line-height: 200px;text-align: center;width: 70%;margin: 0px auto;border-bottom: 1px dashed #888888;'>"
						+ "<i class='layui-icon layui-icon-ok' style='font-size:40px;color: green;font-weight: bold;'></i>"
						+ "<font style='font-size: 30px;margin: 0px 20px;color: green;'>付款成功</font>"
						+ "</p>"
						+ "<p style='text-align: center;'>如果需要查看订单请求，请<a href='jsp/userOrder.jsp' style='color: #1E90FF;font-size: 16px;'>点击查看</a>！"
						+ "</p>";
				
				request.setAttribute("mess", mess);
				return "jsp/payResult.jsp";
			}
			if("2".equals(r9_BType)){
				response.getWriter().write("success");
			}
		}
		
		return null;
	}
	
}
