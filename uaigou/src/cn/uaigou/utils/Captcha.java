package cn.uaigou.utils;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;

/**
 * 验证码创建工具类
 * @author Administrator
 *
 */
public class Captcha {

	private static Captcha captcha = new Captcha();
	private Captcha(){
		
	}
	
	/**
	 * 获取当前类对象的方法
	 */
	public static Captcha getNewIntence(){
		return captcha;
	}
	
	/**
	 * 生成字母+数字的验证码
	 * @param response HttpServletResponse对象
	 * @param width 生成验证码的宽度
	 * @param height 生成验证码的高度
	 * @param fontSize 生成验证码中字体的大小
	 * @return 返回生成的验证码字符串
	 */
	public String defaultImage(HttpServletResponse response,int width,int height,int fontSize){
		
		Random random = new Random();
		//获取随机字符串
		String code = randomStr(4);
		
        //简历bufferedImage对象，制定图片的长度和宽度以及色彩
        BufferedImage bi = new BufferedImage(width,height,BufferedImage.TYPE_3BYTE_BGR);
        //3:获取到 Graphics2D 绘制对象，开始绘制验证码
        Graphics2D g = bi.createGraphics();
        //4:设置文字的字体和大小
        Font font = new Font("微软雅黑",Font.PLAIN,fontSize);
        //设置字体
        g.setFont(font);
        //设置随机颜色
        g.setColor(new Color(20+random.nextInt(200),20+random.nextInt(200),20+random.nextInt(200)));
        //设置背景
        g.setColor(getRandomColor(240,250));
        //开始绘制
        g.fillRect(0,0,width,height);
        //干扰线的绘制，绘制线条到图片中
        g.setColor(getRandomColor(180,230));
        for(int i=0;i<10;i++){
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int x1 = random.nextInt(60);
            int y1 = random.nextInt(60);
            g.drawLine(x,y,x1,y1);
        }
        //绘制形状，获取矩形对象
        FontRenderContext context = g.getFontRenderContext();
        Rectangle2D bounds = font.getStringBounds(code,context);
        //计算文件的坐标和间距
        double x = (width - bounds.getWidth())/2;
        double y = (height - bounds.getHeight())/2;
        double ascent = bounds.getY();
        double baseY = y - ascent;
        g.drawString(code,(int)x,(int)baseY);
        //结束绘制
        g.dispose();
        try {
            ImageIO.write(bi,"jpg",response.getOutputStream());
            //刷新响应流
            response.flushBuffer();
        }catch(Exception ex){
            ex.printStackTrace();
        }

        return code;
	}
	
	/**
	 * 生成算数验证码
	* @param response HttpServletResponse对象
	 * @param width 生成验证码的宽度
	 * @param height 生成验证码的高度
	 * @param fontSize 生成验证码中字体的大小
	 * @return 返回验证码中算术运算结果的String类型
	 */
    public String mathCaptchaImage(HttpServletResponse response,int width,int height,int fontSize){
        //在内存中创建图片
        BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        //创建图片的上下文
        Graphics2D g = image.createGraphics();
        //产生随机对象,此随机对象主要用于算术表达式的数字
        Random random = new Random();
        //设置背景
        g.setColor(getRandomColor(240,250));
        //设置字体
        g.setFont(new Font("微软雅黑", Font.PLAIN,fontSize));
        //开始绘制
        g.fillRect(0,0,width,height);

        //干扰线的绘制，绘制线条到图片中
        g.setColor(getRandomColor(180,230));
        for(int i=0;i<10;i++){
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int x1 = random.nextInt(60);
            int y1 = random.nextInt(60);
            g.drawLine(x,y,x1,y1);
        }

        //开始进行对算术验证码表达式的拼接
        int num1 = (int)(Math.random()*10 + 1);
        int num2 = (int)(Math.random()*10 + 1);
        int fuhao = random.nextInt(3);//产生一个[0,2]之间的随机整数
        //记录符号
        String fuhaostr = null;
        int result = 0;
        switch (fuhao){
            case 0 : fuhaostr = "+";result = num1 + num2;break;
            case 1: fuhaostr = "-";result = num1 - num2;break;
            case 2 : fuhaostr = "*";result = num1 * num2;break;
            //case 3 : fuhaostr = "/";result = num1 / num2;break;
        }
        //拼接算术表达式,用户图片显示。
        String calc = num1 + " " + fuhaostr +" "+ num2 +" = ?";
        //设置随机颜色
        g.setColor(new Color(20+random.nextInt(110),20+random.nextInt(110),20+random.nextInt(110)));
        //绘制表达式
        g.drawString(calc,5,25);
        //结束绘制
        g.dispose();
        try {
            //输出图片到页面
            ImageIO.write(image,"JPEG",response.getOutputStream());
            return String.valueOf(result);
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }
	
	/**
     * 范围随机颜色
     * @param fc
     * @param bc
     * @return
     */
    private Color getRandomColor(int fc,int bc){
        //利用随机数
        Random random  = new Random();
        //随机颜色,了解颜色-Color(red,green,blue).rgb三元色 0-255
        if(fc>255)fc = 255;
        if(bc>255)bc = 255;
        int r = fc+random.nextInt(bc-fc);
        int g = fc+random.nextInt(bc-fc);
        int b = fc+random.nextInt(bc-fc);
        return new Color(r,g,b);
    }
	
	
	/**
	 * 获取随机字符串的方法
	 * @param len 生成字符串的长度
	 * @return 返回随机字符串
	 */
	public String randomStr(int len){
		//创建源字符串
		String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		
		//随机拼接字符，创建随机字符串
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<len;i++){
			int ranNum = new Random().nextInt(str.length());
			sb.append(str.charAt(ranNum));
		}
		
		return sb.toString();
	}
	
}
