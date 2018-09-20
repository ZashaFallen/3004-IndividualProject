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
	
	/*************************
	 * Purpose: Compares the scores of the player's hands, and 
	 * 		returns the best hand score.
	 *************************/
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
	
	/*************************
	 * Purpose: Compares the states of the player's hands, and 
	 * 		returns the best hand state.
	 *************************/
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
	
	/*************************
	 * Purpose: Splits the player's hands into two hands. The second
	 * 		card of the first hand is moved into the second hand, and
	 * 		both hands hit with the deck.
	 *************************/
	protected void split(Deck deck) {
		split = true;
		
		splitHand = new Hand();
		splitHand.add(hand.cards.remove(1));
		
		hand.hit(deck);
		splitHand.hit(deck);
	}
}
