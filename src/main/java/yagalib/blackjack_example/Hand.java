package yagalib.blackjack_example;

import java.util.ArrayList;
import java.util.List;

public class Hand {

    private List<Card> cards;
    private Boolean containsAce;
    private Integer betOnHand;

    public Hand() {
        cards = new ArrayList<Card>();
        containsAce = false;
        betOnHand = 0;
    }

    public Integer getBetOnHand() {
        return betOnHand;
    }

    public void setBetOnHand(Integer betOnHand) {
        this.betOnHand = betOnHand;
    }

    public void addCard(Card card) {
        cards.add(card);
        if(card.getPip() == Card.Pip.ACE) {
            containsAce = true;
        }
    }

    public void reset() {
        cards.clear();
        containsAce = false;
        betOnHand = 0;
    }

    public List<Card> getCards() {
        return cards;
    }

    // Not sure if this is the best way to sum up a hand, but it makes sense to me
    public int getValue() {
        int value = 0;
        int aceCount = 0;
        for(Card card : cards) {
            if(card.getPip() == Card.Pip.ACE) {
                aceCount++;
            } else {
                value += card.getValue();
            }
        }
        for(int i = 0; i < aceCount; i++) {
            if(value + 11 <= 21) {
                value += 11;
            } else {
                value++;
            }
        }
        return value;
    }

    public Boolean isSoft() {
        // A hand is soft if it contains at least one ace currently being valued at 11 rather than 1
        if(!containsAce) {
            return false;
        }
        int value = 0;
        int aceCount = 0;
        for(Card card : cards) {
            if(card.getPip() == Card.Pip.ACE) {
                aceCount++;
            } else {
                value += card.getValue();
            }
        }
        for(int i = 0; i < aceCount; i++) {
            if(value + 11 <= 21) {
                return true;
            } else {
                value++;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "Hand{" +
                "cards=" + cards +
                '}';
    }
}
