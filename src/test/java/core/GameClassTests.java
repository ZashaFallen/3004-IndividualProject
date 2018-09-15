package core;

import org.junit.Test;
import junit.framework.TestCase;

public class GameClassTests extends TestCase {
	
	@Test
	public void testValidateInputValidString() {
		assertEquals("C", Game.validateInput("C"));
		assertEquals("C", Game.validateInput("c"));
		
		assertEquals("F", Game.validateInput("F"));
		assertEquals("F", Game.validateInput("f"));
		
		assertEquals("H", Game.validateInput("H"));
		assertEquals("H", Game.validateInput("h"));
		
		assertEquals("S", Game.validateInput("S"));
		assertEquals("S", Game.validateInput("s"));
	}
	
	@Test
	public void testValidateInputInvalidString() {
		assertEquals("INVALID", Game.validateInput("invalid"));
		assertEquals("INVALID", Game.validateInput(2));
		assertEquals("INVALID", Game.validateInput(2.4));
		assertEquals("INVALID", Game.validateInput(""));
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
		
		gameState = Game.getGameState(123);
		assertEquals(Game.GameState.invalid, gameState);
		
		gameState = Game.getGameState("abc");
		assertEquals(Game.GameState.invalid, gameState);
	}
	
	@Test
	public void testCheckBlackjack() {
		Deck deck = new Deck();
		Game.human = new HumanPlayer(deck);
		Game.dealer = new DealerPlayer(deck);
		
		// neither have a blackjack
		Game.human.hand = new Hand();
		Game.human.hand.add(new Card("Hearts", "9", 9));
		Game.human.hand.add(new Card("Hearts", "5", 5));
		Game.dealer.hand = new Hand();
		Game.dealer.hand.add(new Card("Hearts", "6", 6));
		Game.dealer.hand.add(new Card("Spades", "8", 8));
		assertEquals(false, Game.checkInitialBlackjack());
		
		
		// human has a blackjack
		Game.human.hand = new Hand();
		Game.human.hand.add(new Card("Hearts", "King", 10));
		Game.human.hand.add(new Card("Hearts", "Ace", 0));
		Game.dealer.hand = new Hand();
		Game.dealer.hand.add(new Card("Hearts", "6", 6));
		Game.dealer.hand.add(new Card("Spades", "8", 8));
		assertEquals(true, Game.checkInitialBlackjack());
		
		// dealer has a blackjack
		Game.human.hand = new Hand();
		Game.human.hand.add(new Card("Hearts", "9", 9));
		Game.human.hand.add(new Card("Hearts", "5", 5));
		Game.dealer.hand = new Hand();
		Game.dealer.hand.add(new Card("Hearts", "Ace", 0));
		Game.dealer.hand.add(new Card("Spades", "10", 10));
		assertEquals(true, Game.checkInitialBlackjack());
		
		// both have blackjack
		Game.human.hand = new Hand();
		Game.human.hand.add(new Card("Hearts", "Ace", 0));
		Game.human.hand.add(new Card("Hearts", "10", 10));
		Game.dealer.hand = new Hand();
		Game.dealer.hand.add(new Card("Hearts", "Jack", 10));
		Game.dealer.hand.add(new Card("Spades", "Ace", 0));
		assertEquals(true, Game.checkInitialBlackjack());
	}
	
	
	@Test
	public void testWinner() {
		Deck deck = new Deck();
		Game.human = new HumanPlayer(deck);
		Game.dealer = new DealerPlayer(deck);
		
		// neither have a blackjack, human.getScore() > dealer.getScore()
		Game.human.hand = new Hand();
		Game.human.hand.add(new Card("Hearts", "9", 9));
		Game.human.hand.add(new Card("Hearts", "5", 5));
		Game.dealer.hand = new Hand();
		Game.dealer.hand.add(new Card("Hearts", "6", 6));
		Game.dealer.hand.add(new Card("Spades", "6", 6));
		assertEquals("You Win!", Game.winner());
		
		// neither have a blackjack, human.getScore() = dealer.getScore()
		Game.human.hand = new Hand();
		Game.human.hand.add(new Card("Hearts", "9", 9));
		Game.human.hand.add(new Card("Hearts", "5", 5));
		Game.dealer.hand = new Hand();
		Game.dealer.hand.add(new Card("Hearts", "7", 7));
		Game.dealer.hand.add(new Card("Spades", "7", 7));
		assertEquals("The Dealer Wins!", Game.winner());
		
		// neither have a blackjack, human.getScore() < dealer.getScore()
		Game.human.hand = new Hand();
		Game.human.hand.add(new Card("Hearts", "9", 9));
		Game.human.hand.add(new Card("Hearts", "5", 5));
		Game.dealer.hand = new Hand();
		Game.dealer.hand.add(new Card("Hearts", "9", 9));
		Game.dealer.hand.add(new Card("Spades", "7", 7));
		assertEquals("The Dealer Wins!", Game.winner());
		
		// human has a blackjack
		Game.human.hand = new Hand();
		Game.human.hand.add(new Card("Hearts", "King", 10));
		Game.human.hand.add(new Card("Hearts", "Ace", 0));
		Game.dealer.hand = new Hand();
		Game.dealer.hand.add(new Card("Hearts", "6", 6));
		Game.dealer.hand.add(new Card("Spades", "8", 8));
		assertEquals("You Win!", Game.winner());
		
		// dealer has a blackjack
		Game.human.hand = new Hand();
		Game.human.hand.add(new Card("Hearts", "9", 9));
		Game.human.hand.add(new Card("Hearts", "5", 5));
		Game.dealer.hand = new Hand();
		Game.dealer.hand.add(new Card("Hearts", "Ace", 0));
		Game.dealer.hand.add(new Card("Spades", "10", 10));
		assertEquals("The Dealer Wins!", Game.winner());
		
		// both have blackjack
		Game.human.hand = new Hand();
		Game.human.hand.add(new Card("Hearts", "Ace", 0));
		Game.human.hand.add(new Card("Hearts", "10", 10));
		Game.dealer.hand = new Hand();
		Game.dealer.hand.add(new Card("Hearts", "Jack", 10));
		Game.dealer.hand.add(new Card("Spades", "Ace", 0));
		assertEquals("The Dealer Wins!", Game.winner());
	}
}
