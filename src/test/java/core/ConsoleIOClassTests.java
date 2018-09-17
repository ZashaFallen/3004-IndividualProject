package core;

import org.junit.Test;
import org.junit.Before;

import java.util.ArrayList;
import java.util.List;

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
		
		assertEquals("", ConsoleIO.validateInput("invalid"));
		assertEquals("", ConsoleIO.validateInput(2));
		assertEquals("", ConsoleIO.validateInput(2.4));
		assertEquals("", ConsoleIO.validateInput(""));
	}
	
	@Test
	public void testReadInputFile() {
		ConsoleIO.readInputFile("/3004-IndividualProject/src/main/resources/input files/file1.txt");
		
		String expectedPlayerCommands[] = null;
		List<Card> expectedCardList = new ArrayList<Card>();
		expectedCardList.add(new Card("S", "K", 10));
		expectedCardList.add(new Card("H", "A", 0));
		expectedCardList.add(new Card("H", "Q", 10));
		expectedCardList.add(new Card("C", "A", 0));
			
		assertEquals(expectedCardList, ConsoleIO.inputFile.cardList);
		assertEquals(expectedPlayerCommands, ConsoleIO.inputFile.playerCommands);
	}
}
