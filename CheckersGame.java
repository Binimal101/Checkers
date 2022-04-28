import java.util.Scanner;
import java.lang.Character;
import java.io.*;
import java.nio.file.Files;

public class CheckersGame {
	// public's
	public static final String[] players = new String[] {"Black", "White"};
	// private's
	private int turnCount;
	private boolean saving = true; //manual toggle
	private String path;
	
	Board board;
	public CheckersGame() {
		this.board = new Board();
		this.turnCount = 0;
		this.path = null;
	}

	public CheckersGame(Board board, String path) {
		this.board = board;
		this.turnCount = 0;
		this.path = path;
	}

	public CheckersGame(Board board, int turnCount, String path)  {
		this.board = board;
		this.turnCount = turnCount;
		this.path = path;
	}

	public String gameloop() throws IOException { //Returns the path of the game to delete out of scope
		Scanner input = new Scanner(System.in, "UTF-8");
		
		String currentPlayer;
		
		int x, y, dx, dy;
		int[] coordinates;

		int turnsSinceLastJump = 0;
		final int STALEMATEDELIMITER = 40;

		
		if(path == null) {
			System.out.print("Save game as:\n>>> ");
			path = input.nextLine(); //TODO add redundancy
			System.out.println();
		}
		
		while(true) {
			currentPlayer = players[turnCount % 2];
				
			if((turnsSinceLastJump / 2) == STALEMATEDELIMITER) { // div2 as each player should have moved 40 times
				clearConsole();
				System.out.println(board);
				System.out.println("Game Over\n40 moves have elapsed since a checker was taken: DRAW");
				return path;
				
			} else if(board.blackCount() == 0) {
				clearConsole();
				System.out.println(board);
				System.out.println("Black has exausted its units: WHITE WINS");
				return path;
				
			} else if(board.whiteCount() == 0) {
				clearConsole();
				System.out.println(board);
				System.out.println("White has exausted its units: BLACK WINS");
			
			} else if(!board.canMove(currentPlayer.charAt(0))) {
				clearConsole();
				System.out.println(board);
				System.out.println("Game Over\n" + currentPlayer + " is unable to make any moves: STALEMATE");
				return path;
				
			} else {
				coordinates = getProperCoordinates(currentPlayer);
				x = coordinates[0];
				y = coordinates[1];
				
				dx = coordinates[2];
				dy = coordinates[3];
				
				//At this point we have x, y, dx, dy; we also know they are valid moves
	
				if(Math.abs(dx-x) == 2 && Math.abs(dy-y) == 2) { //We know that the move is valid and two spaces away, we are jumping
					turnsSinceLastJump = 0;
					
					int[] middlePosition = board.getIntermediaryPosition(x, y, dx, dy);
					board.move(new int[] {x, y}, middlePosition); //Short stopping at our middle position to kill enemy
					board.move(middlePosition, new int[] {dx, dy}); //from short stop to final position

					String[] jumpingNames = new String[] {"FILLER", "DOUBLE", "TRIPLE", "QUADRUPLE", "QUINTUPLE", "SEXTUPLE", "SEPTUPLE", "OCTUPLE", "N-TUPLE", "DOU-DECUPLE"};
					boolean jumping = true;
					int count = 0;
					
					while(jumping) { 
						//dx dy are our new starting coordinates after initial move above
						x = dx;
						y = dy;
						
						if(board.canJumpAt(x, y)) { 
							do {
								if(count == 0) {
									System.out.println("You must continue to jump with the piece located at: (" + x + ", " + y + ")");
								} else {
									System.out.println(jumpingNames[count] + " JUMP");
								}
								// When asking for input, we can assume that they will be forced to enter dx, dy as x, y, so we don't ask for that
								int[] newJump = getMoveFromPlayer(currentPlayer, "Place to Jump");
								
								dx = newJump[0];
								dy = newJump[1];
								
							} while(!board.isJumpableMove(x, y, dx, dy));
								//x, y, dx, dy will be a jumpable move at this point in time
								turnsSinceLastJump = 0;
							
								middlePosition = board.getIntermediaryPosition(x, y, dx, dy);
								board.move(new int[] {x, y}, middlePosition); //Short stopping at our middle position to kill enemy
								board.move(middlePosition, new int[] {dx, dy}); //from short stop to final position
							
						} else {
							jumping = false;	
						}
						
						count++;
					}
					
					
				} else {
					board.move(x, y, dx, dy); //If its not a jump, yet it's a valid move, its gotta be a normal allowable move
				}
				
				turnCount++;
				if(saving) {
					new GameSave(board, turnCount).saveToFile(path);
				}
			}
			
		}
		
	}

	public int[] getProperCoordinates(String currentPlayer) {
		int x, y, dx, dy;
		int[] move, moveTo;
		
		do { //Continues until player has decided upon correct coordinates
				x = -1;
				y = -1;
				while(!withinBounds(x, y) || board.isNullAt(x, y) || !isAllowedToControl(currentPlayer, x, y)) {
					move = getMoveFromPlayer(currentPlayer, "Piece To Move");
					x = move[0];
					y = move[1];
				}
	
				dx = -1;
				dy = -1;
				while(!withinBounds(dx, dy)) {
					moveTo = getMoveFromPlayer(currentPlayer, "Move To Space");
					dx = moveTo[0];
					dy = moveTo[1];
				}
				
			} while(!board.validMove(x, y, dx, dy));
		
		return new int[] {x, y, dx, dy}; //these coordinates are correct
	}

	public int[] getMoveFromPlayer(String player, String askingFor) {
		Scanner input = new Scanner(System.in, "UTF-8");
		String turnMessage = "It's " + player + "'s turn";
		
		System.out.println(turnMessage);
		System.out.println(board);

		//X-Coordinate
		String tryX = "Jibberish"; //Placeholder
		while(!Character.isDigit(tryX.charAt(0))) {

			
			System.out.println(askingFor + ": (?, ?)");
			
			System.out.println("Enter horizontal coordinate: ");
			System.out.print(">>> ");
			tryX = input.nextLine();
			
			if(tryX.isEmpty()) {
				tryX = "Jibberish"; //Placeholder, resumes loop
			}
			
			clearConsole();
			System.out.println(turnMessage);
			System.out.println(board);
		}
		int x = Integer.parseInt(tryX.substring(0, 1));
		clearConsole();

		//Y-Coordinate
		String tryY = "Jibberish"; //Placeholder
		while(!Character.isDigit(tryY.charAt(0))) {
			System.out.println(turnMessage);
			System.out.println(board);
			
			System.out.println(askingFor + ": (" + x + ", ?)");
			
			System.out.println("Enter vertical coordinate: ");
			System.out.print(">>> ");
			tryY = input.nextLine();
			
			if(tryY.isEmpty()) {
				tryY = "Jibberish"; //Placeholder, resumes loop
			}
			
			clearConsole();
			System.out.println(turnMessage);
			System.out.println(board);
		}
		int y = Integer.parseInt(tryY.substring(0, 1));
		
		clearConsole();
		return board.fromCartesian(x, y);
	}

	public boolean isAllowedToControl(String player, int x, int y) {
		char playerColor = player.charAt(0); //player will only ever be "White" or "Black" index 0 represents their color
		return Character.toUpperCase(board.getColorAt(x, y)) == playerColor;
	}

	public boolean withinBounds(int x, int y) { //0, 7 as will be compared using non-cartesian coords
		return (x >= 0 && x <= 7) && (y >= 0 && y <=7);
	}
	
	public static void clearConsole() {
		System.out.print("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
		System.out.flush();
	}
	
}