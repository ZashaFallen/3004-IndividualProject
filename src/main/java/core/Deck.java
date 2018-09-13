package core;

import java.util.*;

public class Deck {	
	public static String[] suits = {"Clubs", "Diamonds", "Hearts", "Spades"};
	public static Map<String, Integer> ranks = new HashMap<String, Integer>();
	
	public List<Card> cards = new ArrayList<Card>();
	
	public Deck() {
		populateRanks();
		
		for (String s : suits) {
			for (String r : ranks.keySet()) {
				cards.add(new Card(s, r, ranks.get(r)));
			}
		}
	}	
	

	public void shuffle() {
		Collections.shuffle(cards);
	}
	
	
	public Card draw() {
		return cards.remove(0);
	}
	
	
	private void populateRanks() {
		ranks.put("Ace", 0);
		ranks.put("2", 2);
		ranks.put("3", 3);
		ranks.put("4", 4);
		ranks.put("5", 5);
		ranks.put("6", 6);
		ranks.put("7", 7);
		ranks.put("8", 8);
		ranks.put("9", 9);
		ranks.put("10", 10);
		ranks.put("Jack", 10);
		ranks.put("Queen", 10);
		ranks.put("King", 10);
	}
}
