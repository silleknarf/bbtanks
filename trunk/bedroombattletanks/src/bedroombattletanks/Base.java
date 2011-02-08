package bedroombattletanks;

import jgame.JGColor;
import jgame.JGObject;

class Base extends JGObject {
	Tank localTank;
	GameInfo localInfo;
	String localColour;
	int xOffset = 20;
	int yOffset = -10;
	Base(int x, int y, GameInfo gameInfo, Tank tank, String colour) {
		super("base",true, x, y, 8, colour+"base");
		new Flag(x+xOffset,y+yOffset,gameInfo, tank, colour);
		localInfo = gameInfo;
		localTank = tank;
		localColour = colour;
	}	
	public void hit(JGObject obj) {
		if (localInfo.gameLost==false) {
		if (obj.equals(localTank) && (localColour == "blue") && localInfo.tankData[1].gotFlag) {
			localInfo.winner = "Player 2";
			localInfo.loser = "Player 1";
			localInfo.winnerColor = JGColor.blue;
			localInfo.gameLost = true;
			eng.setGameState("GameOver");
		} else if (obj.equals(localTank) && (localColour == "red") && localInfo.tankData[0].gotFlag) {
			localInfo.winner = "Player 1";
			localInfo.loser = "Player 2";
			localInfo.winnerColor = JGColor.red;
			localInfo.gameLost = true;
			eng.setGameState("GameOver");
		}
		}
	}
}
