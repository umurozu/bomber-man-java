package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.Serializable;

import main.GamePanel;

public class Entity {
	
	public GamePanel gp;
	public int worldX, worldY;
	public int speed;
	
	public String direction;
    public BufferedImage up1,up2,left1,left2,right1,right2,down1,down2,image;

    public int spriteCounter = 0;
    public int spriteNum = 1;
    public Rectangle solidArea;
    public int solidAreaDefaultX, solidAreaDefaultY;
    
    public boolean collisionOn = false;
    public boolean alive;
    public int actionLockCounter;
    
    public Entity(GamePanel gp) {
    	this.gp = gp;
    	this.direction = "";
    	
    }
    
    public void draw(Graphics2D g2) {
    	if(this.alive == true) {
        	BufferedImage image = null;
        	int screenX = worldX - gp.player.worldX + gp.player.screenX;
        	int screenY = worldY - gp.player.worldY + gp.player.screenY;
        	
        	if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
        	   worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
        	   worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
        	   worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {
        		
                if ("up".equals(direction)) {
                    if (spriteNum == 1) {
                        image = up1;
                    }  
                    if (spriteNum == 2) {
                        image = up2;
                    }
                } else if ("down".equals(direction)) {
                    if (spriteNum == 1) {
                        image = down1;
                    }  
                    if (spriteNum == 2) {
                        image = down2;
                    }
                } else if ("left".equals(direction)) {
                    if (spriteNum == 1) {
                        image = left1;
                    } 
                    if (spriteNum == 2) {
                        image = left2;
                    }
                } else if ("right".equals(direction)) {
                    if (spriteNum == 1) {
                        image = right1;
                    } 
                    if (spriteNum == 2) {
                        image = right2;
                    }
                }
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
    public void setAction() {
    	
    }
    
    public void update() {
    	
    	if(this.alive == true) {
    	   	setAction();
        	
        	collisionOn = false;
        	gp.cDetec.checkWorld(this);
        	int monsterIndex = gp.cDetec.checkBomb(this, gp.bomb1);
        	monsterIndex = gp.cDetec.checkBomb(this, gp.bomb2);

        	//gp.cDetec.checkEntity(this, gp.monster);
        	//gp.cDetec.checkPlayer(this, gp.player); eklenmesi lazım
        	
        	if(direction != null) {
        	  if(collisionOn == false) {
              	
                  if(direction.equals("up")) {
                      
                      worldY -= speed;
                  } else if(direction.equals("down")) {
                      
                      worldY += speed;
                  } else if(direction.equals("left")) {
                      
                      worldX -= speed;
                  } else if(direction.equals("right")) {
                      
                      worldX += speed;
                  }
              	
              }
              
              spriteCounter++;
              if(spriteCounter > 12) {
              	if(spriteNum == 1) {
              		spriteNum =2;
              	}
              	else if(spriteNum ==2) {
              		spriteNum =1;
              	}
              	spriteCounter = 0;
              }
        	}	
    	}
 
    }
}
