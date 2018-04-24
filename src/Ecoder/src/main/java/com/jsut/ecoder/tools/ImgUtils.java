package com.jsut.ecoder.tools;

import org.springframework.stereotype.Component;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.imageio.ImageIO;
@Component
public class ImgUtils {
	private int width = 100,height = 40,number = 5;
	private String[] fontName = {"宋体","华文楷体","微软雅黑","黑体","楷体_GB2312"};
	private char[] symbol = {'+','-','X'};
	private String text;
	private BufferedImage img = null;
	private Random random = new Random();
	/*
	 * 动态生成图片	
	 */
	
	private Color randomColor(){
		int r = random.nextInt(150),g = random.nextInt(150),b = random.nextInt(150);
		
		return new Color(r,g,b);
	}
	private Font randomFont(){
		String name = fontName[random.nextInt(fontName.length)];
		int style = random.nextInt(4);
		int size = 16;
		return new Font(name,style,size);
	}
	private void drawLine(){
		int num = 3;
		Graphics2D ph = (Graphics2D)img.getGraphics();
		for(int i = 0;i<num;i++){
			int x1 = random.nextInt(width);
			int x2 = random.nextInt(width);
			int y1 = random.nextInt(height);
			int y2 = random.nextInt(height);
			ph.setColor(randomColor());
			ph.setStroke(new BasicStroke(1.5f));//设置指定宽度线
			ph.drawLine(x1, y1, x2, y2);
		}
	}
	private void drawbgcolor(BufferedImage img){
		Graphics2D gra = (Graphics2D)img.getGraphics();
		gra.setColor(Color.WHITE);
		gra.fillRect(0, 0, width, height);
	}
	private int randomNum(){
		return random.nextInt(100);
	}

	private char randomSymbol(){
		return symbol[random.nextInt(symbol.length)];
	}

	private void drawSymbol(Graphics2D gra,String symbol,int index){
		float x = 1.0f * index * width / number;//每写一个字母，需要后移一段距离
		gra.setFont(randomFont());
		gra.setColor(randomColor());
		gra.drawString(symbol, x, height-15);//高度保持不变，只变水平位置
	}


	public ImgUtils drawImg(){
		img = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
		Graphics2D gra = (Graphics2D)img.getGraphics();
		drawbgcolor(img);
		text="";

		int num1 = randomNum();
		int num2 = randomNum();
		char symbol = randomSymbol();
		if(symbol == 'X')
			text = (num1 * num2) + "";
		else if(symbol == '+')
			text = (num1 + num2) + "";
		else
			text = (num1 - num2) + "";
		String[] str = {num1+"",symbol+"",num2+"","=","?"};
		for(int i = 0;i < str.length;i++)
			drawSymbol(gra,str[i],i);
		drawLine();
		return this;
	}
	public String getText(){
		return text;
	}
	public ImgUtils output(OutputStream out){
		try {
			ImageIO.write(img, "jpeg", out);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this;
	}
}
