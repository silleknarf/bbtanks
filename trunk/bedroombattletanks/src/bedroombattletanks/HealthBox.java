package bedroombattletanks;



//Gives the player an extra life.
class HealthBox extends PowerUp {
		GameInfo localInfo;
       
        HealthBox(int x, int y, GameInfo gameInfo) {
                super("Health", x, y, "health-box");
                localInfo = gameInfo;
        }
       
        void effect(Tank activator) {
        		localInfo.tankData[(activator.colid/2)-1].lives++;
        		this.remove();
                super.effect(activator);
        }
       
}
