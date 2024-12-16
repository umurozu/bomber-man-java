package main;

import java.awt.Rectangle;

import bomb.Bomb;
import entity.Entity;
import skill.Skill;

public class CollisionDetector {
	
	GamePanel gp;
	
	public CollisionDetector(GamePanel gp) {
		
		this.gp = gp;
	}
	
	public void checkWorld(Entity entity) {
		
		int entityLeftWorldX = entity.worldX + entity.solidArea.x;
		int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
		int entityTopWorldY = entity.worldY + entity.solidArea.y;
		int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;
		
		int entityLeftCol = entityLeftWorldX/gp.tileSize;
		int entityRightCol = entityRightWorldX/gp.tileSize;
		int entityTopRow = entityTopWorldY/gp.tileSize;
		int entityBottomRow = entityBottomWorldY/gp.tileSize;
		
		int worldNum1, worldNum2;
		
		if (entity.direction != null)
		switch(entity.direction) {
		case"up":
			entityTopRow = (entityTopWorldY - entity.speed)/gp.tileSize;
			worldNum1 = gp.worldH.mapCodes[entityTopRow][entityLeftCol];
			worldNum2 = gp.worldH.mapCodes[entityTopRow][entityRightCol];
			if(gp.worldH.world[worldNum1].collision == true || gp.worldH.world[worldNum2].collision == true) {
				entity.collisionOn = true;
			}
			break;
		case"down":
			entityBottomRow = (entityBottomWorldY + entity.speed)/gp.tileSize;
			worldNum1 = gp.worldH.mapCodes[entityBottomRow][entityLeftCol];
			worldNum2 = gp.worldH.mapCodes[entityBottomRow][entityRightCol];
			if(gp.worldH.world[worldNum1].collision == true || gp.worldH.world[worldNum2].collision == true) {
				entity.collisionOn = true;
			}
			break;
		case"left":
			entityLeftCol = (entityLeftWorldX - entity.speed)/gp.tileSize;
			worldNum1 = gp.worldH.mapCodes[entityTopRow][entityLeftCol];
			worldNum2 = gp.worldH.mapCodes[entityBottomRow][entityLeftCol];
			if(gp.worldH.world[worldNum1].collision == true || gp.worldH.world[worldNum2].collision == true) {
				entity.collisionOn = true;
			}
			break;
		case"right":
			entityRightCol = (entityRightWorldX + entity.speed)/gp.tileSize;
			worldNum1 = gp.worldH.mapCodes[entityTopRow][entityRightCol];
			worldNum2 = gp.worldH.mapCodes[entityBottomRow][entityRightCol];
			if(gp.worldH.world[worldNum1].collision == true || gp.worldH.world[worldNum2].collision == true) {
				entity.collisionOn = true;
			}
			break;
		}
	}
	
	public int checkEntity(Entity entity, Entity[] monster) {
		
		int index = 999;
		
		for (int i = 0; i < monster.length; i++) {
			
			if(monster[i] != null && monster[i].alive == true) {
				
				entity.solidArea.x = entity.worldX + entity.solidArea.x;
				entity.solidArea.y = entity.worldY + entity.solidArea.y;
				
				monster[i].solidArea.x = monster[i].worldX + monster[i].solidArea.x;
				monster[i].solidArea.y = monster[i].worldY + monster[i].solidArea.y;
				
				switch(entity.direction) {
				case "up":
					entity.solidArea.y -= entity.speed;
					if(entity.solidArea.intersects(monster[i].solidArea)) {
						
							entity.collisionOn = true;
						
						
							index = i;
						
					}
					break;
				case "down":
					entity.solidArea.y += entity.speed;
					if(entity.solidArea.intersects(monster[i].solidArea)) {
						
							entity.collisionOn = true;
						
						
							index = i;
						
					}
					break;

				case "left":
					entity.solidArea.x -= entity.speed;
					if(entity.solidArea.intersects(monster[i].solidArea)) {
						
							entity.collisionOn = true;
						
							index = i;
						
					}
					break;

				case "right":
					entity.solidArea.x += entity.speed;
					if(entity.solidArea.intersects(monster[i].solidArea)) {
							entity.collisionOn = true;
							index = i;
						
					}
					break;

				}
				entity.solidArea.x = entity.solidAreaDefaultX;
				entity.solidArea.y = entity.solidAreaDefaultY;
				gp.monster[i].solidArea.x = gp.monster[i].solidAreaDefaultX;
				gp.monster[i].solidArea.y = gp.monster[i].solidAreaDefaultY;
			}
		}
		return index;
	}
	
public int checkEntity2(Entity entity, Entity[] bluemonster) {
		
		int index = 999;
		
		for (int i = 0; i < bluemonster.length; i++) {
			
			if(bluemonster[i] != null && bluemonster[i].alive == true) {
				
				entity.solidArea.x = entity.worldX + entity.solidArea.x;
				entity.solidArea.y = entity.worldY + entity.solidArea.y;
				
				bluemonster[i].solidArea.x = bluemonster[i].worldX + bluemonster[i].solidArea.x;
				bluemonster[i].solidArea.y = bluemonster[i].worldY + bluemonster[i].solidArea.y;
				
				switch(entity.direction) {
				case "up":
					entity.solidArea.y -= entity.speed;
					if(entity.solidArea.intersects(bluemonster[i].solidArea)) {
						
							entity.collisionOn = true;
						
						
							index = i;
						
					}
					break;
				case "down":
					entity.solidArea.y += entity.speed;
					if(entity.solidArea.intersects(bluemonster[i].solidArea)) {
						
							entity.collisionOn = true;
						
						
							index = i;
						
					}
					break;

				case "left":
					entity.solidArea.x -= entity.speed;
					if(entity.solidArea.intersects(bluemonster[i].solidArea)) {
						
							entity.collisionOn = true;
						
							index = i;
						
					}
					break;

				case "right":
					entity.solidArea.x += entity.speed;
					if(entity.solidArea.intersects(bluemonster[i].solidArea)) {
							entity.collisionOn = true;
							index = i;
						
					}
					break;

				}
				entity.solidArea.x = entity.solidAreaDefaultX;
				entity.solidArea.y = entity.solidAreaDefaultY;
				gp.bluemonster[i].solidArea.x = gp.bluemonster[i].solidAreaDefaultX;
				gp.bluemonster[i].solidArea.y = gp.bluemonster[i].solidAreaDefaultY;
			}
		}
		return index;
	}
	
	public int checkSkill(Entity entity, Skill skill) {
		int index = 999;
		
		if(skill != null && skill.exploded == false && skill.type != 3) {
			
			entity.solidArea.x = entity.worldX + entity.solidArea.x;
			entity.solidArea.y = entity.worldY + entity.solidArea.y;
			
			skill.solidArea.x = skill.worldX * gp.tileSize + skill.solidArea.x;
			skill.solidArea.y = skill.worldY * gp.tileSize + skill.solidArea.y;
			
			switch(entity.direction) {
			case "up":
				entity.solidArea.y -= entity.speed;
				if(entity.solidArea.intersects(skill.solidArea)) {
						entity.collisionOn = true;
					
					
						index = 1;
					
				}
				break;
			case "down":
				entity.solidArea.y += entity.speed;
				if(entity.solidArea.intersects(skill.solidArea)) {
						entity.collisionOn = true;
					
					
						index = 1;
					
				}
				break;

			case "left":
				entity.solidArea.x -= entity.speed;
				if(entity.solidArea.intersects(skill.solidArea)) {
					
						entity.collisionOn = true;
					
						index = 1;
					
				}
				break;

			case "right":
				entity.solidArea.x += entity.speed;
				if(entity.solidArea.intersects(skill.solidArea)) {
						entity.collisionOn = true;
						index = 1;
						
				}
				break;

			}
			entity.solidArea.x = entity.solidAreaDefaultX;
			entity.solidArea.y = entity.solidAreaDefaultY;
			skill.solidArea.x = skill.solidAreaDefaultX;
			skill.solidArea.y = skill.solidAreaDefaultY;
			
		}
		
		return index;
	}
	
	public int checkBomb(Entity entity, Bomb bomb) {
		int index = 999;
		
		if(bomb != null && bomb.alive == true && bomb.collision == true) {
			System.out.println(bomb.alive);
			entity.solidArea.x = entity.worldX + entity.solidArea.x;
			entity.solidArea.y = entity.worldY + entity.solidArea.y;
			
			bomb.solidArea.x = bomb.worldX + bomb.solidArea.x;
			bomb.solidArea.y = bomb.worldY + bomb.solidArea.y;
			
			switch(entity.direction) {
			case "up":
				entity.solidArea.y -= entity.speed;
				if(entity.solidArea.intersects(bomb.solidArea)) {
						entity.collisionOn = true;
					
					
						index = 1;
					
				}
				break;
			case "down":
				entity.solidArea.y += entity.speed;
				if(entity.solidArea.intersects(bomb.solidArea)) {
						entity.collisionOn = true;
					
					
						index = 1;
					
				}
				break;

			case "left":
				entity.solidArea.x -= entity.speed;
				if(entity.solidArea.intersects(bomb.solidArea)) {
					
						entity.collisionOn = true;
					
						index = 1;
					
				}
				break;

			case "right":
				entity.solidArea.x += entity.speed;
				if(entity.solidArea.intersects(bomb.solidArea)) {
						entity.collisionOn = true;
						index = 1;
						
				}
				break;

			}
			entity.solidArea.x = entity.solidAreaDefaultX;
			entity.solidArea.y = entity.solidAreaDefaultY;
			bomb.solidArea.x = bomb.solidAreaDefaultX;
			bomb.solidArea.y = bomb.solidAreaDefaultY;
			
		}
		
		return index;
	}

}
