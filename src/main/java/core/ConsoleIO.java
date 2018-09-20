package core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class ConsoleIO {
	protected static boolean inputError = false;
	protected static List<String> fileCommands = null;
	
	private static Scanner s;
	private static final String[] validInputs =  {"Q", "C", "F", "H", "S", "D", "CD", "FD"};
	private static boolean initialized = false;
	
	
	/**************
	 * Purpose:  Receives any number of strings as an input to
	 * 		show the user before asking for input. If the list
	 * 		of player commands parsed from the file is not null,
	 * 		the next player command is supplied instead of 
	 * 		requesting user input.
	 **************/
	public static String input(String... message) {
		String input = null;
		
		if (fileCommands != null) {
			if (fileCommands.size() > 0) {
				input = fileCommands.remove(0);
			}
			else {
				input = "";
				inputError = true;
			}
			
		}
		else {
			if (!initialized) {
				init();
			}
			output(message);
			input = validateInput(s.nextLine());
		}
		
		return input;
	}
	
	/**************
	 * Purpose:  Print a message containing any number of strings
	 **************/
	public static void output(String... message) {
		
		for (String m : message) {
			System.out.print(m);
		}
		
	}
	
	/**************
	 * Purpose:  Print a message containing any number of strings,
	 * 		and then a newline.
	 **************/
	public static void outputln(String... message) {
		output(message);
		output(System.lineSeparator());
	}
	
	
	/**************
	 * Purpose: Used to validate user input. Receives raw user input
	 * 				(from the console) and checks it against a list of 
	 * 				'valid' inputs.
	 * Input:   Raw user input.
	 * Output:  'null' if the input does not match an entry in the 
	 * 				list of valid inputs.
	 * 			If the input does match a valid input, then it is 
	 * 				returned as a string (upper case).
	 * Created:	 12/09/2018
	 * Modified: 15/09/2018
	 * 			   - moved to ConsoleIO	
	 **************/
	protected static String validateInput(Object rawInput) {
		String input = "";
		
		rawInput = rawInput.toString().toUpperCase();
		for (String s : validInputs) {
			if (Objects.equals(rawInput, s)) input = s;
		}
		 
		return input;
	}
	
	/**************
	 * Purpose:  Read and try to verify a file supplied
	 * 		by the user that describes a game.
	 **************/
	public static void readInputFile(String filePath) {
		File file = new File(filePath);
		
		if (file.exists()) {
			try(BufferedReader br = new BufferedReader(new FileReader(filePath))) {
				String fileText = "";
				List<String> fileContents;
			    StringBuilder sb = new StringBuilder();
			    String line = br.readLine();
			    
			    // read the file into a string, and split that string by spaces when added it to a List.
			    while (line != null) {
			        sb.append(line);
			        sb.append(System.lineSeparator());
			        line = br.readLine();
			    }
			    fileText = sb.toString();
			    fileContents = Arrays.asList(fileText.split("\\s"));
			    
			    Game.deck = new Deck();
			    Game.deck.cards = new ArrayList<Card>();
			    fileCommands = new ArrayList<String>(); 
			    for (String element : fileContents) {
					if (isValidCard(element)) {
						// element.substring(0, 1) contains the first character (the suit)
						// element.substring(1) contains the rest of the characters (the rank)
						Game.deck.cards.add(new Card(element.substring(0, 1).toUpperCase(), 
					       		 					 element.substring(1).toUpperCase(), 
					       		 					 Deck.ranks.get(element.substring(1).toUpperCase())));
					}
					else if (isValidPlayerCommand(element)) {
						fileCommands.add(element);
					}
					else {
						inputError = true;
					}
				}
			    
			    // verify that the deck doesn't have any duplicate cards
			    if (!Game.deck.checkDuplicates()) {
			    	inputError = true;
			    }

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				inputError = true;
			}
		}
		else {
			inputError = true;
		}
	}
	
	
	/**************
	 * Purpose:  Receives the string of a possible card from the input
	 * 		file, and tries to match it against the set of possible
	 * 		cards.
	 **************/
	public static boolean isValidCard(String card) {
		boolean check = false;
		String[] validSuits = { "H", "D", "S", "C" };
		String[] validRanks = { "A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K" };
		
		if (card != null && card.length() >= 2) {
			if (Arrays.asList(validSuits).contains(card.substring(0, 1).toUpperCase()) &
				Arrays.asList(validRanks).contains(card.substring(1).toUpperCase())) {
				check = true;
			}
		}
		
		return check;
	}
	
	
	/**************
	 * Purpose:  Receives the string of a possible player command
	 * 		 from the input file, and tries to match it against the
	 * 		 set of possible user inputs.
	 **************/
	public static boolean isValidPlayerCommand(String playerCommand) {
		boolean check = false;
		String[] validPlayerCommands = { "H", "S", "D" };
		
		if (playerCommand != null && Arrays.asList(validPlayerCommands).contains(playerCommand.toUpperCase())) {
			check = true;
		}
		
		return check;
	}
	
	/**************
	 * Purpose:  Initialize the scanner. A scanner should only be
	 * 		opened and closed once per execution.
	 **************/
	public static void init() {
		if (!initialized) {
			initialized = true;
			s = new Scanner(System.in);
		}
	}
	
	/**************
	 * Purpose:  Close the scanner. A scanner should only be
	 * 		opened and closed once per execution.
	 **************/
	public static void close() {
		s.close();
	}
}
