package bedroombattletanks;


//Removes a life from every other player.
class CurseBox extends PowerUp {
       
	GameInfo localInfo;
    CurseBox(int x, int y, GameInfo gameInfo) {
    	super("Curse",x,y, "curse-box");
        localInfo = gameInfo;
    }
       
    void effect(Tank activator) {
    	for ( Tank tank : localInfo.allTanks ) {
    		if (tank != activator) {
    			localInfo.tankData[(tank.colid/2)-1].lives--;
            }
        }
        super.effect(activator);
    }
}
