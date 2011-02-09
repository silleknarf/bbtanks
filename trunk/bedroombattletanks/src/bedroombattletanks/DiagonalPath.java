package bedroombattletanks;

import java.util.ArrayList;

public class DiagonalPath {
	ArrayList<int[]> queue = new ArrayList<int[]>();
	
	int localStartX;
	int localStartY;
	int localFinishX;
	int localFinishY;
	
	DiagonalPath(int startX, int startY, int finishX, int finishY) {
		localStartX = startX;
		localStartY = startY;
		localFinishX = finishX;
		localFinishY = finishY;
		queue = findPath();
		/* Debug
		for (int[] item : queue) {
			System.out.print(item[0]);
			System.out.print(", ");
			System.out.println(item[1]);
		}
		*/
	};
	
	ArrayList<int[]> findPath() {
		double xDistance = localFinishX - localStartX;
		double yDistance = localFinishY - localStartY;
		double gradient = yDistance / xDistance;
		if (gradient < 1 && gradient >= 0 && yDistance > 0) {
			for (int i = localStartX; i <= localFinishX; i++) {
				queue.add(new int[]{i,localStartY + (int) (i*gradient)});
			}
		} else if (gradient >= 1 && yDistance > 0) {
			for (int i = localStartY; i <= localFinishY; i++) {
				queue.add(new int[]{localStartX + (int) (i/gradient), i});
			}
		} else if (gradient < 1 && gradient >= 0 && yDistance < 0) {
			for (int i = localStartX; i >= localFinishX; i--) {
				queue.add(new int[]{i, localFinishY + (int) (i*gradient)});
			}
		}  else  {
			for (int i = localStartY; i >= localFinishY; i--) {
				queue.add(new int[]{localFinishX + (int) (i*gradient), i});
			}
		}
		return queue;
	}
}
	
