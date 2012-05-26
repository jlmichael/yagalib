package blackjack_example;

import junit.framework.TestCase;
import org.junit.Test;

public class RuleTest extends TestCase {

    @Test
    public void testIsValid() {
        Rule rule = new Rule();

        // Fail due to split on non-splittable
        rule.setDealerValue(1);
        rule.setHandValue(12);
        rule.setHandIsSoft(true);
        rule.setHandIsSplittable(false);
        rule.setCommand(Rule.SPLIT);
        assertFalse(rule.isValid());

        // valid after setting to HIT
        rule.setCommand(Rule.HIT);
        assertTrue(rule.isValid());

        // Fail due to isSoft=true and hand value < 12
        rule.setDealerValue(1);
        rule.setHandValue(10);
        rule.setHandIsSoft(true);
        rule.setHandIsSplittable(true);
        rule.setCommand(Rule.HIT);
        assertFalse(rule.isValid());

        // Valid after setting hand value to 12
        rule.setHandValue(12);
        assertTrue(rule.isValid());
    }

    @Test
    public void testAppliesToTableState() throws Exception {
        Rule rule = new Rule();
        rule.setDealerValue(1);
        rule.setHandValue(16);
        rule.setHandIsSoft(true);
        rule.setHandIsSplittable(false);
        rule.setCommand(Rule.HIT);

        Agent agent = new Agent();
        Hand hand = new Hand();

        hand.addCard(new Card('A', 'H'));
        hand.addCard(new Card('5', 'H'));
        agent.addHand(hand);

        // Fail due to non matching dealer card
        Card dealerCard = new Card('2', 'H');
        assertFalse(rule.appliesToTableState(dealerCard, hand, agent));

        // Fail due to non matching hand value
        dealerCard = new Card('A', 'H');
        hand.addCard(new Card('2', 'H'));
        assertFalse(rule.appliesToTableState(dealerCard, hand, agent));

        // Fail due to non matching soft status - looking for soft but hand is not
        agent.clearHands();
        hand.reset();
        hand.addCard(new Card('9', 'H'));
        hand.addCard(new Card('7', 'H'));
        agent.addHand(hand);
        assertFalse(rule.appliesToTableState(dealerCard, hand, agent));

        // Fail due to non matching soft status - looking for hard but hand is not
        rule.setHandIsSoft(false);
        agent.clearHands();
        hand.reset();
        hand.addCard(new Card('A', 'H'));
        hand.addCard(new Card('5', 'H'));
        agent.addHand(hand);
        assertFalse(rule.appliesToTableState(dealerCard, hand, agent));

        // Fail due to not allowed to split
        agent.clearHands();
        hand.reset();
        hand.addCard(new Card('9', 'H'));
        hand.addCard(new Card('7', 'H'));
        agent.addHand(hand);
        rule.setHandIsSplittable(true);
        assertFalse(rule.appliesToTableState(dealerCard, hand, agent));

        // Fail due to splittable
        agent.clearHands();
        hand.reset();
        hand.addCard(new Card('8', 'H'));
        hand.addCard(new Card('8', 'H'));
        agent.addHand(hand);
        rule.setHandIsSplittable(false);
        assertFalse(rule.appliesToTableState(dealerCard, hand, agent));

        // Valid
        rule.setHandIsSplittable(true);
        assertTrue(rule.appliesToTableState(dealerCard, hand, agent));
    }
    
}
