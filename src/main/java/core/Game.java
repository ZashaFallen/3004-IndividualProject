package core;

import java.util.Objects;
import java.util.Scanner;

public class Game {
	
	public static String gameState = "";
	private static final String[] validInputs =  {"Q", "C", "F", "H", "S"};
	
	
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		
		while (gameState.equals("")) {
			System.out.print("Would you like to use (c)onsole or (f)file input?: ");
			setGameState(s.nextLine());
		}
		
		if (gameState.equals("console")) {
			Deck deck = new Deck();
		}
		else if (gameState.equals("file")) {
			System.out.print("File input is not supported yet.");
		}
		
		s.close();
	}

	
	
	/**************
	 * Purpose: Sets the initial game state. 'Console' means that
	 * 				user input is taken from the console. 'File'
	 * 				means that user input and all of the drawn cards
	 * 				are taken from a file.
	 * Input:   Raw user input.
	 * Output:  No return value. Modifies 'Game.gameState'.
	 * Created:	12/09/2018
	 **************/
	public static void setGameState(Object rawInput) {
		String state = "";
		String input = validateInput(rawInput);
		
		if (input == null) {
			System.out.println("Invalid input. Please enter 'C' for console input, 'F' for file input, or 'Q' to quit.\r\n");
		}
		else if (input.equals("C")) state = "console";
		else if (input.equals("F")) state = "file";
		else if (input.equals("Q")) state = "quit";
		
		gameState = state;
	}
	
	
	/**************
	 * Purpose: Used to validate user input. Receives raw user input
	 * 				(from the console) and checks it against a list of 
	 * 				'valid' inputs.
	 * Input:   Raw user input.
	 * Output:  'null' if the input does not match an entry in the 
	 * 				list of valid inputs.
	 * 			If the input does match a valid input, then it is 
	 * 				returned as a string (upper case).
	 * Created:	12/09/2018
	 **************/
	public static String validateInput(Object rawInput) {
		String input = null;
		
		rawInput = rawInput.toString().toUpperCase();
		for (String s : validInputs) {
			if (Objects.equals(rawInput, s)) input = s;
		}
		 
		return input;
	}
}
