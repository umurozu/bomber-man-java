package data;

import java.io.Serializable;

import bomb.Bomb;
import entity.Entity;
import entity.Player;
import world.WorldHelper;

public class DataStorage implements Serializable {

//	//player
//	Player player;
//	//bomb
//	Bomb bomb1;
//	Bomb bomb2;
//	//world
//	WorldHelper worldH;
//	//redmonster
//	Entity monster[]; // default 5
//	Entity bluemonster[]; // def 7
	
	//int speed;
	int chosen;
	int p_speed;
	int p_worldX;
	int p_worldY;
	boolean bomb2_active;
	boolean bomb_remote;
	boolean world_stone_col_blue_skill;
	int bomb_range;
	
	int doorX;
	int doorY;
	int doorType;
	
	int skillX;
	int skillY;
	int skillType;
	
	int[][]	mapCodes;
	
	int [] redMonX;
	int [] redMonY;
	boolean[] redMonAlive;
	int [] blueMonX;
	int [] blueMonY;
	boolean[] blueMonAlive;
	double time;
}
