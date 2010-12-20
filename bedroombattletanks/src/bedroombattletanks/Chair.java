package bedroombattletanks;

import jgame.*;

class Chair extends Obstacle {		
	
	Chair(int x, int y, char orientation, GameInfo gameInfo) {
		super("chair",x, y, "chair"+orientation, 15, gameInfo);
	}			
	GameUtilities gameUtil = new GameUtilities();
	public void hit(JGObject obj, GameInfo gameInfo) {
		obj.remove();
		new JGObject("boom",true,obj.x,obj.y,1,"tank-bit"+gameUtil.random(1,9,1),gameUtil.random(-10,10),gameUtil.random(-10,10),100);
		health--;
		if (health <= 0) {
			remove();
			for ( JGRectangle removing : gameInfo.objects ) {
				gameInfo.objectIndex++;
				if (gameUtil.rectEquals(this.getBBox(), removing)) {
					break;
				}
			}
			gameInfo.objects.remove(gameInfo.objectIndex);
			gameInfo.objectIndex = -1;
			for (int i = 0; i < gameUtil.random(12,15,1); i++){
				new JGObject("boom",true,x,y,1,"tank-bit"+gameUtil.random(1,9,1),gameUtil.random(-10,10),gameUtil.random(-10,10),100);

			}
		}
	}
}
