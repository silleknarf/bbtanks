package bedroombattletanks;

import jgame.JGObject;

//Class representing the bullet object

class Bullet extends JGObject {

	GameInfo localInfo;

	/** Constructor. */
	Bullet (double tankX, double tankY, double xSpeed, double ySpeed, GameInfo gameInfo) {
		
		super(
			"bullet",// name by which the object is known
			true,//true means add a unique ID number after the object name.
			     //If we don't do this, this object will replace any object
			     //with the same name.
			tankX,  // X position
			tankY, // Y position
			1, // the object's collision ID (used to determine which classes
			   // of objects should collide with each other)
			"paper-ball-small", // name of sprite or animation to use (null is none)
			600 // Lifespan of bullet in frames
		);
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
