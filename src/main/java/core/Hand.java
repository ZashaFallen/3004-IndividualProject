package core;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Hand {
	public enum HandState {
		safe,
		blackjack,
		busted,
		invalid
	}

	protected List<Card> cards = new ArrayList<Card>();
	protected boolean aceWorth11 = false;
	
	private boolean alreadyHit = false;
	
	public void add(Card card) {
		cards.add(card);
	}
	
	public String getCards() {
		String playerHand = cards.stream().map(i -> i.toString()).collect(Collectors.joining(", "));
		
		if (cards.size() == 0) {
			playerHand = "Empty";
		}
		
		return playerHand;
	}
	
	public String getFirstCard() {
		return cards.get(0).toString();
	}
	
	public int getScore() {
		int score = 0;
		int acesCount = 0;
		
		for (Card card : cards) {
			if (card.value == 0) {
				score += 11;
				acesCount += 1;
				aceWorth11 = true;
			}
			else {
				score += card.value;
			}
		}
		
		for (int i = acesCount; i > 0 && score > 21; i--) {
			score -= 10;
		}
		if (acesCount == 0) {
			aceWorth11 = false;
		}
		
		return score;
	}
	
	public HandState getState() {
		HandState state = HandState.invalid;
		int score = this.getScore();
		
		if (score == 21 && cards.size() == 2 && !alreadyHit) {
			state = HandState.blackjack;
		}
		else if (score <= 21) {
			state = HandState.safe;
		}
		else if (score > 21) {
			state = HandState.busted;
		}
		
		return state;
	}

	
	public Card hit(Deck deck) {
		alreadyHit = true;
		
		Card newCard = deck.draw();
		this.add(newCard);
		
		return newCard;
	}
	
	public boolean canSplit() {
		boolean check;
		
		if (alreadyHit) {
			check = false;
		}
		else if (cards.size() == 2 && cards.get(0).getRank().equals(cards.get(1).getRank())) {
			check = true;
		}
		else {
			check = false;
		}
		
		return check;
	}
}
