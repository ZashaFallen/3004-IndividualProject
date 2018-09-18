package core;

public class DealerPlayer extends Player {
	
	public DealerPlayer(Deck deck) {
		super(deck);
	}
	
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
	
	public boolean checkHit(Hand handToCheck) {
		boolean check = false;
		int score = handToCheck.getScore();
		
		if (Game.human.getBestHandState() != Hand.HandState.busted) {
			if (score <= 16 || (
				score == 17 && handToCheck.aceWorth11)) {
			check = true;
			}
		}
		
		return check;
	}
	
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
		
		while(checkHit(currentHand)) {
			ConsoleIO.outputln("The dealer hits", extraHandString, ": ", currentHand.hit(deck).toString());
			
			if (!checkHit(currentHand)) {
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
