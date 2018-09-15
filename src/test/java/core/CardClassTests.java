package core;

import org.junit.Test;
import junit.framework.TestCase;

public class CardClassTests extends TestCase{
	@Test
	public void testCreateCard() {
		Card card = new Card("Clubs", "3", 3);
		assertEquals("3 of Clubs", card.toString());
		
		card = new Card("Clubs", "Jack", 10);
		assertEquals("Jack of Clubs", card.toString());
	}
}
