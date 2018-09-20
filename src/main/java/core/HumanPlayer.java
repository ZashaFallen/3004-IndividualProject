package core;

public class HumanPlayer extends Player {
	
	public HumanPlayer(Deck deck) {
		super(deck);
	}
	
	/*************************
	 * Purpose: Returns the human's hand in string form. If
	 * 		the human has split, return both hands.
	 *************************/
	@Override
	public String getHand() {
		String handString; 
		if (splitHand == null) {
			handString = "Your Hand: " + hand.getCards();
		}
		else {
			handString = "Your First Hand: " + hand.getCards() + 
						 "\r\nYour Second Hand: " + splitHand.getCards();
		}
		return handString;
	}
	
	/*************************
	 * Purpose: Takes the human's turn. Loops while the current hand 
	 * 		still exists, the player hasn't busted (both hands), and 
	 * 		there hasn't been any error with the file input.
	 * 	Requests input from ConsoleIO.input, and performs operations
	 * 		on the hand depending on user input.
	 *  If the user had split before they bust or stay, the current
	 *  	hand is set to the split hand and the loop continues. 
	 *  	Otherwise, the current hand is set to null and the loop
	 *  	escapes. 
	 *************************/
	@Override
	public void takeTurn(Deck deck) {
		String response = "";
		String extraHandText = "";
		
		while (currentHand != null && getBestHandState() != Hand.HandState.busted && !ConsoleIO.inputError) {
			response = ConsoleIO.input("\r\nWould you like to (h)hit, (s)stay, or (d) split", extraHandText, "? ");
			
			switch(response) {
				case "H" :
					ConsoleIO.outputln("You hit", extraHandText, ": ", currentHand.hit(deck).toString());
					ConsoleIO.outputln("Current score", extraHandText, ": ", Integer.toString(currentHand.getScore()));
					
					if(currentHand.getState() == Hand.HandState.busted) {
						ConsoleIO.outputln("You bust", extraHandText + "!");
						ConsoleIO.output(System.lineSeparator());
						
						if (currentHand == hand && split) {
							currentHand = splitHand;
							extraHandText = " on your second hand";
						}
						else {
							currentHand = null;
						}
					}
					break;
				
				case "S" :
					ConsoleIO.outputln("You stay", extraHandText, ".");
					ConsoleIO.output(System.lineSeparator());
					if (currentHand == hand && split) {
						currentHand = splitHand;
						extraHandText = " on your second hand";
					}
					else {
						currentHand = null;
					}
					break;
					
				case "D" :
					if (hand.canSplit() && !split) {
						ConsoleIO.outputln("You split!");
						split(deck);
						extraHandText = " for your first hand";
						ConsoleIO.outputln(getHand());
						ConsoleIO.output(System.lineSeparator());
					}
					else {
						ConsoleIO.outputln("You may only split when your initial two cards are of the same rank.");
					}
					break;
					
				default :
					ConsoleIO.outputln("That's not a valid option.\r\n");
					break;
			}
		}
		
		ConsoleIO.outputln(this.getHand());
	}
}
