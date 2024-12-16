package skill;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;



public class OrangeSkill extends Skill {
	
	public OrangeSkill() {
		
		name = "orange";
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/world/orange_skill.png"));
			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void interaction(GamePanel gp) {
		System.out.println("tip: " + type);
		if(this.type == 2) {
			gp.player.speed = 8;
			this.type = 3;
		}
	}
}
