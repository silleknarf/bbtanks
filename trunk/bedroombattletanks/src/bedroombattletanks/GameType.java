package bedroombattletanks;

public class GameType {
	
	GameType(GameInfo gameInfo){
	Tank tank1 = new Tank(2,"tank1", 180,
			 10,10,
			 'W','S','A','D','Q','E', gameInfo);
	Tank tank2 = new Tank(4,"tank2", 0,
			 924,668,
			 (char) jgame.impl.JGEngineInterface.KeyUp,
			 (char) jgame.impl.JGEngineInterface.KeyDown,
			 (char) jgame.impl.JGEngineInterface.KeyLeft,
			 (char) jgame.impl.JGEngineInterface.KeyRight, 
			 'M',',', gameInfo);
	gameInfo.allTanks.add(tank1);
	gameInfo.allTanks.add(tank2);
	}
}
