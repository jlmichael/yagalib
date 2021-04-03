package blackjack_example;

import junit.framework.TestCase;
import org.junit.Test;
import yagalib.blackjack_example.*;

import java.util.Arrays;

public class AgentTest extends TestCase {

    @Test
    public void testHandFunctions() {
        Agent agent = new Agent();
        Hand hand = new Hand();
        agent.addHand(hand);
        assertEquals(1, (int)agent.getHandCount());
        Hand otherHand = new Hand();
        agent.addHand(otherHand);
        assertEquals(2, (int)agent.getHandCount());
        agent.clearHands();
        assertEquals(0, (int)agent.getHandCount());
    }

    @Test
    public void testPromptForCommand() throws Exception {
        Agent agent = new Agent();
        Card dealerCard = new Card(Card.Pip.TWO);
        Hand hand = new Hand();
        hand.addCard(new Card(Card.Pip.TWO));
        hand.addCard(new Card(Card.Pip.TWO));

        // Assert that empty strategy yields default of hit
        assertEquals(Rule.HIT, agent.promptForCommand(dealerCard, hand));

        // Assert that a rule that does not apply is not selected
        Rule doubleRule = new Rule(3, 4, true, true, Rule.DOUBLE_OR_HIT);
        agent.getStrategy().getGenes().add(doubleRule);
        assertEquals(Rule.HIT, agent.promptForCommand(dealerCard, hand));

        // Assert that matching rule is selected
        Rule splitRule = new Rule(2, 4, false, true, Rule.SPLIT);
        Strategy splitStrategy = new Strategy(Arrays.asList(splitRule));
        agent.setStrategy(splitStrategy);
        assertEquals(Rule.SPLIT, agent.promptForCommand(dealerCard, hand));
    }

}
