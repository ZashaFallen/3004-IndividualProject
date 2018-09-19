package core;

import org.junit.Test;
import java.util.*;
import junit.framework.TestCase;

public class DeckClassTests extends TestCase{
	
	/**************
	 * Purpose:  Verify that a new deck has 52 cards.
	 **************/
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
	
	
	/**************
	 * Purpose:  Test the Deck.checkDuplicates() function
	 * 		can properly detect decks with duplicates.
	 **************/
	@Test
	public void testCheckDuplicates() {
		Deck deck = new Deck();
		deck.cards = new ArrayList<Card>();
		
		deck.cards.add(new Card("S", "K", 10));
		deck.cards.add(new Card("H", "A", 0));
		deck.cards.add(new Card("S", "K", 10));
		deck.cards.add(new Card("C", "A", 0));
		assertFalse(deck.checkDuplicates());
		
		deck.cards = new ArrayList<Card>();
		deck.cards.add(new Card("S", "K", 10));
		deck.cards.add(new Card("H", "A", 0));
		deck.cards.add(new Card("D", "K", 10));
		deck.cards.add(new Card("C", "A", 0));
		assertTrue(deck.checkDuplicates());
	}
	
	
	/**************
	 * Purpose:  Check for duplicate cards in a deck after being 
	 * 	  	created.
	 **************/
	@Test
	public void testForDuplicatesInNewDeck() {
		Deck deck = new Deck();
		
		assertTrue(deck.checkDuplicates());
	}
}
