package bedroombattletanks;

import jgame.*;

class Tank extends JGObject {
			int orientation;
			char up; 
			char down;
			char rotateLeft; 
			char rotateRight;
			char fire;
			String player;
			JGColor playerColor;
			GameInfo localInfo;
			
			double tanksFriction; 
			
			int tankWidth = 7;
			int tankLength = 15;
			
			int tankCentreX = 21;
			int tankCentreY = 24;
			
			// Bullet Starting Distances
			int tankGunXDistance = 35; //21;
			int tankGunYDistance = 35;//48;
			
			
			GameUtilities gameUtil = new GameUtilities();

			// Method that is used to change orientation value so they stay between 0 and 360 degrees
			
			private int orientationAdd(int orientation, int angle) {
				if ((orientation+angle)>=360) 
					return (orientation+angle)-360;
				if ((orientation+angle)<0) 
					return (orientation+angle)+360;
				return orientation+angle;
			}
			private int rangeMaker(int orientation) {
				for (int i = 0; i <= 15; i++) {
					if ((orientation > (i*22.5)) && (orientation <= ((i*22.5)+22.5))) 
						return 15-i;
				}
				return 0;
			}
			
			public void changeFriction(double newFriction) {
		        tanksFriction = newFriction;
			}
	
			
			
			/** Constructor. */
			Tank (int collisionID, String name, int tankOrientation,
				  int startX, int startY,
				  char upKey, char downKey,
				  char rotateLeftKey, char rotateRightKey,
				  char fireKey,
				  GameInfo gameInfo) {
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
				
				localInfo = gameInfo;
				orientation = tankOrientation;
				up = upKey;
				down = downKey;
				rotateLeft = rotateLeftKey;
				rotateRight = rotateRightKey;
				fire = fireKey;
				localInfo.bulletTimer[collisionID] = 0;
				tanksFriction = localInfo.initialFriction;
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
				
				for (JGRectangle collisionCheck : localInfo.objects) {
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
					if (x >  eng.pfWidth() && xspeed>0) xspeed = -xspeed;
					if (x <          0 && xspeed<0) xspeed = -xspeed;
					if (y > eng.pfHeight() && yspeed>0) yspeed = -yspeed;
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

				if (eng.getKey(up)) {
					if (!((((orientation > 270) || (orientation < 90)) && ((y+tankCentreY < 0+tankCentreY)
							|| (itHit && hit == 2) )) ||
					       (((orientation < 270) && (orientation > 90)) && ((y+tankCentreY > eng.pfHeight()-tankCentreY)
					    	|| (itHit && hit == 0) )) ||
					       (((orientation > 180) && (orientation < 360)) && ((x+tankCentreX > eng.pfWidth()-tankCentreX)
					        || (itHit && hit == 3) )) ||
					       (((orientation > 0) && (orientation < 180)) && ((x+tankCentreX < 0+tankCentreX)
					    	|| (itHit && hit == 1) ))))
					 {
					yspeed = -localInfo.tankSpeed*Math.cos(Math.toRadians(orientation));
					xspeed = -localInfo.tankSpeed*Math.sin(Math.toRadians(orientation));
						keyDown = true;					
					}
				}
				
				// left and right keys change the orientation of the tank
				
				if (eng.getKey(rotateLeft)) {
					orientation = orientationAdd(orientation, 5);
				}
				if (eng.getKey(down)) {
					if (!((((orientation < 270) && (orientation > 90)) && ((y+tankCentreY < 0+tankCentreY)
							|| (itHit && hit == 2) )) ||
						 (((orientation > 270) || (orientation < 90)) && ((y+tankCentreY > eng.pfHeight()-tankCentreY)
						    || (itHit && hit == 0) )) ||
						 (((orientation > 0) && (orientation < 180)) && ((x+tankCentreX > eng.pfWidth()-tankCentreX)
						    || (itHit && hit == 3) )) ||
						 (((orientation > 180) && (orientation < 360)) && ((x+tankCentreX < 0+tankCentreX)
						    || (itHit && hit == 1) ))))
						 {
					yspeed = localInfo.tankSpeed*Math.cos(Math.toRadians(orientation));
					xspeed = localInfo.tankSpeed*Math.sin(Math.toRadians(orientation));
						keyDown = true;
					}
				}
				
				if (eng.getKey(rotateRight)) {
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
				
				if (eng.getKey(fire)) { 
					if (localInfo.tankData[colid][localInfo.bulletPosition] < localInfo.maxBullets && localInfo.bulletTimer[colid] == 0) {
					new Bullet(x+tankCentreX+(tankGunXDistance*-Math.sin(Math.toRadians(orientation))),
							   y+tankCentreY+(tankGunYDistance*-Math.cos(Math.toRadians(orientation))), 
							   -Math.sin(Math.toRadians(orientation)),
							   -Math.cos(Math.toRadians(orientation)), localInfo);
					localInfo.bulletTimer[colid] = localInfo.reloadTime;
					localInfo.tankData[colid][localInfo.bulletPosition]++;
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
			
			

			// Removes the tank and bullet when it is hit
			
			public void hit(JGObject obj) {
				obj.remove();
				localInfo.tankData[colid][localInfo.livesPosition]--;
				if (localInfo.tankData[colid][localInfo.livesPosition] <= 0){
					remove();
					for (int i = 0; i < gameUtil.random(50,80,1);i++) {
						new JGObject("boom",true,x,y,1,"tank-bit"+gameUtil.random(1,9,1),gameUtil.random(-10,10),gameUtil.random(-10,10),100);
					}
				}
				eng.playAudio("explosion");
			}
			
			public void paint() {
				eng.setColor(playerColor);
				eng.drawString(player,x+tankCentreX,y+62,0);
				eng.drawString("Lives:" + localInfo.tankData[colid][localInfo.livesPosition],x+tankCentreX,y+74,0);
				eng.drawRect(x+tankCentreX+(tankGunXDistance*-Math.sin(Math.toRadians(orientation))),
					     y+tankCentreY+(tankGunYDistance*-Math.cos(Math.toRadians(orientation))), 8,8, true, true);
			}
		}