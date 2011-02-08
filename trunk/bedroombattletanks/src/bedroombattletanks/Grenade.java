package bedroombattletanks;

import jgame.JGObject;


//Class representing the grenade object

class Grenade extends Projectile {

	int timer = 90;
	int spread = 20;
	double targetX;
	double targetY;
	
	GameUtilities gameUtil = new GameUtilities();
	/** Constructor. */
	Grenade (Tank tank, GameInfo gameInfo) {
		
		super(	1,
				"grenade",
				tank,
				2,
				gameInfo
		);
		// Grenade spread
		targetX = x + gameUtil.random(-spread,spread);
		targetY = y + gameUtil.random(-spread,spread);
	}
	public void move() {
		timer--;
		
		
		// Spread
		if (timer == 20) {
			xspeed = spread/10;
			yspeed = spread/10;
			if (x < targetX && xspeed<0) xspeed = -xspeed;
			if (x > targetX && xspeed>0) xspeed = -xspeed;
			if (y < targetY && yspeed<0) yspeed = -yspeed;
			if (y > targetY && yspeed>0) yspeed = -yspeed;
		}
		// Stop the grenade from moving
		if (timer == 10) {
			xspeed = 0;
			yspeed = 0;
		}
		// Explosion Code
		if (timer == 0) {
			remove();
			for (int i = 0; i < gameUtil.random(12,15,1); i++){
				new JGObject("boom",true,x,y,1,"tank-bit"+gameUtil.random(1,9,1),gameUtil.random(-10,10),gameUtil.random(-10,10),100);
			}
		}
	}
	public void hit(JGObject obj){
	}
}
