package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

import javax.imageio.ImageIO;

public class UI {
	GamePanel gp;
	Font arial_40;
	
	public boolean gameWon;
	public boolean gameLose;
	
	public double playTime;
	DecimalFormat dFormat = new DecimalFormat("#0");
	Font maruMonica;
	
	Graphics2D g2;
	
	BufferedImage image[] = new BufferedImage[5];
	
	public int commandNum;
	public boolean chooseSkill = false;
	public int titleScreenState;
	public final int startScreen = 1;
	public final int skillScreen = 2;
	public UI(GamePanel gp) {
		this.gp = gp;
		
//		arial_40 = new Font("Arial", Font.PLAIN, 40);
		playTime = 200;
		readImage();
		try {
			InputStream is = getClass().getResourceAsStream("/font/MP16REG.ttf");
			maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
		}
		catch(FontFormatException e) {
			e.printStackTrace();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void draw(Graphics2D g2) {
		this.g2 = g2;
		g2.setFont(maruMonica);
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		g2.setFont(g2.getFont().deriveFont(Font.PLAIN,40F));
		
		if(gp.gameState == gp.playState) {
			if(gameWon == true) {
				
				//g2.setFont(maruMonica);
				g2.setColor(Color.white);
				
				String text;
				int textLength;
				int x,y;
				
				
				text = "!! You Won The Game !!";
				textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
				
				x = gp.screenWidth/2 - textLength/2;
				y = gp.screenHeight/2 - (gp.tileSize * 3);
				
				g2.drawString(text, x, y);
			}
			else if(gameLose == true) {
				//g2.setFont(maruMonica);
				g2.setColor(Color.white);
				
				String text;
				int textLength;
				int x,y;
				
				
				text = "Game Over. Try Again";
				textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
				
				x = gp.screenWidth/2 - textLength/2;
				y = gp.screenHeight/2 - (gp.tileSize * 3);
				
				g2.drawString(text, x, y);
			}
			else {
				//g2.setFont(maruMonica);
				g2.setColor(Color.white);
				playTime -=  (double)1/gp.FPS;
				//System.out.println(playTime);
				g2.drawString("Tıme:" + dFormat.format(playTime), gp.tileSize * 11, 65);
			}
		}
		else if(gp.gameState == gp.titleState) {
			//g2.setFont(maruMonica);
			drawTitle();
		}
		
	
	}
	public void drawTitle() {
		
		
			g2.setFont(maruMonica);
			g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			g2.setFont(g2.getFont().deriveFont(Font.PLAIN,120F));
			g2.setColor(new Color(40,40,40));
			g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
			g2.setColor(Color.black);
			String text;
			int textLength;
			int x,y;
			
			
			text = "B   MBERMAN";
			x = gp.screenWidth/2 - textArranger(text)/2;
			y = gp.tileSize * 3;
			g2.drawString(text, x+5, y+5);
			
			g2.setColor(Color.white);
			x = gp.screenWidth/2 - textArranger(text)/2 ;
			y = gp.screenHeight/2 - (gp.tileSize * 3) ;
			g2.drawString(text, x, y);
			
			//image 
			x = gp.screenWidth/2 - gp.tileSize*6;
			y -= gp.tileSize*2 - 10;
			g2.drawImage(gp.bomb1.image, x, y, gp.tileSize*2, gp.tileSize*2, null);
			
			//menu
			g2.setFont(g2.getFont().deriveFont(Font.BOLD,48F));
			text = "NEW GAME";
			x = gp.screenWidth/4 - textArranger(text)/2;
			y += gp.tileSize*5;
			if(commandNum == 0) {
				g2.drawString(">", x - gp.tileSize, y);
			}
			
			g2.drawString(text, x, y);
			
			text = "LOAD GAME";
			y += gp.tileSize*1.5;
			g2.drawString(text, x, y);
			if(commandNum == 1) {
				g2.drawString(">", x - gp.tileSize, y);
			}
			
			g2.drawString(text, x, y);
			
			g2.setFont(g2.getFont().deriveFont(Font.BOLD,24F));
			text = "CHOSEN SKILL:";
			x = gp.screenWidth/2 - textArranger(text)/2;
			y += gp.tileSize*1.5;
			g2.drawString(text, x+gp.tileSize*4, y-gp.tileSize*4);
			
			
			
			if(gp.chosen == 1) {
				g2.drawImage(image[0], x+gp.tileSize*5+10, y-gp.tileSize*4 + 15, gp.tileSize, gp.tileSize, null);
			}
			if(gp.chosen == 2) {
				g2.drawImage(image[1], x+gp.tileSize*5+10, y-gp.tileSize*4+15, gp.tileSize, gp.tileSize, null);
			}
			if(gp.chosen == 3) {
				g2.drawImage(image[2], x+gp.tileSize*5+10, y-gp.tileSize*4+15, gp.tileSize, gp.tileSize, null);
			}
			if(gp.chosen == 4) {
				g2.drawImage(image[3], x+gp.tileSize*5+10, y-gp.tileSize*4+15, gp.tileSize, gp.tileSize, null);
			}
			if(gp.chosen == 5) {
				g2.drawImage(image[4], x+gp.tileSize*5+10, y-gp.tileSize*4+15, gp.tileSize, gp.tileSize, null);
			}
			
			
			x = gp.screenWidth/3 - textArranger(text)/2-10;
			y += gp.tileSize*1;
			
			for(int i = 0; i < image.length; i++) {
				
				g2.drawImage(image[i], x, y, gp.tileSize, gp.tileSize, null);
				x += gp.tileSize*2;
				if (commandNum == i+2) {
					g2.drawString(">", x - gp.tileSize*2-15, y +gp.tileSize-15);
				}
			}
			
			
			
		
			
		
		
		
		
	}
	
	public int textArranger(String text) {
		int textLength;
		
		textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		return textLength;
	}
	
	public void readImage() {
		try {
			image[0] = ImageIO.read(getClass().getResourceAsStream("/world/orange_skill.png"));
			image[1] = ImageIO.read(getClass().getResourceAsStream("/world/cyan_skill.png"));
			image[2] = ImageIO.read(getClass().getResourceAsStream("/world/green_skill.png"));
			image[3] = ImageIO.read(getClass().getResourceAsStream("/world/blue_skill.png"));
			image[4] = ImageIO.read(getClass().getResourceAsStream("/world/yellow_skill.png"));
		}catch(Exception e) {
			e.printStackTrace();
		}
		

	}
	
	public void gameOver() {
		
	}
	
	
}
