package core;

public class DealerPlayer extends Player {
	
	public DealerPlayer(Deck deck) {
		super(deck);
	}
	
	/*************************
	 * Purpose: Returns the dealer's hand in string form.
	 * 		If the showHidden is true, all of the dealer's
	 * 		cards are visible. If the dealer split, return
	 * 		the dealer's second hand as well.
	 *************************/
	public String getHand(boolean showHidden) {
		String dealerHand = "Dealer's Hand: ";
		
		if (showHidden || Game.debug) {
			dealerHand += hand.getCards();
		}
		else {
			final String hiddenCard = "[FACE DOWN]";
			dealerHand += hand.getFirstCard();
			
			for (int i = 1; i < hand.cards.size(); i++) {
				dealerHand = dealerHand + ", " + hiddenCard;
			}
		}
		
		if (splitHand != null) {
			dealerHand += "\r\nDealer's Second Hand: " + splitHand.getCards();
		}
		
		return dealerHand;
	}
	
	/*************************
	 * Purpose: Checks if the dealer should hit on the current hand.
	 * 		The dealer will hit if the hand score is below 16, or
	 * 		is equal to 17 with 11 points coming from an ace. The
	 * 		dealer also does not need to hit if the player has busted.
	 *************************/
	public boolean checkHit() {
		boolean check = false;
		int score = currentHand.getScore();
		
		if (Game.human.getBestHandState() != Hand.HandState.busted) {
			if (score <= 16 || (
				score == 17 && currentHand.getAceWorth11())) {
			check = true;
			}
		}
		
		return check;
	}
	
	/*************************
	 * Purpose: Take the dealer's turn. If the dealer's initial two
	 * 		cards are identical in rank, and they total to 17 or less,
	 * 		the dealer splits with split(). The dealer uses checkHit()
	 * 		to decide if they should hit on each of their hand(s), until
	 * 		they've busted or stayed on them. Finally, print the dealer's
	 * 		full hand(s). 
	 *************************/
	@Override
	public void takeTurn(Deck deck) {
		String extraHandString = "";
		
		ConsoleIO.output(System.lineSeparator());
		ConsoleIO.outputln(this.getHand(false));
		
		if (hand.canSplit() && hand.getScore() <= 17) {
			split(deck);
			extraHandString = " for their first hand";
			ConsoleIO.outputln("\r\nThe dealer splits.");
			ConsoleIO.outputln(this.getHand(true));
		}
		
		while(checkHit()) {
			ConsoleIO.outputln("The dealer hits", extraHandString, ": ", currentHand.hit(deck).toString());
			
			if (!checkHit()) {
				if (currentHand.getState() == Hand.HandState.busted) {
					ConsoleIO.outputln("The dealer busts", extraHandString, "!");
				}
				else {
					ConsoleIO.outputln("The dealer stays", extraHandString, ".");
				}
				
				if (split && currentHand == hand) {
					currentHand = splitHand;
					extraHandString = " for their second hand";
				}
			}
		}
		
		ConsoleIO.outputln(this.getHand(true));
	}
}
