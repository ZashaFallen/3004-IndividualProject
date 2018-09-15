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
	public void testGetScoreAs() {
		Hand hand = new Hand();
		
		// A can count as 1
		hand.add(new Card("C", "K", 10));
		hand.add(new Card("D", "5", 5));
		assertEquals(15, hand.getScore());
		hand.add(new Card("S", "A", 0));
		assertEquals(16, hand.getScore());
		
		// A can count as 11
		hand = new Hand();
		hand.add(new Card("D", "K", 10));
		hand.add(new Card("C", "A", 0));
		assertEquals(21, hand.getScore());
		
		// A hand can count two As worth 1 each
		hand = new Hand();
		hand.add(new Card("D", "K", 10));
		hand.add(new Card("C", "9", 9));
		assertEquals(19, hand.getScore());
		hand.add(new Card("H", "A", 0));
		hand.add(new Card("D", "A", 0));
		assertEquals(21, hand.getScore());
		
		// A can count as 11 then 1
		hand = new Hand();
		hand.add(new Card("C", "A", 0));
		hand.add(new Card("H", "8", 8));
		assertEquals(19, hand.getScore());
		hand.add(new Card("H", "9", 9));
		assertEquals(18, hand.getScore());
	}
	
	@Test
	public void testGetScoreFACards() {
		Hand hand = new Hand();
		
		hand.add(new Card("H", "J", 10));
		assertEquals(10, hand.getScore());
		
		hand = new Hand();
		hand.add(new Card("H", "Q", 10));
		assertEquals(10, hand.getScore());
		
		hand = new Hand();
		hand.add(new Card("H", "K", 10));
		assertEquals(10, hand.getScore());
	}
	
	@Test
	public void testGetCards() {
		Hand hand = new Hand();
		
		assertEquals("Empty", hand.getCards());
		
		hand.add(new Card("C", "A", 0));
		hand.add(new Card("H", "8", 8));
		hand.add(new Card("H", "K", 10));
		
		assertEquals("CA, H8, HK", hand.getCards());
	}
	
	@Test
	public void testGetFirstCard() {
		Hand hand = new Hand();
		
		assertEquals("Empty", hand.getCards());
		
		hand.add(new Card("C", "A", 0));
		hand.add(new Card("H", "8", 8));
		hand.add(new Card("H", "K", 10));
		
		assertEquals("CA", hand.getFirstCard());
	}
	
	@Test
	public void testGetState() {
		Hand hand = new Hand();
		
		hand.add(new Card("S", "K", 10));
		hand.add(new Card("S", "8", 8));
		assertEquals(Player.PlayerState.safe, hand.checkState());
		
		hand.add(new Card("H", "3", 3));
		assertEquals(Player.PlayerState.safe, hand.checkState());
		
		hand.add(new Card("H", "5", 5));
		assertEquals(Player.PlayerState.busted, hand.checkState());
		
		hand = new Hand();
		hand.add(new Card("S", "K", 10));
		hand.add(new Card("S", "A", 0));
		assertEquals(Player.PlayerState.blackjack, hand.checkState());
	}
	
	@Test
	public void testContainsA() {
		Hand hand = new Hand();
		
		assertEquals(false, hand.containsAce());
		
		hand.add(new Card("S", "K", 10));
		assertEquals(false, hand.containsAce());
		
		hand.add(new Card("C", "A", 0));
		assertEquals(true, hand.containsAce());
	}
	
}
