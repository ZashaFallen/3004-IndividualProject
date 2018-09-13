package core;

import java.util.*;

public class Deck {	
	public static String[] suits = {"C", "D", "H", "S"};
	public static Map<String, Integer> ranks = new HashMap<String, Integer>();
	
	public List<Card> cards = new ArrayList<Card>();
	
	public Deck() {
		populateRanks();
		
		for (String s : suits) {
			for (String r : ranks.keySet()) {
				cards.add(new Card(s, r, ranks.get(r)));
			}
		}
		
		Collections.shuffle(cards);
	}	
	

	public void shuffle() {
		
	}
	
	
	private void populateRanks() {
		ranks.put("A", 11);
		ranks.put("2", 2);
		ranks.put("3", 3);
		ranks.put("4", 4);
		ranks.put("5", 5);
		ranks.put("6", 6);
		ranks.put("7", 7);
		ranks.put("8", 8);
		ranks.put("9", 9);
		ranks.put("10", 10);
		ranks.put("J", 10);
		ranks.put("Q", 10);
		ranks.put("K", 10);
	}
}
