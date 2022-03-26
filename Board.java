import java.lang.Character;
import java.util.*;

class Board {
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
					grid[row][col] = new Checker(row, col, 'w');
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
					grid[row][col] = new Checker(row, col, 'b');
				}
				ct++;
			}
			ct++;
		}
	}
//***************************************************************
	
	public int[] toCartesian(int x, int y) {
		return new int[] {y+1, (7-x)+1};
	}

	public int[] fromCartesian(int x, int y) {
		return new int[] {(7-y)+1, x-1};
	}
	
	public void move(int x, int y, int dx, int dy) {
		//Add checks with our python constructed getMoves method and add another method checkValidity to be used here
		Checker temp = grid[x][y];
		grid[dx][dy] = new Checker(temp.x, temp.y, temp.color);
		grid[x][y] = null;
	}	

	public int[][] getSurrounding(Checker[][] grid, int x, int y, char color) {
		HashSet<int[]> surrounding = new HashSet<>(); // TODO Make 2d ints, [0] end coordinate, [1] pieces taken on the way
		
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
		if(x < grid.length-1 && y < grid[x].length-1) {
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
			int[][] fin = new int[surrounding.size()][2];
			int ctdp0 = 0;
			int ctdp1 = 0;
			for(int[] lst : surrounding) {
				for(int elem : lst) {
					fin[ctdp0][ctdp1] = elem;
					ctdp1++;
				}
				ctdp1 = 0;
				ctdp0++;
			}
			return fin;
		}
		
	}

	public int[][] cleanCoordinatePairs(int[][] original) {
		List<Integer> iterateOverMeInstead = new ArrayList<>(Arrays.asList(intArray));
		
		int[][] used = new int[original.length][2]; //Might change 2 to 3, dunno, might be due to change
		int index = 0;
		
		int[][] cleaned = new int[original.length][2]; //same-o

		for(int[] cdPair : iterateOverMeInstead) {
			if(!used.contains(cdPair)) {
				
				index++;
			}
		}
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