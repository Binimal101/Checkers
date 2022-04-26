//THIS IS MERELY FOR PRINTING
public class Moves {
	Board board;
	String typeMoves;
	int[][] moves;
	
	public Moves(Board board, int[][] moves, String typeMoves) {
		this.board = board;
		this.moves = moves;
		this.typeMoves = typeMoves;
	}

	//toString
	public String toString() {
		String str = typeMoves + ": [";
		
		for(int[] movePair : moves) {
			movePair = board.toCartesian(movePair[0], movePair[1]);
			str += "(" + movePair[0] + ", " + movePair[1] + "), ";
		}

		str = str.substring(0, str.length() - 2) + "]";
		return str;
	}
}