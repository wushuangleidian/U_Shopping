package cn.uaigou.servlet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import cn.uaigou.daoimpl.GoodsDaoImpl;
import cn.uaigou.entity.Gclass;
import cn.uaigou.entity.Goods;
import cn.uaigou.entity.GoodsSearchBean;
import cn.uaigou.entity.PageBean;
import cn.uaigou.entity.Tag;
import cn.uaigou.factory.BeanBuilderFactory;
import cn.uaigou.service.IService;
import cn.uaigou.serviceimpl.GclassServiceImpl;
import cn.uaigou.serviceimpl.GoodsServiceImpl;
import cn.uaigou.serviceimpl.TagServiceImpl;
import cn.uaigou.utils.BaseServlet;
import cn.uaigou.utils.BeanUtil;
import cn.uaigou.utils.CreateStrUtil;

/**
 * 处理商品相关的Servlet类
 * 
 * @author Administrator
 * 
 */
@WebServlet("/goods")
public class GoodsServlet extends BaseServlet {

	private IService<Goods> service = BeanBuilderFactory
			.serviceBuiler("cn.uaigou.serviceimpl.GoodsServiceImpl");

	/**
	 * 添加商品前的查询
	 */
	public String addBefore(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		int oneGid = Integer.valueOf(request.getParameter("oneGid"));
		int twoGid = Integer.valueOf(request.getParameter("twoGid"));

		// 生成商品的编号
		String goodsNo = CreateStrUtil.getNewInstance().getGoodsNo();

		// 查询一级分类名称
		Gclass oneGc = new GclassServiceImpl().queryById(oneGid);

		// 查询二级分类名称
		Gclass twoGc = new GclassServiceImpl().queryById(twoGid);

		// 查询二级分类下的所有标签
		List<Tag> tagList = new TagServiceImpl().queryByGid(twoGid);

		// 将数据保存到request域中
		request.setAttribute("oneGc", oneGc);
		request.setAttribute("twoGc", twoGc);
		request.setAttribute("tagList", tagList);
		request.setAttribute("goodsNo", goodsNo);

		return "admin/add_goods.jsp";
	}

	/**
	 * 添加商品
	 */
	public String add(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		// 接收参数
		String[] tags = request.getParameterValues("tag");
		int oneGid = Integer.valueOf(request.getParameter("oneGid"));
		int twoGid = Integer.valueOf(request.getParameter("twoGid"));
		String[] images = request.getParameterValues("images");
		String details = request.getParameter("container");

		Goods goods = BeanUtil
				.getObject(request.getParameterMap(), Goods.class);
		// 添加分类对象
		Gclass oneGc = new GclassServiceImpl().queryById(oneGid);
		goods.setOneGc(oneGc);
		Gclass twoGc = new GclassServiceImpl().queryById(twoGid);
		goods.setTwoGc(twoGc);
		// 添加图片
		String imgs = "";
		if (images != null && images.length > 0) {
			for (int i = 0; i < images.length; i++) {
				if (!images[i].trim().equals("")) {
					imgs += (i == images.length - 1) ? images[i] : images[i] + "<>";
				}
			}
		}
		goods.setImages(imgs);
		// 添加详情
		goods.setDetails(details);

		// 将商品添加到数据库
		boolean rel = service.add(goods);

		if (!rel) {
			// 添加失败
			String mess = "<p style='height: 200px;line-height: 200px;text-align: center;width: 70%;margin: 0px auto;border-bottom: 1px dashed #888888;'>"
					+ "<i class='layui-icon layui-icon-close' style='font-size:40px;color: #FF0000;font-weight: bold;'></i>"
					+ "<font style='font-size: 30px;margin: 0px 20px;color: #FF0000;'>商品添加失败</font>"
					+ "</p>"
					+ "<p style='width: 70%;margin: 30px auto;font-size:16px;text-align: center;height: 50px;line-height: 50px;'>"
					+ "如果需要重新添加"
					+ oneGc.getName()
					+ ">"
					+ twoGc.getName()
					+ "分类下的商品，请<a href='goods?m=addBefore&oneGid="
					+ oneGid
					+ "&twoGid="
					+ twoGid
					+ "' style='color: #1E90FF;font-size: 16px;'>点击继续</a>！"
					+ "</p>";
			request.setAttribute("mess", mess);
			return "admin/result.jsp";
		}

		// 将商品标签保存到数据库
		if (tags != null && tags.length > 0) {
			int[] tids = new int[tags.length];
			for (int i = 0; i < tags.length; i++) {
				tids[i] = Integer.valueOf(tags[i]);
			}
			boolean rel2 = ((GoodsServiceImpl) service).addGoodsTag(
					goods.getGoodsNo(), tids);
		}

		// 添加成功
		String mess = "<p style='height: 200px;line-height: 200px;text-align: center;width: 70%;margin: 0px auto;border-bottom: 1px dashed #888888;'>"
				+ "<i class='layui-icon layui-icon-ok' style='font-size:40px;color: green;font-weight: bold;'></i>"
				+ "<font style='font-size: 30px;margin: 0px 20px;color: green;'>商品添加成功</font>"
				+ "</p>"
				+ "<p style='width: 70%;margin: 30px auto;font-size:16px;text-align: center;height: 50px;line-height: 50px;'>"
				+ "如果需要继续添加"
				+ oneGc.getName()
				+ ">"
				+ twoGc.getName()
				+ "分类下的商品，请<a href='goods?m=addBefore&oneGid="
				+ oneGid
				+ "&twoGid="
				+ twoGid
				+ "' style='color: #1E90FF;font-size: 16px;'>点击继续</a>！"
				+ "</p>"
				+ "<a class='layui-btn layui-btn-danger' href='u?m=goodsDetails&no="
				+ goods.getGoodsNo()
				+ "' target='_blank' style='display: block;width:260px;margin: 10px auto;'>查看商品详情</a>";

		request.setAttribute("mess", mess);
		return "admin/result.jsp";
	}

	/**
	 * 查询所有商品
	 */
	public String queryAll(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// 接收商品搜索的条件
		GoodsSearchBean gs = BeanUtil.getObject(request.getParameterMap(),
				GoodsSearchBean.class);
		// 接收在售或下架商品查询条件
		String status = request.getParameter("status");
		if (status != null && !status.trim().equals("")) {
			gs.setStatus(Integer.valueOf(status));
			request.setAttribute("status", status);// 向客户端发送
		}
		// 接收推荐商品查询条件
		String tuijian = request.getParameter("tuijian");
		if (tuijian != null && !tuijian.trim().equals("")) {
			gs.setTuijian(Integer.valueOf(tuijian));
			request.setAttribute("tuijian", tuijian);
		}

		// 接收要查询的页码
		String dqy = request.getParameter("dqy");
		int pc;
		if (dqy == null || dqy.trim().equals("")) {
			pc = 1;
		} else {
			pc = Integer.valueOf(dqy);
		}

		// 分页查询所有商品
		PageBean<Goods> pb = ((GoodsServiceImpl) service).search(gs, pc, 10);
		request.setAttribute("pb", pb);
		request.setAttribute("gs", gs);
		return "admin/findgoods_all.jsp";
	}

	/**
	 * 修改前查询商品
	 */
	public String updateBefore(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String goodsNo = request.getParameter("goodsNo");

		// 查询商品对象
		Goods goods = service.queryByNo(goodsNo);
		request.setAttribute("goods", goods);

		String[] images = goods.getImages().split("<>");
		for (int i = 0; i < images.length; i++) {
			request.setAttribute("img" + (i + 2), images[i]);
		}

		return "admin/update_goods.jsp";
	}

	/**
	 * 根据商品查询标签
	 */
	public String findTagByGoods(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String goodsNo = request.getParameter("goodsNo");
		int twoGid = Integer.valueOf(request.getParameter("twoGid"));
		// 该商品选中的所有标签
		TagServiceImpl tagService = new TagServiceImpl();
		List<Tag> goodsTags = new TagServiceImpl().findByGoodsNo(goodsNo);
		List<Integer> tids = new ArrayList<Integer>();
		for (Tag tag : goodsTags) {
			tids.add(tag.getId());
		}
		// 查询指定分类下的所有标签
		List<Tag> tags = tagService.queryByGid(twoGid);

		// 封装json对象
		JsonObject jo = new JsonObject();
		JsonArray dataArr = new JsonArray();

		if (tags == null || tags.size() <= 0) {
			// 没有查询到该分类下的标签
			jo.addProperty("result", 0);
			jo.add("data", dataArr);
			response.getWriter().print(jo.toString());
			return null;
		}

		// 50个标签 选中30个
		for (Tag tag : tags) {
			JsonObject tagObj = new JsonObject();
			tagObj.addProperty("id", tag.getId());
			tagObj.addProperty("name", tag.getTagName());

			// 方案一
			// boolean b = false;
			// for (Tag tag2 : goodsTags) {
			//
			// if(tag2.getId()==tag.getId()){
			// b=true;
			// break;
			// }
			// }
			//
			// if(b){
			// tagObj.addProperty("sel", 1);
			// }else{
			// tagObj.addProperty("sel", 2);
			// }

			// 方案二
			boolean b = tids.contains(tag.getId());
			if (b) {
				tagObj.addProperty("sel", 1);
			} else {
				tagObj.addProperty("sel", 2);
			}

			dataArr.add(tagObj);
		}

		jo.addProperty("result", 1);
		jo.add("data", dataArr);
		response.getWriter().print(jo.toString());
		return null;
	}

	/**
	 * 修改商品
	 */
	public String update(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 接收参数
		String[] tags = request.getParameterValues("tag");
		int oneGid = Integer.valueOf(request.getParameter("oneGid"));
		int twoGid = Integer.valueOf(request.getParameter("twoGid"));
		String[] images = request.getParameterValues("images");
		String details = request.getParameter("container");//富文本编辑器中的详情

		Goods goods = BeanUtil
				.getObject(request.getParameterMap(), Goods.class);
		// 添加分类对象
		Gclass oneGc = new GclassServiceImpl().queryById(oneGid);
		goods.setOneGc(oneGc);
		Gclass twoGc = new GclassServiceImpl().queryById(twoGid);
		goods.setTwoGc(twoGc);
		// 添加图片
		String imgs = "";
		if (images != null && images.length > 0) {
			for (int i = 0; i < images.length; i++) {
				if (!images[i].trim().equals("")) {
					imgs += (i == images.length - 1) ? images[i] : images[i]
							+ "<>";
				}
			}
		}
		goods.setImages(imgs);
		// 添加详情
		goods.setDetails(details);

		// 将修改后的商品对象存到数据库
		boolean rel = service.update(goods);

		if (!rel) {
			// 修改失败
			String mess = "<p style='height: 200px;line-height: 200px;text-align: center;width: 70%;margin: 0px auto;border-bottom: 1px dashed #888888;'>"
					+ "<i class='layui-icon layui-icon-close' style='font-size:40px;color: #FF0000;font-weight: bold;'></i>"
					+ "<font style='font-size: 30px;margin: 0px 20px;color: #FF0000;'>商品修改失败</font>"
					+ "</p>";
			request.setAttribute("mess", mess);
			return "admin/result.jsp";
		}

		//删除原有的标签
		boolean b = ((GoodsServiceImpl) service).deleteByGoodsNo(goods.getGoodsNo());
		
		if(!b){
			// 修改失败
			String mess = "<p style='height: 200px;line-height: 200px;text-align: center;width: 70%;margin: 0px auto;border-bottom: 1px dashed #888888;'>"
						+ "<i class='layui-icon layui-icon-close' style='font-size:40px;color: #FF0000;font-weight: bold;'></i>"
						+ "<font style='font-size: 30px;margin: 0px 20px;color: #FF0000;'>标签修改失败</font>"
						+ "</p>";
			request.setAttribute("mess", mess);
			return "admin/result.jsp";
		}
		
		// 将商品标签保存到数据库
		if (tags != null && tags.length > 0) {
			int[] tids = new int[tags.length];
			for (int i = 0; i < tags.length; i++) {
				tids[i] = Integer.valueOf(tags[i]);
			}
			boolean rel2 = ((GoodsServiceImpl) service).addGoodsTag(goods.getGoodsNo(),tids);
		}

		// 修改成功
		String mess = "<p style='height: 200px;line-height: 200px;text-align: center;width: 70%;margin: 0px auto;border-bottom: 1px dashed #888888;'>"
				+ "<i class='layui-icon layui-icon-ok' style='font-size:40px;color: green;font-weight: bold;'></i>"
				+ "<font style='font-size: 30px;margin: 0px 20px;color: green;'>商品修改成功</font>"
				+ "</p>"
				+ "<a class='layui-btn layui-btn-danger' href='u?m=goodsDetails&no="
				+ goods.getGoodsNo()
				+ "' target='_blank' style='display: block;width:260px;margin: 10px auto;'>查看商品详情</a>";

		request.setAttribute("mess", mess);
		return "admin/result.jsp";
	}

	/**
	 * 在商品查询页面中的商品操作
	 * 删除、设置上下架、设置推荐等操作
	 */
	public String set(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		//接收修改的参数
		int id = Integer.valueOf(request.getParameter("id"));
		String delete = request.getParameter("delete");
		if(delete!=null && delete.equals("1")){
			//删除商品
			boolean rel = service.delete(id);
			if(rel){
				//删除成功
				response.getWriter().print(1);
			}else{
				//删除失败
				response.getWriter().print(0);
			}
			return null;
		}
		
		String setStatus = request.getParameter("setStatus");
		String setTuijian = request.getParameter("setTuijian");
		
		//修改商品内容
		GoodsServiceImpl gService = (GoodsServiceImpl) service;
		if(setStatus!=null && !setStatus.equals("")){
			//修改上下架状态
			gService.setStatus(Integer.valueOf(setStatus), id);
		}
		
		if(setTuijian!=null && !setTuijian.equals("")){
			//修改推荐状态
			gService.setTuijian(Integer.valueOf(setTuijian), id);
		}
		
		//接收查询的条件参数
		String dqy = request.getParameter("dqy");
		// 接收商品搜索的条件
		GoodsSearchBean gs = BeanUtil.getObject(request.getParameterMap(),GoodsSearchBean.class);
		//拼接跳转的URL参数
		String urlParams = "&title=" + gs.getTitle() +
				"&goodsNo=" + gs.getGoodsNo() +
				"&oneGid=" + gs.getOneGid() +
				"&twoGid=" + gs.getTwoGid() +
				"&tid=" + gs.getTid() +
				"&priceSort=" + gs.getPriceSort() +
				"&stockSort=" + gs.getStockSort() +
				"&salSort=" + gs.getSalSort() +
				"&status=" + gs.getStatus() +
				"&tuijian=" + gs.getTuijian() +
				"&dqy=" + dqy;
		
		return "goods?m=queryAll"+urlParams;
	}
	
}
