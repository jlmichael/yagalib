package yagalib.blackjack_example;

import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.List;

public class Card {

    public enum Suit {
        HEARTS ('H'),
        DIAMONDS ('D'),
        SPADES ('S'),
        CLUBS ('C');

        private final Character asChar;

        private Suit(Character asChar) {
            this.asChar = asChar;
        }

        @Override
        public String toString() {
            return String.valueOf(asChar);
        }
    }

    public enum Pip {
        TWO (2),
        THREE (3),
        FOUR (4),
        FIVE (5),
        SIX (6),
        SEVEN (7),
        EIGHT (8),
        NINE (9),
        TEN (10),
        JACK (10),
        QUEEN (10),
        KING (10),
        ACE (1);

        private final int handValue;

        private Pip(int handValue) {
            this.handValue = handValue;
        }

        @Override
        public String toString() {
            return String.valueOf(handValue);
        }
    }

    private Pip pip;
    private Suit suit;

    private static Logger logger = Logger.getLogger(Card.class);

    public Card(Pip pip, Suit suit) throws Exception {
        this.pip = pip;
        this.suit = suit;
    }

    public Pip getPip() {
        return pip;
    }

    public Suit getSuit() {
        return suit;
    }

    public int getValue() {
        return pip.handValue;
    }

    @Override
    public String toString() {
        return pip.toString() + suit.toString();
    }
}
