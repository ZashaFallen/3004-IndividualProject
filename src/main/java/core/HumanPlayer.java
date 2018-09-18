package core;

public class HumanPlayer extends Player {
	
	public HumanPlayer(Deck deck) {
		super(deck);
	}
	
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
	
	@Override
	public void takeTurn(Deck deck) {
		String response = "";
		String extraHandText = "";
		
		while (currentHand != null && getBestHandState() != Hand.HandState.busted) {
			response = ConsoleIO.input("\r\nWould you like to (h)hit, (s)stay, or (d) split", extraHandText, "? ");
			
			if (response.equals("H")) {
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
			}
			else if (response.equals("S")) {
				ConsoleIO.outputln("You stay", extraHandText, ".");
				ConsoleIO.output(System.lineSeparator());
				if (currentHand == hand && split) {
					currentHand = splitHand;
					extraHandText = " on your second hand";
				}
				else {
					currentHand = null;
				}
			}
			else if (response.equals("D")) {
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
			}
			else if (response.equals("")) {
				ConsoleIO.outputln("That's not a valid option.\r\n");
			}
			else if (ConsoleIO.inputError) {
				ConsoleIO.outputln("Tried to get file input and did not find any more commands!");
				currentHand = null;
			}
		}
		
		ConsoleIO.outputln(this.getHand());
	}
}
