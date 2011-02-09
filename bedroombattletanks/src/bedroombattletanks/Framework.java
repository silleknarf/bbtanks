package bedroombattletanks;

import jgame.*;
import jgame.platform.*;




@SuppressWarnings("serial")

public class Framework extends JGEngine {

	public static void main(String [] args) {
		//new Framework(new JGPoint(640,480));
		//new Framework(new JGPoint(1024,768));
		new Framework(StdGame.parseSizeArgs(args,0));
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
	
	GameInfo gameInfo = new GameInfo(this);
	
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
			
			addGameState("GUI");
			addGameState("StartGame");
			addGameState("InGame");
			addGameState("GameOver");
			
			playAudio("channel1","menu",true);
			gameInfo.pfWidth = pfWidth();
			gameInfo.pfHeight = pfHeight();
			new GUI(gameInfo, this);
			setGameState("StartGame");
		}
		
		public void doFrameStartGame() {
			if (getKey(' ')) {
				setGameState("InGame");
			}
		}
		public void paintFrameStartGame() {
			setColor(JGColor.black);
			drawString("Press spacebar to start!",viewWidth()/2,viewHeight()/2,0);
		}
		
		public void startInGame() {
			// Music initialization
			new RandomAudio(this);
		}

	
		
       
        // Power-Ups
       
        // 200 is the number of frames before first power-up.
        int powerUpCountDown = 200;       
	
        

		// Some of the game meta logic is here
		
		public void doFrameInGame() {
			
			 // Power-Up code,
            // if timer is expired.
			if (gameInfo.gameLost==false) {
            if (powerUpCountDown <= 0) {
                    double newPowerUpNo = random(0,100);
                    if ( newPowerUpNo > 75 ) {
                           new CurseBox((int)random(0,pfWidth()),(int)random(0,pfHeight()), gameInfo);
                    } else if ( newPowerUpNo > 33 ){
                           new HealthBox((int)random(0,pfWidth()),(int)random(0,pfHeight()), gameInfo);
                    } else if (newPowerUpNo > 0) {
                    	new FrictionBox((int)random(0,pfWidth()),(int)random(0,pfHeight()), gameInfo);
                    }
                    if (newPowerUpNo > 0) {
                    	new WeaponBox((int)random(0,pfWidth()),(int)random(0,pfHeight()), gameInfo,"fireball-box","HomingMissile");
                    }
                    if (newPowerUpNo > 0) {
                    	new WeaponBox((int)random(0,pfWidth()),(int)random(0,pfHeight()), gameInfo,"grenade-box","Grenade");
                    }
                    
                    // else if (newPowerUpNo > 0) {
                    //	new MuddleBox("MuddleBox",(int)random(0,pfWidth()),(int)random(0,pfHeight()));
                    //}
                    powerUpCountDown += random(50,1000);
            } else { // count down.
                    powerUpCountDown--;
            }
			}
			
			// check if the game has ended
			
			if (!gameInfo.gameLost) {
			for (int i = 0; i < gameInfo.tankData.length; i++) {
				if (gameInfo.tankData[i].lives <= 0) {
					if (i==1) {
						gameInfo.winner = "Player 2";
						gameInfo.loser = "Player 1";
						gameInfo.winnerColor = JGColor.blue;
					} 
					if (i==0) {
						gameInfo.winner = "Player 1";
						gameInfo.loser = "Player 2";
						gameInfo.winnerColor = JGColor.red;
					}
				gameInfo.gameLost = true;
				setGameState("GameOver");
				}
			}
			}
			
			
			
            for (Effect effect : gameInfo.activeEffects) {
                    effect.framesLeft--;
                    if (effect.framesLeft <= 0) {
                            effect.finish();
                    }
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
			// Fireball - Tank Collision
			checkCollision(64,2);
			checkCollision(64,4);
			
			
			
			// Flag/Base - Tank Collision
			checkCollision(2,8);
			checkCollision(4,8);
            
			// Tank - Power Up collision
			checkCollision(2,16);
            checkCollision(4,16);
            
            // Bullet - Obstacle Collision
            checkCollision(1,32);
         
    
            
          // Fireball - Obstacle Collision
            checkCollision(64,32);
            
            // Bullet - Fireball Collision
            checkCollision(1,64);
		}
		
	
		
		/** Any graphics drawing beside objects or tiles should be done here.
		 * Usually, only status / HUD information needs to be drawn here. */
		public void paintFrame() {	
		}
		
		public void startGameOver(){
		}
		public void doFrameGameOver() {
			doFrameInGame();
		}
		
		public void paintFrameGameOver() {
			setColor(gameInfo.winnerColor);
			setFont(new JGFont(null, -1, 54));
			drawString("Game 0ver",viewWidth()/2,viewHeight()/2-90,0);
			setFont(new JGFont(null, -1, 24));
			drawString("All "+gameInfo.loser+ "'s base are belong to "+gameInfo.winner+".",viewWidth()/2,viewHeight()/2,0);
		//	drawString(winner +" you're winner.",viewWidth()/2,viewHeight()/2+26,0);
			drawString("Press space-bar to continue.",viewWidth()/2,viewHeight()/2+52,0);
			if (getKey(' ')) {
				gameInfo = new GameInfo(this);
				removeObjects(null,0);
				new GUI(gameInfo, this);
				setGameState("StartGame");
			}
		}
}