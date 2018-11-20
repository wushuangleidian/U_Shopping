package cn.uaigou.servlet;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import cn.uaigou.daoimpl.GoodsDaoImpl;
import cn.uaigou.entity.Gclass;
import cn.uaigou.entity.Goods;
import cn.uaigou.entity.Tag;
import cn.uaigou.factory.BeanBuilderFactory;
import cn.uaigou.service.IService;
import cn.uaigou.serviceimpl.GclassServiceImpl;
import cn.uaigou.serviceimpl.GoodsServiceImpl;
import cn.uaigou.serviceimpl.TagServiceImpl;
import cn.uaigou.utils.BaseServlet;
import cn.uaigou.utils.BeanUtil;

/**
 * 处理品类相关的Servlet类
 * 
 * @author Administrator
 * 
 */
@WebServlet("/gclass")
public class GclassServlet extends BaseServlet {

	private IService<Gclass> service = BeanBuilderFactory
			.serviceBuiler("cn.uaigou.serviceimpl.GclassServiceImpl");

	/**
	 * 查询所有分类
	 */
	public String queryAll(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		PrintWriter out = response.getWriter();

		// 从数据库查询所有商品分类
		List<Gclass> list = service.queryAll();

		// 没有查询到数据
		if (list == null || list.size() <= 0) {
			JsonObject jo = new JsonObject();
			jo.addProperty("result", 0);
			jo.addProperty("data", "null");
			out.print(jo.toString());
			return null;
		}

		// 封装json数据
		JsonObject obj = new JsonObject();// 全局Json对象
		JsonArray data = new JsonArray();// 封装data元素的value值，数组类型

		// 遍历结果集
		for (Gclass gc : list) {
			JsonObject oneObj = new JsonObject();// 一级分类对象
			JsonArray sonData = new JsonArray();// 二级分类数组

			// 封装一级分类
			if (gc.getPid() == 0) {

				oneObj.addProperty("gid", gc.getGid());
				oneObj.addProperty("name", gc.getName());
				oneObj.addProperty("pid", gc.getPid());

				// 对二级分类数组赋值
				for (Gclass sonGc : list) {
					// 封装二级分类对象
					if (sonGc.getPid() == gc.getGid()) {
						JsonObject sonObj = new JsonObject();
						sonObj.addProperty("gid", sonGc.getGid());
						sonObj.addProperty("name", sonGc.getName());
						sonObj.addProperty("pid", sonGc.getPid());
						sonObj.addProperty("sx", sonGc.getSx());
						
						//封装标签
						JsonArray tagArr = new JsonArray();
						int gid = sonGc.getGid();
						List<Tag> tagList = new TagServiceImpl().queryByGid(gid);
						for (Tag tag : tagList) {
							JsonObject tagObj = new JsonObject();
							tagObj.addProperty("id", tag.getId());
							tagObj.addProperty("tagName", tag.getTagName());
							tagObj.addProperty("tj", tag.getTj());
							tagArr.add(tagObj);
						}
						
						sonObj.add("tag", tagArr);
						// 把二级分类对象添加到二级分类数组中
						sonData.add(sonObj);
					}
				}

				oneObj.addProperty("sx", gc.getSx());
				oneObj.add("sonData", sonData);// 添加二级分类数组对象到一级分类中
				
				//查询一级分类下的热销商品
				JsonArray hotGoods = new JsonArray();
				List<Goods> hotList = new GoodsServiceImpl().queryByOneGid(gc.getGid(),0,4);
				for (Goods goods : hotList) {
					JsonObject goodsObj = new JsonObject();
					goodsObj.addProperty("id", goods.getId());
					goodsObj.addProperty("no", goods.getGoodsNo());
					goodsObj.addProperty("title", goods.getTitle());
					goodsObj.addProperty("img", goods.getFirstImg());
					hotGoods.add(goodsObj);
				}
				
				oneObj.add("hotGoods", hotGoods);//将热销商品添加到一级分类中
				// 添加一级分类对象到一级分类数组中
				data.add(oneObj);
			}

		}

		// 封装全局json对象
		obj.addProperty("result", 1);
		obj.add("data", data);

		// 向客户端响应
		out.print(obj.toString());

		return null;
	}

	/**
	 * 展示二级分类的方法
	 */
	public String showTwo(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		int pid = Integer.valueOf(request.getParameter("pid"));//要查询的二级分类的pid字段，使用一级分类id查询二级分类
		PrintWriter out = response.getWriter();

		// 查询二级分类
		List<Gclass> list = ((GclassServiceImpl) service).queryByPid(pid);

		// 没有查询到数据
		if (list == null || list.size() <= 0) {
			JsonObject jo = new JsonObject();
			jo.addProperty("result", 0);
			jo.addProperty("data", "null");
			out.print(jo.toString());
			return null;
		}
		
		JsonObject jo = new JsonObject();//全局json对象
		jo.addProperty("result", 1);
		JsonArray data = new JsonArray();
		
		for (Gclass gclass : list) {
			JsonObject obj = new JsonObject();
			obj.addProperty("gid", gclass.getGid());
			obj.addProperty("name", gclass.getName());
			obj.addProperty("pid", gclass.getPid());
			obj.addProperty("sx", gclass.getSx());
			data.add(obj);
		}
		
		jo.add("data", data);
		out.print(jo.toString());

		return null;
	}
	
	/**
	 * 添加分类
	 */
	public String add(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		Gclass gc = BeanUtil.getObject(request.getParameterMap(), Gclass.class);
		boolean b = service.add(gc);

		if (b) {
			response.getWriter().print(1);
		} else {
			response.getWriter().print(0);
		}

		return null;
	}

	/**
	 * 删除分类
	 */
	public String delete(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		int gid = Integer.valueOf(request.getParameter("id"));

		// 先查询当前分类下是否有子分类
		List<Gclass> list = ((GclassServiceImpl) service).queryByPid(gid);
		if (list == null || list.size() <= 0) {
			boolean b = service.delete(gid);

			if (b) {
				response.getWriter().print(1);
			} else {
				response.getWriter().print(0);
			}
		} else {
			response.getWriter().print(2);
		}

		return null;
	}

	/**
	 * 批量修改分类顺序
	 */
	public String setSx(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		Map<String, String[]> map = request.getParameterMap();

		String[] gidArr = map.get("gid");
		String[] sxArr = map.get("sx");

		List<Gclass> gcList = new ArrayList<Gclass>();

		for (int i = 0; i < gidArr.length; i++) {
			int gid = Integer.valueOf(gidArr[i]);
			Gclass gc = service.queryById(gid);
			int sx = Integer.valueOf(sxArr[i]);
			gc.setSx(sx);
			gcList.add(gc);
		}

		// 批量修改顺序
		int[] rels = ((GclassServiceImpl) service).batch(gcList);

		return "r:admin/show_gclass.jsp";
	}

	/**
	 * 修改分类
	 */
	public String update(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		int gid = Integer.valueOf(request.getParameter("gid"));
		String name = request.getParameter("name");

		// 查询要修改的分类对象
		Gclass gc = service.queryById(gid);
		if (gc == null) {
			response.getWriter().print(0);
			return null;
		}

		gc.setName(name);
		// 修改分类对象
		boolean rel = service.update(gc);
		if (rel) {
			response.getWriter().print(1);
		} else {
			response.getWriter().print(0);
		}

		return null;
	}

}
