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
		
		if (splitHand != null) {
			dealerHand += "\r\nDealer's Second Hand: " + splitHand.getCards();
		}
		
		return dealerHand;
	}
	
	public boolean checkHit(Hand handToCheck) {
		boolean check = false;
		int score = handToCheck.getScore();
		
		if (Game.human.getBestHandState() == PlayerState.busted) {
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
		String firstHand= "";
		
		System.out.print(System.lineSeparator());
		System.out.println(this.showHand(false));
		
		if (hand.canSplit() && hand.getScore() <= 17) {
			split(deck);
			firstHand = " for their first hand";
			System.out.println("The dealer splits.");
			System.out.println(this.showHand(true));
		}
		while(checkHit(hand)) {
			System.out.println("The dealer hits" + firstHand + ": " + hand.hit(deck).toString());
		}
		if (hand.getState() == PlayerState.busted) {
			System.out.println("The dealer busts " + firstHand + "!");
		}
		else {
			System.out.println("The dealer stays " + firstHand + ".");
		}
		
		if (split) {
			System.out.print(System.lineSeparator());
			
			while(checkHit(splitHand)) {
				System.out.println("The dealer hits for their second hand: " + splitHand.hit(deck).toString());
			}
			if (splitHand.getState() == PlayerState.busted) {
				System.out.println("The dealer busts for their second hand!");
			}
			else {
				System.out.println("The dealer stays for their second hand.");
			}
		}
		
		System.out.println(this.showHand(true));
	}
}
