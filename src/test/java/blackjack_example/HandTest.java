package blackjack_example;

import junit.framework.TestCase;
import org.junit.Test;
import yagalib.blackjack_example.Card;
import yagalib.blackjack_example.Hand;

public class HandTest extends TestCase {

    @Test
    public void testHand() throws Exception {
        Hand hand = new Hand();
        hand.addCard(new Card(Card.Pip.TWO));
        hand.addCard(new Card(Card.Pip.TWO));
        assertTrue(!hand.isSoft());
        assertEquals(4, hand.getValue());

        hand.addCard(new Card(Card.Pip.ACE));
        assertTrue(hand.isSoft());
        assertEquals(15, hand.getValue());

        hand.addCard(new Card(Card.Pip.ACE));
        assertTrue(hand.isSoft());
        assertEquals(16, hand.getValue());

        hand.addCard(new Card(Card.Pip.TWO));
        assertTrue(hand.isSoft());
        assertEquals(18, hand.getValue());

        hand.addCard(new Card(Card.Pip.TEN));
        assertTrue(!hand.isSoft());
        assertEquals(18, hand.getValue());

        hand.addCard(new Card(Card.Pip.TEN));
        assertTrue(!hand.isSoft());
        assertEquals(28, hand.getValue());
    }    
}
