package yagalib.blackjack_example;

import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.List;

public class Card {

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
            String result = String.valueOf(handValue);
            if (this.equals(TEN)) {
                result = "T";
            } else if (this.equals(JACK)) {
                result = "J";
            } else if (this.equals(QUEEN)) {
                result = "Q";
            } else if (this.equals(KING)) {
                result = "K";
            } else if (this.equals(ACE)) {
                result = "A";
            }
            return result;
        }
    }

    private Pip pip;

    private static Logger logger = Logger.getLogger(Card.class);

    public Card(Pip pip) throws Exception {
        this.pip = pip;
    }

    public Pip getPip() {
        return pip;
    }

    public int getValue() {
        return pip.handValue;
    }

    @Override
    public String toString() {
        return pip.toString();
    }
}
