package bedroombattletanks;

import java.util.ArrayList;

import jgame.JGColor;
import jgame.JGRectangle;
import jgame.platform.JGEngine;

public class GameInfo {
	//GUI
	boolean guiDone = false;
	
	
	// object removal
    int objectIndex = -1;
	ArrayList<JGRectangle> objects = new ArrayList<JGRectangle>();
	ArrayList<Obstacle> map1 = new ArrayList<Obstacle>();

	int pfWidth;
	int pfHeight;
	
	// Tank Lives
	TankData[] tankData = {new TankData(), new TankData()};

	
	// Bullet Data
	double bulletSpeedMultiplier = 3.5;
	int maxBullets = 6;
	
	ArrayList<Tank> allTanks = new ArrayList<Tank>();

    ArrayList<Effect> activeEffects = new ArrayList<Effect>();
    
    // Friction
    double initialFriction = 0.5;
    double friction = initialFriction;
    
    // Tank Speed
	int tankSpeed = 3;
	
	// Reloading 
	//int[] bulletTimer = new int[5];	
	int reloadTime = 10;
	
	// Winner
    String winner;
    String loser;
    JGColor winnerColor;
    boolean gameLost;
    GameInfo(JGEngine eng) {
    	pfWidth = eng.pfWidth();
    	pfHeight = eng.pfHeight();
    };
}
