package blackjack_example;

import junit.framework.TestCase;
import org.junit.Test;
import yagalib.blackjack_example.Card;
import yagalib.blackjack_example.Hand;

public class HandTest extends TestCase {

    @Test
    public void testHand() throws Exception {
        Hand hand = new Hand();
        hand.addCard(new Card('2', 'H'));
        hand.addCard(new Card('2', 'H'));
        assertTrue(!hand.isSoft());
        assertEquals(4, hand.getValue());

        hand.addCard(new Card('A', 'H'));
        assertTrue(hand.isSoft());
        assertEquals(15, hand.getValue());

        hand.addCard(new Card('A', 'H'));
        assertTrue(hand.isSoft());
        assertEquals(16, hand.getValue());

        hand.addCard(new Card('2', 'H'));
        assertTrue(hand.isSoft());
        assertEquals(18, hand.getValue());

        hand.addCard(new Card('T', 'H'));
        assertTrue(!hand.isSoft());
        assertEquals(18, hand.getValue());

        hand.addCard(new Card('T', 'H'));
        assertTrue(!hand.isSoft());
        assertEquals(28, hand.getValue());
    }    
}
