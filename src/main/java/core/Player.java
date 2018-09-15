package core;


public class Player {
	enum PlayerState {
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
	
	public Card hit(Deck deck) {
		Card newCard = deck.draw();
		hand.add(newCard);
		
		return newCard;
	}
	
	public int getHandScore() {
		return hand.getScore();
	}
	
	public int getSplitHandScore() {
		return splitHand.getScore();
	}
	
	public String showHand() {
		// implemented in 'HumanPlayer' and 'DealerPlayer'
		return null;
	}
	
	public PlayerState getHandState() {		
		return hand.checkState();
	}
	
	public PlayerState getSplitHandState() {	
		return splitHand.checkState();
	}
	
	public void takeTurn(Deck deck) {
		// implemented in 'HumanPlayer' and 'DealerPlayer'
	}
	
	protected void split() {
		
	}
}
