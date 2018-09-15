package core;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.Test;
import junit.framework.TestCase;


public class PlayerClassTests extends TestCase {
	
	/**************
	 * Purpose:  Test a player's 'hit' option. A deck and a player are created,
	 * 		and the player's hand is populated with the first two cards of the 
	 * 		deck (Deck.draw(), Player.Player()). The player's hand is saved with
	 * 		a shallow copy into 'handCopy'. The player hits, and the new card is
	 * 		added to handCopy.
	 **************/
	@Test
	public void testPlayerHit() {
		Deck deck = new Deck();
		Hand handCopy = new Hand();
		
		Player player = new Player(deck);
		for (Card card : player.hand.cards) {
			handCopy.add(card);
		}
		
		handCopy.add(player.hit(deck));
		
		assertEquals(handCopy.getScore(), player.getScore());
	}
	
	@Test
	public void testDealerPlayerShowCards() {
		Deck deck = new Deck();
		DealerPlayer dealer = new DealerPlayer(deck);
		
		dealer.hand = new Hand();
		dealer.hand.add(new Card("C", "8", 8));
		assertEquals("Dealer's Hand: C8", dealer.showHand(false));
		
		dealer.hand.add(new Card("H", "A", 0));
		dealer.hand.add(new Card("D", "5", 5));
		assertEquals("Dealer's Hand: C8, HA, D5", dealer.showHand(true));
	}
	
	@Test
	public void testHumanPlayerShowCards() {
		Deck deck = new Deck();
		HumanPlayer human = new HumanPlayer(deck);
		
		human.hand = new Hand();
		human.hand.add(new Card("C", "8", 8));
		assertEquals("Your Hand: C8", human.showHand());
		
		human.hand.add(new Card("H", "A", 0));
		human.hand.add(new Card("D", "5", 5));
		assertEquals("Your Hand: C8, HA, D5", human.showHand());
	}
	
	@Test
	public void testDealerPlayerHit() {
		Deck deck = new Deck();
		DealerPlayer dealer = new DealerPlayer(deck);
		Game.human = new HumanPlayer(deck);
		
		// if dealer.getHandScore() <= 16, hit
		dealer.hand = new Hand();
		dealer.hand.add(new Card("C", "8", 8));
		assertEquals(true, dealer.checkHit());
		
		// if dealer.getHandScore() <= 16, hit
		dealer.hand.add(new Card("H", "6", 6));
		dealer.hand.add(new Card("H", "2", 2));
		assertEquals(true, dealer.checkHit());
		
		// if dealer.getHandScore() > 16 (and not soft 17), stay
		dealer.hand.add(new Card("C", "4", 8));
		assertEquals(false, dealer.checkHit());
		
		// if dealer.getHandScore() = 17 (soft 17), hit
		dealer.hand = new Hand();
		dealer.hand.add(new Card("C", "A", 0));
		dealer.hand.add(new Card("C", "3", 3));
		dealer.hand.add(new Card("C", "2", 2));
		assertEquals(true, dealer.checkHit());
		
		// if dealer.getHandScore() > 17 (with A), stay
		dealer.hand.add(new Card("H", "3", 3));
		assertEquals(false, dealer.checkHit());
	}
	
	@Test
	public void testDealerPlayerTurn() {
		Deck deck = new Deck();
		deck.shuffle();
		DealerPlayer dealer = new DealerPlayer(deck);
		Game.human = new HumanPlayer(deck);
		
		System.out.println("Full hand for test: " + dealer.showHand(true));
		dealer.takeTurn(deck);
		
		assertThat(dealer.getHandState(), anyOf(is(Player.PlayerState.blackjack), is(Player.PlayerState.safe), is(Player.PlayerState.busted)));
	}
	
	@Test
	public void testHumanPlayerTurn() {
		Deck deck = new Deck();
		deck.shuffle();
		HumanPlayer human = new HumanPlayer(deck);
		
		System.out.print(System.lineSeparator());
		System.out.print(human.showHand());
		human.takeTurn(deck);
		
		assertThat(human.getHandState(), anyOf(is(Player.PlayerState.blackjack), is(Player.PlayerState.safe), is(Player.PlayerState.busted)));
	}
}
