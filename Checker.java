class Checker {
	
	//Instance Variables
	public int x;
	public int y;
	char color;

	//Constructor
	public Checker(int x, int y, char color) {
		this.x = x;
		this.y = y;
		this.color = color;
		tryToKing();
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
	// public void setPosition(int x, int y) { Shouldn't need this, but i'll keep it for now
	// 	this.x = x;
	// 	this.y = y;
		
	// 	tryToKing();
	// }

	public void setColor(char color) {
		this.color = color;
	}

	//toString
	public String toString() {
		return String.valueOf(color);
	}
}