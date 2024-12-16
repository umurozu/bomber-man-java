package main;

import java.io.Serializable;
import java.util.Random;

import monster.BlueMonster;
import monster.RedMonster;
import skill.*;
import world.WorldHelper;

public class AssetSetter{

	GamePanel gp;
	WorldHelper world;
	
	public AssetSetter(GamePanel gp, WorldHelper world) {
		this.gp = gp;
		this.world = world;
		
	}
	
	public void setMonster() {
		Random rand = new Random();
		for (int i = 0; i < gp.monster.length; i++) {
			
			gp.monster[i] = new RedMonster(gp);
			int x = rand.nextInt(31-5+1) + 5;
			int y = rand.nextInt(11-5+1) + 5;
			while(check(x,y) == false) {
				x = rand.nextInt(31-5+1) + 5;
				y = rand.nextInt(11-5+1) + 5;
			}
			gp.monster[i].worldX = gp.tileSize * x;//5-31
			gp.monster[i].worldY = gp.tileSize * y;//5-11		
			
		}
	}
	
	public boolean check (int x, int y) {
		
		if(gp.worldH.mapCodes[y][x] == 0) {
			return true;
		}
		else return false;
	}
	
	public void spawnBlueMonster(int skillOrDoor) {
		// 1 if skill
		if(skillOrDoor == 1) {
			
			for (int i = 0; i < gp.bluemonster.length; i++) {
				
				gp.bluemonster[i] = new BlueMonster(gp);
				gp.bluemonster[i].worldX = gp.worldH.skillX * gp.tileSize;
				gp.bluemonster[i].worldY = gp.worldH.skillY * gp.tileSize;
				
			}
		}
		else {
			for (int i = 0; i < gp.bluemonster.length; i++) {
				System.out.println("giremedim");
				gp.bluemonster[i] = new BlueMonster(gp);
				gp.bluemonster[i].worldX = gp.worldH.doorX * gp.tileSize;
				gp.bluemonster[i].worldY = gp.worldH.doorY * gp.tileSize;
				
			}
		}
		
		//2 if door
	}
	
	public void setSkills(int chosen) {
		
		gp.bomb1.active = true;
		//gp.bomb2.active = true;  TEST 
		
		
		if(chosen == 1) {
			gp.skill[0] = new OrangeSkill();
			gp.skill[0].worldX = world.skillX;
			gp.skill[0].worldY = world.skillY;
			world.skill = gp.skill[0];
		}
		else if(chosen == 2) {
			gp.skill[1] = new CyanSkill();
			gp.skill[1].worldX = world.skillX;
			gp.skill[1].worldY = world.skillY;
			world.skill = gp.skill[1];
		}
		else if(chosen == 3) {
			gp.skill[2] = new GreenSkill();
			gp.skill[2].worldX = world.skillX;
			gp.skill[2].worldY = world.skillY;
			world.skill = gp.skill[2];
		}
		else if(chosen == 4) {
			gp.skill[3] = new BlueSkill();
			gp.skill[3].worldX = world.skillX;
			gp.skill[3].worldY = world.skillY;
			world.skill = gp.skill[3];
		}
		else if(chosen == 5) {
			gp.skill[4] = new YellowSkill();
			gp.skill[4].worldX = world.skillX;
			gp.skill[4].worldY = world.skillY;
			world.skill = gp.skill[4];
		}
		
		gp.skill[5] = new Door();
		gp.skill[5].worldX = world.doorX;
		gp.skill[5].worldY = world.doorY;
		world.door = gp.skill[5];
	}
}
