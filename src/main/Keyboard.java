package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Keyboard implements KeyListener{
	
	GamePanel gp;
	public boolean keyUp, keyDown, keyLeft, keyRight;
	public boolean gameIsPaused;
	public boolean bombIsAlive;
	public boolean remoteBomb;
	public boolean bomb2Alive;
	
	
	public Keyboard(GamePanel gp) {
		this.gp = gp;
	}
	
	public void keyPressed(KeyEvent e) {
		
		int code = e.getKeyCode();
		
//		if(code == KeyEvent.VK_P && gameIsPaused == true) {
//			System.out.println("durdur2");
//			gameIsPaused = false;
//			return;
//		}
//		
//		
//		if(code == KeyEvent.VK_P && gameIsPaused == false) {
//			System.out.println("durdur1");
//			gameIsPaused = true;
//			return;
//		}
		
		//titlestate
		if(gp.gameState == gp.titleState) {
			if(code == KeyEvent.VK_UP) {
				gp.ui.commandNum--;
				if(gp.ui.commandNum < 0) {
					gp.ui.commandNum = 2;
				}

			}
			
			if(code == KeyEvent.VK_DOWN) {
				gp.ui.commandNum++;
				if(gp.ui.commandNum > 6) {
					gp.ui.commandNum = 0;
				}

			}
			
			if(code == KeyEvent.VK_ENTER) {
				if(gp.ui.commandNum == 0) {
					gp.gameState = gp.playState;
					
				}
				if(gp.ui.commandNum == 1) {
					gp.saveLoad.load();
					gp.gameState = gp.playState;
					gp.aSetter.setSkills(gp.chosen);
					gp.saveLoad.load();
					//gp.worldH.getWorldImage();
				}
				if(gp.ui.commandNum == 2) {
					gp.chosen = 1;
					gp.worldH.getWorldImage();
					gp.aSetter.setSkills(gp.chosen);
				}
				if(gp.ui.commandNum == 3) {
					gp.chosen = 2;
					gp.worldH.getWorldImage();
					gp.aSetter.setSkills(gp.chosen);
				}
				if(gp.ui.commandNum == 4) {
					gp.chosen = 3;
					gp.worldH.getWorldImage();
					gp.aSetter.setSkills(gp.chosen);
				}
				if(gp.ui.commandNum == 5) {
					gp.chosen = 4;
					gp.worldH.getWorldImage();
					gp.aSetter.setSkills(gp.chosen);
				}
				if(gp.ui.commandNum == 6) {
					gp.chosen = 5;
					gp.worldH.getWorldImage();
					gp.aSetter.setSkills(gp.chosen);
				}
				
			}
		}
		
		
		else {
			if(code == KeyEvent.VK_P) {
				if(gp.gameState == gp.playState) {
					gp.gameState = gp.pauseState;
					gp.saveLoad.save();
				}
				else if(gp.gameState == gp.pauseState) {
					gp.gameState = gp.playState;
				}
			}
			
			
			if(code == KeyEvent.VK_Z) {
				bombIsAlive = true;
			}
			
			if(code == KeyEvent.VK_B) {
				//System.out.println("za");
				remoteBomb = true;
			}
			
			
			if(code == KeyEvent.VK_UP) {
				keyUp = true;
			}
			if(code == KeyEvent.VK_LEFT) {
				keyLeft = true;
			}
			if(code == KeyEvent.VK_RIGHT) {
				keyRight = true;
			}
			if(code == KeyEvent.VK_DOWN) {
				keyDown = true;
			}
			
		}
		
		
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
		int code = e.getKeyCode();
		
		
		if(code == KeyEvent.VK_UP) {
			keyUp = false;
		}
		if(code == KeyEvent.VK_LEFT) {
			keyLeft = false;
		}
		if(code == KeyEvent.VK_RIGHT) {
			keyRight = false;
		}
		if(code == KeyEvent.VK_DOWN) {
			keyDown = false;
		}
		
		if(code == KeyEvent.VK_Z) {
			bombIsAlive = false;
		}
		
		

		if(code == KeyEvent.VK_B) {
			//System.out.println("za");
			remoteBomb = false;
		}
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
