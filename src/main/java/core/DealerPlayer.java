package core;

public class DealerPlayer extends Player {

	public DealerPlayer(Deck deck) {
		super(deck);
	}
	
	public String showHand() {
		final String hiddenCard = "[FACE DOWN]";
		String dealerHand = "Dealer's Hand: " + hand.getFirstCard();
		
		for (int i = 1; i < hand.cards.size(); i++) {
			dealerHand = dealerHand + ", " + hiddenCard;
		}
		
		return dealerHand;
	}
	
	public boolean checkHit() {
		boolean check = false;
		int score = getScore();
		
		if (score <= 16) {
			check = true;
		}
		else if (score == 17 && hand.containsAce()) {
			check = true;
		}
		else {
			check = false;
		}
		
		return check;
	}
}
