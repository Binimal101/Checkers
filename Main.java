import java.io.*;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) throws IOException {
		
		Scanner input = new Scanner(System.in);
		String decision;
		
		do {
			System.out.print("1: New Game\n2: Saved Game\n>>> ");
			decision = input.nextLine();
			System.out.println();
			System.out.println();
			CheckersGame.clearConsole();
			
		} while(!decision.equals("1") && !decision.equals("2"));

		String path;
		if(decision.equals("1")) {
			CheckersGame game = new CheckersGame();
			path = game.gameloop();
			
		} else {
			path = GameSave.getPath();
			GameSave saved = GameSave.loadGame(path);
			CheckersGame game = new CheckersGame(saved.board, saved.curTurn, path);
			game.gameloop();
		}

		File deleteable = new File("saved/" + path + ".ser"); //When a game is won/over, delete it
		deleteable.delete();
		
		input.close();
	}
}