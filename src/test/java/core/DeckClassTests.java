package core;

import org.junit.Test;
import java.util.*;
import junit.framework.TestCase;

public class DeckClassTests extends TestCase{
	@Test
	public void testCreateDeck() {
		Deck deck = new Deck();
		
		assertEquals(52, deck.cards.size());
	}
	
	
	/**************
	 * Purpose:  Test that shuffling the deck returns a new deck
	 * 		with a different order.
	 **************/
	@Test
	public void testShuffleDeck() {
		Deck deck = new Deck();
		List<Card> initialCardList = new ArrayList<Card>(deck.cards);
		
		deck.shuffle();
		
		assertNotSame(deck.cards, initialCardList);
	}
	
	
	public void testCheckDuplicates() {
		Deck deck = new Deck();
		deck.cards = new ArrayList<Card>();
		
		deck.cards.add(new Card("S", "K", 10));
		deck.cards.add(new Card("H", "A", 0));
		deck.cards.add(new Card("S", "K", 10));
		deck.cards.add(new Card("C", "A", 0));
		assertTrue(deck.checkDuplicates());
		
		deck.cards = new ArrayList<Card>();
		deck.cards.add(new Card("S", "K", 10));
		deck.cards.add(new Card("H", "A", 0));
		deck.cards.add(new Card("D", "K", 10));
		deck.cards.add(new Card("C", "A", 0));
		assertFalse(deck.checkDuplicates());
	}
	
	
	/**************
	 * Purpose:  Check for duplicate cards in a deck after being 
	 * 	  	created. duplicateSet.Add() returns false if the item 
 *   *    	already exists in the set.
	 **************/
	@Test
	public void testForDuplicatesInNewDeck() {
		Deck deck = new Deck();
		boolean check = true;
		
		Set<Card> duplicateSet = new HashSet<Card>();
		
		for (Card c : deck.cards) {
			check = duplicateSet.add(c);
		}
		
		assertTrue(check);
	}
}
