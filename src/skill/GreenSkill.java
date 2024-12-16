package skill;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class GreenSkill extends Skill {
	
	public GreenSkill() {
		
		name = "green";
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/world/orange_skill.png"));
			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void interaction(GamePanel gp) {
		if(this.type == 2) {
			gp.bomb1.range = 2;
			this.type = 3;
		}
	}

}
