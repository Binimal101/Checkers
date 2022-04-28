import java.util.Scanner;	
import java.util.ArrayList;
import java.nio.file.Files;
import java.io.*;

public class GameSave implements java.io.Serializable {
	transient Checker[][] grid;
	Board board;
	int curTurn;
	
	public GameSave(Board board, int curTurn) {
		this.board = board;
		this.grid = board.returnGrid();
		this.curTurn = curTurn;
	}

	public void saveToFile(String filename) throws IOException {
		Serial<GameSave> cerial = new Serial<GameSave>(this);
		cerial.serialize(filename);
	}

	public static GameSave loadGame(String filename) throws IOException {
		Serial<GameSave> cerial = new Serial<GameSave>();
		return cerial.deserialize(filename);
	}

	public static String getPath() throws IOException {// Required, idk why, "https://stackoverflow.com/questions/2305966/why-do-i-get-the-unhandled-exception-type-ioexception"
		ArrayList<String> directories = new ArrayList<String>();
		final String FOLDERPATH = "saved/";
		
		
		Scanner input = new Scanner(System.in, "UTF-8");
		String filename;

		
		ArrayList<String> placeholder = new ArrayList();
		Files.list(new File(FOLDERPATH).toPath()) //copy paste, look into later
                .forEach(path -> {
					placeholder.add(path.toString());
                });

		String[] splitted;
		String directory;
		for(String dir : placeholder) {
			splitted = dir.split("/");
			directory = splitted[1].substring(0, splitted[1].length()-4);
			directories.add(directory);
		}
		
		do {
			CheckersGame.clearConsole();
			System.out.println("Saved Game Files:");
			
			for(String dir : directories) {
				if(dir != null && !dir.isBlank() && !dir.isEmpty()) {
					System.out.println("\t- \"" + dir.strip() + "\"");
				}
			}
		
		
			System.out.print("Enter gamesave directory:\n>>> ");
			filename = input.nextLine();
			System.out.println();
			
		} while(!directories.contains(filename));
		CheckersGame.clearConsole();
		
		return filename;
	}
}
