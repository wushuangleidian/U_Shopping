package cn.uaigou.servlet;

import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.uaigou.entity.Gclass;
import cn.uaigou.entity.Goods;
import cn.uaigou.entity.PageBean;
import cn.uaigou.entity.Tag;
import cn.uaigou.factory.BeanBuilderFactory;
import cn.uaigou.service.IService;
import cn.uaigou.serviceimpl.GclassServiceImpl;
import cn.uaigou.serviceimpl.GoodsServiceImpl;
import cn.uaigou.serviceimpl.TagServiceImpl;
import cn.uaigou.utils.BaseServlet;

/**
 * 前台查询操作的Servlet类
 * @author Administrator
 *
 */
@WebServlet("/u")
public class UaigouServlet extends BaseServlet {

	private IService<Goods> service = BeanBuilderFactory.serviceBuiler("cn.uaigou.serviceimpl.GoodsServiceImpl");
	
	/**
	 * 查询商品详情
	 */
	public String goodsDetails(HttpServletRequest request,HttpServletResponse response)throws Exception{
		String no = request.getParameter("no");
		
		Goods goods = service.queryByNo(no);
		//转换规格类型
		String[] guige = goods.getGuige().split(",");
		
		//转换图片类型
		if(goods.getImages()!=null && goods.getImages().length()>0){
			String[] images = goods.getImages().split("<>");
			request.setAttribute("images", images);
		}
		
		request.setAttribute("goods", goods);
		request.setAttribute("guige", guige);
		return "jsp/goodsMess.jsp";
	}
	
	/**
	 * 搜索商品列表
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String list(HttpServletRequest request,HttpServletResponse response)throws Exception{
		
		GoodsServiceImpl gService = (GoodsServiceImpl)service;
		
		//封装页码
		String dqy = request.getParameter("dqy");
		int pc ;
		if(dqy==null || "".equals(dqy)){
			pc = 1;
		}else{
			pc = Integer.valueOf(dqy);
		}
		
		//按价格排序搜索
		String sx = request.getParameter("sx");
		
		//按一级分类查询
		String oneGid = request.getParameter("oneGid");
		if(oneGid!=null && !oneGid.trim().equals("")){
			int onegid = Integer.valueOf(oneGid);
			//查询一级分类下的所有标签
			List<Tag> tagList = new TagServiceImpl().queryByOneGid(onegid);
			request.setAttribute("tagList", tagList);
			
			//查询的分页封装对象
			PageBean<Goods> pb = gService.findByOnegidOfSearch(onegid, pc, 12,sx);
			request.setAttribute("oneGid", oneGid);
			request.setAttribute("pb", pb);
			return "jsp/goodsList.jsp";
		}
		
		//按二级分类查询
		String twoGid = request.getParameter("twoGid");
		if(twoGid!=null && !twoGid.trim().equals("")){
			int twogid = Integer.valueOf(twoGid);
			//查询标签
			List<Tag> tagList = new TagServiceImpl().queryByGid(twogid);
			request.setAttribute("tagList", tagList);
			//查询的分页封装对象
			PageBean<Goods> pb = gService.findByTwogidOfSearch(twogid, pc, 12,sx);
			request.setAttribute("twoGid", twoGid);
			request.setAttribute("pb", pb);
			return "jsp/goodsList.jsp";
		}
		
		//按标签查询
		String tId = request.getParameter("tid");
		if(tId!=null && !tId.trim().equals("")){
			int tid = Integer.valueOf(tId);
			//查询标签
			List<Tag> tagList = new TagServiceImpl().queryByTwoGidOtherId(tid);
			request.setAttribute("tagList", tagList);
			//查询的分页封装对象
			PageBean<Goods> pb = gService.queryByTid(tid, pc, 12,sx);
			request.setAttribute("tid", tId);
			request.setAttribute("pb", pb);
			return "jsp/goodsList.jsp";
		}
		
		
		//按关键词查询
		String kw = request.getParameter("kw");
		if(kw!=null && !kw.trim().equals("")){
			//查询标签
			List<Tag> tagList = new TagServiceImpl().queryByGoodsKw(kw.trim());
			request.setAttribute("tagList", tagList);
			//查询分页封装对象
			PageBean<Goods> pb = gService.queryByKw(kw.trim(), pc, 12,sx);
			request.setAttribute("kw", kw.trim());
			request.setAttribute("pb", pb);
			return "jsp/goodsList.jsp";
		}
		
		return "r:jsp/index.jsp";
	}
	
}
