package skill;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class CyanSkill extends Skill {
	
	public CyanSkill() {
		
		name = "cyan";
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/world/orange_skill.png"));
			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void interaction(GamePanel gp) {
		if(this.type == 2) {
			gp.bomb1.remote = true;
			this.type = 3;
		}
	}

}
