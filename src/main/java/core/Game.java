package core;

import java.util.Objects;
import java.util.Scanner;

public class Game {
	
	enum GameState {
		console,
		file,
		quit,
		invalid
	}
	
	public static GameState gameState = GameState.invalid;
	public static boolean debug;
	
	private static Deck deck;
	private static HumanPlayer human;
	private static DealerPlayer dealer;
	private static final String[] validInputs =  {"Q", "C", "F", "H", "S", "CD", "FD"};
	
	
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		
		while (gameState == GameState.invalid) {
			System.out.print("Would you like to use (c)onsole or (f)file input?: ");
			gameState = getGameState(s.nextLine());
		}
		
		if (gameState == GameState.console) {
			setUpGame();
			
		}
		else if (gameState == GameState.file) {
			System.out.print("File input is not supported yet.");
		}
		
		s.close();
	}


	private static void setUpGame() {
		deck = new Deck();
		deck.shuffle();
		human = new HumanPlayer(deck);
		dealer = new DealerPlayer(deck);
		
		System.out.println(dealer.showHand());
		System.out.println(human.showHand());
		
		if (dealer.getHandState().equals(Player.PlayerState.blackjack)) {
			System.out.println("The dealer wins!");
		}
		else if (human.getHandState().equals(Player.PlayerState.blackjack)) {
			System.out.println("You win!");
		}
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
	protected static GameState getGameState(Object rawInput) {
		GameState state = GameState.invalid;
		String input = validateInput(rawInput);
		
		if (input == null) {
			System.out.println("Invalid input. Please enter 'C' for console input, 'F' for file input, or 'Q' to quit.\r\n");
		}
		else if (input.equals("C")) state = GameState.console;
		else if (input.equals("F")) state = GameState.file;
		else if (input.equals("Q")) state = GameState.quit;
		else if (input.equals("CD")) {
			state = GameState.console;
			debug = true;
		}
		else if (input.equals("FD")) {
			state = GameState.file;
			debug = true;
		}
		
		return state;
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
	protected static String validateInput(Object rawInput) {
		String input = null;
		
		rawInput = rawInput.toString().toUpperCase();
		for (String s : validInputs) {
			if (Objects.equals(rawInput, s)) input = s;
		}
		 
		return input;
	}
}
