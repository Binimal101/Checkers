class Checker {
	
	//Instance Variables
	public int[][] moves;
	
	//Will be split into two from an original 3d list
	public int[][] jumpableMoves;
	public int[][] jumpableMovesTaken;
	
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
		
		int[][][] placeholder = board.getJumpableMoves(x, y, this.color); //Unpacked in next lines
		
		jumpableMoves = (placeholder == null ? null : placeholder[0]);
		jumpableMovesTaken = (placeholder == null ? null : placeholder[1]);
			
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