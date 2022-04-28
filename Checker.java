public class Checker implements java.io.Serializable {
	
	//Instance Variables
	public int[][] moves;
	public int[][] jumpableMoves;
	
	public int x;
	public int y;
	char color;
	
	//Constructor
	public Checker(Board board, int x, int y, char color) {
		this.x = x;
		this.y = y;
		this.color = color;

		tryToKing(); //turn piece kinged if at desired location
		
		moves = board.getMoves(x, y, this.color);
		jumpableMoves = board.getJumpableMoves(x, y, this.color);
	}

	//Instance Methods
	public void tryToKing() {
		if(x == 0 && color == 'b') {
			setColor('B');
		} else if(x == 7 && color == 'w') {
			setColor('W');
		}
	}
	
	//Setters
	public void setColor(char color) {
		this.color = color;
	}
	
	//toString
	public String toString() {
		return String.valueOf(color);
	}
}