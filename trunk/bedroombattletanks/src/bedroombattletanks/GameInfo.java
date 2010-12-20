package bedroombattletanks;

import java.util.ArrayList;

import jgame.JGRectangle;

public class GameInfo {
	// object removal
    int objectIndex = -1;
	ArrayList<JGRectangle> objects = new ArrayList<JGRectangle>();
	int pfWidth;
	int pfHeight;
	// Tank Lives
	int tankNumber = 5;
	int numberVariables = 4;
	int livesPosition = 1;
	int flagPosition = 2;
	int bulletPosition = 3;
	int numberLives = 10;
	
	int[][] tankData = new int [tankNumber][numberVariables];
	
	// Bullet Data
	double bulletSpeedMultiplier = 3.5;
	int maxBullets = 6;
	
	ArrayList<Tank> allTanks = new ArrayList<Tank>();

    ArrayList<Effect> activeEffects = new ArrayList<Effect>();
    
    // Friction
    double initialFriction = 0.5;
    
    // Tank Speed
	int tankSpeed = 3;
	
	// Reloading 
	int[] bulletTimer = new int[5];	
	int reloadTime = 10;
	
	
}
