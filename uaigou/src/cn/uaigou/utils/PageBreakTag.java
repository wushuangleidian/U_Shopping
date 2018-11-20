package cn.uaigou.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * 实现分页的标签类
 * @author Administrator
 *
 */
public class PageBreakTag extends TagSupport {
	
	private int currentPage;
	private int totalPage;
	private String url;
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public int doStartTag() throws JspException {
		
		JspWriter out = pageContext.getOut();
		//获取当前项目的路径
		HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
		String path = request.getContextPath();
		
		try {
			
			out.print("<span class='my-fenye-mess'>当前第"+currentPage+"页/共"+totalPage+"页\t</span>");
			/*
			 * 首页
			 */
			if(totalPage>1){
				out.print("<a href='"+path+"/"+url+"="+1+"' class='my-fenye my-fenye-start'>首页</a>\t");
			}
			/*
			 * 上一页
			 */
			if(currentPage>1){
				out.print("<a href='"+path+"/"+url+"="+(currentPage-1)+"' class='my-fenye my-fenye-pgup'>上一页</a>\t");
			}else{
				out.print("\t<a href='javascript:;' class='my-fenye my-fenye-pgup my-fenye-disable'>上一页</a>\t");
			}
			
			
			/*
			 * 页码
			 */
			int begin = 0;
			int end = 0;
			//当总页数不超过10页
			if(totalPage<=10){
				begin = 1;
				end = totalPage;
			}else if(totalPage > 10){
				//总页数大于10页
				begin = currentPage - 5;
				end = currentPage + 4;
				if(begin<1){
					//头溢出
					begin = 1;
					end = 10;
				}else if(end > totalPage){
					//尾溢出
					begin = totalPage - 9;
					end = totalPage;
				}
				
			}
			//循环页码
			for (int i = begin; i <= end; i++) {
				if(i==currentPage){
					//当页码为当前页时
					out.print("<a href='javascript:;' class='my-fenye my-fenye-pageNum my-fenye-this'>"+i+"</a>");
				}else{
					//其他页码
					out.print("<a href='"+path+"/"+url+"="+i+"' class='my-fenye my-fenye-pageNum'>"+i+"</a>");
				}
			}
			
			
			/*
			 * 下一页
			 */
			if(currentPage<totalPage){
				out.print("\t<a href='"+path+"/"+url+"="+(currentPage+1)+"' class='my-fenye my-fenye-pgdn'>下一页</a>\t");
			}else{
				out.print("\t<a href='javascript:;' class='my-fenye my-fenye-pgdn my-fenye-disable'>下一页</a>\t");
			}
			/*
			 * 尾页
			 */
			if(totalPage>1){
				out.print("<a href='"+path+"/"+url+"="+totalPage+"' class='my-fenye my-fenye-end'>尾页</a>");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return super.doStartTag();
	}
	
}
