package core;

import org.junit.Test;
import junit.framework.TestCase;

public class CardClassTests extends TestCase{
	@Test
	public void testCreateCard() {
		Card card = new Card("C", "3", 3);
		assertEquals("C3", card.toString());
		
		card = new Card("C", "J", 10);
		assertEquals("CJ", card.toString());
	}
}
