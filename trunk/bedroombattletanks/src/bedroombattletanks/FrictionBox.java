package bedroombattletanks;

 
class FrictionBox extends PowerUp {
                
	GameInfo localInfo;
	
	public FrictionBox(int x, int y, GameInfo gameInfo) {
		super("Frictionless",x,y, "friction-box");
		localInfo = gameInfo;
    }
               
	void effect(Tank activator) {
		for ( Tank tank : localInfo.allTanks ) {
			if (tank != activator) {
				localInfo.activeEffects.add(new Frictionless(2000,tank, localInfo));
			}
       	}
        remove();
	}                   
}