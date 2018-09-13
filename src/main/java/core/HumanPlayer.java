package core;

public class HumanPlayer extends Player {

	public HumanPlayer(Deck deck) {
		super(deck);
	}
	
	public String showHand() {
		return "Your Hand: " + hand.getCards();
	}
}
