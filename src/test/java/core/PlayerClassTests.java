package core;

import org.junit.Test;
import junit.framework.TestCase;

public class PlayerClassTests extends TestCase {
	
	@Test
	public void testPlayerHit() {
		Deck deck = new Deck();
		
		Player player = new Player(deck);
		int initialScore = player.getScore();
		Card hitCard = player.hit(deck);
		
		assertEquals(initialScore + hitCard.value, player.getScore());
	}
	
}
