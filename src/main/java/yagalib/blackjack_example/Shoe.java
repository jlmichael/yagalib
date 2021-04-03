package yagalib.blackjack_example;

import yagalib.EvolutionManager;

import java.util.*;

public class Shoe {

    private int numDecks;
    private Stack<Card> cards;
    private int reshuffleAtPercent;
    private Boolean needsReshuffleIndicator;

    public Shoe(int numDecks, int reshuffleAtPercent) throws Exception {
        this.numDecks = numDecks;
        this.reshuffleAtPercent = reshuffleAtPercent;
        this.cards = new Stack<Card>();
        this.needsReshuffleIndicator = false;
        reshuffleShoe();
    }

    public int getReshuffleAtPercent() {
        return reshuffleAtPercent;
    }

    public void setReshuffleAtPercent(int reshuffleAtPercent) {
        this.reshuffleAtPercent = reshuffleAtPercent;
    }

    public int getNumDecks() {
        return numDecks;
    }

    public void setNumDecks(int numDecks) {
        this.numDecks = numDecks;
    }

    public Boolean needsReshuffle() {
        return needsReshuffleIndicator;
    }

    public Card getNextCard() {
        Card card = cards.pop();
        if(cards.size() <= (52 * numDecks * reshuffleAtPercent / 100)) {
                needsReshuffleIndicator = true;
        }
        return card;
    }

    public void reshuffleShoe() throws Exception {
        cards.clear();
        needsReshuffleIndicator = false;
        for(int i = 0; i < numDecks; i++) {
            for(Card.Pip pip : Card.Pip.values()) {
                for(int j = 0; j < 4; j++) {
                    try {
                        cards.push(new Card(pip));
                    } catch (Exception e) {
                        throw new Exception("Error when trying to create Shoe: ", e);
                    }
                }
            }
        }
        Collections.shuffle(cards, EvolutionManager.random);
    }
}
