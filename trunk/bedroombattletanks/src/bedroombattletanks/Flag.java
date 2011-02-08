package bedroombattletanks;

import jgame.JGObject;

class Flag extends JGObject {
	
	GameInfo obsList;
	Tank localTank;
	static GameUtilities gameUtil = new GameUtilities();
	/** Constructor. */
	Flag (int x, int y, GameInfo gameInfo, Tank tank, String colour) {
		super(
			"flag",// name by which the object is known
			true,//true means add a unique ID number after the object name.
			     //If we don't do this, this object will replace any object
			     //with the same name.
			x,  // X position
			y, // Y position
			8, // the object's collision ID (used to determine which classes
			   // of objects should collide with each other)
			colour+"flag", // name of sprite or animation to use (null is none)
			-1
		);
		obsList = gameInfo;
		localTank = tank;
	}
	public void hit(JGObject obj) {
		if (!obj.equals(localTank)) {
			remove();
			obsList.tankData[(obj.colid/2)-1].gotFlag = true;
		}
	}

}
