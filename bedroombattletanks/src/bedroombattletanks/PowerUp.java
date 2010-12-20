package bedroombattletanks;

import jgame.JGObject;

// Creating the super class for the powerUps.
public abstract class PowerUp extends JGObject {
       
		String powerUpName;
	
        public void hit(JGObject obj) {
                effect((Tank) obj);
        }
        // Constructor.
    PowerUp(String name, int xLoc, int yLoc, String image) {
                super( name, true, xLoc, yLoc, 16, image);
                powerUpName = name;
    	}
       
        // What happens when you run over the power-up.
        // Activator is the number of the tank that runs over the power up.
        void effect(Tank activator) {
                this.remove();
        }
        public void paint() {
			eng.drawString(powerUpName,x+6,y-15,0);
        }

}
