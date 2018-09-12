package core;

import org.junit.Test;
import junit.framework.TestCase;

public class CardClassTests extends TestCase{
	@Test
	public void testCreateCard() {
		Card card = new Card(Card.Suit.Clubs, Card.Rank.Three);
		
		assertEquals("C3", card.toString());
	}
}
