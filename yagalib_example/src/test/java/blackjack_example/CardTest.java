package blackjack_example;

import junit.framework.TestCase;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class CardTest extends TestCase {
    @Test
    public void testConstructorBadPip() throws Exception {
        Exception thrown = null;
        try {
            Card card = new Card('X', 'H');
        } catch(Exception e) {
            thrown = e;
        }

        assertNotNull(thrown);
    }

    @Test
    public void testConstructorBadSuit() throws Exception {
        Exception thrown = null;
        try {
            Card card = new Card('2', 'X');
        } catch(Exception e) {
            thrown = e;
        }

        assertNotNull(thrown);
    }

    @Test
    public void testGets() throws Exception {
        Card card = new Card('2', 'H');
        assertEquals('2', (char)card.getPip());
        assertEquals('H', (char)card.getSuit());
        assertEquals(2, card.getValue());
    }

    @Test
    public void testConstructAll() throws Exception {
        List<Card> cards = new ArrayList<Card>();
        Card card;
        for(Character pip : Card.ALLPIPS) {
            for(Character suit : Card.ALLSUITS) {
                card = new Card(pip, suit);
                cards.add(card);
            }
        }

        assertEquals(52, cards.size());

        card = cards.get(0);
        assertEquals("2H", card.toString());
        assertEquals(2, card.getValue());
        card = cards.get(1);
        assertEquals("2D", card.toString());
        assertEquals(2, card.getValue());
        card = cards.get(2);
        assertEquals("2S", card.toString());
        assertEquals(2, card.getValue());
        card = cards.get(3);
        assertEquals("2C", card.toString());
        assertEquals(2, card.getValue());

        card = cards.get(4);
        assertEquals("3H", card.toString());
        assertEquals(3, card.getValue());
        card = cards.get(5);
        assertEquals("3D", card.toString());
        assertEquals(3, card.getValue());
        card = cards.get(6);
        assertEquals("3S", card.toString());
        assertEquals(3, card.getValue());
        card = cards.get(7);
        assertEquals("3C", card.toString());
        assertEquals(3, card.getValue());

        card = cards.get(8);
        assertEquals("4H", card.toString());
        assertEquals(4, card.getValue());
        card = cards.get(9);
        assertEquals("4D", card.toString());
        assertEquals(4, card.getValue());
        card = cards.get(10);
        assertEquals("4S", card.toString());
        assertEquals(4, card.getValue());
        card = cards.get(11);
        assertEquals("4C", card.toString());
        assertEquals(4, card.getValue());

        card = cards.get(12);
        assertEquals("5H", card.toString());
        assertEquals(5, card.getValue());
        card = cards.get(13);
        assertEquals("5D", card.toString());
        assertEquals(5, card.getValue());
        card = cards.get(14);
        assertEquals("5S", card.toString());
        assertEquals(5, card.getValue());
        card = cards.get(15);
        assertEquals("5C", card.toString());
        assertEquals(5, card.getValue());

        card = cards.get(16);
        assertEquals("6H", card.toString());
        assertEquals(6, card.getValue());
        card = cards.get(17);
        assertEquals("6D", card.toString());
        assertEquals(6, card.getValue());
        card = cards.get(18);
        assertEquals("6S", card.toString());
        assertEquals(6, card.getValue());
        card = cards.get(19);
        assertEquals("6C", card.toString());
        assertEquals(6, card.getValue());

        card = cards.get(20);
        assertEquals("7H", card.toString());
        assertEquals(7, card.getValue());
        card = cards.get(21);
        assertEquals("7D", card.toString());
        assertEquals(7, card.getValue());
        card = cards.get(22);
        assertEquals("7S", card.toString());
        assertEquals(7, card.getValue());
        card = cards.get(23);
        assertEquals("7C", card.toString());
        assertEquals(7, card.getValue());

        card = cards.get(24);
        assertEquals("8H", card.toString());
        assertEquals(8, card.getValue());
        card = cards.get(25);
        assertEquals("8D", card.toString());
        assertEquals(8, card.getValue());
        card = cards.get(26);
        assertEquals("8S", card.toString());
        assertEquals(8, card.getValue());
        card = cards.get(27);
        assertEquals("8C", card.toString());
        assertEquals(8, card.getValue());

        card = cards.get(28);
        assertEquals("9H", card.toString());
        assertEquals(9, card.getValue());
        card = cards.get(29);
        assertEquals("9D", card.toString());
        assertEquals(9, card.getValue());
        card = cards.get(30);
        assertEquals("9S", card.toString());
        assertEquals(9, card.getValue());
        card = cards.get(31);
        assertEquals("9C", card.toString());
        assertEquals(9, card.getValue());

        card = cards.get(32);
        assertEquals("TH", card.toString());
        assertEquals(10, card.getValue());
        card = cards.get(33);
        assertEquals("TD", card.toString());
        assertEquals(10, card.getValue());
        card = cards.get(34);
        assertEquals("TS", card.toString());
        assertEquals(10, card.getValue());
        card = cards.get(35);
        assertEquals("TC", card.toString());
        assertEquals(10, card.getValue());

        card = cards.get(36);
        assertEquals("JH", card.toString());
        assertEquals(10, card.getValue());
        card = cards.get(37);
        assertEquals("JD", card.toString());
        assertEquals(10, card.getValue());
        card = cards.get(38);
        assertEquals("JS", card.toString());
        assertEquals(10, card.getValue());
        card = cards.get(39);
        assertEquals("JC", card.toString());
        assertEquals(10, card.getValue());

        card = cards.get(40);
        assertEquals("QH", card.toString());
        assertEquals(10, card.getValue());
        card = cards.get(41);
        assertEquals("QD", card.toString());
        assertEquals(10, card.getValue());
        card = cards.get(42);
        assertEquals("QS", card.toString());
        assertEquals(10, card.getValue());
        card = cards.get(43);
        assertEquals("QC", card.toString());
        assertEquals(10, card.getValue());

        card = cards.get(44);
        assertEquals("KH", card.toString());
        assertEquals(10, card.getValue());
        card = cards.get(45);
        assertEquals("KD", card.toString());
        assertEquals(10, card.getValue());
        card = cards.get(46);
        assertEquals("KS", card.toString());
        assertEquals(10, card.getValue());
        card = cards.get(47);
        assertEquals("KC", card.toString());
        assertEquals(10, card.getValue());

        card = cards.get(48);
        assertEquals("AH", card.toString());
        assertEquals(1, card.getValue());
        card = cards.get(49);
        assertEquals("AD", card.toString());
        assertEquals(1, card.getValue());
        card = cards.get(50);
        assertEquals("AS", card.toString());
        assertEquals(1, card.getValue());
        card = cards.get(51);
        assertEquals("AC", card.toString());
        assertEquals(1, card.getValue());
    }

}
