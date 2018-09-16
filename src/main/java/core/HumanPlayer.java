package core;

public class HumanPlayer extends Player {

	public HumanPlayer(Deck deck) {
		super(deck);
	}
	
	@Override
	public String showHand() {
		String handString; 
		if (splitHand == null) {
			handString = "Your Hand: " + hand.getCards();
		}
		else {
			handString = "Your First Hand: " + hand.getCards() + 
						 "\r\nYour Second Hand: " + splitHand.getCards();
		}
		return handString;
	}
	
	@Override
	public void takeTurn(Deck deck) {
		String response = "";
		String extraHandText = "";
		
		while (!response.equals("S") && getBestHandState() != PlayerState.busted) {
			response = ConsoleIO.input("\r\nWould you like to (h)hit, (s)stay, or (d)split? " + extraHandText + ": ");
			
			if (response.equals("H")) {
				System.out.println("You hit" + extraHandText + ": " + currentHand.hit(deck).toString());
				System.out.println("Current score" + extraHandText + ": " + currentHand.getScore());
				
				if(currentHand.getState() == PlayerState.busted) {
					System.out.println("\r\nYou bust" + extraHandText + "!");
					
					if (currentHand == hand && split) {
						currentHand = splitHand;
						response = "";
						extraHandText = " on your second hand";
					}
				}
			}
			else if (response.equals("S")) {
				System.out.println("You stay" + extraHandText + ".");
				if (currentHand == hand && split) {
					currentHand = splitHand;
					response = "";
					extraHandText = " on your second hand";
				}
			}
			else if (response.equals("D")) {
				if (hand.canSplit() && !split) {
					System.out.println("You split!");
					split(deck);
					extraHandText = " for your first hand";
					System.out.println(showHand());
				}
				else {
					System.out.println("You may only split when your initial two cards are of the same rank.\r\n\r\n");
				}
			}
			else if (response.equals("INVALID")) {
				System.out.println("That's not a valid option.\r\n\r\n");
			}
		}
		
		System.out.println(this.showHand());
	}
}
