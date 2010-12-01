package bedroombattletanks;


import jgame.*;
import jgame.platform.*;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class Framework extends JGEngine {

	public static void main(String [] args) {
		//new Framework(new JGPoint(640,480));
		new Framework(new JGPoint(1224,868));
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
					 10,10,
					 'W','S','A','D','Q');
			Tank tank2 = new Tank(4,"tank2", 0,
					 924,668,
					 (char) KeyUp,(char) KeyDown,(char) KeyLeft,(char) KeyRight, 'M');
			
			allTanks.add(tank1);
			allTanks.add(tank2);
			map1.add(new Rug(200, 200, 'V'));
			map1.add(new Bed(0, 545, 'H'));
			map1.add(new Bed(760, 0, 'V'));
			map1.add(new Chair(800, 475, 'H'));
			map1.add(new Chair(425, 50, 'H'));
			map1.add(new Pencil(700, 300, 'V'));
			map1.add(new Pencil(300, 500, 'H'));

		}
		
		boolean rectEquals(JGRectangle rect1, JGRectangle rect2) {	
			if (
				(rect1.x == rect2.x) &&
				(rect1.y == rect2.y) &&
				(rect1.width == rect2.width) &&
				(rect1.height == rect2.height)) {
				return true;
				} else { 
				return false;
			}
		}
	// Setting some global variables
	
		boolean initialized = false;
		ArrayList<Tank> allTanks = new ArrayList<Tank>();
		int sizeObjects = -1;
		ArrayList<JGRectangle> objects = new ArrayList<JGRectangle>();
		ArrayList<Obstacle> map1 = new ArrayList<Obstacle>();
		 // Winner
        
        String winner;
        String loser;
        JGColor winnerColor;
        boolean gameLost;
       
        // Power-Ups
       
        // 200 is the number of frames before first power-up.
        int powerUpCountDown = 200;
        int powerUpCollisionID = 16;
        ArrayList<Effect> activeEffects = new ArrayList<Effect>();
       
        
       // object removal
        int objectIndex = -1;
		
	// Reloading 
		
		int[] bulletTimer = new int[5];	
		int reloadTime = 10;
	
	// Bullet Recovery
		int timer = 0;
		int tankBulletRecovery = 35;
	
	// Tank Speed and size
		int tankSpeed = 3;
		int tankWidth = 7;
		int tankLength = 15;
		
		int tankCentreX = 21;
		int tankCentreY = 24;
	
		// Friction
        double initialFriction = 0.5;
        double friction = initialFriction;
	
	// Bullet Starting Distances
		int tankGunXDistance = 35; //21;
		int tankGunYDistance = 35;//48;
		
	// Bullet Data
		double bulletSpeedMultiplier = 3.5;
		int maxBullets = 6;
		
	// Tank Lives
		int tankNumber = 5;
		int numberVariables = 4;
		int livesPosition = 1;
		int flagPosition = 2;
		int bulletPosition = 3;
		int numberLives = 10;
		
		int[][] tankData = new int [tankNumber][numberVariables];
		
	
		// Method that is used to change orientation value so they stay between 0 and 360 degrees
		
		int orientationAdd(int orientation, int angle) {
			if ((orientation+angle)>=360) 
				return (orientation+angle)-360;
			if ((orientation+angle)<0) 
				return (orientation+angle)+360;
			return orientation+angle;
		}
		
		int rangeMaker(int orientation) {
			for (int i = 0; i <= 15; i++) {
				if ((orientation > (i*22.5)) && (orientation <= ((i*22.5)+22.5))) 
					return 15-i;
			}
			return 0;
		}
		
		abstract class Obstacle extends JGObject {
			int health = 3;
			Obstacle(String name, int x, int y, String image) {
				super(name,true, x, y, 32, image );
				if (name != "rug")
					objects.add(getBBox());
			}
			Obstacle(String name, int x, int y, String image, int healthValue) {
				super(name,true, x, y, 32, image );
				if (name != "rug")
					objects.add(getBBox());
				health = healthValue;
			}
			

			public void hit(JGObject obj) {
				obj.remove();
				health--;
				if (health <= 0) {
					remove();
					for ( JGRectangle removing : objects ) {
						objectIndex++;
						if (rectEquals(this.getBBox(), removing)) {
							break;
						}
					}
					objects.remove(objectIndex);
					objectIndex = -1;
				}
			}
			public void paint() {
				
			}
		}
		
		class Chair extends Obstacle {		
			
			Chair(int x, int y, char orientation) {
				super("chair",x, y, "chair"+orientation, 15);
			}			
			public void hit(JGObject obj) {
				obj.remove();
				new JGObject("boom",true,obj.x,obj.y,1,"tank-bit"+random(1,9,1),random(-10,10),random(-10,10),100);
				health--;
				if (health <= 0) {
					remove();
					for ( JGRectangle removing : objects ) {
						objectIndex++;
						if (rectEquals(this.getBBox(), removing)) {
							break;
						}
					}
					objects.remove(objectIndex);
					objectIndex = -1;
					for (int i = 0; i < random(12,15,1); i++){
						new JGObject("boom",true,x,y,1,"tank-bit"+random(1,9,1),random(-10,10),random(-10,10),100);

					}
				}
			}
		}
		class Bed extends Obstacle {			
			Bed(int x, int y, char orientation) {
				super("bed",x, y, "bed"+orientation);
			}			
			public void hit(JGObject obj) {
				obj.remove();
				
				new JGObject("boom",true,obj.x,obj.y,1,"tank-bit"+random(1,9,1),random(-10,10),random(-10,10),100);
			}
		}
		class Pencil extends Obstacle {			
			Pencil(int x, int y, char orientation) {
				super("pencil",x, y, "pencil"+orientation );
			}			
		}
		class Rug extends Obstacle {			
			Rug(int x, int y, char orientation) {
				super("rug",x, y, "rug"+orientation );
			}	
			@Override
			public void hit(JGObject obj) {
			}
		}
		
		class Flag extends JGObject {
	
			
			/** Constructor. */
			Flag () {
				
				super(
					"flag",// name by which the object is known
					true,//true means add a unique ID number after the object name.
					     //If we don't do this, this object will replace any object
					     //with the same name.
					random(0, pfWidth()),  // X position
					random(0, pfHeight()), // Y position
					8, // the object's collision ID (used to determine which classes
					   // of objects should collide with each other)
					"flag", // name of sprite or animation to use (null is none)
					-1
				);
			}
			public void hit(JGObject obj) {
				remove();
				tankData[obj.colid][flagPosition] = 1;
			}
			public void paint() {
			}
		}
		
		  // Creating the super class for the powerUps.
        abstract class PowerUp extends JGObject {
               
        		String powerUpName;
        	
                public void hit(JGObject obj) {
                        effect((Tank) obj);
                }
                // Constructor.
            PowerUp(String name, int xLoc, int yLoc, String image) {
                        super( name, true, xLoc, yLoc, powerUpCollisionID, image);
                        powerUpName = name;
            	}
               
                // What happens when you run over the power-up.
                // Activator is the number of the tank that runs over the power up.
                void effect(Tank activator) {
                        this.remove();
                }
                public void paint() {
    				drawString(powerUpName,x+6,y-15,0);
                }
               
        }
       
        // Gives the player an extra life.
        class HealthBox extends PowerUp {
               
                HealthBox(String name, int x, int y) {
                        super(name, x, y, "health-box");
                }
               
                void effect(Tank activator) {
                        tankData[activator.colid][livesPosition]++;
                        this.remove();
                        super.effect(activator);
                }
               
        }
       
        // Removes a life from every other player.
        class CurseBox extends PowerUp {
               
                CurseBox(String name, int x, int y) {
                        super(name,x,y, "curse-box");
                }
               
                void effect(Tank activator) {
                        for ( Tank tank : allTanks ) {
                                if (tank != activator) {
                                        tankData[tank.colid][livesPosition]--;
                                }
                        }
                        super.effect(activator);

                }
               
        }
       
        class FrictionBox extends PowerUp {
                
                public FrictionBox(String name, int x, int y) {
                        super(name,x,y, "friction-box");
                       
                }
               
                void effect(Tank activator) {
                	for ( Tank tank : allTanks ) {
                        if (tank != activator) {
                        	activeEffects.add(new Frictionless(2000,tank));
                        }
                	}
                	remove();
                }         
               
        }
        
        class MuddleBox extends PowerUp {
        	
        	public MuddleBox(String name, int x, int y) {
        		super(name,x,y, "muddle-box");
        	}
        	
        	void effect(Tank activator) {
                for ( Tank tank : allTanks ) {
                    if (tank != activator) {
                    	activeEffects.add(new Muddle(2000, tank));
                    }
                }
                remove();
        	}
        	
        }
       
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
       
        class Frictionless extends Effect {
               
                boolean isAffected = false;
               
                Frictionless(int duration, Tank effected) {
                	super(duration,effected);
                	isAffected = true;
                }
               
                public void start() {
                	effected.changeFriction(0);
                }
               
                public void finish() {
                	if (!isAffected)	
                		effected.changeFriction(initialFriction);
                        isAffected = false;
                }
               
        }
       
		class Muddle extends Effect {
			char oriUp, oriDown, oriLeft, oriRight;
			
			Muddle(int duration, Tank effected) {
				super(duration, effected);
				oriUp = effected.getUp();
				oriDown = effected.getDown();
				oriLeft = effected.getRotateLeft();
				oriRight = effected.getRotateRight();
				System.out.println(oriUp);
			}
			
			
			
			void start() {
				effected.up = oriDown;
				effected.rotateLeft =(char)  oriRight;
				effected.down = (char) oriUp;
				effected.rotateRight = (char) oriLeft;
			}
			
			void finish() {
				effected.up = (char) oriUp;
				effected.rotateLeft = (char) oriLeft;
				effected.down = (char) oriDown;
				effected.rotateRight = (char) oriRight;
			}
		}
		
		// Class representing the bullet object
		
		class Bullet extends JGObject {
	
			
			/** Constructor. */
			Bullet (double tankX, double tankY, double xSpeed, double ySpeed) {
				
				super(
					"bullet",// name by which the object is known
					true,//true means add a unique ID number after the object name.
					     //If we don't do this, this object will replace any object
					     //with the same name.
					tankX,  // X position
					tankY, // Y position
					1, // the object's collision ID (used to determine which classes
					   // of objects should collide with each other)
					"paper-ball-small", // name of sprite or animation to use (null is none)
					600 // Lifespan of bullet in frames
				);
				// Give the object an initial speed in defined direction multplied by the global bullet speed variable.
				xspeed = xSpeed*bulletSpeedMultiplier;
				yspeed = ySpeed*bulletSpeedMultiplier;
			}

			/** Update the object. This method is called by moveObjects. */
			public void move() {
				
				// bounce off the borders of the screen.
				if (x >  pfWidth()-8 && xspeed>0) xspeed = -xspeed;
				if (x <          0 && xspeed<0) xspeed = -xspeed;
				if (y > pfHeight()-8 && yspeed>0) yspeed = -yspeed;
				if (y <          0 && yspeed<0) yspeed = -yspeed;
				// xspeed and yspeed are added to x and y automatically at the end
				// of the move() method.
			}// bounce off the borders of the screen.

		}
		
		class Tank extends JGObject {
			int orientation;
			char up; 
			char down;
			char rotateLeft; 
			char rotateRight;
			char fire;
			String player;
			JGColor playerColor;
			double tanksFriction = initialFriction;
			
			public char getUp() {
				return up;
			}
			public void setUp(char up) {
				this.up = up;
			}
			public char getDown() {
				return down;
			}
			public void setDown(char down) {
				this.down = down;
			}
			public char getRotateLeft() {
				return rotateLeft;
			}
			public void setRotateLeft(char rotateLeft) {
				this.rotateLeft = rotateLeft;
			}
			public char getRotateRight() {
				return rotateRight;
			}
			public void setRotateRight(char rotateRight) {
				this.rotateRight = rotateRight;
			}

			
			
			/** Constructor. */
			Tank (int collisionID, String name, int tankOrientation,
				  int startX, int startY,
				  char upKey, char downKey,
				  char rotateLeftKey, char rotateRightKey,
				  char fireKey) {
				// Initialise game object by calling an appropriate constructor
				// in the JGObject class.
				super(
					name,// name by which the object is known
					true,//true means add a unique ID number after the object name.
					     //If we don't do this, this object will replace any object
					     //with the same name.
					startX,  // X position
					startY, // Y position
					collisionID, // the object's collision ID (used to determine which classes
					   // of objects should collide with each other)
					"tank_0000" // name of sprite or animation to use (null is none)
				);
				
				orientation = tankOrientation;
				up = upKey;
				down = downKey;
				rotateLeft = rotateLeftKey;
				rotateRight = rotateRightKey;
				fire = fireKey;
				bulletTimer[collisionID] = 0;
				setBBox(7,9,27,30);
				if (collisionID == 2) {
					player = "Player 1";
					playerColor = JGColor.red;
				} else {
					player = "Player 2";
					playerColor = JGColor.blue;

				}
				
				}
			public void move() {
				int hit = 0;
				boolean itHit = false;
				
				for (JGRectangle collisionCheck : objects) {
					if (collisionCheck.intersects(getBBox())){
						if (y+tankCentreY <= collisionCheck.y+8){
							hit = 0;
						} else if (y+tankCentreY >= collisionCheck.y+collisionCheck.height-8) {
							hit = 2;
						} else if (x+tankCentreX <= collisionCheck.x+8) {
							hit = 3;
						} else if (x+tankCentreX >= collisionCheck.x+collisionCheck.width-8) {
							hit = 1;
						}
						itHit = true;
					}	
				}
				
				// bounce off the borders of the screen.
				if (tanksFriction <= 0) {
					if (x >  pfWidth() && xspeed>0) xspeed = -xspeed;
					if (x <          0 && xspeed<0) xspeed = -xspeed;
					if (y > pfHeight() && yspeed>0) yspeed = -yspeed;
					if (y <          0 && yspeed<0) yspeed = -yspeed;
					
				} 
				// records whether a key has been pressed this frame
				
				boolean keyDown = false;
				
				// Tank control setup
				
				// Multiplies the tanks speed by the cos and the sin of the orientation of the tank to find the 
				// x and y components of the tank direction
				
				// for example if the tank has an orientation of 180 degrees (pointing directly down) then there 
				// will only be a y component which is tank speed * -1 
				
				// if you don't understand this don't worry i can explain it to you

				if (getKey(up)) {
					if (!((((orientation > 270) || (orientation < 90)) && ((y+tankCentreY < 0+tankCentreY)
							|| (itHit && hit == 2) )) ||
					       (((orientation < 270) && (orientation > 90)) && ((y+tankCentreY > pfHeight()-tankCentreY)
					    	|| (itHit && hit == 0) )) ||
					       (((orientation > 180) && (orientation < 360)) && ((x+tankCentreX > pfWidth()-tankCentreX)
					        || (itHit && hit == 3) )) ||
					       (((orientation > 0) && (orientation < 180)) && ((x+tankCentreX < 0+tankCentreX)
					    	|| (itHit && hit == 1) ))))
					 {
					yspeed = -tankSpeed*Math.cos(Math.toRadians(orientation));
					xspeed = -tankSpeed*Math.sin(Math.toRadians(orientation));
						keyDown = true;					
					}
				}
				
				// left and right keys change the orientation of the tank
				
				if (getKey(rotateLeft)) {
					orientation = orientationAdd(orientation, 5);
				}
				if (getKey(down)) {
					if (!((((orientation < 270) && (orientation > 90)) && ((y+tankCentreY < 0+tankCentreY)
							|| (itHit && hit == 2) )) ||
						 (((orientation > 270) || (orientation < 90)) && ((y+tankCentreY > pfHeight()-tankCentreY)
						    || (itHit && hit == 0) )) ||
						 (((orientation > 0) && (orientation < 180)) && ((x+tankCentreX > pfWidth()-tankCentreX)
						    || (itHit && hit == 3) )) ||
						 (((orientation > 180) && (orientation < 360)) && ((x+tankCentreX < 0+tankCentreX)
						    || (itHit && hit == 1) ))))
						 {
					yspeed = tankSpeed*Math.cos(Math.toRadians(orientation));
					xspeed = tankSpeed*Math.sin(Math.toRadians(orientation));
						keyDown = true;
					}
				}
				
				if (getKey(rotateRight)) {
					orientation = orientationAdd(orientation, -5);
				}
				
				
				
				// slows the tank down when there are no keys pressed to a halt when it gets close to 0
				// this stops the movement from being jerky
				
				 if (keyDown == false) {
                     if ((xspeed < tanksFriction && xspeed > -tanksFriction)||(yspeed < tanksFriction && yspeed > -tanksFriction)) {
                             xspeed = 0;
                             yspeed = 0;
                     } else {
                             if (xspeed > 0)
                                     xspeed -= tanksFriction;
                             if (xspeed < 0)
                                     xspeed += tanksFriction;
                             if (yspeed > 0)
                                     yspeed -= tanksFriction;
                             if (yspeed < 0)
                                     yspeed += tanksFriction;
                     }
             }
				
				// creates a new bullet instance above the tank with the direction equal 
				// to the orientation of the tank
				
				if (getKey(fire)) { 
					if (tankData[colid][bulletPosition] < maxBullets && bulletTimer[colid] == 0) {
					new Bullet(x+tankCentreX+(tankGunXDistance*-Math.sin(Math.toRadians(orientation))),
							   y+tankCentreY+(tankGunYDistance*-Math.cos(Math.toRadians(orientation))), 
							   -Math.sin(Math.toRadians(orientation)),
							   -Math.cos(Math.toRadians(orientation)));
					bulletTimer[colid] = reloadTime;
					tankData[colid][bulletPosition]++;
					}
				}
				
				
				switch (rangeMaker(orientation)) {
				case 0: setGraphic("tank_0000"); break;
				case 1: setGraphic("tank_0225"); break;
				case 2: setGraphic("tank_0450"); break;
				case 3: setGraphic("tank_0675"); break;
				case 4: setGraphic("tank_0900"); break;
				case 5: setGraphic("tank_1125"); break;
				case 6: setGraphic("tank_1350"); break;
				case 7: setGraphic("tank_1575"); break;
				case 8: setGraphic("tank_1800"); break;
				case 9: setGraphic("tank_2025"); break;
				case 10: setGraphic("tank_2250"); break;
				case 11: setGraphic("tank_2475"); break;				
				case 12: setGraphic("tank_2700"); break;
				case 13: setGraphic("tank_2925"); break;
				case 14: setGraphic("tank_3150"); break;
				case 15: setGraphic("tank_3375"); break;
				}
				
			}
			
			public void changeFriction( double newFriction ) {
                tanksFriction = newFriction;
			}
			
			

			// Removes the tank and bullet when it is hit
			
			public void hit(JGObject obj) {
				obj.remove();
				tankData[colid][livesPosition]--;
				if (tankData[colid][livesPosition] <= 0){
					remove();
					for (int i = 0; i < random(50,80,1);i++) {
						new JGObject("boom",true,x,y,1,"tank-bit"+random(1,9,1),random(-10,10),random(-10,10),100);
					}
				}
				playAudio("explosion");
			}
			
			public void paint() {
				setColor(playerColor);
				drawString(player,x+tankCentreX,y+62,0);
				drawString("Lives:" + tankData[colid][livesPosition],x+tankCentreX,y+74,0);
				drawRect(x+tankCentreX+(tankGunXDistance*-Math.sin(Math.toRadians(orientation))),
					     y+tankCentreY+(tankGunYDistance*-Math.cos(Math.toRadians(orientation))), 8,8, true, true);
			}
		}
			
		

		
		// Some of the game meta logic is here
		
		public void doFrameInGame() {
			
			sizeObjects = objects.size();
			
			// initializing arrays
			if (initialized == false) {
				for (int i  = 0; i < bulletTimer.length; i++) {
					bulletTimer[i] = 0;
				}checkCollision(2,16);
                checkCollision(4,16);
				for (int i  = 0; i < tankData.length; i++) {
					tankData[i][livesPosition] = numberLives;
					tankData[i][flagPosition] = 0;
					tankData[i][bulletPosition] = 0;
				}
				initialized = true;
			}
			// check if the game has ended
			
			if (!gameLost) {
			for (int i  = 0; i < tankData.length; i++) {
				if (tankData[i][livesPosition] <= 0) {
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
                           new CurseBox("Curse",(int)random(0,pfWidth()),(int)random(0,pfHeight()));
                    } else if ( newPowerUpNo > 33 ){
                           new HealthBox("Health",(int)random(0,pfWidth()),(int)random(0,pfHeight()));
                    } else if (newPowerUpNo > 0) {
                    	new FrictionBox("Frictionless",(int)random(0,pfWidth()),(int)random(0,pfHeight()));
                    }// else if (newPowerUpNo > 0) {
                    //	new MuddleBox("MuddleBox",(int)random(0,pfWidth()),(int)random(0,pfHeight()));
                    //}
                    powerUpCountDown += random(50,1000);
            } else { // count down.
                    powerUpCountDown--;
            }
            for (Effect effect : activeEffects) {
                    effect.framesLeft--;
                    if (effect.framesLeft <= 0) {
                            effect.finish();
                    }
            }
			
			// timer only deals with the bullet recovery of the tanks
			
			for (int i  = 0; i < bulletTimer.length; i++) {
				if (bulletTimer[i] > 0) {
					bulletTimer[i]--;
				}
			}
			
			timer++;
			if (timer % tankBulletRecovery == 0) {
				if (tankData[2][bulletPosition] > 0)
					tankData[2][bulletPosition]--;
				if (tankData[4][bulletPosition] > 0)
					tankData[4][bulletPosition]--;
			}
			
			// moves all the objects that need to move
			
			moveObjects(
					null,// object name prefix of objects to move (null means any)
					0    // object collision ID of objects to move (0 means any)
				);
			
			checkCollision(
					1, // cids of objects that our objects should collide with
					2  // cids of the objects whose hit() should be called
				);
			checkCollision(1,4);
			
			//flag checks
			checkCollision(2,8);
			checkCollision(4,8);

			checkCollision(2,16);
            checkCollision(4,16);
            checkCollision(1,32);
		}
		
	
		
		/** Any graphics drawing beside objects or tiles should be done here.
		 * Usually, only status / HUD information needs to be drawn here. */
		public void paintFrame() {	
			/*	
			setColor(JGColor.red);
			
			drawString("Player 1",60,viewHeight()-90,0);
			drawString("Lives: "+tankData[2][livesPosition],60,viewHeight()-70,0);
			if (tankData[2][flagPosition] == 1)
				drawString("Got flag!", 60,viewHeight()-50,0);
			
			setColor(JGColor.blue);

			
			drawString("Player 2",viewWidth()-60,viewHeight()-90,0);
			drawString("Lives: "+tankData[4][livesPosition],viewWidth()-60,viewHeight()-70,0);
			if (tankData[4][flagPosition] == 1)
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
			drawString("G4m3 0v3r",viewWidth()/2,viewHeight()/2-90,0);
			setFont(new JGFont(null, -1, 24));
			drawString("All "+loser+ "'s base are belong to "+winner+".",viewWidth()/2,viewHeight()/2,0);
			drawString(winner +" you're winner.",viewWidth()/2,viewHeight()/2+26,0);
			drawString("Press space-bar to continue.",viewWidth()/2,viewHeight()/2+52,0);
			if (getKey(' ')) {
				removeObjects(null,0);
				setGameState("InGame");
			}
		}
}