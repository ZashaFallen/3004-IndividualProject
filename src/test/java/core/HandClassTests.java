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
	}
	
	@Test
	public void testGetScoreAces() {
		Hand hand = new Hand();
		
		// Ace can count as 1
		hand.add(new Card("Clubs", "K", 10));
		hand.add(new Card("Diamonds", "5", 5));
		assertEquals(15, hand.getScore());
		hand.add(new Card("Spades", "A", 0));
		assertEquals(16, hand.getScore());
		
		// Ace can count as 11
		hand = new Hand();
		hand.add(new Card("Diamonds", "K", 10));
		hand.add(new Card("Clubs", "A", 0));
		assertEquals(21, hand.getScore());
		
		// A hand can count two Aces worth 1 each
		hand = new Hand();
		hand.add(new Card("Diamonds", "K", 10));
		hand.add(new Card("Clubs", "9", 9));
		assertEquals(19, hand.getScore());
		hand.add(new Card("Hearts", "A", 0));
		hand.add(new Card("Diamonds", "A", 0));
		assertEquals(21, hand.getScore());
		
		// Ace can count as 11 then 1
		hand = new Hand();
		hand.add(new Card("Clubs", "A", 0));
		hand.add(new Card("Hearts", "8", 8));
		assertEquals(19, hand.getScore());
		hand.add(new Card("Hearts", "9", 9));
		assertEquals(18, hand.getScore());
	}
	
	@Test
	public void testGetScoreFaceCards() {
		Hand hand = new Hand();
		
		hand.add(new Card("Hearts", "J", 10));
		assertEquals(10, hand.getScore());
		
		hand = new Hand();
		hand.add(new Card("Hearts", "Q", 10));
		assertEquals(10, hand.getScore());
		
		hand = new Hand();
		hand.add(new Card("Hearts", "K", 10));
		assertEquals(10, hand.getScore());
	}
	
	@Test
	public void testGetCards() {
		Hand hand = new Hand();
		
		assertEquals("Empty", hand.getCards());
		
		hand.add(new Card("Clubs", "A", 0));
		hand.add(new Card("Hearts", "8", 8));
		hand.add(new Card("Hearts", "K", 10));
		
		assertEquals("Ace of Clubs, 8 of Hearts, King of Hearts", hand.getCards());
	}
	
	@Test
	public void testGetFirstCard() {
		Hand hand = new Hand();
		
		assertEquals("Empty", hand.getCards());
		
		hand.add(new Card("Clubs", "A", 0));
		hand.add(new Card("Hearts", "8", 8));
		hand.add(new Card("Hearts", "K", 10));
		
		assertEquals("Ace of Clubs", hand.getFirstCard());
	}
	
}
