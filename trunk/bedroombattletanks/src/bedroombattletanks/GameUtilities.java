package bedroombattletanks;

import jgame.JGRectangle;

import java.lang.Math;

public class GameUtilities {
//Compares two rectangles and outputs true if they are both equal
	public boolean rectEquals(JGRectangle rect1, JGRectangle rect2) {	
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
	
	public double fullSpeed(double xSpeed, double ySpeed) {
		if (xSpeed>=0 && ySpeed>=0)
			return Math.sqrt((xSpeed*xSpeed) + (ySpeed*ySpeed));
		else 
			return -Math.sqrt((xSpeed*xSpeed) + (ySpeed*ySpeed));
	}
	public double normalSpeed(double xSpeed, double fullSpeed) {
		if (fullSpeed>=0)
			return Math.sqrt((fullSpeed*fullSpeed)-(xSpeed*xSpeed));
		else
			return -Math.sqrt((fullSpeed*fullSpeed)-(xSpeed*xSpeed));
	}
// Creates a random integer between start and finish 
// on the increment specified 
	public int random(int start, int finish, int inc) {
		int range = ((finish - start)/inc)+1;
		int[] selection = new int[range];
		for (int i = 0; i < range; i++) {
			selection[i] = start;
			start += inc;
		}
		int randomIndex = (int)(range * Math.random());
		return selection[randomIndex];
	}
// Creates a random integer between start and finish
	public int random(int start, int finish) {
		int range = (finish - start)+1;
		int[] selection = new int[range];
		for (int i = 0; i < range; i++) {
			selection[i] = start;
			start++;
		}
		int randomIndex = ((int)(range * Math.random()));
		return selection[randomIndex];
	}
}
