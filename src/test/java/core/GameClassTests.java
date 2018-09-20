package core;

import org.junit.Test;

import org.junit.Before;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;

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
	
	
	/***********************
	 * Purpose: test Game.setUp(), which either reads the input file or
	 * 		initializes the player objects, depending on the game state.
	 ***********************/
	@Test
	public void testSetUp() {	
		Game.gameState = Game.GameState.file;
		Game.setUp();
		assertFalse(ConsoleIO.inputError);
		List<String> expectedPlayerCommands = new ArrayList<String>();
		List<Card> expectedCardList = new ArrayList<Card>();
		expectedCardList.add(new Card("S", "K", 10));
		expectedCardList.add(new Card("H", "A", 0));
		expectedCardList.add(new Card("H", "Q", 10));
		expectedCardList.add(new Card("C", "A", 0));
		
		assertEquals(expectedPlayerCommands, ConsoleIO.fileCommands);
		for (int i = 0; i < Game.deck.cards.size(); i++) {
			assertEquals(expectedCardList.get(i).toString(), Game.deck.cards.get(i).toString());
		}
		
		Game.gameState = Game.GameState.console;
		Game.setUp();
		assertNotNull(Game.deck);
		assertNotNull(Game.human);
		assertNotNull(Game.dealer);
	}
	
	/***********************
	 * Purpose: test the function that runs Game.takePlayerTurns().
	 ***********************/
	@Test
	public void testTakePlayerTurns() {
		Deck deck = new Deck();
		Game.human = new HumanPlayer(deck);
		Game.dealer = new DealerPlayer(deck);
		
		Game.takePlayerTurns();
		
		assertFalse(ConsoleIO.inputError);
		if (Game.human.getBestHandState() == Hand.HandState.busted) {
			assertNotSame(Hand.HandState.busted, Game.dealer.getBestHandState());
		}
		else {
			assertThat(Game.human.getBestHandState(), anyOf(is(Hand.HandState.blackjack), is(Hand.HandState.safe)));
		}
	}
}
