package bedroombattletanks;

import jgame.JGObject;

//Class representing the bullet object

class Projectile extends JGObject {

	GameInfo localInfo;
	double xSpeed;
	double ySpeed;
	
	/** Constructor. */
	Projectile (int colid, String image, Tank tank, double minDistance, GameInfo gameInfo) {
	//	Projectile (String image, double tankX, double tankY, double xSpeed, double ySpeed, Tank tank, GameInfo gameInfo) {
	
		super(
			"bullet",// name by which the object is known
			true,//true means add a unique ID number after the object name.
			     //If we don't do this, this object will replace any object
			     //with the same name.
			(tank.x)+(tank.tankCentreX)+(minDistance*(tank.tankGunXDistance)*-Math.sin(Math.toRadians(tank.orientation))),  // X position
			(tank.y)+(tank.tankCentreY)+(minDistance*(tank.tankGunYDistance)*-Math.cos(Math.toRadians(tank.orientation))),  // Y position
			colid, // the object's collision ID (used to determine which classes
			   // of objects should collide with each other)
			image, // name of sprite or animation to use (null is none)
			600 // Lifespan of bullet in frames
		);
		
		xSpeed = -Math.sin(Math.toRadians(tank.orientation));
		ySpeed = -Math.cos(Math.toRadians(tank.orientation));
		// Give the object an initial speed in defined direction multplied by the global bullet speed variable.
		xspeed = xSpeed*gameInfo.bulletSpeedMultiplier;
		yspeed = ySpeed*gameInfo.bulletSpeedMultiplier;
		localInfo = gameInfo;
	}

	/** Update the object. This method is called by moveObjects. */
	public void move() {

		// bounce off the borders of the screen.
		if (x >  eng.pfWidth()-8 && xspeed>0) xspeed = -xspeed;
		if (x <          0 && xspeed<0) xspeed = -xspeed;
		if (y > eng.pfHeight()-8 && yspeed>0) yspeed = -yspeed;
		if (y <          0 && yspeed<0) yspeed = -yspeed;
		// xspeed and yspeed are added to x and y automatically at the end
		// of the move() method.
	}// bounce off the borders of the screen.
}
