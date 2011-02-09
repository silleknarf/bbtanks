package bedroombattletanks;

import java.util.ArrayList;

import jgame.JGObject;
import jgame.JGRectangle;
import jgame.platform.JGEngine;

public class MapGenerator {
	JGEngine localEng;
	GameInfo localInfo;
	GameUtilities gameUtil = new GameUtilities();
	int mapWidth = 64;
	int mapHeight = 48;
	int x;
	int y;
	boolean[][] objectMap = new boolean[mapWidth][mapHeight];
	ArrayList<int[]> usableMap = new ArrayList<int[]>();
	
	MapGenerator(GameInfo gameInfo, JGEngine eng) {
		localEng = eng;
		localInfo = gameInfo;
		objectMap = initialiseObjectMap(objectMap);
		//new PathFinder(new boolean[][]{{true,true,true,true},{true,true,true,true},{true,true,true,true},{true,true,true,true}}, 0, 0, 3, 3);
		//printGrid(objectMap);
		placeObject(new Rug(0, 0, 'V', gameInfo));
		for (int i =  1; i <= eng.random(1, 2); i++) {
			switch((int)eng.random(1, 2)) {
			case 1: placeObject(new Bed(0, 0, 'H', gameInfo));
			case 2: placeObject(new Bed(0, 0, 'V', gameInfo));
			}
		}
		for (int i =  1; i <= eng.random(1, 2); i++) {
			switch((int)eng.random(1, 2)) {
			case 1: placeObject(new Chair(0, 0, 'H', gameInfo));
			case 2: placeObject(new Chair(0, 0, 'V', gameInfo));
			}
		}
		for (int i =  1; i <= eng.random(1, 15); i++) {
			switch((int)eng.random(1, 2)) {
			case 1: placeObject(new Pencil(0, 0, 'H', gameInfo));
			case 2: placeObject(new Pencil(0, 0, 'V', gameInfo));
			}
		} 
		printGrid(objectMap);
	}
	boolean[][] initialiseObjectMap(boolean[][] fullObjectMap) {
		for (int i = 0; i < mapWidth; i++) {
			for (int j = 0; j < mapHeight; j++) {
				fullObjectMap[i][j] = true;
			}
		}
		fullObjectMap = clearBases(fullObjectMap);
		fullObjectMap = clearPath(fullObjectMap);
		return fullObjectMap;
	}
	void printGrid(boolean[][] printMap){
		for (int j = 0; j < mapHeight; j++) {
			for (int i = 0; i < mapWidth; i++) {
				if (printMap[i][j])
					System.out.print("1,");
				else 
					System.out.print("0,");
			}
			System.out.println();
		}
	}
	
	void findUsableMapForShape(int width, int height){
		usableMap =  new ArrayList<int[]>();
		int searchGridX = mapWidth - width;
		int searchGridY = mapHeight - height;
		
		// Main grid search
		for (int i = 0; i < searchGridX; i++) {
			for (int j = 0; j < searchGridY; j++) {
				// Shape grid check
				boolean usable = true;
				for (int x = 0; x < width; x++) {
					for (int y = 0; y < height; y++){
						if (objectMap[i+x][j+y] == false) {
							usable = false;
						}
					}
				}
				int[] coords = {i,j};
				if (usable) {
					usableMap.add(coords);
				}
			}
		}
	};
	
	boolean placeShapeRandomly(int width, int height){
		if (usableMap.size() != 0) {
			int randomPoint = (int) localEng.random(1,(double)usableMap.size()-1);
			x = usableMap.get(randomPoint)[0]*16;
			y = usableMap.get(randomPoint)[1]*16;
	
			for (int j = 0; j < height; j++){
				for (int i = 0; i < width; i++){
					objectMap[usableMap.get(randomPoint)[0]+i][usableMap.get(randomPoint)[1]+j] = false;
				}
			}
			return true;
		} else {
			return false;
		}
	}
	void printList(ArrayList<int[]> arrayList){
		for (int[] item : arrayList) {
			System.out.print("(");
			System.out.print(item[0]);
			System.out.print(", ");
			System.out.print(item[1]);
			System.out.print(")");
			System.out.println();
		}
	}
	
	boolean[][] clearBases(boolean[][] fullObjectMap) {
		int squareSide = 15;
		int gameWidth = 64;
		int gameHeight = 48;
		for (int i = 0; i < squareSide; i++) {
			for (int j = 0; j < squareSide; j++) {
				fullObjectMap[i][j] = false;
				fullObjectMap[(gameWidth-1)-i][(gameHeight-1)-j] = false;
			}
		}
		return fullObjectMap;
	}
	
	boolean[][] clearPath(boolean[][] fullObjectMap) {
		
		DiagonalPath diag = new DiagonalPath(0,0,mapWidth-1,mapHeight-1);
		for (int[] item : diag.queue) {
			for (int i = -2; i < 3; i++) {
				if (item[0]+i >= 0 && item[0]+i < mapWidth)
					fullObjectMap[item[0]+i][item[1]] = false;
				if (item[1]+i >= 0 && item[1]+i < mapHeight)
					fullObjectMap[item[0]][item[1]+i] = false;
			}
		}
		return fullObjectMap;
	}


//		PathFinder middleDiagonal = new PathFinder(fullObjectMap,0,mapWidth-1,mapHeight-1,0);
//		PathFinder middleDiagonal = new PathFinder(fullObjectMap,0,0,3,3);
/*		for (int[] item : middleDiagonal.queue) {
			fullObjectMap[item[0]][item[1]] = false;
		}
		return fullObjectMap;
		*/ 

	
	void placeObject(Obstacle obs){
		int obsWidth = (obs.getBBox().width/16)+1;
		int obsHeight = (obs.getBBox().height/16)+1;
		findUsableMapForShape(obsWidth, obsHeight);
		
		for ( JGRectangle removing : localInfo.objects ) {
			localInfo.objectIndex++;
			if (gameUtil.rectEquals(obs.getBBox(), removing)) {
				break;
			}
		}
		if (localInfo.objects.size()!=0) 
			localInfo.objects.remove(localInfo.objectIndex);
		localInfo.objectIndex = -1;
		

		if (placeShapeRandomly(obsWidth, obsHeight)) {
		//	if (x!=0) {
				obs.x = x;
				obs.y = y;
				localInfo.objects.add(obs.getBBox());
				localInfo.map1.add(obs);
		//	} else {
		//		System.out.println("Cant place 1");
		//		localEng.removeObjects(obs.getName(),0,true);
		//	}
		} else {
			System.out.println("Cant place 2");
			localEng.removeObjects(obs.getName(),0,true);
		}
	}
}
