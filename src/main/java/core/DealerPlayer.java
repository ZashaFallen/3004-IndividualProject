package core;

public class DealerPlayer extends Player {

	public DealerPlayer(Deck deck) {
		super(deck);
	}
	
	public String showHand(boolean showHidden) {
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
		
		return dealerHand;
	}
	
	public boolean checkHit(Hand handToCheck) {
		boolean check = false;
		int score = handToCheck.getScore();
		
		if (Game.human.getHandState() == PlayerState.busted) {
			check = false;
		}
		else if (score <= 16) {
			check = true;
			showHand();
		}
		else if (score == 17 && handToCheck.containsAce()) {
			check = true;
			showHand();
		}
		else {
			check = false;
		}
		
		return check;
	}
	
	@Override
	public void takeTurn(Deck deck) {
		System.out.print(System.lineSeparator());
		System.out.println(this.showHand(false));
		
		if (hand.canSplit() && hand.getScore() <= 17) {
			split(deck);
		}
		
		while(checkHit(hand)) {
			System.out.println("The dealer hits: " + hand.hit(deck).toString());
		}
		if (getHandState() == PlayerState.busted) {
			System.out.println("The dealer busts!.");
		}
		else {
			System.out.println("The dealer stays.");
		}
		
		System.out.println(this.showHand(true));
	}
}
