package skill;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class BlueSkill extends Skill {
	
	public BlueSkill() {
		
		name = "blue";
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/world/orange_skill.png"));
			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void interaction(GamePanel gp) {
		if(this.type == 2) {
			gp.worldH.world[2].collision = false;
			gp.worldH.world[3].collision = false;
			gp.worldH.world[4].collision = false;
			this.type = 3;
		}
	}

}
