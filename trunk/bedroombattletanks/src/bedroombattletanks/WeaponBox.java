package bedroombattletanks;



//Gives the player an extra life.
class WeaponBox extends PowerUp {
		GameInfo localInfo;
		String localWeapon;
       
        WeaponBox(int x, int y, GameInfo gameInfo, String icon, String weapon) {
                super(weapon, x, y, icon);
                localInfo = gameInfo;
                localWeapon = weapon;
        }
       
        void effect(Tank activator) {
        		activator.secondaryWeapon = localWeapon;
        		this.remove();
                super.effect(activator);
        }
       
}
