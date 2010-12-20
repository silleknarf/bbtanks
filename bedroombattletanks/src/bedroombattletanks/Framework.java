package bedroombattletanks;

import jgame.*;
import jgame.platform.*;
import java.util.ArrayList;




@SuppressWarnings("serial")

public class Framework extends JGEngine {

	

	public static void main(String [] args) {
		//new Framework(new JGPoint(640,480));
		new Framework(new JGPoint(1024,768));
		//new Framework(StdGame.parseSizeArgs(args,0));
	}

	public Framework() {
		// This inits the engine as an applet.
		initEngineApplet(); 
	}

	public Framework(JGPoint size) {
		// This inits the engine as an application.
		initEngine(size.x,size.y); 
	}

	public void initCanvas() {
		// The only thing we need to do in this method is to tell the engine
		// what canvas settings to use.  We should not yet call any of the
		// other game engine methods here!
		setCanvasSettings(
			64,  // width of the canvas in tiles
			48,  // height of the canvas in tiles
			16,  // width of one tile
			16,  // height of one tile
			     //    (note: total size = 20*16=320  x  15*16=240)
			null,// foreground colour -> use default colour white
			new JGColor(0,0,0),// background colour -> use default colour black
			null // standard font -> use default font
		);
	}

	/** This method is called when the engine has finished initialising and is
	 * ready to produce frames.  Note that the engine logic runs in its own
	 * thread, separate from the AWT event thread, which is used for painting
	 * only.  This method is the first to be called from within its thread.
	 * During this method, the game window shows the intro screen. */
	
	public void initGame() {
			setFrameRate(
				35,// 35 = frame rate, 35 frames per second
				2  //  2 = frame skip, skip at most 2 frames before displaying
				   //      a frame again
			);
			
			defineMedia("/resources/data.tbl");		
			
			try { Thread.sleep(100); }
			catch (InterruptedException e) {}
			
			
			try { Thread.sleep(100); }
			catch (InterruptedException e) {}
			
			addGameState("StartGame");
			addGameState("InGame");
			addGameState("GameOver");
			
			setBGImage("wooden-floor");
			
			setGameState("StartGame");

			
		}
	
		
		public void doFrameStartGame() {		
			if (getKey(' ')) {
				setGameState("InGame");
			}
		}
		public void paintFrameStartGame() {
			setColor(JGColor.black);
			drawString("Pr355 5p4c3bar 4 pwn4g3!",viewWidth()/2,viewHeight()/2,0);
		}
		
		public void startInGame() {
			playAudio("channel1","our-score",true);
			gameLost = false;
			initialized = false;
			Tank tank1 = new Tank(2,"tank1", 180,
					 100,100,
					 'W','S','A','D','Q', gameInfo);
		/*	Tank tank1 = new Tank(2,"tank1", 180,
					 10,10,
					 'W','S','A','D','Q', gameInfo);*/
			Tank tank2 = new Tank(4,"tank2", 0,
					 924,668,
					 (char) KeyUp,(char) KeyDown,(char) KeyLeft,(char) KeyRight, 'M', gameInfo);
		
			
			map1.add(new Rug(200, 200, 'V', gameInfo));
			map1.add(new Bed(0, 545, 'H', gameInfo));
			map1.add(new Bed(760, 0, 'V', gameInfo));
			map1.add(new Chair(800, 475, 'H', gameInfo));
			map1.add(new Chair(425, 50, 'H', gameInfo));
			map1.add(new Pencil(700, 300, 'V', gameInfo));
			map1.add(new Pencil(300, 500, 'H', gameInfo));
			gameInfo.allTanks.add(tank1);
			gameInfo.allTanks.add(tank2);
		}
		
		
	// Setting some global variables
	
		boolean initialized = false;
		int sizeObjects = -1;
		GameInfo gameInfo = new GameInfo();
		//ArrayList<JGRectangle> objects = new ArrayList<JGRectangle>();
		ArrayList<Obstacle> map1 = new ArrayList<Obstacle>();
		
		// Winner
        
        String winner;
        String loser;
        JGColor winnerColor;
        boolean gameLost;
       
        // Power-Ups
       
        // 200 is the number of frames before first power-up.
        int powerUpCountDown = 200;       
		
	
	
	// Bullet Recovery
		int timer = 0;
		int tankBulletRecovery = 35;
		
	
	// Friction
        double friction = gameInfo.initialFriction;
	
	
       
       
        
       
       
       
		
		
		
		
		
			
		

		
		// Some of the game meta logic is here
		
		public void doFrameInGame() {
			
			sizeObjects = gameInfo.objects.size();
			
			// initializing arrays
			if (initialized == false) {
				for (int i  = 0; i < gameInfo.bulletTimer.length; i++) {
					gameInfo.bulletTimer[i] = 0;
				}
				checkCollision(2,16);
                checkCollision(4,16);
				for (int i  = 0; i < gameInfo.tankData.length; i++) {
					gameInfo.tankData[i][gameInfo.livesPosition] = gameInfo.numberLives;
					gameInfo.tankData[i][gameInfo.flagPosition] = 0;
					gameInfo.tankData[i][gameInfo.bulletPosition] = 0;
				}
				initialized = true;
			}
			// check if the game has ended
			
			if (!gameLost) {
			for (int i  = 0; i < gameInfo.tankData.length; i++) {
				if (gameInfo.tankData[i][gameInfo.livesPosition] <= 0) {
					if (i==2) {
						winner = "Player 2";
						loser = "Player 1";
						winnerColor = JGColor.blue;
					} else {
						winner = "Player 1";
						loser = "Player 2";
						winnerColor = JGColor.red;
					}
				gameLost = true;
				setGameState("GameOver");
				}
			}
			}
			
			 // Power-Up code,
            // if timer is expired.
            if (powerUpCountDown <= 0) {
                    double newPowerUpNo = random(0,100);
                    if ( newPowerUpNo > 75 ) {
                           new CurseBox((int)random(0,pfWidth()),(int)random(0,pfHeight()), gameInfo);
                    } else if ( newPowerUpNo > 33 ){
                           new HealthBox((int)random(0,pfWidth()),(int)random(0,pfHeight()), gameInfo);
                    } else if (newPowerUpNo > 0) {
                    	new FrictionBox((int)random(0,pfWidth()),(int)random(0,pfHeight()), gameInfo);
                    }// else if (newPowerUpNo > 0) {
                    //	new MuddleBox("MuddleBox",(int)random(0,pfWidth()),(int)random(0,pfHeight()));
                    //}
                    powerUpCountDown += random(50,1000);
            } else { // count down.
                    powerUpCountDown--;
            }
            for (Effect effect : gameInfo.activeEffects) {
                    effect.framesLeft--;
                    if (effect.framesLeft <= 0) {
                            effect.finish();
                    }
            }
			
			// timer only deals with the bullet recovery of the tanks
			
			for (int i  = 0; i < gameInfo.bulletTimer.length; i++) {
				if (gameInfo.bulletTimer[i] > 0) {
					gameInfo.bulletTimer[i]--;
				}
			}
			
			timer++;
			if (timer % tankBulletRecovery == 0) {
				if (gameInfo.tankData[2][gameInfo.bulletPosition] > 0)
					gameInfo.tankData[2][gameInfo.bulletPosition]--;
				if (gameInfo.tankData[4][gameInfo.bulletPosition] > 0)
					gameInfo.tankData[4][gameInfo.bulletPosition]--;
			}
			
			// moves all the objects that need to move
			
			moveObjects(
					null,// object name prefix of objects to move (null means any)
					0    // object collision ID of objects to move (0 means any)
				);
			
			// Bullet - Tank Collision
			checkCollision(
					1, // cids of objects that our objects should collide with
					2  // cids of the objects whose hit() should be called
				);
			checkCollision(1,4);
			
			// Flag - Tank Collision
			checkCollision(2,8);
			checkCollision(4,8);
            
			// Tank - Power Up collision
			checkCollision(2,16);
            checkCollision(4,16);
            
            // Bullet - Obstacle Collision
            checkCollision(1,32);
		}
		
	
		
		/** Any graphics drawing beside objects or tiles should be done here.
		 * Usually, only status / HUD information needs to be drawn here. */
		public void paintFrame() {	
			/*	
			setColor(JGColor.red);
			
			drawString("Player 1",60,viewHeight()-90,0);
			drawString("Lives: "+gameInfo.tankData[2][livesPosition],60,viewHeight()-70,0);
			if (gameInfo.tankData[2][flagPosition] == 1)
				drawString("Got flag!", 60,viewHeight()-50,0);
			
			setColor(JGColor.blue);

			
			drawString("Player 2",viewWidth()-60,viewHeight()-90,0);
			drawString("Lives: "+gameInfo.tankData[4][livesPosition],viewWidth()-60,viewHeight()-70,0);
			if (gameInfo.tankData[4][flagPosition] == 1)
				drawString("Got flag!", viewWidth()-60,viewHeight()-50,0);
*/
		}
		
		public void startGameOver(){
		}
		public void doFrameGameOver() {
			doFrameInGame();
		}
		
		public void paintFrameGameOver() {
			setColor(winnerColor);
			setFont(new JGFont(null, -1, 54));
			drawString("Game 0ver",viewWidth()/2,viewHeight()/2-90,0);
			setFont(new JGFont(null, -1, 24));
			drawString("All "+loser+ "'s base are belong to "+winner+".",viewWidth()/2,viewHeight()/2,0);
		//	drawString(winner +" you're winner.",viewWidth()/2,viewHeight()/2+26,0);
			drawString("Press space-bar to continue.",viewWidth()/2,viewHeight()/2+52,0);
			if (getKey(' ')) {
				gameInfo.allTanks = new ArrayList<Tank>();
				gameInfo.activeEffects = new ArrayList<Effect>();
				removeObjects(null,0);
				setGameState("InGame");
			}
		}
}