import java.util.Scanner;

class CheckersGame {
	Board board;
	public CheckersGame() {
		this.board = new Board();
	}

	public void gameloop() {
		String[] players = new String[] {"Black", "White"};
		String currentPlayer;
		
		int turnCount = 0;
		while(true) {
			int[] move = getMoveFromPlayer(players[turnCount % 2]);
			
			
		}
	}

	public int[] getMoveFromPlayer(String player) {
		Scanner input = new Scanner(System.in);
		
		System.out.println("It's " + player + "'s turn");
		
		System.out.println("Enter horizontal coordinate: ");
		System.out.print(">>> ");
		int x = input.nextInt();
		System.out.println();
		
		System.out.println("Enter vertical coordinate: ");
		System.out.print(">>> ");
		int y = input.nextInt();
		System.out.println();
		
		return board.toCartesian(x, y);
	}

	public void clearConsole() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}
}