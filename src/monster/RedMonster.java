package monster;

import java.awt.Rectangle;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import entity.Entity;
import main.GamePanel;

public class RedMonster extends Entity implements Runnable{
	
	Thread thr;
	
	public RedMonster(GamePanel gp) {
		super(gp);
		
		speed = 1;
		alive = true;
		solidArea = new Rectangle(3,10,46,38);
		
		
		getImage();
		
	}
	
	public void getImage() {
		
		 try {
	        	
	            up1 = ImageIO.read(getClass().getResourceAsStream("/monster/redslime_down_1.png"));
	            up2 = ImageIO.read(getClass().getResourceAsStream("/monster/redslime_down_2.png"));
	            down1 = ImageIO.read(getClass().getResourceAsStream("/monster/redslime_down_1.png"));
	            down2 = ImageIO.read(getClass().getResourceAsStream("/monster/redslime_down_2.png"));
	            left1 = ImageIO.read(getClass().getResourceAsStream("/monster/redslime_down_1.png"));
	            left2 = ImageIO.read(getClass().getResourceAsStream("/monster/redslime_down_2.png"));
	            right1 = ImageIO.read(getClass().getResourceAsStream("/monster/redslime_down_1.png"));
	            right2 = ImageIO.read(getClass().getResourceAsStream("/monster/redslime_down_2.png"));      
	            } catch (IOException e) {
	            e.printStackTrace();
	        }
	}
	
	public void setAction() {
//		
//		actionLockCounter ++;
//		
//		
//		
//		if(actionLockCounter == 120) {
//			
//			Random rand = new Random();
//			int i = rand.nextInt(100) + 1;
//			
//			if(i <= 25) {
//				direction = "up";
//			}
//			else if(i <= 50) {
//				direction = "down";
//			}
//	
//			else if(i <= 75) {
//				direction = "left";
//			}
//	
//			else if(i <= 100) {
//				direction = "right";
//			}
//			
//			actionLockCounter = 0;
//		}
		thr = new Thread(this);
		thr.start();
	
		

	}

	@Override
	public void run() {
		
		if(this.alive == true) {
			actionLockCounter ++;
			
			
			
			if(actionLockCounter == gp.FPS*2) {
				
				Random rand = new Random();
				int i = rand.nextInt(100) + 1;
				
				if(i <= 25) {
					direction = "up";
				}
				else if(i <= 50) {
					direction = "down";
				}
		
				else if(i <= 75) {
					direction = "left";
				}
		
				else if(i <= 100) {
					direction = "right";
				}
				
				actionLockCounter = 0;
			}
		}
		// TODO Auto-generated method stub
	
	}
}
