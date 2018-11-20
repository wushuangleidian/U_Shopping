package cn.uaigou.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.uaigou.pay.util.PayConfig;
import cn.uaigou.pay.util.PaymentUtil;
import cn.uaigou.utils.BaseServlet;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.demo.trade.config.Configs;
import com.alipay.demo.trade.model.ExtendParams;
import com.alipay.demo.trade.model.builder.AlipayTradePrecreateRequestBuilder;
import com.alipay.demo.trade.model.result.AlipayF2FPrecreateResult;
import com.alipay.demo.trade.service.AlipayTradeService;
import com.alipay.demo.trade.service.impl.AlipayTradeServiceImpl;
import com.alipay.demo.trade.utils.ZxingUtils;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * 支付宝支付请求功能
 * @author Administrator
 * 
 */
@WebServlet("/zhifu")
public class AliPayServlet extends BaseServlet {

	/**
	 * 支付宝付款
	 */
	public String alipay(HttpServletRequest request,HttpServletResponse response) throws Exception{
		if(request.getParameter("orderNo")!=null){
	        // 一定要在创建AlipayTradeService之前设置参数
	        Configs.init("zfbinfo.properties");
	        AlipayTradeService tradeService = new AlipayTradeServiceImpl.ClientBuilder().build();

	        // (必填) 商户网站订单系统中唯一订单号，64个字符以内，只能包含字母、数字、下划线，
	        // 需保证商户系统端不能重复，建议通过数据库sequence生成，
	        String outTradeNo = request.getParameter("orderNo");

	        // (必填) 订单标题，粗略描述用户的支付目的。如“喜士多（浦东店）消费”
	        String subject = request.getParameter("title");

	        // (必填) 订单总金额，单位为元，不能超过1亿元
	        // 如果同时传入了【打折金额】,【不可打折金额】,【订单总金额】三者,则必须满足如下条件:【订单总金额】=【打折金额】+【不可打折金额】
	        String totalAmount = request.getParameter("tm");

	        // (可选) 订单不可打折金额，可以配合商家平台配置折扣活动，如果酒水不参与打折，则将对应金额填写至此字段
	        // 如果该值未传入,但传入了【订单总金额】,【打折金额】,则该值默认为【订单总金额】-【打折金额】
	        String undiscountableAmount = request.getParameter("undiscountableAmount");

	        // 卖家支付宝账号ID，用于支持一个签约账号下支持打款到不同的收款账号，(打款到sellerId对应的支付宝账号)
	        // 如果该字段为空，则默认为与支付宝签约的商户的PID，也就是appid对应的PID
	        String sellerId = "";

	        // 订单描述，可以对交易或商品进行一个详细地描述，比如填写"购买商品2件共15.00元"
	        String goodsNum = request.getParameter("goodsNum");
	        String body = "购买商品"+goodsNum+"件共"+totalAmount+"元";

	        // 商户操作员编号，添加此参数可以为商户操作员做销售统计
	        String operatorId = "test_operator_id";

	        // (必填) 商户门店编号，通过门店号和商家后台可以配置精准到门店的折扣信息，详询支付宝技术支持
	        String storeId = "test_store_id";

	        // 业务扩展参数，目前可添加由支付宝分配的系统商编号(通过setSysServiceProviderId方法)，详情请咨询支付宝技术支持
	        ExtendParams extendParams = new ExtendParams();
	        extendParams.setSysServiceProviderId("2088100200300400500");

	        // 支付超时，定义为120分钟
	        String timeoutExpress = "10m";

	        AlipayTradePrecreateRequestBuilder builder =new AlipayTradePrecreateRequestBuilder()
	                .setSubject(subject)
	                .setTotalAmount(totalAmount)
	                .setOutTradeNo(outTradeNo)
	                .setUndiscountableAmount(undiscountableAmount)
	                .setSellerId(sellerId)
	                .setBody(body)
	                .setOperatorId(operatorId)
	                .setStoreId(storeId)
	                .setExtendParams(extendParams)
	                .setTimeoutExpress(timeoutExpress)
   	                .setNotifyUrl("http://javaee.51vip.biz/uaigou/rollurl?m=alipayRoll")//支付宝服务器主动通知商户服务器里指定的页面http路径,根据需要设置
	                .setGoodsDetailList(null);

	        AlipayF2FPrecreateResult result = tradeService.tradePrecreate(builder);
	        switch (result.getTradeStatus()) {
	            case SUCCESS:
	               //支付宝预下单成功

	                AlipayTradePrecreateResponse res = result.getResponse();

	                String basePath = request.getSession().getServletContext().getRealPath("/");
	                String fileName = "images/alipay_qrcode/"+res.getOutTradeNo()+".png";
	                String filePath = new StringBuilder(basePath).append(fileName).toString();
	                
	                request.setAttribute("fileName", fileName);
	                ZxingUtils.getQRCodeImge(res.getQrCode(), 256, filePath);
	                request.getRequestDispatcher("jsp/alipay.jsp").forward(request, response);
	                break;

	            case FAILED:
	                //支付宝预下单失败
	                break;

	            case UNKNOWN:
	                //系统异常，预下单状态未知
	                break;

	            default:
	                //不支持的交易状态，交易返回异常
	                break;
	        }
	        System.out.println(result.getResponse().getBody());
	    }
		return null;
	}
	
	/**
	 * 网银付款
	 */
	public String yeepay(HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		String p0_Cmd = "Buy";
		String p1_MerId = PayConfig.getValue("p1_MerId");	//合作者ID
		String keyValue = PayConfig.getValue("keyValue");	//合作者KEY
		String p2_Order = request.getParameter("orderNo");	//订单号
		String p3_Amt = request.getParameter("tm");	//订单金额
		String p4_Cur = "CNY";
		String p5_Pid = "";
		String p6_Pcat = "";
		String p7_Pdesc = "";
		String p8_Url = PayConfig.getValue("responseURL");
		String p9_SAF = "";
		String pa_MP = "";
		String pd_FrpId = request.getParameter("FrpId");//所选银行编号
		String pr_NeedResponse = "1";
		
		String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt, p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc, p8_Url, p9_SAF, pa_MP, pd_FrpId, pr_NeedResponse, keyValue);
		
		String url = "https://www.yeepay.com/app-merchant-proxy/node?" +
				"p0_Cmd="+p0_Cmd+
				"&p1_MerId="+p1_MerId+
				"&p2_Order="+p2_Order+
				"&p3_Amt="+p3_Amt+
				"&p4_Cur="+p4_Cur+
				"&p5_Pid="+p5_Pid+
				"&p6_Pcat="+p6_Pcat+
				"&p7_Pdesc="+p7_Pdesc+
				"&p8_Url="+p8_Url+
				"&p9_SAF="+p9_SAF+
				"&pa_MP="+pa_MP+
				"&pd_FrpId="+pd_FrpId+
				"&pr_NeedResponse="+pr_NeedResponse+
				"&hmac="+hmac;

		response.sendRedirect(url);
		return null;
	}
	
}
