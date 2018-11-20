package cn.uaigou.servlet;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.google.gson.JsonObject;

@WebServlet("/uploadImg")
public class UploadImgServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req,resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	
		req.setCharacterEncoding("utf-8");
		resp.setContentType("text/html;charset=utf-8");
		
		//获取系统中图片存储的路径
		
		String url = "img/goodsImg/";
		
		String path = getServletContext().getRealPath("/")+url;
		
		DiskFileItemFactory factory = new DiskFileItemFactory();
		
		ServletFileUpload sfu = new ServletFileUpload(factory);
		
		try {
			List<FileItem> fileItemList = sfu.parseRequest(req);
			
			FileItem fileItem = fileItemList.get(0);
			String fileName = new Date().getTime()+"_"+fileItem.getName();
			
			File file = new File(path+fileName);
			
			fileItem.write(file);
			
			//封装json数据
			JsonObject jo = new JsonObject();
			jo.addProperty("code", 0);
			jo.addProperty("msg", "图片上传失败");
			
			JsonObject data = new JsonObject();
			data.addProperty("src", url+fileName);
			data.addProperty("title", fileName);
			
			jo.add("data", data);
			
			resp.getWriter().print(jo.toString());
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
