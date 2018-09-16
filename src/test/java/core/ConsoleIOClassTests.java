package core;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import junit.framework.TestCase;

public class ConsoleIOClassTests extends TestCase {
	@Before
	public void init() {
		ConsoleIO.init();
	}
	
	@After
	public void close() {
		ConsoleIO.close();
	}
	
	@Test
	public void testValidateInputValidString() {
		assertEquals("C", ConsoleIO.validateInput("C"));
		assertEquals("C", ConsoleIO.validateInput("c"));
		
		assertEquals("F", ConsoleIO.validateInput("F"));
		assertEquals("F", ConsoleIO.validateInput("f"));
		
		assertEquals("H", ConsoleIO.validateInput("H"));
		assertEquals("H", ConsoleIO.validateInput("h"));
		
		assertEquals("S", ConsoleIO.validateInput("S"));
		assertEquals("S", ConsoleIO.validateInput("s"));
	}
	
	@Test
	public void testValidateInputInvalidString() {
		assertEquals(null, ConsoleIO.validateInput("invalid"));
		assertEquals(null, ConsoleIO.validateInput(2));
		assertEquals(null, ConsoleIO.validateInput(2.4));
		assertEquals(null, ConsoleIO.validateInput(""));
	}
}
