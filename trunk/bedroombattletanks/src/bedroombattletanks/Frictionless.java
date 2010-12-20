package bedroombattletanks;


class Frictionless extends Effect {
	
	boolean isAffected = false;
	GameInfo localInfo;
    
	
	
	Frictionless(int duration, Tank effected, GameInfo gameInfo) {
    	super(duration,effected);
    	isAffected = true;
    	localInfo = gameInfo;
    }
               
    public void start() {
    	effected.changeFriction(0);
    }
               
    public void finish() {
    	if (!isAffected) {
        	effected.changeFriction(localInfo.initialFriction);
            isAffected = false;
        }
    }
}