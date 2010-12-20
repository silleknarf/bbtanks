package bedroombattletanks;

 
class MuddleBox extends PowerUp {
        	
	GameInfo localInfo;
        	public MuddleBox(String name, int x, int y, GameInfo gameInfo) {
        		super(name,x,y, "muddle-box");
        		localInfo = gameInfo;
        	}
        	
        	void effect(Tank activator) {
                for ( Tank tank : localInfo.allTanks ) {
                    if (tank != activator) {
                    	localInfo.activeEffects.add(new Muddle(2000, tank));
                    }
                }
                remove();
        	}
}