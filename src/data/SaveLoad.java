package data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import main.GamePanel;
import skill.Skill;

public class SaveLoad {
	
	GamePanel gp;
	
	public SaveLoad(GamePanel gp) {
		
		this.gp = gp;
	}
	
	public void save() {
		
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("save.dat")));
			
			DataStorage ds = new DataStorage();
			
//			ds.player = gp.player;
//			ds.bomb1 = gp.bomb1;
//			ds.bomb2 = gp.bomb2;
//			ds.worldH = gp.worldH;
//			ds.monster = gp.monster;
//			ds.bluemonster = gp.bluemonster;
			ds.chosen = gp.chosen;
			ds.p_speed = gp.player.speed;
			ds.p_worldX = gp.player.worldX;
			ds.p_worldY = gp.player.worldY;
			
			ds.bomb2_active = gp.bomb2.active;
			ds.bomb_remote = gp.bomb1.remote;
			ds.bomb_range = gp.bomb1.range;
			
			ds.world_stone_col_blue_skill = gp.worldH.world[2].collision;
			
			ds.doorX = gp.worldH.doorX;
			ds.doorY = gp.worldH.doorY;
			ds.doorType = gp.worldH.door.type;
			
			ds.skillX = gp.worldH.skillX;
			ds.skillY = gp.worldH.skillY;
			ds.skillType = gp.worldH.skill.type;
			
			ds.mapCodes = gp.worldH.mapCodes;
			
			ds.redMonX = new int[gp.monster.length];
			ds.redMonY = new int[gp.monster.length];
			ds.blueMonX = new int[gp.bluemonster.length];
			ds.blueMonX = new int[gp.bluemonster.length];
			ds.redMonAlive = new boolean[gp.monster.length];
			ds.blueMonAlive = new boolean[gp.bluemonster.length];
			ds.time = gp.ui.playTime;
			
			for (int i = 0; i < ds.redMonX.length; i++) {
				if(gp.monster[i] != null) {
					ds.redMonX[i] = gp.monster[i].worldX;
					ds.redMonY[i] = gp.monster[i].worldY;
					ds.redMonAlive[i] = gp.monster[i].alive;
				}
				
			}
			for (int i = 0; i < ds.blueMonX.length; i++) {
				if(gp.bluemonster[i] != null) {
					ds.blueMonX[i] = gp.bluemonster[i].worldX;
					ds.blueMonY[i] = gp.bluemonster[i].worldY;
					ds.blueMonAlive[i] = gp.bluemonster[i].alive;
				}
				
			}
			
			oos.writeObject(ds);
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
	}
	
	public void load() {
		
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("save.dat")));
			
			DataStorage ds = (DataStorage)ois.readObject();
			
//			gp.player = ds.player;
//			gp.bomb1 = ds.bomb1;
//			gp.bomb2 = ds.bomb2;
//			gp.worldH = ds.worldH;
//			gp.monster = ds.monster;
//			gp.bluemonster = ds.bluemonster;
			gp.chosen = ds.chosen;
			gp.player.speed = ds.p_speed;
	        gp.player.worldX = ds.p_worldX;
	        gp.player.worldY = ds.p_worldY;

	        gp.bomb2.active = ds.bomb2_active;
	        gp.bomb1.remote = ds.bomb_remote;
	        gp.bomb1.range = ds.bomb_range;

	        gp.worldH.world[2].collision = ds.world_stone_col_blue_skill;
	        gp.worldH.world[3].collision = ds.world_stone_col_blue_skill;
	        gp.worldH.world[4].collision = ds.world_stone_col_blue_skill;

	        gp.worldH.doorX = ds.doorX;
	        gp.worldH.doorY = ds.doorY;
	        gp.worldH.door.type = ds.doorType;
	        
	        if(gp.player.door != null) {
	        	gp.player.door.worldX = ds.doorX;
		        gp.player.door.worldY = ds.doorY;
		        gp.player.door.type = ds.doorType;
	        }
	        


	        gp.worldH.skillX = ds.skillX;
	        gp.worldH.skillY = ds.skillY;
	        gp.worldH.skill.type = ds.skillType;
	        
	        
	        if(gp.player.skill != null) {
	        	gp.player.skill.worldX = ds.skillX;
	 	        gp.player.skill.worldY = ds.skillY;
	 	        System.out.println(ds.skillType+"zzzz");
	 	        gp.player.skill.type = ds.skillType;
	        }
	       
	        
	        gp.worldH.mapCodes = ds.mapCodes;

	        for (int i = 0; i < ds.redMonX.length; i++) {
	        	if(gp.monster[i] != null) {
	        		gp.monster[i].worldX = ds.redMonX[i];
		            gp.monster[i].worldY = ds.redMonY[i];
		            gp.monster[i].alive = ds.redMonAlive[i];
	        	}
	            
	        }
	        
	        for (int i = 0; i < ds.blueMonX.length; i++) {
	        	if(gp.bluemonster[i] != null) {
	        		gp.bluemonster[i].worldX = ds.blueMonX[i];
		            gp.bluemonster[i].worldY = ds.blueMonY[i];
		            gp.bluemonster[i].alive = ds.blueMonAlive[i];
	        	}
	            
	        }
	        gp.ui.playTime = ds.time;
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
