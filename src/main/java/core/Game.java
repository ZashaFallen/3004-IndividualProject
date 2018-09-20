package core;

public class Game {
	
	enum GameState {
		console,
		file,
		quit,
		invalid
	}
	
	public static GameState gameState = GameState.invalid;
	public static boolean debug;
	
	protected static HumanPlayer human;
	protected static DealerPlayer dealer;
	protected static Deck deck;	
	private final static String INPUT_FILE = "src/main/resources/input files/file1.txt";
	
	public static void main(String[] args) {
		ConsoleIO.init();
		
		while (gameState == GameState.invalid) {
			setGameState(ConsoleIO.input("Would you like to use (c)console or (f)file input?: "));
		}
		
		if (gameState != GameState.quit) {
			
			setUp();
			takePlayerTurns();
			
			if (!ConsoleIO.inputError) {
				showWinner();
			}
			else {
				ConsoleIO.output("There was an issue with the input file. Please verify that there are no duplicate cards, and the player commands are sufficient.");
			}
		}
		
		ConsoleIO.close();
	}

	
	/**************
	 * Purpose: Checks that there was no initial blackjack, and then 
	 * 		takes the players' turns. If the human busted, the dealer
	 * 		doesn't take their turn.
	 **************/
	protected static void takePlayerTurns() {
		if (!ConsoleIO.inputError) {
			if (!checkInitialBlackjack()) {
				human.takeTurn(deck);
				if (human.getBestHandState() != Hand.HandState.busted && !ConsoleIO.inputError) {
					dealer.takeTurn(deck);
				}
			}
		}
	}

	/**************
	 * Purpose: Set up the game. For console input, make a new deck
	 * 		and shuffle it. For file input, read the file input using
	 * 		ConsoleIO.readInputFile(). For both types of input, create
	 * 		the players and print the hands.
	 **************/
	protected static void setUp() {
		if (gameState == GameState.console) {
			deck = new Deck();
			deck.shuffle();
		}
		else if (gameState == GameState.file) {
			ConsoleIO.readInputFile(INPUT_FILE);
		}

		if (!ConsoleIO.inputError) {
			human = new HumanPlayer(deck);
			dealer = new DealerPlayer(deck);
			ConsoleIO.outputln(System.lineSeparator());
			ConsoleIO.outputln(dealer.getHand(false));
			ConsoleIO.outputln(human.getHand());
			ConsoleIO.output(System.lineSeparator());
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
	
	
	/**************
	 * Purpose: Check if either of the players have an initial
	 * 		blackjack.
	 **************/
	protected static boolean checkInitialBlackjack() {
		boolean check = false;
		
		if (dealer.getBestHandState() == Hand.HandState.blackjack) {
			check = true;
			dealer.initialBlackjack = true;
		}
		if (human.getBestHandState() == Hand.HandState.blackjack) {
			check = true;
			human.initialBlackjack = true;
		}
		
		return check;
	}
	
	
	/**************
	 * Purpose: Print the full hands and final scores of both players.
	 **************/
	protected static void displayBothHands() {
		ConsoleIO.output(System.lineSeparator());
		ConsoleIO.output(dealer.getHand(true) + " Final Score: " + dealer.getBestHandScore() + System.lineSeparator());
		ConsoleIO.output(human.getHand() + " Final Score: " + human.getBestHandScore() + System.lineSeparator());
	}
	
	
	/**************
	 * Purpose: Checks if the human won. True if they did, false if
	 * 		the dealer won.
	 **************/
	protected static boolean getWinner() {
		boolean humanWon = false;
		
		checkInitialBlackjack();
		
		// the player has an initial blackjack and the dealer doesn't
		if (!dealer.initialBlackjack && human.initialBlackjack) {
			humanWon = true;
		}
		// the dealer busts, and the player is safe
		// neither player busts, and the human's score is higher
		else if (human.getBestHandState() == Hand.HandState.safe && (
				dealer.getBestHandState() == Hand.HandState.busted ||
				human.getBestHandScore() > dealer.getBestHandScore())) {
			humanWon = true;
		}
	
		return humanWon;
	}
	
	
	/**************
	 * Purpose: Print whether the human won or lost, and if someone
	 * 		got an initial blackjack.
	 **************/
	private static void showWinner() {
		if (!ConsoleIO.inputError) {
			displayBothHands();
			
			if (getWinner()) {
				if (checkInitialBlackjack()) {
					ConsoleIO.output("You got an initial blackjack, and the dealer didn't!");
				}
				ConsoleIO.output("You won!");
			}
			else {
				if (checkInitialBlackjack()) {
					ConsoleIO.output("The dealer got an initial blackjack! ");
				}
				ConsoleIO.output("You lost!");
			}
		}
		
	}
}
