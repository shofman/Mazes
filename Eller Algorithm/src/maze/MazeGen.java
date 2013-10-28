package maze;

import java.util.ArrayList;
import java.util.Random;

public class MazeGen {
	static ArrayList<Cell[]> cellList = new ArrayList<Cell[]>();
	static int amountOfCol = 9;
	static Random myRandom;
	public static void main(String[] args) {

		//Create first row
		int row = 1;
		myRandom = new Random();

		Cell[] cellArray = new Cell[amountOfCol];
		for (int i=0; i<amountOfCol; i++) {
			Cell tmpCell = new Cell(row, i+1);
			cellArray[i] = tmpCell;
		}
		cellList.add(cellArray);

		//Join any cells not members of a set to their own unique set
		for (int i=0; i<amountOfCol; i++) {
			if(cellList.get(0)[i].getSet() == 0) {
				cellList.get(0)[i].setSet(i+1);
			}
		}
		for (int i=0; i<10; i++) {
			buildWall(i);
			buildFloors(i);
			duplicateRow(i);

		}
	
		display(3);
		System.out.println();
		display(1);

	}

	public static void buildWall(int row) {
		boolean buildWall;

		for (int i=1; i<amountOfCol; i++) {
			if (cellList.get(row)[i-1].set == cellList.get(row)[i].set) {
				cellList.get(row)[i-1].rightWall = true;
			} else {
				// .4 chance of building a wall
				buildWall = ((float)myRandom.nextInt(amountOfCol)+1)/amountOfCol < .4;
				if(buildWall) {
					//Create the wall, and tell that it's the last in the list
					cellList.get(row)[i-1].rightWall = true;
					cellList.get(row)[i-1].isLastInSet = true;
				} else {
					cellList.get(row)[i].set = cellList.get(row)[i-1].set;
				}
			}
		}
		cellList.get(row)[amountOfCol-1].isLastInSet = true;
	}

	public static void buildFloors(int row) {
		//Add floors
		for (int i=0; i<amountOfCol; i++) {
			//If the cell is the last in the set, it has to provide a down path unless a cell earlier in the set has allowed a down path
			if ((cellList.get(row)[i].isLastInSet && cellList.get(row)[i].set.hasDownOption)
					|| !cellList.get(row)[i].isLastInSet) {
				boolean buildWall = myRandom.nextFloat() < .7;
				if (buildWall) {
					cellList.get(row)[i].floorWall = true;
				} else {
					cellList.get(row)[i].set.hasDownOption = true;
				}
			} 
		}
	}

	public static void duplicateRow(int row) {
		//Duplicate row, reseting sets and walls
		Cell[] cellArray = new Cell[amountOfCol];
		//Copy array		
		for(int i=0; i<amountOfCol; i++) {
			Cell tmpCell = new Cell(1, i);
			tmpCell.set = cellList.get(row)[i].set;
			tmpCell.floorWall = cellList.get(row)[i].floorWall;
			tmpCell.rightWall = false;
			tmpCell.isLastInSet = false;
			cellArray[i] = tmpCell;

			//Reset the sets of those with floors
			if (cellArray[i].floorWall) {
				cellArray[i].set = new Set();
				cellArray[i].setSet(i+1);
				cellArray[i].floorWall = false;
			}
		}
		cellList.add(cellArray);
	}

	public static void display(int whatToDisplay) {
		System.out.println("\n_______________");
		for (int i=0; i<cellList.size(); i++) {
			System.out.print("|");
			String display;
			for (int j=0; j<amountOfCol; j++) {
				display = "";
				switch(whatToDisplay) {
				case 1:
					//Set group cell list belongs to
					display += cellList.get(i)[j].getSet();
					break;
				case 2:
					display += cellList.get(i)[j].isLastInSet;
					break;
				case 3:
					if (cellList.get(i)[j].floorWall) {
						display += "_";
					} else {
						display += " ";
					}
				}
				if (cellList.get(i)[j].rightWall)  {
					System.out.print(display+"|");
				} else {
					System.out.print(display+" ");
				}
			}
			System.out.print("|\n");
			for (int j=0; j<amountOfCol; j++) {
				if(cellList.get(i)[j].floorWall) {
					//System.out.print("- ");
				} else {
					//System.out.print("  ");
				}
			}
			//System.out.println("|");
		}
	}

}
