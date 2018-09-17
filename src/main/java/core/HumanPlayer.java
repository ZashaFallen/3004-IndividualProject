package core;

import java.util.List;

public class HumanPlayer extends Player {
	
	public List<String> fileCommands; 
	
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
		
		while (currentHand != null && getBestHandState() != PlayerState.busted) {
			response = ConsoleIO.input("\r\nWould you like to (h)hit, (s)stay, or (d) split", extraHandText, "? ");
			
			if (response.equals("H")) {
				ConsoleIO.outputln("You hit", extraHandText, ": ", currentHand.hit(deck).toString());
				ConsoleIO.outputln("Current score", extraHandText, ": ", Integer.toString(currentHand.getScore()));
				
				if(currentHand.getState() == PlayerState.busted) {
					ConsoleIO.outputln("You bust", extraHandText + "!");
					
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
				}
				else {
					ConsoleIO.outputln("You may only split when your initial two cards are of the same rank.\r\n");
				}
			}
			else if (response.equals("INVALID")) {
				ConsoleIO.outputln("That's not a valid option.\r\n");
			}
		}
		
		ConsoleIO.outputln(this.getHand());
	}
}
