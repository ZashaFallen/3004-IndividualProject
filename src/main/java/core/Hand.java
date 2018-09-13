package core;

import java.util.ArrayList;
import java.util.List;

public class Hand {
	public List<Card> cards = new ArrayList<Card>();
	
	public void add(Card card) {
		cards.add(card);
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
}
