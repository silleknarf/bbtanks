package bedroombattletanks;


//Class representing the bullet object

class Bullet extends Projectile {


	/** Constructor. */
	Bullet (Tank tank, GameInfo gameInfo) {
		
		super(	1,
				"paper-ball-small",
				tank,
				1,
				gameInfo
		);
		
	}
	
}
