package recursive;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RecursiveBacktrack {
	static int mazeWidth = 5;
	static int mazeHeight = 5;
	static int[][] grid = new int[mazeHeight][mazeWidth];
	
	public static void main(String[] args) {
		for (int i =0; i<grid.length; i++) {
			for (int j=0; j<grid[i].length; j++) {
				grid[i][j] = 0;
			}
		}
		carvePassages(0, 0, grid);
		outputMaze();
		System.out.println();
		outputMazeInfo();
	}
	
	public static void carvePassages(int xPos, int yPos, int[][] grid) {
		List<Direction> test = getDirectionArray();
		for (int i=0; i<test.size(); i++) {
			String d = test.get(i).toString();
			int newXPos = xPos + moveHorizontalDirection(test.get(i));
			int newYPos = yPos + moveVerticalDirection(test.get(i));
			
			if ((newXPos >= 0) && (newYPos >= 0) && (newXPos < mazeWidth) && (newYPos < mazeHeight) && grid[newYPos][newXPos] == 0) {
				grid[yPos][xPos] |= getUniqueMarkers(test.get(i));
				grid[newYPos][newXPos] |= getUniqueMarkers(getOpposite(test.get(i)));
				carvePassages(newXPos, newYPos, grid);
			}
		}
	}
	
	public static int getUniqueMarkers(Direction d) {
		if (d == Direction.E) return 4;
		else if (d == Direction.W) return 8;
		else if (d == Direction.S) return 2;
		else if (d == Direction.N) return 1;
		return 0;
	}
	
	public static Direction getOpposite(Direction moving) {
		if (moving == Direction.E) return Direction.W;
		else if (moving == Direction.W) return Direction.E;
		else if (moving == Direction.S) return Direction.N;
		else if (moving == Direction.N) return Direction.S;
		return null;
	}
	
	public static int moveHorizontalDirection(Direction input) {
		if (input == Direction.E) return 1;
		else if (input == Direction.W) return -1;
		return 0;
	}
	
	public static int moveVerticalDirection(Direction input) {
		if (input == Direction.N) return -1;
		else if (input == Direction.S) return 1;
		return 0;
	}
	public static List<Direction> getDirectionArray() {
		List<Direction> directionArray = new ArrayList<Direction>(); 
		directionArray.add(Direction.N);
		directionArray.add(Direction.E);
		directionArray.add(Direction.S);
		directionArray.add(Direction.W);
		Collections.shuffle(directionArray);
		return directionArray;
	}
	
	public static void outputMaze() {
		for (int i=0; i<mazeWidth; i++) {
			System.out.print(" _");
		}
		System.out.print("\n");
		for (int i=0; i<mazeHeight; i++) {
			System.out.print("|");
			for (int j=0; j<mazeWidth; j++) {
				System.out.print(((grid[j][i] & getUniqueMarkers(Direction.S)) != 0) ? " " : "_");
				if((grid[i][j] & getUniqueMarkers(Direction.E)) != 0) {
					System.out.print((((grid[i][j] | grid[i][j+1]) & getUniqueMarkers(Direction.S)) != 0) ? " " : "_");
				} else {
					System.out.print("|");
				}
			}
			System.out.print("\n");
		}
	}
	
	public static void outputMazeInfo() {
		for (int i=0; i<mazeHeight; i++) {
			for (int j=0; j<mazeWidth; j++) {
				System.out.print(grid[i][j] + " ");
			}
			System.out.println();
		}
	}

	
	enum Direction {
		N,
		S,
		W,
		E	
	}
}
