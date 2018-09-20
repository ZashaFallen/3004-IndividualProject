package core;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Hand {
	public enum HandState {
		safe,
		blackjack,
		busted,
		invalid
	}

	protected List<Card> cards = new ArrayList<Card>();
	
	private boolean aceWorth11 = false;
	private boolean alreadyHit = false;
	
	public void add(Card card) {
		cards.add(card);
	}
	
	/*************************
	 * Purpose: Return a string representation of the hand,
	 * 		with the cards joined by ', '.
	 *************************/
	public String getCards() {
		String playerHand = cards.stream().map(i -> i.toString()).collect(Collectors.joining(", "));
		
		if (cards.size() == 0) {
			playerHand = "Empty";
		}
		
		return playerHand;
	}
	
	/*************************
	 * Purpose: Return a string representation of the first card
	 * 		in the hand.
	 *************************/
	public String getFirstCard() {
		return cards.get(0).toString();
	}
	
	
	/*************************
	 * Purpose: Calculates a hand's score. The values are added up,
	 * 		with the aces initially counting as 11. Then, for each
	 * 		aces in the hand and as long as the score is above 21,
	 * 		subtract 10. If there is still an ace worth 11, aceWorth11
	 * 		will be set to true.
	 *************************/
	public int getScore() {
		int score = 0;
		int acesCount = 0;
		
		for (Card card : cards) {
			if (card.value == 0) {
				score += 11;
				acesCount += 1;
				aceWorth11 = true;
			}
			else {
				score += card.value;
			}
		}
		
		for (int i = acesCount; i > 0 && score > 21; i--) {
			score -= 10;
		}
		if (acesCount == 0) {
			aceWorth11 = false;
		}
		
		return score;
	}
	
	/*************************
	 * Purpose: Returns the sate of the hand. 
	 * If the score is greater than 21, the hand is busted.
	 * If the score is equal to 21, there is an ace worth 11,
	 * 		and the player has not hit on the hand, the hand
	 * 		is a blackjack.
	 * If the score is less than or equal to 21, the hand is
	 * 		safe.
	 *************************/
	public HandState getState() {
		HandState state = HandState.invalid;
		int score = this.getScore();
		
		if (score > 21) {
			state = HandState.busted;
		}
		else if (score == 21 && aceWorth11 && !alreadyHit) {
			state = HandState.blackjack;
		}
		else {
			state = HandState.safe;
		}
		
		return state;
	}

	/*************************
	 * Purpose: Remove the first card from the deck and add
	 * 		it to the hand. Returns the card pulled from the
	 * 		deck.
	 *************************/
	public Card hit(Deck deck) {
		alreadyHit = true;
		
		Card newCard = deck.draw();
		this.add(newCard);
		
		return newCard;
	}
	
	/*************************
	 * Purpose: Checks if the hand is able to be split. The hand
	 * 		must not have already hit, have two cards, and both
	 * 		cards must be identical in rank.
	 *************************/
	public boolean canSplit() {
		boolean check = false;

		if (!alreadyHit && 
			cards.size() == 2 && 
			cards.get(0).getRank().equals(cards.get(1).getRank())) {
			check = true;
		}
		
		return check;
	}
	
	public boolean getAceWorth11() {
		return aceWorth11;
	}
}
