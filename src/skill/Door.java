package skill;

import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UI;

public class Door extends Skill {
	
	public Door() {
		
		name = "door";
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/world/orange_skill.png"));
			
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void interaction(GamePanel gp) {
		
		if(this.type == 2 && allMonstersAreDead(gp) == true) { // canavarlar öldüyse.
			
			System.out.println("WON THE GAME");
			
			gp.ui.gameWon = true;
			
			this.type = 3;
		}
		
	}
	
	public boolean allMonstersAreDead(GamePanel gp) {
		
		boolean bool = true;
		
		for(int i = 0; i < gp.monster.length; i++) {
			if(gp.monster[i] != null && gp.monster[i].alive) {
				bool = false;
			}
		}
		
		for(int i = 0; i < gp.bluemonster.length; i++) {
			if(gp.bluemonster[i] != null && gp.bluemonster[i].alive) {
				bool = false;
			}
		}
		
		return bool;
	}

}
