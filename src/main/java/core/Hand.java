package core;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Hand {
	public List<Card> cards = new ArrayList<Card>();
	
	private final String HAND_SAFE = "SAFE";
	private final String HAND_BLACKJACK = "BLACKJACK";
	private final String HAND_BUSTED = "BUSTED";
	
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
			}
			else {
				score += card.value;
			}
		}
		
		for (int i = acesCount; i > 0 && score > 21; i--) {
			score -= 10;
		}
		
		return score;
	}
	
	public String checkHandState() {
		String state = "";
		int score = this.getScore();
		
		if (score < 21) {
			state = HAND_SAFE;
		}
		else if (score == 21) {
			state = HAND_BLACKJACK;
		}
		else if (score > 21) {
			state = HAND_BUSTED;
		}
		
		return state;
	}
}
