package bedroombattletanks;

public class Duel extends GameType {

	Duel(GameInfo gameInfo) {
		super(gameInfo);
		gameInfo.map1.add(new Rug(200, 200, 'V', gameInfo));
		gameInfo.map1.add(new Bed(0, 545, 'H', gameInfo));
		gameInfo.map1.add(new Bed(760, 0, 'V', gameInfo));
		gameInfo.map1.add(new Chair(800, 475, 'H', gameInfo));
		gameInfo.map1.add(new Chair(425, 50, 'H', gameInfo));
		gameInfo.map1.add(new Pencil(700, 300, 'V', gameInfo));
		gameInfo.map1.add(new Pencil(300, 500, 'H', gameInfo));
		}
}
