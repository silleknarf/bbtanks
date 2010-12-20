package bedroombattletanks;


abstract class Effect {
	int framesLeft;
    Tank effected;
               
    // Constructor.
    // Also runs the start method.
    Effect(int duration, Tank obj) {
    	framesLeft = duration;
        this.effected = obj;
        this.start();
    }
               
    // What happens when the effect starts.
    abstract void start();
               
    // What happens when framesLeft becomes zero.
    abstract void finish();
}