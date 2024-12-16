package bomb;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Entity;
import main.GamePanel;
import main.Keyboard;
import world.WorldHelper;

public class Bomb extends Entity implements Runnable {
	
    GamePanel gp;
    Keyboard key;
    WorldHelper worldH;
    
    public final int screenX;
    public final int screenY;
    public int range;
    public boolean active = false;
    public boolean remote = false; // Test Stage
    Thread thread;
	
    public boolean collision;
   
    //FOR TEST
    String name;
    
	public Bomb(GamePanel gp, Keyboard key, WorldHelper worldH, String name) {
		super(gp);
		this.gp = gp;
        this.key = key;
        this.worldH = worldH;
        this.name = name;
        screenX = gp.screenWidth/2;
        screenY = gp.screenHeight/2;
        
        this.collision = false;
        solidArea = new Rectangle(8,16,32,32);
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        alive = false;
        
        setDefaultValues();
        getPlayerImage(); 
	}

	private void getPlayerImage() {
		
        try {
			image = ImageIO.read(getClass().getResourceAsStream("/bomb/bomb1.png"));
		} catch (IOException e) {
			
			e.printStackTrace();
		}

		
	}

	private void setDefaultValues() {
		alive = false;
		range = 1; // TEST
	}
	
	public void update() {
		
		if((key.bombIsAlive == true) && this.alive) {
//			System.out.println(key.bombIsAlive + " "+ this.name);
			if(this.name.equals("bomb2"))
			key.bombIsAlive = false;
			
			
		}
		if((key.bombIsAlive == true || key.bomb2Alive == true) && !this.alive) {
//			System.out.println(this.name);
			thread = new Thread(this);
			setBombCoordinates(gp.player);
			this.alive = true;
			thread.start();
			key.bombIsAlive = false;
			
			

		}
		
	}
	
	public void setBombCoordinates(Entity entity) {
		int entityLeftWorldX = entity.worldX + entity.solidArea.x;
		int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
		int entityTopWorldY = entity.worldY + entity.solidArea.y;
		int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;
		
		int entityLeftCol = entityLeftWorldX/gp.tileSize;
		int entityRightCol = entityRightWorldX/gp.tileSize;
		int entityTopRow = entityTopWorldY/gp.tileSize;
		int entityBottomRow = entityBottomWorldY/gp.tileSize;
		
		if(entity.direction.equals("up") || entity.direction.equals("down")) {
			
			this.worldX = entityLeftCol * gp.tileSize;
			
			int bottomDiff = Math.abs((entityLeftCol) * gp.tileSize - entityBottomWorldY);
			int topDiff = Math.abs(entityLeftCol * gp.tileSize - entityTopWorldY);
			
			if(bottomDiff < topDiff) {
				this.worldY = entityTopRow * gp.tileSize;
			}
			else {
				this.worldY = entityBottomRow * gp.tileSize;
			}
			
			
		}
		else if(entity.direction.equals("right") || entity.direction.equals("left")) {
			
			this.worldY = entityTopRow * gp.tileSize;
			
			int rightDiff = Math.abs(entityTopRow * gp.tileSize - entityRightWorldX) ;
			int leftDiff = Math.abs((entityTopRow) * gp.tileSize - entityLeftWorldX) ;
			
			if(rightDiff < leftDiff) {
				this.worldX = entityRightCol * gp.tileSize;
			}
			else {
				this.worldX = entityLeftCol * gp.tileSize;
			}

		}
	}
	
	public void run() {
		
		if(remote) {
			try {
				Thread.sleep(500);
				this.collision = true;
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			while(remote) {
				 try {
			            Thread.sleep(10); // 10 milisaniye bekleme süresi
			            
			        } catch (InterruptedException e) {
			            e.printStackTrace();
			        }
				 
				if(key.remoteBomb) {
					//System.out.println("1");
					explode();
					key.remoteBomb = false;
					this.collision = false;
					break;
				}
					
			}
		}
		else {
			//if not green
			try {
				thread.sleep(1000);
				this.collision = true;
				thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			explode();
			this.collision = false;
		}

	}
	public void checkKill(int row, int col, Entity entity) {
		
		entity.solidArea.x = entity.worldX + entity.solidArea.x;
		entity.solidArea.y = entity.worldY + entity.solidArea.y;
		
		Rectangle block = new Rectangle(col * gp.tileSize, row * gp.tileSize, 48, 48);
		
		System.out.println(entity.solidArea.x + " "+ entity.solidArea.y + " " + block.x + " " + block.y);
		if(block.intersects(entity.solidArea)) {
			System.out.println("burdayım checkKill2");
			entity.alive = false;
		}
		entity.solidArea.x = entity.solidAreaDefaultX;
		entity.solidArea.y = entity.solidAreaDefaultY;
		
	}
	
	public void explode() { // checkKill implement et
		int bombY = this.worldY / gp.tileSize;
		int bombX = this.worldX / gp.tileSize;
		
		
		
		if((bombY + this.range - 1) > -1 && (bombY + this.range - 1) < gp.maxWorldRow) {
			int topBlock = worldH.mapCodes[bombY + this.range-1][bombX];
			checkKill(bombY + this.range-1, bombX, gp.player);
			for (int i = 0; i < gp.monster.length; i++) {
				if(gp.monster[i] != null) {
					checkKill(bombY + this.range-1, bombX, gp.monster[i]);
				}
			}
			for (int i = 0; i < gp.bluemonster.length; i++) {
				if(gp.bluemonster[i] != null) {
					checkKill(bombY + this.range-1, bombX, gp.bluemonster[i]);
				}
			}
			if(worldH.world[topBlock].type == 4) {
				//worldH.door.exploded = true;
				gp.aSetter.spawnBlueMonster(2);
			}
			if(worldH.world[topBlock].destructible == true) {
				if(worldH.world[topBlock].type == 2) {
					worldH.skill.exploded = true;
					gp.aSetter.spawnBlueMonster(1);
				}
				
				
					
				worldH.mapCodes[bombY + this.range-1][bombX] = 0;
				
			}
		}
		
		if((bombY - this.range + 1) > -1 && (bombY - this.range + 1) < gp.maxWorldRow) {
			int bottomBlock = worldH.mapCodes[bombY - this.range+1][bombX];
			checkKill(bombY - this.range+1, bombX, gp.player);
			for (int i = 0; i < gp.monster.length; i++) {
				if(gp.monster[i] != null) {
					checkKill(bombY - this.range+1, bombX, gp.monster[i]);
				}
			}
			for (int i = 0; i < gp.bluemonster.length; i++) {
				if(gp.bluemonster[i] != null) {
					checkKill(bombY - this.range+1, bombX, gp.bluemonster[i]);
				}
			}
			if(worldH.world[bottomBlock].type == 4) {
				//worldH.door.exploded = true;
				gp.aSetter.spawnBlueMonster(2);
			}
			if(worldH.world[bottomBlock].destructible == true) {
				if(worldH.world[bottomBlock].type == 2)
				 {
					worldH.skill.exploded = true;
					gp.aSetter.spawnBlueMonster(1);
				}
				
					
				worldH.mapCodes[bombY - this.range+1][bombX] = 0;
			}
		}
		if((bombX + this.range - 1) > -1 && (bombX + this.range - 1) < gp.maxWorldCol) {
			int rightBlock = worldH.mapCodes[bombY][bombX + this.range-1];
			checkKill(bombY, bombX + this.range-1, gp.player);
			for (int i = 0; i < gp.monster.length; i++) {
				if(gp.monster[i] != null) {
					checkKill(bombY, bombX + this.range-1, gp.monster[i]);
				}
			}
			for (int i = 0; i < gp.bluemonster.length; i++) {
				if(gp.bluemonster[i] != null) {
					checkKill(bombY, bombX + this.range-1, gp.bluemonster[i]);
				}
			}
			if(worldH.world[rightBlock].type == 4) {
				//worldH.door.exploded = true;
				gp.aSetter.spawnBlueMonster(2);
			}
			if(worldH.world[rightBlock].destructible == true) {
				
				if(worldH.world[rightBlock].type == 2)
				 {
					worldH.skill.exploded = true;
					gp.aSetter.spawnBlueMonster(1);
					//System.out.println("?");
				}
				
					
				
				worldH.mapCodes[bombY][bombX + this.range-1] = 0;
			}
		}
		
		if((bombX - this.range + 1) > -1 && (bombX - this.range + 1) < gp.maxWorldCol) {
			int leftBlock = worldH.mapCodes[bombY][bombX - this.range+1];
			checkKill(bombY, bombX - this.range+1, gp.player);
			for (int i = 0; i < gp.monster.length; i++) {
				if(gp.monster[i] != null) {
					checkKill(bombY, bombX - this.range+1, gp.monster[i]);
				}
			}
			for (int i = 0; i < gp.bluemonster.length; i++) {
				if(gp.bluemonster[i] != null) {
					checkKill(bombY, bombX - this.range+1, gp.bluemonster[i]);
				}
			}
			if(worldH.world[leftBlock].type == 4) {
				//worldH.door.exploded = true;
				gp.aSetter.spawnBlueMonster(2);
			}
			if(worldH.world[leftBlock].destructible == true) {
				
				if(worldH.world[leftBlock].type == 2)
				 {
					worldH.skill.exploded = true;
					gp.aSetter.spawnBlueMonster(1);
					
				}
				
					
				worldH.mapCodes[bombY][bombX - this.range+1] = 0;
			}
			
		}
		
		
		
		
		
		//collison olan duvardan da geçiriyor onu engellemek lazım
		
		if((bombY + this.range) > -1 && (bombY + this.range) < gp.maxWorldRow) {
			int topBlock = worldH.mapCodes[bombY + this.range-1][bombX];
			int topBlock2 = worldH.mapCodes[bombY + this.range][bombX];
			if(worldH.world[topBlock].destructible == true || topBlock == 0) {
				checkKill(bombY + this.range,bombX, gp.player);
				for (int i = 0; i < gp.monster.length; i++) {
					if(gp.monster[i] != null) {
						checkKill(bombY + this.range, bombX, gp.monster[i]);
					}
				}
				for (int i = 0; i < gp.bluemonster.length; i++) {
					if(gp.bluemonster[i] != null) {
						checkKill(bombY + this.range, bombX, gp.bluemonster[i]);
					}
				}
			}
			
			if(worldH.world[topBlock2].type == 4) {
				//worldH.door.exploded = true;
				gp.aSetter.spawnBlueMonster(2);
			}
			if((worldH.world[topBlock].destructible == true || topBlock == 0) && worldH.world[topBlock2].destructible == true) {
				if(worldH.world[topBlock2].type == 2) {
					worldH.skill.exploded = true;

					gp.aSetter.spawnBlueMonster(1);
				}
				
				worldH.mapCodes[bombY + this.range][bombX] = 0;
			}
		}
		
		if((bombY - this.range) > -1 && (bombY - this.range) < gp.maxWorldRow) {
			int bottomBlock = worldH.mapCodes[bombY - this.range+1][bombX];
			int bottomBlock2 = worldH.mapCodes[bombY - this.range][bombX];
			if(worldH.world[bottomBlock].destructible == true || bottomBlock == 0) {
				checkKill(bombY - this.range,bombX, gp.player);
				for (int i = 0; i < gp.monster.length; i++) {
					if(gp.monster[i] != null) {
						checkKill(bombY - this.range, bombX, gp.monster[i]);
					}
				}
				for (int i = 0; i < gp.bluemonster.length; i++) {
					if(gp.bluemonster[i] != null) {
						checkKill(bombY - this.range, bombX, gp.bluemonster[i]);
					}
				}
			}
			if(worldH.world[bottomBlock2].type == 4) {
				//worldH.door.exploded = true;
				gp.aSetter.spawnBlueMonster(2);
			}
			if((worldH.world[bottomBlock].destructible == true || bottomBlock == 0) && worldH.world[bottomBlock2].destructible == true) {
				if(worldH.world[bottomBlock2].type == 2) {
					worldH.skill.exploded = true;

					gp.aSetter.spawnBlueMonster(1);
				}
				
				worldH.mapCodes[bombY - this.range][bombX] = 0;
			}
		}
		if((bombX + this.range) > -1 && (bombX + this.range) < gp.maxWorldCol) {
			int rightBlock = worldH.mapCodes[bombY][bombX + this.range-1];
			int rightBlock2 = worldH.mapCodes[bombY][bombX + this.range];
			if(worldH.world[rightBlock].destructible == true || rightBlock == 0) {
				checkKill(bombY,bombX + this.range, gp.player);
				for (int i = 0; i < gp.monster.length; i++) {
					if(gp.monster[i] != null) {
						checkKill(bombY, bombX + this.range, gp.monster[i]);
					}
				}
				for (int i = 0; i < gp.bluemonster.length; i++) {
					if(gp.bluemonster[i] != null) {
						checkKill(bombY, bombX + this.range, gp.bluemonster[i]);
					}
				}
			}
			if(worldH.world[rightBlock2].type == 4) {
				//worldH.door.exploded = true;
				gp.aSetter.spawnBlueMonster(2);
			}
			if((worldH.world[rightBlock].destructible == true || rightBlock == 0) && worldH.world[rightBlock2].destructible == true) {
				if(worldH.world[rightBlock2].type == 2) {
					worldH.skill.exploded = true;

					gp.aSetter.spawnBlueMonster(1);
				}
				
				worldH.mapCodes[bombY][bombX + this.range] = 0;
			}
		}
		
		if((bombX - this.range) > -1 && (bombX - this.range) < gp.maxWorldCol) {
			int leftBlock = worldH.mapCodes[bombY][bombX - this.range+1];
			int leftBlock2 = worldH.mapCodes[bombY][bombX - this.range];
			if(worldH.world[leftBlock].destructible == true || leftBlock == 0) {
				checkKill(bombY,bombX - this.range, gp.player);
				for (int i = 0; i < gp.monster.length; i++) {
					if(gp.monster[i] != null) {
						checkKill(bombY, bombX - this.range, gp.monster[i]);
					}
				}
				for (int i = 0; i < gp.bluemonster.length; i++) {
					if(gp.bluemonster[i] != null) {
						checkKill(bombY, bombX - this.range, gp.bluemonster[i]);
					}
				}
			}
			if(worldH.world[leftBlock2].type == 4) {
				//worldH.door.exploded = true;
				gp.aSetter.spawnBlueMonster(2);
			}
			if((worldH.world[leftBlock].destructible == true || leftBlock == 0) && worldH.world[leftBlock2].destructible == true) {
				if(worldH.world[leftBlock2].type == 2) {
					worldH.skill.exploded = true;

					gp.aSetter.spawnBlueMonster(1);
				}
				
				worldH.mapCodes[bombY][bombX - this.range] = 0;
			}
			
		}
		worldH.checkSkillUnlocked();
		this.alive = false;
	}

	public void draw(Graphics2D g2) {
    	
    	BufferedImage image = null;
    	int screenX = worldX - gp.player.worldX + gp.player.screenX;
    	int screenY = worldY - gp.player.worldY + gp.player.screenY;
    	
    	if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
    	   worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
    	   worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
    	   worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
    		
    		if(this.alive == true)
    		image = this.image;
    		 int x = screenX, y = screenY;
             
             if(screenX > worldX) {
             	x = worldX;
             	
             }
             if(screenY > worldY) {
             	y = worldY;
             }
             int rightOffset = gp.screenWidth - gp.player.screenX;
 			if(rightOffset > gp.worldWidth - gp.player.worldX) {
 				x = gp.screenWidth - (gp.worldWidth - worldX);
 			}
 			
 			int bottomOffset = gp.screenHeight - gp.player.screenY;
 			if(bottomOffset > gp.worldHeight - gp.player.worldY) {
 				y = gp.screenHeight - (gp.worldHeight - worldY);
 			}
 			
            g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);

        
            } 
         }
    	
}

    

	
	
	


