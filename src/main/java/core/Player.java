package core;


public class Player {
	public Hand hand;
	public Hand splitHand = null;
	public boolean initialBlackjack = false;
	
	protected boolean split;
	protected Hand currentHand;
	
	public Player(Deck deck) {
		hand = new Hand();
		
		hand.add(deck.draw());
		hand.add(deck.draw());
		
		currentHand = hand;
	}
	
	public int getBestHandScore() {
		int bestScore = 0;
		
		if (hand.getScore() <= 21) {
			bestScore = hand.getScore();
		}
		if (splitHand != null) {
			if (bestScore <= splitHand.getScore() && (
				splitHand.getState() == Hand.HandState.safe ||
				splitHand.getState() == Hand.HandState.blackjack)) {
				bestScore = splitHand.getScore();
			}
		}
		
		return bestScore;
	}
	
	public String getHand() {
		// implemented in 'HumanPlayer' and 'DealerPlayer'
		return null;
	}
	
	public Hand.HandState getBestHandState() {		
		Hand.HandState bestState = Hand.HandState.invalid;
		
		if (splitHand == null) {
			bestState = hand.getState();
		}
		else {
			if (hand.getState() == Hand.HandState.blackjack ||
				splitHand.getState() == Hand.HandState.blackjack) {
				bestState = Hand.HandState.blackjack;
			}
			else if (hand.getState() == Hand.HandState.safe ||
					splitHand.getState() == Hand.HandState.safe) {
					bestState = Hand.HandState.safe;
			}
			else bestState = Hand.HandState.busted;
		}
		
		return bestState;
	}
	
	public void takeTurn(Deck deck) {
		// implemented in 'HumanPlayer' and 'DealerPlayer'
	}
	
	protected void split(Deck deck) {
		split = true;
		
		splitHand = new Hand();
		splitHand.add(hand.cards.remove(1));
		
		hand.hit(deck);
		splitHand.hit(deck);
	}
}
