package core;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import junit.framework.TestCase;

public class GameClassTests extends TestCase {	
	
	@Before
	public void init() {
		ConsoleIO.init();
	}
	
	@After
	public void close() {
		ConsoleIO.close();
	}
	
	@Test
	public void testSetGameState() {
		Game.GameState gameState = Game.getGameState("Q");
		assertEquals(Game.GameState.quit, gameState);
		
		gameState = Game.getGameState("F");
		assertEquals(Game.GameState.file, gameState);
		
		gameState = Game.getGameState("C");
		assertEquals(Game.GameState.console, gameState);
	}
	
	@Test
	public void testSetInvalidGameState() {
		Game.GameState gameState = Game.getGameState("");
		assertEquals(Game.GameState.invalid, gameState);
		
		gameState = Game.getGameState(null);
		assertEquals(Game.GameState.invalid, gameState);
		
		gameState = Game.getGameState("abc");
		assertEquals(Game.GameState.invalid, gameState);
	}
	
	@Test
	public void testCheckBlackJ() {
		Deck deck = new Deck();
		Game.human = new HumanPlayer(deck);
		Game.dealer = new DealerPlayer(deck);
		
		// neither have a blackJ
		Game.human.hand = new Hand();
		Game.human.hand.add(new Card("H", "9", 9));
		Game.human.hand.add(new Card("H", "5", 5));
		Game.dealer.hand = new Hand();
		Game.dealer.hand.add(new Card("H", "6", 6));
		Game.dealer.hand.add(new Card("S", "8", 8));
		assertEquals(false, Game.checkInitialBlackjack());
		
		
		// human has a blackJ
		Game.human.hand = new Hand();
		Game.human.hand.add(new Card("H", "K", 10));
		Game.human.hand.add(new Card("H", "A", 0));
		Game.dealer.hand = new Hand();
		Game.dealer.hand.add(new Card("H", "6", 6));
		Game.dealer.hand.add(new Card("S", "8", 8));
		assertEquals(true, Game.checkInitialBlackjack());
		
		// dealer has a blackJ
		Game.human.hand = new Hand();
		Game.human.hand.add(new Card("H", "9", 9));
		Game.human.hand.add(new Card("H", "5", 5));
		Game.dealer.hand = new Hand();
		Game.dealer.hand.add(new Card("H", "A", 0));
		Game.dealer.hand.add(new Card("S", "10", 10));
		assertEquals(true, Game.checkInitialBlackjack());
		
		// both have blackJ
		Game.human.hand = new Hand();
		Game.human.hand.add(new Card("H", "A", 0));
		Game.human.hand.add(new Card("H", "10", 10));
		Game.dealer.hand = new Hand();
		Game.dealer.hand.add(new Card("H", "J", 10));
		Game.dealer.hand.add(new Card("S", "A", 0));
		assertEquals(true, Game.checkInitialBlackjack());
	}
	
	
	@Test
	public void testWinner() {
		Deck deck = new Deck();
		Game.human = new HumanPlayer(deck);
		Game.dealer = new DealerPlayer(deck);
		
		// neither have a blackJ, human.getScore() > dealer.getScore()
		Game.human.hand = new Hand();
		Game.human.hand.add(new Card("H", "9", 9));
		Game.human.hand.add(new Card("H", "5", 5));
		Game.dealer.hand = new Hand();
		Game.dealer.hand.add(new Card("H", "6", 6));
		Game.dealer.hand.add(new Card("S", "6", 6));
		assertEquals("You Win!", Game.winner());
		
		// neither have a blackJ, human.getScore() = dealer.getScore()
		Game.human.hand = new Hand();
		Game.human.hand.add(new Card("H", "9", 9));
		Game.human.hand.add(new Card("H", "5", 5));
		Game.dealer.hand = new Hand();
		Game.dealer.hand.add(new Card("H", "7", 7));
		Game.dealer.hand.add(new Card("S", "7", 7));
		assertEquals("The Dealer Wins!", Game.winner());
		
		// neither have a blackJ, human.getScore() < dealer.getScore()
		Game.human.hand = new Hand();
		Game.human.hand.add(new Card("H", "9", 9));
		Game.human.hand.add(new Card("H", "5", 5));
		Game.dealer.hand = new Hand();
		Game.dealer.hand.add(new Card("H", "9", 9));
		Game.dealer.hand.add(new Card("S", "7", 7));
		assertEquals("The Dealer Wins!", Game.winner());
		
		// human has a blackJ
		Game.human.hand = new Hand();
		Game.human.hand.add(new Card("H", "K", 10));
		Game.human.hand.add(new Card("H", "A", 0));
		Game.dealer.hand = new Hand();
		Game.dealer.hand.add(new Card("H", "6", 6));
		Game.dealer.hand.add(new Card("S", "8", 8));
		assertEquals("You Win!", Game.winner());
		
		// dealer has a blackJ
		Game.human.hand = new Hand();
		Game.human.hand.add(new Card("H", "9", 9));
		Game.human.hand.add(new Card("H", "5", 5));
		Game.dealer.hand = new Hand();
		Game.dealer.hand.add(new Card("H", "A", 0));
		Game.dealer.hand.add(new Card("S", "10", 10));
		assertEquals("The Dealer Wins!", Game.winner());
		
		// both have blackJ
		Game.human.hand = new Hand();
		Game.human.hand.add(new Card("H", "A", 0));
		Game.human.hand.add(new Card("H", "10", 10));
		Game.dealer.hand = new Hand();
		Game.dealer.hand.add(new Card("H", "J", 10));
		Game.dealer.hand.add(new Card("S", "A", 0));
		assertEquals("The Dealer Wins!", Game.winner());
	}
}
