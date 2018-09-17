package core;

public class Game {
	
	enum GameState {
		console,
		file,
		quit,
		invalid,
		winner
	}
	
	public static GameState gameState = GameState.invalid;
	public static boolean debug;
	
	protected static HumanPlayer human;
	protected static DealerPlayer dealer;
	protected static Deck deck;	
	
	public static void main(String[] args) {
		ConsoleIO.init();
		
		while (gameState == GameState.invalid) {
			setGameState(ConsoleIO.input("Would you like to use (c)console or (f)file input?: "));
		}
		
		if (gameState == GameState.console) {
			setUpGame();
			
			if (!checkInitialBlackjack()) {
				human.takeTurn(deck);
				if (human.getBestHandState() != Player.PlayerState.busted) {
					dealer.takeTurn(deck);
				}
			}

			
			displayBothHands();
			if (getWinner()) {
				if (checkInitialBlackjack()) {
					ConsoleIO.output("You got an initial blackjack, and the dealer didn't!");
				}
				ConsoleIO.output("You won!");
			}
			else {
				if (checkInitialBlackjack()) {
					ConsoleIO.output("The dealer got an initial blackjack!");
				}
				ConsoleIO.output("You lost!");
			}
			
		}
		else if (gameState == GameState.file) {
			ConsoleIO.output("File input is not supported yet.");
		}
		
		ConsoleIO.close();
	}


	private static void setUpGame() {
		deck = new Deck();
		deck.shuffle();
		human = new HumanPlayer(deck);
		dealer = new DealerPlayer(deck);
		
		System.out.print(System.lineSeparator());
		System.out.println(dealer.getHand(false));
		System.out.println(human.getHand());
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
	protected static void setGameState(String validatedInput) {		
		
		if (validatedInput.equals("C")) {
			gameState = GameState.console;
		}
		else if (validatedInput.equals("F")) {
			gameState = GameState.file;
		}
		else if (validatedInput.equals("Q")) {
			gameState = GameState.quit;
		}
		else if (validatedInput.equals("CD")) {
			gameState = GameState.console;
			debug = true;
		}
		else if (validatedInput.equals("FD")) {
			gameState = GameState.file;
			debug = true;
		}
		else {
			ConsoleIO.output("Invalid input. Please enter 'C' for console input, 'F' for file input, or 'Q' to quit.\r\n");
			gameState = GameState.invalid;
		}
	}
	
	
	protected static boolean checkInitialBlackjack() {
		boolean check = false;
		
		if (dealer.getBestHandState() == Player.PlayerState.blackjack) {
			check = true;
			dealer.initialBlackjack = true;
		}
		if (human.getBestHandState() == Player.PlayerState.blackjack) {
			check = true;
			human.initialBlackjack = true;
		}
		
		return check;
	}
	
	
	protected static void displayBothHands() {
		ConsoleIO.output(System.lineSeparator());
		ConsoleIO.output(dealer.getHand(true) + " Final Score: " + dealer.getBestHandScore() + System.lineSeparator());
		ConsoleIO.output(human.getHand() + " Final Score: " + human.getBestHandScore() + System.lineSeparator());
	}
	
	
	protected static boolean getWinner() {
		boolean humanWon = false;
		
		checkInitialBlackjack();
		
		// the player has an initial blackjack and the dealer doesn't
		if (!dealer.initialBlackjack && human.initialBlackjack) {
			humanWon = true;
		}
		// the dealer busts, and the player is safe
		// neither player busts, and the human's score is higher
		else if (human.getBestHandState() == Player.PlayerState.safe && (
				dealer.getBestHandState() == Player.PlayerState.busted ||
				human.getBestHandScore() > dealer.getBestHandScore())) {
			humanWon = true;
		}
	
		return humanWon;
	}
}
