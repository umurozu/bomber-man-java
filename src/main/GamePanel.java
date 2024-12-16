package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JPanel;

import bomb.Bomb;
import data.SaveLoad;
import entity.Entity;
import entity.Player;
import skill.Skill;
import world.WorldHelper;

public class GamePanel extends JPanel implements Runnable {
	
	final int originalTileSize = 16;
	final int scale = 3;
	
	public final int tileSize = originalTileSize * scale;
	public final int maxScreenCol = 16;
	public final int maxScreenRow = 12;
	public final int screenWidth = tileSize * maxScreenCol;
	public final int screenHeight = tileSize * maxScreenRow;
	
	
	public final int maxWorldCol = 33;
	public final int maxWorldRow = 13;
	public final int worldWidth = tileSize * maxWorldCol;
	public final int worldHeight = tileSize * maxWorldRow;
	public int chosen = 1; //1 orange 2 cyan 3 green 4 blue 5 yellow
	
	int test = 0;
	
	public int FPS = 30;
	public UI ui = new UI(this);
	public WorldHelper worldH = new WorldHelper(this);
	Keyboard key = new Keyboard(this);
	Thread gameThread;
	public CollisionDetector cDetec = new CollisionDetector(this);
	public AssetSetter aSetter = new AssetSetter(this, worldH);
	public SaveLoad saveLoad = new SaveLoad(this);
	
	public Player player = new Player(this, key);
	public Bomb bomb1 = new Bomb(this, key, worldH, "bomb1");
	public Bomb bomb2 = new Bomb(this, key, worldH, "bomb2");
	public Skill skill[] = new Skill[6];//includes door because same structure
	public Entity monster[] = new Entity[5]; // default 5
	public Entity bluemonster[] = new Entity[2]; // default 7
	ArrayList<Entity> entityList = new ArrayList<>();
	
	//-----------
	public int gameState;
	public final int playState = 1;
	public final int pauseState = 2;
	public final int titleState = 3;
	

	public GamePanel() {
		
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(new Color(83,139,77 ));
		this.setDoubleBuffered(true);
		this.addKeyListener(key);
		this.setFocusable(true);
		
	}
	
	public void setupGame() {
		aSetter.setSkills(chosen);
		aSetter.setMonster();
		gameState = titleState; // normally default state is title state but for testing
	}
	
	
	public void startGameThread() {
		
		gameThread = new Thread(this);
		gameThread.start();
	}

	@Override
	public void run() {
		
		double drawInterval = 1000000000/FPS;
		double nextDrawTime = System.nanoTime() + drawInterval;
		
		while(gameThread != null) {
			
			update();
			
			repaint();
			
			try {
				double remainingTime = nextDrawTime - System.nanoTime();
				remainingTime = remainingTime/1000000;
				
				if(remainingTime < 0) remainingTime = 0;
				
				Thread.sleep((long) remainingTime);
				
				nextDrawTime += drawInterval;
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	public void update() {
		
		
		
			
		
		if(gameState == playState) {
			//System.out.println(key.gameIsPaused);
			
			if(player.alive == true && ui.playTime > 0){
				
				player.update();
				
				for (int i = 0; i < monster.length; i++) {
					
					if(monster[i] != null) {
						
						monster[i].update();
					}
				}
				

				for (int i = 0; i < bluemonster.length; i++) {
					
					if(bluemonster[i] != null) {
						
						bluemonster[i].update();
					}
				}
				
				if(bomb1.active == true) {
					bomb1.update();
				}
				if(bomb2.active == true) {
					//System.out.println("bomb2");
					bomb2.update();
				}
				
			}
			else {
				ui.gameLose = true;
			}
		}
		
		if(gameState == pauseState) {
			// do not update anything
		}
		
		
	}
	
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g;
		
		//title
		if(gameState == titleState) {
			
			ui.draw(g2);
		}
		
		
		else {
			
			worldH.draw(g2);
			
			player.draw(g2);
			
			for (int i  = 0; i < monster.length; i++) {
				if(monster[i] != null) {
					entityList.add(monster[i]);
					//System.out.println(entityList.size());
				}
			}
			
			Collections.sort(entityList, new Comparator<Entity>() {
				
				public int compare(Entity e1, Entity e2) {
					int result = Integer.compare(e1.worldY, e2.worldY);
					return result;
				}
			});
			
			for (int i = 0; i < entityList.size(); i++) {
				entityList.get(i).draw(g2);
			}
			entityList.clear();
			//-----------------------------------------------------
			for (int i  = 0; i < bluemonster.length; i++) {
				if(bluemonster[i] != null) {
					entityList.add(bluemonster[i]);
					//System.out.println(entityList.size());
				}
			}
			
			Collections.sort(entityList, new Comparator<Entity>() {
				
				public int compare(Entity e1, Entity e2) {
					int result = Integer.compare(e1.worldY, e2.worldY);
					return result;
				}
			});
			
			for (int i = 0; i < entityList.size(); i++) {
				entityList.get(i).draw(g2);
			}
			entityList.clear();
			//-----------------------------------------------------
			if(bomb1.active == true) {
				bomb1.draw(g2);
			}
			if(bomb2.active == true) {
				bomb2.draw(g2);
			}
			
			ui.draw(g2);
		}
		
		

		
		g2.dispose();
		
		
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
