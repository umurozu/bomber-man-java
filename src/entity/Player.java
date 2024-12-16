package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel;
import main.Keyboard;
import skill.Skill;

public class Player extends Entity {
    
    GamePanel gp;
    Keyboard key;
    public Skill skill;
    public Skill door;
    
    public final int screenX;
    public final int screenY;
    
    public Player(GamePanel gp, Keyboard key) {
    	
    	super(gp);
    	  this.gp = gp;
          this.key = key;
          this.skill = gp.worldH.skill;
          this.door = gp.worldH.door;
          screenX = gp.screenWidth/2;
          screenY = gp.screenHeight/2;
          
          solidArea = new Rectangle(8,16,25,25);
          solidAreaDefaultX = solidArea.x;
          solidAreaDefaultY = solidArea.y;
          alive = true;
          
          setDefaultValues();
          getPlayerImage(); 
          
        
    }
    
    public void setDefaultValues() {
        worldX = gp.tileSize * 1;
        worldY = gp.tileSize * 1;
        speed = 5;
        direction = "right";
    }
    
    public void getPlayerImage() {
        try {
        	
            up1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_1.png"));
            up2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_2.png"));
            down1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_1.png"));
            down2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_2.png"));      
            } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void update() {
    	
    	if(key.keyUp == true 
    		|| key.keyDown == true 
    		|| key.keyLeft == true
    		||key.keyRight == true) {
    		

            if(key.keyUp) {
                direction = "up";
//                worldY -= speed;
            } else if(key.keyDown) {
                direction = "down";
//                worldY += speed;
            } else if(key.keyLeft) {
                direction = "left";
//                worldX -= speed;
            } else if(key.keyRight) {
                direction = "right";
//                worldX += speed;
            }
            
            collisionOn = false;
            gp.cDetec.checkWorld(this);
            
           
            int monsterIndex = gp.cDetec.checkEntity(this, gp.monster);
            interactMonster(monsterIndex);
            monsterIndex = gp.cDetec.checkEntity2(this, gp.bluemonster);
            interactMonster(monsterIndex);
            monsterIndex = gp.cDetec.checkBomb(this, gp.bomb1);
            monsterIndex = gp.cDetec.checkBomb(this, gp.bomb2);

            this.skill = gp.worldH.skill;
            if(skill!=null) {
            	
//                	System.out.println(this.worldX);
                	int skillIndex = gp.cDetec.checkSkill(this,skill);
                    interactSkill(skillIndex);
                    gp.worldH.checkSkillTaken();
            	

            }
            this.door = gp.worldH.door;
            if(door != null) {
            	int skillIndex = gp.cDetec.checkSkill(this,door);
                interactDoor(skillIndex);
                gp.worldH.checkSkillTaken();
            }
            
            
            if(collisionOn == false) {
            	
                if(key.keyUp) {
                    direction = "up";
                    worldY -= speed;
                } else if(key.keyDown) {
                    direction = "down";
                    worldY += speed;
                } else if(key.keyLeft) {
                    direction = "left";
                    worldX -= speed;
                } else if(key.keyRight) {
                    direction = "right";
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
    
    private void interactMonster(int monsterIndex) {
		if (monsterIndex != 999)
			alive = false;
		
	}
    
    private void interactSkill(int index) {
    	System.out.println("index ne" + index);
    	if (index == 1) {
    		System.out.println("girdim");
    		skill.interaction(this.gp);
    	}
    		
    }
    
    private void interactDoor(int index) {
    	if (index == 1) {
    		//System.out.println("girdim");
    		door.interaction(this.gp);
    	}
    		
    }

	public void draw(Graphics2D g2) {
        BufferedImage image = right1;
        
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
        
        int x = screenX;
        int y = screenY;
	        if(screenX > worldX) {
	        	x = worldX;
	        	
	        }
	        if(screenY > worldY) {
	        	y = worldY;
	        }
        int rightOffset = gp.screenWidth - screenX;
		if(rightOffset > gp.worldWidth - worldX) {
			x = gp.screenWidth - (gp.worldWidth - worldX);
		}
		
		int bottomOffset = gp.screenHeight - screenY;
		if(bottomOffset > gp.worldHeight - worldY) {
			y = gp.screenHeight - (gp.worldHeight - worldY);
		}
        
        if (image != null) {
        	//buraya if satırı eklenecek
            g2.drawImage(image, x, y, gp.tileSize, gp.tileSize, null);
        } else {
            System.out.println("Image is null for direction: " + direction);
        }
    }
}
