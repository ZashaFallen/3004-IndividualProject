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
	
	private static Deck deck;	
	
	public static void main(String[] args) {
		ConsoleIO.init();
		
		while (gameState == GameState.invalid) {
			gameState = getGameState(ConsoleIO.input("Would you like to use (c)console or (f)file input?: "));
		}
		
		if (gameState == GameState.console) {
			setUpGame();
			
			if (checkInitialBlackjack()) {
				System.out.println(winner());
			}
			else {
				human.takeTurn(deck);
				if (human.getBestHandState() != Player.PlayerState.busted) {
					dealer.takeTurn(deck);
				}
				
				System.out.println(winner());
			}
		}
		else if (gameState == GameState.file) {
			System.out.print("File input is not supported yet.");
		}
		
		ConsoleIO.close();
	}


	private static void setUpGame() {
		deck = new Deck();
		deck.shuffle();
		human = new HumanPlayer(deck);
		dealer = new DealerPlayer(deck);
		
		System.out.print(System.lineSeparator());
		System.out.println(dealer.showHand(false));
		System.out.println(human.showHand());
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
	protected static GameState getGameState(String validatedInput) {
		GameState state = GameState.invalid;
		
		if (validatedInput == null) {
			System.out.println("Invalid input. Please enter 'C' for console input, 'F' for file input, or 'Q' to quit.\r\n");
		}
		else if (validatedInput.equals("C")) state = GameState.console;
		else if (validatedInput.equals("F")) state = GameState.file;
		else if (validatedInput.equals("Q")) state = GameState.quit;
		else if (validatedInput.equals("CD")) {
			state = GameState.console;
			debug = true;
		}
		else if (validatedInput.equals("FD")) {
			state = GameState.file;
			debug = true;
		}
		
		return state;
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
		System.out.println(System.lineSeparator());
		System.out.println(dealer.showHand(true) + " Final Score: " + dealer.getBestHandScore());
		System.out.println(human.showHand() + " Final Score: " + human.getBestHandScore());
	}
	
	
	protected static String winner() {
		final String win = "You Win!";
		final String lose = "The Dealer Wins!";
		String winMessage;
		
		displayBothHands();
		
		if (dealer.initialBlackjack) {
			winMessage = "The dealer gets an initial blackjack and automatically wins!";
		}
		else if (human.initialBlackjack) {
			winMessage = "You get an initial blackjack and win!";
		}
		else {
			// The dealer has a blackjack
			if (dealer.getBestHandState() == Player.PlayerState.blackjack) {
				winMessage = lose;
			}
			// The human has a blackjack, and the dealer does not
			else if (human.getBestHandState() == Player.PlayerState.blackjack) {
				winMessage = win;
			}
			else if (human.getBestHandState() == Player.PlayerState.busted &&
					dealer.getBestHandState() == Player.PlayerState.safe) {
				winMessage = lose;
			}
			else if (human.getBestHandState() == Player.PlayerState.safe) {
				// the dealer busts, and the player is safe
				// neither player busts, and the human's score is higher
				if (dealer.getBestHandState() == Player.PlayerState.busted ||
					human.getBestHandScore() > dealer.getBestHandScore()) {
					winMessage = win;
				}
				// the human busts
				else {
					winMessage = lose;
				}
			}
			else {
				winMessage = "Both players bust! This shouldn't happen!";
			}
		}
	
		return winMessage;
	}
}
