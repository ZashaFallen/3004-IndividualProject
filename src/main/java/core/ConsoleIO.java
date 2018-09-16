package core;

import java.util.Objects;
import java.util.Scanner;

public class ConsoleIO {
	private static Scanner s;
	private static final String[] validInputs =  {"Q", "C", "F", "H", "S", "D", "CD", "FD"};
	private static boolean initialized = false;
	
	public static String input(String message) {
		String input = null;
		
		if (!initialized) {
			init();
		}
		System.out.print(message);
		input = validateInput(s.nextLine());
		
		return input;
	}
	
	public static void init() {
		if (!initialized) {
			initialized = true;
			s = new Scanner(System.in);
		}
	}
	
	public static void close() {
		s.close();
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
		String input = null;
		
		rawInput = rawInput.toString().toUpperCase();
		for (String s : validInputs) {
			if (Objects.equals(rawInput, s)) input = s;
		}
		 
		return input;
	}
}
