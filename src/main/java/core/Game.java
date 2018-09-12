package core;

import java.util.Objects;
import java.util.Scanner;

public class Game {
	
	public static String gameState = "";
	
	/*enum validInputs {
		Q,
		C,
		F,
		H,
		S
	}*/
	private static final String[] validInputs =  {"Q", "C", "F", "H", "S"};
	
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		
		System.out.print("test");
		s.close();
	}

	public static void setGameState(Object rawInput) {
		String state = "";
		String input = validateInput(rawInput);
		
		if (input == null) {
			System.out.println("Invalid input. Please enter 'C' for console input, 'F' for file input, or 'Q' to quit.\r\n");
			System.out.print("Would you like to use (c)onsole or (f)file input?: ");
		}
		else if (input.equals("C")) state = "console";
		else if (input.equals("F")) state = "file";
		else if (input.equals("Q")) state = "quit";
		
		gameState = state;
	}
	
	public static String validateInput(Object rawInput) {
		String input = null;
		
		rawInput = rawInput.toString().toUpperCase();
		for (String s: validInputs) {
			if (Objects.equals(rawInput, s)) input = s;
		}
		 
		return input;
	}
}
