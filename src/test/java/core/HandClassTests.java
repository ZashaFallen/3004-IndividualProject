package core;

import org.junit.Test;
import junit.framework.TestCase;

public class HandClassTests extends TestCase{
	
	@Test
	public void testGetScore() {
		Hand hand = new Hand();
		
		hand.add(new Card("C", "K", 10));
		hand.add(new Card("D", "5", 5));
		assertEquals(15, hand.getScore());

		hand.add(new Card("A", "A", 0));
		assertEquals(16, hand.getScore());
		
		hand = new Hand();
		hand.add(new Card("A", "A", 0));
		hand.add(new Card("A", "A", 0));
		assertEquals(12, hand.getScore());
		
		hand.add(new Card("9", "9", 9));
		assertEquals(21, hand.getScore());
	}
	
}
