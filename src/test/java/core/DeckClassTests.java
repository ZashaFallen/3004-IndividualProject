package core;

import org.junit.Test;
import junit.framework.TestCase;

public class DeckClassTests extends TestCase{
	@Test
	public void testCreateDeck() {
		Deck deck = new Deck();
		
		assertEquals(52, deck.cards.size());
	}
}
