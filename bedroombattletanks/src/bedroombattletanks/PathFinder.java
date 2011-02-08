package bedroombattletanks;

import java.util.ArrayList;

public class PathFinder {
	ArrayList<int[]> queue = new ArrayList<int[]>();
	ArrayList<int[]> queue2 = new ArrayList<int[]>();
	ArrayList<int[]> nearbyCells = new ArrayList<int[]>();
	ArrayList<int[]> adjacentList;
	ArrayList<int[]> finalPath = new ArrayList<int[]>();
	
	int[] outputAdjacent;
	int minAdjacent;
	int[] adjacent;
	int index = 0;
	int queueIndex = 0;
	boolean firstItem = true;
	int localStartX;
	int localStartY;
	int localFinishX;
	int localFinishY;
	boolean[][] localMap;
	
	PathFinder(boolean[][] map, int startX, int startY, int finishX, int finishY) {
		localMap = map;
		queue.add(new int[]{finishX,finishY, index});
		localStartX = startX;
		localStartY = startY;
		localFinishX = finishX;
		localFinishY = finishY;
		queue = findPathList();
		for (int[] item : queue) {
			System.out.print(item[0]);
			System.out.print(", ");
			System.out.print(item[1]);
			System.out.print(", ");
			System.out.print(item[2]);
			System.out.println();
		}
	/*	queue = removeTail(queue,1);
		for (int[] item : queue) {
			System.out.print(item[0]);
			System.out.print(", ");
			System.out.print(item[1]);
			System.out.println();
		} */
		queue = findPath(queue);
		for (int[] item : queue) {
			System.out.print(item[0]);
			System.out.print(", ");
			System.out.print(item[1]);
			System.out.println();
		}
		
	};
	
	ArrayList<int[]> findPathList() {
		int itemCount = 0;
		int last = 1;
		boolean pathFound = false;
		while (index < 150 && !pathFound) {
			while (!pathFound) {
				for (int[] thing : queue){
					System.out.print(thing[0]);
					System.out.print(", ");
					System.out.println(thing[1]);
				}
				int[] item = queue.get(itemCount);
				// Break if index has increased
				int current = item[2];
				if (current > last) {
					last = item[2];
					break;
				}
				itemCount++;
				last = item[2];
				// Find surrounding co-ords
				
				nearbyCells = new ArrayList<int[]>();
				nearbyCells.add(new int[]{item[0]+1,item[1],index+1});
				nearbyCells.add(new int[]{item[0]-1,item[1],index+1});
				nearbyCells.add(new int[]{item[0],item[1]+1,index+1});
				nearbyCells.add(new int[]{item[0],item[1]-1,index+1});

				// Remove invalid cells
				
				ArrayList<int[]> toRemove = new ArrayList<int[]>();
				for (int[] cell : nearbyCells) {
					// remove off grid cells
					boolean onGrid = true;
					if (cell[0]<0 || cell[0]>=localMap[0].length || cell[1]<0 || cell[1]>=localMap.length)
						{
						onGrid = false;
						}
					
					// remove used cells
					boolean isUsed = false;
				
					//if (onGrid)
						//if (!localMap[cell[0]][cell[1]])
							//isUsed = true;
					
					if (!onGrid || isUsed)
						toRemove.add(cell);
				}
				for (int[] remove : toRemove) 
					nearbyCells.remove(remove);
					
				// Remove already used by the pathfinder cells
					
					toRemove = new ArrayList<int[]>();
					for (int[] cell : nearbyCells) {
						for (int[] check : queue) {
							if (check[0] == cell[0] && check[1] == cell[1] && check[2] <= cell[2]) {
								toRemove.add(cell);
							}
						}
					}
					for (int[] remove : toRemove) 
						nearbyCells.remove(remove);	
					
					for (int[] add : nearbyCells) { 
						queue.add(add);
						if (add[0]==localStartX && add[1]==localStartY) 
							pathFound = true;
					}
			}
			index++;
		}
		
		return queue;
	}
	
	/* Unused Code
	ArrayList<int[]> removeTail(ArrayList<int[]> fullPathList, int numberOfTails) {
		ArrayList<int[]> tailToRemove = new ArrayList<int[]>();
		for (int i = fullPathList.size()-1; i>0; i--) {
			if (!(fullPathList.get(i)[0]==localStartX && fullPathList.get(i)[1]==localStartY)) {
				tailToRemove.add(fullPathList.get(i));
			} else {
				numberOfTails--;
				if (numberOfTails>0) {
					tailToRemove.add(fullPathList.get(i));
				} else {
					break;
				}
			}
		}
		for (int[] tailItem : tailToRemove)
			fullPathList.remove(tailItem);
		return fullPathList;
	}
	*/
	
	
	ArrayList<int[]> findPath(ArrayList<int[]> pathList) {
		
	//	boolean firstTime = true;
		
		int pathIndex = 0;
		
		finalPath.add(new int[]{pathList.get(pathList.size()-1)[0],pathList.get(pathList.size()-1)[1]});
		boolean pathFound = false;
		System.out.println(pathList.size());
		
		while (!pathFound) {
			int[] path = finalPath.get(finalPath.size()-1);
			adjacentList = new ArrayList<int[]>();
			
			System.out.print("currentitem: ");
			System.out.println(pathList.size()-1-pathIndex);
			System.out.print("listlength: ");
			System.out.println(pathList.size());
			
			for (int adj = (pathList.size()-1)-pathIndex; adj >= 0; adj--) {
				boolean toAdd = false;
				int[] adjacent = pathList.get(adj);
				
				if (path[0]<localMap[0].length-1) 
					if (path[0]+1 == adjacent[0] && path[1] == adjacent[1]) 
						toAdd = true;
				
				if (path[0]>=1)
					if (path[0]-1 == adjacent[0] && path[1] == adjacent[1]) 
						toAdd = true;
				
				if (path[1]<localMap.length-1)
					if(path[0] == adjacent[0] && path[1]+1 == adjacent[1])
						toAdd = true;
				
				if (path[1]>=1)			
					if (path[0] == adjacent[0] && path[1]-1 == adjacent[1])
						toAdd = true;
				
				if (toAdd) {
					System.out.print("adjacent: ");
					System.out.print(adjacent[0]);
					System.out.print(", ");
					System.out.println(adjacent[1]);
					adjacentList.add(adjacent);
				}
			}
			
				
			pathIndex += adjacentList.size();

			System.out.print(adjacentList.size());
			System.out.print("- adj.size ");
			System.out.println();
			// Gets the adjacent square with the lowest number
				outputAdjacent = adjacentList.get(0);
				minAdjacent = outputAdjacent[2];
				//adjacent = new int[]{outputAdjacent[0],outputAdjacent[1]};
				for (int[] adjacentSquare : adjacentList) {
					if (adjacentSquare[2] <= minAdjacent) {
						outputAdjacent = adjacentSquare;
						minAdjacent = outputAdjacent[2];
					}
				}
			adjacent = new int[]{outputAdjacent[0],outputAdjacent[1]};
			System.out.print("chosen: ");
			System.out.print(adjacent[0]);
			System.out.print(", ");
			System.out.println(adjacent[1]);
			pathList.remove(outputAdjacent);
			
		finalPath.add(adjacent);
		
		int[] pathEntry = adjacent;
			if (pathEntry[0] == localFinishX && pathEntry[1] == localFinishY) {
				pathFound = true;
			}
			if (index > 150) {
				System.out.println("Path not found");
				pathFound = true;
			}
		}
		return finalPath;
	}
}
