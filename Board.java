import java.lang.Character;
import java.util.*;

public class Board {
	//Finals
	private static final char B = '⬛';
 	private static final char W = '⬜';

	//Gameboard ref under 2d grid
	Checker[][] grid = new Checker[8][8];
	
	char[][] emptyGrid = {
		{'⬜', '⬛', '⬜', '⬛', '⬜', '⬛', '⬜', '⬛'},
		{'⬛', '⬜', '⬛', '⬜', '⬛', '⬜', '⬛', '⬜'},
		{'⬜', '⬛', '⬜', '⬛', '⬜', '⬛', '⬜', '⬛'},
		{'⬛', '⬜', '⬛', '⬜', '⬛', '⬜', '⬛', '⬜'},
		{'⬜', '⬛', '⬜', '⬛', '⬜', '⬛', '⬜', '⬛'},
		{'⬛', '⬜', '⬛', '⬜', '⬛', '⬜', '⬛', '⬜'},
		{'⬜', '⬛', '⬜', '⬛', '⬜', '⬛', '⬜', '⬛'},
		{'⬛', '⬜', '⬛', '⬜', '⬛', '⬜', '⬛', '⬜'},
	};
	
	//Constructors
	public Board() {
		//Null spaces
		for(int row = 0; row < 8; row++) {
			for(int col = 0; col < 8; col++) {
				grid[row][col] = null;
			}
		}
		int ct;
		
		//Initialize white pieces
		ct = 1;
		for(int row = 0; row < 3; row++) {
			for(int col = 0; col < 8; col++) {
				if(ct % 2 == 0) {
					grid[row][col] = new Checker(this, row, col, 'w');
				}
				ct++;
			}
			ct++;
		}
		
		//Initialize black pieces
		ct = 0;
		for(int row = 7; row > 4; row--) {
			for(int col = 0; col < 8; col++) {
				if(ct % 2 == 0) {
					grid[row][col] = new Checker(this, row, col, 'b');
				}
				ct++;
			}
			ct++;
		}
		updateBoard();

	}
//***************************************************************
	public void printCoordinateMovePairs() {
		for(Checker[] row : grid) {
			for(Checker checker : row) {
				if(checker != null) {
					int[] coords = toCartesian(checker.x, checker.y);
					
					System.out.println("************************************************************");
					System.out.println("Movesets at (" + coords[0] + ", " + coords[1] + "):");
					
					if(checker.moves != null) {
						System.out.println(new Moves(this, checker.moves, "Non-Jumpable"));
					}
					
					if(checker.jumpableMoves != null) {
						System.out.println(new Moves(this, checker.jumpableMoves, "Jumpable"));
					}
					
					System.out.println();
				}
			}
		}
	}
	
	//Coordinate Editing Methods
	public int[] toCartesian(int x, int y) {
		return new int[] {y+1, (7-x)+1};
	}

	public int[] fromCartesian(int x, int y) {
		return new int[] {(7-y)+1, x-1};
	}

	//Matrix Manipulation and Checks
	public int[][] getSurrounding(int x, int y, char color) {
		HashSet<int[]> surrounding = new HashSet<>();
		//x=7, y=0 runs and works problem is elsewere
		
		//Top Right
		if(x != 0 && y < grid[x].length-1) {
			if(color == 'b' || Character.isUpperCase(color)) {
				surrounding.add(new int[] {x-1, y+1});
			}
		} 

		//Top Left
		if(x != 0 && y != 0) {
			if(color == 'b' || Character.isUpperCase(color)) {
				surrounding.add(new int[] {x-1, y-1});
			}
		}

		//Bottom Right
		if(x < grid.length - 1 && y < grid[x].length - 1) {
			if(color == 'w' || Character.isUpperCase(color)) {
				surrounding.add(new int[] {x+1, y+1});
			}
		} 

		//Bottom Left
		if(x < grid.length-1 && y != 0) {
			if(color == 'w' || Character.isUpperCase(color)) {
				surrounding.add(new int[] {x+1, y-1});
			}
		} 
		
		if(surrounding.size() == 0) {
			return null;
			
		} else {
			//Converting HashSet<int[2]> to int[], most likely a better way to do it, but we will use the iterable interface
			int[][] fin = new int[surrounding.size()][2];
			int ctdp0 = 0;
			int ctdp1 = 0;
			for(int[] lst : surrounding) {
				for(int elem : lst) {
					fin[ctdp0][ctdp1] = elem;
					ctdp1++; //inner depth- referring to each dimension in the array
				}
				ctdp1 = 0;
				ctdp0++; //outer depth
			}
			
			return fin;
		}
		
	}
	
	//@Param (modifier) is the distance diagonal away
	public String getDirection(int x, int y, int dx, int dy, int modifier) { //String length 2, signifies lateral and horizontal displacement from origin
		
		//dx is top right of xy
		if((x - modifier) == dx && (y + modifier) == dy){
			return "tr";
		}
			
		//dx is top left of xy
		else if((x - modifier) == dx && (y - modifier) == dy) {
			return "tl";
		}
			
		//dx is bottom right of xy
		else if((x + modifier) == dx && (y + modifier) == dy) {
			return "br";
		}
			
		//dx is bottom left of xy
		else if((x + modifier) == dx && (y - modifier) == dy) {
			return "bl";
			
		} else {
			return "This will not run but it will shut up the errors in the tab";
		}
		
	}

	//gets final position given direction and original position
	public int[] getNextPositionFrom(int x, int y, String direction) {
		if(direction == "tr") {
			if(Math.min(Math.min(x-2, y+2), 0) < 0 || Math.max(Math.max(x-2, y+2), 7) > 7) { //if out of bounds
				return null;
			} 
			return new int[] {x-2, y+2};
		}
			
		else if(direction == "tl") {
			if(Math.min(Math.min(x-2, y-2), 0) < 0 || Math.max(Math.max(x-2, y-2), 7) > 7) { //if out of bounds
				return null;
			} 
			return new int[] {x-2, y-2};
		}
			
		else if(direction == "br") {
			if(Math.min(Math.min(x+2, y+2), 0) < 0 || Math.max(Math.max(x+2, y+2), 7) > 7) {  //if out of bounds
				return null;				
			}
			return new int[] {x+2, y+2};
		}
			
		else if(direction == "bl") {
			if(Math.min(Math.min(x+2, y-2), 0) < 0 || Math.max(Math.max(x+2, y-2), 7) > 7) {  //if out of bounds
				return null;				
			}
			return new int[] {x+2, y-2};	
			
		} else {
			return new int[] {0}; //will not run
		}
	}

	//@returns (int[2]) represents the position in between (x, y) and (dx, dy)
	public int[] getIntermediaryPosition(int x, int y, int dx, int dy) {
		String direction = getDirection(x, y, dx, dy, 2);
		
		switch(direction) {
			case "tr":
				return new int[] {x-1, y+1};
			case "tl":
				return new int[] {x-1, y-1};
			case "br":
				return new int[] {x+1, y+1};
			case "bl":
				return new int[] {x+1, y-1};
				
			default: 
				return null;
		}
	}

	public int blackCount() {
		int count = 0;
		
		for(Checker[] row : grid) {
			for(Checker checker : row) {
				if(checker != null && Character.toUpperCase(checker.color) == 'B') {
					count++;
				}
			}	
		}
		
		return count;
	}

	public int whiteCount() {
		int count = 0;
		
		for(Checker[] row : grid) {
			for(Checker checker : row) {
				if(checker != null && Character.toUpperCase(checker.color) == 'W') {
					count++;
				}
			}	
		}
		
		return count;
	}
	
	public char getColorAt(int x, int y) {
		return grid[x][y].color;
	}

	public boolean isNullAt(int x, int y) {
		return grid[x][y] == null;
	}

	//Updates moveset attributes
	public void updateBoard() {
		for(int row = 0; row < grid.length; row++) {
			for(int column = 0; column < grid[row].length; column++) {
				
				Checker checker = grid[row][column];
				if(checker != null) {
					System.out.println(checker.x + " " + checker.y + " " + checker.color);
					grid[row][column] = new Checker(this, checker.x, checker.y, checker.color);
				}
				
			}
		}
	}
	
	//Move Checks
	public int[][][] getJumpableMoves(int x, int y, char color) { //Will get the moves the piece can make along with 
		char enemyColor = ((color == 'B' || color == 'b') ? 'w' : 'b'); //Enemy color will always be represented as lowercase
		
		int[] finalPosition;
		String direction;
		int moveX, moveY;
		
		ArrayList<int[]> moves = new ArrayList<int[]>();
		ArrayList<int[]> taken = new ArrayList<int[]>();
		
		int[][] aroundPiece = getSurrounding(x, y, color); //Spaces around current piece that can be moved to given the piece color
		
		for(int[] cdPair : aroundPiece) {			
			moveX = cdPair[0];
			moveY = cdPair[1];
			
			if(grid[moveX][moveY] != null && Character.toLowerCase(grid[moveX][moveY].color) == enemyColor) { //Current space is filled by an enemy piece
				direction = getDirection(x, y, moveX, moveY, 1);
				finalPosition = getNextPositionFrom(x, y, direction);
				
				if(grid[finalPosition[0]][finalPosition[1]] == null) { //Not a checker object
					moves.add(finalPosition);
					taken.add(cdPair);
				}
			}
		}

		int[][][] finalMoveset = new int[2][moves.size()][2];
		finalMoveset[0] = moves.toArray(new int[moves.size()][2]);
		finalMoveset[1] = taken.toArray(new int[moves.size()][2]); //moves is a list that will be parallel in length to taken
		
		if(finalMoveset.length == 0 || finalMoveset[0].length == 0 && finalMoveset[1].length == 0) { //return null instead of returning null arrays
			finalMoveset = null;
		}
		
		return finalMoveset;
	}

	public int[][] getMoves(int x, int y, char color) {
		ArrayList<int[]> moves = new ArrayList<int[]>();
		int[][] aroundPiece = getSurrounding(x, y, color);
		
		for(int i = 0; i < aroundPiece.length; i++) {
			int aroundX = aroundPiece[i][0];
			int aroundY = aroundPiece[i][1];

			if(grid[aroundX][aroundY] == null || grid[aroundX][aroundY].color != 'b' && grid[aroundX][aroundY].color != 'w') { //Not a checker object / free space
				moves.add(aroundPiece[i]);
			}
		}

		if(moves.size() == 0) {
			return null;
		}
		
		return moves.toArray(new int[moves.size()][]);
	}
	
	//Move Logic
	public boolean canMove(char color) {
		for(int row = 0; row < grid.length; row++) {
			for(int column = 0; column < grid[row].length; column++) {
				
				Checker checker = grid[row][column];
				
				//Same color as playing player
				if(checker != null && Character.toUpperCase(checker.color) == Character.toUpperCase(color)) {
					if(canMoveAt(checker.x, checker.y)) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public boolean canJump(char color) {
		for(int row = 0; row < grid.length; row++) {
			for(int column = 0; column < grid[row].length; column++) {
				
				Checker checker = grid[row][column];
				
				//Same color as playing player
				if(checker != null && Character.toUpperCase(checker.color) == Character.toUpperCase(color)) {
					if(canJumpAt(checker.x, checker.y)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public boolean canMoveAt(int x, int y) {
		return grid[x][y].moves != null;
	}
	
	public boolean canJumpAt(int x, int y) {
		return grid[x][y].jumpableMoves != null;
	}
	
	//Checks for a non-jumpable move
	public boolean isMove(int x, int y, int dx, int dy) {
		Checker checker = grid[x][y];
		for(int[] cdp : checker.moves) {
			int cdx = cdp[0];
			int cdy = cdp[1];

			if(cdx == dx && cdy == dy) {
				return true;
			}
		}
		return false;
	}
	
	//Checks for a jumpable move
	public boolean isJumpableMove(int x, int y, int dx, int dy) {
		Checker checker = grid[x][y];

		if(checker.jumpableMoves == null) {
			return false;
		}
		
		for(int[] cdp : checker.jumpableMoves) {
			int cdx = cdp[0];
			int cdy = cdp[1];

			if(cdx == dx && cdy == dy) {
				return true;
			}
		}
		return false;
	}
// 7, 3
// 8, 4
	public boolean validMove(int x, int y, int dx, int dy) { //disallow moves if move invalid, or jump is available and not jump move
		if(canJump(grid[x][y].color)) {
			if(isJumpableMove(x, y, dx, dy)) {
				return true;
			} else {
				System.out.println("Error, you are required to jump this turn");
				return false;
			}
			
		} else if(canMove(grid[x][y].color)) {
			if(isMove(x, y, dx, dy)) {
				return true;
			} else {
				return false;
			}
			
		} else {
			return false;
		}
		
	}
	
	public void move(int x, int y, int dx, int dy) {
		Checker start = grid[x][y];
		grid[dx][dy] = new Checker(this, dx, dy, start.color);
		grid[x][y] = null;
		
		updateBoard();
	}

	public void move(int[] xy, int[] dxdy) { //given coordinate pairs
		int x = xy[0];
		int y = xy[1];
		int dx = dxdy[0];
		int dy = dxdy[1];
		
		move(x, y, dx, dy);
	}
	
	//toString
	public String toString() {
		String str = "";
		for(int i = 0; i < grid.length; i++) {
			str += ((7-i)+1) + " ";
			for(int k = 0; k < grid[i].length; k++) {
				if(grid[i][k] == null) {
					str += emptyGrid[i][k];
				} else {
					str += grid[i][k] + " ";	
				}
			}
			str += "\n";
		}
		
		return str + "  1 2 3 4 5 6 7 8\n";
	}

}