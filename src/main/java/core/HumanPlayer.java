package core;

import java.util.Scanner;

public class HumanPlayer extends Player {

	public HumanPlayer(Deck deck) {
		super(deck);
	}
	
	public String showHand() {
		return "Your Hand: " + hand.getCards();
	}
	
	public void takeTurn(Deck deck) {
		Scanner s = new Scanner(System.in);
		String response = "";
		
		while (!response.equals("S") && getHandState() != PlayerState.busted) {
			System.out.print("\r\nWould you like to (h)it or (s)tay?: ");
			response = Game.validateInput(s.nextLine());
			
			if (response.equals("H")) {
				System.out.println("You hit: " + hit(deck).toString());
				System.out.println("Current score: " + getScore());
				
				if (getHandState() == PlayerState.busted) {
					System.out.println("\r\nYou bust!");
				}
			}
			else if (response.equals("S")) {
				System.out.println("You stay.");
			}
			else if (response.equals("INVALID")) {
				System.out.println("That's not a valid option.\r\n\r\n");
			}
		}
		
		System.out.println(this.showHand());
		s.close();
	}
}
