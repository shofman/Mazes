package maze;

public class Cell {
	Set set = new Set();
	int row;
	int column;
	boolean rightWall = false;
	boolean floorWall = false;
	boolean isLastInSet = false;
	
	public Cell(int row, int column) {
		this.row = row;
		this.column = column;
	}
	
	public void setSet(int set) {
		this.set.setValue = set;
	}
	
	public int getSet() {
		return this.set.setValue;
	}
}
