package core;

public class Card {	
	enum Suit {
		Clubs,
		Diamonds,
		Hearts,
		Spades
	}
	enum Rank {
		Ace,
		Two,
		Three,
		Four,
		Five,
		Six,
		Seven,
		Eight,
		Nine,
		Ten,
		Jack,
		Queen,
		King
	}
	
	private Rank rank;
	private Suit suit;
	private int value;
	
	public Card (Suit suit, Rank rank) {
		this.suit = suit;
		this.rank = rank;
	}
	
	
	public String toString() {
		
	}
}
