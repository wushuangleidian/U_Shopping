package cn.uaigou.utils;

import java.io.UnsupportedEncodingException;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

/**
 * 系统发送邮件的工具类
 * @author Administrator
 *
 */
public class MailSendUtil {

	private String from;//发件人邮箱地址
	private String password;//发件人邮箱登录密码
	private String host;//SMTP主机
	
	/**
	 * 构造方法，用于加载邮件发送的基本信息
	 */
	public MailSendUtil(){
		Properties prop = PropertiesUtils.getProp("/web.properties");
		this.from = prop.getProperty("emailFrom");
		this.password = prop.getProperty("emailPassword");
		this.host = prop.getProperty("emailHost");
	}
	
	/**
	 * 发送一份HTML的邮件
	 * @param to 收件人
	 * @param title 邮件主题
	 * @param content 邮件内容
	 * @return 发送成功返回true
	 */
	public boolean sendHtmlMail(String to,String title,String content){
		
		final String userName = from;
		final String pwd = password;
		
		try {
			
			//1、创建配置信息对象
			Properties props = new Properties();
			props.setProperty("mail.smtp.host", host);
			props.put("mail.smtp.auth", "true");//配置邮箱服务器的认证
			
			//2、获取连接邮箱服务器的session对象，设置发件人的邮箱账号和密码
			Session session = Session.getDefaultInstance(props, new Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(userName, pwd);
				}
				
			});
			
			//3、封装邮件信息对象
			MimeMessage mess = new MimeMessage(session);
			
			//设置发件人地址
			//设置自定义发件人昵称  
	        String nick="";  
	        try {  
	            nick= MimeUtility.encodeText("优爱购官网");  
	        } catch (UnsupportedEncodingException e) {  
	            e.printStackTrace();  
	        }   
	        mess.setFrom(new InternetAddress(nick+" <"+from+">")); 
			//mess.setFrom(new InternetAddress(from));
	        
			//设置收件人地址，同时发多人
			mess.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(to));
			//设置主题
			mess.setSubject(title);
			//设置邮件内容，发送文本邮件
			//mess.setText("恭喜您注册成功");
			//设置邮件内容，发送HTML格式的邮件
			mess.setContent(content, "text/html;charset=utf-8");
			
			
			//发送邮件
			Transport.send(mess);
			
			return true;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("邮件发送异常，发送失败！");
		}
		
	}
	
	/**
	 * 发送带附件的邮件
	 * @param to 收件人
	 * @param title 邮件主题
	 * @param content 邮件内容
	 * @param filePath 附件文件名称
	 * @return 发送成功返回true
	 */
	public boolean sendMultipartMail(String to,String title,String content,String filePath){
		
		final String userName = from;
		final String pwd = password;
		
		try {
			
			//1、创建配置信息对象
			Properties props = new Properties();
			props.setProperty("mail.smtp.host", host);
			props.put("mail.smtp.auth", "true");//配置邮箱服务器的认证
			
			//2、获取连接邮箱服务器的session对象，设置发件人的邮箱账号和密码
			Session session = Session.getDefaultInstance(props, new Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(userName, pwd);
				}
				
			});
			
			//3、封装邮件信息对象
			MimeMessage mess = new MimeMessage(session);
			
			//设置发件人地址
			mess.setFrom(new InternetAddress(from));
			//设置收件人地址，同时发多人
			mess.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(to));
			//设置主题
			mess.setSubject(title);
			//设置邮件内容，发送文本邮件
			//mess.setText("恭喜您注册成功");
			//设置邮件内容，发送HTML格式的邮件
			mess.setContent(content, "text/html;charset=utf-8");
			
			// 创建消息部分
	         BodyPart messageBodyPart = new MimeBodyPart();
	 
	         // 消息
	         messageBodyPart.setText("JDBC学习笔记");
	        
	         // 创建多重消息
	         Multipart multipart = new MimeMultipart();
	 
	         // 设置文本消息部分
	         multipart.addBodyPart(messageBodyPart);
	 
	         // 附件部分
	         messageBodyPart = new MimeBodyPart();
	        // String filename = "E:\\JDBC学习笔记.docx";
	         DataSource source = new FileDataSource(filePath);
	         
	         messageBodyPart.setDataHandler(new DataHandler(source));
	        // messageBodyPart.setFileName(filename);
	         messageBodyPart.setFileName(MimeUtility.encodeWord(filePath));
	         
	         multipart.addBodyPart(messageBodyPart);
	 
	         // 发送完整消息
	         mess.setContent(multipart );
			
			//发送邮件
			Transport.send(mess);
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("邮件发送异常，发送失败！");
		}
		
	}
	
	
}
