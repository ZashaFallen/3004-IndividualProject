package core;


public class Player {
	public enum PlayerState {
		safe,
		blackjack,
		busted,
		invalid
	}
	
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
				splitHand.getState() == PlayerState.safe ||
				splitHand.getState() == PlayerState.blackjack)) {
				bestScore = splitHand.getScore();
			}
		}
		
		return bestScore;
	}
	
	public String getHand() {
		// implemented in 'HumanPlayer' and 'DealerPlayer'
		return null;
	}
	
	public PlayerState getBestHandState() {		
		PlayerState bestState = PlayerState.invalid;
		
		if (splitHand == null) {
			bestState = hand.getState();
		}
		else {
			if (hand.getState() == PlayerState.blackjack ||
				splitHand.getState() == PlayerState.blackjack) {
				bestState = PlayerState.blackjack;
			}
			else if (hand.getState() == PlayerState.safe ||
					splitHand.getState() == PlayerState.safe) {
					bestState = PlayerState.safe;
			}
			else bestState = PlayerState.busted;
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
