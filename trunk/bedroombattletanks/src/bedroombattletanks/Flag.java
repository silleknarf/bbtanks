package bedroombattletanks;

import jgame.JGObject;

class Flag extends JGObject {
	
	GameInfo obsList;
	static GameUtilities gameUtil = new GameUtilities();
	/** Constructor. */
	Flag (GameInfo gameInfo) {
		super(
			"flag",// name by which the object is known
			true,//true means add a unique ID number after the object name.
			     //If we don't do this, this object will replace any object
			     //with the same name.
			gameUtil.random(0, gameInfo.pfWidth),  // X position
			gameUtil.random(0, gameInfo.pfHeight), // Y position
			8, // the object's collision ID (used to determine which classes
			   // of objects should collide with each other)
			"flag", // name of sprite or animation to use (null is none)
			-1
		);
		obsList = gameInfo;
	}
	public void hit(JGObject obj) {
		remove();
		obsList.tankData[obj.colid][obsList.flagPosition] = 1;
	}
	//public void paint() {
	//}
}
