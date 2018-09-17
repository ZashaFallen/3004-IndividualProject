package core;

public class Card {	
	
	public int value;		// value is 0 for aces
	
	private String rank;
	private String suit;
	
	public Card (String suit, String rank, int value) {
		this.suit = suit;
		this.rank = rank;
		this.value = value;
	}
	
	
	@Override
	public String toString() {
		return suit + rank;
	}


	public String getRank() {
		return rank;
	}
}
