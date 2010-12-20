package bedroombattletanks;

import jgame.JGObject;

class Bed extends Obstacle {			
	Bed(int x, int y, char orientation, GameInfo gameInfo) {
		super("bed",x, y, "bed"+orientation, gameInfo);
	}			
	public void hit(JGObject obj) {
		obj.remove();
		
		new JGObject("boom",true,obj.x,obj.y,1,"tank-bit"+gameUtil.random(1,9,1),gameUtil.random(-10,10),gameUtil.random(-10,10),100);
	}
}
