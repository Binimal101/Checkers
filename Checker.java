class Checker {
	int x;
	int y;
	char color;
	
	public Checker(int x, int y, char color) {
		this.x = x;
		this.y = y;
		this.color = color;
	}

	public String toString() {
		return String.valueOf(color);
	}
}