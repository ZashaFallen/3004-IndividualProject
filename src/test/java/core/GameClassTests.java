package core;

import org.junit.Test;

import core.Game.GameState;

import org.junit.Before;

import java.util.ArrayList;
import java.util.List;

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
		Game.setGameState("Q");
		assertEquals(Game.GameState.quit, Game.gameState);
		
		Game.setGameState("F");
		assertEquals(Game.GameState.file, Game.gameState);
		
		Game.setGameState("C");
		assertEquals(Game.GameState.console, Game.gameState);
		
		Game.setGameState("");
		assertEquals(Game.GameState.invalid, Game.gameState);
		
		Game.setGameState("1");
		assertEquals(Game.GameState.invalid, Game.gameState);
		
		Game.setGameState("abc");
		assertEquals(Game.GameState.invalid, Game.gameState);
	}
	
	@Test
	public void testCheckBlackjack() {
		Deck deck = new Deck();
		Game.human = new HumanPlayer(deck);
		Game.dealer = new DealerPlayer(deck);
		
		// neither have a blackjack
		Game.human.hand = new Hand();
		Game.human.hand.add(new Card("H", "9", 9));
		Game.human.hand.add(new Card("H", "5", 5));
		Game.dealer.hand = new Hand();
		Game.dealer.hand.add(new Card("H", "6", 6));
		Game.dealer.hand.add(new Card("S", "8", 8));
		assertEquals(false, Game.checkInitialBlackjack());
		
		
		// human has a blackjack
		Game.human.hand = new Hand();
		Game.human.hand.add(new Card("H", "K", 10));
		Game.human.hand.add(new Card("H", "A", 0));
		Game.dealer.hand = new Hand();
		Game.dealer.hand.add(new Card("H", "6", 6));
		Game.dealer.hand.add(new Card("S", "8", 8));
		assertEquals(true, Game.checkInitialBlackjack());
		
		// dealer has a blackjack
		Game.human.hand = new Hand();
		Game.human.hand.add(new Card("H", "9", 9));
		Game.human.hand.add(new Card("H", "5", 5));
		Game.dealer.hand = new Hand();
		Game.dealer.hand.add(new Card("H", "A", 0));
		Game.dealer.hand.add(new Card("S", "10", 10));
		assertEquals(true, Game.checkInitialBlackjack());
		
		// both have blackjack
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
		
		// both are safe, human.getScore() > dealer.getScore()
		Game.human.hand = new Hand();
		Game.human.hand.add(new Card("H", "9", 9));
		Game.human.hand.add(new Card("H", "5", 5));
		Game.dealer.hand = new Hand();
		Game.dealer.hand.add(new Card("H", "6", 6));
		Game.dealer.hand.add(new Card("S", "6", 6));
		assertEquals(true, Game.getWinner());
		
		// both are safe, human.getScore() = dealer.getScore()
		Game.human.hand = new Hand();
		Game.human.hand.add(new Card("H", "9", 9));
		Game.human.hand.add(new Card("H", "5", 5));
		Game.dealer.hand = new Hand();
		Game.dealer.hand.add(new Card("H", "7", 7));
		Game.dealer.hand.add(new Card("S", "7", 7));
		assertEquals(false, Game.getWinner());
		
		// both are safe, human.getScore() < dealer.getScore()
		Game.human.hand = new Hand();
		Game.human.hand.add(new Card("H", "9", 9));
		Game.human.hand.add(new Card("H", "5", 5));
		Game.dealer.hand = new Hand();
		Game.dealer.hand.add(new Card("H", "9", 9));
		Game.dealer.hand.add(new Card("S", "7", 7));
		assertEquals(false, Game.getWinner());
		
		// human has an initial blackjack
		Game.human.hand = new Hand();
		Game.human.hand.add(new Card("H", "K", 10));
		Game.human.hand.add(new Card("H", "A", 0));
		Game.dealer.hand = new Hand();
		Game.dealer.hand.add(new Card("H", "6", 6));
		Game.dealer.hand.add(new Card("S", "8", 8));
		assertEquals(true, Game.getWinner());
		
		// dealer has an initial blackjack
		Game.human.hand = new Hand();
		Game.human.hand.add(new Card("H", "9", 9));
		Game.human.hand.add(new Card("H", "5", 5));
		Game.dealer.hand = new Hand();
		Game.dealer.hand.add(new Card("H", "A", 0));
		Game.dealer.hand.add(new Card("S", "10", 10));
		assertEquals(false, Game.getWinner());
		
		// both have an initial blackjack
		Game.human.hand = new Hand();
		Game.human.hand.add(new Card("H", "A", 0));
		Game.human.hand.add(new Card("H", "10", 10));
		Game.dealer.hand = new Hand();
		Game.dealer.hand.add(new Card("H", "J", 10));
		Game.dealer.hand.add(new Card("S", "A", 0));
		assertEquals(false, Game.getWinner());
		
		// dealer is safe, human busted
		Game.human.hand = new Hand();
		Game.human.hand.add(new Card("H", "K", 10));
		Game.human.hand.add(new Card("H", "10", 10));
		Game.human.hand.add(new Card("D", "8", 8));
		Game.dealer.hand = new Hand();
		Game.dealer.hand.add(new Card("H", "J", 10));
		Game.dealer.hand.add(new Card("S", "5", 5));
		assertEquals(false, Game.getWinner());
	}
	
	
	/*
	 * Purpose: test Game.setUp(), which either reads the input file or
	 * 		initializes the player objects, depending on the game state.
	 */
	@Test
	public void testSetUp() {
		Game.gameState = Game.GameState.console;
		Game.setUp();
		assertNotNull(Game.deck);
		assertNotNull(Game.human);
		assertNotNull(Game.dealer);
		
		Game.gameState = Game.GameState.file;
		Game.setUp();

		List<String> expectedPlayerCommands = new ArrayList<String>();
		List<Card> expectedPlayerCardList = new ArrayList<Card>();
		expectedPlayerCardList.add(new Card("S", "K", 10));
		expectedPlayerCardList.add(new Card("H", "A", 0));
		List<Card> expectedDealerCardList = new ArrayList<Card>();
		expectedDealerCardList.add(new Card("H", "Q", 10));
		expectedDealerCardList.add(new Card("C", "A", 0));
		
		assertFalse(ConsoleIO.inputError);
		assertEquals(expectedPlayerCommands, ConsoleIO.fileCommands);
		for (int i = 0; i < Game.human.hand.cards.size(); i++) {
			assertEquals(expectedPlayerCardList.get(i).toString(), Game.human.hand.cards.get(i).toString());
		}
		for (int i = 0; i < Game.dealer.hand.cards.size(); i++) {
			assertEquals(expectedDealerCardList.get(i).toString(), Game.dealer.hand.cards.get(i).toString());
		}
	}
}
