package core;


public class Player {
	public Hand hand;
	
	public Player(Deck deck) {
		Hand hand = new Hand();
		
		hand.add(deck.draw());
		hand.add(deck.draw());
	}
	
	public Card hit(Deck deck) {
		
	}
	
	public int getScore() {
		return hand.getScore();
	}
}
