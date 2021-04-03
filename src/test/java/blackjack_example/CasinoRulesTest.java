package blackjack_example;

import junit.framework.TestCase;
import org.junit.Test;
import yagalib.blackjack_example.Agent;
import yagalib.blackjack_example.Card;
import yagalib.blackjack_example.CasinoRules;
import yagalib.blackjack_example.Hand;

public class CasinoRulesTest extends TestCase {

    @Test
    public void testAgentCanSplitHand() throws Exception {
        Agent agent = new Agent();
        Hand hand = new Hand();

        // Test empty hand
        assertFalse(CasinoRules.agentCanSplitHand(agent, hand));
        agent.addHand(hand);
        assertFalse(CasinoRules.agentCanSplitHand(agent, hand));

        // Test hand too big
        hand.addCard(new Card(Card.Pip.TWO));
        hand.addCard(new Card(Card.Pip.TWO));
        hand.addCard(new Card(Card.Pip.TWO));
        assertFalse(CasinoRules.agentCanSplitHand(agent, hand));
        agent.clearHands();
        hand.reset();

        // Test not a pair
        agent.addHand(hand);
        hand.addCard(new Card(Card.Pip.TWO));
        hand.addCard(new Card(Card.Pip.THREE));
        assertFalse(CasinoRules.agentCanSplitHand(agent, hand));
        agent.clearHands();
        hand.reset();

        // Test too many splits already
        hand.addCard(new Card(Card.Pip.TWO));
        hand.addCard(new Card(Card.Pip.TWO));
        agent.addHand(hand);
        Hand secondHand = new Hand();
        secondHand.addCard(new Card(Card.Pip.TWO));
        secondHand.addCard(new Card(Card.Pip.TWO));
        agent.addHand(secondHand);
        CasinoRules.setResplitsAllowed(1);
        assertFalse(CasinoRules.agentCanSplitHand(agent, hand));
        assertFalse(CasinoRules.agentCanSplitHand(agent, secondHand));
        agent.clearHands();
        hand.reset();
        secondHand.getCards().clear();
        CasinoRules.setResplitsAllowed(-1);

        // Test cannot resplit aces
        hand.addCard(new Card(Card.Pip.ACE));
        hand.addCard(new Card(Card.Pip.ACE));
        agent.addHand(hand);
        secondHand.addCard(new Card(Card.Pip.ACE));
        secondHand.addCard(new Card(Card.Pip.ACE));
        agent.addHand(secondHand);
        CasinoRules.setCanResplitAces(false);
        assertFalse(CasinoRules.agentCanSplitHand(agent, hand));
        assertFalse(CasinoRules.agentCanSplitHand(agent, secondHand));
        agent.clearHands();
        hand.reset();
        secondHand.reset();

        // Test can split
        hand.addCard(new Card(Card.Pip.TWO));
        hand.addCard(new Card(Card.Pip.TWO));
        agent.addHand(hand);
        assertTrue(CasinoRules.agentCanSplitHand(agent, hand));
    }

    @Test
    public void testAgentCanDoubleHand() throws Exception {
        Agent agent = new Agent();
        Hand hand = new Hand();

        // Test too many cards
        hand.addCard(new Card(Card.Pip.TWO));
        hand.addCard(new Card(Card.Pip.TWO));
        hand.addCard(new Card(Card.Pip.TWO));
        agent.addHand(hand);
        assertFalse(CasinoRules.agentCanDoubleOnHand(agent, hand));
        agent.clearHands();
        hand.reset();

        // Test not 9, 10, 11
        CasinoRules.setDoubleOnNineTenElevenOnly(true);
        hand.addCard(new Card(Card.Pip.TWO));
        hand.addCard(new Card(Card.Pip.TWO));
        agent.addHand(hand);
        assertFalse(CasinoRules.agentCanDoubleOnHand(agent, hand));
        agent.clearHands();
        hand.reset();
        CasinoRules.setDoubleOnNineTenElevenOnly(false);

        // Test no double after split
        CasinoRules.setCanDoubleAfterSplit(false);
        hand.addCard(new Card(Card.Pip.TWO));
        hand.addCard(new Card(Card.Pip.TWO));
        agent.addHand(hand);
        Hand secondHand = new Hand();
        secondHand.addCard(new Card(Card.Pip.TWO));
        secondHand.addCard(new Card(Card.Pip.TWO));
        agent.addHand(hand);
        assertFalse(CasinoRules.agentCanDoubleOnHand(agent, hand));
        assertFalse(CasinoRules.agentCanDoubleOnHand(agent, secondHand));
        agent.clearHands();
        hand.reset();
        secondHand.reset();

        // Test can double
        CasinoRules.setDoubleOnNineTenElevenOnly(true);
        hand.addCard(new Card(Card.Pip.FIVE));
        hand.addCard(new Card(Card.Pip.FIVE));
        agent.addHand(hand);
        assertTrue(CasinoRules.agentCanDoubleOnHand(agent, hand));
    }
    
}
