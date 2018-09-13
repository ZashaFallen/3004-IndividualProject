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
}
