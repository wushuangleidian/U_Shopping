package cn.uaigou.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;

import cn.uaigou.entity.Gclass;
import cn.uaigou.entity.Goods;
import cn.uaigou.entity.GoodsExtend;
import cn.uaigou.entity.IndexShowBean;
import cn.uaigou.serviceimpl.GclassServiceImpl;
import cn.uaigou.serviceimpl.GoodsServiceImpl;
/**
 * 用于展示首页数据的Servlet类
 * @author Administrator
 *
 */
@WebServlet("/index.html")
public class IndexServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		
		/*
		 * 查询一级分类，获取一级分类的集合
		 * 此步骤主要获取一级分类ID，用一级分类ID查询商品
		 * 商品主要展示在首页
		 */
		List<Gclass> gList = new GclassServiceImpl().queryOfIndex(4);//设置展示的分类数目
		Object[] params = new Object[gList.size()];//声明一级分类ID数组对象
		for (int i=0;i<gList.size();i++) {
			params[i] = gList.get(i).getGid();//获取一级分类id，并赋值给参数数组
		}
		
		//查询一级分类下的商品
		List<GoodsExtend> goodsList = new GoodsServiceImpl().queryOfIndex(params);
		
		//封装Javabean
		List<IndexShowBean> isbList = new ArrayList<IndexShowBean>();
		for (Gclass gc : gList) {//遍历一级分类集合
			IndexShowBean isb = new IndexShowBean();//首页每个分类下展示的商品对象，属性：Gclass gc,GoodsExtend[] ges
			isb.setGc(gc);
			GoodsExtend[] gs = new GoodsExtend[6];//设置首页每个分类下展示6个商品
			
			int j = 0;
			for(int i=0;i<goodsList.size();i++){//分别遍历查询到的一级分类下的商品
				GoodsExtend ge = goodsList.get(i);
				if(ge.getOneGid()==gc.getGid()){//把展示的商品归类
					gs[j] = ge;
					j++;
				}
			}
			isb.setGes(gs);
			
			isbList.add(isb);
		}
		
		req.setAttribute("isbList", isbList);
		req.getRequestDispatcher("jsp/index.jsp").forward(req, resp);
	}
	
	
}
