package bedroombattletanks;

class Pencil extends Obstacle {			
	Pencil(int x, int y, char orientation, GameInfo gameInfo) {
		super("pencil",x, y, "pencil"+orientation, gameInfo);
	}			
}
