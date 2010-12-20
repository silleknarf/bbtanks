package bedroombattletanks;

import jgame.JGObject;

class Rug extends Obstacle {			
	Rug(int x, int y, char orientation, GameInfo gameInfo) {
		super("rug",x, y, "rug"+orientation, gameInfo);
	}	
	public void hit(JGObject obj) {
	}
}
