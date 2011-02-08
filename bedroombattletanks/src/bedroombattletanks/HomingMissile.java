package bedroombattletanks;

import jgame.JGObject;

//Class representing the bullet object

class HomingMissile extends Projectile {

	Tank localShooter;
	int targetX;
	int targetY;
	int targetCentreX;
	int targetCentreY;
	double localXSpeed = 0.75;
	double localYSpeed = 0.75;
	double fullSpeed;
	GameUtilities gameUtil = new GameUtilities();

	/** Constructor. */
	HomingMissile(Tank tank, GameInfo gameInfo) {
		
		super(	64,
				"fireball",
				tank,
				3, // minDistance
				gameInfo
		);
		// Give the object an initial speed in defined direction multplied by the global bullet speed variable.
		
		localInfo = gameInfo;
		localShooter = tank;
	}

	/** Update the object. This method is called by moveObjects. */
	public void move() {
		
		// Homing Code
		
		// Get opposing tank data
		for (Tank tank : localInfo.allTanks){
			if (tank != localShooter) {
				targetX = (int) (tank.x +tank.tankCentreX);
				targetCentreX = tank.tankCentreX/2;
				targetY = (int) (tank.y +tank.tankCentreY);
				targetCentreY = tank.tankCentreY/2;
			}
		}
		
		// Move bullet towards other tank
		
		xspeed = localXSpeed*localInfo.bulletSpeedMultiplier;
		yspeed = localYSpeed*localInfo.bulletSpeedMultiplier;
		if (x < targetX && xspeed<0) xspeed = -xspeed;
		if (x > targetX && xspeed>0) xspeed = -xspeed;
		if (y < targetY && yspeed<0) yspeed = -yspeed;
		if (y > targetY && yspeed>0) yspeed = -yspeed;
		
		// Stop jittering when the bullet is adjacent to the tank
		
		if (x > (targetX-targetCentreX) && x < (targetX+targetCentreX))
			xspeed = 0;
		if (y > (targetY-targetCentreY) && y < (targetY+targetCentreY))
			yspeed = 0;
	
		
		
		// bounce off the borders of the screen.
		if (x >  eng.pfWidth()-8 && xspeed>0) xspeed = -xspeed;
		if (x <          0 && xspeed<0) xspeed = -xspeed;
		if (y > eng.pfHeight()-8 && yspeed>0) yspeed = -yspeed;
		if (y <          0 && yspeed<0) yspeed = -yspeed;
		// xspeed and yspeed are added to x and y automatically at the end
		// of the move() method.
	}// bounce off the borders of the screen.
	public void hit(JGObject obj) {
		obj.remove();
		remove();
	}
}
