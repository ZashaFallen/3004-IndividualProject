package core;

public class Card {	
	private String rank;
	private String suit;
	private int value;
	
	public Card (String suit, String rank, int value) {
		this.suit = suit;
		this.rank = rank;
		this.value = value;
	}
	
	
	public String toString() {
		return suit + rank;
	}
}
