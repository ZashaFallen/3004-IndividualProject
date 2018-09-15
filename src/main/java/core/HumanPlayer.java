package core;

import java.util.Scanner;

public class HumanPlayer extends Player {

	public HumanPlayer(Deck deck) {
		super(deck);
	}
	
	@Override
	public String showHand() {
		String handString = "Your Hand: " + hand.getCards();
		if (splitHand != null) {
			handString += "\r\nYour Second Hand: " + splitHand.getCards();
		}
		return handString;
	}
	
	@Override
	public void takeTurn(Deck deck) {
		Scanner s = new Scanner(System.in);
		String response = "";
		
		while (!response.equals("S") && getBestHandState() != PlayerState.busted) {
			System.out.print("\r\nWould you like to (h)hit, (s)stay, or (d)split?: ");
			response = Game.validateInput(s.nextLine());
			
			if (response.equals("H")) {
				System.out.println("You hit: " + hand.hit(deck).toString());
				System.out.println("Current score: " + getBestHandScore());
				
				if (getBestHandState() == PlayerState.busted) {
					System.out.println("\r\nYou bust!");
				}
			}
			else if (response.equals("S")) {
				System.out.println("You stay.");
			}
			else if (response.equals("D")) {
				
			}
			else if (response.equals("INVALID")) {
				System.out.println("That's not a valid option.\r\n\r\n");
			}
		}
		
		System.out.println(this.showHand());
		s.close();
	}
}
