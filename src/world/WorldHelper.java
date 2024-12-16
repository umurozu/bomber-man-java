package world;

import java.awt.Graphics2D;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;

import main.GamePanel;
import skill.Skill;

public class WorldHelper {
	
	public int doorX, doorY, skillX, skillY;
	public Skill skill;
	public Skill door;
	GamePanel gp;
	public World[] world;
	public int [] [] mapCodes = {
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
			{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
			{1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1},
			{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
			{1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1},
			{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
			{1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1},
			{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
			{1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1},
			{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
			{1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1},
			{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
			{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}
			};
	
	public WorldHelper(GamePanel gp) {
		
		this.gp = gp;
		
		world = new World[10];
		
		getWorldImage();
		
		makeMap(this.mapCodes);
		
		setDoorAndSkill(this.mapCodes);
			
		
	}
	
	public void checkSkillUnlocked() {
		
		if(mapCodes[skillY][skillX] == 0 && skill.exploded == false && skill.type != 3) {
			//System.out.println("?");
			mapCodes[skillY][skillX] = 5;
			skill.type = 2;
		}
		
		
		if(mapCodes[doorY][doorX] == 0 && door.exploded == false && door.type != 3) {
			System.out.println("?");
			mapCodes[doorY][doorX] = 6;
			door.type = 2;
		}
		
	}
	
	public void checkSkillTaken() {
		//System.out.println("abi skill niye yok olmuyor");
		System.out.println(skill.type);
		if(mapCodes[skillY][skillX] == 5 && skill.exploded == false && skill.type == 3) {
			
			mapCodes[skillY][skillX] = 0;
		}
		if(mapCodes[doorY][doorX] == 6 && door.exploded == false && door.type == 3) {
			mapCodes[doorY][doorX] = 0;
		}
	}
	
	
	
	public void getWorldImage() {
		
		try {
			
			world[0] = new World();
			world[0].image = ImageIO.read(getClass().getResourceAsStream("/world/grass01.png"));
			
			world[1] = new World();
			world[1].image = ImageIO.read(getClass().getResourceAsStream("/world/wall01.png"));
			world[1].collision = true;
			
			
			world[2] = new World();
			world[2].image = ImageIO.read(getClass().getResourceAsStream("/world/destructivestone.png"));
			world[2].collision = true;
			world[2].destructible = true;

			world[4] = new World();
			world[4].image = ImageIO.read(getClass().getResourceAsStream("/world/door_block.png"));
			world[4].collision = true;
			world[4].destructible = true;
			
			
			world[6] = new World();
			world[6].image = ImageIO.read(getClass().getResourceAsStream("/world/door.png"));
			world[6].collision = false;
			world[6].destructible = false; // actually false but for spwning monster needs to see as true (TEST)
			world[6].type = 4;
			
			
			
			world[3] = new World();
			world[3].collision = true;
			world[3].destructible = true;
			
		
			if(gp.chosen == 1) world[3].image = ImageIO.read(getClass().getResourceAsStream("/world/orange_block.png"));
			if(gp.chosen == 2) world[3].image = ImageIO.read(getClass().getResourceAsStream("/world/cyan_block.png"));
			if(gp.chosen == 3) world[3].image = ImageIO.read(getClass().getResourceAsStream("/world/green_block.png"));
			if(gp.chosen == 4) world[3].image = ImageIO.read(getClass().getResourceAsStream("/world/blue_block.png"));
			if(gp.chosen == 5) world[3].image = ImageIO.read(getClass().getResourceAsStream("/world/yellow_block.png"));
			
			world[5] = new World();
			world[5].collision = true;
			world[5].destructible = true;
			world[5].type = 2;
			
			if(gp.chosen == 1) world[5].image = ImageIO.read(getClass().getResourceAsStream("/world/orange_skill.png"));
			if(gp.chosen == 2) world[5].image = ImageIO.read(getClass().getResourceAsStream("/world/cyan_skill.png"));
			if(gp.chosen == 3) world[5].image = ImageIO.read(getClass().getResourceAsStream("/world/green_skill.png"));
			if(gp.chosen == 4) world[5].image = ImageIO.read(getClass().getResourceAsStream("/world/blue_skill.png"));
			if(gp.chosen == 5) world[5].image = ImageIO.read(getClass().getResourceAsStream("/world/yellow_skill.png"));
			
		
			
			

		}catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void draw(Graphics2D g2) {
		
		
		
		int worldCol = 0;
		int worldRow = 0;
		
		
		while(worldRow < gp.maxWorldRow && worldCol < gp.maxWorldCol) {
			
			int tileNum = mapCodes[worldRow][worldCol];
			
			
			
			int worldX = worldCol * gp.tileSize;
			int worldY = worldRow * gp.tileSize;
			int screenX = worldX - gp.player.worldX + gp.player.screenX;
			int screenY = worldY - gp.player.worldY + gp.player.screenY;
			
			if(gp.player.screenX > gp.player.worldX) {
				screenX = worldX;
			}
			if(gp.player.screenY > gp.player.worldY) {
				screenY = worldY;
			}
			int rightOffset = gp.screenWidth - gp.player.screenX;
			if(rightOffset > gp.worldWidth - gp.player.worldX) {
				screenX = gp.screenWidth - (gp.worldWidth - worldX);
			}
			
			int bottomOffset = gp.screenHeight - gp.player.screenY;
			if(bottomOffset > gp.worldHeight - gp.player.worldY) {
				screenY = gp.screenHeight - (gp.worldHeight - worldY);
			}
			
			
			
			
			
		    g2.drawImage(world[tileNum].image, screenX, screenY, gp.tileSize, gp.tileSize, null);
		            
		    worldCol++;
			
			
			if(worldCol == gp.maxWorldCol) {
				
				worldCol = 0;
				worldRow++;
				
			}
		}
	}
	
	private void setDoorAndSkill(int[][] mapCodes) {
		
		
		
		 List<int[]> indices = new ArrayList<>();
	        
		 for (int i = 0; i < mapCodes.length; i++) {
	            for (int j = 0; j < mapCodes[i].length; j++) {
	                if (mapCodes[i][j] == 2) {
	                    indices.add(new int[] { i, j });
	                }
	            }
	        }
	        
	      
		 Random rand = new Random();
	     int[] firstIndex = indices.remove(rand.nextInt(indices.size()));
	     int[] secondIndex = indices.remove(rand.nextInt(indices.size()));
	        
	     mapCodes[firstIndex[0]][firstIndex[1]] = 3;
	     doorX = secondIndex[1];
	     doorY = secondIndex[0];
	     mapCodes[secondIndex[0]][secondIndex[1]] = 4;   
	     skillX = firstIndex[1];
	     skillY = firstIndex[0];
	}

	public void makeMap(int[][] mapCodes) {
		int limit=0;
		for (int i = 0; i < mapCodes.length; i++) {
			for(int j=0;j<mapCodes[i].length;j++) {
				Random random=new Random();
				int randomInt=random.nextInt(3);
				
				if(mapCodes[i][j]==0 && randomInt==2 && limit!=60 && (i> 5 || j >5)) { // i j koşulu hatalı düzeltme yapılması lazım
					mapCodes[i][j]=2;
					limit++;
					
				}else if(limit==60) { // hatalı düzelt if in daha kapsayıcı olması lazım
					return;
				}
			}
		}	
	}

}
