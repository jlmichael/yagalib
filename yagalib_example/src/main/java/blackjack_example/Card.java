package blackjack_example;

import org.apache.log4j.Logger;

import java.util.Arrays;
import java.util.List;

public class Card {

    private Character pip;
    private Character suit;
    private int value;

    public static final List<Character> ALLPIPS = Arrays.asList('2', '3', '4', '5', '6', '7', '8', '9', 'T', 'J', 'Q', 'K', 'A');
    public static final List<Character> ALLSUITS = Arrays.asList('H', 'D', 'S', 'C');

    private static Logger logger = Logger.getLogger(Card.class);

    public Card(Character pip, Character suit) throws Exception {
        if(!ALLPIPS.contains(pip)) {
            logger.error("Tried to create a new Card with bad pip: " + pip);
            throw new Exception("Tried to create a new Card with bad pip: " + pip);
        }
        if(!ALLSUITS.contains(suit)) {
            logger.error("Tried to create a new Card with bad suit: " + suit);
            throw new Exception("Tried to create a new Card with bad suit: " + suit);
        }

        this.pip = pip;
        this.suit = suit;
        switch(this.pip) {
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
                this.value = Integer.valueOf(String.valueOf(this.pip));
                break;
            case 'A':
                this.value = 1;
                break;
            default:
                this.value = 10;
                break;
        }
    }

    public Character getPip() {
        return pip;
    }

    public Character getSuit() {
        return suit;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(pip) + String.valueOf(suit);
    }
}
