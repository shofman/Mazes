package maze;

import java.util.ArrayList;
import java.util.Random;

public class MazeGen {
	static ArrayList<Cell[]> cellList = new ArrayList<Cell[]>();
	static int amountOfCol = 10;
	public static void main(String[] args) {
		
		//Create first row
		int row = 1;
		
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
		
		Random myRandom = new Random();
		boolean buildWall;
		for (int i=1; i<amountOfCol; i++) {
			if (cellList.get(0)[i-1].set == cellList.get(0)[i].set) {
				cellList.get(0)[i-1].rightWall = true;
			}
			// .4 chance of building a wall
			System.out.println((float)(myRandom.nextInt(amountOfCol)+1)/amountOfCol);
			buildWall = ((float)myRandom.nextInt(amountOfCol)+1)/amountOfCol < .4;
			if(buildWall) {
				//Create the wall, and tell that it's the last in the list
				cellList.get(0)[i-1].rightWall = true;
				cellList.get(0)[i-1].isLastInSet = true;
			} else {
				cellList.get(0)[i].set = cellList.get(0)[i-1].set;
			}
		}
		cellList.get(0)[amountOfCol-1].isLastInSet = true;

		//Add floors
		for (int i=0; i<amountOfCol; i++) {
			//If the cell is the last one in
			if ((cellList.get(0)[i].isLastInSet && cellList.get(0)[i].set.hasDownOption)
					|| !cellList.get(0)[i].isLastInSet) {
				buildWall = myRandom.nextFloat() < .7;
				if (buildWall) {
					cellList.get(0)[i].floorWall = true;
				} else {
					cellList.get(0)[i].set.hasDownOption = true;
				}
			} 
		}
		
		for(int i=0; i<amountOfCol; i++) {
			
		}
		
		display(1);

	}
	
	public static void display(int whatToDisplay) {
		System.out.println("\n_______________");
		for (int i=0; i<cellList.size(); i++) {
			System.out.print("| ");
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
			System.out.print("|\n| ");
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
