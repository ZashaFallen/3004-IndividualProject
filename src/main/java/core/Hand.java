package core;

import java.util.ArrayList;
import java.util.List;

public class Hand {
	public List<Card> cards = new ArrayList<Card>();
	
	public void add(Card card) {
		cards.add(card);
	}
	
	
	public int getScore() {
		return 0;
	}
}
