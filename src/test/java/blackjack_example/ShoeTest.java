package blackjack_example;

import junit.framework.TestCase;
import org.junit.Test;
import yagalib.blackjack_example.Card;
import yagalib.blackjack_example.Shoe;

import java.util.HashMap;
import java.util.Map;

public class ShoeTest extends TestCase {

    @Test
    public void testConstructor() throws Exception {
        Shoe shoe = new Shoe(4, 10);
        assertEquals(4, shoe.getNumDecks());
        assertEquals(10, shoe.getReshuffleAtPercent());
    }

    @Test
    public void testReshuffleIndicator() throws Exception {
        Shoe shoe = new Shoe(1, 0);
        for(int i = 0; i < 51; i++) {
            shoe.getNextCard();
            assertFalse(shoe.needsReshuffle());
        }
        shoe.getNextCard();
        assertTrue(shoe.needsReshuffle());
    }

    @Test
    public void testGetNextCard() throws Exception {
        Shoe shoe = new Shoe(1, 0);
        Map<String, Integer> seenCards = new HashMap<String, Integer>();
        for(int i = 0; i < 52; i++) {
            Card card = shoe.getNextCard();
            if(seenCards.containsKey(card.toString())) {
                assertTrue(false);
            }
            seenCards.put(card.toString(), 1);
        }

        assertEquals(52, seenCards.size());
        for(Card.Pip pip : Card.Pip.values()) {
            for(int i = 0; i < 4; i++) {
                Card card = new Card(pip);
                assertTrue(seenCards.containsKey(card.toString()));
                assertEquals(1, (int)seenCards.get(card.toString()));
            }
        }

        shoe = new Shoe(4, 0);
        seenCards.clear();
        for(int i = 0; i < 52 * 4; i++) {
            if(shoe.needsReshuffle()) {
                shoe.reshuffleShoe();
            }
            Card card = shoe.getNextCard();
            if(!seenCards.containsKey(card.toString())) {
                seenCards.put(card.toString(), 1);
            } else {
                int count = seenCards.get(card.toString()) + 1;
                seenCards.put(card.toString(), count);
            }
        }

        assertEquals(52, seenCards.size());
        for(Card.Pip pip : Card.Pip.values()) {
            for(int i = 0; i < 4; i++) {
                Card card = new Card(pip);
                assertTrue(seenCards.containsKey(card.toString()));
                assertEquals(4, (int)seenCards.get(card.toString()));
            }
        }
    }
    
}
