package cn.uaigou.servlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.uaigou.entity.Gclass;
import cn.uaigou.entity.PageBean;
import cn.uaigou.entity.Tag;
import cn.uaigou.factory.BeanBuilderFactory;
import cn.uaigou.service.IService;
import cn.uaigou.serviceimpl.GclassServiceImpl;
import cn.uaigou.serviceimpl.TagServiceImpl;
import cn.uaigou.utils.BaseServlet;
import cn.uaigou.utils.BeanUtil;

@WebServlet("/tag")
public class TagServlet extends BaseServlet{

	private IService<Tag> service = BeanBuilderFactory.serviceBuiler("cn.uaigou.serviceimpl.TagServiceImpl");
			
	/**
	 * 分页查询所有标签
	 */
	public String queryAll(HttpServletRequest request,HttpServletResponse response)throws Exception{
		
		//分页对象
		PageBean<Tag> pb = null;
		
		//接收一级分类的ID
		String OneGid = request.getParameter("oneGid");
		int oneGid = 0;
		if(OneGid!=null && !OneGid.trim().equals("") && OneGid.matches("^[1-9]+$")){
			oneGid = Integer.valueOf(OneGid);
		}
		//接收要查询的页码
		String dqy = request.getParameter("dqy");
		int pc ;
		if(dqy==null || dqy.trim().equals("")){
			pc = 1;
		}else{
			pc = Integer.valueOf(dqy);
		}
		//接收二级分类的ID
		String twoGid = request.getParameter("twoGid");
		
		if(twoGid!=null && !twoGid.trim().equals("") && twoGid.matches("^[1-9]+$")){
			int gid = Integer.valueOf(twoGid);
			//根据二级分类id查询标签
			pb = ((TagServiceImpl)service).showAll(gid,pc);
			
			//查询标签所在分类
			Gclass oneGc =new GclassServiceImpl().queryById(oneGid);
			Gclass twoGc = new GclassServiceImpl().queryById(gid);
			
			request.setAttribute("oneGc", oneGc);
			request.setAttribute("twoGc", twoGc);
		}else{
			//没有分类的id，查询所有
			pb = ((TagServiceImpl)service).findAllByPage(pc, 10);
		}
		
		request.setAttribute("pb", pb);
		
		return "admin/show_tag.jsp";
	}
	
	/**
	 * 查询所有标签，不分页
	 */
	public String findAll(HttpServletRequest request,HttpServletResponse response)throws Exception{
		
		return null;
	}
	
	/**
	 * 根据ID查询标签
	 * 用于修改前查询
	 */
	public String findById(HttpServletRequest request,HttpServletResponse response)throws Exception{
		int id = Integer.valueOf(request.getParameter("id"));
		String dqy = request.getParameter("dqy");
		String oneGid = request.getParameter("oneGid");
		String twoGid = request.getParameter("twoGid");
		
		//查询标签对象
		Tag tag = service.queryById(id);
		
		System.out.println(tag+","+tag.getOneGc()+","+tag.getTwoGc());
		
		request.setAttribute("tag", tag);
		request.setAttribute("dqy", dqy);
		request.setAttribute("oneGid", oneGid);
		request.setAttribute("twoGid", twoGid);
		
		return "admin/update_Tag.jsp";
	}
	
	/**
	 * 添加标签
	 */
	public String add(HttpServletRequest request,HttpServletResponse response)throws Exception{
		int oneGid = Integer.valueOf(request.getParameter("oneGid"));
		int twoGid = Integer.valueOf(request.getParameter("twoGid"));
		String tagName = request.getParameter("tagName");
		int tj = Integer.valueOf(request.getParameter("tj"));
		
		Tag tag = new Tag();
		tag.setTagName(tagName);
		tag.setTj(tj);
		
		tag.setOneGc(new Gclass(oneGid));
		tag.setTwoGc(new Gclass(twoGid));
		
		
		//添加到数据库
		boolean rel = service.add(tag);
		if(rel){
			response.getWriter().print(1);
		}else{
			response.getWriter().print(0);
		}
		return null;
	}
	
	/**
	 * 修改标签
	 */
	public String update(HttpServletRequest request,HttpServletResponse response)throws Exception{
		
		int oneGid = Integer.valueOf(request.getParameter("oneGid"));
		int twoGid = Integer.valueOf(request.getParameter("twoGid"));
		
		Tag tag = BeanUtil.getObject(request.getParameterMap(), Tag.class);
		
		tag.setOneGc(new Gclass(oneGid));
		tag.setTwoGc(new Gclass(twoGid));
		
		//修改数据
		boolean rel = service.update(tag);
		if(rel){
			response.getWriter().print(1);
		}else{
			response.getWriter().print(0);
		}
		return null;
	}
	
	/**
	 * 修改推荐或取消
	 */
	public String changeTj(HttpServletRequest request,HttpServletResponse response)throws Exception{
		
		int id = Integer.valueOf(request.getParameter("id"));
		int tj = Integer.valueOf(request.getParameter("tj"));
		String dqy = request.getParameter("dqy");//接收要查询的页码
		
		String oneGid = request.getParameter("oneGid");
		String twoGid = request.getParameter("twoGid");
		
		//查询Tag
		Tag tag = service.queryById(id);
		tag.setTj(tj);
		//修改Tag
		boolean rel = service.update(tag);
		if(rel){
			request.setAttribute("mess", "<font color='green'>修改成功</font>");
		}else{
			request.setAttribute("mess", "<font color='red'>修改失败</font>");
		}
		
		return "tag?m=queryAll&dqy="+dqy+"&oneGid="+oneGid+"&twoGid="+twoGid;
	}
	
	/**
	 * 删除标签
	 * 
	 */
	public String delete(HttpServletRequest request,HttpServletResponse response)throws Exception{
		int id = Integer.valueOf(request.getParameter("id"));
		String oneGid = request.getParameter("oneGid");
		String twoGid = request.getParameter("twoGid");
		
		boolean rel = service.delete(id);
		if(rel){
			request.setAttribute("mess", "<font color='green'>删除成功</font>");
		}else{
			request.setAttribute("mess", "<font color='red'>删除失败</font>");
		}
		return "tag?m=queryAll&dqy="+1+"&oneGid="+oneGid+"&twoGid="+twoGid;
	}
}
