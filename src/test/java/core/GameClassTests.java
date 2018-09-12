package core;

import org.junit.Test;

import junit.framework.TestCase;

public class GameClassTests  extends TestCase {
	
	@Test
	public void testValidateInputValidString() {
		assertEquals("C", Game.ValidateInput("C"));
		assertEquals("C", Game.ValidateInput("c"));
		
		assertEquals("F", Game.ValidateInput("F"));
		assertEquals("F", Game.ValidateInput("f"));
		
		assertEquals("H", Game.ValidateInput("H"));
		assertEquals("H", Game.ValidateInput("h"));
		
		assertEquals("S", Game.ValidateInput("S"));
		assertEquals("S", Game.ValidateInput("s"));
	}
	
	@Test
	public void testValidateInputInvalidString() {
		assertEquals(null, Game.ValidateInput("invalid"));
		assertEquals(null, Game.ValidateInput(2));
		assertEquals(null, Game.ValidateInput(2.4));
		assertEquals(null, Game.ValidateInput(""));
	}
	
	@Test
	public void testSetGameState() {
		Game.setGameState("Q");
		assertEquals("quit", Game.gameState);
		
		Game.setGameState("F");
		assertEquals("file", Game.gameState);
		
		Game.setGameState("C");
		assertEquals("console", Game.gameState);
	}
	
	@Test
	public void testSetInvalidGameState() {
		Game.setGameState("");
		assertEquals("", Game.gameState);
		
		Game.setGameState(123);
		assertEquals("", Game.gameState);
		
		Game.setGameState("abc");
		assertEquals("", Game.gameState);
	}
	
}
