package bedroombattletanks;

import jgame.JGObject;
import jgame.JGRectangle;

public abstract class Obstacle extends JGObject {
		int health = 3;
		GameInfo localInfo;
		Obstacle(String name, int x, int y, String image, GameInfo gameInfo) {
			super(name,true, x, y, 32, image );
			if (name != "rug")
				gameInfo.objects.add(getBBox());
			localInfo = gameInfo;
		}
		Obstacle(String name, int x, int y, String image, int healthValue, GameInfo gameInfo) {
			super(name,true, x, y, 32, image );
			if (name != "rug")
				gameInfo.objects.add(getBBox());
			health = healthValue;
			localInfo = gameInfo;
		}
		GameUtilities gameUtil = new GameUtilities();
		public void hit(JGObject obj) {
			obj.remove();
			health--;
			if (health <= 0) {
				remove();
				for ( JGRectangle removing : localInfo.objects ) {
						localInfo.objectIndex++;
						if (gameUtil.rectEquals(this.getBBox(), removing)) {
							break;
						}
				}
				localInfo.objects.remove(localInfo.objectIndex);
				localInfo.objectIndex = -1;
			}
		}
		
		public void paint() {
		}
		
}

