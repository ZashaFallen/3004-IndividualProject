package core;

import junit.framework.TestCase;

public class GameClassTests  extends TestCase {
	
	public void testValidateInputValidString() {
		Game game = new Game();
		
		assertEquals("C", game.ValidateInput("C"));
		assertEquals("C", game.ValidateInput("c"));
		
		assertEquals("F", game.ValidateInput("F"));
		assertEquals("F", game.ValidateInput("f"));
		
		assertEquals("H", game.ValidateInput("H"));
		assertEquals("H", game.ValidateInput("h"));
		
		assertEquals("S", game.ValidateInput("S"));
		assertEquals("S", game.ValidateInput("s"));
	}
	
	public void testValidateInputInvalidString() {
		Game game = new Game();
		
		assertEquals(null, game.ValidateInput("invalid"));
		assertEquals(null, game.ValidateInput(2));
		assertEquals(null, game.ValidateInput(2.4));
		assertEquals(null, game.ValidateInput(""));
	}
	
}
