package skill;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.Serializable;

import main.GamePanel;

public class Skill {
	
	public BufferedImage image;
	public String name;
	public boolean collision = true;
	public int worldX, worldY;
	public boolean exploded = false;
	public Rectangle solidArea = new Rectangle(0,0,48,48);
	public int solidAreaDefaultX = 0;
	public int solidAreaDefaultY = 0;
	public int type = 1; // 2 opened 3 taken
	
	
	
	
	
	


	public void interaction(GamePanel gp) {
		// TODO Auto-generated method stub
		System.out.println("noluyo");
	}

}
