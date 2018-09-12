package core;

import org.junit.Test;

import junit.framework.TestCase;

public class GameClassTests  extends TestCase {
	
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
		assertEquals(null, Game.validateInput("invalid"));
		assertEquals(null, Game.validateInput(2));
		assertEquals(null, Game.validateInput(2.4));
		assertEquals(null, Game.validateInput(""));
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
